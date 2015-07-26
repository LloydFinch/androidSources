package wyf.wpf;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
/*
 * 该类继承自Thread，主要功能是提供GameView的定时绘制
 * 采用的方式是SurfaceHolder对象将画布锁上，然后对其进行
 * 绘制。
 */
public class DrawThread extends Thread{
	SurfaceHolder surfaceHolder;			//SurfaceHolder对象
	GameView gv;							//GameView对象引用
	boolean flag;							//标志位执行标志位
	boolean isGameOn;						//刷屏标志位
	int sleepSpan=25;						//休眠时间（毫秒为单位）
	//构造器
	public DrawThread(SurfaceHolder surfaceHolder,GameView gv){
		this.surfaceHolder = surfaceHolder;
		this.gv = gv;
		this.flag = true;
	}
	//方法：线程的执行方法
	public void run(){
		while(flag){
			Canvas canvas;		//创建一个Canvas对象
			while(isGameOn){	//判断游戏是否在进行中
				canvas = null;
				//锁定并绘制屏幕
				try{
					canvas = surfaceHolder.lockCanvas(null);		//锁定屏幕
					synchronized(surfaceHolder){
						gv.doDraw(canvas);					//调用相应的绘制方法
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally{		//在finally语句中释放锁
					if(canvas != null){
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
				//线程休眠
				try{
					Thread.sleep(sleepSpan);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			try{//内层循环执行完毕后的休眠
				Thread.sleep(1000);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

	}
}