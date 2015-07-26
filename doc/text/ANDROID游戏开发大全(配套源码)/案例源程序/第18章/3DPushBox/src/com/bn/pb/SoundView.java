package com.bn.pb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SoundView extends SurfaceView 
implements SurfaceHolder.Callback{
	
	MyActivity activity;
	Paint paint;//画笔
	
	KeyThread keythread;//主界面的刷帧线程
	MainMenuButton backgroundSound;//背景音乐按钮的索引
	MainMenuButton soundEffect;//音效按钮的索引
	
	float pointx;//屏幕上触控到的点
	float pointy;//屏幕上触控到的点
	
	Bitmap mainbackground;//主界面的背景图片
	//音乐按钮图片 
	Bitmap backgroundsound_pic1;
	Bitmap backgroundsound_pic2;
	//音效按钮图片
	Bitmap soundeffect_pic1;
	Bitmap soundeffect_pic2;

	public SoundView(MyActivity activity) {
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
		//绘制主界面的背景图片
		canvas.drawBitmap(mainbackground, 0, 0, paint);
		//绘制音乐按钮
		backgroundSound.drawSelf(canvas, paint);
		//绘制音效按钮
		soundEffect.drawSelf(canvas, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		pointx=(float) event.getX();
		pointy=(float) event.getY();
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://按下
			if(backgroundSound.isPointInRect(pointx, pointy))
			{//跳转到游戏界面
				backgroundSound.setswitch();
				activity.soundFlag=backgroundSound.isOnflag();//设置声音的开启，关闭
			}
			
			else if(soundEffect.isPointInRect(pointx, pointy))
			{
				soundEffect.setswitch();
				activity.soundPoolFlag=soundEffect.isOnflag();
			}
			break;
		case MotionEvent.ACTION_UP://抬起
			
			break;
		}
		return true;
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		paint=new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		
		initBitmap();//初始化位图资源
		keythread=new KeyThread();//刷帧线程创建对象
		//开始按钮创建对象
		backgroundSound=new MainMenuButton(activity,backgroundsound_pic1,backgroundsound_pic2,
				Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/4);
		//设置按钮创建对象
		soundEffect=new MainMenuButton(activity,soundeffect_pic1,soundeffect_pic2,
				Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/2);
		
		
		
		
		keythread.setKeyFlag(true);//刷帧线程的标志位设为true
		keythread.start();//开启刷帧线程
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		keythread.setKeyFlag(false);//刷帧线程的标志位设为false
	}
	//初始化图片资源
	public void initBitmap(){
		//主界面的背景图片
		mainbackground=BitmapFactory.decodeResource(this.getResources(), R.drawable.main_background);
		mainbackground=PicLoadUtil.scaleToFit(mainbackground,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
		
		//--------------------------------音乐按钮图片------------------------------------
		backgroundsound_pic1=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound1);
		backgroundsound_pic1=PicLoadUtil.scaleToFit(backgroundsound_pic1,5*Constant.SCREEN_WIDTH/16, Constant.SCREEN_HEIGHT/4);
		//音乐按钮图片
		backgroundsound_pic2=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound2);
		backgroundsound_pic2=PicLoadUtil.scaleToFit(backgroundsound_pic2,5*Constant.SCREEN_WIDTH/16, Constant.SCREEN_HEIGHT/4);
		
		//--------------------------------音效按钮图片----------------------------------------------
		soundeffect_pic1=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound3);
		soundeffect_pic1=PicLoadUtil.scaleToFit(soundeffect_pic1,5*Constant.SCREEN_WIDTH/16, Constant.SCREEN_HEIGHT/4);
		//音效按钮图片
		soundeffect_pic2=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound4);
		soundeffect_pic2=PicLoadUtil.scaleToFit(soundeffect_pic2,5*Constant.SCREEN_WIDTH/16, Constant.SCREEN_HEIGHT/4);
		
	}
	
	//刷帧线程
	private class KeyThread extends Thread
	{
		private boolean keyFlag=false;
		@Override
		public void run()
		{
			while(isKeyFlag())
			{
				try
				{
					Thread.sleep(20);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				repaint();
			}
		}
		public void setKeyFlag(boolean keyFlag) {//设置线程的开始和停止
			this.keyFlag = keyFlag;
		}
		public boolean isKeyFlag() {//得到线程的标志位
			return keyFlag;
		}
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
