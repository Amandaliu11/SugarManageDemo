/**
 * SampleData.java [v1.0.0]
 * classes: com.youngohealth.domain.SampleData
 * Amanda Create at 2014骞�10鏈�10鏃� 涓婂崍12:33:52
 * Copyright 闃冲厜鍋ュ悍淇℃伅鎶�鏈湁闄愬叕鍙�
 */
package com.example.sugarmanagedemo.domain;

import java.util.Calendar;
import java.util.Date;

import com.example.sugarmanagedemo.util.AppUtil;

/**
 * com.youngohealth.domain.SampleData
 * @author Amanda
 * create at 2014骞�10鏈�10鏃� 涓婂崍12:33:52
 */
public class SampleData {

    private int ID;
    private int bindId;
    private int patientId;
    private Date receiveTime;
    private double sampleElectric;
    private int sampleNo;
    private int sampleTemperature;
    private Date sampleTime;
    private int sampleValue;

    public SampleData()
    {
        sampleValue = -1;
        sampleElectric = -1D;
        sampleNo = -1;
        sampleTemperature = -1;
    }

    public SampleData(byte abyte0[])
    {
        sampleValue = -1;
        sampleElectric = -1D;
        sampleNo = -1;
        sampleTemperature = -1;
        int i = AppUtil.bytesToUint(AppUtil.getRange(abyte0, 0, 4));
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1, 0, 0, 0);
        calendar.add(13, i);
        sampleTime = calendar.getTime();
        sampleValue = calcSampleValue(AppUtil.getRange(abyte0, 4, 3));
        sampleTemperature = calcSampleTemperature(AppUtil.getRange(abyte0, 4, 3));
        sampleElectric = calcEletricValue(sampleValue);
        receiveTime = new Date();
//        patientId = App.getCurrentPatient().getID();//////////
//        DeviceBinding devicebinding = App.getCurrDeviceBind();///////////////////
        int j = 0;
//        if (devicebinding != null)
//        {
//            j = App.getCurrDeviceBind().getID();/////////////////
//        }
        bindId = j;
    }

    private double calcEletricValue(int i)
    {
        return (1000D * (0.00030525030525030525D * (double)(4096 - i))) / 1.8D;
    }

    private int calcSampleTemperature(byte abyte0[])
    {
        return AppUtil.byte2int((byte)(0x3f & abyte0[0]));
    }

    private int calcSampleValue(byte abyte0[])
    {
//        byte byte0 = abyte0[2];
//        byte byte1 = abyte0[1];
//        return AppUtil.bytesToUint(new byte[] {
//            AppUtil.int2byte(byte0 & 0xf), byte1
//        });
        
        
        return AppUtil.bytesToUint(abyte0);
    }

    public int getBindId()
    {
        return bindId;
    }

    public int getID()
    {
        return ID;
    }

    public int getPatientId()
    {
        return patientId;
    }

    public Date getReceiveTime()
    {
        return receiveTime;
    }

    public double getSampleElectric()
    {
        return sampleElectric;
    }

    public int getSampleNo()
    {
        return sampleNo;
    }

    public int getSampleTemperature()
    {
        return sampleTemperature;
    }

    public Date getSampleTime()
    {
        return sampleTime;
    }

    public double getSampleValue()
    {
        return (double)sampleValue;
    }

    public void setBindId(int i)
    {
        bindId = i;
    }

    public void setID(int i)
    {
        ID = i;
    }

    public void setPatientId(int i)
    {
        patientId = i;
    }

    public void setReceiveTime(Date date)
    {
        receiveTime = date;
    }

    public void setSampleElectric(double d)
    {
        sampleElectric = d;
    }

    public void setSampleNo(int i)
    {
        sampleNo = i;
    }

    public void setSampleTemperature(int i)
    {
        sampleTemperature = i;
    }

    public void setSampleTime(Date date)
    {
        sampleTime = date;
    }

    public void setSampleValue(int i)
    {
        sampleValue = i;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(sampleNo);
        stringbuilder.append(",");
        stringbuilder.append(sampleValue);
        stringbuilder.append(",");
        stringbuilder.append(AppUtil.date2String(sampleTime));
        return stringbuilder.toString();
    }
}
