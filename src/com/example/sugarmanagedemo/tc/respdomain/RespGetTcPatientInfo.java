package com.example.sugarmanagedemo.tc.respdomain;

import java.util.Calendar;

import com.example.sugarmanagedemo.util.AppUtil;

public class RespGetTcPatientInfo extends RespBase {
    private int dataVersion;
    private int measureDataId;
    private Calendar time;
    private int transferDataId;

    public RespGetTcPatientInfo(byte abyte0[])
    {
        super(abyte0);
        dataVersion = abyte0[3];
        time = intBytes2Date(getRange(abyte0, 4, 4));
        measureDataId = AppUtil.bytesToUint(getRange(abyte0, 8, 4));
        transferDataId = AppUtil.bytesToUint(getRange(abyte0, 12, 4));
    }

    public int getDataVersion()
    {
        return dataVersion;
    }

    public int getMeasureDataId()
    {
        return measureDataId;
    }

    public Calendar getTime()
    {
        return time;
    }

    public int getTransferDataId()
    {
        return transferDataId;
    }
}
