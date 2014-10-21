package com.example.sugarmanagedemo.pop;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;

public class BindBTPop {

    private Context mContext;
    private View mParent;
    private onResultBindBTPop mIResult;
    
    private PopupWindow mPop = null;
    private View mView = null;
    
    private TextView mTxtTitle;
    private EditText mSN;
    private EditText mSensor;
    private Button mBtnOK;
    private Button mBtnCancel;
    
    private int mWidth;
    private int mHeight;
    
    public BindBTPop(Context vContext ,View vParent,onResultBindBTPop vIResult){
        mContext = vContext;
        mParent = vParent;
        mIResult = vIResult;
        
        mWidth = vContext.getResources().getDimensionPixelSize(R.dimen.dialog_openbt_width);
        mHeight = vContext.getResources().getDimensionPixelSize(R.dimen.dialog_bindbt_height);
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
        
        mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_btbind_bg, null);
        mPop = new PopupWindow(mView);
        mPop.setFocusable(false);
        mPop.setTouchable(true);
        
        mTxtTitle = (TextView)mView.findViewById(R.id.title);
        mSN = (EditText)mView.findViewById(R.id.sn);
        mSensor = (EditText)mView.findViewById(R.id.sensor);
        mBtnOK = (Button)mView.findViewById(R.id.btn_left);
        mBtnCancel = (Button)mView.findViewById(R.id.btn_right);
        
        mTxtTitle.setText(mContext.getResources().getString(R.string.bind_device));
        
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
                    if(checkInput()){
                        mIResult.ok(deleteSpace(mSN.getText().toString()),deleteSpace(mSensor.getText().toString()));
                        hide();
                    }                                       
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
    
    private boolean checkInput(){
//        if(mSN.getText().toString().isEmpty()){
//            Toast.makeText(mContext, "no sn", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        
//        if(mSensor.getText().toString().isEmpty()){
//            Toast.makeText(mContext, "no sensor id", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        
        return true;
    }
    
    private String deleteSpace(String str){
        String result = str;
        if(str.isEmpty()){
            return "";
        }else{
            result = str.replaceAll(" ", "");
            return result;
        }
    }
    
    public interface onResultBindBTPop{
        public void ok(String sn, String sensor);
        public void cancel();
    }

}
