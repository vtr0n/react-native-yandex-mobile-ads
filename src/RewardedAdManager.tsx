import { NativeModules } from 'react-native';

const { RewardedAdManager } = NativeModules;

interface RewardedResp {
  amount: bigint;
  type: string;
  click: boolean;
}

export default {
  showAd(adUnitId: string): Promise<RewardedResp> {
    return RewardedAdManager.showAd(adUnitId);
  },
};
