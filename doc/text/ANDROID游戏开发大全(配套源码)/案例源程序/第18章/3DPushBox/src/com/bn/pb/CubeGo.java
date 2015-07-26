package com.bn.pb;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.pb.Constant.*;
/*
 * 木箱运动的动画类
 */
public class CubeGo 
{
	MySurfaceView father;
	
	TextureRect texture1;//小面纹理矩形的前面
	TextureRect texture2;//小面纹理矩形的前面
	TextureRect texture3;//上下两个面
	TextureRect texture4;//上下两个面
	TextureRect texture5;//左右两个面
	TextureRect texture6;//左右两个面
	
	float height;//设定为高（即面向用户面的竖向长）
	
	float angleZ=0;//实时旋转扰动角度   绕Z轴
	float angleX=0;//实时旋转扰动角度   绕X轴
	float angleY=0;//实时旋转扰动角度   绕Y轴
	
	boolean flag=false;//是否绘制木箱的标志位（//播放玩动画后，禁止继续画此处的箱子的标志位设为false）
	
	float offsetx=0;//坐标位置增量
	float offsetz=0;//坐标位置增量
	
	float positionx;//坐标位置
	float positionz;//坐标位置
	
	int m=0;//行数
	int n=0;//列数
	
	int i=0;//控制箱子走动动画的变量
	
	public CubeGo(MySurfaceView father,float height,int textureID1)
	{
		this.father=father;
		
		this.height=height;//设定为高（即面向用户面的竖向长）
		
		//创建大小面纹理矩形对象
		texture1=new TextureRect(height,height,textureID1);//前面
		texture2=new TextureRect(height,height,textureID1);//后面
		texture3=new TextureRect(height,height,textureID1);//上面
		texture4=new TextureRect(height,height,textureID1);//下面
		texture5=new TextureRect(height,height,textureID1);//左面
		texture6=new TextureRect(height,height,textureID1);//右面

	}
	
	public void drawSelf(GL10 gl)
	{
		if(flag)
		{
			gl.glPushMatrix();
			
			positionx=offsetx+(n+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT][0].length/2)*UNIT_SIZE;
			positionz=offsetz+(m+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT].length/2)*UNIT_SIZE;
			
			gl.glTranslatef(positionx, FLOOR_Y+CUBE_SIZE, positionz);//移动
			
	        gl.glRotatef(angleY, 0, 1, 0);
	        gl.glRotatef(angleZ, 0, 0, 1);
			
			//绘制前小面
			gl.glPushMatrix();
			gl.glTranslatef(0, 0, UNIT_SIZE*height);
			texture1.drawSelf(gl);		
			gl.glPopMatrix();
			
			//绘制后小面
			gl.glPushMatrix();		
			gl.glTranslatef(0, 0, -UNIT_SIZE*height);
			gl.glRotatef(180, 0, 1, 0);
			texture2.drawSelf(gl);		
			gl.glPopMatrix();
			
			//绘制上大面
			gl.glPushMatrix();			
			gl.glTranslatef(0,UNIT_SIZE*height,0);
			gl.glRotatef(-90, 1, 0, 0);
			texture3.drawSelf(gl);
			gl.glPopMatrix();
			
			//绘制下大面
			gl.glPushMatrix();			
			gl.glTranslatef(0,-UNIT_SIZE*height,0);
			gl.glRotatef(90, 1, 0, 0);
			texture4.drawSelf(gl);
			gl.glPopMatrix();
			
			//绘制左大面
			gl.glPushMatrix();			
			gl.glTranslatef(UNIT_SIZE*height,0,0);		
			gl.glRotatef(-90, 1, 0, 0);
			gl.glRotatef(90, 0, 1, 0);
			texture5.drawSelf(gl);
			gl.glPopMatrix();
			
			//绘制右大面
			gl.glPushMatrix();			
			gl.glTranslatef(-UNIT_SIZE*height,0,0);		
			gl.glRotatef(90, 1, 0, 0);
			gl.glRotatef(-90, 0, 1, 0);
			texture6.drawSelf(gl);
			gl.glPopMatrix();
			
			gl.glPopMatrix();
		}

	}
	//箱子运动动画
	public  void cubeGo(final int robotAngle)
	{
		i=0;

		new Thread()
		{
			public void run()
			{
				while(i<100)//我要用100步走出1.0的距离
				{
					father.robotgroup.robot.flag=false;
					try
					{
						Thread.sleep(10);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					
					switch(robotAngle)
					{
					case 0://z轴正方向
						offsetz+=0.01;
						break;
					case 180://z轴负方向
						offsetz-=0.01;
						break;
					case 270://x轴负方向
						offsetx-=0.01;
						break;
					case 90://x轴正方向
						offsetx+=0.01;
						break;
					}

					i++;
				}
				switch(robotAngle)
				{
				case 0://z轴正方向
					if(father.objectMap[m+1][n]==3)//如果将要被推到的地方是目标点
					{	
						father.objectMap[m+1][n]=6;
					}else
					{
						father.objectMap[m+1][n]=4;//箱子将要被推到的地方画箱子
					}
					break;
				case 180://z轴负方向
					if(father.objectMap[m-1][n]==3)//如果将要被推到的地方是目标点
					{	
						father.objectMap[m-1][n]=6;//箱子将要被推到的地方画红色箱子
					}else
					{
						father.objectMap[m-1][n]=4;//箱子将要被推到的地方画箱子
					}
					break;
				case 270://x轴负方向
					if(father.objectMap[m][n-1]==3)//如果将要被推到的地方是目标点
					{	
						father.objectMap[m][n-1]=6;//箱子将要被推到的地方画红色箱子
					}else
					{
						father.objectMap[m][n-1]=4;//箱子将要被推到的地方画箱子
					}
					break;
				case 90://x轴正方向
					if(father.objectMap[m][n+1]==3)//如果将要被推到的地方是目标点
					{	
						father.objectMap[m][n+1]=6;//箱子将要被推到的地方画红色箱子
					}else
					{
						father.objectMap[m][n+1]=4;//箱子将要被推到的地方画箱子
					}
					break;
				}
				flag=false;//播放玩动画后，禁止继续画此处的箱子的标志位设为false
				offsetx=0;
				offsetz=0;
				father.robotgroup.robot.flag=true;
			}
		}.start();
	}
	
	
}
