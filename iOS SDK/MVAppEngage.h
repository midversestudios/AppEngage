//
//  PDPublisherSDK.h
//  PDPublisherSDK
//
//  Created by Ryan Bertrand on 6/26/13.
//  Copyright (c) 2013 Midverse. All rights reserved.
//

#import <Foundation/Foundation.h>

#define KMVAppEngageSDKVersion @"1.00"

//Engagement Action Options
#define KMVEngagementActionPlay @"play"
#define KMVEngagementActionLevelUp @"levelUp"
#define KMVEngagementActionWin @"win"
#define KMVEngagementActionUse @"use"
#define KMVEngagementActionBuy @"buy"
#define KMVEngagementActionShare @"share"

typedef void (^MVCurrencyRewardHandler)(NSNumber*currencyAmount, NSString*claimToken);

@protocol MVAppEngageRewardDelegate <NSObject>

-(void)appEngageUserDidEarnCurrency:(NSNumber *)currencyAmount claimToken:(NSString *)claimToken;

@end

@interface MVAppEngage : NSObject{
    
}

+(MVAppEngage *)sharedEngage;

//Launching
+(void)applicationDidLaunchWithAppKey:(NSString *)theAppKey;

//Currency Handler
+(void)setCurrencyRewardHandler:(MVCurrencyRewardHandler)handler;
+(void)setCurrencyRewardDelegate:(id <MVAppEngageRewardDelegate>)delegate;

//Engagement
+(void)showEngagementDialog;
+(void)userPerformedEngagementAction:(NSString *)eAction;

//Custom ID (this is used for apps who want to be passed their own userIDs on app conversions.)
+(void)setPublisherUserID:(NSString *)userID;
+(NSString *)publisherUserID;

//Helpers
+(NSString *)appKey;
+(NSString *)currencyName;

@end
