##Prerequisites
Clone this repo or [download here](https://github.com/midversestudios/AppEngage/archive/master.zip)


##Starting up the Android nGage SDK

1. Get the the latest SDK and extract the zip. Here you will find:

	nGage- android resource project and includes the ngageSDK.jar  library
	SampleApp - Sample SDK project 


2. Add nGage resource to the project: -  Since Android does not allow packing resources directly into a library file you must add the nGage Android project. In eclipse, Import 'nGage' project from the SDK zip file. Go to your apps Project Properties and select Android menu item on left. On the right you will see a 'Library' section. Select the 'Add' button and find the android project 'nGage'. 

	Note: Make sure the nGage project has a Target Android Version of 3.2 or higher. Minimum Android version can be as low as 2.1.


3. In your apps Manifest file add the lines inside the <application> tag:
```Java
 <application …>
	…

	 <service android:name="org.openudid.OpenUDID_service">
			 <intent-filter>
				<action android:name="org.openudid.GETUDID"/>
			</intent-filter>
	</service>

      <activity android:screenOrientation="sensorLandscape" android:configChanges="keyboardHidden|orientation" android:name="com.tinidream.ngage.nGageActivity"/>
	…
</application>
``` 
Also in the Manifest, add attribute ```android:launchMode="singleTask"``` to your apps starting activity tag. 
For example, you will have something like ```<activity android:name="com.company.appname.startingActivity" … 	android:launchMode="singleTask"/>```

`4. Call the **onCreate** function with your app's Activity and your app's AppEngage API Key. You can find your SDK Key on the our dashboard once you have setup a company account and created an app.

```Java
nGage.getInstance().onCreate(this, "YOUR_APP_API_KEY");
```

##Setting up your device for testing 

Before you begin, make sure your application is set up correctly on the AppEngage dashboard at engage.pxladdicts.com. Add your test device’s Android ID to the list of test devices on the AppEngage dashboard. 

2.	To get your Android ID:

	a.	Run the sample app which displays the Android ID. 
	
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
nGage.getInstance().showAchievements();
```

####STEP 3 DETAILS: Call the AppEngage onDestroy function.
When your application exits, call function **onDestroy()**. Our recommended placement is in your app's Activity onDestroy function but anywhere will do as long as it is when the app exits. 
```Java
nGage.getInstance().onDestroy();
```

####STEP 4 DETAILS: Completing Actions.
To complete an action add the below line when the action requirements are completed in your app. Pass the action type as the parameter.

```Java
nGage.getInstance().completeAction("THE_ACTION");
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
Don't forget to reward your users with their virtual currency. In your apps Activity **onResume** function add the following code.

```Java
//Calls the server to check for rewards when the app resumes. The placement of this code is crucial to keep your users happy!
nGage.getInstance().getPendingRewards();
```

Implement the nGageListener to the class you wish to receive your callback on.
```Java
public class myClassWhereIwantReward implements nGageListener
```

Pass that class instance to 'setRewardListener':
```Java
nGage.getInstance().setRewardListener(this);
```

Add the callback function to reward your user:
```Java

@Override
  public void nGageReward(int reward, String currency_claim_token) {
	  // Callback from getPendingRewards call
	  if (reward>0)
		  Log.w("nGage", "You've received a reward of "+reward+". Your server confirmation token is "+ currency_claim_token);
	  
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
nGage.getInstance().showInterstitial();
```

For Incentivized call:
```Java
nGage.getInstance().showIncentedInterstitial();
```

If you've setup the Receive Rewards section above then you are ready to receive rewards from incentivized interstitial also. 

####(Optional) Interstitial Fill Callback 

You can optionally setup a callback for informational purposes. To do so implement **nGageInterstitialListener** with callback function.

Pass the class instance implementing 'nGageInterstitialListener' to 'setInterstitialListener':
```Java
nGage.getInstance().setInterstitialListener(<classInstance>);
```
Then create the callback function 'nGageInterstitial':

```Java
@Override
void nGageInterstitial(boolean displayed, String errorCode){
	//param displayed - If true then the ad was shown and errorCode will be null. If false then no inventory was available or some other server error occurred.
	//param errorCode - errorCode returns a server code prompt for debugging.
}
```
####(Optional) Interstitial Close By Device Back Key

If you would like to set the device back key to close the interstitial you can optionally call: 

```Java
nGage.getInstance().onBackPressed()
```
which will close the interstitial if it's open. It also returns true if the interstitial was open and was closed successfully. If it returns false the interstitial was not showing and you can process the back key normally for your app. 

##(Optional) Proguard 
If you are using proguard add the following lines to your proguard.cfg file: 

```Java
-dontwarn com.tinidream.**
-keep class com.tinidream.** {*;}
```

##Sample App

If you have any issues take a look at how the SampleApp works. If you still having issues contact your representative with specific questions and we will be happy to help.
