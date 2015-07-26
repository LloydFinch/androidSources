package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class HelpView extends SurfaceView implements SurfaceHolder.Callback {
	PlaneActivity activity;
	private TutorialThread thread;//刷帧的线程
	Paint paint;
	Bitmap background;
	Bitmap help2;
	Bitmap help3;
	Bitmap ok;
	public HelpView(PlaneActivity activity) {//构造器 
		super(activity);
		this.activity = activity;
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);
        initBitmap();//初始化图片资源
	}
	public void initBitmap(){//初始化图片资源的方法
		paint = new Paint(); 
		background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		help2 = BitmapFactory.decodeResource(getResources(), R.drawable.help2);
		help3 = BitmapFactory.decodeResource(getResources(), R.drawable.help3);
		ok = BitmapFactory.decodeResource(getResources(), R.drawable.ok);
	}
	public void onDraw(Canvas canvas){//自己写的绘制方法
		//画的内容是z轴的，后画的会覆盖前面画的
		canvas.drawColor(Color.WHITE);//将屏幕刷成白色 
		canvas.drawBitmap(background, 0,0, paint);
		canvas.drawBitmap(help2, -30, 30, paint);   
		canvas.drawBitmap(help3, 260, 70, paint);
		canvas.drawRect(0, 0, 480, 48, paint);//绘制上下的黑框
		canvas.drawRect(0, 270, 480, 320, paint); 
		canvas.drawBitmap(ok, 360, 255, paint);  
 
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
			if(event.getX()>360 && event.getX()<360+ok.getWidth()
					&& event.getY()>250 && event.getY()<250+ok.getHeight()){//点击了确定按钮
				Message msg1 = activity.myHandler.obtainMessage(7); 
				activity.myHandler.sendMessage(msg1);
			}  
		}
		return super.onTouchEvent(event);
	}
	class TutorialThread extends Thread{//刷帧线程
		private int span = 100;//睡眠的 毫秒数 
		private SurfaceHolder surfaceHolder;
		private HelpView helpView;
		private boolean flag = false;
        public TutorialThread(SurfaceHolder surfaceHolder, HelpView helpView) {//构造器
            this.surfaceHolder = surfaceHolder;
            this.helpView = helpView;
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
                    	helpView.onDraw(c);
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