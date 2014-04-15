//
//  AppDelegate.h
//  AppEngageSampleApp
//
//  Created by Ryan Bertrand on 1/6/14.
//  Copyright (c) 2014 Midverse. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MVAppEngage.h"

@class ViewController;
@interface AppDelegate : UIResponder <UIApplicationDelegate, MVAppEngageRewardDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) ViewController *viewController;

@end
