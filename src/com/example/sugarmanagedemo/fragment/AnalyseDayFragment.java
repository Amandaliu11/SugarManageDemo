package com.example.sugarmanagedemo.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sugarmanagedemo.R;

public class AnalyseDayFragment extends BaseFragment {
    @BindView(id = R.id.image)
    private ImageView mImage;
    
    private View mParent;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
            Bundle bundle) {
        mParent = inflater.inflate(R.layout.fragment_blank_image, null);
        return mParent;
    }
    
    @Override
    protected void initData() {
        super.initData();
    }
    
    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        
        mImage.setImageResource(R.drawable.temp1);
    }



    @Override
    protected void widgetClick(View v) {
        // TODO Auto-generated method stub
        super.widgetClick(v);
    }



}
