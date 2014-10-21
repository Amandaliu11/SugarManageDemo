package com.example.sugarmanagedemo.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.fragment.BaseFragment;
import org.kymjs.aframe.ui.widget.KJListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.activity.RecordDetailActivity;
import com.example.sugarmanagedemo.adapter.RecordListAdapter;
import com.example.sugarmanagedemo.constant.Constants;

public class RecordFragment extends BaseFragment {
    @BindView(id = R.id.txt_currentsugar)
    private TextView mTextView_CurrentSugar;
    
    @BindView(id = R.id.btn_record_diet,click = true)
    private Button mBtn_CurrentDiet;
    
    @BindView(id = R.id.btn_record_add,click = true)
    private Button mBtn_AddDiet;
    
    @BindView(id = R.id.list_record)
    private KJListView mListRecords;
    
    private View mParent;
    private Activity mActivity;
    
    private RecordListAdapter mAdapter;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
            Bundle bundle) {
        mParent = inflater.inflate(R.layout.fragment_record, null);
        mActivity = getActivity();
        return mParent;
    }
    
    @Override
    public void initData(){
        super.initData();
        
        mAdapter = new RecordListAdapter(mActivity);
        mListRecords.setAdapter(mAdapter);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        
        mListRecords.setPullRefreshEnable(false);
        mListRecords.setPullLoadEnable(false);
        mListRecords.setOnItemClickListener(mListItem_clickListener);
    }
    
    @Override
    protected void widgetClick(View v) {
        super.widgetClick(v);
        switch(v.getId()){
        case R.id.btn_record_diet:
            break;
        case R.id.btn_record_add:
            break;
        }
    }
    
    private ListView.OnItemClickListener mListItem_clickListener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                Intent vIntent = new Intent();
                vIntent.putExtra("tag", getTagIndex(position-1));//header is 0
                vIntent.setClass(mActivity, RecordDetailActivity.class);
                mActivity.startActivity(vIntent);
        }        
    };
    
    
    private String getTagIndex(int position){
        switch(position){
        case 0:
            return Constants.FRAGMENT_TAG_RECORD_DIET_TIME;
        case 1:
            return Constants.FRAGMENT_TAG_RECORD_SUGAR;
        case 2:
            return Constants.FRAGMENT_TAG_RECORD_DIET;
        case 3:
            return Constants.FRAGMENT_TAG_RECORD_MEDICINE;
        case 4:
            return Constants.FRAGMENT_TAG_RECORD_SPORT;
        case 5:
            return Constants.FRAGMENT_TAG_RECORD_MOOD;
        default:
            return Constants.FRAGMENT_TAG_RECORD_SUGAR;
        }
    }

}
