package com.example.sugarmanagedemo.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.adapter.TopicsGridAdapter.ViewHolder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicsListAdapter extends BaseAdapter {
    private List<Map<String,Object>> mData;
    
    private Activity mActivity;
    
    public TopicsListAdapter(Activity context){
        this.mActivity = context;
        initDemoData();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mActivity, R.layout.item_topiclist, null);
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.item_title);
            holder.mMain = (TextView) convertView.findViewById(R.id.item_main);
            holder.mDate = (TextView) convertView.findViewById(R.id.item_date);
            holder.mStar = (TextView) convertView.findViewById(R.id.item_star);
            convertView.setTag(holder);

            Map<String,Object> mMap = mData.get(position);
            
            holder.mTitle.setText(mMap.get("title").toString());
            holder.mMain.setText(mMap.get("main").toString());
            holder.mDate.setText(mMap.get("date").toString());
            holder.mStar.setText(mMap.get("star").toString());
            
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    
    
    private void initDemoData(){
        mData = new ArrayList<Map<String,Object>>();
        
        Map<String,Object> mMap = new HashMap<String,Object>();
        mMap.put("title", "С�����˶���10��ע������");
        mMap.put("main", "�˶���1������");
        mMap.put("date", "2014��02��28��");
        mMap.put("star", 17);
        mData.add(mMap);
        
        mMap = new HashMap<String,Object>();
        mMap.put("title", "I������������");
        mMap.put("main", "������I�����򲡣�C�ģ�Ѫ��");
        mMap.put("date", "2014��01��28��");
        mMap.put("star", 11);
        mData.add(mMap);
        
        mMap = new HashMap<String,Object>();
        mMap.put("title", "�������򲡲�����Ѫ�Ǵ��");
        mMap.put("main", "I������");
        mMap.put("date", "2014��09��25��");
        mMap.put("star", 31);
        mData.add(mMap);       
    }
    
    
    static class ViewHolder {
        TextView mTitle;
        TextView mMain;
        TextView mDate;
        TextView mStar;
    }

}
