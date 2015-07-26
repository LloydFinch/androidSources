package com.bn.pb;
import static com.bn.pb.Constant.*;
import javax.microedition.khronos.opengles.GL10;
//��ʾ������ľ�������
public class RobotGroup {
	MySurfaceView father;
	Robot robot;//������ľ��
	float yAngle=0;//������ľ����Y����ת�ĽǶ�
	
	float offsetx=0;//x��y������
	float offsetz=0;
	
	int i=0;//���ƶ�����֡��
	
	float positionx=0;//�����˵�λ��
	float positionz=0;//�����˵�λ��
	
	boolean flag=true;//�Ƿ���ת�ı��
	
	int m=0;//�����˵��к���
	int n=0;
	
	public RobotGroup(MySurfaceView father,int texId,int texId_head)
	{
		this.father=father;
		robot=new Robot(father,texId,texId_head);
	}
	
	public void drawSelf(GL10 gl)
	{	
		//ɨ�辧��λ�õ�ͼ��ÿ�����ӣ����˸��о��������
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
					
					gl.glPushMatrix();//�����ֳ�
					//�ƶ����嵽�˸��Ӧ��λ��
				    gl.glTranslatef(positionx, 
				    		FLOOR_Y+CUBE_SIZE, 
				    		positionz);
				    //��������Y����ת
				    gl.glRotatef(yAngle, 0, 1, 0);
				    //���ƾ���
				    robot.drawSelf(gl);
				    gl.glPopMatrix();//�ָ��ֳ�
				}
			}
		}
	}
	//-------------------��ת�䶯��--------------------
	public  void backRotate(final int robotAngle)
	{
		i=0;
		new Thread()
		{
			public void run()
			{
				while(i<180)//��Ҫ��100���߳�1.0�ľ���
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
					yAngle+=1;//ÿ�μ�18��,��100��ת180��
					i++;
				}
			   	if(robotAngle==0)//������ת�䷽��
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
	//-------------------��ת�䶯��--------------------
	public  void leftRotate(final int robotAngle)
	{
		i=0;
		new Thread()
		{
			public void run()
			{
				while(i<90)//��Ҫ��100���߳�1.0�ľ���
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
					yAngle+=1;//ÿ�μ�18��,��100��ת180��
					i++;
				}
			   	if(robotAngle==0)//������ת�䷽��
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
	//-------------------��ת�䶯��--------------------
	public  void rightRotate(final int robotAngle)
	{
		i=0;
		new Thread()
		{
			public void run()
			{
				while(i<90)//��Ҫ��100���߳�1.0�ľ���
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
					yAngle-=1;//ÿ�μ�18��,��100��ת180��
					i++;
				}
			   	if(robotAngle==0)//������ת�䷽��
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
