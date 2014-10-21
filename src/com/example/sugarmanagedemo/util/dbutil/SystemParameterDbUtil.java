package com.example.sugarmanagedemo.util.dbutil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sugarmanagedemo.domain.DeviceBinding;
import com.example.sugarmanagedemo.util.AppUtil;

public class SystemParameterDbUtil extends DatabaseUtil {

    public SystemParameterDbUtil(Context paramContext, boolean paramBoolean) {
        super(paramContext, paramBoolean);
    }

    public DeviceBinding getCurrDeviceBindFromDB()
    {
        Cursor cursor = db.query("T_SystemParameter", new String[] {
            "ParamValue"
        }, "ParamKey=?", new String[] {
            "CurrConnectDeviceID"
        }, null, null, null);
        if (cursor.getCount() != 1)
        {
            Log.e("TC_Log", "SystemParameterDbUtil -- getCurrDeviceBindFromDB:currDeviceId error");
            cursor.close();
            return null;
        }
        cursor.moveToNext();
        int i = cursor.getInt(0);
        if (i <= 0)
        {
            Log.e("TC_Log", "SystemParameterDbUtil -- getCurrDeviceBindFromDB:currDeviceId<=0");
            cursor.close();
            return null;
        }
        cursor.close();
        SQLiteDatabase sqlitedatabase = db;
        String as[] = new String[1];
        as[0] = String.valueOf(i);
        Cursor cursor1 = sqlitedatabase.query("T_DeviceBinding", null, "ID=?", as, null, null, null);
        if (cursor1.getCount() != 1)
        {
            Log.e("TC_Log", "SystemParameterDbUtil -- getCurrDeviceBindFromDB:find muli-datas");
            cursor1.close();
            return null;
        }
        cursor1.moveToNext();
        DeviceBinding devicebinding = new DeviceBinding();
        devicebinding.setID(cursor1.getInt(cursor1.getColumnIndex("ID")));
        devicebinding.setDeviceSN(cursor1.getString(cursor1.getColumnIndex("DeviceSN")));
        devicebinding.setMac(cursor1.getString(cursor1.getColumnIndex("Mac")));
        devicebinding.setSensorId(cursor1.getString(cursor1.getColumnIndex("SensorID")));
        devicebinding.setPatientId(cursor1.getInt(cursor1.getColumnIndex("PatientID")));
        devicebinding.setBindingTime(AppUtil.string2Date(cursor1.getString(cursor1.getColumnIndex("BindingTime"))));
        boolean flag;
        if (cursor1.getInt(cursor1.getColumnIndex("CompletedFlag")) == 1)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        devicebinding.setCompletedFlag(flag);
        cursor1.close();
        return devicebinding;
    }
}
