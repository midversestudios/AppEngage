﻿using UnityEngine;
using System.Collections;


public class MidversePlugin {

	private static AndroidJavaObject androidPlugin;

	static  MidversePlugin()
	{
		if( Application.platform != RuntimePlatform.Android )
			return;

		
		using( var pluginClass = new AndroidJavaClass( "com.geniteam.midverseplugin.MidversePlugin" ) )
			androidPlugin = pluginClass.CallStatic<AndroidJavaObject>( "getInstance" );
	}
	
	public static void init( string apiKey )
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
	
	public static void callnGageOnDestroy()
	{
		if( Application.platform != RuntimePlatform.Android )
			return;

		androidPlugin.Call( "nGageDestroy" );
	}
}
