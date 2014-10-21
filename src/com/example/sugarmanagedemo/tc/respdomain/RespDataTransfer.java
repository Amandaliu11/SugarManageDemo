/**
 * RespDataTransfer.java [v1.0.0]
 * classes: com.youngohealth.tc.respdomain.RespDataTransfer
 * Amanda Create at 2014骞�10鏈�10鏃� 涓婂崍12:32:06
 * Copyright 闃冲厜鍋ュ悍淇℃伅鎶�鏈湁闄愬叕鍙�
 */
package com.example.sugarmanagedemo.tc.respdomain;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;

import com.example.sugarmanagedemo.domain.SampleData;
import com.example.sugarmanagedemo.util.AppUtil;

/**
 * com.youngohealth.tc.respdomain.RespDataTransfer
 * @author Amanda
 * create at 2014骞�10鏈�10鏃� 涓婂崍12:32:06
 */
public class RespDataTransfer extends RespBase {
    private int dataCount;
    private int dataId;
    private ArrayList<SampleData> dataValues;

    public RespDataTransfer(byte abyte0[])
    {
        super(abyte0);
        dataId = AppUtil.bytesToUint(getRange(abyte0, 3, 4));
        dataCount = AppUtil.byte2int(abyte0[7]);
        dataValues = new ArrayList<SampleData>(dataCount);
        if (dataCount == 0)
        {
            Log.w("TC_Log", "RespDataTransfer -- get data count is 0");
        }
        int i = 0;
        do
        {
            if (i >= dataCount)
            {
                return;
            }
            SampleData sampledata = new SampleData(getRange(abyte0, 8 + i * 7, 7));
//            sampledata.setSampleNo(App.getCurrentMonitorDataId());
            System.out.println((new StringBuilder("RespDataTransfer -- get Data from Tc:")).append(sampledata.getSampleValue()).append(",").append(AppUtil.date2String(sampledata.getSampleTime())).toString());
            dataValues.add(sampledata);
//            App.addCurrentMonitorDataId();////////////
            i++;
        } while (true);
    }

    public int getDataCount()
    {
        return dataCount;
    }

    public int getDataId()
    {
        return dataId;
    }

    public ArrayList getDataValues()
    {
        return dataValues;
    }

    public void setDataCount(int i)
    {
        dataCount = i;
    }

    public void setDataId(int i)
    {
        dataId = i;
    }

    public void setDataValues(ArrayList arraylist)
    {
        dataValues = arraylist;
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append((new StringBuilder("Data Id:")).append(dataId).append(" ").toString());
        stringbuilder.append((new StringBuilder("Data Count:")).append(dataCount).append(" ").toString());
        stringbuilder.append("Data Values:");
        Iterator<SampleData> iterator = dataValues.iterator();
        do
        {
            if (!iterator.hasNext())
            {
                return stringbuilder.toString();
            }
            stringbuilder.append((iterator.next()).toString());
            stringbuilder.append(";");
        } while (true);
    }
}
