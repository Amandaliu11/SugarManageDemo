package com.example.sugarmanagedemo.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;

public class RecordListAdapter extends BaseAdapter {
    private Activity mActivity;
    
    private final static String[] ItemsTitles = {"就餐时间列表","血糖","饮食","用药","运动","心情"};
    
    public RecordListAdapter(Activity context){
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
            convertView = View.inflate(mActivity, R.layout.item_recordlist, null);
            holder = new ViewHolder();
            holder.mIcon = (ImageView) convertView.findViewById(R.id.img);
            holder.mTxt = (TextView)convertView.findViewById(R.id.txt);
            convertView.setTag(holder);
            
            switch(position){
            case 0:
                holder.mIcon.setVisibility(View.GONE);
                holder.mTxt.setTextColor(Color.BLACK);
                holder.mTxt.setText(ItemsTitles[position]);
                break;
            case 1:
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageResource(R.drawable.emo_im_foot_in_mouth);
                holder.mTxt.setTextColor(mActivity.getResources().getColor(R.color.base_green));
                holder.mTxt.setText(ItemsTitles[position]);
                break;
            case 2:
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageResource(R.drawable.emo_im_tongue_sticking_out);
                holder.mTxt.setTextColor(mActivity.getResources().getColor(R.color.base_green));
                holder.mTxt.setText(ItemsTitles[position]);
                break;
            case 3:
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageResource(R.drawable.emo_im_kissing);
                holder.mTxt.setTextColor(mActivity.getResources().getColor(R.color.base_green));
                holder.mTxt.setText(ItemsTitles[position]);
                break;
            case 4:
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageResource(R.drawable.emo_im_cool);
                holder.mTxt.setTextColor(mActivity.getResources().getColor(R.color.base_green));
                holder.mTxt.setText(ItemsTitles[position]);
                break;
            case 5:
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageResource(R.drawable.emo_im_winking);
                holder.mTxt.setTextColor(mActivity.getResources().getColor(R.color.base_green));
                holder.mTxt.setText(ItemsTitles[position]);
                break;
            }
            
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
