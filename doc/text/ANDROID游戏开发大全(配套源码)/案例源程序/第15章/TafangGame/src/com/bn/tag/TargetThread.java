package com.bn.tag;

import com.bn.tag.GameView;


public class TargetThread extends Thread{
	GameView gameView;
	boolean flag=true;
	boolean whileflag=true;	
	public TargetThread(GameView gameView){
		this.gameView=gameView;
	}
	
	public void run(){		
			while(whileflag)
			{	
				if(flag)
				{
					synchronized(gameView.alTarget1){
					for(Target tar:gameView.alTarget1)
					{			
						tar.goRode();			
					}
					gameView.alTarget1.removeAll(Target.ta);	
					gameView.alTarget1.removeAll(Shell.tas);
					Target.ta.clear();
					Shell.tas.clear();
					}
				}							
				try{
					Thread.sleep(10);
				}
				catch(Exception e){
					e.printStackTrace();
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
				
	public double CallLength(float x,float y)
	{
		double result = 0.0;
		if(x==0&&y>0)
		{
			result = y;
		}
		else if(x==0&&y<0)
		{
			result=-y;
		}
		else if(y==0&&x>0)
		{
			result=x;
		}
		else if(y==0&&x<0)
		{
			result=-x;
		}
		return  result;
	}	
}