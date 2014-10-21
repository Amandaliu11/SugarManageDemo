package com.example.sugarmanagedemo.activity;

import java.util.Date;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.activity.BaseActivity;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.domain.DeviceBinding;
import com.example.sugarmanagedemo.domain.StatusManager;
import com.example.sugarmanagedemo.util.AppUtil;

public class CTPolarizingActivity extends BaseActivity {
    private final static long TOTAL_TIME = 11820;   //03:17:00
    private final static int INTERNAL_UPDATE = 1000;
    
    
    @BindView(id = R.id.txt_timer)
    protected TextView mTxt_countdown;
    
    @BindView(id = R.id.txt_tips)
    protected TextView mTxt_tips;
    
    private long mTimeLeft = TOTAL_TIME;
    

    protected View mParent;
    private final Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable(){ 
        @Override
        public void run() {
            mTxt_countdown.setText(AppUtil.sec2Str(mTimeLeft));
            mTimeLeft--;
            
            mHandler.postDelayed(mRunnable, INTERNAL_UPDATE);
        }       
    }; 
    
    public CTPolarizingActivity(){
        this.setAllowFullScreen(true);
        this.setHiddenActionBar(true);
    }

    @Override
    public void setRootView() {
        mParent = getLayoutInflater().inflate(R.layout.activity_polarizing, null);
        setContentView(mParent);
    }
    
    @Override
    public void initData(){
        DeviceBinding vDevice = StatusManager.getInstance().getCurrDeviceBind();
        if(null != vDevice){
            Date vBindTime = vDevice.getBindingTime();
            
            long vStart = vBindTime.getTime();
            long vEnd = (new Date()).getTime();
            
            mTimeLeft = (vEnd - vStart)/1000;
        }
        
        
        
        mHandler.post(mRunnable);
    }
    
    @Override
    protected void initWidget(){
    }
    
    @Override
    public void widgetClick(View v){
        
    }

}
