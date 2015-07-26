package com.bn.box2d.blockl;
import static com.bn.box2d.blockl.Constant.*;
//绘制线程
public class DrawThread extends Thread
{
	GameView gv;
	public DrawThread(GameView gv)
	{
		this.gv=gv;
	}
	
	@Override
	public void run()
	{
		while(DRAW_THREAD_FLAG)
		{
			gv.activity.world.step(TIME_STEP, ITERA);//开始模拟
			gv.repaint();

			try 
			{
				Thread.sleep(10);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			for(MyBody mpi:gv.activity.bl)
			{
				if(mpi instanceof MyRectColor)
				{
					MyRectColor mrc=(MyRectColor)mpi;
					if(!mrc.isLive&&!mrc.isDeleted)
					{
						mrc.isDeleted=true;
						gv.activity.world.destroyBody(mrc.body);
					}	
				}							
			}
			
			try 
			{
				Thread.sleep(5);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
