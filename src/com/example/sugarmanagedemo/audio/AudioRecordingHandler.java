/**
 * AudioRecordingHandler.java [v1.0.0]
 * classes: com.example.audiorecordingtest.AudioRecordingHandler
 * Amanda Create at 2014骞�10鏈�10鏃� 涓嬪崍2:24:50
 * Copyright 闃冲厜鍋ュ悍淇℃伅鎶�鏈湁闄愬叕鍙�
 */
package com.example.sugarmanagedemo.audio;

/**
 * com.example.audiorecordingtest.AudioRecordingHandler
 * @author Amanda
 * create at 2014骞�10鏈�10鏃� 涓嬪崍2:24:50
 */
public interface AudioRecordingHandler {
    public void onFftDataCapture(byte[] bytes);
    public void onRecordSuccess();
    public void onRecordingError();
    public void onRecordSaveError();
}