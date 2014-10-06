
using System;
using UnityEngine;
 
public class SampleAppScript: MonoBehaviour
{
	int width, height;
	float scalex,scaley;
	public GUIStyle centeredStyle;
	public GUIStyle buttonStyle;
	 
	public int reward=0;
	String app_key = "Add your AppEngage App Key here";//"Add your AppEngage App Key here";
	String getDeviceID="";

	void Start(){
		DontDestroyOnLoad(this);
		height= Screen.height;
		width= Screen.width;
		 
		scalex=((float)width)/1024;
		scaley=((float)height)/768;
	  
		centeredStyle.alignment = TextAnchor.UpperCenter;
		centeredStyle.fontStyle = FontStyle.Bold;
		centeredStyle.fontSize = 22;

		AppEngagePlugin.onCreate (app_key); //call once on startup
		this.getDeviceID = AppEngagePlugin.getDeviceID ();
		Debug.Log ("unityStart");
	}


	void OnGUI () {
		buttonStyle = GUI.skin.GetStyle ("Button");
		buttonStyle.fontSize = 19;
		float xPos = width * .2f;
		int yPos = scaleY (50);
		float buttonWidth = width * .6f;
		int buttonHeight = scaleY (80);

		GUI.Label (new Rect (0, scaleY (50), width, buttonHeight), "AppEngage Sample App ", centeredStyle);

		//Show AppEngage Dialog
		yPos += scaleY (100);
		if (GUI.Button (new Rect (xPos, yPos, buttonWidth, buttonHeight), "Show AppEngage Dialog", buttonStyle)) {
			AppEngagePlugin.showAchievements ();
		}
 
		//complete AppEngage action
		yPos += scaleY (120);
		if (GUI.Button (new Rect (xPos, yPos, buttonWidth, buttonHeight), "Complete Action 'play'", buttonStyle)) {
			AppEngagePlugin.completeAction ("play");
		}

		yPos += scaleY (120);
		if (GUI.Button (new Rect (xPos, yPos, buttonWidth, buttonHeight), "Show Interstitial", buttonStyle)) { 
			AppEngagePlugin.showInterstitial("Custom Text");
		}
  
		//Quit app with proper AppEngage call
		yPos += scaleY (120);
		if (GUI.Button (new Rect (xPos, yPos, buttonWidth, buttonHeight), "Quit", buttonStyle)) {
			 
			Application.Quit ();
		}
		yPos += scaleY (120);
		GUI.Label (new Rect (0, yPos, width, scaleY(100)), "Your Device ID = "+getDeviceID ,centeredStyle);

		yPos += scaleY (100);
		if (app_key.Contains ("Add your AppEngage")) {
			GUI.Label (new Rect (0, yPos, width, scaleY(100)), "Add your AppEngage app key to SampleAppScript" ,centeredStyle);
		}
		else if (reward>0)
			GUI.Label (new Rect (0, yPos, width, scaleY(100)), "User Reward ="+reward ,centeredStyle);
	}

	void OnApplicationPause(bool pauseStatus) {
		//Call AppEngage getPendingRewards when app resumes
		if (!pauseStatus) {
			reward=0;
			AppEngagePlugin.getPendingRewards ();
			Debug.Log ("OnApplicationPause");
		}
	}

	int scaleX(int x){
		return ((int) (x*scalex));
	}
	int scaleY(int y){
		return ((int) (y*scaley));
	}


}
 

