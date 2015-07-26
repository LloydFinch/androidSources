package com.bn.tag;


public class TargetNumThread extends Thread {
	GameView gameView;
	private int sleepSpan=10000;
	private boolean flag=true;
	private boolean whileflag=true;
	private int distancespan=1000;
	private int blood_A=2;//不同怪物种类最低级的血量
	private int blood_B=3;
	private int blood_C=4;
	private int NUMBER=20;//每十二波改变一次怪物精灵的血量
	double direction=0.0*Math.PI;
	int tempk=1;
	TargetNumThread(GameView gameView)
	{
		this.gameView=gameView;
	}
	@Override
	public void run()
	{
		while(whileflag)
		{
			try{
	        	Thread.sleep(distancespan);//睡眠指定毫秒数
	        }
	        catch(Exception e){
	        	e.printStackTrace();//打印堆栈信息
	        }
			if(flag)
			{
				
				if(tempk<=NUMBER)
				{
					tempk=tempk+1;	
					if(tempk==NUMBER)
					{
						setblood();						
					}
				}				
				else 
				{
					tempk=1;
				}
				
				if(gameView.alTarget1.size()==0)
				{
					for(int i=0;i<tempk;i++)
					{
						if(flag)  //0代表怪0  1代表怪1  2代表怪2
						{
							double m = Math.random();
							if(m<0.5)
							{
								gameView.alTarget1.add(new Target(gameView,gameView.target1Bitmap,20,180,blood_A,blood_A,0,direction,0));//创建目标对象，加入列表
							}
							else if(m<0.8&&m>=0.5)
							{
								gameView.alTarget1.add(new Target(gameView,gameView.target1Bitmap,20,180,blood_B,blood_B,1,direction,0));
							}
							else if(m>=0.8)
							{
								gameView.alTarget1.add(new Target(gameView,gameView.target1Bitmap,20,180,blood_C,blood_C,2,direction,0));
							}
							
							
						}
						else if(!flag) 
						{
							i--;
						}
						try{
				        	Thread.sleep(distancespan);//睡眠指定毫秒数
				        }
				        catch(Exception e){
				        	e.printStackTrace();//打印堆栈信息
				        }
					}
					
				}
			}
			try{
	        	Thread.sleep(sleepSpan);//睡眠指定毫秒数
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
	
	void setblood()
	{
		blood_A=blood_A+2;
		blood_B=blood_B+2;
		blood_C=blood_C+2;				
	}
}
