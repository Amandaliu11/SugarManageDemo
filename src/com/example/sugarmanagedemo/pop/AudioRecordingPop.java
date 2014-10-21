/**
 * AudioRecordingPop.java [v1.0.0]
 * classes: com.example.audiorecordingtest.AudioRecordingPop
 * Amanda Create at 2014骞�10鏈�10鏃� 涓婂崍11:30:04
 * Copyright 闃冲厜鍋ュ悍淇℃伅鎶�鏈湁闄愬叕鍙�
 */
package com.example.sugarmanagedemo.pop;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.view.RoundProgressBar;

/**
 * com.example.audiorecordingtest.AudioRecordingPop
 * @author Amanda
 * create at 2014骞�10鏈�10鏃� 涓婂崍11:30:04
 */
public class AudioRecordingPop {
    private Context mContext;
    private View mParent;
    
    private PopupWindow mPop = null;
    private View mView = null;
    
//    private ImageView mImg;
    private TextView mTxt;
    private RoundProgressBar mProgressBar;
    
    private int mWidth;
    
    public AudioRecordingPop(Context vContext ,View vParent){
        mContext = vContext;
        mParent = vParent;
        
//        mHeight = vContext.getResources().getDimensionPixelSize(R.dimen.dialog_bindbt_height);   
        
        Display display = ((WindowManager)vContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        
        mWidth = (int) ((int)metrics.widthPixels/2);
        
        
    }
    
    public void show(){
        if(null == mPop){
            mPop = create();
        }
        
        if(!mPop.isShowing()){
            mPop.showAtLocation(mParent, Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 0);
            mPop.update(0, 80, mWidth, mWidth);
        }
        
    }
    
    
    public void show(String txt){
        this.show();
        this.updateText(txt);
    }
    
    public void hide(){
        if(null != mPop && mPop.isShowing()){
            mPop.dismiss();
        }
    }
    
    public boolean isShowing(){
        if(null == mPop || !mPop.isShowing()){
            return false;
        }else{
            return true;
        }
    }
    
    
    public void updateText(String txt){
        if(null != mPop && mPop.isShowing()){
            mTxt.setText(txt);
        }
    }
    
    
    public void updateProgress(int progress){
        if(null != mPop && mPop.isShowing()){
            mProgressBar.setProgress(progress);
        }
    }
    
    private PopupWindow create(){        
        if(mPop != null){
            return mPop;
        }
        
        mView = LayoutInflater.from(mContext).inflate(R.layout.audiorecord, null);
        mPop = new PopupWindow(mView);
        mPop.setFocusable(false);
        mPop.setTouchable(false);
        
//        mImg = (ImageView)mView.findViewById(R.id.img_microfan);
        mTxt = (TextView)mView.findViewById(R.id.txt_microfan);
        mProgressBar = (RoundProgressBar)mView.findViewById(R.id.roundProgressBar);
        
        return mPop;
    }
    

    
}
