package com.example.sugarmanagedemo.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.activity.BaseActivity;
import org.kymjs.aframe.ui.widget.KJListView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.adapter.BTDevicesAdapter;
import com.example.sugarmanagedemo.bt.BluetoothBondUtils;
import com.example.sugarmanagedemo.constant.Constants;
import com.example.sugarmanagedemo.domain.StatusManager;
import com.example.sugarmanagedemo.pop.BindBTPop;
import com.example.sugarmanagedemo.pop.BindBTPop.onResultBindBTPop;
import com.example.sugarmanagedemo.pop.LoadingPop;
import com.example.sugarmanagedemo.pop.OpenBTPop;
import com.example.sugarmanagedemo.pop.OpenBTPop.onResultOpenBTPop;
import com.example.sugarmanagedemo.tc.ReqCmdEncodeFactory;
import com.example.sugarmanagedemo.tc.ResponseDataDecodeFactory;
import com.example.sugarmanagedemo.tc.TCDevice;
import com.example.sugarmanagedemo.tc.TCTransmitHelp;
import com.example.sugarmanagedemo.tc.respdomain.RespDataTransfer;
import com.example.sugarmanagedemo.util.AppUtil;

public class BTSearchActivity extends BaseActivity{
    private final static int USER_OPEN_BT = 101;
    private final static String PIN_BOUND = "9527";
    private final static String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    
    @BindView(id = R.id.btn_back)
    protected ImageButton mBtn_back;
    
    @BindView(id = R.id.btn_menu, click = true)
    protected ImageButton mBtn_menu;
    
    @BindView(id = R.id.txt_title)
    protected TextView mTxt_title;
    
    @BindView(id = R.id.linear_head)
    protected LinearLayout mLayout_head;
    
    @BindView(id = R.id.lv_devices)
    protected KJListView mListview_devicelist;
    
    @BindView(id = R.id.btn_search,click = true)
    protected Button mBtn_search;
    
    @BindView(id = R.id.btn_cancel,click = true)
    protected Button mBtn_cancel;
    
    protected View mParent;
    
    private BluetoothAdapter mBTAdapter;
    private BluetoothSocket mBTSocket;
    
    private BroadcastReceiver discoveryResult;
    private BroadcastReceiver discoveryMonitor;
    
    private List<BluetoothDevice> foundDevices;
    
    private BTDevicesAdapter mBTDevicesAdapter;
    
    
    private boolean isauto = false;
    private boolean isSearching = false;
    
    private OpenBTPop mOpenBTPop;
    private Handler mHandler;
    
    private BindBTPop mBindBTPop;
    
    private LoadingPop mLoadingPop;
    
    public BTSearchActivity(){
        this.setAllowFullScreen(true);
        this.setHiddenActionBar(true);
    }
    
    @Override
    public void setRootView() {
        mParent = getLayoutInflater().inflate(R.layout.activity_device_search, null);
        setContentView(mParent);
    }
    
    @Override
    public void initData(){
        super.initData();        
        
        if(null != this.getIntent().getExtras()){
            isauto = this.getIntent().getExtras().getBoolean(Constants.NEED_UPDATE_BT);
        }
        
        foundDevices = new ArrayList<BluetoothDevice>();
        mBTDevicesAdapter = new BTDevicesAdapter(BTSearchActivity.this,foundDevices);
        mListview_devicelist.setAdapter(mBTDevicesAdapter);
        mHandler = new Handler();
        
        this.discoveryResult = new BroadcastReceiver()
        {
            public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
            {
                if ("android.bluetooth.device.action.FOUND".equals(paramAnonymousIntent.getAction()))
                {
                  BluetoothDevice localBluetoothDevice = (BluetoothDevice)paramAnonymousIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                  String str = localBluetoothDevice.getName();                  
                  Toast.makeText(BTSearchActivity.this, "BluetoothSetting -- 发现一个蓝牙设备：" + str + "<" + localBluetoothDevice.getAddress() + ">", Toast.LENGTH_SHORT).show();
                                    
                  boolean hasBefore = false;
                  for(BluetoothDevice de : BTSearchActivity.this.foundDevices){
                      if(de.getAddress().equals(localBluetoothDevice.getAddress())){
                          hasBefore = true;
                          return;
                      }                      
                  }
                  
                  if(!hasBefore){
                      BTSearchActivity.this.foundDevices.add(localBluetoothDevice);
                      BTSearchActivity.this.mBTDevicesAdapter.notifyDataSetChanged();
                  }
                }
            }
        };
        
        this.discoveryMonitor = new BroadcastReceiver()
        {
            String dFinished = "android.bluetooth.adapter.action.DISCOVERY_FINISHED";
            String dStarted = "android.bluetooth.adapter.action.DISCOVERY_STARTED";
              
            public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
            {
                if (this.dStarted.equals(paramAnonymousIntent.getAction()))
                {
                    Toast.makeText(BTSearchActivity.this, "search started", Toast.LENGTH_SHORT).show();
                    
                    BTSearchActivity.this.foundDevices.clear();
                    BTSearchActivity.this.mBTDevicesAdapter.notifyDataSetChanged();
                    
                    isSearching = true;                    
                    mBtn_search.setText(BTSearchActivity.this.getString(R.string.cancel_search));
                    
                }else if(this.dFinished.equals(paramAnonymousIntent.getAction())){
                    Toast.makeText(BTSearchActivity.this, "search finished", Toast.LENGTH_SHORT).show();
                    
                    if(null == BTSearchActivity.this.foundDevices || BTSearchActivity.this.foundDevices.size() == 0){
                        Toast.makeText(BTSearchActivity.this, "No devices", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    
//                    StringBuilder strBuilder = new StringBuilder();
//                    for(int i= 0; i<BTSearchActivity.this.foundDevices.size(); ++i){
//                        strBuilder.append(BTSearchActivity.this.foundDevices.get(i).getName() + " , "+BTSearchActivity.this.foundDevices.get(i).getAddress());
//                        strBuilder.append("\n");
//                    }
                    
                    BTSearchActivity.this.mBTDevicesAdapter.notifyDataSetChanged();
                    
                    isSearching = false;                    
                    mBtn_search.setText(BTSearchActivity.this.getString(R.string.search));
                }
           }
         };
         
         if(isauto){
             //检查当前设备是否有蓝牙
             mBTAdapter = BluetoothAdapter.getDefaultAdapter();
             if(mBTAdapter == null){
                 Toast.makeText(BTSearchActivity.this, "no bluetooth", Toast.LENGTH_SHORT).show();
                 return;
             }
             
             //检查当前设备蓝牙是否打开
             if (!this.mBTAdapter.isEnabled()) {                 
                 showPopInitial();
                 return;
             }

             //搜索周边设备
             mBTAdapter.startDiscovery();
             
             isSearching = true;
             mBtn_search.setText(this.getString(R.string.cancel_search));
         }
    }
    
    
    private void showPopInitial()  
    { 
        mOpenBTPop = new OpenBTPop(BTSearchActivity.this,mParent,new onResultOpenBTPop(){

            @Override
            public void ok() {
                startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), USER_OPEN_BT);
            }

            @Override
            public void cancel() {                        
            }
             
         });
        Runnable showPopWindowRunnable = new Runnable() {  
              
            @Override  
            public void run() {
                // 如何根元素的width和height大于0说明activity已经初始化完毕  
                if( mParent != null && mParent.getWidth() > 0 && mParent.getHeight() > 0) {  
                    // 显示popwindow  
                    mOpenBTPop.show();
                    // 停止检测  
                    mHandler.removeCallbacks(this);  
                } else {  
                    // 如果activity没有初始化完毕则等待20毫秒再次检测  
                    mHandler.postDelayed(this, 20);  
                }  
            }  
        };  
        // 开始检测  
        mHandler.post(showPopWindowRunnable);
    }
    
    
    @Override
    protected void initWidget() {
        super.initWidget();
        
        mTxt_title.setText(this.getString(R.string.title_devicelist));
        mBtn_back.setVisibility(View.INVISIBLE);
        mBtn_menu.setVisibility(View.INVISIBLE);
        
        mListview_devicelist.setPullRefreshEnable(false);
        mListview_devicelist.setPullLoadEnable(false);
        mListview_devicelist.setOnItemClickListener(mListItem_clickListener);
    }
    
    @Override
    public void widgetClick(View v) { 
        super.widgetClick(v);        
        switch(v.getId()){
        case R.id.btn_search:
            if(isSearching)
            {
                if(null != mBTAdapter){
                    mBTAdapter.cancelDiscovery();
                }
                
                mBtn_search.setText(this.getString(R.string.search));
            }else{
                this.foundDevices.clear();
                this.mBTDevicesAdapter.notifyDataSetChanged();
                
                //检查当前设备是否有蓝牙
                mBTAdapter = BluetoothAdapter.getDefaultAdapter();
                if(mBTAdapter == null){
                    Toast.makeText(BTSearchActivity.this, "no bluetooth", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                //检查当前设备蓝牙是否打开
                if (!this.mBTAdapter.isEnabled()) {
                    OpenBTPop vPop = new OpenBTPop(BTSearchActivity.this,mParent,new onResultOpenBTPop(){

                        @Override
                        public void ok() {
                            startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), USER_OPEN_BT);
                        }

                        @Override
                        public void cancel() {                        
                        }
                         
                     });
                     
                     vPop.show();
                     return;
                }

                //搜索周边设备
                mBTAdapter.startDiscovery();                
                

                isSearching = true;
                mBtn_search.setText(this.getString(R.string.cancel_search));
            }
            break;
        case R.id.btn_cancel:
            if(null != mBTAdapter){
                mBTAdapter.cancelDiscovery();
            }
            
            BTSearchActivity.this.skipActivity(BTSearchActivity.this, MainActivity.class); 
            break;
        default:
            break;
        }
    }
    
    private ListView.OnItemClickListener mListItem_clickListener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            if(null != mBTAdapter){
                mBTAdapter.cancelDiscovery();
            }
            
            isSearching = false;                    
            mBtn_search.setText(BTSearchActivity.this.getString(R.string.search));
            
            final BluetoothDevice btDevice = foundDevices.get(position-1);
            
            //check device
            boolean isTCDevice = TCDevice.checkDevice(btDevice);
            if(!isTCDevice){
                Toast.makeText(BTSearchActivity.this, btDevice.getName()+" isn't TC device", Toast.LENGTH_SHORT).show();
                return;
            }
            
            //开始配对连接
            mBindBTPop = new BindBTPop(BTSearchActivity.this,mParent,new onResultBindBTPop(){
                @Override
                public void ok(String sn, String sensor) {
                    //TODO  sn,sensor need to do something
                    BluetoothDevice device = mBTAdapter.getRemoteDevice(btDevice.getAddress());
                    
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED)
                    {
                        try
                        {
                            Toast.makeText(BTSearchActivity.this, "NOT BOND_BONDED", Toast.LENGTH_SHORT).show();
                            BluetoothBondUtils.setPin(device.getClass(), device, PIN_BOUND); // 手机和蓝牙采集器配对
                            BluetoothBondUtils.createBond(device.getClass(), device);
                            
                            AnsyTry mAnsy = new AnsyTry(device);
                            mAnsy.execute();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(BTSearchActivity.this, "setPiN failed!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
             
                    }
                    else
                    {
                        Toast.makeText(BTSearchActivity.this, "HAS BOND_BONDED", Toast.LENGTH_SHORT).show();
                        try
                        {
                            BluetoothBondUtils.createBond(device.getClass(), device);
                            BluetoothBondUtils.setPin(device.getClass(), device, PIN_BOUND); // 手机和蓝牙采集器配对
                            BluetoothBondUtils.createBond(device.getClass(), device);
                            
                            AnsyTry mAnsy = new AnsyTry(device);
                            mAnsy.execute();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(BTSearchActivity.this, "setPiN failed!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    } 
                }

                @Override
                public void cancel() {                    
                }
                
            });
            
            mBindBTPop.show();
            
        }
        
    };
    
    
    private class AnsyTry extends AsyncTask<String, Integer, Boolean>{        
        private final static int LOADING = 1001;
        private final static int FINISH = 1003;
        private final static int FAILED = 1004;
        private final static int PROCESS = 1005;
        
        private LoadingPop te = null;
        private BluetoothDevice btDev;
        private boolean result = false;
        
        private String temp;
       
        public AnsyTry(BluetoothDevice dev) {
            super();
            this.te = new LoadingPop(BTSearchActivity.this,mParent);
            this.btDev = dev;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            
            publishProgress(LOADING);
            
            final UUID uuid = UUID.fromString(SPP_UUID);
            try {  
                mBTSocket = btDev.createRfcommSocketToServiceRecord(uuid);
                mBTSocket.connect();
                
                publishProgress(FINISH);
                result = true;
            } catch (Exception e1) { 
                e1.printStackTrace();
                
                try{
                    mBTSocket =  BluetoothBondUtils.createInsecureRfcommSocketToServiceRecord(BluetoothDevice.class, btDev, uuid);
                    mBTSocket.connect();
                    
                    publishProgress(FINISH);
                    result = true;
                    
                }catch(Exception e2){
                    e2.printStackTrace();
                    
                    try{
                        mBTSocket = BluetoothBondUtils.createRfcommSocket(BluetoothDevice.class, btDev, 1);
                        mBTSocket.connect();
                        
                        publishProgress(FINISH);
                        result = true;
                        
                    }catch(Exception e3){                        
                        e3.printStackTrace();
                        
                        publishProgress(FAILED);
                        
                    }
                }
            }
            
            if(null != mBTSocket){
                StatusManager.getInstance().setBluetoothSocket(mBTSocket);
            }
            
            
            if(result){
                //TODO 获取数据
//                ？？
                
                try {  
                    
                    InputStream mmInStream = mBTSocket.getInputStream();  
                    OutputStream mmOutStream = mBTSocket.getOutputStream();  
                    TCTransmitHelp mHelper = new TCTransmitHelp(mmInStream,mmOutStream);
                    
                    //获取TC参数
                    //send：01 01 01 01 35 c7
                    //recv: 02 03 01 14 36 00 00 01 00 12 08 01 00 14 24 02 ff ff ff ff 00 00 00 01 5d
//                    RespGetTcParams localRespGetTcParams = (RespGetTcParams)ResponseDataDecodeFactory.getRespDomain(AppUtil.getByteArrayFromByteList(mHelper.communicate(ReqCmdEncodeFactory.getReqGetTcParamsCmd(), true)));
//                    Log.d("cc","TCStatus: "+localRespGetTcParams.getTcStatusOperation());
//                    Log.d("cc","DeviceSn: "+localRespGetTcParams.getDeviceSn());
//                    Log.d("cc","PatientCount: "+localRespGetTcParams.getPatientCount());
//                    
////                    StatusManager.getInstance().setCurrTcStatus(localRespGetTcParams.getTcStatusOperation());
////                    
////                    StringBuilder mBuilder = new StringBuilder();
////                    mBuilder.append(localRespGetTcParams.getCmdRespStatus()+"-------1\n");
////                    mBuilder.append(localRespGetTcParams.getTcStatusOperation()+"-------2\n");
////                    mBuilder.append(localRespGetTcParams.getDeviceSn()+"-------3\n");
////                    mBuilder.append(localRespGetTcParams.getPatientCount()+"-------4\n");
////                    
////                    temp = mBuilder.toString();
////                    publishProgress(PROCESS);
//                    
//                    DeviceBinding mDevice = new DeviceBinding();
//                    mDevice.setBindingTime(new Date());
//                    mDevice.setDeviceSN(btDev.getName());
//                    mDevice.setMac(btDev.getAddress());
//                    StatusManager.getInstance().setCurrDeviceBind(mDevice);
                    
                    //获取患者信息
                    //send:01 01 01 05 37 00 00 00 01 C0 
                    //recv:02 0A 01 10 38 00 00 01 12 34 CE DA 00 00 3E 31 00 00 3E 0D 02 
//                    byte paramByte = (byte)0x00000001;
//                    RespGetTcPatientInfo localRespGetTcParams = (RespGetTcPatientInfo)ResponseDataDecodeFactory.getRespDomain(AppUtil.getByteArrayFromByteList(mHelper.communicate(ReqCmdEncodeFactory.getReqGetTcPatientInfoCmd(paramByte), true)));
//                    
//                    Log.d("cc","TCStatus: "+localRespGetTcParams.getMeasureDataId());
//                    Log.d("cc","TCStatus: "+localRespGetTcParams.getTransferDataId());                    
//                    
//                    StringBuilder mBuilder = new StringBuilder();
//                    mBuilder.append("localRespGetTcParams.getMeasureDataId(): "+localRespGetTcParams.getMeasureDataId()+"\n");
//                    mBuilder.append("localRespGetTcParams.getTransferDataId(): "+localRespGetTcParams.getTransferDataId()+"\n");
//                    temp = mBuilder.toString();
//                    
//                    System.out.println(temp);
//                    publishProgress(PROCESS);
                    
                    //获取数据
                    //一次发送10个数据，每隔11s一个数据
//                    I/cc      (14015): send:01 01 01 09 3F 00 00 00 01 00 00 3E 0D 69
//                    I/cc      (14015): recv:02 11 01 4E 40 00 00 00 00 3E 0D 0A 54 25 B9 FD 20 FF 0F
//                     54 25 BA 08 20 FF 0F 54 25 BA 13 20 FF 0F 54 25 BA 1E 20 FF 0F 54 25 BA 2A 20 F
//                    F 0F 54 25 BA 35 20 FF 0F 54 25 BA 40 20 FF 0F 54 25 BA 4B 20 FF 0F 54 25 BA 57
//                    20 FF 0F 54 25 BA 62 20 FF 0F 67
//                    D/cc      (14015): Data Id:15885 Data Count:10 Data Values:-1,4095,2014-09-26 19
//                    :09:49;-1,4095,2014-09-26 19:10:00;-1,4095,2014-09-26 19:10:11;-1,4095,2014-09-2
//                    6 19:10:22;-1,4095,2014-09-26 19:10:34;-1,4095,2014-09-26 19:10:45;-1,4095,2014-
//                    09-26 19:10:56;-1,4095,2014-09-26 19:11:07;-1,4095,2014-09-26 19:11:19;-1,4095,2
//                    014-09-26 19:11:30;
                    RespDataTransfer localParamsDataTransfer = (RespDataTransfer)ResponseDataDecodeFactory.getRespDomain(AppUtil.getByteArrayFromByteList(mHelper.communicate(ReqCmdEncodeFactory.getReqDataTransferCmd(1, 15895), true)));
                    Log.d("cc", localParamsDataTransfer.toString());
  
                    //获取历史数据
//                    I/cc      (14015): send:01 01 01 06 53 01 00 00 3E 0D 58
//                    I/cc      (14015): recv:02 12 01 4E 40 00 00 00 00 3E 0D 0A 54 2B CB 14 20 FF 0F
//                     54 2B CB 1F 20 FF 0F 54 2B CB 2A 20 FF 0F 54 2B CB 36 20 FF 0F 54 2B CB 41 20 F
//                    F 0F 54 2B CB 4C 20 FF 0F 54 2B CB 57 20 FF 0F 54 2B CB 63 20 FF 0F 54 2B CB 6E
//                    20 FF 0F 54 2B CB 79 20 FF 0F 97
//                    D/cc      (14015): Data Id:15885 Data Count:10 Data Values:-1,4095,2014-10-01 09
//                    :36:20;-1,4095,2014-10-01 09:36:31;-1,4095,2014-10-01 09:36:42;-1,4095,2014-10-0
//                    1 09:36:54;-1,4095,2014-10-01 09:37:05;-1,4095,2014-10-01 09:37:16;-1,4095,2014-
//                    10-01 09:37:27;-1,4095,2014-10-01 09:37:39;-1,4095,2014-10-01 09:37:50;-1,4095,2
//                    014-10-01 09:38:01;                   
                    localParamsDataTransfer = (RespDataTransfer)ResponseDataDecodeFactory.getRespDomain(AppUtil.getByteArrayFromByteList(mHelper.communicate(ReqCmdEncodeFactory.getReqHistoryDataTransferCmd((byte)0x02, 15885), true)));
                    Log.d("cc", localParamsDataTransfer.toString());
                    
                    localParamsDataTransfer = (RespDataTransfer)ResponseDataDecodeFactory.getRespDomain(AppUtil.getByteArrayFromByteList(mHelper.communicate(ReqCmdEncodeFactory.getReqHistoryDataTransferCmd((byte)0x02, 0), true)));
                    Log.d("cc", localParamsDataTransfer.toString());
                    
                    
                } catch (IOException e) { }
            }
            
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            te.hide(2000);
            if(result){                
                BTSearchActivity.this.skipActivity(BTSearchActivity.this, CTPolarizingActivity.class);                
            }else{
                super.onPostExecute(result); 
            }            
            
        }

        @Override
        protected void onPreExecute() {
            te.show();
            
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch(values[0]){
            case FINISH:
                te.updateText(BTSearchActivity.this.getResources().getString(R.string.success_bind));
                break;
            case FAILED:
                te.updateText(BTSearchActivity.this.getResources().getString(R.string.fail_bind));
                break;
            case LOADING:
                te.updateText(BTSearchActivity.this.getResources().getString(R.string.binding));
                break;
            case PROCESS:
                te.updateText(temp);
                break;
            }
            
        }
       
    }    
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
        case USER_OPEN_BT:
            if(resultCode == RESULT_OK){                
                //搜索周边设备
                mBTAdapter.startDiscovery();
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(BTSearchActivity.this, "user give up opening bluetooth", Toast.LENGTH_SHORT).show();
            }            

            break;
        default:
            break;
        }
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        
        if(null != mBTAdapter){
            mBTAdapter.cancelDiscovery();
        }
        
        
    }
    
    @Override
    public void registerBroadcast() {
        super.registerBroadcast();
        
        registerReceiver(this.discoveryResult, new IntentFilter("android.bluetooth.device.action.FOUND"));
        registerReceiver(this.discoveryMonitor, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_STARTED"));
        registerReceiver(this.discoveryMonitor, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
    }

    @Override
    public void unRegisterBroadcast() {
        super.unRegisterBroadcast();
        
        unregisterReceiver(this.discoveryResult);
        unregisterReceiver(this.discoveryMonitor);
    }
    

}
