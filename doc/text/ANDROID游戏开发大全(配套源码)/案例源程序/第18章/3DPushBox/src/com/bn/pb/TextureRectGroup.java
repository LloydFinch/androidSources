package com.bn.pb;
import static com.bn.pb.Constant.*;
import javax.microedition.khronos.opengles.GL10;
//��ʾ������ľ�������
public class TextureRectGroup {
	MySurfaceView father;
	
	TextureRect texturerect1;//Ŀ���������
	
	float yAngle=0;//��ת�Ƕ�
	
	int objectMap[][];//���ڸ��Ƶ�ǰ�صĵ�ͼ�Ķ�ά����
	int rows=0;//��ǰ��ά���������
	int cols=0;//��ǰ��ά���������
	
	public TextureRectGroup(MySurfaceView father,int texId1)
	{
		this.father=father;
		texturerect1=new TextureRect(CUBE_SIZE,CUBE_SIZE,texId1);
		
		//------------------���Ƶ�ǰ�صĶ�ά����-------------------------------------------
		rows=Constant.MAP[Constant.COUNT].length;
		cols=Constant.MAP[Constant.COUNT][0].length;
		
		objectMap=new int[rows][cols];
		
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				objectMap[i][j]=Constant.MAP[Constant.COUNT][i][j];
			}
		}
		//------------------���Ƶ�ǰ�صĶ�ά����end-------------------------------------------
	}
	
	public void drawSelf(GL10 gl)
	{	
		//ɨ���ͼ��ÿ�����ӣ����˸�����Ŀ�������
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if(objectMap[i][j]==3)//����Ŀ��
				{
					gl.glPushMatrix();//�����ֳ�
					//�ƶ����˸��Ӧ��λ��
				    gl.glTranslatef((j+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT][0].length/2)*UNIT_SIZE, 
				    		FLOOR_Y+0.1f, 
				    		(i+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT].length/2)*UNIT_SIZE );
				    //��Y����ת
				    gl.glRotatef(yAngle, 0, 1, 0);
				    gl.glRotatef(-90, 1, 0, 0);
				    //����
				    texturerect1.drawSelf(gl);
				    gl.glPopMatrix();//�ָ��ֳ�
				}
			}
		}
	}
}
