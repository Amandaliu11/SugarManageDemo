package com.example.sugarmanagedemo.tc.respdomain;

public class ResponseStatusEnums {
   public enum CmdResponseStatus{
        IllegalCommand,
        IllegalParam,
        IllegalPatientId,
        NoPatientId,
        Normal,
        SnNotMatching,
        Unrecognized;
    }
    
   public enum TcStatusOperation{
        Idle,
        MeasureFinished,
        Measuring,
        PolarizePreparing,
        Polarizing,
        Unrecognized;
    }
}
