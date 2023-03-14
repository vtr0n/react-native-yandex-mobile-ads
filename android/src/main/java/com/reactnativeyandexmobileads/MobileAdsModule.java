package com.reactnativeyandexmobileads;

import android.os.Handler;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;
import com.yandex.mobile.ads.common.MobileAds;

public class MobileAdsModule extends ReactContextBaseJavaModule {

  public MobileAdsModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "MobileAds";
  }

  @ReactMethod
  public void initialize(ReadableMap configuration, Promise promise) {
    Handler mainHandler = new Handler(getReactApplicationContext().getMainLooper());
    Runnable myRunnable = () -> MobileAds.initialize(getReactApplicationContext(), () -> {
      MobileAds.initialize(getReactApplicationContext(), () -> {
        MobileAds.setUserConsent(configuration.getBoolean("userConsent"));
        MobileAds.setLocationConsent(configuration.getBoolean("locationConsent"));
        MobileAds.enableLogging(configuration.getBoolean("enableLogging"));
        MobileAds.enableDebugErrorIndicator(configuration.getBoolean("enableDebugErrorIndicator"));
		promise.resolve(true);
      });
    });
    mainHandler.post(myRunnable);
  }
}
