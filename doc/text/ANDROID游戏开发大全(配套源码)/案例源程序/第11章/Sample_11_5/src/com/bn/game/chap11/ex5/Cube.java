package com.bn.game.chap11.ex5;
import javax.microedition.khronos.opengles.GL10;
public class Cube 
{
	TextureRect[] rect=new TextureRect[6];
	float xAngle=0;//��x����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    float a;
    float b;
    float c;
    float size;//�ߴ�
	public Cube(float scale,float[] abc,int[] textureIds)
	{
		a=abc[0];
		b=abc[1];
		c=abc[2];
		rect[0]=new TextureRect(scale,a,b,textureIds[0]);
		rect[1]=new TextureRect(scale,a,b,textureIds[1]);
		rect[2]=new TextureRect(scale,c,b,textureIds[2]);
		rect[3]=new TextureRect(scale,c,b,textureIds[3]);
		rect[4]=new TextureRect(scale,a,c,textureIds[4]);
		rect[5]=new TextureRect(scale,a,c,textureIds[5]);
		// ��ʼ����ɺ��ٸı������ֵ
		size=Constant.UNIT_SIZE*scale;
		a*=size;
		b*=size;
		c*=size;
	}
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(xAngle, 1, 0, 0);
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glRotatef(zAngle, 0, 0, 1);
        //ǰ��
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, c/2);
		rect[0].drawSelf(gl);
		gl.glPopMatrix();
		//����
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -c/2);
		gl.glRotatef(180.0f, 0, 1, 0);
		rect[1].drawSelf(gl);
		gl.glPopMatrix();
		//����
		gl.glPushMatrix();
		gl.glTranslatef(a/2, 0, 0);
		gl.glRotatef(90.0f, 0, 1, 0);
		rect[2].drawSelf(gl);
		gl.glPopMatrix();
		//����
		gl.glPushMatrix();
		gl.glTranslatef(-a/2, 0, 0);
		gl.glRotatef(-90.0f, 0, 1, 0);
		rect[3].drawSelf(gl);
		gl.glPopMatrix();
		//����
		gl.glPushMatrix();
		gl.glTranslatef(0, -b/2, 0);
		gl.glRotatef(90.0f, 1, 0, 0);
		rect[4].drawSelf(gl);
		gl.glPopMatrix();
		//����
		gl.glPushMatrix();
		gl.glTranslatef(0, b/2, 0);
		gl.glRotatef(-90.0f, 1, 0, 0);
		rect[5].drawSelf(gl);
		gl.glPopMatrix();
		
	}
}
