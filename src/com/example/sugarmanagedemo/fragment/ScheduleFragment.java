package com.example.sugarmanagedemo.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.fragment.BaseFragment;
import org.kymjs.aframe.ui.widget.KJListView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;

public class ScheduleFragment extends BaseFragment {
    @BindView(id = R.id.btn_broadcast, click = true)
    private ImageButton mBtnBroadcast;
    
    @BindView(id = R.id.list_schedule)
    private KJListView mListSchedule;
    
    private View mParent;

    protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        mParent = arg0.inflate(R.layout.fragment_schedule, null);
        return mParent;
    }
    
    @Override
    public void initData(){
        super.initData();
    }
    
    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
    }
    
    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
    }

}
