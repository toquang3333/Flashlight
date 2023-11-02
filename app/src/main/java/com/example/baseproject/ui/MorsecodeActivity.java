package com.example.baseproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baseproject.R;
import com.google.android.material.textfield.TextInputLayout;

public class MorsecodeActivity extends AppCompatActivity {
    ImageView imageView,Imgtrong;
    TextView button,Texttrong;
    EditText input,output;
    TextInputLayout textInputLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morsecode);
        imageView = findViewById(R.id.outmorsecode);
        button = findViewById(R.id.buttonConvertToString);
        input = findViewById(R.id.editTextMorse);
        output = findViewById(R.id.textViewOutputString);
        Imgtrong = findViewById(R.id.imgtrong);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String in = input.getText().toString();
                if(TextUtils.isEmpty(in)){
                    input.setError("Please fill in this box");
                    input.requestFocus();
//                    Imgtrong.setVisibility(View.VISIBLE);
                }
                    convert();

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void convert() {
        String strInput = String.valueOf(input.getText()).toUpperCase();
        String strOutput = "";

        for (char c : strInput.toCharArray()) {
            strOutput += MainActivity.morseCode.get(c) + " ";
        }

        output.setText(strOutput.trim());


    }
}