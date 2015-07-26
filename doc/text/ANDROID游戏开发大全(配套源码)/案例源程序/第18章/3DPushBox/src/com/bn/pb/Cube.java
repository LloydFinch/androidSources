package com.bn.pb;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.pb.Constant.*;

public class Cube 
{
	TextureRect texture1;//С��������ε�ǰ��
	TextureRect texture2;//С��������ε�ǰ��
	TextureRect texture3;//����������
	TextureRect texture4;//����������
	TextureRect texture5;//����������
	TextureRect texture6;//����������
	
	float height;//�趨Ϊ�ߣ��������û�������򳤣�
	float width;//���
	float length;//�趨Ϊ���������û���ĺ��򳤣�
	
	float angleZ=0;//ʵʱ��ת�Ŷ��Ƕ�   ��Z��
	float angleX=0;//ʵʱ��ת�Ŷ��Ƕ�   ��X��
	float angleY=0;//ʵʱ��ת�Ŷ��Ƕ�   ��Y��
	
	
	public Cube(float height,float length,float width,
		int textureID1,int textureID2,int textureID3,
			int textureID4,int textureID5,int textureID6)
	{
		this.height=height;//�趨Ϊ�ߣ��������û�������򳤣�
		this.length=length;
		this.width=width;
		
		//������С��������ζ���
		texture1=new TextureRect(length,height,textureID1);//ǰ��
		texture2=new TextureRect(length,height,textureID2);//����
		texture3=new TextureRect(length,width,textureID3);//����
		texture4=new TextureRect(length,width,textureID4);//����
		texture5=new TextureRect(height,width,textureID5);//����
		texture6=new TextureRect(height,width,textureID6);//����

	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		
        gl.glRotatef(angleY, 0, 1, 0);
        gl.glRotatef(angleZ, 0, 0, 1);
		
		//����ǰС��
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, UNIT_SIZE*width);
		texture1.drawSelf(gl);		
		gl.glPopMatrix();
		
		//���ƺ�С��
		gl.glPushMatrix();		
		gl.glTranslatef(0, 0, -UNIT_SIZE*width);
		gl.glRotatef(180, 0, 1, 0);
		texture2.drawSelf(gl);		
		gl.glPopMatrix();
		
		//�����ϴ���
		gl.glPushMatrix();			
		gl.glTranslatef(0,UNIT_SIZE*height,0);
		gl.glRotatef(-90, 1, 0, 0);
		texture3.drawSelf(gl);
		gl.glPopMatrix();
		
//		//�����´���
//		gl.glPushMatrix();			
//		gl.glTranslatef(0,-UNIT_SIZE*height,0);
//		gl.glRotatef(90, 1, 0, 0);
//		texture4.drawSelf(gl);
//		gl.glPopMatrix();
		
		//���������
		gl.glPushMatrix();			
		gl.glTranslatef(UNIT_SIZE*length,0,0);		
		gl.glRotatef(-90, 1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
		texture5.drawSelf(gl);
		gl.glPopMatrix();
		
		//�����Ҵ���
		gl.glPushMatrix();			
		gl.glTranslatef(-UNIT_SIZE*length,0,0);		
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
		texture6.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}
}
