package com.example.baseproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.baseproject.R;


public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout,backgroud;
    ImageView imageView, imageView1,setting,flashlight;
    boolean moning = false;

   ImageView power;
   boolean hasCameraFlash = false;
   boolean flashOn = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = findViewById(R.id.chedo);
        backgroud = findViewById(R.id.backgroud);
        imageView = findViewById(R.id.bordermoring);
        imageView1 = findViewById(R.id.bordereving);
        imageView1.setImageResource(R.drawable.brightness);
        power = findViewById(R.id.turnOnOffFlash);
        setting = findViewById(R.id.setting);
        flashlight = findViewById(R.id.flashlight);
        flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,FlashLightActivity.class));
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(hasCameraFlash){
                   if(flashOn){
                       flashOn = false;
                       try {
                           flashLightOff();
                       } catch (CameraAccessException e) {
                           e.printStackTrace();
                       }
                   }else{
                       flashOn = true;
                       try {
                           flashLightOn();
                       } catch (CameraAccessException e) {
                           e.printStackTrace();
                       }
                   }
               }else{
                   Toast.makeText(MainActivity.this, "AAA", Toast.LENGTH_SHORT).show();
               }
            }
        });

//        binding = MainActivity.inflate(getLayoutInflater());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moning){
                    imageView1.setImageResource(R.drawable.brightness);
                    backgroud.setBackgroundColor(R.drawable.bodermoning);
                    moning= false;
                }else {
                    imageView1.setImageResource(R.drawable.moon);
                    imageView.setBackgroundResource(R.drawable.boderevening);
                    backgroud.setBackgroundColor(R.drawable.backgroud);
                    moning= true;
                }
            }
        });
    }
    private  void flashLightOn() throws CameraAccessException{
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager!=null;
        String cameraID = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraID,true);
        Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();
    }
    private  void flashLightOff() throws CameraAccessException{
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager!=null;
        String cameraID = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraID,false);
        Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();
    }
}