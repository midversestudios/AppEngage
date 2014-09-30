##Prerequisites
Clone this repo or [download here](https://github.com/midversestudios/AppEngage/archive/master.zip)

##Starting up the Cocos2d-x SDK AppEngage SDK

-1. Get the the latest SDK and extract the zip. Here you will find:

	nGageX.cpp/h - Coco2D-x JNI nGage wrapper
	nGageHelper.java - The java JNI nGage wrapper that connects to nGageX.cpp/h
	nGageSDK_Vxxx.zip - Latest Android nGage SDK
	

 

-2. Lets setup the Android side of the Android nGageSDK project library. Unzip the Android nGageSDK_Vxxx.zip. 

-3. Add the nGage SDK to your project. Since Android does not allow packing resources directly into a library file you must add the nGage Android project. In eclipse, Import 'nGage' project from the SDK zip file. Go to your apps Project Properties and select Android menu item on left. On the right you will see a 'Library' section. Select the 'Add' button and find the android project 'nGage'. 

-4 Add the Google Play Services Library Project (GPSLP): GPSLP is a library project required for retrieving the new Advertising ID required by Google. Instructions for adding (GPSLP) can be found here http://developer.android.com/google/play-services/setup.html

-5. In your apps Manifest file add the lines inside the <application> tag:
```Java
 <application …>
	…
 
      <meta-data  android:name="com.google.android.gms.version"  android:value="@integer/google_play_services_version" />

      <activity android:screenOrientation="sensorLandscape" android:configChanges="keyboardHidden|orientation" android:name="com.tinidream.ngage.nGageActivity"/>
	…
</application>
``` 

-6. Also in the Manifest, add attribute ```android:launchMode="singleTask"``` to your apps starting activity tag. 
For example, you will have something like ```<activity android:name="com.company.appname.startingActivity" … android:launchMode="singleTask"/>```
```
Note: Make sure the nGage project has a Target Android Version of 3.2 or higher. Minimum Android version can be as low as 2.1.
```

-7. Let add the Java JNI wrapper. Copy the nGageHelper.java to your Coco2D-x "proj.android/src" folder. Open you activity class and add ```nGageHelper.setActivity(this);``` as described below.
```Java
	//public static Cocos2dxActivity activity;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		nGageHelper.setActivity(this);
	}
```

-8. Copy ```nGageHelper``` to your Android projects src folder. Same folder as the Activity class file.

-9. Now let add the Coco JNI wrapper classes. Copy the nGage.cpp and .h wrapper files to you Coco2D-x project. 



##Initializing AppEngage Coco2D-x SDK 

In your Coco2D-x ```AppDelegate.cpp``` class function ```applicationDidFinishLaunching``` call  **startnGageSession**  and pass it your app's AppEngage API Key. You can find your SDK Key on the our dashboard once you have setup a company account and created an app.

```Java
 nGageX::startnGageSession("<Your apps nGage SDK Key");
```

##Setting up your device for testing 

Before you begin, make sure your application is set up correctly on the AppEngage dashboard at engage.pxladdicts.com. Add your test device’s Advertising ID to the list of test devices on the AppEngage dashboard. 

2.	To get your Advertising ID:

	a.	Run the sample app which displays the Advertising ID. 
	
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
nGageX::showAchievementsDialog();
```
####STEP 3 DETAILS: Call the AppEngage onDestroy function.
When your application exits, call function **nGageX::onDestroy();**. Any location will do as long as it is the last call before the app exits. 
```Java
nGageX::onDestroy(); 
```

####STEP 4 DETAILS: Completing Actions.
To complete an action add the below line when the action requirements are completed in your app. Pass the action type as the parameter.

```Java
nGageX::completeAction(<actionID>);
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
Don't forget to reward your users with their virtual currency. In your apps Activity **onResume** function add the following code.

```Java
//Calls the server to check for rewards when the app resumes. The placement of this code is crucial to keep your users happy!
nGageX::getPendingRewards();
```

Create an **nGageXDelegate** instance in the class you wish to receive reward callbacks.
```Java
 nGageXDelegate *callbackReward; //in your h file
```

Pass that class instance to **setDelegate**:
```Java
  callbackReward=new nGageXDelegate();
  nGageX::setDelegate(callbackReward);
```

Add the callback function to reward your user:
```Java
void nGageXDelegate::onReceivenGageReward(int reward,const char* currency_claim_token){
  if (reward>0){
  	CCLog("You've received a reward of %d", reward);
  	CCLog("Your server confirmation token is %s", currency_claim_token);
  }
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
nGageX::showInterstitial()
```

For Incentivized call:
```Java
nGageX::showIncentedInterstitial();
```

If you've setup the Receive Rewards section above then you are ready to receive rewards from incentivized interstitial also. 

####(Optional) Interstitial Fill Callback 

You can optionally setup a callback for informational purposes. To do so implement **nGageXInterstitialDelegate** with callback function.

Create an **nGageXInterstitialDelegate** instance in the class you wish to receive intersticial callbacks.
```Java
nGageXInterstitialDelegate *callbackInterstitial;
```

Pass that class instance to **setInterstitialDelegate**:
```Java
 callbackInterstitial=new nGageXInterstitialDelegate();
 nGageX::setInterstitialDelegate(callbackInterstitial);
```

Add the callback function:
```Java
 void nGageXInterstitialDelegate::onReceiveInterstitialInfo(bool displayed,const char* errorCode){
 	CCLog("Did the interstitial display? %d", displayed);
	CCLog("Was there an an error code? %s", errorCode);
 }
```

####(Optional) Interstitial Close By Device Back Key

If you would like to set the device back key to close the interstitial you can optionally call:

```Java
 nGageX::onBackPressed();
```
which will close the interstitial if it's open. It also returns true if the interstitial was open and was closed successfully. If it returns false the interstitial was not showing and you can process the back key normally for your app.  

##Proguard (optional)
If you are using proguard add the following lines to your proguard.cfg file: 

```Java
-dontwarn com.tinidream.**
-keep class com.tinidream.** {*;}
```

