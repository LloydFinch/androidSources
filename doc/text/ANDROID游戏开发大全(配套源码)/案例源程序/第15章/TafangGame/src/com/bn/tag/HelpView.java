package com.bn.tag;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * 
 * 显示百纳科技的动画界面
 *
 */
public class HelpView extends SurfaceView 
implements SurfaceHolder.Callback  //实现生命周期回调接口
{
	TafangGameActivity activity;
	Paint paint;//画笔
	Bitmap helppic;
	DrawThread drawThread;
	
	
	public HelpView(TafangGameActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);//设置生命周期回调接口的实现者
		paint = new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		
		//加载图片
		helppic=BitmapFactory.decodeResource(activity.getResources(), R.drawable.help); 
	}
	public void onDraw(Canvas canvas){	
		//绘制黑填充矩形清背景
		paint.setColor(Color.BLACK);//设置画笔颜色
		paint.setAlpha(255);		
		//进行平面贴图
		//if(currentLogo==null)return;				
		canvas.drawBitmap(helppic, 0, 0, paint);	
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
	
	//绘制SurfaceView的线程
	private class DrawThread extends Thread{
		private boolean flag = true;	
		private int sleepSpan = 100;
		HelpView fatherView;
		SurfaceHolder surfaceHolder;
		public DrawThread(HelpView helpView){
			this.fatherView = helpView;
			this.surfaceHolder = helpView.getHolder();
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