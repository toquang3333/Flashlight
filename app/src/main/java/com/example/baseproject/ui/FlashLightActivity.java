package com.example.baseproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.baseproject.R;
import com.example.baseproject.ads.AdsConfig;

public class FlashLightActivity extends AppCompatActivity {

    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    RelativeLayout relativeLayout;
    SwitchCompat switchCompat;
    boolean incoming = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ImageView out;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        relativeLayout = findViewById(R.id.settime);
        switchCompat = findViewById(R.id.scCall);
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

        switchCompat.setOnClickListener(v -> {
            onNext();
        });
        switchCompat.setChecked(incoming);
       switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    incoming = z;
                    editor.putString(getDefaultSmsAppPackage(getApplicationContext()), "Lock");
                    editor.commit();
                } else {
                    incoming = z;
                    editor.putString(getDefaultSmsAppPackage(getApplicationContext()), "Lock");
                    editor.commit();
                }
            }
        });

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
    private void setEnableCheckedSwitch(SwitchCompat view, boolean isCheck) {
        view.setChecked(isCheck);
        view.setEnabled(!isCheck);
    }

    private void onNext() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            checkAndRequestPermissionsCall();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                isEnabled()) {
            switchCompat.setChecked(true);
            switchCompat.setChecked(true);
            switchCompat.setChecked(true);
        }

        setEnableCheckedSwitch(switchCompat, ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1232) {
            if (iArr[0] == PackageManager.PERMISSION_GRANTED) {
                switchCompat.setChecked(true);
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(String.valueOf(iArr[0]));
                if (!showRationale) {
                    showGoToSetting();
                } else switchCompat.setChecked(false);
            }
        }
    }


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
}