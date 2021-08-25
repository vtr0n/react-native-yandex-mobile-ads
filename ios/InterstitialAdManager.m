#import "InterstitialAdManager.h"
#import "YandexMobileAds/YandexMobileAds.h"
#import <React/RCTUtils.h>
#import <React/RCTLog.h>

@interface InterstitialAdManager () <YMAInterstitialAdDelegate>

@property (nonatomic, strong) RCTPromiseResolveBlock resolve;
@property (nonatomic, strong) RCTPromiseRejectBlock reject;
@property (nonatomic, strong) YMAInterstitialAd *interstitialAd;
@property (nonatomic, strong) UIViewController *adViewController;
@property (nonatomic) bool didClick;
@property (nonatomic) bool didLoad;
@property (nonatomic) bool showWhenLoaded;
@property (nonatomic) bool isBackground;

@end

@implementation InterstitialAdManager

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE(InterstitialAdManager)

- (void)setBridge:(RCTBridge *)bridge
{
    _bridge = bridge;
}

RCT_EXPORT_METHOD(
                  showAd:(NSString *)blockId
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  )
{
    RCTAssert(_resolve == nil && _reject == nil, @"Only one `showAd` can be called at once");
    RCTAssert(_isBackground == false, @"`showAd` can be called only when experience is running in foreground");

    _resolve = resolve;
    _reject = reject;

    _interstitialAd = [[YMAInterstitialAd alloc] initWithBlockID:blockId];
    _interstitialAd.delegate = self;
    _showWhenLoaded = true;

    [self->_interstitialAd load];

}

#pragma mark - YMAInterstitialAdDelegate

- (void)interstitialAdDidLoad:(YMAInterstitialAd *)interstitialAd
{
    _didLoad = true;
    if (_showWhenLoaded) {

        [_interstitialAd presentFromViewController:RCTPresentedViewController()];
    }

}

- (void)interstitialAdDidFailToLoad:(nonnull YMAInterstitialAd *)interstitialAd
                              error:(nonnull NSError *)error;
{
    _reject(@"E_FAILED_TO_LOAD", [error localizedDescription], error);

    [self cleanUpAd];
}

- (void)interstitialAdWillLeaveApplication:
(nonnull YMAInterstitialAd *)interstitialAd;
{
    _didClick = true;
}

- (void)interstitialAdDidDisappear:(nonnull YMAInterstitialAd *)interstitialAd;
{
    _resolve(@(_didClick));

    [self cleanUpAd];
}

- (void)bridgeDidForeground:(NSNotification *)notification
{
    _isBackground = false;

    if (_adViewController) {
        [RCTPresentedViewController() presentViewController:_adViewController animated:NO completion:nil];
        _adViewController = nil;
    }
}

- (void)bridgeDidBackground:(NSNotification *)notification
{
    _isBackground = true;

    if (_interstitialAd) {
        _adViewController = RCTPresentedViewController();
        [_adViewController dismissViewControllerAnimated:NO completion:nil];
    }
}

- (void)cleanUpAd
{
    _reject = nil;
    _resolve = nil;
    _interstitialAd = nil;
    _adViewController = nil;
    _didClick = false;
    _didLoad = false;
    _showWhenLoaded = false;
}

@end
