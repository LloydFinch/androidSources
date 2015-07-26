package com.bn.box2d.sndls;
import org.jbox2d.dynamics.Body;
import android.graphics.Bitmap;
import static com.bn.box2d.sndls.Constant.*;

//ľͷ��
public class BodyWood extends MyPolygonImg
{
	static int YZ1=3;
	static int YZ2=6;
	
	public BodyWood(Body body,Bitmap[] bma,float width,float height,GameView gv)
	{
		super(body,bma,width,height,gv);
	}
	
	int count=0;
	//��ײ��ִ�в�ͬ�Ķ���
	@Override
	public void doAction(float x,float y)
	{
		count++;
		if(count>YZ1&&count<YZ2&&bmIndex==0)
		{
			bmIndex=1;	
			SCORE=SCORE+200;   
			gv.scoreList.add(new Score(P_NUM_ARRAY,200,x,y));
		}
		else if(count>=YZ2&&bmIndex==1)
		{
			bmIndex=2;
			SCORE=SCORE+200;
			gv.scoreList.add(new Score(P_NUM_ARRAY,200,x,y));		
			long tempST=System.currentTimeMillis();
			if(tempST-timeStamp>800)
			{
				gv.activity.soundutil.playSound(2, 0);
				timeStamp=tempST;
			}
		}
		
	}
}
