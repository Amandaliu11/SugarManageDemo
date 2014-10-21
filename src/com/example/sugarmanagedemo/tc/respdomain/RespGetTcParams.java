package com.example.sugarmanagedemo.tc.respdomain;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.example.sugarmanagedemo.util.AppUtil;

public class RespGetTcParams extends RespBase{
    private String deviceSn;
    private int patientCount;
    private ArrayList<Integer> patientIds;

    public RespGetTcParams(byte abyte0[]) throws UnsupportedEncodingException
    {
        super(abyte0);
        patientIds = new ArrayList<Integer>();
        deviceSn = new String(getRange(abyte0, 3, 8),"UTF8");
                
        patientCount = AppUtil.byte2int(abyte0[11]);
        
        for(int i = 0; i < patientCount; ++i){
            patientIds.add(Integer.valueOf(AppUtil.bytesToUint(getRange(abyte0, 12 + i * 4, 4))));
        }
    }

    public String getDeviceSn()
    {
        return deviceSn;
    }

    public int getPatientCount()
    {
        return patientCount;
    }

    public ArrayList getPatientIds()
    {
        return patientIds;
    }
}
