package com.example.sugarmanagedemo.activity;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.activity.KJFragmentActivity;
import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.constant.Constants;
import com.example.sugarmanagedemo.fragment.MedicineFragment;
import com.example.sugarmanagedemo.fragment.RecordDietFragment;
import com.example.sugarmanagedemo.fragment.RecordDietTimeListFragment;
import com.example.sugarmanagedemo.fragment.RecordMoodFragment;
import com.example.sugarmanagedemo.fragment.RecordSportFragment;
import com.example.sugarmanagedemo.fragment.RecordSugarFragment;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenu;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenu.SateliteClickedListener;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenuItem;

public class RecordDetailActivity extends KJFragmentActivity  implements OnTouchListener,OnGestureListener{
    private final static String[] FRAGMENTS_LIST = new String[]{Constants.FRAGMENT_TAG_RECORD_DIET_TIME,
        Constants.FRAGMENT_TAG_RECORD_SUGAR,
        Constants.FRAGMENT_TAG_RECORD_DIET,
        Constants.FRAGMENT_TAG_RECORD_MEDICINE,
        Constants.FRAGMENT_TAG_RECORD_SPORT,
        Constants.FRAGMENT_TAG_RECORD_MOOD};
    
    
    @BindView(id = R.id.btn_back, click = true)
    protected ImageButton mBtn_back;
    
    @BindView(id = R.id.btn_menu)
    protected ImageButton mBtn_menu;
    
    @BindView(id = R.id.txt_title)
    protected TextView mTxt_title;
    
    @BindView(id = R.id.patch_menu)
    protected SatelliteMenu mPathMenu;    
    
    @BindView(id = R.id.indictor_group)
    public LinearLayout mIndictorGroup;
    
    private View mParent;
    
    private BaseFragment mRecordDietTimeListFragment;
    private BaseFragment mRecordSugarFragment;
    private BaseFragment mRecordDietFragment;    
    private BaseFragment mRecordMedicineFragment;
    private BaseFragment mRecordSportFragment;
    private BaseFragment mRecordMoodFragment;
    
    private String mCurrentFragmentTag = Constants.FRAGMENT_TAG_RECORD_SUGAR;
    private int mCurrentIndex = 1;
    
    private GestureDetector mGestureDetector = null;  
    
    // 最小的水平有效划动距离和速度，超过该距离和速度才触发划动事件  
    private int slideMinDistance = 200;    
    private int slideMinVelocity = 0;
    
    private ImageView[] tips;
    
    public RecordDetailActivity(){
        this.setHiddenActionBar(true);
    }
    
    @Override
    public void setRootView() {
        mParent = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(mParent);
    }
    
    @Override
    public void initData(){
        super.initData();
        
        Intent mIntent = this.getIntent();
        if(mIntent != null){
            mCurrentFragmentTag = mIntent.getStringExtra("tag");
            
            Log.d("dd",mCurrentFragmentTag);
        }else{
            Log.d("dd","mCurrentFragmentTag is null");
        }
        
        mGestureDetector = new GestureDetector((OnGestureListener)RecordDetailActivity.this);
        
    }
    
    @Override
    protected void initWidget() {
        super.initWidget();
        
        mBtn_menu.setVisibility(View.INVISIBLE);
        initPathMenu();
        initWidgetIndictors();
        
        mRecordDietTimeListFragment = new RecordDietTimeListFragment();
        mRecordSugarFragment = new RecordSugarFragment();        
        mRecordDietFragment = new RecordDietFragment();    
        mRecordMedicineFragment = new MedicineFragment();
        mRecordSportFragment = new RecordSportFragment();
        mRecordMoodFragment = new RecordMoodFragment();
        
        changeFragment(mCurrentFragmentTag);
    }
    
    
    
    
    @Override
    public void widgetClick(View v) { 
        super.widgetClick(v);
        if(v == mBtn_back){
            RecordDetailActivity.this.skipActivity(RecordDetailActivity.this, RecordActivity.class);
        }
    } 
    
    public void changeFragment(String tag){
        if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_DIET_TIME)){
            mCurrentIndex = 0;
            changeFragment(mRecordDietTimeListFragment,Constants.FRAGMENT_TAG_RECORD_DIET_TIME);
            
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_SUGAR)){
            mCurrentIndex = 1;
            changeFragment(mRecordSugarFragment,Constants.FRAGMENT_TAG_RECORD_SUGAR);
            
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_DIET)){
            mCurrentIndex = 2;
            changeFragment(mRecordDietFragment,Constants.FRAGMENT_TAG_RECORD_DIET);
            
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_MEDICINE)){
            mCurrentIndex = 3;
            changeFragment(mRecordMedicineFragment,Constants.FRAGMENT_TAG_RECORD_MEDICINE);
            
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_SPORT)){
            mCurrentIndex = 4;
            changeFragment(mRecordSportFragment,Constants.FRAGMENT_TAG_RECORD_SPORT);
            
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_MOOD)){
            mCurrentIndex = 5;
            changeFragment(mRecordMoodFragment,Constants.FRAGMENT_TAG_RECORD_MOOD);  
            
        }else{
            mCurrentIndex = 1;
            changeFragment(mRecordSugarFragment,Constants.FRAGMENT_TAG_RECORD_SUGAR);            
        }
    }

    public void changeFragment(BaseFragment targetFragment,String tag) {
        mCurrentFragmentTag = tag;
        mTxt_title.setText(getTitleFromTag(tag));
        
        Log.d("dd","tag: "+tag);
        
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main, targetFragment, tag)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        
        setImageBackground(mCurrentIndex);
    }
    
    public void changeFragment(BaseFragment targetFragment,int tagId) {
        String str = this.getString(tagId);
        changeFragment(targetFragment,str);
    }
    
    @Override
    public void changeFragment(BaseFragment targetFragment) {
        changeFragment(targetFragment,"fragment");        
    }
    
    
    private void initPathMenu(){
        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(4, R.drawable.ic_4));
        items.add(new SatelliteMenuItem(3, R.drawable.ic_3));
        items.add(new SatelliteMenuItem(2, R.drawable.ic_2));
        items.add(new SatelliteMenuItem(1, R.drawable.ic_1));
        
        mPathMenu.addItems(items);        
        
        mPathMenu.setOnItemClickedListener(new SateliteClickedListener() {            
            public void eventOccured(int id) {
                Log.i("sat", "Clicked on " + id);
                
                switch(id){
                case 1:
                    RecordDetailActivity.this.skipActivity(RecordDetailActivity.this, MainActivity.class);
                    break;
                case 2:
                    RecordDetailActivity.this.skipActivity(RecordDetailActivity.this, RecordActivity.class);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                }
            }
        });
    }
    
    private void initWidgetIndictors(){
        //将点点加入到ViewGroup中  
        tips = new ImageView[FRAGMENTS_LIST.length];  
        for(int i=0; i<FRAGMENTS_LIST.length; i++){  
            ImageView imageView = new ImageView(this);  
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(10,10));  
            tips[i] = imageView;  
            if(i == 0){  
                tips[i].setBackgroundResource(R.drawable.index_f);  
            }else{  
                tips[i].setBackgroundResource(R.drawable.index_n);  
            }  
              
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,    
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT));  
            layoutParams.leftMargin = 20;  
            layoutParams.rightMargin = 20;  
            mIndictorGroup.addView(imageView, layoutParams); 
        }
    }
        
    /** 
     * 设置选中的tip的背景 
     * @param selectItems 
     */  
    private void setImageBackground(int selectItems){  
        for(int i=0; i<FRAGMENTS_LIST.length; i++){  
            if(i == selectItems){  
                tips[i].setBackgroundResource(R.drawable.index_f);  
            }else{  
                tips[i].setBackgroundResource(R.drawable.index_n);  
            }  
        }  
    }


    private String getTitleFromTag(String tag){
        if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_DIET_TIME)){
            return "就餐时间列表";
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_SUGAR)){
            return "血糖记录";
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_DIET)){
            return "饮食记录";
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_MEDICINE)){
            return "用药记录";
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_SPORT)){
            return "运动记录";
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD_MOOD)){
            return "心情记录";
        }else{
            return getString(R.string.app_name);
        }
    }
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.setBackListener(false);        
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            RecordDetailActivity.this.skipActivity(RecordDetailActivity.this, RecordActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
            float distanceY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
        if (((e1.getX() - e2.getX()) > slideMinDistance) && Math.abs(velocityX) > slideMinVelocity) {    
            Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();
            mCurrentIndex = (mCurrentIndex == (FRAGMENTS_LIST.length-1)?(FRAGMENTS_LIST.length-1):(mCurrentIndex+1));
            changeFragment(FRAGMENTS_LIST[mCurrentIndex]);
            
        } else if (e2.getX() - e1.getX() > slideMinDistance && Math.abs(velocityX) > slideMinVelocity) {
            Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();
            mCurrentIndex = (mCurrentIndex == 0?0:(mCurrentIndex-1));
            changeFragment(FRAGMENTS_LIST[mCurrentIndex]);
        }  
        
        return false; 
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {        
        return mGestureDetector.onTouchEvent(event);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);  
    } 

}
