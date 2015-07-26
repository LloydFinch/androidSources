package com.bn.box2d.blockl;
import java.util.ArrayList;
import org.jbox2d.dynamics.Body;

public class BodySearchUtil 
{
	public static void doAction(Body body1,Body body2,ArrayList<MyBody> bl)
	{
		for(MyBody mpi:bl)
		{	
			if(body1==mpi.body||body2==mpi.body)
			{
				if(mpi instanceof MyRectColor)
				{
					MyRectColor mrc=(MyRectColor)mpi;
					if(mrc.isBlock)
					{
						mrc.isLive=false;
					}
				}
			}
		}
	}
}
