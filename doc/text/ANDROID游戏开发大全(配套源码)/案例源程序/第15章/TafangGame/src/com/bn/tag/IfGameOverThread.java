package com.bn.tag;

import com.bn.tag.GameView;

/**
 * 
 *负责判断游戏结束的线程
 *
 */
public class IfGameOverThread extends Thread {
	GameView gameView;
	private boolean flag=true;
	private int sleepSpan=1000;		
	public IfGameOverThread(GameView gameView)
	{
		this.gameView=gameView;
	}
	@Override
	public void run()
	{
		while(flag)
		{			
			 try{
	            	Thread.sleep(sleepSpan);//睡眠指定毫秒数
	            }
	            catch(Exception e){
	            	e.printStackTrace();//打印堆栈信息
	            }
	            gameView.GameOver();
		}
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
