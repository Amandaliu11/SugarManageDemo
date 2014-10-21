package com.example.sugarmanagedemo.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;

public class OpenBTPop {
    private Context mContext;
    private View mParent;
    private onResultOpenBTPop mIResult;
    
    private PopupWindow mPop = null;
    private View mView = null;
    
    private TextView mTxtTitle;
    private TextView mTxtTip;
    private Button mBtnOK;
    private Button mBtnCancel;
    
    private int mWidth;
    private int mHeight;
    
    public OpenBTPop(Context vContext ,View vParent,onResultOpenBTPop vIResult){
        mContext = vContext;
        mParent = vParent;
        mIResult = vIResult;
        
        mWidth = vContext.getResources().getDimensionPixelSize(R.dimen.dialog_openbt_width);
        mHeight = vContext.getResources().getDimensionPixelSize(R.dimen.dialog_openbt_height);
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
    
    private PopupWindow create(){
        if(mPop != null){
            return mPop;
        }
        
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_bt_bg, null);
        mPop = new PopupWindow(mView);
        mPop.setFocusable(false);
        mPop.setTouchable(true);
        
        mTxtTitle = (TextView)mView.findViewById(R.id.title);
        mTxtTip = (TextView)mView.findViewById(R.id.tip);
        mBtnOK = (Button)mView.findViewById(R.id.btn_left);
        mBtnCancel = (Button)mView.findViewById(R.id.btn_right);
        
        mTxtTitle.setText(mContext.getResources().getString(R.string.open_bt));
        mTxtTip.setText(mContext.getResources().getString(R.string.tip_open_bt));
        
        mBtnOK.setOnTouchListener(mTouchListener);
        mBtnCancel.setOnTouchListener(mTouchListener);
        return mPop;
    }
    
    private Button.OnTouchListener mTouchListener = new Button.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                switch(v.getId()){
                case R.id.btn_left:
                    mIResult.ok();
                    hide();                   
                    return true;
                case R.id.btn_right:
                    mIResult.cancel();
                    hide();                    
                    return true;
                default:
                    break;
                }
            }
            
            return false;
        }
        
    };
    
    public interface onResultOpenBTPop{
        public void ok();
        public void cancel();
    }
}
