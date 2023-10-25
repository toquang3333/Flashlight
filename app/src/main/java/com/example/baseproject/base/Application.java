package com.example.baseproject.base;

import androidx.appcompat.app.AppCompatActivity;


import com.lvt.ads.util.AdsApplication;

import java.util.List;

public class Application extends AdsApplication {
    @Override
    public boolean enableAdsResume() {
        return false;
    }

    @Override
    public List<String> getListTestDeviceId() {
        return null;
    }

    @Override
    public String getResumeAdId() {
        return "";
    }

    @Override
    public Boolean buildDebug() {
        return false;
    }
}
