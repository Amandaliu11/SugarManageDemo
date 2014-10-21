/**
 * LongTouchBtn.java [v1.0.0]
 * classes: com.example.audiorecordingtest.view.LongTouchBtn
 * Amanda Create at 2014骞�10鏈�11鏃� 涓嬪崍3:54:06
 * Copyright 闃冲厜鍋ュ悍淇℃伅鎶�鏈湁闄愬叕鍙�
 */
package com.example.sugarmanagedemo.view;

import java.util.Date;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * com.example.audiorecordingtest.view.LongTouchBtn
 * @author Amanda
 * create at 2014骞�10鏈�11鏃� 涓嬪崍3:54:06
 */
public class LongTouchButton extends ImageButton {
    private static final int TIME_CHECK = 100;  //鎸夐挳闀挎寜鏃� 闂撮殧澶氬皯姣鏉ュ鐞� 鍥炶皟鏂规硶     
    private static final int TIME_LONGTOUCH = 1000;  //鎸変笅澶氬皯ms鍚庡垽鏂负闀挎寜
    private static final int TIME_INTERRUPT_TO_START = 10000;   //鏈�澶氶暱鎸変笅1鍒嗛挓
    
    /**
     * 璁板綍褰撳墠鑷畾涔塀tn鏄惁鎸変笅
     */
    private boolean isStillClick = false;
     
    /**
     * 涓嬫媺鍒锋柊鐨勫洖璋冩帴鍙�
     */
    private CumTouchListener mListener;
     
    /**
     * 闀挎寜寮�濮嬪悗锛宮Interrupt2Start ms鏃堕棿鍚庤Е鍙憃nLongTouchDown浜嬩欢
     * 濡傛灉涓�0锛屽垯璇存槑涓嶄腑閫旀墦鏂敤鎴烽暱鎸変簨浠�
     */
    private boolean mIsInterrupt = false;

     
    /**
     * 鏋勯�犲嚱鏁�
     * @param context
     * @param attrs
     */
    public LongTouchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
 
    /**
     * 澶勭悊touch浜嬩欢
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            long startTime = (new Date()).getTime();
            isStillClick = true;
            new LongTouchTask().execute(startTime);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            isStillClick = false;
        }
        return super.onTouchEvent(event);
    }
 
    /**
     * 浣垮綋鍓嶇嚎绋嬬潯鐪犳寚瀹氱殑姣鏁般��
     * 
     * @param time
     *            鎸囧畾褰撳墠绾跨▼鐫＄湢澶氫箙锛屼互姣涓哄崟浣�
     */
    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
     
    /**
     * 澶勭悊闀挎寜鐨勪换鍔�
     */
    class  LongTouchTask extends AsyncTask<Long,Integer,Boolean>{
        private long start;
        private boolean isLongTouch = false;
        private boolean needCheckLongTouch = true;
        private long startLongTouch;
        
        @Override
        protected Boolean doInBackground(Long... params) {
            start = params[0];
            
            while(isStillClick)
            {
                sleep(TIME_CHECK);
                
                if(needCheckLongTouch && new Date().getTime() - start >= TIME_LONGTOUCH){
                    publishProgress(0);
                    
                    startLongTouch = new Date().getTime();
                    isLongTouch = true;
                    needCheckLongTouch = false;
                }
                
                if(isLongTouch && mIsInterrupt && new Date().getTime() - startLongTouch >= TIME_INTERRUPT_TO_START){                    
                    return isLongTouch;
                }
               
            }           
            
            
            return isLongTouch;
        }
 
        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                mListener.onLongTouchUp(LongTouchButton.this);
            }else{
                mListener.onShortTouch(LongTouchButton.this);
            }
        }
 
        @Override
        protected void onProgressUpdate(Integer... values) {
            if(values[0]==0){
                mListener.onLongTouchDown(LongTouchButton.this);
            }            
        }
         
    }
     
    /**
     * 缁欓暱鎸塨tn鎺т欢娉ㄥ唽涓�涓洃鍚櫒銆�
     * 
     * @param listener 鐩戝惉鍣ㄧ殑瀹炵幇
     */
    public void setOnCumTouchListener(CumTouchListener listener) {
        setOnCumTouchListener(listener,false);
    }
    
    /**
     * 缁欓暱鎸塨tn鎺т欢娉ㄥ唽涓�涓洃鍚櫒銆�
     * 
     * @param listener 鐩戝惉鍣ㄧ殑瀹炵幇
     * @param interrupt 涓�旀槸鍚︽墦鏂暱鎸変簨浠�;濡傛灉涓虹┖锛屽垯鏈�澶氶暱鎸変笅1鍒嗛挓锛�1鍒嗛挓鍚庤嚜鍔ㄨ皟鐢╫nLongTouchDown
     */
    public void setOnCumTouchListener(CumTouchListener listener,boolean interrupt) {
        mListener = listener;         
        mIsInterrupt = interrupt;
    }
     
    /**
     * 闀挎寜鐩戝惉鎺ュ彛锛屼娇鐢ㄦ寜閽暱鎸夌殑鍦版柟搴旇娉ㄥ唽姝ょ洃鍚櫒鏉ヨ幏鍙栧洖璋冦��
     */
    public interface CumTouchListener {
 
        /**
         * 澶勭悊闀挎寜鐨勫洖璋冩柟娉�
         */
        void onLongTouchUp(View v);   //闀挎寜鏉惧紑鎵嬫椂瑙﹀彂鐨勪簨浠�
        void onLongTouchDown(View v); //闀挎寜鎸変笅鏃惰Е鍙戠殑浜嬩欢
        
        /**
         * 澶勭悊鐭寜鐨勫洖璋冩柟娉�
         */
        void onShortTouch(View v);
    }

    public static CumTouchListener CumTouchListener() {
        // TODO Auto-generated method stub
        return null;
    }

}
