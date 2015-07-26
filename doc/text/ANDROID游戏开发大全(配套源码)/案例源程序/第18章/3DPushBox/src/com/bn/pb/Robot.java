package com.bn.pb;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.pb.Constant.*;

/**
 * 
 * 用于控制机器人姿态的变化
 * @author lishengjie
 *
 */
public class Robot 
{
	MySurfaceView father;
    boolean flag=true;//运动标志位
    //-----------------------复制变量---------------------------------------------
 	Globe head;//头
 	Globe body_bottom;//身体下部
 	Globe arm_terminal;//胳膊终端
 	Globe leg_terminal;//腿终端
    
 	Circle head_bottom;//头下部
 	Circle body_top;//身体上部
 	Cylinder body;//身体
 	Cylinder arm;//胳膊
 	Cylinder leg;//腿
 	Cylinder antenna;//天线
 	
 	//----------------部件甩动角度----------------
 	public static float left_arm_degree=0;//左胳膊甩动角度
 	public static float right_arm_degree=0;//右胳膊甩动角度
	public static float left_leg_degree=0;//左腿甩动角度
 	public static float right_leg_degree=0;//右腿甩动角度
 	public static float count=5.0001f;//角度增长值
 	
 	int android_texId;//纹理id
 	int android_head;//机器人头纹理id
 	
 	int i=0;//控制rotate()方法只执行一次
	 	
	public Robot(MySurfaceView father,int android_texId,int android_head)
	{
		this.father=father;
		
		this.android_texId=android_texId;
		this.android_head=android_head;
		
		head=new Globe(HEAD_RADIUS,15,0);//头部
		head_bottom=new Circle(12,HEAD_RADIUS,new float[]{10,10},HEAD_RADIUS);//头下部
		body=new Cylinder(BODY_RADIUS,BODY_LENGTH,20,0.1F);//身体
		body_top=new Circle(12,BODY_RADIUS,new float[]{10,10},BODY_RADIUS);//身体上部
		body_bottom=new Globe(BODY_RADIUS*1.16f,15,30);//身体下部
		arm=new Cylinder(ARM_RADIUS,ARM_LENGTH,20,0.1f);//胳膊
		arm_terminal=new Globe(ARM_RADIUS,15,0); //胳膊终端
		leg=new Cylinder(LEG_RADIUS,LEG_LENGTH,20,0.1f);//腿
		leg_terminal=new Globe(LEG_RADIUS,15,0);//腿终端
		antenna=new Cylinder(ANTENNA_RADIUS,ANTENNA_LENGTH,20,0.1f);//天线

	}
	public void drawSelf(GL10 gl)
	{
		//---------------------头----------------------
		gl.glPushMatrix();
		gl.glTranslatef(0, HEAD_Y, 0);//-----------------移动
		head.drawSelf(gl, android_head);//头
		gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0);
		head_bottom.drawSelf(gl, android_texId);//头下部
		gl.glPopMatrix();
		gl.glPopMatrix();
		
		//----------------------身体----------------------
		gl.glPushMatrix();
		gl.glTranslatef(0, BODY_Y, 0);//----------------移动
		body.drawSelf(gl, android_texId);//身体
		gl.glPushMatrix();
		gl.glTranslatef(0, BODY_LENGTH/2, 0);
		gl.glRotatef(-90, 1, 0, 0);
		body_top.drawSelf(gl,android_texId);//身体上部
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0, -BODY_LENGTH/8f, 0);
		gl.glRotatef(180, 1, 0, 0);
		body_bottom.drawSelf(gl, android_texId);//身体下部
		gl.glPopMatrix();
		gl.glPopMatrix();
		
		//--------------左胳膊------------------
		gl.glPushMatrix();
		gl.glTranslatef(-(float)(BODY_RADIUS+BODY_RADIUS/3), (float) (ARM_Y-ARM_LENGTH/2*Math.cos(Math.toRadians(left_arm_degree))),
						(float) (ARM_LENGTH/2*Math.sin(Math.toRadians(left_arm_degree))));//---------------移动
		gl.glRotatef(-left_arm_degree,1, 0, 0);//---------------旋转
		arm.drawSelf(gl, android_texId);
		gl.glPushMatrix();
		gl.glTranslatef(0, ARM_LENGTH/2,0 );
		arm_terminal.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0, -ARM_LENGTH/2,0 );
		gl.glRotatef(180, 1, 0, 0);
		arm_terminal.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		gl.glPopMatrix();
		//-------------右胳膊------------------------
		gl.glPushMatrix();
		gl.glTranslatef((float)(BODY_RADIUS+BODY_RADIUS/3), (float) (ARM_Y-ARM_LENGTH/2*Math.cos(Math.toRadians(right_arm_degree))),
				(float) (ARM_LENGTH/2*Math.sin(Math.toRadians(right_arm_degree))));//---------------移动
		gl.glRotatef(-right_arm_degree,1, 0, 0);//---------------旋转
		arm.drawSelf(gl, android_texId);
		gl.glPushMatrix();
		gl.glTranslatef(0, ARM_LENGTH/2,0 );
		arm_terminal.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0, -ARM_LENGTH/2,0 );
		gl.glRotatef(180, 1, 0, 0);
		arm_terminal.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		gl.glPopMatrix();
		//----------------------左腿--------------------------
		gl.glPushMatrix();
		gl.glTranslatef(-(float)(BODY_RADIUS/2), (float) (LEG_Y-LEG_LENGTH/2*Math.cos(Math.toRadians(left_leg_degree))),
				(float) (LEG_LENGTH/2*Math.sin(Math.toRadians(left_leg_degree))));//---------------移动
		gl.glRotatef(-left_leg_degree,1, 0, 0);//---------------旋转
		leg.drawSelf(gl, android_texId);
		gl.glPushMatrix();
		gl.glTranslatef(0, LEG_LENGTH/2,0 );
		leg_terminal.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0, -LEG_LENGTH/2,0 );
		gl.glRotatef(180, 1, 0, 0);
		leg_terminal.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		gl.glPopMatrix();
		//----------------------右腿--------------------------
		gl.glPushMatrix();
		gl.glTranslatef((float)(BODY_RADIUS/2), (float) (LEG_Y-LEG_LENGTH/2*Math.cos(Math.toRadians(right_leg_degree))),
				(float) (LEG_LENGTH/2*Math.sin(Math.toRadians(right_leg_degree))));//---------------移动
		gl.glRotatef(-right_leg_degree,1, 0, 0);//---------------旋转
		leg.drawSelf(gl, android_texId);
		gl.glPushMatrix();
		gl.glTranslatef(0, LEG_LENGTH/2,0 );
		leg_terminal.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0, -LEG_LENGTH/2,0 );
		gl.glRotatef(180, 1, 0, 0);
		leg_terminal.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		gl.glPopMatrix();
		//---------------------左天线------------------------
		gl.glPushMatrix();
		gl.glTranslatef(-(float)(2*BODY_RADIUS/3), 
				(float)(HEAD_Y+ANTENNA_LENGTH+ANTENNA_LENGTH/3), 0);//---------------移动
		gl.glRotatef(30, 0, 0, 1);//---------------旋转
		antenna.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		//---------------------右天线------------------------
		gl.glPushMatrix();
		gl.glTranslatef((float)(2*BODY_RADIUS/3), 
				(float)(HEAD_Y+ANTENNA_LENGTH+ANTENNA_LENGTH/3), 0);//---------------移动
		gl.glRotatef(-30, 0, 0, 1);//---------------旋转
		antenna.drawSelf(gl, android_texId);
		gl.glPopMatrix();
	}
	//-------------------开启线程,使胳膊和腿运动--------------------
	public  void rotate(final int robotAngle)
	{
		i=0;
		right_arm_degree=0;
		left_arm_degree=0;
		right_leg_degree=0;
		left_leg_degree=0;
		new Thread()
		{
			public void run()
			{
				while(i<100)//我要用100步走出1.0的距离
				{
					flag=false;//动画运行时，按键不起作用
					switch(robotAngle)
					{
					case 0://z轴正方向
						father.robotgroup.offsetz+=0.01;//z的大小增加
						break;
					case 180://z轴负方向
						father.robotgroup.offsetz-=0.01;//z减小
						break;
					case 270://x轴负方向
						father.robotgroup.offsetx-=0.01;
						break;
					case 90://x轴正方向
						father.robotgroup.offsetx+=0.01;
						break;
					}

					
					if(left_arm_degree>60)
					{
						count=-5.0001f;
					}
					else if(left_arm_degree<-60)
					{
						count=5.0001f;
					}
					right_arm_degree-=count;
					left_arm_degree+=count;
					right_leg_degree+=count;
					left_leg_degree-=count;
					if(i==99)
					{
						right_arm_degree=0;
						left_arm_degree=0;
						right_leg_degree=0;
						left_leg_degree=0;
					}
					try
					{
						Thread.sleep(10);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					i++;
				}
				flag=true;//动画结束时，按键才会起作用
			}
		}.start();
	}
	//-------------------推箱子的动画效果--------------------
	public  void pushbox(final int robotAngle)
	{
		i=0;
		right_arm_degree=90;
		left_arm_degree=90;
		right_leg_degree=0;
		left_leg_degree=0;
		new Thread()
		{
			public void run()
			{
				while(i<100)//我要用100步走出1.0的距离
				{
					flag=false;//动画运行时，按键不起作用
					switch(robotAngle)
					{
					case 0://z轴正方向
						father.robotgroup.offsetz+=0.01;//z的大小增加
						break;
					case 180://z轴负方向
						father.robotgroup.offsetz-=0.01;//z减小
						break;
					case 270://x轴负方向
						father.robotgroup.offsetx-=0.01;
						break;
					case 90://x轴正方向
						father.robotgroup.offsetx+=0.01;
						break;
					}
					
					
					if(right_leg_degree>60)
					{
						count=-5.0001f;
					}
					else if(right_leg_degree<-60)
					{
						count=5.0001f;
					}
					right_leg_degree+=count;
					left_leg_degree-=count;
					
					if(i==99)
					{
						right_arm_degree=0;
						left_arm_degree=0;
						right_leg_degree=0;
						left_leg_degree=0;
					}
					try
					{
						Thread.sleep(10);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
					i++;
				}
			}
		}.start();
	}
}
