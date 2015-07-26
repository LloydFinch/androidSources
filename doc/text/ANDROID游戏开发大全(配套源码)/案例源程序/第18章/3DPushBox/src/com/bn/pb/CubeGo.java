package com.bn.pb;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.pb.Constant.*;
/*
 * ľ���˶��Ķ�����
 */
public class CubeGo 
{
	MySurfaceView father;
	
	TextureRect texture1;//С��������ε�ǰ��
	TextureRect texture2;//С��������ε�ǰ��
	TextureRect texture3;//����������
	TextureRect texture4;//����������
	TextureRect texture5;//����������
	TextureRect texture6;//����������
	
	float height;//�趨Ϊ�ߣ��������û�������򳤣�
	
	float angleZ=0;//ʵʱ��ת�Ŷ��Ƕ�   ��Z��
	float angleX=0;//ʵʱ��ת�Ŷ��Ƕ�   ��X��
	float angleY=0;//ʵʱ��ת�Ŷ��Ƕ�   ��Y��
	
	boolean flag=false;//�Ƿ����ľ��ı�־λ��//�����涯���󣬽�ֹ�������˴������ӵı�־λ��Ϊfalse��
	
	float offsetx=0;//����λ������
	float offsetz=0;//����λ������
	
	float positionx;//����λ��
	float positionz;//����λ��
	
	int m=0;//����
	int n=0;//����
	
	int i=0;//���������߶������ı���
	
	public CubeGo(MySurfaceView father,float height,int textureID1)
	{
		this.father=father;
		
		this.height=height;//�趨Ϊ�ߣ��������û�������򳤣�
		
		//������С��������ζ���
		texture1=new TextureRect(height,height,textureID1);//ǰ��
		texture2=new TextureRect(height,height,textureID1);//����
		texture3=new TextureRect(height,height,textureID1);//����
		texture4=new TextureRect(height,height,textureID1);//����
		texture5=new TextureRect(height,height,textureID1);//����
		texture6=new TextureRect(height,height,textureID1);//����

	}
	
	public void drawSelf(GL10 gl)
	{
		if(flag)
		{
			gl.glPushMatrix();
			
			positionx=offsetx+(n+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT][0].length/2)*UNIT_SIZE;
			positionz=offsetz+(m+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT].length/2)*UNIT_SIZE;
			
			gl.glTranslatef(positionx, FLOOR_Y+CUBE_SIZE, positionz);//�ƶ�
			
	        gl.glRotatef(angleY, 0, 1, 0);
	        gl.glRotatef(angleZ, 0, 0, 1);
			
			//����ǰС��
			gl.glPushMatrix();
			gl.glTranslatef(0, 0, UNIT_SIZE*height);
			texture1.drawSelf(gl);		
			gl.glPopMatrix();
			
			//���ƺ�С��
			gl.glPushMatrix();		
			gl.glTranslatef(0, 0, -UNIT_SIZE*height);
			gl.glRotatef(180, 0, 1, 0);
			texture2.drawSelf(gl);		
			gl.glPopMatrix();
			
			//�����ϴ���
			gl.glPushMatrix();			
			gl.glTranslatef(0,UNIT_SIZE*height,0);
			gl.glRotatef(-90, 1, 0, 0);
			texture3.drawSelf(gl);
			gl.glPopMatrix();
			
			//�����´���
			gl.glPushMatrix();			
			gl.glTranslatef(0,-UNIT_SIZE*height,0);
			gl.glRotatef(90, 1, 0, 0);
			texture4.drawSelf(gl);
			gl.glPopMatrix();
			
			//���������
			gl.glPushMatrix();			
			gl.glTranslatef(UNIT_SIZE*height,0,0);		
			gl.glRotatef(-90, 1, 0, 0);
			gl.glRotatef(90, 0, 1, 0);
			texture5.drawSelf(gl);
			gl.glPopMatrix();
			
			//�����Ҵ���
			gl.glPushMatrix();			
			gl.glTranslatef(-UNIT_SIZE*height,0,0);		
			gl.glRotatef(90, 1, 0, 0);
			gl.glRotatef(-90, 0, 1, 0);
			texture6.drawSelf(gl);
			gl.glPopMatrix();
			
			gl.glPopMatrix();
		}

	}
	//�����˶�����
	public  void cubeGo(final int robotAngle)
	{
		i=0;

		new Thread()
		{
			public void run()
			{
				while(i<100)//��Ҫ��100���߳�1.0�ľ���
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
					case 0://z��������
						offsetz+=0.01;
						break;
					case 180://z�Ḻ����
						offsetz-=0.01;
						break;
					case 270://x�Ḻ����
						offsetx-=0.01;
						break;
					case 90://x��������
						offsetx+=0.01;
						break;
					}

					i++;
				}
				switch(robotAngle)
				{
				case 0://z��������
					if(father.objectMap[m+1][n]==3)//�����Ҫ���Ƶ��ĵط���Ŀ���
					{	
						father.objectMap[m+1][n]=6;
					}else
					{
						father.objectMap[m+1][n]=4;//���ӽ�Ҫ���Ƶ��ĵط�������
					}
					break;
				case 180://z�Ḻ����
					if(father.objectMap[m-1][n]==3)//�����Ҫ���Ƶ��ĵط���Ŀ���
					{	
						father.objectMap[m-1][n]=6;//���ӽ�Ҫ���Ƶ��ĵط�����ɫ����
					}else
					{
						father.objectMap[m-1][n]=4;//���ӽ�Ҫ���Ƶ��ĵط�������
					}
					break;
				case 270://x�Ḻ����
					if(father.objectMap[m][n-1]==3)//�����Ҫ���Ƶ��ĵط���Ŀ���
					{	
						father.objectMap[m][n-1]=6;//���ӽ�Ҫ���Ƶ��ĵط�����ɫ����
					}else
					{
						father.objectMap[m][n-1]=4;//���ӽ�Ҫ���Ƶ��ĵط�������
					}
					break;
				case 90://x��������
					if(father.objectMap[m][n+1]==3)//�����Ҫ���Ƶ��ĵط���Ŀ���
					{	
						father.objectMap[m][n+1]=6;//���ӽ�Ҫ���Ƶ��ĵط�����ɫ����
					}else
					{
						father.objectMap[m][n+1]=4;//���ӽ�Ҫ���Ƶ��ĵط�������
					}
					break;
				}
				flag=false;//�����涯���󣬽�ֹ�������˴������ӵı�־λ��Ϊfalse
				offsetx=0;
				offsetz=0;
				father.robotgroup.robot.flag=true;
			}
		}.start();
	}
	
	
}
