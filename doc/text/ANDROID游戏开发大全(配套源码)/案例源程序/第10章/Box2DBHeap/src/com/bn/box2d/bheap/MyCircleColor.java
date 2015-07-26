package com.bn.box2d.bheap;
import org.jbox2d.dynamics.Body;
import android.graphics.Canvas;
import android.graphics.Paint;
import static com.bn.box2d.bheap.Constant.*;

//自定义的圆形类
public class MyCircleColor extends MyBody
{
	float radius;//半径
	
	public MyCircleColor(Body body,float radius,int color)
	{
		this.body=body;
		this.radius=radius;	
		this.color=color;
	}
	
	public void drawSelf(Canvas canvas,Paint paint)
	{ 		  
		paint.setColor(color&0x8CFFFFFF); 
		float x=body.getPosition().x*RATE;
		float y=body.getPosition().y*RATE;		
		canvas.drawCircle(x, y, radius, paint);   
		paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);  
		paint.reset();
	}
}
