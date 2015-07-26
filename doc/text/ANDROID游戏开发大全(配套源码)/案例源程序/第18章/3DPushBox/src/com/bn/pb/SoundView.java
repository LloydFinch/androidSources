package com.bn.pb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SoundView extends SurfaceView 
implements SurfaceHolder.Callback{
	
	MyActivity activity;
	Paint paint;//����
	
	KeyThread keythread;//�������ˢ֡�߳�
	MainMenuButton backgroundSound;//�������ְ�ť������
	MainMenuButton soundEffect;//��Ч��ť������
	
	float pointx;//��Ļ�ϴ��ص��ĵ�
	float pointy;//��Ļ�ϴ��ص��ĵ�
	
	Bitmap mainbackground;//������ı���ͼƬ
	//���ְ�ťͼƬ 
	Bitmap backgroundsound_pic1;
	Bitmap backgroundsound_pic2;
	//��Ч��ťͼƬ
	Bitmap soundeffect_pic1;
	Bitmap soundeffect_pic2;

	public SoundView(MyActivity activity) {
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
		//����������ı���ͼƬ
		canvas.drawBitmap(mainbackground, 0, 0, paint);
		//�������ְ�ť
		backgroundSound.drawSelf(canvas, paint);
		//������Ч��ť
		soundEffect.drawSelf(canvas, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		pointx=(float) event.getX();
		pointy=(float) event.getY();
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://����
			if(backgroundSound.isPointInRect(pointx, pointy))
			{//��ת����Ϸ����
				backgroundSound.setswitch();
				activity.soundFlag=backgroundSound.isOnflag();//���������Ŀ������ر�
			}
			
			else if(soundEffect.isPointInRect(pointx, pointy))
			{
				soundEffect.setswitch();
				activity.soundPoolFlag=soundEffect.isOnflag();
			}
			break;
		case MotionEvent.ACTION_UP://̧��
			
			break;
		}
		return true;
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		paint=new Paint();//��������
		paint.setAntiAlias(true);//�򿪿����
		
		initBitmap();//��ʼ��λͼ��Դ
		keythread=new KeyThread();//ˢ֡�̴߳�������
		//��ʼ��ť��������
		backgroundSound=new MainMenuButton(activity,backgroundsound_pic1,backgroundsound_pic2,
				Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/4);
		//���ð�ť��������
		soundEffect=new MainMenuButton(activity,soundeffect_pic1,soundeffect_pic2,
				Constant.SCREEN_WIDTH/3,Constant.SCREEN_HEIGHT/2);
		
		
		
		
		keythread.setKeyFlag(true);//ˢ֡�̵߳ı�־λ��Ϊtrue
		keythread.start();//����ˢ֡�߳�
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		keythread.setKeyFlag(false);//ˢ֡�̵߳ı�־λ��Ϊfalse
	}
	//��ʼ��ͼƬ��Դ
	public void initBitmap(){
		//������ı���ͼƬ
		mainbackground=BitmapFactory.decodeResource(this.getResources(), R.drawable.main_background);
		mainbackground=PicLoadUtil.scaleToFit(mainbackground,Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
		
		//--------------------------------���ְ�ťͼƬ------------------------------------
		backgroundsound_pic1=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound1);
		backgroundsound_pic1=PicLoadUtil.scaleToFit(backgroundsound_pic1,5*Constant.SCREEN_WIDTH/16, Constant.SCREEN_HEIGHT/4);
		//���ְ�ťͼƬ
		backgroundsound_pic2=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound2);
		backgroundsound_pic2=PicLoadUtil.scaleToFit(backgroundsound_pic2,5*Constant.SCREEN_WIDTH/16, Constant.SCREEN_HEIGHT/4);
		
		//--------------------------------��Ч��ťͼƬ----------------------------------------------
		soundeffect_pic1=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound3);
		soundeffect_pic1=PicLoadUtil.scaleToFit(soundeffect_pic1,5*Constant.SCREEN_WIDTH/16, Constant.SCREEN_HEIGHT/4);
		//��Ч��ťͼƬ
		soundeffect_pic2=BitmapFactory.decodeResource(this.getResources(), R.drawable.sound4);
		soundeffect_pic2=PicLoadUtil.scaleToFit(soundeffect_pic2,5*Constant.SCREEN_WIDTH/16, Constant.SCREEN_HEIGHT/4);
		
	}
	
	//ˢ֡�߳�
	private class KeyThread extends Thread
	{
		private boolean keyFlag=false;
		@Override
		public void run()
		{
			while(isKeyFlag())
			{
				try
				{
					Thread.sleep(20);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				repaint();
			}
		}
		public void setKeyFlag(boolean keyFlag) {//�����̵߳Ŀ�ʼ��ֹͣ
			this.keyFlag = keyFlag;
		}
		public boolean isKeyFlag() {//�õ��̵߳ı�־λ
			return keyFlag;
		}
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
