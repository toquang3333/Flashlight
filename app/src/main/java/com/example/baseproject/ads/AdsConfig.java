package com.example.baseproject.ads;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.lvt.ads.callback.NativeCallback;
import com.lvt.ads.util.Admob;
import com.lvt.ads.util.AppOpenManager;

import java.util.ArrayList;
import java.util.List;

public class AdsConfig {
    public static boolean isShowInterSave = true;
    // list id
    public static List<String> listIdAdsNativeLanguage = new ArrayList<>();
    public static List<String> listIdAdsNativeIntro = new ArrayList<>();
    public static List<String> listIdAdsBannerAll = new ArrayList<>();
    public static List<String> listIdAdsInterAll = new ArrayList<>();
    public static List<String> listIDAdsNativePermission = new ArrayList<>();
    public static List<String> listIDAdsCollapseBanner = new ArrayList<>();

    // enable/disable asd resume
    public static void enableAdsResume(Activity context) {
        AppOpenManager.getInstance().enableAppResumeWithActivity(context.getClass());
    }

    public static void disableAdsResume(Activity context) {
        AppOpenManager.getInstance().disableAppResumeWithActivity(context.getClass());
    }

    // loadBanner
    public static void loadBanner(Activity context) {
        Admob.getInstance().loadBannerFloor(context, listIdAdsBannerAll);
    }

    public static void loadCollapseBanner(Activity activity){
        Admob.getInstance().loadCollapsibleBannerFloor(activity, listIDAdsCollapseBanner, "bottom");
    }

    //Inter All
    public static InterstitialAd interAll;

//    public static void loadInterAll(Context context) {
//        Admob.getInstance().loadInterAdsFloor(context, listIdAdsInterAll, new InterCallback() {
//            @Override
//            public void onAdLoadSuccess(InterstitialAd interstitialAd) {
//                super.onAdLoadSuccess(interstitialAd);
//                interAll = interstitialAd;
//            }
//            @Override
//            public void onAdFailedToLoad(LoadAdError i) {
//                super.onAdFailedToLoad(i);
//                interAll = null;
//            }
//        });
//    }

    // load and show native
    public static void showNativeFloor(Activity context, List<String> idAds, FrameLayout adsView, int idLayoutNative) {
        try {
            Admob.getInstance().loadNativeAd(context, idAds, new NativeCallback() {
                @Override
                public void onNativeAdLoaded(NativeAd nativeAd) {
                    super.onNativeAdLoaded(nativeAd);
                    NativeAdView adView = (NativeAdView) LayoutInflater.from(context)
                            .inflate(idLayoutNative, null);
                    adView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E4E4E4")));
                    adsView.removeAllViews();
                    adsView.addView(adView);
                    Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);
                }

                @Override
                public void onAdFailedToLoad() {
                    adsView.removeAllViews();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            adsView.removeAllViews();
        }
    }

    public static void showNative(Activity context, int idAds, FrameLayout adsView, int idLayoutNative) {
        try {
            Admob.getInstance().loadNativeAd(context, context.getString(idAds), new NativeCallback() {
                @Override
                public void onNativeAdLoaded(NativeAd nativeAd) {
                    super.onNativeAdLoaded(nativeAd);
                    NativeAdView adView = (NativeAdView) LayoutInflater.from(context)
                            .inflate(idLayoutNative, null);
                    adView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E4E4E4")));
                    adsView.removeAllViews();
                    adsView.addView(adView);
                    Admob.getInstance().pushAdsToViewCustom(nativeAd, adView);
                }

                @Override
                public void onAdFailedToLoad() {
                    adsView.removeAllViews();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            adsView.removeAllViews();
        }
    }
}
