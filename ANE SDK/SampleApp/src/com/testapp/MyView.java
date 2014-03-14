package com.testapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.tinidream.ngage.nGage;



public class MyView extends View {
	nGage ngage;
	int width,height;
	Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
	Paint paintButton = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
	Paint paintBG = new Paint();
	final float fontScale = getContext().getResources().getDisplayMetrics().density;
 
	SampleActivity sampleActivity;
	RectF rect1,rect2,rect3;
	
	public MyView (SampleActivity sampleActivity){
		super (sampleActivity);
		this.sampleActivity=sampleActivity;
		ngage=nGage.getInstance();
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN ) {

			if (event.getY()<height/3 && event.getY()>height*.1f){
				sampleActivity.ngageMessage="";
				if (ngage.getMessage()!=null && !ngage.getMessage().equals("No data available."))
					sampleActivity.ngageMessage="Server message: "+ngage.getMessage();
				
				ngage.showAchievements();
				
			} else if (event.getY()<height*2/3){
				sampleActivity.ngageMessage="";
				ngage.showInterstitial();
				
			} else if (event.getY()<height*.9f){
				sampleActivity.ngageMessage="";
				ngage.showIncentedInterstitial();
			}
		
		}
		return true;
	}
	
	@Override
	 protected void onSizeChanged(int widthMeasureSpec, int heightMeasureSpec, int xOld, int yOld){
		//called once on orientation change
		
	     super.onSizeChanged(widthMeasureSpec, heightMeasureSpec, xOld, yOld);
    
	    width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		    
		paintHeader.setColor(Color.WHITE);
		paintHeader.setStrokeWidth(2);
		paintHeader.setTextAlign(Align.CENTER); 
		paintHeader.setTextSize(scaleF(17));
		paintHeader.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
		 
		paintBG.setColor(Color.BLACK);
		
		paintButton.setColor(0xff5e656b);
		
		rect1=new RectF(width*.05f,height*.1f, width*.95f, height/3);
		rect2=new RectF(width*.05f,rect1.bottom+10, width*.95f, height*2/3-10);
		rect3=new RectF(width*.05f,rect2.bottom+10, width*.95f, height*.9f);
	}
		
	@Override	 
	protected void onDraw(Canvas c) {
	        super.onDraw(c);
	        
	        c.drawRect(0, 0, width, height, paintBG);
	       
	        paintHeader.setColor(Color.RED);
	        
	        c.drawText("AppEngage Dashboard Test Device ID:"+ngage.getDeviceID(), width/2, paintHeader.getTextSize(), paintHeader);
	       
	        if (sampleActivity.AppEngageAppKey.contains("<"))
	        	 c.drawText("Enter your AppEngage App Key from the dashboard into the sample app."  , width/2, height*.98f, paintHeader);	       
	        else 
	        	 c.drawText(sampleActivity.ngageMessage , width/2, height*.98f, paintHeader);
	        	
	        paintHeader.setColor(Color.WHITE);
	        
	        c.drawRoundRect(rect1, 10, 10, paintButton);
	        c.drawRoundRect(rect2, 10, 10, paintButton);
	        c.drawRoundRect(rect3, 10, 10, paintButton);
	        c.drawText("Touch here to open AppEngage Apps Dialog", width/2, height/4, paintHeader);
	        c.drawText("Touch here to open AppEngage non-incentivised Intersticial", width/2, height*2/4, paintHeader);
	        c.drawText("Touch here to open AppEngage incentivised Intersticial", width/2, height*3/4+ paintHeader.getTextSize(), paintHeader);
	  
	        
	        invalidate();
	}
	
	int scaleF(int pixels){ 
	    return (int) (pixels * fontScale + 0.5f);
	       
	}
		
}
