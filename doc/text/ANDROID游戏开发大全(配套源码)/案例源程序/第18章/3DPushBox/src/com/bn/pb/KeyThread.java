package com.bn.pb;

//��������״̬���߳�
public class KeyThread extends Thread
{
	MySurfaceView mv; 
	boolean flag=true;
	public KeyThread(MySurfaceView mv)
	{
		this.mv=mv;
	}
	
	public void run() 
	{
		while(flag)
		{
			if(mv.keyState==1&&mv.robotgroup.robot.flag==true) 
			{//��UP������
				
				if(mv.robotgroup.yAngle==0)//z���������˶�
        		{
					mv.zRightDirection();
        			
        		}
        		else if(mv.robotgroup.yAngle==180)//z�Ḻ����
        		{
        			mv.znegativeDirection();
        			
        		}else if(mv.robotgroup.yAngle==270)//x�Ḻ����
        		{
        			mv.xnegativeDirection();
        			
        		}else if(mv.robotgroup.yAngle==90)//x��������
        		{
        			mv.xRightDirection();
        		}
			}
			
			try {Thread.sleep(100);
				} catch (InterruptedException e) 
				{e.printStackTrace();}
		}
	}
}
