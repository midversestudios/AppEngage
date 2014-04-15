//
//  AppDelegate.m
//  AppEngageSampleApp
//
//  Created by Ryan Bertrand on 1/6/14.
//  Copyright (c) 2014 Midverse. All rights reserved.
//

#import "AppDelegate.h"
#import "ViewController.h"


@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
    
    
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    self.viewController = [[ViewController alloc] initWithNibName:@"ViewController" bundle:nil];
    self.window.rootViewController = self.viewController;
    [self.window makeKeyAndVisible];
    
    
    [MVAppEngage applicationDidLaunchWithAppKey:@"8b02a018f926c46bf27971a5fdab4b3ca9fda22a"];
    [MVAppEngage setPublisherUserID:@"THIS_IS_MY_APPS_UNIQUE_USER_ID"];
    
    //Use Blocks for currency rewards
    [MVAppEngage setCurrencyRewardHandler:^(NSNumber *currencyAmount, NSString *currencyClaimToken){
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"AppEngage Reward" message:[NSString stringWithFormat:@"Congrats!  You just claimed %i currency for free using AppEngage!  Come back tomorrow for more!", [currencyAmount integerValue]] delegate:nil cancelButtonTitle:@"Okay" otherButtonTitles:nil];
        [alert show];
    }];
    
    //OR Use the Delegate model for currency rewards
    [MVAppEngage setCurrencyRewardDelegate:self];
    
    return YES;
}
							
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

#pragma mark - AppEngage Reward Currency Delegate

-(void)appEngageUserDidEarnCurrency:(NSNumber *)currencyAmount claimToken:(NSString *)claimToken{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"AppEngage Reward" message:[NSString stringWithFormat:@"Congrats!  You just claimed %i currency for free using AppEngage!  Come back tomorrow for more!", [currencyAmount integerValue]] delegate:nil cancelButtonTitle:@"Okay" otherButtonTitles:nil];
    [alert show];
}

@end
