package com.bn.box2d.sndls;
import static com.bn.box2d.sndls.Constant.*;

import org.jbox2d.dynamics.Body;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//小猫
public class BodyCat extends MyPolygonImg
{
	static int YZ1=3;
	static int YZ2=28; 
	static int YZ3=53;
	static int YZ4=78;    
	

	
	public BodyCat(Body body,Bitmap[] bma,float width,float height,GameView gv)
	{    
		super(body,bma,width,height,gv);
	}
	
	int count=0;
	//碰撞后执行不同的动作
	@Override
	public void doAction(float x,float y)
	{  		
		if(count==0)
		{
			SCORE=SCORE+100;
			gv.scoreList.add(new Score(Y_NUM_ARRAY,100,x,y));
		}
		count++;
	}
	
	public void drawSelf(Canvas canvas,Paint paint)
	{
		super.drawSelf(canvas, paint);
		
		if(count>0)
		{
			count++;
		}
     
		float x=body.getWorldCenter().x*RATE;
		float y=body.getWorldCenter().y*RATE;
		
		if(count>=YZ2&&count<=YZ3&&bmIndex==1)
		{
			SCORE=SCORE+100;
			gv.scoreList.add(new Score(Y_NUM_ARRAY,100,x,y));
		}
		else if(count>=YZ3&&count<YZ4&&bmIndex==2)
		{
			SCORE=SCORE+100;
			gv.scoreList.add(new Score(Y_NUM_ARRAY,100,x,y));
		}
		else if(count>YZ4&&this.isLive)
		{
			this.isLive=false;	
			SCORE=SCORE+5000;
			gv.scoreList.add(new Score(Y_NUM_ARRAY,5000,x,y));
			
			long tempST=System.currentTimeMillis();
			if(tempST-timeStamp>1000)
			{
				gv.activity.soundutil.playSound(5, 0);
				timeStamp=tempST;
			}
			
		}
	}
}
