package com.bn.game.chap11.ex14;
import javax.microedition.khronos.opengles.GL10;
public class Cube 
{
	TextureRect[] rect=new TextureRect[6];
	float xAngle=0;//绕x轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度
    float a;
    float b;
    float c;
    float size;//尺寸
	public Cube(float scale,float[] abc,int[] textureIds)
	{
		a=abc[0];
		b=abc[1];
		c=abc[2];
		rect[0]=new TextureRect(scale,a,b,textureIds[0],1,1);
		rect[1]=new TextureRect(scale,a,b,textureIds[1],1,1);
		rect[2]=new TextureRect(scale,c,b,textureIds[2],1,1);
		rect[3]=new TextureRect(scale,c,b,textureIds[3],1,1);
		rect[4]=new TextureRect(scale,a,c,textureIds[4],1,1);
		rect[5]=new TextureRect(scale,a,c,textureIds[5],1,1);
		// 初始化完成后再改变各量的值
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
        //前面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, c/2);
		rect[0].drawSelf(gl);
		gl.glPopMatrix();
		//后面
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -c/2);
		gl.glRotatef(180.0f, 0, 1, 0);
		rect[1].drawSelf(gl);
		gl.glPopMatrix();
		//右面
		gl.glPushMatrix();
		gl.glTranslatef(a/2, 0, 0);
		gl.glRotatef(90.0f, 0, 1, 0);
		rect[2].drawSelf(gl);
		gl.glPopMatrix();
		//左面
		gl.glPushMatrix();
		gl.glTranslatef(-a/2, 0, 0);
		gl.glRotatef(-90.0f, 0, 1, 0);
		rect[3].drawSelf(gl);
		gl.glPopMatrix();
		//下面
		gl.glPushMatrix();
		gl.glTranslatef(0, -b/2, 0);
		gl.glRotatef(90.0f, 1, 0, 0);
		rect[4].drawSelf(gl);
		gl.glPopMatrix();
		//上面
		gl.glPushMatrix();
		gl.glTranslatef(0, b/2, 0);
		gl.glRotatef(-90.0f, 1, 0, 0);
		rect[5].drawSelf(gl);
		gl.glPopMatrix();
	}
}
