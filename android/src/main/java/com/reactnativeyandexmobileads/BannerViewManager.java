package com.reactnativeyandexmobileads;

import androidx.annotation.Nullable;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.yandex.mobile.ads.banner.BannerAdSize;

import java.util.Map;

public class BannerViewManager extends SimpleViewManager<BannerView> {

  @ReactProp(name = "adUnitId")
  public void setAdUnitId(BannerView view, String adUnitId) {
    view.setAdUnitId(adUnitId);
  }

  @ReactProp(name = "size")
  public void setSize(BannerView view, String size) {
    BannerAdSize adSize;
    switch (size) {
      case "BANNER_300x250":
        adSize = BannerAdSize.inlineSize(view.getContext(), 300, 250);
        break;
      case "BANNER_300x300":
        adSize = BannerAdSize.inlineSize(view.getContext(), 300, 300);
        break;
      case "BANNER_320x50":
        adSize = BannerAdSize.inlineSize(view.getContext(), 320, 50);
        break;
      case "BANNER_320x100":
        adSize = BannerAdSize.inlineSize(view.getContext(), 320, 100);
        break;
      case "BANNER_400x240":
        adSize = BannerAdSize.inlineSize(view.getContext(), 400, 240);
        break;
      case "BANNER_728x90":
        adSize = BannerAdSize.inlineSize(view.getContext(), 728, 90);
        break;
      default:
        adSize = BannerAdSize.inlineSize(view.getContext(), 240, 400);
        break;
    }

    view.setSize(adSize);
  }

  @Override
  protected BannerView createViewInstance(ThemedReactContext reactContext) {
    return new BannerView(reactContext);
  }

  @Override
  public @Nullable
  Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of(
      "onError",
      MapBuilder.of("registrationName", "onError"),
      "onLoad",
      MapBuilder.of("registrationName", "onLoad"),
      "onLeftApplication",
      MapBuilder.of("registrationName", "onLeftApplication"),
      "onReturnedToApplication",
      MapBuilder.of("registrationName", "onReturnedToApplication")
    );
  }

  @Override
  public String getName() {
    return "BannerView";
  }
}
