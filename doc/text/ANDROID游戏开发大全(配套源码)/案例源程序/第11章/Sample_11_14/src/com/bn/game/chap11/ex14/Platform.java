package com.bn.game.chap11.ex14;
import javax.microedition.khronos.opengles.GL10;

public class Platform {
	Quadrangle top;
	Quadrangle bottom;
	Quadrangle side0;
	Quadrangle side1;
	Quadrangle side2;
	Quadrangle side3;
    float xAngle=0;//绕z轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度
    float h;
    float scale;
	public Platform(
			float scale,//大小
			float aBottom, float bBottom, //底面长宽
			float aTop, float bTop, //顶面长宽
			float h, //高度
			int topTexId, int bottomTexId, int sideTexId,//的纹理id
			int nS, int nT//顶面的行列数
			) 
	{	//初始化各量，不改变原始数据
		this.scale=scale*Constant.UNIT_SIZE;
		this.h=this.scale*h;
		//8个顶点坐标
		float x0=-aBottom/2; float y0=0; float z0=-bBottom/2;
		float x1=-aBottom/2; float y1=0; float z1=bBottom/2; 
		float x2=aBottom/2; float y2=0; float z2=bBottom/2; 
		float x3=aBottom/2; float y3=0; float z3=-bBottom/2; 
		
		float x4=-aTop/2; float y4=h; float z4=-bTop/2; 
		float x5=-aTop/2; float y5=h; float z5=bTop/2; 
		float x6=aTop/2; float y6=h; float z6=bTop/2; 
		float x7=aTop/2; float y7=h; float z7=-bTop/2; 
		bottom=new Quadrangle(
				scale,
        		x0,y0,z0,
        		x3,y3,z3,
        		x2,y2,z2,
        		x1,y1,z1,
        		bottomTexId,
        		nS,nT
        		);
		top=new Quadrangle(
				scale,
        		x4,y4,z4,
        		x5,y5,z5,
        		x6,y6,z6,
        		x7,y7,z7,
        		topTexId,
        		nS,nT
        		);
		side0=new Quadrangle(
				scale,
				x4,y4,z4,
				x0,y0,z0,
				x1,y1,z1,        		
        		x5,y5,z5,
        		sideTexId,
        		1,1
        		);
		side1=new Quadrangle(
				scale,
        		x5,y5,z5,
        		x1,y1,z1, 
        		x2,y2,z2,
        		x6,y6,z6,
        		sideTexId,
        		1,1
        		);
		side2=new Quadrangle(
				scale,
				x6,y6,z6,
				x2,y2,z2,
				x3,y3,z3,        		
        		x7,y7,z7,
        		sideTexId,
        		1,1
        		);
		side3=new Quadrangle(
				scale,
				x7,y7,z7,
				x3,y3,z3,
				x0,y0,z0,        		
        		x4,y4,z4,
        		sideTexId,
        		1,1
        		);
	}
    public void drawSelf(GL10 gl)
    {
    	gl.glPushMatrix();
    	//绕轴旋转
        gl.glRotatef(xAngle, 1, 0, 0);
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glRotatef(zAngle, 0, 0, 1);
    	top.drawSelf(gl);
    	bottom.drawSelf(gl);
    	side0.drawSelf(gl);
    	side1.drawSelf(gl);
    	side2.drawSelf(gl);
    	side3.drawSelf(gl);
    	gl.glPopMatrix();
    }
}
