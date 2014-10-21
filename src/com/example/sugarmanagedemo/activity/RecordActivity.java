package com.example.sugarmanagedemo.activity;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.KJActivityManager;
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
import com.example.sugarmanagedemo.fragment.DiagnoseFragment;
import com.example.sugarmanagedemo.fragment.RecordFragment;
import com.example.sugarmanagedemo.fragment.ScheduleFragment;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenu;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenu.SateliteClickedListener;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenuItem;
import com.example.sugarmanagedemo.residemenu.ResideMenu;
import com.example.sugarmanagedemo.residemenu.ResideMenuItem;

public class RecordActivity extends KJFragmentActivity implements OnTouchListener,OnGestureListener{
    @BindView(id = R.id.btn_back, click = true)
    protected ImageButton mBtn_back;
    
    @BindView(id = R.id.btn_menu, click = true)
    protected ImageButton mBtn_menu;
    
    @BindView(id = R.id.txt_title)
    protected TextView mTxt_title;
    
    @BindView(id = R.id.patch_menu)
    protected SatelliteMenu mPathMenu;    
    
    @BindView(id = R.id.indictor_group)
    public LinearLayout mIndictorGroup;
    
    private View mParent;
    
    private ResideMenu mResideMenu;
    private ResideMenuItem mResideMenuItemMain;
    private ResideMenuItem mResideMenuItemProfile;
    private ResideMenuItem mResideMenuItemHospital;
    private ResideMenuItem mResideMenuItemTopic;
    private ResideMenuItem mResideMenuItemExit;
    private boolean isShowResideMenu;
    
    private BaseFragment mRecordFragment;
    private BaseFragment mScheduleFragment;
    private BaseFragment mDiagnoseFragment;
    
    private String mCurrentFragmentTag;
    
    private ImageView[] tips;
    
    private GestureDetector mGestureDetector = null;  
    
    // 最小的水平有效划动距离和速度，超过该距离和速度才触发划动事件  
    private int slideMinDistance = 200;    
    private int slideMinVelocity = 0;
    
    public RecordActivity(){
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
        mGestureDetector = new GestureDetector((OnGestureListener)RecordActivity.this);
    }
    
    @Override
    protected void initWidget() {
        super.initWidget();
        
        initSlideMenu();
        initPathMenu();
        initWidgetIndictors();
        
        mRecordFragment = new RecordFragment(); 
        mScheduleFragment = new ScheduleFragment();
        
        mDiagnoseFragment = new DiagnoseFragment();
        
        changeFragment(mRecordFragment,Constants.FRAGMENT_TAG_RECORD);
    }
    
    @Override
    public void widgetClick(View v) { 
        super.widgetClick(v);
        if(v == mBtn_menu){            
            if(isShowResideMenu){                
                mResideMenu.closeMenu();
            }else{
                mResideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        }else if(v == mBtn_back){
            mResideMenu.closeMenu();
            this.skipActivity(this, MainActivity.class);
            
        }else if(v == mResideMenuItemMain){
            mResideMenu.closeMenu();
            changeFragment(mRecordFragment,Constants.FRAGMENT_TAG_RECORD); 
            
        }else if(v == mResideMenuItemProfile){
            mResideMenu.closeMenu();
            Toast.makeText(RecordActivity.this, "正在建设中...", Toast.LENGTH_SHORT).show();
            
        }else if(v == mResideMenuItemHospital){
            mResideMenu.closeMenu();
            changeFragment(mDiagnoseFragment,Constants.FRAGMENT_TAG_HOSPITAL); 
            
        }else if(v == mResideMenuItemTopic){
            mResideMenu.closeMenu();
            
            Intent mIntent = new Intent();
            mIntent.setClass(RecordActivity.this, TopicActivity.class);
            RecordActivity.this.startActivity(mIntent);
            
        }else if(v == mResideMenuItemExit){
            mResideMenu.closeMenu();
            KJActivityManager.create().AppExit(RecordActivity.this);
        }
    }    
    
    
    private void initSlideMenu(){
        mResideMenu = new ResideMenu(this);
        mResideMenu.setBackground(R.drawable.menu_background);
        mResideMenu.attachToActivity(this);
        mResideMenu.setScaleValue(0.6f);
        mResideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
        mResideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        
        mResideMenu.setMenuListener(new ResideMenu.OnMenuListener() {
            @Override
            public void openMenu() {
                isShowResideMenu = true;
            }

            @Override
            public void closeMenu() {
                isShowResideMenu = false;
            }
        });
        
        mResideMenuItemMain   = new ResideMenuItem(this, R.drawable.icon_home,"首页");
        mResideMenuItemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "个人中心");
        mResideMenuItemHospital = new ResideMenuItem(this, R.drawable.icon_calendar, "诊前体检");
        mResideMenuItemTopic = new ResideMenuItem(this, R.drawable.icon_settings, "支招");
        mResideMenuItemExit = new ResideMenuItem(this, R.drawable.icon_home, "退出登录");
        
        mResideMenuItemMain.setOnClickListener(this);
        mResideMenuItemProfile.setOnClickListener(this);
        mResideMenuItemHospital.setOnClickListener(this);
        mResideMenuItemTopic.setOnClickListener(this);
        mResideMenuItemExit.setOnClickListener(this);

        mResideMenu.addMenuItem(mResideMenuItemMain, ResideMenu.DIRECTION_RIGHT);
        mResideMenu.addMenuItem(mResideMenuItemProfile, ResideMenu.DIRECTION_RIGHT);
        mResideMenu.addMenuItem(mResideMenuItemHospital, ResideMenu.DIRECTION_RIGHT);
        mResideMenu.addMenuItem(mResideMenuItemTopic, ResideMenu.DIRECTION_RIGHT);
        mResideMenu.addMenuItem(mResideMenuItemExit, ResideMenu.DIRECTION_RIGHT);        
    }


    public void changeFragment(BaseFragment targetFragment,String tag) {
        mCurrentFragmentTag = tag;
        mTxt_title.setText(getTitleFromTag(tag));
        
        mBtn_back.setVisibility(View.VISIBLE);
        
        mResideMenu.clearIgnoredViewList();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main, targetFragment, tag)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        
        if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD)){
            mIndictorGroup.setVisibility(View.VISIBLE);
            setImageBackground(0);
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_SCHEDULE)){
            mIndictorGroup.setVisibility(View.VISIBLE);
            setImageBackground(1);
        }else{
            mIndictorGroup.setVisibility(View.GONE);
        }
        
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
                    RecordActivity.this.skipActivity(RecordActivity.this, MainActivity.class);
                    break;
                case 3:
                    RecordActivity.this.skipActivity(RecordActivity.this, AnalyseActivity.class);
                    break;
                case 4:
                    Toast.makeText(RecordActivity.this, "正在建设中...", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        });
    }
    
    private void initWidgetIndictors(){
        //将点点加入到ViewGroup中  
        tips = new ImageView[2];  
        for(int i=0; i<2; i++){  
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
        for(int i=0; i<2; i++){  
            if(i == selectItems){  
                tips[i].setBackgroundResource(R.drawable.index_f);  
            }else{  
                tips[i].setBackgroundResource(R.drawable.index_n);  
            }  
        }  
    }  


    private String getTitleFromTag(String tag){
        if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD)){
            return "记录";
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_PROFILE)){
            return "个人中心";
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_HOSPITAL)){
            return "诊前体检";
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC)){
            return "支招";
        }else{
            return getString(R.string.app_name);
        }
    }
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.setBackListener(false);
        
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD) 
                    || mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_SCHEDULE) ){
                this.skipActivity(this, MainActivity.class);
                return true;
            }else{
                changeFragment(mRecordFragment,Constants.FRAGMENT_TAG_RECORD);
                return true;
            }
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
            if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_RECORD)){
                changeFragment(mScheduleFragment,Constants.FRAGMENT_TAG_SCHEDULE);
            }
            
        } else if (e2.getX() - e1.getX() > slideMinDistance && Math.abs(velocityX) > slideMinVelocity) {
            Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();            
            if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_SCHEDULE)){
                changeFragment(mRecordFragment,Constants.FRAGMENT_TAG_RECORD);
            }
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
