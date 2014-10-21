package com.example.sugarmanagedemo.activity;

import org.kymjs.aframe.ui.activity.BaseSplash;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.ImageView;

import com.example.sugarmanagedemo.R;

public class SplashActivity extends BaseSplash {
    private SharedPreferences sharedPrefrences;
    private boolean isFirstInstalled = true;
    
    public SplashActivity(){
    }
    
    @Override
    protected void setRootBackground(ImageView view) {
        view.setBackgroundResource(R.drawable.bg);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        
    }
    
    @Override
    protected boolean firstsInstall(){
        sharedPrefrences = this.getSharedPreferences(this.getPackageName(),MODE_PRIVATE);
        isFirstInstalled = sharedPrefrences.getInt("first", 1)==0?false:true;
        
        return isFirstInstalled;
    }
    
    @Override
    protected void redirectTo() {
        if(this.firstsInstall()){            
            Editor editor = getSharedPreferences(this.getPackageName(), MODE_PRIVATE).edit();
            editor.putInt("first", 0);
            editor.commit();
            
            this.skipActivity(this, DiagnoseActivity.class);
        }else{
            
            
//            this.skipActivity(this, MainActivity.class);
            
            this.skipActivity(this, BTSearchActivity.class);
        }
    }



}
