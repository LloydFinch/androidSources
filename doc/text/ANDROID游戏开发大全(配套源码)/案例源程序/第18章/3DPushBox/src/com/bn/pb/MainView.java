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

public class MainView extends SurfaceView 
implements SurfaceHolder.Callback{
	
	MyActivity activity;
	Paint paint;//����
	
	KeyThread keythread;//�������ˢ֡�߳�
	MainMenuButton startbutton;//��ʼ��ť������
	MainMenuButton set;//���ð�ť������
	
	float pointx;//��Ļ�ϴ��ص��ĵ�
	float pointy;//��Ļ�ϴ��ص��ĵ�
	
	Bitmap mainbackground;//������ı���ͼƬ
	//��ʼ��ťͼƬ 
	Bitmap mainstartbutton1;
	Bitmap mainstartbutton2;
	//����ͼƬ
	Bitmap set_pic;

	public MainView(MyActivity activity) {
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
		//���ƿ�ʼ��ť
		startbutton.drawSelf(canvas, paint);
		//�������ð�ť
		set.drawSelf(canvas, paint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		pointx=(float) event.getX();
		pointy=(float) event.getY();
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN://����
			if(startbutton.isPointInRect(pointx, pointy))
			{	//��ת����Ϸ����
				startbutton.switchOff();//�任ͼƬ
				activity.sendMessage(1);//������Ϣ��������Ϸ����
				
			}else if(set.isPointInRect(pointx, pointy))
			{
				//��ת���������ֿ���ҳ��
				activity.sendMessage(2);
			}
			break;
		case MotionEvent.ACTION_UP://̧��
			startbutton.switchOn();//�任ͼƬ
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
		startbutton=new MainMenuButton(activity,mainstartbutton1,mainstartbutton2,
				9*Constant.SCREEN_WIDTH/32,5*Constant.SCREEN_HEIGHT/16);
		//���ð�ť��������
		set=new MainMenuButton(activity,set_pic,set_pic,0,4*Constant.SCREEN_HEIGHT/5);

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
		//��ʼ��ťonͼƬ
		mainstartbutton1=BitmapFactory.decodeResource(this.getResources(), R.drawable.start_button1);
		mainstartbutton1=PicLoadUtil.scaleToFit(mainstartbutton1,5*Constant.SCREEN_WIDTH/16, 9*Constant.SCREEN_HEIGHT/32);
		//��ʼ��ťoffͼƬ
		mainstartbutton2=BitmapFactory.decodeResource(this.getResources(), R.drawable.start_button2);
		mainstartbutton2=PicLoadUtil.scaleToFit(mainstartbutton1,5*Constant.SCREEN_WIDTH/16, 9*Constant.SCREEN_HEIGHT/32);
		//����ͼƬ
		set_pic=BitmapFactory.decodeResource(this.getResources(), R.drawable.set);
		set_pic=PicLoadUtil.scaleToFit(set_pic,Constant.SCREEN_HEIGHT/5, Constant.SCREEN_HEIGHT/5);
		
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
