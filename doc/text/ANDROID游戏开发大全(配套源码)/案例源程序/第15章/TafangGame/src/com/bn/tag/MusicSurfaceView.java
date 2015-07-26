package com.bn.tag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.tag.Constant.*;
/**
 * 
 * 显示百纳科技的动画界面
 *
 */
public class MusicSurfaceView extends SurfaceView 
implements SurfaceHolder.Callback  //实现生命周期回调接口
{
	TafangGameActivity activity;
	Paint paint;//画笔
	Bitmap musicback;
	Bitmap backMusicoff;//背景音乐关
	Bitmap backMusicon;//背景音乐开
	Bitmap Yinxiaooff;//关闭游戏音效
	Bitmap Yinxiaoon;//开启游戏音效
	boolean backmusicFlag01=true;
	boolean backmusicFlag02=true;
	boolean yinxiaoFlag01=true;
	boolean yinxiaoFlag02=true;
	float pic_x=200;
	float pic_y=130;
	
	DrawThread drawThread;
	
	
	public MusicSurfaceView(TafangGameActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);//设置生命周期回调接口的实现者
		paint = new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		
		//加载图片
		backMusicoff=BitmapFactory.decodeResource(activity.getResources(), R.drawable.backmusicoff);
		backMusicon=BitmapFactory.decodeResource(activity.getResources(), R.drawable.backmusicon);
		Yinxiaooff=BitmapFactory.decodeResource(activity.getResources(), R.drawable.yinxiaooff);
		Yinxiaoon=BitmapFactory.decodeResource(activity.getResources(), R.drawable.yinxiaoon);
		musicback=BitmapFactory.decodeResource(activity.getResources(), R.drawable.musicbackground);
	}
	public void onDraw(Canvas canvas){	
		//绘制黑填充矩形清背景
		paint.setColor(Color.BLACK);//设置画笔颜色
		paint.setAlpha(255);		
		//进行平面贴图
		//if(currentLogo==null)return;				
		canvas.drawBitmap(musicback, 0, 0, paint);	
		if(backmusicFlag01)
		{
			
			canvas.drawBitmap(backMusicoff, pic_x, pic_y, paint);	
		}
		else if(!backmusicFlag01) 
		{
			canvas.drawBitmap(backMusicon, pic_x, pic_y, paint);
		}
		if(yinxiaoFlag01)
		{
			float x1=pic_x;
			float y1=pic_y+MUSIC_HEIGHT+25;
			canvas.drawBitmap(Yinxiaooff, x1, y1, paint);
		}
		else if(!yinxiaoFlag01)
		{
			float x1=pic_x;
			float y1=pic_y+MUSIC_HEIGHT+25;
			canvas.drawBitmap(Yinxiaoon, x1, y1, paint);
		}
		
//		canvas.save();
//    	canvas.clipPath(makePathDash());
//    	canvas.drawBitmap(Yinxiaoon, 0,0, null);
//    	//canvas.drawBitmap(Yinxiaooff, 0, 0, null);
//    	canvas.restore();	
//    	canvas.drawBitmap(Yinxiaooff, 0, 120, null);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();				
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		
    		//背景音乐的控制开关
    		if(x>pic_x&&x<pic_x+MUSIC_WEIGHT&&y>pic_y&&y<pic_y+MUSIC_HEIGHT)
    		{
    			backmusicFlag02=!backmusicFlag02;
    			activity.setBackGroundMusicOn(backmusicFlag02);
    			backmusicFlag01=!backmusicFlag01;
    		}
    		if(x>pic_x&&x<pic_x+MUSIC_WEIGHT&&y>pic_y+MUSIC_HEIGHT+25&&y<pic_y+MUSIC_HEIGHT+25+MUSIC_HEIGHT)
    		{
    			yinxiaoFlag02=!yinxiaoFlag02;
    			activity.setSoundOn(yinxiaoFlag02);
    			yinxiaoFlag01=!yinxiaoFlag01;
    		}
    		break;
    	}
		return true;
	}	

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}
	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用		
		drawThread=new DrawThread(this);
		drawThread.start();
		
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//销毁时被调用

		boolean retry = true;
		drawThread.setFlag(false);
	        while (retry) {
	            try {
	            	drawThread.join();
	                retry = false;
	            } 
	            catch (InterruptedException e) {e.printStackTrace();}//不断地循环，直到刷帧线程结束
	        }
	}
	
	
//	
	//绘制SurfaceView的线程
	private class DrawThread extends Thread{
		private boolean flag = true;	
		private int sleepSpan = 100;
		MusicSurfaceView fatherView;
		SurfaceHolder surfaceHolder;
		public DrawThread(MusicSurfaceView MusicSurfaceView){
			this.fatherView = MusicSurfaceView;
			this.surfaceHolder = MusicSurfaceView.getHolder();
		}
		public void run(){
			Canvas c;
	        while (this.flag) {
	            c = null;
	            try {
	            	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
	                c = this.surfaceHolder.lockCanvas(null);
	                synchronized (this.surfaceHolder) {
	                	fatherView.onDraw(c);//绘制
	                }
	            } finally {
	                if (c != null) {
	                	//并释放锁
	                    this.surfaceHolder.unlockCanvasAndPost(c);
	                }
	            }
	            try{
	            	Thread.sleep(sleepSpan);//睡眠指定毫秒数
	            }
	            catch(Exception e){
	            	e.printStackTrace();//打印堆栈信息
	            }
	        }
		}
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
	}
}