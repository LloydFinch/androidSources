package com.bn.box2d.jx;
import static com.bn.box2d.jx.Constant.*;
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
			//ʹ���ĳ���������ת
			float angle=gv.activity.rj1.getJointAngle();
			if(angle<(float) (-0.95))
			{
				gv.activity.rj1.setMotorSpeed((float)(Math.PI/2));
			}
			else if(angle>(float) (0.95))
			{
				gv.activity.rj1.setMotorSpeed((float)(-Math.PI/2));
			}
			try 
			{
				Thread.sleep(150);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
