package com.example.baseproject.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baseproject.R;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    public static final HashMap<Character, String> morseCode = generateMorseCodeMap();
    public static final HashMap<String, String> stringMap = generateStringMap();

    private static HashMap<String, String> generateStringMap() {
        HashMap<String, String> morseCodeMap = new HashMap<>();

        morseCodeMap.put(".-", "A");
        morseCodeMap.put("-...", "B");
        morseCodeMap.put("-.-.", "C");
        morseCodeMap.put("-..", "D");
        morseCodeMap.put(".", "E");
        morseCodeMap.put("..-.", "F");
        morseCodeMap.put("--.", "G");
        morseCodeMap.put("....", "H");
        morseCodeMap.put("..", "I");
        morseCodeMap.put(".---", "J");
        morseCodeMap.put("-.-", "K");
        morseCodeMap.put(".-..", "L");
        morseCodeMap.put("--", "M");
        morseCodeMap.put("-.", "N");
        morseCodeMap.put("---", "O");
        morseCodeMap.put(".--.", "P");
        morseCodeMap.put("--.-", "Q");
        morseCodeMap.put(".-.", "R");
        morseCodeMap.put("...", "S");
        morseCodeMap.put("-", "T");
        morseCodeMap.put("..-", "U");
        morseCodeMap.put("...-", "V");
        morseCodeMap.put(".--", "W");
        morseCodeMap.put("-..-", "X");
        morseCodeMap.put("-.--", "Y");
        morseCodeMap.put("--..", "Z");
        morseCodeMap.put(".----", "1");
        morseCodeMap.put("..---", "2");
        morseCodeMap.put("...--", "3");
        morseCodeMap.put("....-", "4");
        morseCodeMap.put(".....", "5");
        morseCodeMap.put("-....", "6");
        morseCodeMap.put("--...", "7");
        morseCodeMap.put("---..", "8");
        morseCodeMap.put("----.", "9");
        morseCodeMap.put("-----", "0");

        return morseCodeMap;
    }


    RelativeLayout relativeLayout, backgroud;
    ImageView imageView, imageView1, setting, flashlight, morsecode, compass, notification1, Viewcolor, camera;

    CardView Set, Sos, Brightness;
    boolean moning = false;
    ImageView iset, isos, iBrightness;
    ImageView power;
    boolean hasCameraFlash = false;
    boolean flashOn = false;
    int REQUEST_CODE = 123;
    Handler handler = new Handler();

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
        Viewcolor = findViewById(R.id.viewcolor);
        power = findViewById(R.id.turnOnOffFlash);
        setting = findViewById(R.id.setting);
        flashlight = findViewById(R.id.flashlight);
        morsecode = findViewById(R.id.morsecode);
        compass = findViewById(R.id.compass);
        notification1 = findViewById(R.id.notification);
        camera = findViewById(R.id.camera);
        Set = findViewById(R.id.cvset);
        Sos = findViewById(R.id.cvsos);
        Brightness = findViewById(R.id.cvBrightness);
        iset = findViewById(R.id.icset);
        isos = findViewById(R.id.icsos);
        iBrightness = findViewById(R.id.icBrightness);

        Brightness.setOnClickListener(view -> {
            iset.setSelected(false);
            isos.setSelected(false);
            iBrightness.setSelected(true);
            Brightness.setCardBackgroundColor(getApplication().getColor(R.color.ylew));
            Set.setCardBackgroundColor(getApplication().getColor(R.color.tim));
            Sos.setCardBackgroundColor(getApplication().getColor(R.color.tim));
        });

        Sos.setOnClickListener(view1 -> {
            iBrightness.setSelected(false);
            isos.setSelected(true);
            iset.setSelected(false);
            Set.setCardBackgroundColor(getApplication().getColor(R.color.tim));
            Sos.setCardBackgroundColor(getApplication().getColor(R.color.ylew));
            Brightness.setCardBackgroundColor(getApplication().getColor(R.color.tim));



        });

        Set.setOnClickListener(view1 -> {
            iBrightness.setSelected(false);
            isos.setSelected(false);
            iset.setSelected(true);
            Set.setCardBackgroundColor(getApplication().getColor(R.color.ylew));
            Sos.setCardBackgroundColor(getApplication().getColor(R.color.tim));
            Brightness.setCardBackgroundColor(getApplication().getColor(R.color.tim));

        });


//        switch ()
//        Set.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Set.setBackgroundResource(R.drawable.boder_v);
//            }
//        });
//        Sos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Set.setBackgroundResource(R.drawable.boder_v);
//            }
//        });
//        Brightness.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Set.setBackgroundResource(R.drawable.boder_v);
//            }
//        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,REQUEST_CODE);
                startActivity(new Intent(MainActivity.this, CameraActivity2.class));
            }
        });


        notification1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            }
        });
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CompassActivity.class));
            }
        });
        morsecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MorsecodeActivity.class));
            }
        });
        flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FlashLightActivity.class));
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });

        Viewcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ColorActivity.class));
            }
        });

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasCameraFlash) {
                    if (flashOn) {
                        flashOn = false;
                        try {
                            flashLightOff();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        flashOn = true;
                        try {
                            flashLightOn();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "AAA", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        binding = MainActivity.inflate(getLayoutInflater());
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moning) {
                    imageView1.setImageResource(R.drawable.brightness);
                    backgroud.setBackgroundColor(R.drawable.bodermoning);
                    moning = false;
                } else {
                    imageView1.setImageResource(R.drawable.moon);
                    imageView.setBackgroundResource(R.drawable.boderevening);
                    backgroud.setBackgroundColor(R.drawable.backgroud);
                    moning = true;
                }
            }
        });
    }


    public static HashMap<Character, String> generateMorseCodeMap() {
        HashMap<Character, String> morseCodeMap = new HashMap<>();

        morseCodeMap.put('A', ".-");
        morseCodeMap.put('B', "-...");
        morseCodeMap.put('C', "-.-.");
        morseCodeMap.put('D', "-..");
        morseCodeMap.put('E', ".");
        morseCodeMap.put('F', "..-.");
        morseCodeMap.put('G', "--.");
        morseCodeMap.put('H', "....");
        morseCodeMap.put('I', "..");
        morseCodeMap.put('J', ".---");
        morseCodeMap.put('K', "-.-");
        morseCodeMap.put('L', ".-..");
        morseCodeMap.put('M', "--");
        morseCodeMap.put('N', "-.");
        morseCodeMap.put('O', "---");
        morseCodeMap.put('P', ".--.");
        morseCodeMap.put('Q', "--.-");
        morseCodeMap.put('R', ".-.");
        morseCodeMap.put('S', "...");
        morseCodeMap.put('T', "-");
        morseCodeMap.put('U', "..-");
        morseCodeMap.put('V', "...-");
        morseCodeMap.put('W', ".--");
        morseCodeMap.put('X', "-..-");
        morseCodeMap.put('Y', "-.--");
        morseCodeMap.put('Z', "--..");
        morseCodeMap.put(' ', "/");

        return morseCodeMap;
    }


    private void flashLightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraID = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraID, true);
        //Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();
    }

    private void flashLightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        assert cameraManager != null;
        String cameraID = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraID, false);
        //Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}