package wyf.ytl;
import android.graphics.Bitmap;//引入相关类
import android.graphics.BitmapFactory;//引入相关类
import android.graphics.Canvas;//引入相关类
import android.graphics.Paint;//引入相关类
import android.view.SurfaceHolder;//引入相关类
import android.view.SurfaceView;//引入相关类
public class ProcessView extends SurfaceView implements SurfaceHolder.Callback {
	PlaneActivity activity;//activity的引用
	private TutorialThread thread;//刷帧的线程
	Paint paint;//画笔
	Bitmap processBitmap;//加载
	Bitmap processBackground;//背景图片
	Bitmap processMan;//人的图片
	int process = 0;//0到100表示进度
	int startX = 90;
	int startY = 150;
	int type;//当前加载的是哪个View
	int k = 0;//加载中的点点
	public ProcessView(PlaneActivity activity, int type) {//构造器 
		super(activity);
		this.activity = activity;//得到activity的引用
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);//初始化重绘线程
        this.type = type;//当前加载的是那么界面
        initBitmap();//初始化图片资源
	}
	public void initBitmap(){//初始化图片资源的方法
		paint = new Paint();//创建画笔
		paint.setTextSize(12);//设置字体大小
		processBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.progress);
		processBackground = BitmapFactory.decodeResource(getResources(), R.drawable.processbackground);
		processMan = BitmapFactory.decodeResource(getResources(), R.drawable.processman);
	}
	public void onDraw(Canvas canvas){//自己写的绘制方法
		//画的内容是z轴的，后画的会覆盖前面画的
		canvas.drawBitmap(processBackground, -10, 10, paint);//绘制背景图片
		canvas.drawBitmap(processMan, 10, 130, paint);//绘制人的图片
		canvas.drawBitmap(processBitmap, startX, startY, paint);
		canvas.drawRect(startX+process*(processBitmap.getWidth()/100), startY, startX+processBitmap.getWidth(), startY+processBitmap.getHeight(), paint);
		if(k == 0){
			canvas.drawText("加载中", startX+(processBitmap.getWidth()/2)-20,
					startY+processBitmap.getHeight()+20, paint);
		}
		else if(k == 1){
			canvas.drawText("加载中.", startX+(processBitmap.getWidth()/2)-20, 
					startY+processBitmap.getHeight()+20, paint);
		}
		else if(k == 2){
			canvas.drawText("加载中..", startX+(processBitmap.getWidth()/2)-20, 
					startY+processBitmap.getHeight()+20, paint);
		}
		else{
			canvas.drawText("加载中...", startX+(processBitmap.getWidth()/2)-20, 
					startY+processBitmap.getHeight()+20, paint);
		}
		canvas.drawRect(0, 0, 480, 48, paint);//绘制上下的黑框
		canvas.drawRect(0, 270, 480, 320, paint);
		k = (k+1)%4;
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {//创建是被调用
        this.thread.setFlag(true);//设置线程标志位
        this.thread.start();//启动线程
	}
	public void surfaceDestroyed(SurfaceHolder holder) {//摧毁时被调用
        boolean retry = true;//循环标志位
        thread.setFlag(false);//设置循环标志位
        while (retry) {
            try {
                thread.join();//等待线程结束
                retry = false;
            } 
            catch (InterruptedException e) {}//不断地循环，直到刷帧线程结束
        }
	}
	class TutorialThread extends Thread{//刷帧线程
		private int span = 400;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private ProcessView processView;//processView引用
		private boolean flag = false;//循环标志位
        public TutorialThread(SurfaceHolder surfaceHolder, ProcessView processView) {//构造器
            this.surfaceHolder = surfaceHolder;
            this.processView = processView;//得到加载界面
        }
        public void setFlag(boolean flag) {//设置标志位
        	this.flag = flag;
        }
		public void run() {//重写的run方法
			Canvas c;//画布
            while (this.flag) {//循环
                c = null;
                try {
                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	processView.onDraw(c);//调用绘制方法
                    }
                } finally {//使用finally语句保证下面代码一定被执行
                    if (c != null) {
                    	//更新屏幕显示内容
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                if(processView.process >= 100){//当加载完成时
                	if(processView.type == 1){//切到WelcomeView
                		processView.activity.myHandler.sendEmptyMessage(4);//向主activity发送Handler消息
                	}
                	else if(processView.type == 2){//切到GameView
                		processView.activity.myHandler.sendEmptyMessage(6);//向主activity发送Handler消息
                	}
                }
                try{
                	Thread.sleep(span);//睡眠指定毫秒数
                }
                catch(Exception e){//捕获异常信息
                	e.printStackTrace();//打印异常信息
                }
            }
		}
	}
}