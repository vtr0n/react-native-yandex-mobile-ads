import { NativeModules } from 'react-native';

const { RewardedAdManager } = NativeModules;

export default {
  showAd(blockId: string): Promise<boolean> {
    return RewardedAdManager.showAd(blockId);
  },
};
