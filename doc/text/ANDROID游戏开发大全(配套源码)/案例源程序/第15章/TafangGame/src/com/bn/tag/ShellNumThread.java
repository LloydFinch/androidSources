package com.bn.tag;

import static com.bn.tag.Constant.*;

import java.util.List;
import java.util.Vector;

public class ShellNumThread extends Thread {
	GameView gameView;
	private boolean flag=true;
	private boolean whileflag=true;

	ShellNumThread(GameView gameView)
	{
		this.gameView=gameView;
	}
	@Override
	public void run()
	{
		while(whileflag)
		{
			if(flag)
			{
				List<Target> targetmid=new Vector<Target>(gameView.alTarget1);
				List<SingleJianta> jiantalista=new Vector<SingleJianta>(gameView.jiantaList);
				for(int i=0;i<jiantalista.size();i++)
				{
					List<Target> tartar=new Vector<Target>();
					float y1=jiantalista.get(i).row*SINGLE_RODER+SINGLE_RODER/2;
					float x1=jiantalista.get(i).clo*SINGLE_RODER+SINGLE_RODER/2;
					float y2=jiantalista.get(i).row*SINGLE_RODER-JIAN_TA_HEIGHT+SINGLE_RODER;
					for(int j=0;j<targetmid.size();j++)				
					{					
						if((y1-targetmid.get(j).bally)*(y1-targetmid.get(j).bally)+(x1-targetmid.get(j).ballx)*(x1-targetmid.get(j).ballx)<=jiantalista.get(i).state*jiantalista.get(i).state)
						{
							tartar.add(targetmid.get(j));
						}
						else 
						{
							continue;
						}
					}
					if(tartar.size()!=0)
					{
						Target targett=getMaxNumtarget(tartar);
						gameView.shellsjian.add(new Shell(gameView,gameView.tubiao1,x1,y2,targett,jiantalista.get(i)));						
					}
					tartar.clear();
				}
			}
			
			try{
	        	Thread.sleep(2000);//睡眠指定毫秒数
	        }
	        catch(Exception e){
	        	e.printStackTrace();//打印堆栈信息
	        }
		}		
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public void setwhileflag(boolean whileflag)
	{
		this.whileflag=whileflag;
	}
	
	public Target getMaxNumtarget(List<Target> target)
	{
		Target result = target.get(0);
		for(int i=1;i<target.size();i++)
		{
			if(result.num<target.get(i).num)
			{
				result=target.get(i);
			}			
		}
		return result;
		
	}
}
