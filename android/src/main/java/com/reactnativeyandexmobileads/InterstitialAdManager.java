package com.reactnativeyandexmobileads;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.common.ImpressionData;

public class InterstitialAdManager extends ReactContextBaseJavaModule implements InterstitialAdEventListener, LifecycleEventListener {

  private Promise mPromise;
  private boolean mDidClick = false;
  private boolean mViewAtOnce = false;

  private InterstitialAd mInterstitial;

  public InterstitialAdManager(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addLifecycleEventListener(this);
  }

  @ReactMethod
  public void showAd(String adUnitId, Promise p) {
    if (mPromise != null) {
      p.reject("E_FAILED_TO_SHOW", "Only one `showAd` can be called at once");
      return;
    }
    Handler mainHandler = new Handler(Looper.getMainLooper());
    Runnable myRunnable = () -> {
      ReactApplicationContext reactContext = getReactApplicationContext();
      mInterstitial = new InterstitialAd(reactContext);
      mInterstitial.setAdUnitId(adUnitId);
      mInterstitial.setInterstitialAdEventListener(this);
      mInterstitial.loadAd(new AdRequest.Builder().build());
    };

    mViewAtOnce = true;
    mPromise = p;

    mainHandler.post(myRunnable);
  }

  @Override
  public String getName() {
    return "InterstitialAdManager";
  }

  private void cleanUp() {
    mPromise = null;
    mDidClick = false;
    mViewAtOnce = false;

    if (mInterstitial != null) {
      mInterstitial.destroy();
      mInterstitial = null;
    }
  }

  @Override
  public void onHostResume() {

  }

  @Override
  public void onHostPause() {

  }

  @Override
  public void onImpression(ImpressionData impressionData)  {

  }

  @Override
  public void onAdClicked() {
    mDidClick = true;
  }

  @Override
  public void onHostDestroy() {
    cleanUp();
  }

  @Override
  public void onAdLoaded() {
    if (mViewAtOnce) {
      mInterstitial.show();
    }
  }

  @Override
  public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
    mPromise.reject("E_FAILED_TO_LOAD", adRequestError.getDescription());
    cleanUp();
  }

  @Override
  public void onAdShown() {

  }

  @Override
  public void onAdDismissed() {
    mPromise.resolve(mDidClick);
    cleanUp();
  }

  @Override
  public void onLeftApplication() {
    mDidClick = true;
  }

  @Override
  public void onReturnedToApplication() {

  }
}
