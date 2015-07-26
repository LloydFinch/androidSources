package com.bn.box2d.sndls;

import static com.bn.box2d.sndls.Constant.*;

import java.util.ArrayList;
import java.util.List;   

public class TJThread extends Thread
{
	static float lsxoffset;//老鼠的X位置
	static float lsyoffset;//老鼠的Y位置
	static float vx;//老鼠x方向的速度
	static float vy;//老鼠y方向的速度
	int[] num={4,5,8,4,5,5,4,5,5,4,5,8};
	int index;
	MainMenuView tjview;
	List<Taj> tlist=new ArrayList<Taj>();
	public TJThread(MainMenuView tjview)
	{
		this.tjview=tjview;
	}
	@Override
	public void run()
	{
		while(TJ_CONTROL_FLAG)
		{
			//得到位置、速度以及索引值
			lsxoffset=(float) (SCREEN_WIDTH/2*Math.random());
			lsyoffset=SCREEN_HEIGHT;
			vx=(float) (25*Math.random())*yMainRatio;
			vy=(float) (25*Math.random())*yMainRatio;
			index=(int)(Math.random()*num.length);
			this.tlist.add(new Taj(num[index],lsxoffset,lsyoffset,vx,vy));
			try
			{
				Thread.sleep(2000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}