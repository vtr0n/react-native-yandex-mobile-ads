import { NativeModules } from 'react-native';

const { InterstitialAdManager } = NativeModules;

export default {
  showAd(blockId: string): Promise<boolean> {
    return InterstitialAdManager.showAd(blockId);
  },
};
