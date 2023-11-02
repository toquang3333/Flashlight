package com.example.baseproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Telephony;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.akexorcist.snaptimepicker.SnapTimePickerDialog;
import com.example.baseproject.R;
import com.example.baseproject.ads.AdsConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlashLightActivity extends AppCompatActivity {

    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    RelativeLayout relativeLayout;
    SwitchCompat switchCompat;
    boolean incoming = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ImageView out;

    SeekBar seekBar,blinkSpeedSeekBar;
    private int brightness;
    ContentResolver contentResolver;
    Window window;


    private CameraManager cameraManager;

    private String cameraId;

    private Handler handler;

    private Runnable runnable;

    private boolean isFlashOn;

    private int blinkInterval = 500;
  int t1Hour,t1Minute;
    TextView textView;
    TextView onClick;
    SwitchCompat icvibrate,icMute;
    private AudioManager audioManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        relativeLayout = findViewById(R.id.settime);
        textView = findViewById(R.id.timeout);
        onClick = findViewById(R.id.onclick);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        switchCompat = findViewById(R.id.scCallfl);
        icvibrate = findViewById(R.id.scVibrat);
        icMute = findViewById(R.id.scMute);
        switchCompat.setChecked(false);
        icMute.setChecked(false);
        icvibrate.setChecked(false);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        if (notificationManager.isNotificationPolicyAccessGranted()) {
            // Thực hiện thay đổi trạng thái "Do Not Disturb" ở đây
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
// Hoặc
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
        } else {
            // Yêu cầu quyền truy cập
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }
        switchCompat.setOnCheckedChangeListener((buttonView,isChecked)->{
            if (isChecked) {
                // Bật âm thanh
                if (checkAndRequestPermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    icvibrate.setChecked(false);
                    icMute.setChecked(false);
                }
            } else {
                // Tắt âm thanh
                if (checkAndRequestPermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
            }
        });
        icvibrate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Bật rung
                if (checkAndRequestPermission(android.Manifest.permission.VIBRATE)) {
                    // Vibrate code here
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    switchCompat.setChecked(false);
                    icMute.setChecked(false);
                }
            } else {
                // Tắt rung
                // Turn off vibration code here
                if (checkAndRequestPermission(android.Manifest.permission.VIBRATE)) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        });

        icMute.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Mute code here
                if (checkAndRequestPermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS)) {
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
                    switchCompat.setChecked(false);
                    icvibrate.setChecked(false);
                }
            } else {
                // Unmute code here
                if (checkAndRequestPermission(android.Manifest.permission.MODIFY_AUDIO_SETTINGS)) {
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
                }
            }
        });

        onClick.setOnClickListener(v ->{
            SnapTimePickerDialog.Builder builder = new SnapTimePickerDialog.Builder();
            builder.setTitle(R.string.title);
            builder.setTitleColor(R.color.black);
            builder.setThemeColor(R.color.tim);
            SnapTimePickerDialog dialog = builder.build();
            dialog.setListener((SnapTimePickerDialog.Listener) (hour, minute) -> onTimePicked(hour, minute));
            dialog.show(getSupportFragmentManager(),SnapTimePickerDialog.TAG);
        });

        textView.setOnClickListener(v ->{
            SnapTimePickerDialog.Builder builder = new SnapTimePickerDialog.Builder();
            builder.setTitle(R.string.title);
            builder.setTitleColor(R.color.black);
            builder.setThemeColor(R.color.tim);
            SnapTimePickerDialog dialog = builder.build();
            dialog.setListener((SnapTimePickerDialog.Listener) (hour, minute) -> onTimePicked(hour, minute));
            dialog.show(getSupportFragmentManager(),SnapTimePickerDialog.TAG);
        });


        blinkSpeedSeekBar = findViewById(R.id.blinkSpeedSeekBar);
        TextView blinkButton = findViewById(R.id.blinkButton);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            blinkButton.setEnabled(false);
            Toast.makeText(this, "Thiết bị không hỗ trợ đèn flash", Toast.LENGTH_SHORT).show();
            return;
        }
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0]; // Lấy ID của camera mặc định (phía sau)
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        handler = new Handler();
        isFlashOn = false;
        blinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlash();
                setBlinkInterval(blinkInterval);
            }
        });
        blinkSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Thay đổi tốc độ nhấp nháy dựa trên giá trị của SeekBar
                blinkInterval = progress;

            }



            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {

                // Không cần xử lý

            }



            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {

                // Không cần xử lý

            }

        });
//        setBlinkInterval(blinkSpeedSeekBar.getProgress());
        seekBar = findViewById(R.id.seekbar);
        contentResolver = getContentResolver();
        window = getWindow();
        seekBar.setMax(290);
        seekBar.setKeyProgressIncrement(1);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(Settings.System.canWrite(this)){
//                Toast.makeText(this, "You can", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:"+getApplication().getPackageName()));
                startActivity(intent);
            }
        }
        try {
            brightness = Settings.System.getInt(contentResolver,Settings.System.SCREEN_BRIGHTNESS);
            seekBar.setProgress(brightness);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightness = i;
                Settings.System.putInt(contentResolver,Settings.System.SCREEN_BRIGHTNESS,brightness);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.screenBrightness = brightness/(float)300;
                window.setAttributes(layoutParams);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.sharedPreferences = defaultSharedPreferences;
        this.editor = defaultSharedPreferences.edit();
        out = findViewById(R.id.out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        switchCompat.setOnClickListener(v -> {
//            onNext();
//        });
//        switchCompat.setChecked(incoming);
//       switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
//                if (z) {
//                    incoming = z;
//                    editor.putString(getDefaultSmsAppPackage(getApplicationContext()), "Lock");
//                    editor.commit();
//                } else {
//                    incoming = z;
//                    editor.putString(getDefaultSmsAppPackage(getApplicationContext()), "Lock");
//                    editor.commit();
//                }
//            }
//        });

    }
    private boolean checkAndRequestPermission(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
            return false;
        }
        return true;
    }
    private void onTimePicked(int selectedHour, int selectedMinute) {
        String hour = String.format("%02d", selectedHour);
        String minute = String.format("%02d", selectedMinute);
        textView.setText(getString(R.string.selected_time_format, hour, minute));
    }
    public static String getDefaultSmsAppPackage(Context context) {
        return Telephony.Sms.getDefaultSmsPackage(context);
    }
    public boolean isEnabled() {
        String packageName = getPackageName();
        String string = Settings.Secure.getString(getContentResolver(), ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(string)) {
            for (String str : string.split(":")) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                if (unflattenFromString != null && TextUtils.equals(packageName, unflattenFromString.getPackageName())) {
                    Log.d("iiitrue", "true");
                    return true;
                }
            }
        }
        Log.d("iiifalse", "false");
        return false;
    }
    private void checkAndRequestPermissionsCall() {
        editor.putBoolean("request", true);
        editor.commit();
        ActivityCompat.requestPermissions(FlashLightActivity.this,
                new String[]{Manifest.permission.READ_PHONE_STATE, "a"}, 1232);
    }
//    private void setEnableCheckedSwitch(SwitchCompat view, boolean isCheck) {
//        view.setChecked(isCheck);
//        view.setEnabled(!isCheck);
//    }

    private void onNext() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            checkAndRequestPermissionsCall();
        }
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
//                isEnabled()) {
//            switchCompat.setChecked(true);
//            switchCompat.setChecked(true);
//            switchCompat.setChecked(true);
//        }
//
//        setEnableCheckedSwitch(switchCompat, ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
//    }

//    @Override
//    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
//        super.onRequestPermissionsResult(i, strArr, iArr);
//        if (i == 1232) {
//            if (iArr[0] == PackageManager.PERMISSION_GRANTED) {
//                switchCompat.setChecked(true);
//            } else {
//                boolean showRationale = shouldShowRequestPermissionRationale(String.valueOf(iArr[0]));
//                if (!showRationale) {
//                    showGoToSetting();
//                } else switchCompat.setChecked(false);
//            }
//        }
//    }


    private void showGoToSetting() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(R.string.Grant_Permission);
        builder1.setMessage(R.string.Please_grant_all_permissions);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                R.string.Go_to_setting,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AdsConfig.disableAdsResume(FlashLightActivity.this);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



    private void toggleFlash() {
        if (isFlashOn) {
            // Tắt đèn flash
            try {
                cameraManager.setTorchMode(cameraId, false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            isFlashOn = false;
            handler.removeCallbacks(runnable);
        } else {
            // Bật đèn flash và bắt đầu nhấp nháy
            try {
                cameraManager.setTorchMode(cameraId, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            isFlashOn = true;
            handler.post(runnable);
        }
    }
    private void startBlinking() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isFlashOn) {
                    // Tắt đèn flash
                    try {
                        cameraManager.setTorchMode(cameraId, false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    isFlashOn = false;
                } else {
                    // Bật đèn flash
                    try {
                        cameraManager.setTorchMode(cameraId, true);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    isFlashOn = true;
                }
                handler.postDelayed(this, blinkInterval);
            }
        };
        handler.post(runnable);
    }
    private void stopBlinking() {
        if (isFlashOn) {
            try {
                cameraManager.setTorchMode(cameraId, false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        handler.removeCallbacks(runnable);
    }
    public void setBlinkInterval(int blinkInterval) {
        this.blinkInterval = blinkInterval;
        stopBlinking();
        startBlinking();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopBlinking();
    }
}