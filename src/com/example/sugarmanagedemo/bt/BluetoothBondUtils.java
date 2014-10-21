package com.example.sugarmanagedemo.bt;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class BluetoothBondUtils {
    /**
     * 涓庤澶囬厤瀵� 鍙傝�冩簮鐮侊細platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public boolean createBond(Class btClass, BluetoothDevice btDevice)
            throws Exception
    {
        Method createBondMethod = btClass.getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }
 
    /**
     * 涓庤澶囪В闄ら厤瀵� 鍙傝�冩簮鐮侊細platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    static public boolean removeBond(Class btClass, BluetoothDevice btDevice)
            throws Exception
    {
        Method removeBondMethod = btClass.getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }
 
    static public boolean setPin(Class btClass, BluetoothDevice btDevice,
            String str) throws Exception
    {
        try
        {
            Method removeBondMethod = btClass.getDeclaredMethod("setPin", new Class[]{byte[].class});
            Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice,new Object[]{str.getBytes()});
//            Log.e("returnValue", "" + returnValue);
        }
        catch (SecurityException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
 
    }
 
    // 鍙栨秷鐢ㄦ埛杈撳叆
    static public boolean cancelPairingUserInput(Class btClass,
            BluetoothDevice device)
 
    throws Exception
    {
        Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
        // cancelBondProcess()
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }
 
    // 鍙栨秷閰嶅
    static public boolean cancelBondProcess(Class btClass,BluetoothDevice device)throws Exception
    {
        Method createBondMethod = btClass.getMethod("cancelBondProcess");
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }
    
    
    public static BluetoothSocket createInsecureRfcommSocketToServiceRecord(Class paramClass, BluetoothDevice paramBluetoothDevice, UUID paramUUID)
            throws Exception
          {
            Method localMethod = paramClass.getDeclaredMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
            if (localMethod != null) {
              return (BluetoothSocket)localMethod.invoke(paramBluetoothDevice, new Object[] { paramUUID });
            }
            return null;
          }
 
    /**
     * 
     * @param clsShow
     */
    static public void printAllInform(Class clsShow)
    {
        try
        {
            // 鍙栧緱鎵�鏈夋柟娉�
            Method[] hideMethod = clsShow.getMethods();
            int i = 0;
            for (; i < hideMethod.length; i++)
            {
//                Log.e("method name", hideMethod[i].getName() + ";and the i is:"
//                        + i);
            }
            // 鍙栧緱鎵�鏈夊父閲�
            Field[] allFields = clsShow.getFields();
            for (i = 0; i < allFields.length; i++)
            {
//                Log.e("Field name", allFields[i].getName());
            }
        }
        catch (SecurityException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    public static BluetoothSocket createRfcommSocket(Class paramClass, BluetoothDevice paramBluetoothDevice, int paramInt)
            throws Exception
          {
            Class[] arrayOfClass = new Class[1];
            arrayOfClass[0] = Integer.TYPE;
            Method localMethod = paramClass.getMethod("createRfcommSocket", arrayOfClass);
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(paramInt);
            return (BluetoothSocket)localMethod.invoke(paramBluetoothDevice, arrayOfObject);
          }
}
