package com.testapp;

 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tinidream.ngage.nGage;
import com.tinidream.ngage.nGageInterstitialListener;
import com.tinidream.ngage.nGageListener;

  

public class SampleActivity extends Activity implements nGageListener,nGageInterstitialListener{
	nGage ngage;
	RelativeLayout layout;
	MyView myview;
	 
	Activity activity;
	String AppEngageAppKey="<Your AppEngage SDK App Key>";
	String ngageMessage="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=this;
	//	setContentView(R.layout.activity_main);
	      requestWindowFeature(Window.FEATURE_NO_TITLE);
	        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON  |
    							 WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED  // turn on screen even if locked (easier development)
    							 | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
    							 | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON   
    			);
    	
    	
    	
		layout = new RelativeLayout(getApplicationContext());
		myview=new MyView(this);
	 
		
		ngage=nGage.getInstance();
		
		ngage.onCreate(activity, AppEngageAppKey);
  
		ngage.setRewardListener(this); // Setup callback to receive rewards
		
		ngage.setInterstitialListener(this); // Setup callback to receive interstitial information
		
		layout.addView(myview);
		setContentView(layout);

	}
	
	
 
	/**
	 * onDestroy - clear static variables and ensure nGage data is refreshed upon next launch
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		 ngage.onDestroy();
		 
	 }
	
	 /** 
	 * getPendingRewards will ping our servers ONCE to see if the user has any pending rewards. If some exist it will be return by the TrakerListener call back. 
	 */	
	@Override
	protected void onResume() {
		super.onResume(); 
		 ngage.getPendingRewards();
		 
	 }
	 
	/**
	  * nGageReward callback function receives pending rewards.
	  * @param reward - if >0 reward is passed then removed from the server
	  */
	@Override
	public void nGageReward(int reward, String currency_claim_token) {
		// Callback from getPendingRewards call
		if (reward>0){
			ngageMessage="Received reward="+reward+". Server confirmation token="+ currency_claim_token;
			Log.w("nGage", "You've received a reward of "+reward+". Your server confirmation token is "+ currency_claim_token);
		}
	}
	
	
	/** completeAction will tell our servers to that an action is complete.  
	 * @param actionID - the system action name that the user has completed. IE. checkIn, completeTutorial etc
	 */	
	public void completeAction (String actionID){
		ngage.completeAction(actionID);
		
	}
	 
	
	/** completeAction will tell our servers to that an action is complete.  
	 * @param actionID - the system action name that the user has completed. IE. checkIn, completeTutorial etc
	 * @param value - in case your action type requires a value to be checked like a high score or level number to reach.
	 */	
	public void completeAction (String actionID, int value){
		ngage.completeAction(actionID,value);
		
	}
	
	void showAdAlert(final String titleTxt,final String message){
		activity.runOnUiThread(new Runnable() {
		        public void run() {
		        	
					// 1. Instantiate an AlertDialog.Builder with its constructor
					 AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			
					 // 2. Chain together various setter methods to set the dialog characteristics
					 builder.setMessage(message); 
					 
					 TextView title = new TextView(activity);
					
					 // You Can Customise your Title here 
					title.setText(titleTxt);
					title.setBackgroundColor(Color.DKGRAY);
					title.setPadding(10, 10, 10, 10);
					title.setGravity(Gravity.CENTER);
					title.setTextColor(Color.WHITE);
					title.setTextSize(20);
	
					builder.setCustomTitle(title);
					 
					// Add the buttons 
					 builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int id) {
					             
					            }
					        });
			
					
					 
					 AlertDialog dialog = builder.create();
					 dialog.show();
		        }
		 });
	}

	@Override
	public void nGageInterstitial(boolean displayed, String errorCode) {
		ngageMessage="Intersticial displayed="+displayed+". Server Code="+errorCode+".";
		Log.w("nGage", ngageMessage);
		
	}	 
	
	@Override
	public void onBackPressed() {
		  
		try{
			//use Gage.getInstance() here in the case ngage instance variable is not initialized yet
			if(nGage.getInstance().onBackPressed() ) {
				return;
			}
			else {
				//Handle device's back button normally
				 
				System.exit(0);// same as activity.finsh but also clears static variables
				 
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	 

}
