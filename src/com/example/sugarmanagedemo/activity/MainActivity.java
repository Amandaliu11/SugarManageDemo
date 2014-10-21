package com.example.sugarmanagedemo.activity;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.KJActivityManager;
import org.kymjs.aframe.ui.ViewInject;
import org.kymjs.aframe.ui.activity.KJFragmentActivity;
import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.constant.Constants;
import com.example.sugarmanagedemo.fragment.DiagnoseFragment;
import com.example.sugarmanagedemo.fragment.MainFragment;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenu;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenu.SateliteClickedListener;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenuItem;
import com.example.sugarmanagedemo.residemenu.ResideMenu;
import com.example.sugarmanagedemo.residemenu.ResideMenuItem;

public class MainActivity extends KJFragmentActivity {    
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
    
    private BaseFragment mMainFragment;
    private BaseFragment mDiagnoseFragment;
    
    private String mCurrentFragmentTag;
    
    public MainActivity(){
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
    }
    
    @Override
    protected void initWidget() {
        super.initWidget();
        
        initSlideMenu();
        initPathMenu();
        mIndictorGroup.setVisibility(View.INVISIBLE);
        
        
        mMainFragment = new MainFragment(); 
        mDiagnoseFragment = new DiagnoseFragment();
        
        changeFragment(mMainFragment,Constants.FRAGMENT_TAG_MAIN);
        
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
            changeFragment(mMainFragment,Constants.FRAGMENT_TAG_MAIN);
            
        }else if(v == mResideMenuItemMain){
            mResideMenu.closeMenu();
            changeFragment(mMainFragment,Constants.FRAGMENT_TAG_MAIN); 
            
        }else if(v == mResideMenuItemProfile){
            mResideMenu.closeMenu();
            Toast.makeText(MainActivity.this, "正在建设中...", Toast.LENGTH_SHORT).show();
        }else if(v == mResideMenuItemHospital){
            mResideMenu.closeMenu();
            changeFragment(mDiagnoseFragment,Constants.FRAGMENT_TAG_HOSPITAL); 
            
        }else if(v == mResideMenuItemTopic){
            mResideMenu.closeMenu();
            
            Intent mIntent = new Intent();
            mIntent.setClass(MainActivity.this, TopicActivity.class);
            MainActivity.this.startActivity(mIntent);
        }else if(v == mResideMenuItemExit){
            mResideMenu.closeMenu();
            KJActivityManager.create().AppExit(MainActivity.this);
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
        
        if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_MAIN)){
            mBtn_back.setVisibility(View.INVISIBLE);
        }else{
            mBtn_back.setVisibility(View.VISIBLE);
        }
        
        mResideMenu.clearIgnoredViewList();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main, targetFragment, tag)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
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
                case 2:
                    MainActivity.this.skipActivity(MainActivity.this, RecordActivity.class);
                    break;
                case 3:
                    MainActivity.this.skipActivity(MainActivity.this, AnalyseActivity.class);
                    break;
                case 4:
                    Toast.makeText(MainActivity.this, "正在建设中...", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        });
    }


    private String getTitleFromTag(String tag){
        if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_MAIN)){
            return "首页";
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
            if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_MAIN)){
                ViewInject.create().getExitDialog(this);
                return true;
            }else{
                changeFragment(mMainFragment,Constants.FRAGMENT_TAG_MAIN);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
