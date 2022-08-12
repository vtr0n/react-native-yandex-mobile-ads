#import "RewardedAdManager.h"
#import "YandexMobileAds/YandexMobileAds.h"
#import <React/RCTUtils.h>
#import <React/RCTLog.h>

@interface RewardedAdManager () <YMARewardedAdDelegate>

@property (nonatomic, strong) RCTPromiseResolveBlock resolve;
@property (nonatomic, strong) RCTPromiseRejectBlock reject;
@property (nonatomic, strong) YMARewardedAd *rewardedAd;
@property (nonatomic, strong) UIViewController *adViewController;
@property (nonatomic) bool didClick;
@property (nonatomic) bool didLoad;
@property (nonatomic) bool showWhenLoaded;
@property (nonatomic) bool isBackground;

@end

@implementation RewardedAdManager

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE(RewardedAdManager)

- (void)setBridge:(RCTBridge *)bridge
{
    _bridge = bridge;
}

RCT_EXPORT_METHOD(
                  showAd:(NSString *)adUnitId
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject
                  )
{
    RCTAssert(_resolve == nil && _reject == nil, @"Only one `showAd` can be called at once");
    RCTAssert(_isBackground == false, @"`showAd` can be called only when experience is running in foreground");

    _resolve = resolve;
    _reject = reject;

    _rewardedAd = [[YMARewardedAd alloc] initWithAdUnitID:adUnitId];
    _rewardedAd.delegate = self;
    _showWhenLoaded = true;

    [self->_rewardedAd load];

}

#pragma mark - YMARewardedAdDelegate

- (void)rewardedAdDidLoad:(YMARewardedAd *)rewardedAd
{
    _didLoad = true;
    if (_showWhenLoaded) {

        [_rewardedAd presentFromViewController:RCTPresentedViewController()];
    }

}

- (void)rewardedAdDidFailToLoad:(nonnull YMARewardedAd *)rewardedAd
                              error:(nonnull NSError *)error;
{
    _reject(@"E_FAILED_TO_LOAD", [error localizedDescription], error);

    [self cleanUpAd];
}

- (void)rewardedAdWillLeaveApplication:
(nonnull YMARewardedAd *)rewardedAd;
{
    _didClick = true;
}

- (void)rewardedAdDidDisappear:(nonnull YMARewardedAd *)rewardedAd;
{
    _resolve(@(_didClick));

    [self cleanUpAd];
}

- (void)rewardedAdDidReward:(nonnull YMARewardedAd *)rewardedAd;
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

    if (_rewardedAd) {
        _adViewController = RCTPresentedViewController();
        [_adViewController dismissViewControllerAnimated:NO completion:nil];
    }
}

- (void)cleanUpAd
{
    _reject = nil;
    _resolve = nil;
    _rewardedAd = nil;
    _adViewController = nil;
    _didClick = false;
    _didLoad = false;
    _showWhenLoaded = false;
}

@end
