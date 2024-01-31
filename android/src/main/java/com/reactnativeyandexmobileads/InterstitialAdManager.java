package com.reactnativeyandexmobileads;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.yandex.mobile.ads.common.AdError;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestConfiguration;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener;
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader;

import java.util.Objects;

public class InterstitialAdManager extends ReactContextBaseJavaModule implements InterstitialAdEventListener, InterstitialAdLoadListener, LifecycleEventListener {

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

    final InterstitialAdLoader loader = new InterstitialAdLoader(getReactApplicationContext());
    loader.setAdLoadListener(this);
    loader.loadAd(new AdRequestConfiguration.Builder(adUnitId).build());

    mViewAtOnce = true;
    mPromise = p;
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
      mInterstitial.setAdEventListener(null);
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
  public void onAdClicked() {
    mDidClick = true;
  }

  @Override
  public void onAdImpression(@Nullable ImpressionData impressionData) {
  }

  @Override
  public void onHostDestroy() {
    cleanUp();
  }

  @Override
  public void onAdShown() {

  }

  @Override
  public void onAdFailedToShow(@NonNull AdError adError) {
    mPromise.reject("E_FAILED_TO_SHOW", adError.getDescription());
    cleanUp();
  }

  @Override
  public void onAdDismissed() {
    mPromise.resolve(mDidClick);
    cleanUp();
  }

  @Override
  public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
    mInterstitial = interstitialAd;
    mInterstitial.setAdEventListener(this);

    if (mViewAtOnce) {
      mInterstitial.show(Objects.requireNonNull(this.getCurrentActivity()));
    }
  }

  @Override
  public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
    mPromise.reject("E_FAILED_TO_LOAD", adRequestError.getDescription());
    cleanUp();
  }
}
