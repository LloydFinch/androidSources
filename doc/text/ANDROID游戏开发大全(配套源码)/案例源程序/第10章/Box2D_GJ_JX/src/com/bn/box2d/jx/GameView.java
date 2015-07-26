package com.bn.box2d.jx;
import static com.bn.box2d.jx.Constant.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView 
implements SurfaceHolder.Callback  //实现生命周期回调接口
{
	MyBox2dActivity activity;
	Paint paint;//画笔		
	DrawThread dt;//绘制线程
	
	public GameView(MyBox2dActivity activity) 
	{
		super(activity);
		this.activity = activity;		
		//设置生命周期回调接口的实现者
		this.getHolder().addCallback(this);
		//初始化画笔
		paint = new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		//启动绘制线程
		dt=new DrawThread(this);
		dt.start();
	} 

	public void onDraw(Canvas canvas)
	{		
		if(canvas==null)
		{
			return;
		}
		canvas.drawARGB(255, 220, 220, 220);
		
		//绘制场景中的物体
		for(MyBody mb:activity.bl)
		{
			mb.drawSelf(canvas, paint);
		}
		
		//绘制滑轮线
		paint.setColor(Color.RED);
		canvas.drawLine(11*kd, SCREEN_HEIGHT-16*kd, 5.5f*kd, SCREEN_HEIGHT-16*kd, paint);
		canvas.drawLine(11*kd, SCREEN_HEIGHT-16*kd, 11*kd,activity.ball.body.getWorldCenter().y*RATE-kd,paint);
		canvas.drawLine(5.5f*kd, SCREEN_HEIGHT-16*kd, 5.5f*kd,activity.clt2.body.getWorldCenter().y*RATE-3*kd,paint);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
	{
		
	}

	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用
		repaint();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//销毁时被调用

	}
	
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();
		Canvas canvas = holder.lockCanvas();//获取画布
		try{
			synchronized(holder){
				onDraw(canvas);//绘制
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
}