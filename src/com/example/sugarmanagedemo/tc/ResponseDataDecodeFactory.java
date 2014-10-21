package com.example.sugarmanagedemo.tc;

import java.io.UnsupportedEncodingException;

import com.example.sugarmanagedemo.tc.respdomain.RespBase;
import com.example.sugarmanagedemo.tc.respdomain.RespDataTransfer;
import com.example.sugarmanagedemo.tc.respdomain.RespGetTcParams;
import com.example.sugarmanagedemo.tc.respdomain.RespGetTcPatientInfo;

public class ResponseDataDecodeFactory {
    public static RespBase getRespDomain(byte[] paramArrayOfByte) throws UnsupportedEncodingException{
        switch (paramArrayOfByte[0]){
        case CommandIds.responseIdGetTcParams:
            return new RespGetTcParams(paramArrayOfByte);
        case CommandIds.responseIdGetTcPatientInfo:
            return new RespGetTcPatientInfo(paramArrayOfByte);
        case CommandIds.responseIdDataTransfer:
            return new RespDataTransfer(paramArrayOfByte);
        default: 
            return null;
        }
    }
}
