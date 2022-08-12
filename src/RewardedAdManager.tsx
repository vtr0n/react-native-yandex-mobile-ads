import { NativeModules } from 'react-native';

const { RewardedAdManager } = NativeModules;

export default {
  showAd(adUnitId: string): Promise<boolean> {
    return RewardedAdManager.showAd(adUnitId);
  },
};
