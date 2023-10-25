package com.example.baseproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.Manifest;
import android.view.View;

import com.example.baseproject.R;
import com.example.baseproject.base.BaseActivity;
import com.example.baseproject.databinding.ActivityLanguageBinding;
import com.example.baseproject.databinding.ActivityPermissionBinding;
import com.example.baseproject.databinding.ActivityTutorialBinding;
import com.example.baseproject.dialog.PermissionDialog;
import com.example.baseproject.utils.PermissionUtils;

import java.util.List;

public class PermissionActivity extends BaseActivity<ActivityPermissionBinding> {
    String[] STORAGE_PERMISSION;
    String[] NOTIFICATION_PERMISSION;

    @Override
    protected ActivityPermissionBinding setViewBinding() {
        return ActivityPermissionBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            STORAGE_PERMISSION = new String[]{
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
            };
        } else {
            STORAGE_PERMISSION = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            NOTIFICATION_PERMISSION = new String[]{Manifest.permission.POST_NOTIFICATIONS};
        } else {
            NOTIFICATION_PERMISSION = new String[]{};
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkPermission(NOTIFICATION_PERMISSION)) allowStoragePermission();
        } else {
            binding.tableNotif.setVisibility(View.GONE);
        }

        if (checkPermission(STORAGE_PERMISSION)) allowStoragePermission();
    }

    @Override
    protected void viewListener() {
        binding.tvContinue.setOnClickListener(v->{startActivity(new Intent(PermissionActivity.this, MainActivity.class));});
        binding.swPer2.setOnClickListener(v->{
            PermissionDialog dialogPermission = new PermissionDialog(this, R.style.MyThemeDialogNoBg, () -> {});
            dialogPermission.setCancelable(false);
            dialogPermission.show();
        });
    }

    @Override
    protected void dataObservable() {

    }

    private boolean checkPermission(String[] listPermission){
        for (String per : listPermission){
            boolean allow = ActivityCompat.checkSelfPermission(this,per) == PackageManager.PERMISSION_GRANTED;
            if (!allow) return false;
        }
        return  true;
    }
    private void allowStoragePermission(){
        binding.swPer2.setImageResource(R.drawable.switch_on);
    }
    public void showDialogPermission(String[] permissions) {
        for (String per : permissions) {
            if (!PermissionUtils.checkPermission(per,this)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, per)) {
                    PermissionDialog dialogPermission = new PermissionDialog(this, R.style.MyThemeDialogNoBg, () -> {

                    });
                    dialogPermission.show();
                } else {
                   // requestPermissionLauncher.launch(permissions);
                }
                return;
            }
        }
        allowStoragePermission();
    }

}