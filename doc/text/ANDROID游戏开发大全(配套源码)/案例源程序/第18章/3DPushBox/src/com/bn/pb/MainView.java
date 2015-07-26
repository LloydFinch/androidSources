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

public class MainView extends SurfaceView 
implements SurfaceHolder.Callback{
	
	MyActivity activity;
	Paint paint;//画笔
	
	KeyThread keythread;//主界面的刷帧线程
	MainMenuButton startbutton;//开始按钮的索引
	MainMenuButton set;//设置按钮的索引
	
	float pointx;//屏幕上触控到的点
	float pointy;//屏幕上触控到的点
	
	Bitmap mainbackground;//主界面的背景图片
	//开始按钮图片 
	Bitmap mainstartbutton1;
	Bitmap mainstartbutton2;
	//设置图片
	Bitmap set_pic;

	public MainView(MyActivity activity) {
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
		//绘制开始按钮
		startbutton.drawSelf(canvas, paint);
		//绘制设置按钮
		set.drawSelf(canvas, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		pointx=(float) event.getX();
		pointy=(float) event.getY();
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://按下
			if(startbutton.isPointInRect(pointx, pointy))
			{	//跳转到游戏界面
				startbutton.switchOff();//变换图片
				activity.sendMessage(1);//发送消息，进入游戏界面
				
			}else if(set.isPointInRect(pointx, pointy))
			{
				//跳转到背景音乐控制页面
				activity.sendMessage(2);
			}
			break;
		case MotionEvent.ACTION_UP://抬起
			startbutton.switchOn();//变换图片
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
		startbutton=new MainMenuButton(activity,mainstartbutton1,mainstartbutton2,
				9*Constant.SCREEN_WIDTH/32,5*Constant.SCREEN_HEIGHT/16);
		//设置按钮创建对象
		set=new MainMenuButton(activity,set_pic,set_pic,0,4*Constant.SCREEN_HEIGHT/5);

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
		//开始按钮on图片
		mainstartbutton1=BitmapFactory.decodeResource(this.getResources(), R.drawable.start_button1);
		mainstartbutton1=PicLoadUtil.scaleToFit(mainstartbutton1,5*Constant.SCREEN_WIDTH/16, 9*Constant.SCREEN_HEIGHT/32);
		//开始按钮off图片
		mainstartbutton2=BitmapFactory.decodeResource(this.getResources(), R.drawable.start_button2);
		mainstartbutton2=PicLoadUtil.scaleToFit(mainstartbutton1,5*Constant.SCREEN_WIDTH/16, 9*Constant.SCREEN_HEIGHT/32);
		//设置图片
		set_pic=BitmapFactory.decodeResource(this.getResources(), R.drawable.set);
		set_pic=PicLoadUtil.scaleToFit(set_pic,Constant.SCREEN_HEIGHT/5, Constant.SCREEN_HEIGHT/5);
		
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
