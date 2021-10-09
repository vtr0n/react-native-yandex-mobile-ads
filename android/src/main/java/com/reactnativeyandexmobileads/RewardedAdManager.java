package com.reactnativeyandexmobileads;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.rewarded.Reward;
import com.yandex.mobile.ads.rewarded.RewardedAd;
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener;

public class RewardedAdManager extends ReactContextBaseJavaModule implements RewardedAdEventListener, LifecycleEventListener {

  private Promise mPromise;
  private boolean mDidClick = false;
  private boolean mViewAtOnce = false;

  private RewardedAd mRewarded;

  public RewardedAdManager(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addLifecycleEventListener(this);
  }

  @ReactMethod
  public void showAd(String blockId, Promise p) {
    if (mPromise != null) {
      p.reject("E_FAILED_TO_SHOW", "Only one `showAd` can be called at once");
      return;
    }

    ReactApplicationContext reactContext = this.getReactApplicationContext();
    mRewarded = new RewardedAd(reactContext);
    mRewarded.setBlockId(blockId);
    mRewarded.setRewardedAdEventListener(this);

    mViewAtOnce = true;
    mPromise = p;

    mRewarded.loadAd(new AdRequest.Builder().build());
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
      mRewarded.destroy();
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
  public void onHostDestroy() {
    cleanUp();
  }

  @Override
  public void onAdLoaded() {
    if (mViewAtOnce) {
      mRewarded.show();
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
    cleanUp();
  }

  @Override
  public void onRewarded(@NonNull Reward reward) {
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
