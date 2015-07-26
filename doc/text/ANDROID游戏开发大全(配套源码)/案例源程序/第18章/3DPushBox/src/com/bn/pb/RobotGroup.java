package com.bn.pb;
import static com.bn.pb.Constant.*;
import javax.microedition.khronos.opengles.GL10;
//表示立方体木箱组的类
public class RobotGroup {
	MySurfaceView father;
	Robot robot;//立方体木箱
	float yAngle=0;//立方体木箱沿Y轴旋转的角度
	
	float offsetx=0;//x与y的增量
	float offsetz=0;
	
	int i=0;//控制动画的帧数
	
	float positionx=0;//机器人的位置
	float positionz=0;//机器人的位置
	
	boolean flag=true;//是否旋转的标记
	
	int m=0;//机器人的行和列
	int n=0;
	
	public RobotGroup(MySurfaceView father,int texId,int texId_head)
	{
		this.father=father;
		robot=new Robot(father,texId,texId_head);
	}
	
	public void drawSelf(GL10 gl)
	{	
		//扫描晶体位置地图的每个格子，若此格有晶体则绘制
		for(int i=0;i<father.rows;i++)
		{
			for(int j=0;j<father.cols;j++)
			{
				if(father.objectMap[i][j]==5)
				{	
					m=i;
					n=j;
					positionx=offsetx+(j+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT][0].length/2)*UNIT_SIZE;
					positionz=offsetz+(i+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT].length/2)*UNIT_SIZE;
					
					gl.glPushMatrix();//保护现场
					//移动晶体到此格对应的位置
				    gl.glTranslatef(positionx, 
				    		FLOOR_Y+CUBE_SIZE, 
				    		positionz);
				    //将晶体绕Y轴旋转
				    gl.glRotatef(yAngle, 0, 1, 0);
				    //绘制晶体
				    robot.drawSelf(gl);
				    gl.glPopMatrix();//恢复现场
				}
			}
		}
	}
	//-------------------后转弯动画--------------------
	public  void backRotate(final int robotAngle)
	{
		i=0;
		new Thread()
		{
			public void run()
			{
				while(i<180)//我要用100步走出1.0的距离
				{
					robot.flag=false;
					try
					{
						Thread.sleep(10);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					yAngle+=1;//每次加18度,分100次转180度
					i++;
				}
			   	if(robotAngle==0)//机器人转变方向
            	{
            		yAngle=180;
            	}else if(robotAngle==90)
            	{
            		yAngle=270;
            	}else if(robotAngle==180)
            	{
            		yAngle=0;
            	}else if(robotAngle==270)
            	{
            		yAngle=90;
            	}
			   	robot.flag=true;
			}
		}.start();
	}
	//-------------------左转弯动画--------------------
	public  void leftRotate(final int robotAngle)
	{
		i=0;
		new Thread()
		{
			public void run()
			{
				while(i<90)//我要用100步走出1.0的距离
				{
					robot.flag=false;
					try
					{
						Thread.sleep(10);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					yAngle+=1;//每次加18度,分100次转180度
					i++;
				}
			   	if(robotAngle==0)//机器人转变方向
            	{
            		yAngle=90;
            	}else if(robotAngle==90)
            	{
            		yAngle=180;
            	}else if(robotAngle==180)
            	{
            		yAngle=270;
            	}else if(robotAngle==270)
            	{
            		yAngle=0;
            	}
			   	
			   	robot.flag=true;
			}
		}.start();
	}
	//-------------------右转弯动画--------------------
	public  void rightRotate(final int robotAngle)
	{
		i=0;
		new Thread()
		{
			public void run()
			{
				while(i<90)//我要用100步走出1.0的距离
				{
					robot.flag=false;
					try
					{
						Thread.sleep(10);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					yAngle-=1;//每次加18度,分100次转180度
					i++;
				}
			   	if(robotAngle==0)//机器人转变方向
            	{
            		yAngle=270;
            	}else if(robotAngle==90)
            	{
            		yAngle=0;
            	}else if(robotAngle==180)
            	{
            		yAngle=90;
            	}else if(robotAngle==270)
            	{
            		yAngle=180;
            	}
			   	robot.flag=true;
			}
		}.start();
	}
	
}
