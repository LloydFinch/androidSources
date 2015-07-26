package com.bn.pb;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.pb.Constant.*;

/**
 * 
 * ���ڿ��ƻ�������̬�ı仯
 * @author lishengjie
 *
 */
public class Robot 
{
	MySurfaceView father;
    boolean flag=true;//�˶���־λ
    //-----------------------���Ʊ���---------------------------------------------
 	Globe head;//ͷ
 	Globe body_bottom;//�����²�
 	Globe arm_terminal;//�첲�ն�
 	Globe leg_terminal;//���ն�
    
 	Circle head_bottom;//ͷ�²�
 	Circle body_top;//�����ϲ�
 	Cylinder body;//����
 	Cylinder arm;//�첲
 	Cylinder leg;//��
 	Cylinder antenna;//����
 	
 	//----------------����˦���Ƕ�----------------
 	public static float left_arm_degree=0;//��첲˦���Ƕ�
 	public static float right_arm_degree=0;//�Ҹ첲˦���Ƕ�
	public static float left_leg_degree=0;//����˦���Ƕ�
 	public static float right_leg_degree=0;//����˦���Ƕ�
 	public static float count=5.0001f;//�Ƕ�����ֵ
 	
 	int android_texId;//����id
 	int android_head;//������ͷ����id
 	
 	int i=0;//����rotate()����ִֻ��һ��
	 	
	public Robot(MySurfaceView father,int android_texId,int android_head)
	{
		this.father=father;
		
		this.android_texId=android_texId;
		this.android_head=android_head;
		
		head=new Globe(HEAD_RADIUS,15,0);//ͷ��
		head_bottom=new Circle(12,HEAD_RADIUS,new float[]{10,10},HEAD_RADIUS);//ͷ�²�
		body=new Cylinder(BODY_RADIUS,BODY_LENGTH,20,0.1F);//����
		body_top=new Circle(12,BODY_RADIUS,new float[]{10,10},BODY_RADIUS);//�����ϲ�
		body_bottom=new Globe(BODY_RADIUS*1.16f,15,30);//�����²�
		arm=new Cylinder(ARM_RADIUS,ARM_LENGTH,20,0.1f);//�첲
		arm_terminal=new Globe(ARM_RADIUS,15,0); //�첲�ն�
		leg=new Cylinder(LEG_RADIUS,LEG_LENGTH,20,0.1f);//��
		leg_terminal=new Globe(LEG_RADIUS,15,0);//���ն�
		antenna=new Cylinder(ANTENNA_RADIUS,ANTENNA_LENGTH,20,0.1f);//����

	}
	public void drawSelf(GL10 gl)
	{
		//---------------------ͷ----------------------
		gl.glPushMatrix();
		gl.glTranslatef(0, HEAD_Y, 0);//-----------------�ƶ�
		head.drawSelf(gl, android_head);//ͷ
		gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0);
		head_bottom.drawSelf(gl, android_texId);//ͷ�²�
		gl.glPopMatrix();
		gl.glPopMatrix();
		
		//----------------------����----------------------
		gl.glPushMatrix();
		gl.glTranslatef(0, BODY_Y, 0);//----------------�ƶ�
		body.drawSelf(gl, android_texId);//����
		gl.glPushMatrix();
		gl.glTranslatef(0, BODY_LENGTH/2, 0);
		gl.glRotatef(-90, 1, 0, 0);
		body_top.drawSelf(gl,android_texId);//�����ϲ�
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0, -BODY_LENGTH/8f, 0);
		gl.glRotatef(180, 1, 0, 0);
		body_bottom.drawSelf(gl, android_texId);//�����²�
		gl.glPopMatrix();
		gl.glPopMatrix();
		
		//--------------��첲------------------
		gl.glPushMatrix();
		gl.glTranslatef(-(float)(BODY_RADIUS+BODY_RADIUS/3), (float) (ARM_Y-ARM_LENGTH/2*Math.cos(Math.toRadians(left_arm_degree))),
						(float) (ARM_LENGTH/2*Math.sin(Math.toRadians(left_arm_degree))));//---------------�ƶ�
		gl.glRotatef(-left_arm_degree,1, 0, 0);//---------------��ת
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
		//-------------�Ҹ첲------------------------
		gl.glPushMatrix();
		gl.glTranslatef((float)(BODY_RADIUS+BODY_RADIUS/3), (float) (ARM_Y-ARM_LENGTH/2*Math.cos(Math.toRadians(right_arm_degree))),
				(float) (ARM_LENGTH/2*Math.sin(Math.toRadians(right_arm_degree))));//---------------�ƶ�
		gl.glRotatef(-right_arm_degree,1, 0, 0);//---------------��ת
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
		//----------------------����--------------------------
		gl.glPushMatrix();
		gl.glTranslatef(-(float)(BODY_RADIUS/2), (float) (LEG_Y-LEG_LENGTH/2*Math.cos(Math.toRadians(left_leg_degree))),
				(float) (LEG_LENGTH/2*Math.sin(Math.toRadians(left_leg_degree))));//---------------�ƶ�
		gl.glRotatef(-left_leg_degree,1, 0, 0);//---------------��ת
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
		//----------------------����--------------------------
		gl.glPushMatrix();
		gl.glTranslatef((float)(BODY_RADIUS/2), (float) (LEG_Y-LEG_LENGTH/2*Math.cos(Math.toRadians(right_leg_degree))),
				(float) (LEG_LENGTH/2*Math.sin(Math.toRadians(right_leg_degree))));//---------------�ƶ�
		gl.glRotatef(-right_leg_degree,1, 0, 0);//---------------��ת
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
		//---------------------������------------------------
		gl.glPushMatrix();
		gl.glTranslatef(-(float)(2*BODY_RADIUS/3), 
				(float)(HEAD_Y+ANTENNA_LENGTH+ANTENNA_LENGTH/3), 0);//---------------�ƶ�
		gl.glRotatef(30, 0, 0, 1);//---------------��ת
		antenna.drawSelf(gl, android_texId);
		gl.glPopMatrix();
		//---------------------������------------------------
		gl.glPushMatrix();
		gl.glTranslatef((float)(2*BODY_RADIUS/3), 
				(float)(HEAD_Y+ANTENNA_LENGTH+ANTENNA_LENGTH/3), 0);//---------------�ƶ�
		gl.glRotatef(-30, 0, 0, 1);//---------------��ת
		antenna.drawSelf(gl, android_texId);
		gl.glPopMatrix();
	}
	//-------------------�����߳�,ʹ�첲�����˶�--------------------
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
				while(i<100)//��Ҫ��100���߳�1.0�ľ���
				{
					flag=false;//��������ʱ��������������
					switch(robotAngle)
					{
					case 0://z��������
						father.robotgroup.offsetz+=0.01;//z�Ĵ�С����
						break;
					case 180://z�Ḻ����
						father.robotgroup.offsetz-=0.01;//z��С
						break;
					case 270://x�Ḻ����
						father.robotgroup.offsetx-=0.01;
						break;
					case 90://x��������
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
				flag=true;//��������ʱ�������Ż�������
			}
		}.start();
	}
	//-------------------�����ӵĶ���Ч��--------------------
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
				while(i<100)//��Ҫ��100���߳�1.0�ľ���
				{
					flag=false;//��������ʱ��������������
					switch(robotAngle)
					{
					case 0://z��������
						father.robotgroup.offsetz+=0.01;//z�Ĵ�С����
						break;
					case 180://z�Ḻ����
						father.robotgroup.offsetz-=0.01;//z��С
						break;
					case 270://x�Ḻ����
						father.robotgroup.offsetx-=0.01;
						break;
					case 90://x��������
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
