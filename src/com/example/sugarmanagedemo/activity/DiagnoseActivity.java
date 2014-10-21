package com.example.sugarmanagedemo.activity;

import org.kymjs.aframe.ui.activity.KJFragmentActivity;
import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.constant.Constants;
import com.example.sugarmanagedemo.fragment.DiagnoseFragment;

public class DiagnoseActivity extends KJFragmentActivity {
    private View mParent;
    private BaseFragment mDiagnoseFragment;
    
    public DiagnoseActivity(){
        this.setHiddenActionBar(true);
        this.setAllowFullScreen(true);
    }

    @Override
    public void setRootView() {
        mParent = getLayoutInflater().inflate(R.layout.activity_blank, null);
        setContentView(mParent);
    }
    
    @Override
    public void initData(){
        super.initData();
    }
    
    @Override
    protected void initWidget() {
        super.initWidget();
        
        mDiagnoseFragment = new DiagnoseFragment();
        
        changeFragment(mDiagnoseFragment);
    }

    @Override
    public void changeFragment(BaseFragment targetFragment) {
        getFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment_main, targetFragment, "fragment")
        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .commit();

    }

}
