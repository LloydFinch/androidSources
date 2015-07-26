package com.bn.box2d.sndls;
import java.util.ArrayList;
import org.jbox2d.dynamics.Body;

import static com.bn.box2d.sndls.Constant.*;

public class BodySearchUtil 
{
	public static void doAction(GameView gv,Body body1,Body body2,ArrayList<MyPolygonImg> bl,float x,float y)
	{		
		if(gv.hero==null)
		{
			return;
		}		
		Body heroBody=gv.hero.body;
		for(MyPolygonImg mpi:bl)
		{
			if(!START_SCORE)
			{
				if(body1==heroBody||body2==heroBody)
				{
					START_SCORE=true;
				}
			}			
			if(START_SCORE)
			{				
				if(body1==mpi.body||body2==mpi.body)
				{
					mpi.doAction(x,y);
				}
			}
		}
	}
}
