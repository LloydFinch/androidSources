package com.bn.game.chap11.ex14;
import javax.microedition.khronos.opengles.GL10;
public class RegularPyramid
{
	Circle bottomCircle;//底圆
	Circle topCircle;//顶圆
	RegularPyramidSide regularPyramidSide;//侧面
	float xAngle=0;//绕x轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度
    float size;//尺寸
    float h;
	public RegularPyramid(float scale,float r, float h, int n, 
			int BottomTexId, int sideTexId)
	{
		bottomCircle=new Circle(scale,r,n, BottomTexId);
		regularPyramidSide=new RegularPyramidSide(scale, r,  h, n, sideTexId);
		// 初始化完成后再改变各量的值
		size=Constant.UNIT_SIZE*scale;
		this.h=h*size;
	}
	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(xAngle, 1, 0, 0);
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glRotatef(zAngle, 0, 0, 1);		
		//底面
		gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(180, 0, 0, 1);
		bottomCircle.drawSelf(gl);
		gl.glPopMatrix();
		//侧面
		gl.glPushMatrix();
		regularPyramidSide.drawSelf(gl);
		gl.glPopMatrix();
	}
}
