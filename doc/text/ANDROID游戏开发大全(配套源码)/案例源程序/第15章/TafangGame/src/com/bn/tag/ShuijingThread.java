package com.bn.tag;							//声明包语句

public class ShuijingThread extends Thread{
	GameView gv;			//GameView对象的引用	
	boolean flag;			//线程是否执行标志位		//游戏是否进行标志位
	boolean whileflag=true;
	//int sleepSpan = AI_THREAD_SLEEP_SPAN;	//游戏进行时线程执行时间	
	public ShuijingThread(GameView gv){
		this.gv = gv;		
		flag = false;
		//isGameOn = true;
	}
	public void run(){		//方法：线程执行放法
		while(whileflag){
			
			if(flag){
				gv.alpha=gv.alpha-10;
				if(gv.alpha<0)
				{
					gv.alpha=200;
					gv.shuijing_D_Z_Flag=false;
					this.setFlag(false);
					
				}
			}
			try{
				
				Thread.sleep(50);					//线程空转时间
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
}