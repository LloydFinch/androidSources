package com.bn.game.chap11.ex14;
import javax.microedition.khronos.opengles.GL10;
public class Cylinder
{
	Circle bottomCircle;//底圆
	Circle topCircle;//顶圆
	CylinderSide cylinderSide;//侧面
	float xAngle=0;//绕x轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度
    float size;//尺寸
    float h;
	public Cylinder(float scale,float r, float h, int n, 
			int topTexId, int BottomTexId, int sideTexId)
	{
		topCircle=new Circle(scale,r,n, topTexId);
		bottomCircle=new Circle(scale,r,n, BottomTexId);
		cylinderSide=new CylinderSide(scale, r,  h, n, sideTexId);
		// 初始化完成后再改变各量的值
		size=Constant.UNIT_SIZE*scale;
		this.h=h*size;
	}
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(xAngle, 1, 0, 0);
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glRotatef(zAngle, 0, 0, 1);		
		//顶面
		gl.glPushMatrix();
		gl.glTranslatef(0, h, 0);
		gl.glRotatef(-90, 1, 0, 0);
		topCircle.drawSelf(gl);
		gl.glPopMatrix();
		//底面
		gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(180, 0, 0, 1);
		bottomCircle.drawSelf(gl);
		gl.glPopMatrix();
		//侧面
		gl.glPushMatrix();
		cylinderSide.drawSelf(gl);
		gl.glPopMatrix();
	}
}
