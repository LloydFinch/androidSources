package com.bn.chess;
import static com.bn.chess.ViewConstant.scaleToFit;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import static com.bn.chess.ViewConstant.*;
public class WelcomeView extends SurfaceView 
implements SurfaceHolder.Callback   //ʵ���������ڻص��ӿ�
{
	Chess_DJB_Activity activity;//activity������
	Paint paint;      //����
	int currentAlpha=0;  //��ǰ�Ĳ�͸��ֵ
//	int screenWidth=480;   //��Ļ���
//	int screenHeight=320;  //��Ļ�߶�
	int sleepSpan=50;      //������ʱ��ms
	Bitmap[] logos=new Bitmap[2];//logoͼƬ����
	Bitmap currentLogo;  //��ǰlogoͼƬ����
	int currentX;      //ͼƬλ��
	int currentY;
	public WelcomeView(Chess_DJB_Activity activity)
	{
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);  //�����������ڻص��ӿڵ�ʵ����
		paint = new Paint();  //��������
		paint.setAntiAlias(true);  //�򿪿����
		//����ͼƬ
		float xZoom=ViewConstant.xZoom;
		if(xZoom<1)
		{
			xZoom*=1.5f;
		}
		logos[0]=scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.baina),xZoom);
		logos[1]=scaleToFit(BitmapFactory.decodeResource(activity.getResources(), R.drawable.bnkjs),xZoom);		
	}
	public void onDraw(Canvas canvas)
	{	
		//���ƺ��������屳��
		paint.setColor(Color.BLACK);//���û�����ɫ
		paint.setAlpha(255);//���ò�͸����Ϊ255
		canvas.drawRect(0, 0, width, height, paint);
		//����ƽ����ͼ
		if(currentLogo==null)return;
		paint.setAlpha(currentAlpha);		
		canvas.drawBitmap(currentLogo, currentX, currentY, paint);	
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
	}
	public void surfaceCreated(SurfaceHolder holder) //����ʱ������	
	{	
		new Thread()
		{
			public void run()
			{
				for(Bitmap bm:logos)
				{
					currentLogo=bm;//��ǰͼƬ������
					currentX=(int) (width/2-bm.getWidth()/2);//ͼƬλ��
					currentY=(int) (height/2-bm.getHeight()/2);
					for(int i=255;i>-10;i=i-10)
					{//��̬����ͼƬ��͸����ֵ�������ػ�	
						currentAlpha=i;
						if(currentAlpha<0)//�����ǰ��͸����С����
						{
							currentAlpha=0;//����͸������Ϊ��
						}
						SurfaceHolder myholder=WelcomeView.this.getHolder();//��ȡ�ص��ӿ�
						Canvas canvas = myholder.lockCanvas();//��ȡ����
						try{
							synchronized(myholder)//ͬ��
							{
								onDraw(canvas);//���л��ƻ���
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							if(canvas!= null)//�����ǰ������Ϊ��
							{
								myholder.unlockCanvasAndPost(canvas);//��������
							}
						}
						try
						{
							if(i==255)//������ͼƬ����ȴ�һ��
							{
								Thread.sleep(1000);
							}
							Thread.sleep(sleepSpan);
						}
						catch(Exception e)//�׳��쳣
						{
							e.printStackTrace();
						}
					}
				}
				activity.hd.sendEmptyMessage(0);//������Ϣ����ʼ��������ģ��
			}
		}.start();
	}
	public void surfaceDestroyed(SurfaceHolder arg0)
	{//����ʱ������
	}
}