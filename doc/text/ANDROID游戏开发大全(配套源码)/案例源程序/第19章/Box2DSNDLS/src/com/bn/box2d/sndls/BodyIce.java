package com.bn.box2d.sndls;
import static com.bn.box2d.sndls.Constant.*;

import org.jbox2d.dynamics.Body;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

//冰块
public class BodyIce extends MyPolygonImg
{
	static int YZ1=3;
	static int YZ2=18; 
	static int YZ3=33;
	static int YZ4=48;

	
	public BodyIce(Body body,Bitmap[] bma,float width,float height,GameView gv)
	{    
		super(body,bma,width,height,gv);
	}
	
	int count=0;
	//碰撞后执行不同的动作
	@Override
	public void doAction(float x,float y)
	{  
		count++;
		if(bmIndex==0)
		{
			bmIndex=1;	
			SCORE=SCORE+200;
			gv.scoreList.add(new Score(Y_NUM_ARRAY,200,x,y));
		}			
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
			bmIndex=2;
			SCORE=SCORE+300;
			gv.scoreList.add(new Score(Y_NUM_ARRAY,300,x,y));
		}
		else if(count>=YZ3&&count<YZ4&&bmIndex==2)
		{
			bmIndex=3;
			SCORE=SCORE+300;
			gv.scoreList.add(new Score(Y_NUM_ARRAY,300,x,y));
		}
		else if(count>YZ4&&this.isLive)
		{
			this.isLive=false;	
			SCORE=SCORE+500;
			gv.scoreList.add(new Score(Y_NUM_ARRAY,500,x,y));
			long tempST=System.currentTimeMillis();
			if(tempST-timeStamp>800)
			{
				gv.activity.soundutil.playSound(3, 0);
				timeStamp=tempST;
			}			
		}
	}
}
