package com.example.sugarmanagedemo.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.fragment.BaseFragment;
import org.kymjs.aframe.ui.widget.KJListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.activity.TopicActivity;
import com.example.sugarmanagedemo.adapter.TopicsListAdapter;
import com.example.sugarmanagedemo.constant.Constants;

public class TopicListFragment extends BaseFragment {
    @BindView(id = R.id.list_topics)
    private KJListView mTopicList;
    
    private View mParent;
    private Activity mActivity;
    private TopicsListAdapter mAdapter;

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
            Bundle bundle) {
        mParent = inflater.inflate(R.layout.fragment_topiclist, null);
        mActivity = this.getActivity();
        return mParent;
    }
    
    @Override
    public void initData(){
        super.initData();
        
        mAdapter = new TopicsListAdapter(mActivity);
        mTopicList.setAdapter(mAdapter);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        
        mTopicList.setPullRefreshEnable(true);
        mTopicList.setPullLoadEnable(true);
        mTopicList.setOnItemClickListener(mListItem_clickListener);
    }
    
    private ListView.OnItemClickListener mListItem_clickListener = new ListView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            if(mActivity instanceof TopicActivity){
                Bundle bundle = new Bundle();
                bundle.putString("url", "http://bbs.tnbz.com/thread-38935-1-1.html");
                
                ((TopicActivity)mActivity).setBundleToDetail(bundle);
                ((TopicActivity)mActivity).changeFragment(Constants.FRAGMENT_TAG_TOPIC_DETAIL,null);
            }            
        }
        
    };
}
