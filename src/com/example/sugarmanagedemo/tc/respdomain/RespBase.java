package com.example.sugarmanagedemo.tc.respdomain;

import java.util.Calendar;

import com.example.sugarmanagedemo.tc.TcErrorStatus;
import com.example.sugarmanagedemo.util.AppUtil;

public class RespBase {
    private ResponseStatusEnums.CmdResponseStatus cmdRespStatus;
    private byte id;
    private TcErrorStatus tcErrorStatus;
    private ResponseStatusEnums.TcStatusOperation tcStatusOperation;

    protected RespBase(byte abyte0[])
    {
        id = abyte0[0];
        cmdRespStatus = getCmdRespStatusFromByte(abyte0[1]);
        tcErrorStatus = getTcErrorStatusFromByte(abyte0[1]);
        tcStatusOperation = getTcStatusOperationFromByte(abyte0[2]);
    }
    
    public ResponseStatusEnums.CmdResponseStatus getCmdRespStatus()
    {
      return this.cmdRespStatus;
    }
    
    /*
     * 鑾峰彇鎺ユ敹鐨勫懡浠ゅ簲绛旂姸鎬侊細bit0~bit2
     */
    protected ResponseStatusEnums.CmdResponseStatus getCmdRespStatusFromByte(byte paramByte)
    {
        switch (paramByte & 0x7)
        {      
          case 0: 
            return ResponseStatusEnums.CmdResponseStatus.Normal;  //鎺ユ敹鍛戒护姝ｅ父
          case 1: 
            return ResponseStatusEnums.CmdResponseStatus.SnNotMatching; //鎺ユ敹鍛戒护鐨凷N涓庢湰璁惧鐨凷N涓嶅尮閰�
          case 2: 
            return ResponseStatusEnums.CmdResponseStatus.IllegalPatientId;  //鎺ユ敹鍛戒护鐨勬偅鑰匢D閿欒
          case 3: 
            return ResponseStatusEnums.CmdResponseStatus.IllegalCommand;    //鎺ユ敹鍛戒护闈炴硶
          case 4: 
            return ResponseStatusEnums.CmdResponseStatus.IllegalParam;  //鎺ユ敹鍙傛暟闈炴硶
          case 5:
              return ResponseStatusEnums.CmdResponseStatus.NoPatientId; //鏃犳鎮ｈ�匢D
          default:
              return ResponseStatusEnums.CmdResponseStatus.Unrecognized;    //鏃犳硶璇嗗埆杩斿洖鐨勫懡浠ょ姸鎬�
        }
    }
    
    public byte getId()
    {
      return this.id;
    }
    
    protected byte[] getRange(byte[] paramArrayOfByte, int paramInt)
    {
      return getRange(paramArrayOfByte, paramInt, paramArrayOfByte.length - paramInt);
    }
    
    protected byte[] getRange(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      byte[] arrayOfByte = new byte[paramInt2];
      for (int i = 0;; i++)
      {
        if (i >= paramInt2) {
          return arrayOfByte;
        }
        arrayOfByte[i] = paramArrayOfByte[(paramInt1 + i)];
      }
    }
    
    public TcErrorStatus getTcErrorStatus()
    {
      return this.tcErrorStatus;
    }
    
    protected TcErrorStatus getTcErrorStatusFromByte(byte byte0)
    {
        boolean flag = true;
        TcErrorStatus tcerrorstatus = new TcErrorStatus();
        boolean flag1;
        boolean flag2;
        boolean flag3;
        boolean flag4;
        if ((0x1 & byte0 >> 7) == 1)
        {
            flag1 = flag;
        } else
        {
            flag1 = false;
        }
        tcerrorstatus.IsSystemError = flag1;
        if ((0x1 & byte0 >> 6) == 1)
        {
            flag2 = flag;
        } else
        {
            flag2 = false;
        }
        tcerrorstatus.NoState = flag2;
        if ((0x1 & byte0 >> 5) == 1)
        {
            flag3 = flag;
        } else
        {
            flag3 = false;
        }
        tcerrorstatus.IsMemoryFull = flag3;
        if ((0x1 & byte0 >> 4) == 1)
        {
            flag4 = flag;
        } else
        {
            flag4 = false;
        }
        tcerrorstatus.IsAbnormalSensor = flag4;
        if ((0x1 & byte0 >> 3) != 1)
        {
            flag = false;
        }
        tcerrorstatus.IsLowBattery = flag;
        return tcerrorstatus;
    }
    
    public ResponseStatusEnums.TcStatusOperation getTcStatusOperation()
    {
      return this.tcStatusOperation;
    }
    
    protected ResponseStatusEnums.TcStatusOperation getTcStatusOperationFromByte(byte paramByte)
    {
        switch (paramByte & 0xF)
        {
          case 0:
            return ResponseStatusEnums.TcStatusOperation.Idle;
          case 1: 
            return ResponseStatusEnums.TcStatusOperation.PolarizePreparing;
          case 2: 
            return ResponseStatusEnums.TcStatusOperation.Polarizing;
          case 3: 
            return ResponseStatusEnums.TcStatusOperation.Measuring;
          case 4:
              return ResponseStatusEnums.TcStatusOperation.MeasureFinished;
          default:
              return ResponseStatusEnums.TcStatusOperation.Unrecognized;
        }
      
    }
    
    protected Calendar intBytes2Date(byte[] paramArrayOfByte)
    {
      long l = 1000L * AppUtil.bytesToUint(paramArrayOfByte);
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTimeInMillis(l);
      return localCalendar;
    }
}
