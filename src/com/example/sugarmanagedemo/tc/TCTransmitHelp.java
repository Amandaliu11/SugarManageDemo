package com.example.sugarmanagedemo.tc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.util.Log;

import com.example.sugarmanagedemo.util.AppUtil;

public class TCTransmitHelp {
    private InputStream in;
    private byte m_deviceFrameId = 1;
    private byte m_linkId = 1;
    private byte m_pcFrameId = 1;
    private OutputStream out;
    
    public TCTransmitHelp() {}
    
    public TCTransmitHelp(InputStream paramInputStream, OutputStream paramOutputStream)
    {
      this.in = paramInputStream;
      this.out = paramOutputStream;
    }
    
    private ArrayList<Byte> readResponseBytes()
      throws IOException
    {
        ArrayList<Byte> localArrayList = new ArrayList<Byte>();
        int numByte = 0;
        int numVaules = 0;
        
        boolean isStillRead = true;
        while(isStillRead){
            localArrayList.add(Byte.valueOf(AppUtil.int2byte(this.in.read())));
            numByte++;
            
            if(numByte >= 4){
                numVaules = AppUtil.byte2int(((Byte)localArrayList.get(3)).byteValue());
                
                if(numByte == 5+numVaules){
                    isStillRead = false;
                }
            }
        }
        
        Log.i("cc", "recv:" + AppUtil.bytes2HexString(localArrayList));
        return localArrayList;
    }
    
    public ArrayList<Byte> communicate(ArrayList<Byte> paramArrayList, boolean paramBoolean)throws IOException
    {
        Frame sendFrame = null;
        if(paramBoolean){
            sendFrame = new Frame(this.m_pcFrameId, this.m_deviceFrameId, this.m_linkId, paramArrayList);
        }else{
            sendFrame = new Frame(this.m_pcFrameId, this.m_deviceFrameId, this.m_linkId, null);
        }
        
        byte[] arrayOfByte = AppUtil.getByteArrayFromByteList(sendFrame.getRawFramData());
        Log.i("cc", "send:" + AppUtil.bytes2HexString(arrayOfByte));
        this.out.write(arrayOfByte);
        
        Frame recvFrame = new Frame(readResponseBytes());
        if(recvFrame.isValid()){
            return recvFrame.getAppData();
        }else{
            return null;
        }
    }
    
    public ArrayList<Byte> communicate(byte[] paramArrayOfByte, boolean paramBoolean) throws IOException{
        return communicate(AppUtil.getByteListFromByteArray(paramArrayOfByte), paramBoolean);
    }
    
    public void setIn(InputStream paramInputStream)
    {
      this.in = paramInputStream;
    }
    
    public void setOut(OutputStream paramOutputStream)
    {
      this.out = paramOutputStream;
    }
}
