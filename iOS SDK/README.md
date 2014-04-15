##Prerequisites
Clone this repo or [download here](https://github.com/midversestudios/AppEngage/archive/master.zip)

Add the following files to your Xcode project:
+ **libAppEngage.a** 
+ **MVAppEngage.h**
+ **MVAppEngageResources.bundle**


Link the following frameworks:
+ **AdSupport.framework**  
+ **Security.framework**
 


In your target's **Build Settings** under the **Other Linker Flags** section, add the following flags:
+ **-ObjC**
+ **-all_load**
 
If you are publishing the **AppEngage Dialog**, complete the following steps:

1.	Setup **App** and **Engagement Actions**.  Make sure that your app and engagement actions are set up in the dashboard correctly at engage.pxladdicts.com.

##Setting up your device for testing 

Before you begin, make sure your application is set up correctly on the AppEngage dashboard at engage.pxladdicts.com. Add your test device’s **IDFA** to the list of test devices on the AppEngage dashboard. 


##Let's start up the AppEngage SDK!


In your **AppDelgate**'s **application:didFinishLaunchingWithOptions:** method:

Initialize our SDK with your app's APP Key from our dashboard: 
```objective-c
[MVAppEngage applicationDidLaunchWithAppKey:@"PUT_YOUR_APP_KEY_HERE"];
```

Set your game's user ID for the logged in user.  We recomend this is a uniqueID such as a user's databaseID, etc.
```objective-c
[MVAppEngage setPublisherUserID:@"THIS_IS_MY_APPS_UNIQUE_USER_ID"];
```

Set up a block to be notified when your user earns currency.
```objective-c
[MVAppEngage setCurrencyRewardHandler:^(NSNumber *currencyAmount, NSString *currencyClaimToken){
        NSLog(@"User just earned %@ currency with the claim token of %@", currencyAmount, currencyClaimToken);
    }];
```

##Showing the AppEngage Dialog

To show the AppEngage dialog call:
```objective-c
[MVAppEngage showEngagementDialog];
```

##Completing Engagement Actions
To complete an action add the below line when the action requirements are completed in your app. Pass the action type as the parameter.

```objective-c
[MVAppEngage userPerformedEngagementAction:@"THE_ACTION"];
```
	
Built in Engagement Actions:

| Action        | Description   |
| ------------- |:------------- |
| "LevelUp"     | Called each time your user levels up |
| "Win"      | Called each time your user wins      |
| "Play" |  Called each time your user plays a round      |
| "Buy" | Called each time your user buys an item      |
| "Use" | Called each time your user uses an item (i.e. power up)     |
| "Share" | Called each time your user shares on a social network     |

You can also create custom action types on the campaign editor.


##Server Currency Verification
Publishers are able to verify currency claims by making a call to the following URL:
	http://engage.pxladdicts.com/engage/verifycurrencyclaimtoken/token/TOKEN_FROM_SDK
	Parameters:
	**TOKEN_FROM_SDK** - token is provided by the client-side SDK on every rewarding of currency

```json
	{"result": {"token_verified": 0 or 1, "claimed": 0 or 1, "currency_amount":0 or more}}
```

To prevent fraud, you should give currency to the user only server-side, and only when **token_verified** is 1 and claimed is 0

##Sample App

If you have any issues take a look at how the SampleApp works. If you still having issues contact your representative with specific questions and we will be happy to help.
