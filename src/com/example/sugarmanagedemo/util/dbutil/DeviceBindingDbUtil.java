package com.example.sugarmanagedemo.util.dbutil;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sugarmanagedemo.domain.DeviceBinding;
import com.example.sugarmanagedemo.util.AppUtil;

public class DeviceBindingDbUtil extends DatabaseUtil
{

 public DeviceBindingDbUtil(Context context, boolean flag)
 {
     super(context, flag);
 }


 private ContentValues getDeviceContent(DeviceBinding devicebinding)
 {
     ContentValues contentvalues = new ContentValues();
     if (devicebinding.getID() > 0)
     {
         contentvalues.put("ID", Integer.valueOf(devicebinding.getID()));
     }
     if (devicebinding.getDeviceSN() != null)
     {
         contentvalues.put("DeviceSN", devicebinding.getDeviceSN());
     }
     if (devicebinding.getMac() != null)
     {
         contentvalues.put("Mac", devicebinding.getMac());
     }
     if (devicebinding.getSensorId() != null)
     {
         contentvalues.put("SensorID", devicebinding.getSensorId());
     }
     if (devicebinding.getPatientId() > 0)
     {
         contentvalues.put("PatientID", Integer.valueOf(devicebinding.getPatientId()));
     }
     if (devicebinding.getBindingTime() != null)
     {
         contentvalues.put("BindingTime", AppUtil.date2String(devicebinding.getBindingTime()));
     }
     if (devicebinding.isCompletedFlag())
     {
         contentvalues.put("CompletedFlag", Integer.valueOf(1));
         return contentvalues;
     } else
     {
         contentvalues.put("CompletedFlag", Integer.valueOf(0));
         return contentvalues;
     }
 }


 public boolean addDeviceBind(DeviceBinding paramDeviceBinding)
 {
     try
     {
       insert("T_DeviceBinding", null, getDeviceContent(paramDeviceBinding));
       Cursor localCursor = this.db.query("T_DeviceBinding", new String[] { "max(ID)" }, null, null, null, null, null);
       if (localCursor.getCount() != 1)
       {
         Log.e("TC_Log", "DeviceBindingDbUtil -- get new DeviceBind ID error");
         return false;
       }
       localCursor.moveToNext();
       int i = localCursor.getInt(0);
       paramDeviceBinding.setID(i);
       localCursor.close();
       Log.d("TC_Log", "DeviceBindingDbUtil -- add Device bind success:" + paramDeviceBinding.getDeviceSN() + ",id=" + i);
       return true;
     }
     catch (Exception localException)
     {
       Log.e("TC_Log", "DeviceBindingDbUtil -- add Device bind error:" + paramDeviceBinding.getDeviceSN(), localException);
     }
     return false;
 }


 public DeviceBinding getDeviceBindById(int i)
 {
     SQLiteDatabase sqlitedatabase = db;
     String as[] = new String[1];
     as[0] = String.valueOf(i);
     Cursor cursor = sqlitedatabase.query("T_DeviceBinding", null, "ID=?", as, null, null, null);
     if (cursor.getCount() != 1)
     {
         Log.e("TC_Log", "DeviceBindingDbUtil -- getDeviceBindById:find muli-datas");
         return null;
     }
     cursor.moveToNext();
     DeviceBinding devicebinding = new DeviceBinding();
     devicebinding.setID(cursor.getInt(cursor.getColumnIndex("ID")));
     devicebinding.setDeviceSN(cursor.getString(cursor.getColumnIndex("DeviceSN")));
     devicebinding.setMac(cursor.getString(cursor.getColumnIndex("Mac")));
     devicebinding.setSensorId(cursor.getString(cursor.getColumnIndex("SensorID")));
     devicebinding.setPatientId(cursor.getInt(cursor.getColumnIndex("PatientID")));
     devicebinding.setBindingTime(AppUtil.string2Date(cursor.getString(cursor.getColumnIndex("BindingTime"))));
     boolean flag;
     if (cursor.getInt(cursor.getColumnIndex("CompletedFlag")) == 1)
     {
         flag = true;
     } else
     {
         flag = false;
     }
     devicebinding.setCompletedFlag(flag);
     cursor.close();
     return devicebinding;
 }
 
 private boolean hasGlucoseDataByBindingId(int paramInt1, int paramInt2)
 {
   SQLiteDatabase localSQLiteDatabase = this.db;
   String[] arrayOfString1 = { "count(*)" };
   String[] arrayOfString2 = new String[3];
   arrayOfString2[0] = String.valueOf(paramInt1);
   arrayOfString2[1] = String.valueOf(paramInt2);
   arrayOfString2[2] = String.valueOf(0);
   Cursor localCursor = localSQLiteDatabase.query("T_AverageGlucose", arrayOfString1, "PatientID=? and BindID=? and GlucoseValue>?", arrayOfString2, null, null, "GlucoseTime");
   int i = localCursor.getCount();
   localCursor.close();
   Log.i("TC_Log", "DeviceBindingDbUtil -- hasGlucoseDataByBindingId: search glucose count = " + i);
   return i > 0;
 }

 public ArrayList<DeviceBinding> getDeviceBindByPatientId(int i)
 {
     SQLiteDatabase sqlitedatabase = db;
     String as[] = new String[1];
     as[0] = String.valueOf(i);
     Cursor cursor = sqlitedatabase.query("T_DeviceBinding", null, "PatientID=?", as, null, null, null);
     if (cursor.getCount() <= 0)
     {
         Log.w("TC_Log", (new StringBuilder("DeviceBindingDbUtil -- getDeviceBindByPatientId:find no deviceBinding data for PatientId=")).append(i).toString());
         cursor.close();
         return null;
     }
     ArrayList<DeviceBinding> arraylist = new ArrayList<DeviceBinding>();
     do
     {
         int j;
         do
         {
             if (!cursor.moveToNext())
             {
                 cursor.close();
                 return arraylist;
             }
             j = cursor.getInt(cursor.getColumnIndex("ID"));
         } while (!hasGlucoseDataByBindingId(i, j));
         DeviceBinding devicebinding = new DeviceBinding();
         devicebinding.setID(j);
         devicebinding.setDeviceSN(cursor.getString(cursor.getColumnIndex("DeviceSN")));
         devicebinding.setMac(cursor.getString(cursor.getColumnIndex("Mac")));
         devicebinding.setSensorId(cursor.getString(cursor.getColumnIndex("SensorID")));
         devicebinding.setPatientId(cursor.getInt(cursor.getColumnIndex("PatientID")));
         devicebinding.setBindingTime(AppUtil.string2Date(cursor.getString(cursor.getColumnIndex("BindingTime"))));
         boolean flag;
         if (cursor.getInt(cursor.getColumnIndex("CompletedFlag")) == 1)
         {
             flag = true;
         } else
         {
             flag = false;
         }
         devicebinding.setCompletedFlag(flag);
         arraylist.add(devicebinding);
     } while (true);
 }

 public DeviceBinding getDeviceBindByTime(String s, int i)
 {
     SQLiteDatabase sqlitedatabase = db;
     String as[] = new String[2];
     as[0] = s;
     as[1] = String.valueOf(i);
     Cursor cursor = sqlitedatabase.query("T_DeviceBinding", null, "BindingTime=? and PatientID=?", as, null, null, null);
     if (cursor.getCount() > 1)
     {
         Log.e("TC_Log", "DeviceBindingDbUtil -- getDeviceBindByTime:find muli-data");
         cursor.close();
         return null;
     }
     if (cursor.getCount() == 0)
     {
         Log.w("TC_Log", "DeviceBindingDbUtil -- getDeviceBindByTime:find no deviceBinding data");
         cursor.close();
         return null;
     }
     cursor.moveToNext();
     DeviceBinding devicebinding = new DeviceBinding();
     devicebinding.setID(cursor.getInt(cursor.getColumnIndex("ID")));
     devicebinding.setDeviceSN(cursor.getString(cursor.getColumnIndex("DeviceSN")));
     devicebinding.setMac(cursor.getString(cursor.getColumnIndex("Mac")));
     devicebinding.setSensorId(cursor.getString(cursor.getColumnIndex("SensorID")));
     devicebinding.setPatientId(cursor.getInt(cursor.getColumnIndex("PatientID")));
     devicebinding.setBindingTime(AppUtil.string2Date(cursor.getString(cursor.getColumnIndex("BindingTime"))));
     boolean flag;
     if (cursor.getInt(cursor.getColumnIndex("CompletedFlag")) == 1)
     {
         flag = true;
     } else
     {
         flag = false;
     }
     devicebinding.setCompletedFlag(flag);
     cursor.close();
     return devicebinding;
 }

 public DeviceBinding getLastDeviceBind(int i)
 {
     SQLiteDatabase sqlitedatabase = db;
     String as[] = {
         "max(BindingTime)"
     };
     String as1[] = new String[1];
     as1[0] = String.valueOf(i);
     Cursor cursor = sqlitedatabase.query("T_DeviceBinding", as, "PatientID", as1, null, null, null);
     if (cursor.moveToNext())
     {
         String s = cursor.getString(0);
         Log.e("TC_Log", (new StringBuilder("DeviceBindingDbUtil -- getLastDeviceBind:")).append(s).toString());
         cursor.close();
         return getDeviceBindByTime(s, i);
     } else
     {
         Log.w("TC_Log", "DeviceBindingDbUtil -- getLastDeviceBind: no deviceBinding data");
         cursor.close();
         return null;
     }
 }


 public boolean updateCurrBindDeviceID(int i)
 {
     try
     {
         ContentValues contentvalues = new ContentValues();
         contentvalues.put("ParamValue", String.valueOf(i));
         update("T_SystemParameter", contentvalues, "ParamKey=?", new String[] {
             "CurrConnectDeviceID"
         });
         
         Log.d("TC_Log", (new StringBuilder("DeviceBindingDbUtil -- updateCurrBindDeviceID success,id:")).append(i).toString());
     }
     catch (Exception exception)
     {
         Log.e("TC_Log", (new StringBuilder("DeviceBindingDbUtil -- updateCurrBindDeviceID failed,id:")).append(i).toString(), exception);
         return false;
     }
     return true;
 }
}
