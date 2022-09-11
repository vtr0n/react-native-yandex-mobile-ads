package com.reactnativeyandexmobileads;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.yandex.mobile.ads.common.InitializationListener;
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
  public void initialize(ReadableMap configuration) {
    MobileAds.initialize(getReactApplicationContext(), new InitializationListener() {
      @Override
      public void onInitializationCompleted() {
        MobileAds.setUserConsent(configuration.getBoolean("userConsent"));
        MobileAds.setLocationConsent(configuration.getBoolean("locationConsent"));
        MobileAds.enableLogging(configuration.getBoolean("enableLogging"));
        MobileAds.enableDebugErrorIndicator(configuration.getBoolean("enableDebugErrorIndicator"));
      }
    });
  }
}
