package com.example.sugarmanagedemo.tc;

import android.bluetooth.BluetoothDevice;

public class TCDevice {
    
    private final static String MIN_ADDR = "70F1A19C1A58";
    private final static String MAX_ADDR = "70F1A1AB5C98";
    public static boolean checkDevice(BluetoothDevice device){
        //device shouldn't be null
        if(null == device){
            return false;
        }
        
        //device's name shouldn't be null
        String name = device.getName();
        if(null == name){
            return false;
        }
        
        //device's name should start with "TC"
        if(!name.startsWith("TC")){
            return false;
        }
        
        //device's address should contains ":"
        String addr = device.getAddress();
        if(!addr.contains(":")){
            return false;
        }
        
        //device's address should between 70:F1:A1:9C:1A:58 to 70:F1:A1:AB:5C:98
        String addrNoColon = addr.replace(":", "");
        long addrL = Long.parseLong(addrNoColon, 16);
        long addMin = Long.parseLong(MIN_ADDR,16);
        long addMax = Long.parseLong(MAX_ADDR, 16);
        
        if(addrL < addMin){
            return false;
        }
        
        if(addrL > addMax){
            return false;
        }
        
        return true;
        
    }


}
