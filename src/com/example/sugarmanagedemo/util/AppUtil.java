package com.example.sugarmanagedemo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.util.Log;

public class AppUtil {
    public static int byte2int(byte paramByte)
    {
      if (paramByte < 0) {
        paramByte &= 0xFF;
      }
      return paramByte;
    }
    
    public static byte int2byte(int paramInt)
    {
      return (byte)(paramInt & 0xFF);
    }
    
    public static String bytes2HexString(List<Byte> paramList)
    {
      StringBuilder localStringBuilder = new StringBuilder("");
      for (int i = 0;; i++)
      {
        if (i >= paramList.size()) {
          return localStringBuilder.toString();
        }
        String str = Integer.toHexString(0xFF & ((Byte)paramList.get(i)).byteValue());
        if (str.length() == 1) {
          str = '0' + str;
        }
        localStringBuilder.append(str.toUpperCase(Locale.ENGLISH) + " ");
      }
    }
    
    public static String bytes2HexString(byte[] paramArrayOfByte)
    {
      StringBuilder localStringBuilder = new StringBuilder("");
      for (int i = 0;; i++)
      {
        if (i >= paramArrayOfByte.length) {
          return localStringBuilder.toString();
        }
        String str = Integer.toHexString(0xFF & paramArrayOfByte[i]);
        if (str.length() == 1) {
          str = '0' + str;
        }
        localStringBuilder.append(str.toUpperCase(Locale.ENGLISH) + " ");
      }
    }
    
    public static byte[] getByteArrayFromByteList(List<Byte> paramList)
    {
      byte[] arrayOfByte = new byte[paramList.size()];
      for (int i = 0;; i++)
      {
        if (i >= paramList.size()) {
          return arrayOfByte;
        }
        arrayOfByte[i] = ((Byte)paramList.get(i)).byteValue();
      }
    }
    
    public static ArrayList<Byte> getByteListFromByteArray(byte[] paramArrayOfByte)
    {
      ArrayList<Byte> localArrayList = new ArrayList<Byte>(paramArrayOfByte.length);
      for (int i = 0;; i++)
      {
        if (i >= paramArrayOfByte.length) {
          return localArrayList;
        }
        localArrayList.add(Byte.valueOf(paramArrayOfByte[i]));
      }
    }
    
    public static String date2String(Calendar paramCalendar)
    {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramCalendar.getTime());
    }
    
    public static String date2String(Date paramDate)
    {
      if (paramDate == null) {
        return "";
      }
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramDate);
    }
    
    public static int bytesToUint(byte abyte0[])
    {
        int value = 0;    
        for(int i = 0 ; i<abyte0.length;++i){
            value |= ((abyte0[i] & 0xFF) << (8* (abyte0.length - i-1)));
        }
        
        
//        value = (int) ( ((src[0] & 0xFF)<<24)  
//                |((src[1] & 0xFF)<<16)  
//                |((src[2] & 0xFF)<<8)  
//                |(src[3] & 0xFF));  
        return value;
    }
    
    public static byte[] uintToBytes(int value)   
    {   
        return uintToBytes(value,4);
    }
    
    public static byte[] uintToBytes(int paramInt1, int paramInt2)
    {
        byte[] arrayOfByte = new byte[paramInt2];
        for (int i = 0;; i++)
        {
          if (i >= paramInt2) {
            return arrayOfByte;
          }
          arrayOfByte[i] = ((byte)(0xFF & paramInt1 >> 8 * (paramInt2 - 1 - i)));
        }
    }
    
    public static byte[] getRange(byte abyte0[], int i)
    {
        return getRange(abyte0, i, abyte0.length - i);
    }

    public static byte[] getRange(byte abyte0[], int i, int j)
    {
        byte abyte1[] = new byte[j];
        int k = 0;
        do
        {
            if (k >= j)
            {
                return abyte1;
            }
            abyte1[k] = abyte0[i + k];
            k++;
        } while (true);
    }
    
    private final static int LENGTH_MOBILE_PHONE = 12;
    public static byte[] mobilePhone2BCDBytes(String paramString)
    {
        StringBuilder mZero = new StringBuilder("0");
        StringBuilder mBuilder = new StringBuilder(paramString);
        if(paramString.length()<LENGTH_MOBILE_PHONE){
            for(int i=0; i<(LENGTH_MOBILE_PHONE-paramString.length());++i){
                mBuilder = mZero.append(mBuilder);
            }
        }else{
            mBuilder = new StringBuilder(mBuilder.substring(0,LENGTH_MOBILE_PHONE));
        }
        
        byte[] arrayOfByte = new byte[LENGTH_MOBILE_PHONE/2];
        for(int i = 0; i<LENGTH_MOBILE_PHONE/2;++i){
            arrayOfByte[i] = decade2BCDByte(mBuilder.substring(i * 2, 2 + i * 2));
        }
        
        return arrayOfByte;
    }
    
    public static byte decade2BCDByte(int paramInt)
    {
      return (byte)((byte)(paramInt % 10) | (byte)(paramInt / 10 % 10) << 4);
    }
    
    public static byte decade2BCDByte(String paramString)
    {
      return decade2BCDByte(Integer.valueOf(paramString).intValue());
    }
    
    public static Date string2Date(String paramString)
    {
      if ((paramString == null) || ("".equals(paramString))) {
        return null;
      }
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try
      {
        Date localDate = localSimpleDateFormat.parse(paramString);
        return localDate;
      }
      catch (ParseException localParseException)
      {
        Log.e("TC_Log", "AppUtil -- 字符串解析日期是出错", localParseException);
      }
      return null;
    }
    
    public static String sec2Str(long seconds){
        StringBuilder mResult = new StringBuilder();
      
        int h,m,s = 0;
        int tmp = 0;
          
        s = (int)seconds%60;
        tmp = (int)seconds/60;
          
        m = tmp %60;
        h = tmp/60;
          
        if(h<10){
            mResult.append("0"+h+":");
        }else{
            mResult.append(h+":");
        }
          
          if(m<10){
              mResult.append("0"+m+":");
          }else{
              mResult.append(m+":");
          }
          
          if(s<10){
              mResult.append("0"+s);
          }else{
              mResult.append(""+s);
          }
          
          return mResult.toString();
    }  
}
