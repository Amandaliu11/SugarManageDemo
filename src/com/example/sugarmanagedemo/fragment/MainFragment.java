package com.example.sugarmanagedemo.fragment;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYSeriesMark;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.activity.MainActivity;
import com.example.sugarmanagedemo.audio.AudioRecordingHandler;
import com.example.sugarmanagedemo.audio.AudioRecordingSaveMp3Thread;
import com.example.sugarmanagedemo.chart.RealTimeSugarChart;
import com.example.sugarmanagedemo.chart.RealTimeXYPoint;
import com.example.sugarmanagedemo.pop.AudioRecordingPop;
import com.example.sugarmanagedemo.utils.StorageUtils;
import com.example.sugarmanagedemo.view.LongTouchButton;

public class MainFragment extends BaseFragment {
    private final static int REFRESH_TIME = 60;//3min
    
    @BindView(id = R.id.txt_bloodsuger)
    protected TextView mTxtBloodsugar;
    
    @BindView(id = R.id.txt_time)
    protected TextView mTxtTime;
    
    @BindView(id = R.id.txt_bloodsuger_pre)
    protected TextView mTxtBloodsugerPre;
    
    @BindView(id = R.id.btn_sport)
    protected LongTouchButton mBtnSport;
    
    @BindView(id = R.id.btn_diet)
    protected LongTouchButton mBtnDiet;
    
    @BindView(id = R.id.btn_medicine)
    protected LongTouchButton mBtnMedicine;
    
    @BindView(id = R.id.btn_mood)
    protected LongTouchButton mBtnMood;
    
    @BindView(id = R.id.btn_more)
    protected LongTouchButton mBtnMore;
    
    @BindView(id = R.id.layout_chart)
    protected FrameLayout mLayoutChart;
    
    private View mParent;
    
    private List<RealTimeXYPoint> mSampleData;
    private GraphicalView graphicalView;    
    private RealTimeSugarChart mChartInstance;
    private XYMultipleSeriesRenderer mSeriesRenderer;
    private XYMultipleSeriesDataset mSeriesDataset;
    
    private ChartUpdatehandler mChartUpdateHandler; 
    
    private AudioRecordingPop mPop;    
    private AudioRecordingSaveMp3Thread mThread = null;
    private static String fileMp3Name = null;
    private boolean isRecording = false;
    
    private static long currentX;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
            Bundle bundle) {
        Log.d("cc","inflaterView");
        mParent = inflater.inflate(R.layout.fragment_main, null);
        return mParent;
    }
    
    @Override
    public void initData(){
        super.initData();
        
        mSampleData = tempData();
        mPop = new AudioRecordingPop(getActivity(), mParent);
        fileMp3Name = StorageUtils.getMP3FileName(getActivity());
    }
    
    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        
        mBtnSport.setOnCumTouchListener(mCumTouchListener,true);
        mBtnDiet.setOnCumTouchListener(mCumTouchListener,true);
        mBtnMedicine.setOnCumTouchListener(mCumTouchListener,true);
        mBtnMood.setOnCumTouchListener(mCumTouchListener,true);
        mBtnMore.setOnCumTouchListener(mCumTouchListener,true);
        
        
        mTxtBloodsugar.setText("5.2");
        mTxtTime.setText(long2Str(currentX));
        
    }
    
    
    private LongTouchButton.CumTouchListener mCumTouchListener = new LongTouchButton.CumTouchListener(){

        @Override
        public void onLongTouchUp(View v) {
            recordStop();
        }

        @Override
        public void onLongTouchDown(View v) {
            recordStart();
        }

        @Override
        public void onShortTouch(View v) {
            String ann = "";
            int type = 0;
            switch(v.getId()){
                case R.id.btn_sport:
                    type = XYSeriesMark.MARK_SPORT;
                    ann = "运动了";
                    break;
                case R.id.btn_diet:
                    type = XYSeriesMark.MARK_DIET;
                    ann = "吃饭了";
                    break;
                case R.id.btn_medicine:
                    type = XYSeriesMark.MARK_MEDICINE;
                    ann = "吃药了";
                    break;
                case R.id.btn_mood:
                    type = XYSeriesMark.MARK_MOOD;
                    ann = "心情不错";
                    break;
            }
            
            if(null != mSeriesDataset && mSeriesDataset.getSeriesCount() != 0 && !ann.isEmpty()){
                XYSeries mSeries = mSeriesDataset.getSeriesAt(0);
                double xAnn = mSeries.getX(mSeries.getItemCount()-1);
                double yAnn = mSeries.getY(mSeries.getItemCount()-1);
                XYSeriesMark mMark = new XYSeriesMark(type,ann,xAnn,yAnn);
                mSeries.addMark(mMark);
                
                graphicalView.repaint();
                
            }
        }
        
    };
    
    
    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
    }
    
    
    @Override
    public void onResume(){
        super.onResume();
        
        if(getActivity() instanceof MainActivity){
                mChartInstance = new RealTimeSugarChart(mSampleData);
                graphicalView = mChartInstance.execute((MainActivity)(this.getActivity()));
                graphicalView.setBackgroundColor(Color.TRANSPARENT);
                            
                mLayoutChart.addView(graphicalView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
                mSeriesRenderer = mChartInstance.getRenderer();
                mSeriesDataset = mChartInstance.getDataset();
       }
        
      
        if(null == mChartUpdateHandler){
            mChartUpdateHandler = new ChartUpdatehandler((MainActivity)(this.getActivity()),mChartInstance,mTxtBloodsugar,mTxtTime);
            mChartUpdateHandler.sendEmptyMessageDelayed(0, REFRESH_TIME*1000);            
        }
    }
    
    public static String long2Str(long sed){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm",Locale.US);
        Date date = new Date(sed*1000);
        return simpleDateFormat.format(date);
        
    }
    
        
    private static class ChartUpdatehandler extends Handler {
        WeakReference<MainActivity> mActivity;
        RealTimeSugarChart mChart;
        TextView mRealTimeSugar;
        TextView mDate;
    
        ChartUpdatehandler(MainActivity activity,RealTimeSugarChart chart,TextView realValue,TextView date) {
            mActivity = new WeakReference<MainActivity>(activity);
            mChart = chart;
            mRealTimeSugar = realValue;
            mDate = date;
        }
        
        private double randomY(long seed){
            Random random = new Random(seed);
            double result = random.nextInt(27)+random.nextDouble();
            DecimalFormat df = new DecimalFormat("#.##");
            result = Double.parseDouble(df.format(result));  
            
            return result;
        }
    
        @Override
        public void handleMessage(Message msg) {
            this.removeMessages(0);
            
            
            MainActivity theActivity = mActivity.get(); 
            
            currentX +=REFRESH_TIME;
            double currentY = randomY(currentX);
            
            RealTimeXYPoint point = new RealTimeXYPoint(currentX,currentY);                
            mChart.update(theActivity,point);
            
            mRealTimeSugar.setText(""+currentY);
            mDate.setText(long2Str(currentX));
            
            this.sendMessageDelayed(this.obtainMessage(), REFRESH_TIME*1000);
        }
    };
    
    private List<RealTimeXYPoint> tempData(){
        List<RealTimeXYPoint> data = new ArrayList<RealTimeXYPoint>();
        
        currentX = new Date().getTime()/1000-REFRESH_TIME*5;
        RealTimeXYPoint point = new RealTimeXYPoint(currentX,5.2);
        data.add(point);
        
        currentX +=REFRESH_TIME;
        point = new RealTimeXYPoint(currentX,7.2);
        data.add(point);
        
        currentX +=REFRESH_TIME;
        point = new RealTimeXYPoint(currentX,6.7);
        data.add(point);
        
        currentX +=REFRESH_TIME;
        point = new RealTimeXYPoint(currentX,11.0);
        data.add(point);
        
        currentX +=REFRESH_TIME;
        point = new RealTimeXYPoint(currentX,6.7);
        data.add(point);
        
        currentX +=REFRESH_TIME;
        point = new RealTimeXYPoint(currentX,5.7);
        data.add(point);
        
        return data;
    }
        
    
    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        Log.d("cc","onDestroyView");
        super.onDestroyView();
        
        mLayoutChart.removeAllViews();
        
        if(mChartUpdateHandler != null){
            mChartUpdateHandler.removeMessages(0);
            mChartUpdateHandler = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.d("cc","onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Log.d("cc","onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        Log.d("cc","onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.d("cc","onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("cc","onStart");
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d("cc","onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        Log.d("cc","onDetach");
        super.onDetach();
    }
    
    
    private void recordStart(){
        if(!isRecording){
            startRecording();
            isRecording = true;
            
            mPop.show("开始录音");
        } 
    }
    
    private void recordStop(){
        if(isRecording){
            stopRecording();
            isRecording = false;
            
            mPop.hide();
        }       
    }
    
    private void startRecording(){
        mThread = new AudioRecordingSaveMp3Thread(fileMp3Name, new AudioRecordingHandler() {
            @Override
            public void onFftDataCapture(final byte[] bytes) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {                        
                        for(int i = 0; i<bytes.length/2; i=i+2){
                            byte rfk = bytes[i*2];
                            byte ifk = bytes[i*2+1];
                            long magnitude = rfk*rfk+ifk*ifk;
                            int dbValue = 0;
                            if(magnitude !=0L){
                                dbValue = (int)(10*Math.log10(magnitude));
                            }                            
                            mPop.updateProgress(dbValue);
                        }
                    }
                });
            }

            @Override
            public void onRecordSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {                        
                        mPop.updateText("录音成功");
                    }
                });
                
            }

            @Override
            public void onRecordingError() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        recordStop();
                        mPop.updateText("录音失败");
                    }
                });
            }

            @Override
            public void onRecordSaveError() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        recordStop();
                        mPop.updateText("录音文件保存失败");
                    }
                });
            }
        });
        mThread.start();
    }
    
    private void stopRecording(){
        if(mThread != null){
            mThread.stopRecording();
            mThread = null;
        }
    }

}
