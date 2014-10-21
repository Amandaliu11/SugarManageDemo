package com.example.sugarmanagedemo.domain;

import android.bluetooth.BluetoothSocket;
import android.content.Context;

import com.example.sugarmanagedemo.constant.BluetoothStatusEnums;
import com.example.sugarmanagedemo.tc.respdomain.ResponseStatusEnums;
import com.example.sugarmanagedemo.util.dbutil.SystemParameterDbUtil;

public class StatusManager {
    private static StatusManager mInstance;
    private StatusManager(){        
    }
    
    public static StatusManager getInstance(){
        if(null == mInstance){
            mInstance = new StatusManager();
        }
        
        return mInstance;
    }
    
    
    /*
     * =====================================================
     * Bluetooth status
     * =====================================================
     */
    private BluetoothStatusEnums mBTStatus;
    public BluetoothStatusEnums getBTStatus(){
        return this.mBTStatus;
    }
    
    public void setBTStatus(BluetoothStatusEnums status){
        this.mBTStatus = status;
    }
    
    /*
     * =====================================================
     * tc device
     * =====================================================
     */
    private DeviceBinding currDeviceBind = null;
    public DeviceBinding getCurrDeviceBind()
    {
        return currDeviceBind;
    }

    public DeviceBinding getCurrDeviceBind(Context context)
    {
        if (currDeviceBind == null)
        {
            SystemParameterDbUtil systemparameterdbutil = new SystemParameterDbUtil(context, true);
            setCurrDeviceBind(systemparameterdbutil.getCurrDeviceBindFromDB());
            systemparameterdbutil.closeDB();
        }
        return currDeviceBind;
    }
    
    public void setCurrDeviceBind(DeviceBinding devicebinding)
    {
        currDeviceBind = devicebinding;
    }
        
    /*
     * =====================================================
     * tc status
     * =====================================================
     */
    private ResponseStatusEnums.TcStatusOperation currTcStatus = null;
    public ResponseStatusEnums.TcStatusOperation getCurrTcStatus(){
        return currTcStatus;
    }
    
    public void setCurrTcStatus(ResponseStatusEnums.TcStatusOperation status){
        currTcStatus = status;
    }
    
    
    private BluetoothSocket socket = null;
    public BluetoothSocket getBluetoothSocket(){
        return socket;
    }
    
    public void setBluetoothSocket(BluetoothSocket s){
        this.socket = s;
    }
    
}
