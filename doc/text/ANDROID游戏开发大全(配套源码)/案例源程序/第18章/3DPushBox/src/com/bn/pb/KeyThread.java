package com.bn.pb;

//监听键盘状态的线程
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
			{//有UP键按下
				
				if(mv.robotgroup.yAngle==0)//z轴正方向运动
        		{
					mv.zRightDirection();
        			
        		}
        		else if(mv.robotgroup.yAngle==180)//z轴负方向
        		{
        			mv.znegativeDirection();
        			
        		}else if(mv.robotgroup.yAngle==270)//x轴负方向
        		{
        			mv.xnegativeDirection();
        			
        		}else if(mv.robotgroup.yAngle==90)//x轴正方向
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
