package com.example.sugarmanagedemo.domain;

import java.util.Date;

public class DeviceBinding {
    private int ID = -1;
    private Date bindingTime;
    private String deviceSN;
    private boolean isCompletedFlag = false;
    private String mac;
    private int patientId;
    private String sensorId;

    public DeviceBinding(){
    }

    public DeviceBinding(int id, String sn, String mac, String sensorid, int patientid, Date date, boolean flag){
        ID = id;
        deviceSN = sn;
        this.mac = mac;
        sensorId = sensorid;
        patientId = patientid;
        bindingTime = date;
        isCompletedFlag = flag;
    }

    public Date getBindingTime(){
        return bindingTime;
    }

    public String getDeviceSN(){
        return deviceSN;
    }

    public int getID(){
        return ID;
    }

    public String getMac(){
        return mac;
    }

    public int getPatientId(){
        return patientId;
    }

    public String getSensorId(){
        return sensorId;
    }

    public boolean isCompletedFlag(){
        return isCompletedFlag;
    }

    public void setBindingTime(Date date){
        bindingTime = date;
    }

    public void setCompletedFlag(boolean flag){
        isCompletedFlag = flag;
    }

    public void setDeviceSN(String s){
        deviceSN = s;
    }

    public void setID(int i){
        ID = i;
    }

    public void setMac(String s){
        mac = s;
    }

    public void setPatientId(int i){
        patientId = i;
    }

    public void setSensorId(String s){
        sensorId = s;
    }
}
