package com.example.sugarmanagedemo.adapter;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.adapter.RecordListAdapter.ViewHolder;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicsGridAdapter extends BaseAdapter {
    private Activity mActivity;
    
    private final static String[] ItemsTitles = {"ÌÇÄò²¡ÒûÊ³","IÐÍÌÇÄò²¡","IIÐÍÌÇÄò²¡","¶ùÍ¯ÌÇÄò²¡","ÈÑÉïÌÇÄò²¡"};
    
    public TopicsGridAdapter(Activity context){
        this.mActivity = context;
    }

    @Override
    public int getCount() {
        return ItemsTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return ItemsTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mActivity, R.layout.item_topicgrid, null);
            holder = new ViewHolder();
            holder.mIcon = (ImageView) convertView.findViewById(R.id.item_img);
            holder.mTxt = (TextView)convertView.findViewById(R.id.item_txt);
            convertView.setTag(holder);
            
            switch(position){
            case 0:
                holder.mIcon.setImageResource(R.drawable.u86_normal);
                break;
            case 1:
                holder.mIcon.setImageResource(R.drawable.u90_normal);
                break;
            case 2:
                holder.mIcon.setImageResource(R.drawable.u92_normal);
                break;
            case 3:
                holder.mIcon.setImageResource(R.drawable.u99_normal);
                break;
            case 4:
                holder.mIcon.setImageResource(R.drawable.u96_normal);
                
                break;
            }
            
            holder.mTxt.setText(ItemsTitles[position]);
            
            
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    
    static class ViewHolder {
        ImageView mIcon;
        TextView mTxt;
    }

}
