package com.example.baseproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.baseproject.R;

public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout,backgroud;
    ImageView imageView, imageView1;
    boolean moning = false;
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
}