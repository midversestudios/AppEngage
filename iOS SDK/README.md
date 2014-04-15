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

##Let's start it up!




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

##Setting up your device for testing 

Before you begin, make sure your application is set up correctly on the AppEngage dashboard at engage.pxladdicts.com. Add your test device’s **IDFA** to the list of test devices on the AppEngage dashboard. 


##How to integrate the AppEngage Dialog

If you are integrating/showing the AppEngage dialog, complete the following steps:

1.	Setup App and Campaign in Dashboard.

	a.	Make sure that your app and campaign actions are set up in the dashboard correctly at engage.pxladdicts.com.
	
2.	Show the AppEngage dialog in your app.

3.	Mark your engagement actions complete.


More details on these steps follow:

####STEP 1 DETAILS: Set up App and Campaign on Dashbaord.
Your app’s publishing status should be set to live, and you should have created an engagement campaign.  The engagement campaign should have at least one silver action (For example: Play 1 Hand)

####STEP 2 DETAILS: Show the AppEngage dialog in your app.
You should show the dialog from at least two places in your app:

     i.  When the user starts the app. We recommend after your own promotional windows.
     
     ii. From a button in your app’s store, or wherever you have free virtual currency available.


To show the AppEngage dialog call:
```objective-c
[MVAppEngage showEngagementDialog];
```

####STEP 3 DETAILS: Completing Actions.
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
	TOKEN_FROM_SDK - token is provided by the client-side SDK on every /engage/getpendingrewards call

Response:
	The API call returns JSON in the following format:
	{"result": {"token_verified": 0 or 1, i.e. is the token valid, "claimed": 0 or 1, i.e. has the token been claimed before, currency_amount":AMOUNT_OF_CURRENCY_AS_AN_INTEGER}}

To prevent fraud, you should give currency to the user only server-side, and only when token_verified is 1 and claimed is 0





##Sample App

If you have any issues take a look at how the SampleApp works. If you still having issues contact your representative with specific questions and we will be happy to help.
