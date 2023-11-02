package com.example.baseproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baseproject.R;

public class ColorActivity extends AppCompatActivity {
    ImageView colorclick;
    View colorView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        colorclick = findViewById(R.id.colorclick);
        colorView = findViewById(R.id.viewcolorn);

//        colorView.setBackgroundColor(Color.rgb(r,g,b));
        colorclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dilog(Gravity.BOTTOM);
            }
        });
    }
    public void dilog(int gravity){
        Dialog dialog = new Dialog(ColorActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogitem_color);
        View showColor;
        ImageView clickColor;
        TextView textView1;
        textView1 = dialog.findViewById(R.id.done);
        showColor = dialog.findViewById(R.id.showcolor);
        clickColor = dialog.findViewById(R.id.clickcolor);
        clickColor.setDrawingCacheEnabled(true);
        clickColor.buildDrawingCache(true);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        clickColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    bitmap = clickColor.getDrawingCache();
                    int pixel = bitmap.getPixel((int)motionEvent.getX(),(int) motionEvent.getY());
                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

//                    String hex = "#" +Integer.toHexString(pixel);
                    showColor.setBackgroundColor(Color.rgb(r,g,b));
                    colorView.setBackgroundColor(Color.rgb(r,g,b));

                }
                return true;
            }
        });
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        dialog.setCancelable(true);
        dialog.show();
    }
}