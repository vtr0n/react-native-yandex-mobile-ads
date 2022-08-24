# react-native-yandex-mobile-ads

Implementation Yandex Mobile Ads SDK for react native

## Installation

Add the package to your project using either yarn:

```bash
yarn add react-native-yandex-mobile-ads
```

or npm:

```bash
npm install --save react-native-yandex-mobile-ads
```

## Linking

### React Native >= 0.60

CLI autolink feature links the module while building the app.

**Note**: for iOS make sure to install Pods through CocoaPods by running this command in your project's root directory:
`cd ios && pod install`

<details>
<summary>For React-Native < 0.60</summary>
Link the native dependencies:

```bash
$ react-native link react-native-yandex-mobile-ads
```
</details>

## Usage

```tsx
import { BannerView, InterstitialAdManager, RewardedAdManager } from 'react-native-yandex-mobile-ads';

<BannerView
  adUnitId={'R-M-DEMO-300x250'}
  size="BANNER_300x250"
  onLoad={() => console.log('onLoad')}
  onLeftApplication={() => console.log('onLeftApplication')}
  onReturnedToApplication={() => console.log('onReturnedToApplication')}
  onError={(err: any) => console.log('error', err)}
/>

InterstitialAdManager.showAd('R-M-DEMO-interstitial')
  .then((didClick: boolean) => {
    console.log('clicked: ' + didClick);
  })
  .catch((error: any) => {
    console.log('error: ' + error);
  });

RewardedAdManager.showAd('R-M-DEMO-rewarded-client-side-rtb')
  .then((resp) => {
    const amount = resp.amount;
    const type = resp.type;
    const clicked = resp.click;
    console.log(
      `amount: ${amount}, type: ${type}, clicked: ${clicked}`
    );
  })
  .catch((error: any) => {
    console.log('error: ' + error);
  });
}}
```

## Contributing
Mobile Ads SDK [documentation](https://yandex.ru/dev/mobile-ads/doc/intro/about.html)
You can look at alternative implementations [react-native-admob](https://github.com/sbugert/react-native-admob),
[react-native-fbads](https://github.com/callstack/react-native-fbads) and implement similar code

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
