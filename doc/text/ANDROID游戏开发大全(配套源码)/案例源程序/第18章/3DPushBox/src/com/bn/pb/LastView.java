package com.bn.pb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LastView extends SurfaceView 
implements SurfaceHolder.Callback{
	
	MyActivity activity;
	Paint paint;//画笔
	
	float pointx;//屏幕上触控到的点
	float pointy;//屏幕上触控到的点
	
	Bitmap mainbackground;//最终界面的背景图片


	public LastView(MyActivity activity) {
		super(activity);
		this.activity=activity;
		getHolder().addCallback(this);//注册回调接口
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT));//只在屏幕范围内绘制图片
		canvas.drawColor(Color.WHITE);//界面设置为白色
		//绘制最终界面的背景图片
		canvas.drawBitmap(mainbackground, 0, 0, paint);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		paint=new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		
		initBitmap();//初始化位图资源
		repaint();//绘制界面
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
	//初始化图片资源
	public void initBitmap(){
		//主界面的背景图片
		mainbackground=BitmapFactory.decodeResource(this.getResources(), R.drawable.gameover);
		mainbackground=PicLoadUtil.scaleToFit(mainbackground,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
	}
	//重新绘制画面
	public void repaint()
	{
		Canvas canvas=this.getHolder().lockCanvas();
		try
		{
			synchronized(canvas)
			{
				onDraw(canvas);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(canvas!=null)
			{
				this.getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

}
