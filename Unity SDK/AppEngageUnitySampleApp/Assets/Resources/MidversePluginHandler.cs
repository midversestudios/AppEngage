using UnityEngine;
using System.Collections;

public class MidversePluginHandler : MonoBehaviour {
	static bool initialized = false;
	SampleAppScript instance;
	void Start () {
		DontDestroyOnLoad(this);
		instance=GameObject.Find("Main Camera").GetComponent("SampleAppScript") as SampleAppScript;
	}
	
 
	
	
	void OnEnable()
	{
		Debug.Log ("OnEnable");
		MidversePluginManager.nGageRewardEvent += nGageRewardEvent;
		MidversePluginManager.nGageInterstitialEvent += nGageInterstitialEvent;
		
	}
	
	void OnDisable()
	{
		Debug.Log ("OnDisable");
		MidversePluginManager.nGageRewardEvent -= nGageRewardEvent;
		MidversePluginManager.nGageInterstitialEvent -= nGageInterstitialEvent;
	}
	
	
	void nGageRewardEvent( int reward, string currencyClaimToken )
	{
		instance.reward = reward;
		Debug.Log( "nGageRewardEvent reward="+reward+" currencyClaimToken="+currencyClaimToken );

	}
	
	void nGageInterstitialEvent( bool displayed, string errorCode )
	{
		//Debug.LogError( "displayed="+displayed+" errorCode="+errorCode );
	}
	
	
}
