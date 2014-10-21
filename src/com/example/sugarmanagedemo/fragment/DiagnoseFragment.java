package com.example.sugarmanagedemo.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.activity.KJFragmentActivity;
import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.activity.MainActivity;

public class DiagnoseFragment extends BaseFragment {
    private View mParent;
    
    @BindView(id = R.id.switch1)
    private Switch mSwitch;
    
    @BindView(id = R.id.relativelyt)
    private RelativeLayout mReLayout;
    
    @BindView(id = R.id.btn_commit, click = true)
    protected ImageButton mBtnCommit;
    
    @BindView(id = R.id.btn_cancel, click = true)
    protected ImageButton mBtnCancel;   

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
            Bundle bundle) {
        mParent = inflater.inflate(R.layout.fragment_diagnose, null);
        return mParent;
    }
    
    @Override
    public void initData(){
        super.initData();
    }
    
    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        
        mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                if(isChecked){
                    mReLayout.setVisibility(View.VISIBLE);
                }else{
                    mReLayout.setVisibility(View.GONE);
                }
                
            }
            
        });
    }
    
    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        
        switch(v.getId()){
        case R.id.btn_commit:
        case R.id.btn_cancel:
            if(this.getActivity() instanceof KJFragmentActivity){
                ((KJFragmentActivity)this.getActivity()).skipActivity(getActivity(), MainActivity.class);
            }
            break;
        }
    }

}
