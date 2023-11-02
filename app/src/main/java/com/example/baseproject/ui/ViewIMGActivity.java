package com.example.baseproject.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.baseproject.R;
import com.example.baseproject.adapter.ViewPagerIMGAdapter;

import java.io.File;
import java.util.ArrayList;

public class ViewIMGActivity extends AppCompatActivity {
    ArrayList<String> f = new ArrayList<>();// list of file paths
    File[] listFile;
    private String folderName = "MyPhotoDir";
    // Creating object of ViewPager
    ViewPager mViewPager;
    // Creating Object of ViewPagerAdapter
    ViewPagerIMGAdapter mViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_imgactivity);
        getFromSdcard();
        // Initializing the ViewPager Object
        mViewPager = findViewById(R.id.viewPagerMain);
        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new ViewPagerIMGAdapter(this, f);
        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
    }
    public void getFromSdcard() {
        File file = new File(getExternalFilesDir(folderName), "/");
        if (file.isDirectory()) {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }
}