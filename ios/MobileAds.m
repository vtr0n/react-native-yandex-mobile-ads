#import "MobileAds.h"
#import "YandexMobileAds/YandexMobileAds.h"
#import <React/RCTUtils.h>
#import <React/RCTLog.h>

@implementation MobileAds

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE(MobileAds)

- (void)setBridge:(RCTBridge *)bridge
{
    _bridge = bridge;
}

RCT_EXPORT_METHOD(initialize:(NSDictionary *)configuration)
{
    [YMAMobileAds setUserConsent:configuration[@"userConsent"]];
    [YMAMobileAds setLocationTrackingEnabled:(BOOL) configuration[@"locationConsent"]];
    if (configuration[@"enableLogging"]) {
        [YMAMobileAds enableLogging];
    }
    if (configuration[@"enableDebugErrorIndicator"]) {
        [YMAMobileAds enableVisibilityErrorIndicatorForDeviceType:(YMADeviceType) YMADeviceTypeNone];
    }
}


@end
