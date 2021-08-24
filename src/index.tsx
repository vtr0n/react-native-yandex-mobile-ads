import { NativeModules } from 'react-native';

type YandexMobileAdsType = {
  multiply(a: number, b: number): Promise<number>;
};

const { YandexMobileAds } = NativeModules;

export { default as BannerView } from './BannerView';
export default YandexMobileAds as YandexMobileAdsType;
