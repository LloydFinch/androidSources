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
implements SurfaceHolder.Callback   //实现生命周期回调接口
{
	Chess_DJB_Activity activity;//activity的引用
	Paint paint;      //画笔
	int currentAlpha=0;  //当前的不透明值
//	int screenWidth=480;   //屏幕宽度
//	int screenHeight=320;  //屏幕高度
	int sleepSpan=50;      //动画的时延ms
	Bitmap[] logos=new Bitmap[2];//logo图片数组
	Bitmap currentLogo;  //当前logo图片引用
	int currentX;      //图片位置
	int currentY;
	public WelcomeView(Chess_DJB_Activity activity)
	{
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);  //设置生命周期回调接口的实现者
		paint = new Paint();  //创建画笔
		paint.setAntiAlias(true);  //打开抗锯齿
		//加载图片
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
		//绘制黑填充矩形清背景
		paint.setColor(Color.BLACK);//设置画笔颜色
		paint.setAlpha(255);//设置不透明度为255
		canvas.drawRect(0, 0, width, height, paint);
		//进行平面贴图
		if(currentLogo==null)return;
		paint.setAlpha(currentAlpha);		
		canvas.drawBitmap(currentLogo, currentX, currentY, paint);	
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
	}
	public void surfaceCreated(SurfaceHolder holder) //创建时被调用	
	{	
		new Thread()
		{
			public void run()
			{
				for(Bitmap bm:logos)
				{
					currentLogo=bm;//当前图片的引用
					currentX=(int) (width/2-bm.getWidth()/2);//图片位置
					currentY=(int) (height/2-bm.getHeight()/2);
					for(int i=255;i>-10;i=i-10)
					{//动态更改图片的透明度值并不断重绘	
						currentAlpha=i;
						if(currentAlpha<0)//如果当前不透明度小于零
						{
							currentAlpha=0;//将不透明度置为零
						}
						SurfaceHolder myholder=WelcomeView.this.getHolder();//获取回调接口
						Canvas canvas = myholder.lockCanvas();//获取画布
						try{
							synchronized(myholder)//同步
							{
								onDraw(canvas);//进行绘制绘制
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						finally
						{
							if(canvas!= null)//如果当前画布不为空
							{
								myholder.unlockCanvasAndPost(canvas);//解锁画布
							}
						}
						try
						{
							if(i==255)//若是新图片，多等待一会
							{
								Thread.sleep(1000);
							}
							Thread.sleep(sleepSpan);
						}
						catch(Exception e)//抛出异常
						{
							e.printStackTrace();
						}
					}
				}
				activity.hd.sendEmptyMessage(0);//发送消息，开始加载棋子模型
			}
		}.start();
	}
	public void surfaceDestroyed(SurfaceHolder arg0)
	{//销毁时被调用
	}
}