package com.example.sugarmanagedemo.util.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtil {
    public static final Object dbLock = new Object();
    protected SQLiteDatabase db = null;
    protected DBHelper dbHelper = null;
    
    public DatabaseUtil(Context paramContext, boolean paramBoolean)
    {
      this.dbHelper = new DBHelper(paramContext, "SugarManager.db");
      if (paramBoolean)
      {
          this.db = this.dbHelper.getReadableDatabase();
      }else{
          this.db = this.dbHelper.getWritableDatabase();
      }
      
    }
    
    public void closeDB()
    {
      synchronized (dbLock)
      {
        this.dbHelper.close();
        this.db.close();
        return;
      }
    }
    
    protected int delete(String paramString1, String paramString2, String[] paramArrayOfString)
    {
      synchronized (dbLock)
      {
        int i = this.db.delete(paramString1, paramString2, paramArrayOfString);
        return i;
      }
    }
    
    protected void execSQL(String paramString)
    {
      synchronized (dbLock)
      {
        this.db.execSQL(paramString);
        return;
      }
    }
    
    protected long insert(String paramString1, String paramString2, ContentValues paramContentValues)
    {
      synchronized (dbLock)
      {
        long l = this.db.insert(paramString1, paramString2, paramContentValues);
        return l;
      }
    }
    
    protected int update(String paramString1, ContentValues paramContentValues, String paramString2, String[] paramArrayOfString)
    {
      synchronized (dbLock)
      {
        int i = this.db.update(paramString1, paramContentValues, paramString2, paramArrayOfString);
        return i;
      }
    }
}
