#import "BannerViewManager.h"
#import "BannerView.h"

@implementation BannerViewManager

RCT_EXPORT_MODULE(BannerView)

@synthesize bridge = _bridge;

- (UIView *)view
{
  return [BannerView new];
}

RCT_EXPORT_VIEW_PROPERTY(size, NSString)
RCT_EXPORT_VIEW_PROPERTY(adUnitId, NSString)
RCT_EXPORT_VIEW_PROPERTY(onError, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onLoad, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onLeftApplication, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onReturnedToApplication, RCTDirectEventBlock)

@end
