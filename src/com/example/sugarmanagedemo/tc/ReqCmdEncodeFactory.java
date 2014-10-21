package com.example.sugarmanagedemo.tc;

import java.util.Calendar;
import java.util.TimeZone;

import com.example.sugarmanagedemo.util.AppUtil;

/*                          
鍛戒护缂栫爜锛堝崄杩涘埗锛�   
    閰嶅鍛戒护                        51(deprecated)
    閰嶅搴旂瓟                        52(deprecated)
    鍙栧緱鍙傛暟鍛戒护              53  
    鍙栧緱鍙傛暟搴旂瓟              54  
    鍙栧緱娴嬮噺鎮ｈ�呬俊鎭�        55  
    鍙栧緱淇℃伅搴旂瓟              56  
    閰嶇疆淇℃伅鍛戒护              57  
    閰嶇疆淇℃伅搴旂瓟              58  
    娴嬮噺寮�濮嬪懡浠�              59(deprecated)  
    娴嬮噺寮�濮嬪簲绛�            60(deprecated)    
    娴嬮噺缁撴潫鍛戒护              61  
    娴嬮噺缁撴潫搴旂瓟              62  
    娴嬮噺鏁版嵁浼犻��              63  
    鏁版嵁搴旂瓟                        64  
    娓呴櫎鍛戒护                        65  
    娓呴櫎搴旂瓟                        66  
    娓呴櫎纭                        67  
    娴嬭瘯鍛戒护                        68(deprecated)  
    娴嬭瘯搴旂瓟                        69(deprecated)  
    鐘舵�佸懡浠�                        70(deprecated)  
    鐘舵�佸簲绛�                        71(deprecated)  
    鏂紑鍛戒护                        72  
    鏂紑搴旂瓟                        73  
    鍘嗗彶浼犻�佸紑濮嬪懡浠�        74(deprecated)  
    鍘嗗彶浼犻�佸紑濮嬪簲绛�        75(deprecated)  
    鍘嗗彶浼犻�佺粨鏉熷懡浠�        76(deprecated)  
    鍘嗗彶浼犻�佺粨鏉熷簲绛�        77(deprecated)  
    璇诲彇TC RTC                    78(deprecated)  
    璁剧疆TC RTC                    79(deprecated)  
    PC鍏虫満鍛戒护                  80  
    PC鍏虫満搴旂瓟                  81  
    鍙栧緱鍘嗗彶鎮ｈ�呬俊鎭�        82  
    鍘嗗彶鏁版嵁浼犻��              83
*/  


//璇锋眰鍛戒护
public class ReqCmdEncodeFactory
{
    //褰撳墠鏃堕棿
    private static byte[] date2IntByteArray()
    {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        localCalendar.set(1970, 0, 1, 0, 0, 0);
        return AppUtil.uintToBytes((int)((Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis() - localCalendar.getTimeInMillis()) / 1000L), 4);
    }
    
    //娓呴櫎鐢ㄦ埛淇℃伅
    public static byte[] getReqClearTcPatientInfoCmd(byte paramByte)
    {
        return new byte[] { CommandIds.requestIdClearTcPatientInfo, paramByte };
    }
    
    //纭娓呴櫎鐢ㄦ埛淇℃伅
    public static byte[] getReqConfirmClearTcPatientInfoCmd(byte paramByte)
    {
        return new byte[] { CommandIds.requsetIdConfirmClearTcPatientInfo, paramByte };
    }
    
    /*
    * 娴嬭瘯鏁版嵁鍙戦��
    * 鍛戒护+paramInt1+paramInt2
    * @paramInt1 鎮ｈ�匢D
    * @paramInt2 鏁版嵁搴忓彿
    * @return
    */
    public static byte[] getReqDataTransferCmd(int paramInt1, int paramInt2)
    {
        byte[] arrayOfByte1 = new byte[9];
        arrayOfByte1[0] = CommandIds.requestIdDataTransfer;
        byte[] arrayOfByte2 = AppUtil.uintToBytes(paramInt1, 4);
        byte[] arrayOfByte3 = AppUtil.uintToBytes(paramInt2, 4);
        //System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length);
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 1, arrayOfByte2.length);
        System.arraycopy(arrayOfByte3, 0, arrayOfByte1, 1 + arrayOfByte2.length, arrayOfByte3.length);
        return arrayOfByte1;
    }
    
    //鏂紑杩炴帴
    public static byte[] getReqDisconnectLinkCmd()
    {
        return new byte[] { CommandIds.requestIdDisconnectLink };
    }
    
    //缁撴潫娴嬮噺
    public static byte[] getReqEndMonitorCmd(int paramInt)
    {
        byte[] arrayOfByte1 = new byte[5];
        arrayOfByte1[0] = CommandIds.requestIdEndMonitor;
        byte[] arrayOfByte2 = AppUtil.uintToBytes(paramInt, 4);
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 1, arrayOfByte2.length);
        return arrayOfByte1;
    }
    
    //鍙栧緱鍘嗗彶鎮ｈ�呬俊鎭�
    public static byte[] getReqGetHistoryPatientInfoCmd(byte paramByte)
    {
        return new byte[] { CommandIds.requestIdGetHistoryPatientInfo, paramByte };
    }
    
    //鍙栧緱鍙傛暟
    public static byte[] getReqGetTcParamsCmd()
    {
        return new byte[] { CommandIds.requestIdGetTcParams };
    }
    
    //鍙栧緱鐢ㄦ埛淇℃伅
    public static byte[] getReqGetTcPatientInfoCmd(int paramInt)
    {
        byte[] arrayOfByte1 = new byte[5];
        arrayOfByte1[0] = CommandIds.requestIdGetTcPatientInfo;
        byte[] arrayOfByte2 = AppUtil.uintToBytes(paramInt, 4);
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 1, arrayOfByte2.length);
        return arrayOfByte1;
    }
    
    //鍙戦�佸巻鍙叉暟鎹�
    public static byte[] getReqHistoryDataTransferCmd(byte paramByte, int paramInt)
    {
        byte[] arrayOfByte1 = new byte[6];
        arrayOfByte1[0] = CommandIds.requestIdHistoryDataTransfer;
        arrayOfByte1[1] = paramByte;
        byte[] arrayOfByte2 = AppUtil.uintToBytes(paramInt, 4);
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 2, arrayOfByte2.length);
        return arrayOfByte1;
    }
    
    //PC鍏虫満
    public static byte[] getReqPhoneShutdownCmd()
    {
        return new byte[] { CommandIds.requestIdPhoneShutdown };
    }
    
    //閰嶇疆淇℃伅
    public static byte[] getReqSetTcPatientInfoCmd(int paramInt)
    {
        byte[] arrayOfByte1 = new byte[9];
        arrayOfByte1[0] = CommandIds.requestIdSetTcPatientInfo;
        byte[] arrayOfByte2 = AppUtil.uintToBytes(paramInt, 4);
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 1, arrayOfByte2.length);
        byte[] arrayOfByte3 = date2IntByteArray();
        System.arraycopy(arrayOfByte3, 0, arrayOfByte1, 1 + arrayOfByte2.length, arrayOfByte3.length);
        return arrayOfByte1;
    }
    
    

}
