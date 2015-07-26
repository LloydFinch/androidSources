package com.bn.game.chap11.ex10;
import javax.microedition.khronos.opengles.GL10;
public class RegularPyramid
{
	Circle bottomCircle;//��Բ
	Circle topCircle;//��Բ
	RegularPyramidSide regularPyramidSide;//����
	float xAngle=0;//��x����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    float size;//�ߴ�
    float h;
	public RegularPyramid(float scale,float r, float h, int n, 
			int BottomTexId, int sideTexId)
	{
		bottomCircle=new Circle(scale,r,n, BottomTexId);
		regularPyramidSide=new RegularPyramidSide(scale, r,  h, n, sideTexId);
		// ��ʼ����ɺ��ٸı������ֵ
		size=Constant.UNIT_SIZE*scale;
		this.h=h*size;
	}
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(xAngle, 1, 0, 0);
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glRotatef(zAngle, 0, 0, 1);		
		//����
		gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(180, 0, 0, 1);
		bottomCircle.drawSelf(gl);
		gl.glPopMatrix();
		//����
		gl.glPushMatrix();
		regularPyramidSide.drawSelf(gl);
		gl.glPopMatrix();
	}
}
