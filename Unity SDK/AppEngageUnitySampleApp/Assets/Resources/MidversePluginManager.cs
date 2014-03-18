using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;
using MiniJSON;


public class MidversePluginManager:MonoBehaviour {

	

	// Fired when receives pending rewards.
	public static event Action<int,string> nGageRewardEvent;

	

	public static event Action<bool,string> nGageInterstitialEvent;

	

	


	


	public void nGageReward( string rewardResp )
	{
		Debug.Log("nGageReward reward response="+rewardResp);
		  IDictionary search = (IDictionary) Json.Deserialize(rewardResp);
		
		string currencyClaimToken=search["currency_claim_token"].ToString();
		Debug.LogError("currencyClaimToken ="+currencyClaimToken);
		
		int reward= Convert.ToInt32(search["reward"].ToString());
		
		Debug.LogError("reward ="+reward);
		
		
		nGageRewardEvent(reward,currencyClaimToken);
	}

	public void nGageInterstitial( string adResp )
	{
		
		
		
//		  IDictionary search = (IDictionary) Json.Deserialize(adResp);
//		bool displayed=(bool)search["displayed"];
//		string errorCode=search["errorCode"].ToString();
//		Debug.LogError("displayed ="+displayed);
//		Debug.LogError("errorCode ="+errorCode);
//		
//		nGageInterstitialEvent(displayed,errorCode);
	}
}
