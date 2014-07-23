##Prerequisites
Clone this repo or [download here](https://github.com/midversestudios/AppEngage/archive/master.zip)

##Starting up the ANE nGage SDK

1. Add nGageANE_v114.ane to the projects Native Extensions. Remember to check the box for nGageANE for the platform(s) on which you are developing. (Note: if you are already using the Google Play Services Library with your app use nGageANE_v114_no_GplayLib.ane instead)


2. In your apps Manifest file add the lines inside the <application> tag:
```Java
 <application …>
	…

	  <meta-data  android:name="com.google.android.gms.version"  android:value="@integer/google_play_services_version" />

      <activity android:screenOrientation="sensorLandscape" android:configChanges="keyboardHidden|orientation" android:name="com.tinidream.ngage.nGageActivity"/>
	…
</application>
```


Also in the Manifest, add attribute ```android:launchMode="singleTask"``` to your apps starting activity tag. 
For example, you will have something like ```<activity android:name="com.company.appname.startingActivity" … android:launchMode="singleTask"/>```

`3. Call the **onCreate** function with your app's Activity and your app's AppEngage API Key. You can find your SDK Key on the our dashboard once you have setup a company account and created an app.

```Java
nGageANE.getInstance().onCreate(this, "YOUR_APP_API_KEY");
```

`4. If you're using nGageANE_v114_no_GplayLib.ane be sure to pass the Google Advertising ID to AppEngage before the **onCreate** call in step 3.
```Java
nGageANE.getInstance().setPublisherUserID("GOOGLE_ADVERTISING_ID");
```

##Setting up your device for testing 

Before you begin, make sure your application is set up correctly on the AppEngage dashboard at engage.pxladdicts.com. Add your test device’s Android ID to the list of test devices on the AppEngage dashboard. 

2.	To get your Android ID:

	a.	Run the AppEngage Android SDK sample app which displays the Android ID. 
	
	b.	Or you can download the following app https://play.google.com/store/apps/details?id=com.evozi.deviceid
  	
  	WARNING: If you use the wrong ID or don’t enter it correctly in the dashboard, the dialogs won't display correctly.
3.	Run the sample app, you should be able to see how the appengage dialog and the interstitial look.


##How to integrate the AppEngage Dialog, Actions and Rewarding 

If you are integrating/showing the AppEngage dialog, complete the following steps:

1.	Setup App and Campaign in Dashboard.

	a.	Make sure that your app and campaign actions are set up in the dashboard correctly at engage.pxladdicts.com.
	
2.	Show the AppEngage dialog in your app.

3.	Call the AppEngage onDestroy function.

4.	Mark your engagement actions complete.

5.	Reward users when they claim their rewards.

6.	(optional) virtual currency verification.

More details on these steps follow:

####STEP 1 DETAILS: Set up App and Campaign on Dashbaord.
Your app’s publishing status should be set to live, and you should have created an engagement campaign.  The engagement campaign should have at least one silver action (For example: Play 1 Hand)

####STEP 2 DETAILS: Show the AppEngage dialog in your app.
You should show the dialog from at least two places in your app:

     i.  When the user starts the app. We recommend after your own promotional windows.
     
     ii. From a button in your app’s store, or wherever you have free virtual currency available.

To show the nGage achievements dialog call:
```Java
nGageANE.getInstance().showAchievements();
```


####STEP 3 DETAILS: Call the AppEngage onDestroy function.
When your application exits, call **onDestroy()**. Our recommended placement is in your app's Activity onDestroy function but anywhere will do as long as it is when the app exits. 
```Java
nGageANE.getInstance().onDestroy();
```

####STEP 4 DETAILS: Completing Actions.
To complete an action add the below line when the action requirements are completed in your app. Pass the action type as the parameter.
```Java
nGageANE.getInstance().completeAction("THE_ACTION");
```
 
	
Built in Engagement Actions:

| Action        | Description   |
| ------------- |:------------- |
| LevelUp      | Called each time your user levels up |
| Win      | Called each time your user wins      |
| Play |  Called each time your user plays a round      |
| Buy | Called each time your user buys an item      |
| Use | Called each time your user uses an item (i.e. power up)     |
| Share | Called each time your user shares on a social network     |

You can also create custom action types on the campaign editor.

####STEP 5 DETAILS: Receiving Rewards
Don't forget to reward your users with their virtual currency. Where your app resumes from an interrupt add the **getPendingRewards** call.
```Java
//Calls the server to check for rewards when the app resumes. The placement of this code is crucial to keep your users happy!
nGageANE.getInstance().getPendingRewards();
```

Add an event listener in the class which you want to handle rewards.  Also you must implement a method to handle this event.
```Java
nGageANE.getInstance().getPendingRewards.addEventListener(nGageANEEvent.REWARD, <YOUR_EVENT_HANDLER_METHOD>);
```
The nGageANEEvent passed to <YOUR_EVENT_HANDLER_METHOD> contains a property "reward" which is the reward amount and "server_token" your server confirmation token. "server_token" is optional in case you wish to server verify rewards. The event handler function will look something like this:

```Java
protected function rewardHandler(event:nGageANEEvent):void
{
	  if (event.reward>0)
		  trace( "You've received a reward of "+ event.reward+". Your server confirmation token is "+ event.server_token);
 
}
```
####Step 6 DETAILS: (Optional) Server Currency Verification
Publishers are able to verify currency claims by making a call to the following URL:
	http://engage.pxladdicts.com/engage/verifycurrencyclaimtoken/token/TOKEN_FROM_SDK
	Parameters:
	TOKEN_FROM_SDK - token is provided by the client-side SDK on every /engage/getpendingrewards call

Response:
	The API call returns JSON in the following format:
	{"result": {"token_verified": 0 or 1, i.e. is the token valid, "claimed": 0 or 1, i.e. has the token been claimed before, currency_amount":AMOUNT_OF_CURRENCY_AS_AN_INTEGER}}

To prevent fraud, you should give currency to the user only server-side, and only when token_verified is 1 and claimed is 0




##Showing Interstitials

We have incentivized and non-incentivized interstitials. 

For Non-incentivized call:
```Java
nGageANE.getInstance().showInterstitial();
```

For Incentivized call:
```Java
nGageANE.getInstance().showIncentedInterstitial();
```

If you've setup the Receive Rewards section above then you are ready to receive rewards from incentivized interstitial also. 

####(Optional) Interstitial Fill Callback 
You can optionally setup a callback for informational purposes. To do so implement **nGageInterstitialListener** with callback function.
```Java
protected function intersticialHandler(event:nGageInterstitialEvent):void
{
    trace("Intersticial received: " + event.ad_displayed +" "+ event.ad_error_code);	
}
```

Add an event listener in the class which you want to get intersticial information.  

```Java
nGageANE.getInstance().addEventListener(nGageInterstitialEvent.INTERSTITIAL, intersticialHandler);
```

####(Optional) Interstitial Close By Device Back Key

If you would like to set the device back key to close the interstitial you can optionally call: 

```Java
nGageANE.getInstance().onBackPressed()
```
which will close the interstitial if it's open. It also returns true if the interstitial was open and was closed successfully. If it returns false the interstitial was not showing and you can process the back key normally for your app. 

##(Optional) Proguard 
If you are using proguard add the following lines to your proguard.cfg file: 

```Java
-dontwarn com.tinidream.**
-keep class com.tinidream.** {*;}
```

