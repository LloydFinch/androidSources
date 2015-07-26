package com.bn.pb;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.pb.Constant.*;

public class Cube 
{
	TextureRect texture1;//小面纹理矩形的前面
	TextureRect texture2;//小面纹理矩形的前面
	TextureRect texture3;//上下两个面
	TextureRect texture4;//上下两个面
	TextureRect texture5;//左右两个面
	TextureRect texture6;//左右两个面
	
	float height;//设定为高（即面向用户面的竖向长）
	float width;//厚度
	float length;//设定为宽（即面向用户面的横向长）
	
	float angleZ=0;//实时旋转扰动角度   绕Z轴
	float angleX=0;//实时旋转扰动角度   绕X轴
	float angleY=0;//实时旋转扰动角度   绕Y轴
	
	
	public Cube(float height,float length,float width,
		int textureID1,int textureID2,int textureID3,
			int textureID4,int textureID5,int textureID6)
	{
		this.height=height;//设定为高（即面向用户面的竖向长）
		this.length=length;
		this.width=width;
		
		//创建大小面纹理矩形对象
		texture1=new TextureRect(length,height,textureID1);//前面
		texture2=new TextureRect(length,height,textureID2);//后面
		texture3=new TextureRect(length,width,textureID3);//上面
		texture4=new TextureRect(length,width,textureID4);//下面
		texture5=new TextureRect(height,width,textureID5);//左面
		texture6=new TextureRect(height,width,textureID6);//右面

	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();
		
        gl.glRotatef(angleY, 0, 1, 0);
        gl.glRotatef(angleZ, 0, 0, 1);
		
		//绘制前小面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, UNIT_SIZE*width);
		texture1.drawSelf(gl);		
		gl.glPopMatrix();
		
		//绘制后小面
		gl.glPushMatrix();		
		gl.glTranslatef(0, 0, -UNIT_SIZE*width);
		gl.glRotatef(180, 0, 1, 0);
		texture2.drawSelf(gl);		
		gl.glPopMatrix();
		
		//绘制上大面
		gl.glPushMatrix();			
		gl.glTranslatef(0,UNIT_SIZE*height,0);
		gl.glRotatef(-90, 1, 0, 0);
		texture3.drawSelf(gl);
		gl.glPopMatrix();
		
//		//绘制下大面
//		gl.glPushMatrix();			
//		gl.glTranslatef(0,-UNIT_SIZE*height,0);
//		gl.glRotatef(90, 1, 0, 0);
//		texture4.drawSelf(gl);
//		gl.glPopMatrix();
		
		//绘制左大面
		gl.glPushMatrix();			
		gl.glTranslatef(UNIT_SIZE*length,0,0);		
		gl.glRotatef(-90, 1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
		texture5.drawSelf(gl);
		gl.glPopMatrix();
		
		//绘制右大面
		gl.glPushMatrix();			
		gl.glTranslatef(-UNIT_SIZE*length,0,0);		
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(-90, 0, 1, 0);
		texture6.drawSelf(gl);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}
}
