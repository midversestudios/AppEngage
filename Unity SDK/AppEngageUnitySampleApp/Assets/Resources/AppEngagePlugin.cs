using UnityEngine;
using System.Collections;


public class AppEngagePlugin {

	private static AndroidJavaObject androidPlugin;

	static  AppEngagePlugin()
	{
		if( Application.platform != RuntimePlatform.Android )
			return;

		
		using( var pluginClass = new AndroidJavaClass( "com.appengageplugin.AppEngagePlugin" ) )
			androidPlugin = pluginClass.CallStatic<AndroidJavaObject>( "getInstance" );
	}
	
	public static void onCreate( string apiKey )
	{
		if( Application.platform != RuntimePlatform.Android )
			return;
		 AndroidJavaClass _ActivityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject _ActivityObject = _ActivityClass.GetStatic<AndroidJavaObject>("currentActivity");
		androidPlugin.Call( "initializeNgage", apiKey,_ActivityObject );
		
	}
	
	public static void completeAction( string apiKey )
	{
		if( Application.platform != RuntimePlatform.Android )
			return;

		androidPlugin.Call( "completeAction", apiKey );
	}
	
	public static void completeAction (string actionID, int value)
	{
		if( Application.platform != RuntimePlatform.Android )
			return;

		androidPlugin.Call( "completeAction", actionID,value );
	}
	
	public static void getPendingRewards(  )
	{
		if( Application.platform != RuntimePlatform.Android )
			return;

		androidPlugin.Call( "getPendingRewards" );
	}
	
	public static void showIncentedInterstitial(  )
	{
		//currently not functional in unity

//		if( Application.platform != RuntimePlatform.Android )
//			return;
//
//		androidPlugin.Call( "showIncentedInterstitial" );
	}
	
	public static void showInterstitial(  )
	{
		//currently not functional in unity

//		if( Application.platform != RuntimePlatform.Android )
//			return;
//
//		androidPlugin.Call( "showInterstitial" );
	}
	
	
	public static void showAchievements()
	{
		if( Application.platform != RuntimePlatform.Android )
			return;

		androidPlugin.Call( "showAchievements" );
	}
	
	public static void OnDestroy()
	{
		if( Application.platform != RuntimePlatform.Android )
			return;

		androidPlugin.Call( "nGageDestroy" );
	}
 
	public static string  getDeviceID()
	{
		if( Application.platform != RuntimePlatform.Android )
			return "";
		
		return androidPlugin.Call<string>( "getDeviceID" );
	}
}
