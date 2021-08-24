# react-native-yandex-mobile-ads

Implementation Yandex ads for react native

## Installation

```sh
npm install react-native-yandex-mobile-ads
```

## Usage

```js
import { BannerView } from 'react-native-yandex-mobile-ads';
// ...

<BannerView
  blockId={'R-M-DEMO-300x300'}
  size="BANNER_320x50"
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
