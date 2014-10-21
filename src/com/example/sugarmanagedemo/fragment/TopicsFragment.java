package com.example.sugarmanagedemo.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.activity.TopicActivity;
import com.example.sugarmanagedemo.adapter.TopicsGridAdapter;
import com.example.sugarmanagedemo.constant.Constants;

public class TopicsFragment extends BaseFragment {
    @BindView(id = R.id.grid_topics)
    private GridView mGrid;
    
    private View mParent;
    private Activity mActivity;
    
    private TopicsGridAdapter mAdapter;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
            Bundle bundle) {
        mParent = inflater.inflate(R.layout.fragment_topics, null);
        mActivity = getActivity();
        return mParent;
    }
    
    @Override
    public void initData(){
        super.initData();
        
        mAdapter = new TopicsGridAdapter(mActivity);
        mGrid.setAdapter(mAdapter);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        
        mGrid.setOnItemClickListener(mGrid_clickListener);
    }
    
    private GridView.OnItemClickListener mGrid_clickListener = new GridView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            String title = mAdapter.getItem(position).toString();
            
            if(mActivity instanceof TopicActivity){
                ((TopicActivity)mActivity).changeFragment(Constants.FRAGMENT_TAG_TOPIC_LIST,title);
            }
        }        
    };

}
