package com.bn.tag;


public class TargetNumThread extends Thread {
	GameView gameView;
	private int sleepSpan=10000;
	private boolean flag=true;
	private boolean whileflag=true;
	private int distancespan=1000;
	private int blood_A=2;//��ͬ����������ͼ���Ѫ��
	private int blood_B=3;
	private int blood_C=4;
	private int NUMBER=20;//ÿʮ�����ı�һ�ι��ﾫ���Ѫ��
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
	        	Thread.sleep(distancespan);//˯��ָ��������
	        }
	        catch(Exception e){
	        	e.printStackTrace();//��ӡ��ջ��Ϣ
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
						if(flag)  //0�����0  1�����1  2�����2
						{
							double m = Math.random();
							if(m<0.5)
							{
								gameView.alTarget1.add(new Target(gameView,gameView.target1Bitmap,20,180,blood_A,blood_A,0,direction,0));//����Ŀ����󣬼����б�
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
				        	Thread.sleep(distancespan);//˯��ָ��������
				        }
				        catch(Exception e){
				        	e.printStackTrace();//��ӡ��ջ��Ϣ
				        }
					}
					
				}
			}
			try{
	        	Thread.sleep(sleepSpan);//˯��ָ��������
	        }
	        catch(Exception e){
	        	e.printStackTrace();//��ӡ��ջ��Ϣ
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
