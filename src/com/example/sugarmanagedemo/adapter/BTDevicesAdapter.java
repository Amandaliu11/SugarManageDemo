package com.example.sugarmanagedemo.adapter;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sugarmanagedemo.R;

public class BTDevicesAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BluetoothDevice> mDevices;
    
    public BTDevicesAdapter(Context context,List<BluetoothDevice> devices){
        mInflater = LayoutInflater.from(context);
        mDevices = devices;
    }

    @Override
    public int getCount() {
        if(null == mDevices){
            return 0;
        }
        return mDevices.size();
    }

    @Override
    public Object getItem(int position) {
        if(null == mDevices){
            return null;
        }
        return mDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder(); 
            convertView = mInflater.inflate(R.layout.item_deviceslist, null);
            holder.vTextView = (TextView)convertView.findViewById(R.id.txt_mac);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        
        if(null != mDevices){
            holder.vTextView.setText(mDevices.get(position).getName()+" , "+mDevices.get(position).getAddress()); 
        }
        
        return convertView;
    }
    
    private final class ViewHolder{
        public TextView vTextView;
    }

}
