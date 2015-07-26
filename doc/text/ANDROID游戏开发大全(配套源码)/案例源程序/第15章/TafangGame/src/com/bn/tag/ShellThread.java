package com.bn.tag;							//声明包语句


import java.util.List;
public class ShellThread extends Thread{
	GameView gv;			//GameView对象的引用
	List<Shell> shells;	//Moster数组引用
	boolean flag;			//线程是否执行标志位
	boolean isGameOn;		//游戏是否进行标志位
	boolean whileflag=true;
	//int sleepSpan = AI_THREAD_SLEEP_SPAN;	//游戏进行时线程执行时间	
	public ShellThread(GameView gv,List<Shell> shellsjian){
		this.gv = gv;
		this.shells = shellsjian;
		flag = true;
		//isGameOn = true;
	}
	public void run(){		//方法：线程执行放法
		while(whileflag){
			
			
			if(flag){
				synchronized(gv.shellsjian){
				for(Shell m:gv.shellsjian){		
					
					m.go();
				}				
				gv.shellsjian.removeAll(Shell.shl);
			
//				try{
//					Thread.sleep(20);
//				}
//				catch(Exception e){
//					e.printStackTrace();
//				}
			}
			}
			try{
				
				Thread.sleep(5);					//线程空转时间
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