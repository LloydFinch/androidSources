package com.bn.box2d.jx;
import static com.bn.box2d.jx.Constant.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView 
implements SurfaceHolder.Callback  //ʵ���������ڻص��ӿ�
{
	MyBox2dActivity activity;
	Paint paint;//����		
	DrawThread dt;//�����߳�
	
	public GameView(MyBox2dActivity activity) 
	{
		super(activity);
		this.activity = activity;		
		//�����������ڻص��ӿڵ�ʵ����
		this.getHolder().addCallback(this);
		//��ʼ������
		paint = new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		//���������߳�
		dt=new DrawThread(this);
		dt.start();
	} 

	public void onDraw(Canvas canvas)
	{		
		if(canvas==null)
		{
			return;
		}
		canvas.drawARGB(255, 220, 220, 220);
		
		//���Ƴ����е�����
		for(MyBody mb:activity.bl)
		{
			mb.drawSelf(canvas, paint);
		}
		
		//���ƻ�����
		paint.setColor(Color.RED);
		canvas.drawLine(11*kd, SCREEN_HEIGHT-16*kd, 5.5f*kd, SCREEN_HEIGHT-16*kd, paint);
		canvas.drawLine(11*kd, SCREEN_HEIGHT-16*kd, 11*kd,activity.ball.body.getWorldCenter().y*RATE-kd,paint);
		canvas.drawLine(5.5f*kd, SCREEN_HEIGHT-16*kd, 5.5f*kd,activity.clt2.body.getWorldCenter().y*RATE-3*kd,paint);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
	{
		
	}

	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������
		repaint();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//����ʱ������

	}
	
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();
		Canvas canvas = holder.lockCanvas();//��ȡ����
		try{
			synchronized(holder){
				onDraw(canvas);//����
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
}