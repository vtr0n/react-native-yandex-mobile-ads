import { NativeModules } from 'react-native';

const { MobileAds } = NativeModules;

interface IConfiguration {
  userConsent?: boolean;
  locationConsent?: boolean;
  enableLogging?: boolean;
  enableDebugErrorIndicator?: boolean;
}

const IConfigurationDefaults: IConfiguration = {
  userConsent: false,
  locationConsent: false,
  enableLogging: false,
  enableDebugErrorIndicator: false,
};

export default {
  initialize(configuration: IConfiguration): null {
    return MobileAds.initialize({
      ...IConfigurationDefaults,
      ...configuration,
    });
  },
};
