import { NativeModules } from 'react-native';

const { InterstitialAdManager } = NativeModules;

export default {
  showAd(adUnitId: string): Promise<boolean> {
    return InterstitialAdManager.showAd(adUnitId);
  },
};
