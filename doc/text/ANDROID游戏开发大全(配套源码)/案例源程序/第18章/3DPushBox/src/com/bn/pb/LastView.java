package com.bn.pb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LastView extends SurfaceView 
implements SurfaceHolder.Callback{
	
	MyActivity activity;
	Paint paint;//����
	
	float pointx;//��Ļ�ϴ��ص��ĵ�
	float pointy;//��Ļ�ϴ��ص��ĵ�
	
	Bitmap mainbackground;//���ս���ı���ͼƬ


	public LastView(MyActivity activity) {
		super(activity);
		this.activity=activity;
		getHolder().addCallback(this);//ע��ص��ӿ�
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.clipRect(new Rect(0,0,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT));//ֻ����Ļ��Χ�ڻ���ͼƬ
		canvas.drawColor(Color.WHITE);//��������Ϊ��ɫ
		//�������ս���ı���ͼƬ
		canvas.drawBitmap(mainbackground, 0, 0, paint);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		paint=new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		
		initBitmap();//��ʼ��λͼ��Դ
		repaint();//���ƽ���
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
	//��ʼ��ͼƬ��Դ
	public void initBitmap(){
		//������ı���ͼƬ
		mainbackground=BitmapFactory.decodeResource(this.getResources(), R.drawable.gameover);
		mainbackground=PicLoadUtil.scaleToFit(mainbackground,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
	}
	//���»��ƻ���
	public void repaint()
	{
		Canvas canvas=this.getHolder().lockCanvas();
		try
		{
			synchronized(canvas)
			{
				onDraw(canvas);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(canvas!=null)
			{
				this.getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

}
