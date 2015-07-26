package com.bn.tag;				//声明包语句

import com.bn.tag.MainMenuSurfaceView;;

public class SelectThread extends Thread{
	MainMenuSurfaceView father;
	boolean flag;
	int sleepSpan = 100;//休眠时间		
	public SelectThread(MainMenuSurfaceView father){
		this.father = father;
		//flag = true;
	}
	//线程的执行方法
	public void run(){
		while(true){
			
			father.ballY+=30;
			if(father.ballY>=0){
				//father.status = 2;
				father.ballY=0;				
				break;
			}			
			try{
				Thread.sleep(sleepSpan);
			}										//线程休眠
			catch(Exception e){
				e.printStackTrace();
			}										//捕获并打印异常
		}
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}