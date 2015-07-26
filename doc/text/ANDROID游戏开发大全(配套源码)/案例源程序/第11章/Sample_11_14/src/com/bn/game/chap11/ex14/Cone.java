package com.bn.game.chap11.ex14;//声名包
import javax.microedition.khronos.opengles.GL10;//引入相关类
public class Cone{
	Circle bottomCircle;//底圆
	ConeSide coneSide;//侧面
	float xAngle=0;//绕x轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度
    float size;//尺寸
    float h;
	public Cone(float scale,float r, float h, int n, 
			int BottomTexId, int sideTexId){
		bottomCircle=new Circle(scale,r,n, BottomTexId);
		coneSide=new ConeSide(scale, r,  h, n, sideTexId);
		// 初始化完成后再改变各量的值
		size=Constant.UNIT_SIZE*scale;
		this.h=h*size;
	}
	public void drawSelf(GL10 gl){		
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
		coneSide.drawSelf(gl);
		gl.glPopMatrix();
}}

