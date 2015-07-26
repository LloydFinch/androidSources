package com.bn.tag;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * 显示游戏结束动画界面
 *
 */
public class GameOverView extends SurfaceView 
implements SurfaceHolder.Callback  //实现生命周期回调接口
{
	TafangGameActivity activity;
	Paint paint;//画笔
	int currentAlpha=0;//当前的不透明值
	
	float screenWidth=Constant.SCREEN_HEIGHT;//屏幕宽度
	float screenHeight=Constant.SCREEN_WIDTH;//屏幕高度
	int sleepSpan=50;//动画的时延ms
		
	Bitmap bitmapGameOver;//当前logo图片引用
	int currentX;
	int currentY;
	
	public GameOverView(TafangGameActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);//设置生命周期回调接口的实现者
		paint = new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		
		//加载图片
		bitmapGameOver=BitmapFactory.decodeResource(activity.getResources(), R.drawable.gameover); 
		
	}
	public void onDraw(Canvas canvas){	
		//绘制黑填充矩形清背景
		paint.setColor(Color.BLACK);//设置画笔颜色
		paint.setAlpha(255);
		canvas.drawRect(0, 0, screenWidth, screenHeight, paint);
		
		//进行平面贴图
		if(bitmapGameOver==null)return;
		paint.setAlpha(currentAlpha);		
		canvas.drawBitmap(bitmapGameOver, currentX, currentY, paint);	
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}
	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用		
		new Thread()
		{
			public void run()
			{									
					//计算图片位置
					currentX=(int) (activity.screenWidth/2-bitmapGameOver.getWidth()/2);
					currentY=(int) (activity.screenHeight/2-bitmapGameOver.getHeight()/2);
					
					for(int i=255;i>-10;i=i-5)
					{//动态更改图片的透明度值并不断重绘	
						currentAlpha=i;
						if(currentAlpha<0)
						{
							currentAlpha=0;
						}
						SurfaceHolder myholder=GameOverView.this.getHolder();
						Canvas canvas = myholder.lockCanvas();//获取画布
						try{
							synchronized(myholder){
								onDraw(canvas);//绘制
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}
						finally{
							if(canvas != null){
								myholder.unlockCanvasAndPost(canvas);
							}
						}						
						try
						{
							if(i==255)
							{//若是新图片，多等待一会
								Thread.sleep(1000);
							}
							Thread.sleep(sleepSpan);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				
				//动画播放完毕后，去主菜单界面				
				 activity.sendMessage(1);
			}
		}.start();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//销毁时被调用

	}
}[]