package com.reactnativeyandexmobileads;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.yandex.mobile.ads.common.AdError;
import com.yandex.mobile.ads.common.AdRequestConfiguration;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.rewarded.Reward;
import com.yandex.mobile.ads.rewarded.RewardedAd;
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener;
import com.yandex.mobile.ads.rewarded.RewardedAdLoadListener;
import com.yandex.mobile.ads.rewarded.RewardedAdLoader;

import java.util.Objects;

public class RewardedAdManager extends ReactContextBaseJavaModule implements RewardedAdEventListener, RewardedAdLoadListener, LifecycleEventListener {

  private Promise mPromise;
  private boolean mDidClick = false;
  private boolean mViewAtOnce = false;

  private RewardedAd mRewarded;

  public RewardedAdManager(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addLifecycleEventListener(this);
  }

  @ReactMethod
  public void showAd(String adUnitId, Promise p) {
    if (mPromise != null) {
      p.reject("E_FAILED_TO_SHOW", "Only one `showAd` can be called at once");
      return;
    }
    final RewardedAdLoader loader = new RewardedAdLoader(this.getReactApplicationContext());
    loader.setAdLoadListener(this);
    loader.loadAd(new AdRequestConfiguration.Builder(adUnitId).build());

    mViewAtOnce = true;
    mPromise = p;
  }

  @Override
  public String getName() {
    return "RewardedAdManager";
  }

  private void cleanUp() {
    mPromise = null;
    mDidClick = false;
    mViewAtOnce = false;

    if (mRewarded != null) {
      mRewarded.setAdEventListener(null);
      mRewarded = null;
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
    cleanUp();
  }

  @Override
  public void onRewarded(@NonNull Reward reward) {
    WritableMap event = Arguments.createMap();

    event.putInt("amount", reward.getAmount());
    event.putString("type", reward.getType());
    event.putBoolean("click", mDidClick);

    mPromise.resolve(event);
    cleanUp();
  }

  @Override
  public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
    mRewarded = rewardedAd;
    mRewarded.setAdEventListener(this);
    if (mViewAtOnce) {
      mRewarded.show(Objects.requireNonNull(this.getCurrentActivity()));
    }
  }

  @Override
  public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
    mPromise.reject("E_FAILED_TO_LOAD", adRequestError.getDescription());
    cleanUp();
  }
}
