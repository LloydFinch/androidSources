package com.bn.tag;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewDrawThread extends Thread{
	boolean flag = true;
	int sleepSpan = 50;
	GameView gameView;
	SurfaceHolder surfaceHolder;
	public GameViewDrawThread(GameView gameView){
		this.gameView = gameView;
		this.surfaceHolder = gameView.getHolder();
	}
	public void run(){
		Canvas c;
        while (flag) {
            c = null;
            try {
            	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                c = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder) {
                	gameView.onDraw(c);//绘制
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
	
	public void setFlag(boolean flag)
	{
		this.flag=flag;
	}
}
