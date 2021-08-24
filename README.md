# react-native-yandex-mobile-ads

Implementation Yandex ads for react native

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

```js
import { BannerView } from 'react-native-yandex-mobile-ads';
// ...

<BannerView
  blockId={'R-M-DEMO-300x250'}
  size="BANNER_300x250"
  onLoad={() => console.log('onLoad')}
  onLeftApplication={() => console.log('onLeftApplication')}
  onReturnedToApplication={() => console.log('onReturnedToApplication')}
  onError={(err: any) => console.log('error', err)}
/>
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
