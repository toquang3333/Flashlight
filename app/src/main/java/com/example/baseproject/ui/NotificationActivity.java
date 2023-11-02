package com.example.baseproject.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.baseproject.R;

public class NotificationActivity extends AppCompatActivity {
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    private static final int REQUEST_SEND_SMS_PERMISSION = 2;
    ImageView imageView;
    SwitchCompat sccall,scsms;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        imageView = findViewById(R.id.outNotification);
        sccall = findViewById(R.id.scCall);
        scsms = findViewById(R.id.scSMS);

        sccall.setChecked(false);
        scsms.setChecked(false);

        sccall.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                requestCallPhonePermission();
            } else {
                // Người dùng tắt SwitchCompat, thực hiện công việc khác (nếu cần)
            }
        });

        scsms.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                requestSendSmsPermission();
            } else {
                // Người dùng tắt SwitchCompat, thực hiện công việc khác (nếu cần)
            }
        });
    }


    private void requestCallPhonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
        } else {
            // Quyền đã được cấp, thực hiện công việc liên quan đến Gọi Điện Thoại
        }
    }

    private void requestSendSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS_PERMISSION);
        } else {
            // Quyền đã được cấp, thực hiện công việc liên quan đến Gửi Tin Nhắn
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền Gọi Điện Thoại đã được cấp, thực hiện công việc liên quan đến Gọi Điện Thoại
            } else {
                // Người dùng đã từ chối quyền, xử lý tương ứng
            }
        } else if (requestCode == REQUEST_SEND_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền Gửi Tin Nhắn đã được cấp, thực hiện công việc liên quan đến Gửi Tin Nhắn
            } else {
                // Người dùng đã từ chối quyền, xử lý tương ứng
            }
        }
    }
}