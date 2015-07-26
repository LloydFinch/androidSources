package com.bn.pb;
import static com.bn.pb.Constant.*;

import javax.microedition.khronos.opengles.GL10;
//��ʾ������ľ�������
public class CubeGroup {
	MySurfaceView father;
	
	Cube cube;//������ľ��
	Cube cubered;//����ɫ������
	float yAngle=0;//������ľ����Y����ת�ĽǶ�
	
	int redbincount=0;//�����ӵ�����
	int bincount=0;//��ͨ���ӵ�����
	int targetcount=0;//Ŀ��������

	
	public CubeGroup(MySurfaceView father,int texId,int texId2)
	{
		this.father=father;
		cube=new Cube(CUBE_SIZE,CUBE_SIZE,CUBE_SIZE,texId,texId,texId,texId,texId,texId);
		cubered=new Cube(CUBE_SIZE,CUBE_SIZE,CUBE_SIZE,texId2,texId2,texId2,texId2,texId2,texId2);
	}
	
	
	public void drawSelf(GL10 gl)
	{
		//ɨ���ͼ��ÿ������
		
		for(int i=0;i<father.rows;i++)
		{
			for(int j=0;j<father.cols;j++)
			{
				if(father.objectMap[i][j]==4)//�������ͨ����
				{	
					gl.glPushMatrix();//�����ֳ�
					//�ƶ����嵽�˸��Ӧ��λ��
				    gl.glTranslatef((j+0.5f)*UNIT_SIZE-(int)(father.objectMap[0].length/2)*UNIT_SIZE, 
				    		FLOOR_Y+CUBE_SIZE, 
				    		(i+0.5f)*UNIT_SIZE-(int)(father.objectMap.length/2)*UNIT_SIZE );
				    //��������Y����ת
				    gl.glRotatef(yAngle, 0, 1, 0);
				    //���ƾ���
				    cube.drawSelf(gl);
				    gl.glPopMatrix();//�ָ��ֳ�
				}else if(father.objectMap[i][j]==6)//���ƺ���ɫ������
				{
					gl.glPushMatrix();//�����ֳ�
					//�ƶ����嵽�˸��Ӧ��λ��
				    gl.glTranslatef((j+0.5f)*UNIT_SIZE-(int)(father.objectMap[0].length/2)*UNIT_SIZE, 
				    		FLOOR_Y+CUBE_SIZE, 
				    		(i+0.5f)*UNIT_SIZE-(int)(father.objectMap.length/2)*UNIT_SIZE );
				    //��������Y����ת
				    gl.glRotatef(yAngle, 0, 1, 0);
				    //���ƾ���
				    cubered.drawSelf(gl);
				    gl.glPopMatrix();//�ָ��ֳ�
					
				}
			}
		}
		
	}
}
