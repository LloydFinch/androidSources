package wyf.wpf;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/*
 * 该类为欢迎界面的后台线程，负责定时绘制屏幕
 */
public class WelcomeDrawThread extends Thread{
	WelcomeView father;			//WelcomeView引用
	SurfaceHolder surfaceHolder;	//WelcomeView的SurfaceHolder
	boolean flag;				//循环标志位
	int sleepSpan = 30;			//休眠时间
	//构造器
	public WelcomeDrawThread(WelcomeView father,SurfaceHolder surfaceHolder){
		this.father = father;
		this.surfaceHolder = surfaceHolder;
		this.flag = true;
	}
	//线程执行方法
	public void run(){
		Canvas canvas = null;
		while(flag){
			try{
				canvas = surfaceHolder.lockCanvas(null);
				synchronized(surfaceHolder){
					father.doDraw(canvas);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				if(canvas != null){
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			try{
				Thread.sleep(sleepSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}