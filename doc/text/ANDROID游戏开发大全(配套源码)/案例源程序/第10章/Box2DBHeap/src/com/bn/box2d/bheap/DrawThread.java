package com.bn.box2d.bheap;
import static com.bn.box2d.bheap.Constant.*;

//�����߳�
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
			gv.activity.world.step(TIME_STEP, ITERA);//��ʼģ��
			gv.repaint();
			try 
			{
				Thread.sleep(20);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
