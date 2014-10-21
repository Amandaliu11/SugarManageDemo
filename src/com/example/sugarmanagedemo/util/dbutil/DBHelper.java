package com.example.sugarmanagedemo.util.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    
    private Context mContext;
    public DBHelper(Context context,String s){
        this(context, s, 1);
        mContext = context;
    }
    
    public DBHelper(Context context, String s, int i)
    {
        this(context, s, null, i);
        mContext = context;
    }

    public DBHelper(Context context, String s, android.database.sqlite.SQLiteDatabase.CursorFactory cursorfactory, int i)
    {
        super(context, s, cursorfactory, i);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqlitedatabase) {
        //create table T_DeviceBinding
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE T_DeviceBinding(");
        stringBuilder.append("ID integer primary key autoincrement,");
        stringBuilder.append("DeviceSN varchar(20),");
        stringBuilder.append("Mac varchar(20),");
        stringBuilder.append("SensorID varchar(30),");
        stringBuilder.append("PatientID integer,");
        stringBuilder.append("BindingTime datetime,");
        stringBuilder.append("CompletedFlag integer)");
        sqlitedatabase.execSQL(stringBuilder.toString());
        
        //create table T_SystemParameter
        StringBuilder stringbuilder2 = new StringBuilder();
        stringbuilder2.append("CREATE TABLE T_SystemParameter(");
        stringbuilder2.append("ID integer primary key autoincrement,");
        stringbuilder2.append("ParamKey varchar(20),");
        stringbuilder2.append("ParamValue varchar(200))");
        sqlitedatabase.execSQL(stringbuilder2.toString());
        
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("ParamKey", "CurrConnectDeviceID");
        contentvalues.put("ParamValue", "");
        sqlitedatabase.insert("T_SystemParameter", null, contentvalues);
        contentvalues.clear();
        contentvalues.put("ParamKey", "CurrMonitorPatient");
        contentvalues.put("ParamValue", "");
        sqlitedatabase.insert("T_SystemParameter", null, contentvalues);
        contentvalues.clear();
        contentvalues.put("ParamKey", "HighGlucoseAlarm");
        contentvalues.put("ParamValue", "7.8");
        sqlitedatabase.insert("T_SystemParameter", null, contentvalues);
        contentvalues.clear();
        contentvalues.put("ParamKey", "LowGlucoseAlarm");
        contentvalues.put("ParamValue", "3.9");
        sqlitedatabase.insert("T_SystemParameter", null, contentvalues);
        contentvalues.clear();
        contentvalues.put("ParamKey", "GlucoseUnit");
        contentvalues.put("ParamValue", "mmol/L");
        sqlitedatabase.insert("T_SystemParameter", null, contentvalues);
        contentvalues.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
