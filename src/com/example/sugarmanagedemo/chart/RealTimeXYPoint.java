package com.example.sugarmanagedemo.chart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RealTimeXYPoint {
    private Date mX;
    private double mY;
    
    public RealTimeXYPoint(){
        mX = new java.util.Date();
        mY = 0.0D;
    }
    
    public RealTimeXYPoint(long time, double sugarValue){
        mX = long2Date(time);
        mY = sugarValue;
    }
    
    public RealTimeXYPoint(Date time, double sugarValue){
        mX = time;
        mY = sugarValue;
    }    
    
    public Date getXValue(){
        return mX;
    }
    
    public double getYValue(){
        return mY;
    }
    
    
    public static Date long2Date(long time){
        return new java.util.Date(time*1000);
    }
    
    
    public static Long date2Long(Date time){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        try {
            return (simpleDateFormat.parse(simpleDateFormat.format(time))).getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return 0L;
    }
}
