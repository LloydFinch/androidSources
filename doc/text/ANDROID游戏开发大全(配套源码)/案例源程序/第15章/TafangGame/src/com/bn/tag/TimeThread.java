package com.bn.tag;


public class TimeThread extends Thread{
		GameView father;
		boolean flag=false;
		boolean whileflag=true;
		int sleepSpan=100;//休眠时间
		
		public TimeThread(GameView father){
			this.father = father;
			
		}
		//线程的执行方法
		public void run(){
			while(whileflag){
				if(flag)
				{
					father.angle+=1;
					if(father.angle>=360)
					{
						father.angle=0;
						this.flag=false;
						father.shuijing_Flag=false;
					}
					
					
					father.changeShuijingXY(father.angle);
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
		public void setwhileflag(boolean whileflag)
		{
			this.whileflag=whileflag;
		}
	}