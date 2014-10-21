/**
 * CommonUtils.java [v1.0.0]
 * classes: com.example.audiorecordingtest.utils.CommonUtils
 * Amanda Create at 2014骞�10鏈�11鏃� 涓嬪崍3:35:00
 * Copyright 闃冲厜鍋ュ悍淇℃伅鎶�鏈湁闄愬叕鍙�
 */
package com.example.sugarmanagedemo.utils;

import java.util.Locale;

/**
 * com.example.audiorecordingtest.utils.CommonUtils
 * @author Amanda
 * create at 2014骞�10鏈�11鏃� 涓嬪崍3:35:00
 */
public class CommonUtils {
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
    
    public static int byte2int(byte paramByte)
    {
      if (paramByte < 0) {
        paramByte &= 0xFF;
      }
      return paramByte;
    }
    
    public static byte[] shortToBytes(short value) {
        byte[] returnByteArray = new byte[2];
        returnByteArray[0] = (byte) (value & 0xff);
        returnByteArray[1] = (byte) ((value >>> 8) & 0xff);
        return returnByteArray;
    }
}
