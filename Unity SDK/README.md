##Prerequisites
Clone this repo or [download here](https://github.com/midversestudios/AppEngage/archive/master.zip)


##Starting up the Unity AppEngage SDK

1. Get the the latest SDK and extract the zip. Here you will find:

	AppEngageUnityPlugin - Unity android resource project and includes the ngageSDK.jar library
	
	AppEngageUnitySampleApp - Sample Unity SDK project 


2. Add AppEngage to your project: In the Assets menu select Import Package and select the AppEngageUnityPlugin. 

3. In your Plugins/Android folder use the given AndroidManifest file or add the below lines to your existing AndroidManifest inside the <application> tag:
```Java
 <application …>
	…

	   <meta-data  android:name="com.google.android.gms.version"  android:value="@integer/google_play_services_version" />

      <activity android:screenOrientation="sensorLandscape" android:configChanges="keyboardHidden|orientation|screenSize" android:name="com.tinidream.ngage.nGageActivity"/>
		 
      <activity android:name="com.unity3d.player.UnityPlayerProxyActivity" android:launchMode="singleTask" android:label="@string/app_name"  android:configChanges="fontScale|keyboard|keyboardHidden|locale|mnc|mcc|navigation|orientation|screenLayout|screenSize|smallestScreenSize|uiMode|touchscreen" android:screenOrientation="landscape">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
     </activity>
        
     <activity android:name="com.unity3d.player.UnityPlayerActivity" android:launchMode="singleTask" android:label="@string/app_name" android:configChanges="fontScale|keyboard|keyboardHidden|locale|mnc|mcc|navigation|orientation|screenLayout|screenSize|smallestScreenSize|uiMode|touchscreen"/>
    
    <activity android:name="com.unity3d.player.UnityPlayerNativeActivity" android:launchMode="singleTask" android:label="@string/app_name" android:configChanges="fontScale|keyboard|keyboardHidden|locale|mnc|mcc|navigation|orientation|screenLayout|screenSize|smallestScreenSize|uiMode|touchscreen">
      <meta-data android:name="unityplayer.ForwardNativeEventsToDalvik" android:value="true" />
    </activity>
	…
</application>
``` 
Also in the Manifest, add attribute ```android:launchMode="singleTask"``` to your apps starting activity tag. 
For example, you will have something like ```<activity android:name="com.company.appname.startingActivity" … 	android:launchMode="singleTask"/>```

`4. Call the **onCreate** function when your app starts with your app's AppEngage API Key. You can find your SDK Key on the our dashboard once you have setup a company account and created an app.

```Java
AppEngagePlugin.onCreate ("YOUR_APP_API_KEY");
```

##Setting up your device for testing 

Before you begin, make sure your application is set up correctly on the AppEngage dashboard at engage.pxladdicts.com. Add your test device’s Advertising ID to the list of test devices on the AppEngage dashboard. 

2.	To get your Advertising ID:

	a.	Run the sample app which displays the Advertising ID. 
	
	b.	Or you can download the following app https://play.google.com/store/apps/details?id=com.evozi.deviceid
  	
  	WARNING: If you use the wrong ID or don’t enter it correctly in the dashboard, the dialogs won't display correctly.
3.	Run the sample app, you should be able to see how the AppEngage dialog looks.


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
AppEngagePlugin.showAchievements();
```

####STEP 3 DETAILS: Call the AppEngage onDestroy function.
When your application exits, call function **OnDestroy()**. Our recommended placement is in your app's Activity onDestroy function but anywhere will do as long as it is when the app exits. 
```Java
AppEngagePlugin.OnDestroy ();
```

####STEP 4 DETAILS: Completing Actions.
To complete an action add the below line when the action requirements are completed in your app. Pass the action type as the parameter.

```Java
AppEngagePlugin.completeAction("THE_ACTION");
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

####STEP 5 DETAILS: Receiving Rewards
Check if AppEngage rewards exist when your app resumes from an interrupt. In Unity's system function OnApplicationPause you can do just that.

```Java
void OnApplicationPause(bool pauseStatus) {
	//Call AppEngage getPendingRewards when app resumes
	if (!pauseStatus) { 
		AppEngagePlugin.getPendingRewards (); 
	}
}
```

A callback function nGageRewardEvent in script file AppEngagePluginHandler which will be called if a reward needs to be given to the user.
```Java
  public void nGageReward(int reward, String currency_claim_token) {
	  // Callback from getPendingRewards call
	  Debug.Log( "User reward="+reward+" server currency claim token="+currencyClaimToken );
	  
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

We have incentivized and non-incentivized interstitials. However, they will be available in a future Unity SDK release.


##Sample App

If you have any issues take a look at how the SampleApp works. If you still having issues contact your representative with specific questions and we will be happy to help.
