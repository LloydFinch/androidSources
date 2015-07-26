package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FailView extends SurfaceView implements SurfaceHolder.Callback {
	PlaneActivity activity;
	private TutorialThread thread;//刷帧的线程
	
	Bitmap fialBackground;//背景
	Bitmap goon;//重新游戏按钮
	Bitmap exit2;//退出按钮
	Paint paint;//画笔
	
	public FailView(PlaneActivity activity) {//构造器 
		super(activity);
		this.activity = activity;
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);
        initBitmap();//初始化图片资源 
	}
	public void initBitmap(){//初始化图片资源的方法
		paint = new Paint();
		fialBackground = BitmapFactory.decodeResource(getResources(), R.drawable.fialbackground);
		goon = BitmapFactory.decodeResource(getResources(), R.drawable.goon);
		exit2 = BitmapFactory.decodeResource(getResources(), R.drawable.exit2);
	}
	public void onDraw(Canvas canvas){//自己写的绘制方法
		//画的内容是z轴的，后画的会覆盖前面画的
		canvas.drawBitmap(fialBackground, 0, 0, paint);
		canvas.drawBitmap(goon, 10, 50, paint);
		canvas.drawBitmap(exit2, 10, 90, paint);
		
		canvas.drawRect(0, 0, 480, 48, paint);//绘制上下的黑框
		canvas.drawRect(0, 270, 480, 320, paint);
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {
        this.thread.setFlag(true);
        this.thread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setFlag(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } 
            catch (InterruptedException e) {//不断地循环，直到刷帧线程结束
            }
        }
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {//屏幕监听
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(event.getX() > 10 && event.getX() < 10+goon.getWidth()
					&& event.getY() > 50 && event.getY() < 50+goon.getHeight()){//点击了重新游戏按钮
				Message msg1 = activity.myHandler.obtainMessage(2);
				activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息			
			}
			else if(event.getX() > 10 && event.getX() < 10+exit2.getWidth()
					&& event.getY() > 90 && event.getY() < 90+exit2.getHeight()){//点击了退出游戏按钮
				System.exit(0);
			}
		}
		return super.onTouchEvent(event);
	}
	class TutorialThread extends Thread{//刷帧线程
		private int span = 500;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private FailView fialView;
		private boolean flag = false;
        public TutorialThread(SurfaceHolder surfaceHolder, FailView fialView) {//构造器
            this.surfaceHolder = surfaceHolder;
            this.fialView = fialView;
        }
        public void setFlag(boolean flag) {
        	this.flag = flag;
        }
		@Override
		public void run() {
			Canvas c;
            while (this.flag) {
                c = null;
                try {
                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	fialView.onDraw(c);
                    }
                } finally {
                    if (c != null) {
                    	//更新屏幕显示内容
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(span);
                }
                catch(Exception e){
                	e.printStackTrace();
                }
            }
		}
	}
}