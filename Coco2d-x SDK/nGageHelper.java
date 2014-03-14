package com.tinidream.ngagecoco;
//update package to your application package
 

import android.app.Activity;
import android.util.Log;

import com.tinidream.ngage.nGage;
import com.tinidream.ngage.nGageInterstitialListener;
import com.tinidream.ngage.nGageListener;

public class nGageHelper implements nGageListener,nGageInterstitialListener{

	private static nGage ngage;
	private static nGageHelper instance_;
    static Activity activity;
	static String tag="nGageX";
	 
	
	public static nGageHelper getInstance(){
		if(instance_==null){
			instance_ = new nGageHelper();
		}
		return instance_;
	}
	
	public static void setActivity(Activity _activity){
		
		activity=_activity;
	}
	 
	public static void startSession(String nGageAppKey){
		if (activity==null){
			Log.e(tag,"Activity is null. Please assign the 'activity' in you onCreate call of your Cocos2dxActivity class.");
		}else{
			getInstance(); 
			ngage = nGage.getInstance(); 
			ngage.onCreate(activity, nGageAppKey);
			ngage.setRewardListener(instance_);
			ngage.setInterstitialListener(instance_);
		}
	}
 
	public static void showAchievementsDialog(){
		if(ngage!=null){
			ngage.showAchievements();
		}
	}
	
	public static void completeAction (String actionID){
		if(ngage!=null){
			ngage.completeAction(actionID);
		}
	}
	 
	public static void completeActionValue (String actionID, String value){
		//if your action requires a value pass this.
		if(ngage!=null){
			ngage.completeAction(actionID,Integer.parseInt(value));
		}
	}
	
	public static void onDestroy() {
		//Clear static variables. Call when your app quits.
		if(ngage!=null){
			ngage.onDestroy();
		}
	}
	
	//Rewarding
	public static void getPendingRewards() {
		//Ping server for rewards. Call this whe your app resumes in your C++ code. Make sure your delegate has been setup.
		if(ngage!=null){
			ngage.getPendingRewards();
		}
	}

	@Override
	public void nGageReward(int reward, String currency_claim_token) {
		//forward to c++ nGageX class
		nGageRewardNative(reward,currency_claim_token);
	}
	
	public native void nGageRewardNative(int reward,String serverToken);

	
	//Interstitials
	public static void showInterstitial(String custom_text){
		if(ngage!=null){
			ngage.showInterstitial(custom_text);
		}
	}
	
	public static void showInterstitial(){
		if(ngage!=null){
			ngage.showInterstitial();
		}
	}
	
	
	public static void showIncentedInterstitial(){
		if(ngage!=null){
			ngage.showIncentedInterstitial();
		}
	}
	
	
	@Override
	public void nGageInterstitial(boolean displayed, String errorCode) {
		 //forward to c++ nGageX class
		nGageInterstitialNative(displayed,errorCode);
	}
	
	public native void nGageInterstitialNative(boolean displayed,String errorCode);
}
