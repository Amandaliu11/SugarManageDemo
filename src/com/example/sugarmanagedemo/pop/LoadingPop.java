package com.example.sugarmanagedemo.pop;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;

public class LoadingPop {    
    private Context mContext;
    private View mParent;
    
    private PopupWindow mPop = null;
    private View mView = null;
    
    private TextView mTxtTip;
    private ProgressBar mPb;
    
    private int mWidth;
    private int mHeight;
    
    private Handler mHandler;
    
    public LoadingPop(Context vContext ,View vParent){
        mContext = vContext;
        mParent = vParent;
        
//        mHeight = vContext.getResources().getDimensionPixelSize(R.dimen.dialog_bindbt_height);   
        
        Display display = ((WindowManager)vContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;
        
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                hide();
            }
        };
    }
    
    public void show(){
        if(null == mPop){
            mPop = create();
        }
        
        if(!mPop.isShowing()){
            mPop.showAtLocation(mParent, Gravity.CENTER, 0, 0);
            mPop.update(0, 0, mWidth, mHeight);
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
    
    public void hide(int delaymills){
        mPb.setVisibility(View.GONE);
        mHandler.sendEmptyMessageDelayed(0, delaymills);
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
            mTxtTip.setText(txt);
        }
    }
    
    private PopupWindow create(){        
        if(mPop != null){
            return mPop;
        }
        
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        mPop = new PopupWindow(mView);
        mPop.setFocusable(false);
        mPop.setTouchable(true);
        
        mTxtTip = (TextView)mView.findViewById(R.id.loading);
        mPb = (ProgressBar)mView.findViewById(R.id.pb);
        
        return mPop;
    }
}

