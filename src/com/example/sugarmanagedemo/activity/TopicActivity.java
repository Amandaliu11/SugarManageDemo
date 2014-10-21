package com.example.sugarmanagedemo.activity;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.activity.KJFragmentActivity;
import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.constant.Constants;
import com.example.sugarmanagedemo.fragment.TopicDetailFragment;
import com.example.sugarmanagedemo.fragment.TopicListFragment;
import com.example.sugarmanagedemo.fragment.TopicsFragment;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenu;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenu.SateliteClickedListener;
import com.example.sugarmanagedemo.pathmenu.SatelliteMenuItem;

public class TopicActivity extends KJFragmentActivity{
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
    
    private BaseFragment mTopicsFragment;
    private BaseFragment mTopicListFragment;
    private BaseFragment mTopicDetailFragment;
    
    private String mCurrentFragmentTag;
    
    private Bundle mBundle;
    
    public TopicActivity(){
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
        
        initPathMenu();
        mIndictorGroup.setVisibility(View.GONE);
        mBtn_menu.setVisibility(View.INVISIBLE);
        
        mTopicsFragment = new TopicsFragment();
        mTopicListFragment  = new TopicListFragment();
        mTopicDetailFragment = new TopicDetailFragment();
        
        changeFragment(mTopicsFragment,Constants.FRAGMENT_TAG_TOPIC_MAIN);
    }
    
    @Override
    public void widgetClick(View v) { 
        super.widgetClick(v);
        if(v == mBtn_back){
            if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_MAIN)){
                this.finish();
            }else if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_LIST)){
                changeFragment(mTopicsFragment,Constants.FRAGMENT_TAG_TOPIC_MAIN);
            }else if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_DETAIL)){
                changeFragment(mTopicListFragment,Constants.FRAGMENT_TAG_TOPIC_LIST);
            }           
        }
    } 
    
    
    public void setBundleToDetail(Bundle bundle){
        mBundle = bundle;
    }
    
    private void changeFragment(BaseFragment targetFragment,String tag,String title) {
        mCurrentFragmentTag = tag;
        
        if(null == title){
            mTxt_title.setText(title);
        }        
        
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main, targetFragment, tag)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();        
    }


    private void changeFragment(BaseFragment targetFragment,String tag) {
        mCurrentFragmentTag = tag;
        mTxt_title.setText(getTitleFromTag(tag));
        
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main, targetFragment, tag)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();        
    }
    
    
    public void changeFragment(String tag,String title) {
        if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_MAIN)){
            changeFragment(this.mTopicsFragment,tag,title);
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_LIST)){
            changeFragment(this.mTopicListFragment,tag,title);
        }else if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_DETAIL)){
            if(null != mBundle){
                mTopicDetailFragment.setArguments(mBundle);
            }
            
            changeFragment(this.mTopicDetailFragment,tag,title);
        }
    }
    
    
    private void changeFragment(BaseFragment targetFragment,int tagId) {
        String str = this.getString(tagId);
        changeFragment(targetFragment,str);
    }
    
    @Override
    public void changeFragment(BaseFragment targetFragment) {
        changeFragment(targetFragment,"fragment");        
    }
    
    
    private String getTitleFromTag(String tag){
        if(tag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_MAIN)){
            return "ж╖уп";
        }else{
            return tag;
        }
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
                    TopicActivity.this.skipActivity(TopicActivity.this, MainActivity.class);
                    break;
                case 2:
                    TopicActivity.this.skipActivity(TopicActivity.this, RecordActivity.class);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                }
            }
        });
    }    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.setBackListener(false);
        
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_MAIN)){
                this.finish();
                return true;
            }else if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_LIST)){
                changeFragment(mTopicsFragment,Constants.FRAGMENT_TAG_TOPIC_MAIN);
                return true;
            }else if(mCurrentFragmentTag.equalsIgnoreCase(Constants.FRAGMENT_TAG_TOPIC_DETAIL)){
                changeFragment(mTopicListFragment,Constants.FRAGMENT_TAG_TOPIC_LIST);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
