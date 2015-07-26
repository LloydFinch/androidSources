package com.bn.box2d.sndls;
import static com.bn.box2d.sndls.Constant.*;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Taj 
{
	int tajIndex;//对应动物的编号
	float xoffset;//x位置
	float yoffset;//y位置
	float vx;//速度   
	float vy;
	int timeSpan=0;
	float maxOffset=1f;//猪头开始的宽度
	public Taj(int tajIndex,float xoffset,float yoffset,float vx,float vy)
	{
		this.tajIndex=tajIndex;
		this.xoffset=xoffset;
		this.yoffset=yoffset;
		this.vx=vx;
		this.vy=vy;
	}
	//绘制方法
	public void drawSelf(Canvas canvas,Paint paint)
	{
		float x=xoffset+vx*timeSpan;
		float y=yoffset-vy*timeSpan+0.5f*timeSpan*timeSpan*1.0f;
		float offset=0.3f;
		Matrix m1=new Matrix();
		m1.setTranslate(x, y);
		Matrix m2=new Matrix();
		if(vx>=5&&vy>=5)
		{
			m2.setScale(maxOffset-offset, maxOffset-offset);
		}
		else if(vx<5&&vy<5)
		{
			m2.setScale(maxOffset+offset, maxOffset+offset);
		}
		else
		{
			m2.setScale(maxOffset, maxOffset);
		} 
		Matrix m3=new Matrix();
		m3.setConcat(m1, m2);
		//在范围之外就不再绘制
		if(xoffset<0||xoffset>SCREEN_WIDTH||yoffset>SCREEN_HEIGHT)
		{
			return;
		}
		else
		{
			canvas.drawBitmap(PE_ARRAY[tajIndex], m3, paint);
		}
	}
}