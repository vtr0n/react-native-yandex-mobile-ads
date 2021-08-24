import React, { Component } from 'react';
import { StyleSheet, View } from 'react-native';
import { BannerView } from 'react-native-yandex-mobile-ads';

export default class BannerAd extends Component {
  render() {
    return (
      <View style={styles.bannerContainer}>
        <BannerView
          blockId={'R-M-DEMO-300x250'}
          size="BANNER_300x250"
          onLoad={() => console.log('onLoad')}
          onLeftApplication={() => console.log('onLeftApplication')}
          onReturnedToApplication={() => console.log('onReturnedToApplication')}
          onError={(err: any) => console.log('error', err)}
        />
      </View>
    );
  }
}

let styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
  },
  bannerContainer: {
    marginVertical: 10,
  },
});
