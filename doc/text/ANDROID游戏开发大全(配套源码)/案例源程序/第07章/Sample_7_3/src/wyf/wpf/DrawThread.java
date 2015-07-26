package wyf.wpf;//声明包语句
import android.graphics.Canvas;//引入相关类
import android.view.SurfaceHolder;//引入相关类
//继承自Thread的子类，负责刷新屏幕
public class DrawThread extends Thread{
	ParticleView pv;		//ParticleView对象引用
	SurfaceHolder surfaceHolder;	//SurfaceHolder对象引用
	boolean flag;			//线程执行标志位
	int sleepSpan = 15;			//睡眠时间
	long start = System.nanoTime();	//记录起始时间，该变量用于计算帧速率
	int count=0;		//记录帧数，该变量用于计算帧速率
	//构造器，初始化主要成员变量
	public DrawThread(ParticleView pv,SurfaceHolder surfaceHolder){
		this.pv = pv;
		this.surfaceHolder = surfaceHolder;
		this.flag = true;	//设置线程执行标志位为true
	}
	//线程执行方法
	public void run(){
		Canvas canvas = null;		//声明一个Canvas对象
		while(flag){
			try{
				canvas = surfaceHolder.lockCanvas(null);//获取ParticleView的画布
				synchronized(surfaceHolder){
					pv.doDraw(canvas);			//调用ParticleView的doDraw方法进行绘制
				}
			}
			catch(Exception e){
				e.printStackTrace();			//捕获并打印异常
			}
			finally{
				if(canvas != null){		//如果canvas不为空
					surfaceHolder.unlockCanvasAndPost(canvas);//surfaceHolder解锁并将画布对象传回
				}
			}
			this.count++;
			if(count == 20){	//如果计满20帧
				count = 0;		//清空计数器
				long tempStamp = System.nanoTime();//获取当前时间
				long span = tempStamp - start;		//获取时间间隔
				start = tempStamp;					//为start重新赋值
				double fps = Math.round(100000000000.0/span*20)/100.0;//计算帧速率
				pv.fps = "FPS:"+fps;//将计算出的帧速率设置到BallView的相应字符串对象中
			}
			try{
				Thread.sleep(sleepSpan);//线程休眠一段时间
			}
			catch(Exception e){
				e.printStackTrace();//捕获并打印异常
			}
		}
	}
}