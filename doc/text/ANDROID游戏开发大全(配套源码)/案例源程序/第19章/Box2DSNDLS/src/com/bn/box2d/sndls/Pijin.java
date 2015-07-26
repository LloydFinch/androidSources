package com.bn.box2d.sndls;
import static com.bn.box2d.sndls.Constant.*;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Pijin
{
	//弹弓上两个拴住皮筋的位置坐标
	public static float[][] lcon=
	{
		{54,20},
		{93,20}
	};
	//老鼠的X坐标
	float lx;
	//老鼠的Y坐标
	float ly;
	//弹弓上拴住皮筋的X坐标
	float dx;
	//弹弓上拴住皮筋的Y坐标
	float dy;
	static boolean flag=false;
	public Pijin(float lx,float ly,float dx,float dy)
	{
		this.lx=lx;
		this.ly=ly;
		this.dx=dx;
		this.dy=dy;
	}
	public float[] getDegrees(float arg0,float arg1,float arg2,float arg3)
	{
		float[] degrees=new float[2];
		float yoffset=arg3-arg1;
		float xoffset=arg2-arg0;
		degrees[0]=(float) Math.sqrt(xoffset*xoffset+yoffset*yoffset);
		degrees[1]=(float) Math.toDegrees(Math.asin(yoffset/degrees[0]));
		return degrees;
	}
	//自定义的绘制方法
	public void drawSelf(Canvas canvas,Paint paint)
	{
		if(lx<150*yMainRatio&&!flag)
		{
			float[] degrees=getDegrees(lx,ly,dx,dy);
			setMatrix(canvas,paint,degrees);
		}    
		else
		{
			flag=true;
			float[] degrees=getDegrees(lx,ly,dx,dy);
			setMatrix(canvas,paint,degrees);
		}
	}
	//设置矩阵的旋转
	public void setMatrix(Canvas canvas,Paint paint,float[] degrees)
	{
		canvas.save();//保存画布状态
		//平移矩阵
		Matrix m1=new Matrix();
		m1.setTranslate(lx+PIC_ARRAY[1].getWidth(), ly+PIC_ARRAY[1].getHeight()/2);
		//旋转矩阵
		Matrix m2=new Matrix();
		m2.setRotate(degrees[1]);
		Matrix m3=new Matrix();
		m3.setConcat(m1, m2);
		Matrix m4=new Matrix();
		float xrotate=(float) (degrees[0])/PIC_ARRAY[24].getWidth();
		m4.setScale(xrotate, 1);
		Matrix m5=new Matrix();
		m5.setConcat(m3, m4);
		canvas.drawBitmap(PIC_ARRAY[24], m5, paint);
		canvas.restore();//恢复画布状态
	}
}