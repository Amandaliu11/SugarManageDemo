package com.example.sugarmanagedemo.tc;

import java.util.ArrayList;

import com.example.sugarmanagedemo.util.AppUtil;

public class Frame {
//  private final int OffsetAppData = 4;
//  private final int OffsetAppDataLength = 3;
//  private final int OffsetDeviceFrameId = 1;
//  private final int OffsetLinkId = 2;
//  private final int OffsetPcFrameId = 0;
  private ArrayList<Byte> appData;
  private byte deviceFrameId;
  private boolean isValid;
  private byte linkId;
  private ArrayList<Byte> mRawFramData;
  private byte pcFrameId;
  
  /*
   * 甯х粨鏋�
   * 鍓嶅悜甯у簭鍙�  鍚庡悜甯х粨鏋�  閾捐矾鍙�  鏁版嵁闀垮害  搴旂敤灞傛暟鎹�  妫�楠屽拰
   *   1Byte       1Byte      1Byte    1Byte    N Bytes     1Byte    
   */
  public Frame(byte paramByte1, byte paramByte2, byte paramByte3, ArrayList<Byte> paramArrayList)
  {
    this.pcFrameId = paramByte1;
    this.deviceFrameId = paramByte2;
    this.linkId = paramByte3;
    this.appData = paramArrayList;
    createFrameData();
    this.isValid = checkValid();
  }
  
  public Frame(ArrayList<Byte> paramArrayList)
  {
    this.mRawFramData = paramArrayList;
    this.pcFrameId = ((Byte)this.mRawFramData.get(0)).byteValue();
    this.deviceFrameId = ((Byte)this.mRawFramData.get(1)).byteValue();
    this.linkId = ((Byte)this.mRawFramData.get(2)).byteValue();
    if (checkValid())
    {
      int i = AppUtil.byte2int(((Byte)this.mRawFramData.get(3)).byteValue());
      this.appData = new ArrayList<Byte>(i);
      for (int j = 0;; j++)
      {
        if (j >= i)
        {
          this.isValid = true;
          return;
        }
        this.appData.add((Byte)paramArrayList.get(j + 4));
      }
    }
    this.appData = null;
    this.isValid = false;
  }
  
  private boolean checkValid()
  {
    int i = 0;
    for (int j = 0;; j++)
    {
      if (j >= this.mRawFramData.size())
      {
        if (i != 0) {
          break;
        }
        return true;
      }
      i = (byte)(i + ((Byte)this.mRawFramData.get(j)).byteValue());
    }
    return false;
  }
  
  //鏁版嵁閾捐矾灞傦細甯х粨鏋勬暟鎹�
  private void createFrameData()
  {
    ArrayList<Byte> localArrayList = new ArrayList<Byte>();
    localArrayList.add(Byte.valueOf(this.pcFrameId));
    localArrayList.add(Byte.valueOf(this.deviceFrameId));
    localArrayList.add(Byte.valueOf(this.linkId));
    if (this.appData != null)
    {
      localArrayList.add(Byte.valueOf(AppUtil.int2byte(this.appData.size())));
      localArrayList.addAll(this.appData);
    }
    
    localArrayList.add(Byte.valueOf(getCRC(localArrayList)));
    this.mRawFramData = localArrayList;
  }
  
  private byte getCRC(ArrayList<Byte> paramArrayList)
  {
    int i = 0;
    for (int j = 0;; j++)
    {
      if (j >= paramArrayList.size()) {
        return AppUtil.int2byte(0xFF & 1 + (i ^ 0xFFFFFFFF));
      }
      i += ((Byte)paramArrayList.get(j)).byteValue();
    }
  }
  
  public ArrayList<Byte> getAppData()
  {
    return this.appData;
  }
  
  public byte getDeviceFrameId()
  {
    return this.deviceFrameId;
  }
  
  public byte getLinkId()
  {
    return this.linkId;
  }
  
  public byte getPcFrameId()
  {
    return this.pcFrameId;
  }
  
  public ArrayList<Byte> getRawFramData()
  {
    return this.mRawFramData;
  }
  
  public boolean isValid()
  {
    return this.isValid;
  }


}
