package com.example.baseproject.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;

import com.example.baseproject.R;
import com.example.baseproject.base.BaseActivity;
import com.example.baseproject.databinding.ActivitySplashBinding;
import com.example.baseproject.model.LanguageModel;

import java.util.List;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    List<LanguageModel> listLanguage;
    String codeLang;
    @Override
    protected ActivitySplashBinding setViewBinding() {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this));
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(() -> startActivity(new Intent(SplashActivity.this,LanguageActivity.class).putExtra("startFirst", true)),3000);
    }

    @Override
    protected void viewListener() {

    }

    @Override
    protected void dataObservable() {

    }

}