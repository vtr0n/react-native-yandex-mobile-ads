import React, { Component } from 'react';
import { Button, StyleSheet, View } from 'react-native';
import { BannerView } from 'react-native-yandex-mobile-ads';
import {
  InterstitialAdManager,
  RewardedAdManager,
} from 'react-native-yandex-mobile-ads';

export default class BannerAd extends Component {
  render() {
    return (
      <View style={styles.container}>
        <View style={styles.bannerContainer}>
          <BannerView
            blockId={'R-M-DEMO-300x250'}
            size="BANNER_300x250"
            onLoad={() => console.log('onLoad')}
            onLeftApplication={() => console.log('onLeftApplication')}
            onReturnedToApplication={() =>
              console.log('onReturnedToApplication')
            }
            onError={(err: any) => console.log('error', err)}
          />
          <View style={styles.button}>
            <Button
              title={'InterstitialAd'}
              onPress={() => {
                InterstitialAdManager.showAd('adf-279013/966533')
                  .then((didClick: boolean) => {
                    console.log('clicked: ' + didClick);
                  })
                  .catch((error: any) => {
                    console.log('error: ' + error);
                  });
              }}
            />
          </View>
          <View style={styles.button}>
            <Button
              title={'RewardedAd'}
              onPress={() => {
                RewardedAdManager.showAd('adf-279013/966487')
                  .then((didClick: boolean) => {
                    console.log('clicked: ' + didClick);
                  })
                  .catch((error: any) => {
                    console.log('error: ' + error);
                  });
              }}
            />
          </View>
        </View>
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
  button: {
    margin: 10,
  },
});
