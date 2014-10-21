/**
 * StorageUtils.java [v1.0.0]
 * classes: com.example.audiorecordingtest.utils.StorageUtils
 * Amanda Create at 2014骞�10鏈�10鏃� 涓嬪崍2:40:48
 * Copyright 闃冲厜鍋ュ悍淇℃伅鎶�鏈湁闄愬叕鍙�
 */
package com.example.sugarmanagedemo.utils;

import android.content.Context;
import android.os.Environment;

/**
 * com.example.audiorecordingtest.utils.StorageUtils
 * @author Amanda
 * create at 2014骞�10鏈�10鏃� 涓嬪崍2:40:48
 */
public class StorageUtils {
    private static final String AUDIO_FILE_MA3_NAME = "audiorecordtest.mp3";
    private static final String AUDIO_FILE_WMV_NAME = "audiorecordtest.wmv";
    public static boolean checkExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public static String getMP3FileName(Context context) {
        if(checkExternalStorageAvailable()){
            String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            return String.format("%s/%s", storageDir, AUDIO_FILE_MA3_NAME);
        }else {
            String storageDir = context.getFilesDir().getAbsolutePath();
            return String.format("%s/%s", storageDir, AUDIO_FILE_MA3_NAME);
        }
    }
    
    public static String getWmvFileName(Context context) {
        if(checkExternalStorageAvailable()){
            String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            return String.format("%s/%s", storageDir, AUDIO_FILE_WMV_NAME);
        }else {
            String storageDir = context.getFilesDir().getAbsolutePath();
            return String.format("%s/%s", storageDir, AUDIO_FILE_WMV_NAME);
        }
    }
}
