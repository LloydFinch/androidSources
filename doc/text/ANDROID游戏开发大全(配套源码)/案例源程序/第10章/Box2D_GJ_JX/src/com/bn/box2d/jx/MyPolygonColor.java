package com.bn.box2d.jx;
import org.jbox2d.dynamics.Body;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import static com.bn.box2d.jx.Constant.*;

//自定义的矩形类(颜色)
public class MyPolygonColor extends MyBody
{	
	Path path;
	float[][] points;
	public MyPolygonColor(Body body,int color,float[][] points)
	{
		this.body=body;	
		this.color=color;
		path=new Path();
		this.points=points;
	}
	
	public void drawSelf(Canvas canvas,Paint paint)
	{ 		
		paint.setColor(color&0x8CFFFFFF); 
		float x=body.getPosition().x*RATE;
		float y=body.getPosition().y*RATE;
		float angle=body.getAngle();
	    canvas.save();
	    Matrix m1=new Matrix();
	    m1.setRotate((float)Math.toDegrees(angle),x, y);
	    canvas.setMatrix(m1);   
	    path.reset();
        path.moveTo(x+points[0][0], y+points[0][1]);
        for(int i=1;i<points.length;i++)
        {
        	path.lineTo(x+points[i][0], y+points[i][1]);
        }
        path.lineTo(x+points[0][0], y+points[0][1]);
        
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawPath(path, paint);
        paint.reset();
        canvas.restore();
	}
}
