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
/*
 * 
 * 主菜单界面
 *
 */
public class MainMenuSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	TafangGameActivity activity;//activity的引用
	Paint paint;//画笔引用
	//线程引用
	DrawThread drawThread;//绘制线程引用
	SelectThread selectthread;
	Bitmap mainbackgroundpic;
	Bitmap selectpic;
	float ballX=0;
	float ballY=-400;
	//虚拟按钮图片
	
	public MainMenuSurfaceView(TafangGameActivity activity) {
		super(activity);
		this.activity=activity;
		//获得焦点并设置为可触控
		this.requestFocus();
        this.setFocusableInTouchMode(true);
		getHolder().addCallback(this);//注册回调接口	
		initBitmap();//初始化位图资源	
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);  	
		//绘制背景
		//canvas.drawColor(Color.WHITE);
		try
		{
			canvas.drawBitmap(mainbackgroundpic,0,0, null);
			canvas.drawBitmap(selectpic, ballX, ballY, null);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
				
				
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();		
		float px = ballY / 300;
    	switch(event.getAction())
    	{
    	case MotionEvent.ACTION_DOWN:
    		//继续游戏
    		if(x>=&&x<=GOON_X+MAIN_LENGTH&&y>=GOON_Y&&y<=GOON_Y+MAIN_WEIGHT)
    		{
    			activity.gotoTaChuGame();
    		}
    		//新游戏
    		if(x>=NEW_X&&x<=NEW_X+MAIN_LENGTH&&y>=NEW_Y&&y<=NEW_Y+MAIN_WEIGHT)
    		{
    			activity.myHandler.sendEmptyMessage(0);
    		}
    		//积分榜
    		if(x>=JIFEN_X&&x<=JIFEN_X+MAIN_LENGTH&&y>=JIFEN_Y&&y<=JIFEN_Y+MAIN_WEIGHT)
    		{
    			activity.myHandler.sendEmptyMessage(3);
    		}
    		//音效设置
    		if(x>=YINXIAO_X&&x<=YINXIAO_X+MAIN_LENGTH&&y>=YINXIAO_Y&&y<=YINXIAO_Y+MAIN_WEIGHT)
    		{
    			activity.myHandler.sendEmptyMessage(4);
    		}
    		//帮助
    		if(x>=HELP_X&&x<=HELP_X+MAIN_LENGTH&&y>=HELP_Y&&y<=HELP_Y+MAIN_WEIGHT)
    		{
    			activity.myHandler.sendEmptyMessage(2);
    		}
    		//退出
    		if( x >= ( EXIT_X * px ) && x <= ( EXIT_X + MAIN_LENGTH ) * px 
    				&& y >= ( EXIT_Y * px ) && y <= ( EXIT_Y + MAIN_WEIGHT ) * px )
    		{
    			System.exit(0);
    		}
    		break;
    	}
		return true;
	}	
	@Override
	public void surfaceChanged( SurfaceHolder holder, int format, int width,
			int height ) {		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder){
		paint=new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		selectthread=new SelectThread(this);
		selectthread.start();
		createAllThreads();
		startAllThreads();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		  boolean retry = true;
		  stopAllThreads();
	}
	//将图片加载
	public void initBitmap(){
		mainbackgroundpic=BitmapFactory.decodeResource(this.getResources(), R.drawable.mainbackground);
		selectpic=BitmapFactory.decodeResource(this.getResources(), R.drawable.select);
	}
	
	void createAllThreads()
	{
		drawThread=new DrawThread(this);//创建绘制线程	
	}
	void startAllThreads()
	{
		drawThread.setFlag(true);     
		drawThread.start();		
	}
	void stopAllThreads()
	{
		drawThread.setFlag(false);
		//selectthread.setFlag(false);
	}
	
	//绘制SurfaceView的线程
	private class DrawThread extends Thread{
		private boolean flag = true;	
		private int sleepSpan = 100;
		MainMenuSurfaceView fatherView;
		SurfaceHolder surfaceHolder;
		public DrawThread(MainMenuSurfaceView fatherView){
			this.fatherView = fatherView;
			this.surfaceHolder = fatherView.getHolder();
		}
		public void run(){
			Canvas c;
	        while (flag) {
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
