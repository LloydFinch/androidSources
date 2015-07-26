package com.bn.box2d.sndls;

import static com.bn.box2d.sndls.Constant.*;

public class MainMenuDrawThread extends Thread
{
	static float syoffset;	//上移
	static float xyoffset;	//下移   
	static float zyoffset;	//右移
	static float yyoffset;	//左移
	static float bdxoffset;//地面的位置    草地的位置
	static float bgxoffset;//背景的位置
	MainMenuView mmv;
	float degrees;
	public MainMenuDrawThread(MainMenuView tjview)
	{
		this.mmv=tjview;
	}
	@Override
	public void run()
	{
		while(MAIN_DRAW_THREAD_FLAG)
		{
			bdxoffset+=2f*yMainRatio;
			bgxoffset+=1f*yMainRatio;
			mmv.repaint();//重新绘制
			try
			{
				Thread.sleep(SLEEPTIME);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			//上移
			if(mmv.flag==1)
			{  
				syoffset+=60*yMainRatio;
				degrees=(float)(Math.toDegrees(syoffset))/1.3f;
			}
			//下移   
			if(mmv.flag==2)
			{
				xyoffset+=60*yMainRatio;
				degrees=(float)(Math.toDegrees(-xyoffset))/1.3f;
			}
			//左移
			if(mmv.ckisTouch)
			{
				zyoffset+=30*yMainRatio;
			}
			if(mmv.isClose)
			{
				yyoffset+=30*yMainRatio;
			}
			try
			{
				Thread.sleep(15);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}