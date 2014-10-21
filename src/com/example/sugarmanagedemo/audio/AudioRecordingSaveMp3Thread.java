/**
 * AudioRecordingThread.java [v1.0.0]
 * classes: com.example.audiorecordingtest.AudioRecordingThread
 * Amanda Create at 2014骞�10鏈�10鏃� 涓嬪崍2:23:10
 * Copyright 闃冲厜鍋ュ悍淇℃伅鎶�鏈湁闄愬叕鍙�
 */
package com.example.sugarmanagedemo.audio;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.example.sugarmanagedemo.audio.fft.Complex;
import com.example.sugarmanagedemo.audio.fft.FFT;
import com.example.sugarmanagedemo.utils.CommonUtils;
import com.pocketdigi.utils.FLameUtils;

/**
 * com.example.audiorecordingtest.AudioRecordingThread
 * @author Amanda
 * create at 2014骞�10鏈�10鏃� 涓嬪崍2:23:10
 */
public class AudioRecordingSaveMp3Thread extends Thread{
    private static final String FILE_NAME = "audiorecordtest.raw";
    private static final int SAMPLING_RATE = 44100;
    private static final int MAGIC_SCALE = 10;
    private static final int FFT_POINTS = 2;

    private String fileName_raw;
    private String fileName_mp3;
    
    private int bufferSize;
    private short[] audioShortBuffer;
    
    private boolean isRecording = true;

    private AudioRecordingHandler handler = null;
    
    
    public AudioRecordingSaveMp3Thread(String fileMp3Name, AudioRecordingHandler handler) {
        this.fileName_mp3 = fileMp3Name;
        this.fileName_raw = getRawName(fileName_mp3);
        this.handler = handler;
        
        bufferSize = AudioRecord.getMinBufferSize(SAMPLING_RATE,
                                                  AudioFormat.CHANNEL_IN_MONO,
                                                  AudioFormat.ENCODING_PCM_16BIT);
        audioShortBuffer = new short[bufferSize];
        
       
  
    }
    
    @Override
    public void run() {
        DataOutputStream output = prepareWriting();        
        if (output == null){
            return;
        }
        
        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, /*AudioSource.VOICE_RECOGNITION*/
                                             SAMPLING_RATE,
                                             AudioFormat.CHANNEL_IN_MONO,
                                             AudioFormat.ENCODING_PCM_16BIT,
                                             bufferSize);
        record.startRecording();    
        
        int read = 0;
        while (isRecording) {
            read = record.read(audioShortBuffer, 0, audioShortBuffer.length);
            
            if ((read == AudioRecord.ERROR_INVALID_OPERATION) || 
                (read == AudioRecord.ERROR_BAD_VALUE) ||
                (read <= 0)) {
                continue;
            }
            
            proceed();
            
            for (int i = 0; i < read; i++) {
                try {
                    output.writeShort(audioShortBuffer[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
          
        record.stop();
        record.release();        
        finishWriting(output);
        
        convertRawToMp3();
    }
    
    

    
    private void proceed() {
        if(audioShortBuffer.length <FFT_POINTS){
            return;
        }
        
        double temp;
        Complex[] y;
        Complex[] complexSignal = new Complex[FFT_POINTS];
        
        for(int i = 0; i<FFT_POINTS; ++i){
            byte[] audioBuffer = CommonUtils.shortToBytes(audioShortBuffer[i]);
            temp = (double)((audioBuffer[0] & 0xFF) | ((audioBuffer[1]& 0xFF) << 8)) / 32768.0F;
            complexSignal[i] = new Complex(temp * MAGIC_SCALE, 0d);
        }        
        y = FFT.fft(complexSignal);
        
        /*
         * See http://developer.android.com/reference/android/media/audiofx/Visualizer.html#getFft(byte[]) for format explanation
         */
        
        final byte[] y_byte = new byte[y.length*2];
        y_byte[0] = (byte) y[0].re();
        y_byte[1] = (byte) y[y.length - 1].re();
        for (int i = 1; i < y.length - 1; i++) {
            y_byte[i*2]   = (byte) y[i].re();
            y_byte[i*2+1] = (byte) y[i].im();
        }
        
        if (handler != null) {
            handler.onFftDataCapture(y_byte);
        }
    }
    
    private DataOutputStream prepareWriting(){
        File file = new File(fileName_raw);
        if (file.exists()) { 
            file.delete();
        }
        
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName_raw, true)));                     
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (handler != null) {
                handler.onRecordingError();
            }
        }
        
        return output;
    }
    

    
    private void finishWriting(DataOutputStream output){
        if(null == output){
            return;
        }
        
        try {
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
            if (handler != null) {
                handler.onRecordingError();
            }
        }
        
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            if (handler != null) {
                handler.onRecordingError();
            }
        }
    }
    
    private String getRawName(String fileWavName) {
        return String.format("%s/%s", getFileDir(fileWavName), FILE_NAME);
    }
    
    private String getFileDir(String fileWavName) {
        File file = new File(fileWavName);
        String dir = file.getParent();
        return (dir == null) ? "" : dir;
    }    
    
    private void convertRawToMp3(){
        File file_raw = new File(fileName_raw);
        if (!file_raw.exists()) { return;}
        File file_mp3 = new File(fileName_mp3);
        if (file_mp3.exists()) { file_mp3.delete(); }
        
        FLameUtils lameUtils=new FLameUtils(1, SAMPLING_RATE, 96);
        boolean result = lameUtils.raw2mp3(fileName_raw, fileName_mp3);
        
        file_raw.delete();
        
        if (handler != null) {
            if(result){
                handler.onRecordSuccess();
            }else{
                handler.onRecordSaveError();
            }
        }
        
        
    }
    
    public synchronized void stopRecording() {
        isRecording = false;
    }
}
