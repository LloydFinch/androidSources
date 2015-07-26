package wyf.wpf;					//声明包语句
import android.content.Context;				//引入相关类
import android.content.res.Resources;		//引入相关类
import android.graphics.Bitmap;			//引入相关类
import android.graphics.BitmapFactory;		//引入相关类
import android.graphics.Canvas;			//引入相关类
import android.graphics.Color;		//引入相关类
import android.graphics.Matrix;		//引入相关类
import android.graphics.Paint;		//引入相关类
import android.view.SurfaceHolder;		//引入相关类
import android.view.SurfaceView;		//引入相关类
/*
 * 该类继承自View，负责欢迎界面的绘制
 */
public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback{
	DriftBall father;
	WelcomeThread wt;
	WelcomeDrawThread wdt;
	
	Bitmap [] bmpBackScreen;		//后台背景的动画帧
	Bitmap [] bmpStartOrQuit;		//开始和退出的两个大球图片
	Bitmap [] bmpSoundOption;		//声音选项的两个大球图片
	
	int status = -1;				//状态值，为0表示待命,1表示开始或退出按钮按下，2为显示加载中
	int selectedIndex = -1;			//被选中的命令，只能是开始或退出
	int backIndex;					//后台帧动画的索引
	Matrix m;						//Matrix对象，负责
	int [][] planetCoordinate={		//三个球的位置数组
			{60,120},
			{120,300},
			{180,220}
	};
	//构造器，初始化成员变量
	public WelcomeView(DriftBall father) {
		super(father);
		wt = new WelcomeThread(this);
		getHolder().addCallback(this);
		wdt = new WelcomeDrawThread(this,getHolder());
		initBitmap(father);					//初始化图片
		this.father = father;
		status = 0;
		m = new Matrix();
	}
	//方法：初始化图片资源
	public void initBitmap(Context context){
		Resources r = context.getResources();
		bmpBackScreen = new Bitmap[9];
		bmpBackScreen[0] = BitmapFactory.decodeResource(r, R.drawable.back_1);
		bmpBackScreen[1] = BitmapFactory.decodeResource(r, R.drawable.back_2);
		bmpBackScreen[2] = BitmapFactory.decodeResource(r, R.drawable.back_3);
		bmpBackScreen[3] = BitmapFactory.decodeResource(r, R.drawable.back_4);
		bmpBackScreen[4] = BitmapFactory.decodeResource(r, R.drawable.back_5);
		bmpBackScreen[5] = BitmapFactory.decodeResource(r, R.drawable.back_6);
		bmpBackScreen[6] = BitmapFactory.decodeResource(r, R.drawable.back_7);
		bmpBackScreen[7] = BitmapFactory.decodeResource(r, R.drawable.back_8);
		bmpBackScreen[8] = BitmapFactory.decodeResource(r, R.drawable.back_9);
		bmpStartOrQuit = new Bitmap[2];
		bmpStartOrQuit[0] = BitmapFactory.decodeResource(r, R.drawable.start);
		bmpStartOrQuit[1] = BitmapFactory.decodeResource(r, R.drawable.quit);
		bmpSoundOption = new Bitmap[2];
		bmpSoundOption[0] = BitmapFactory.decodeResource(r, R.drawable.sound_on);
		bmpSoundOption[1] = BitmapFactory.decodeResource(r, R.drawable.sound_off);
	}
	//屏幕绘制方法
	public void doDraw(Canvas canvas){		
		canvas.drawBitmap(bmpBackScreen[backIndex], 0, 0, null);//画背景
		switch(status){					//根据状态绘制不同的内容
		case 1:		//按钮按下状态
			Bitmap tmpBmp = bmpStartOrQuit[selectedIndex];
			//获得缩小后的图片
			bmpStartOrQuit[selectedIndex] = Bitmap.createBitmap(tmpBmp, 0, 0, tmpBmp.getWidth(), tmpBmp.getHeight(),m,true);
			if(tmpBmp.getWidth() <= 5){				//如果图片已经缩小到一定程度
				father.myHandler.sendEmptyMessage(selectedIndex);		//向Activity发Handler
				status = 2;								//设置状态为2，即显示加载中提示
			}
		case 0:		//正常待命状态,绘制正常的开始和退出按钮
			canvas.drawBitmap(bmpStartOrQuit[0], planetCoordinate[0][0], planetCoordinate[0][1], null);
			canvas.drawBitmap(bmpStartOrQuit[1], planetCoordinate[1][0], planetCoordinate[1][1], null);
			int soundIndex = (father.wantSound?1:0);
			canvas.drawBitmap(bmpSoundOption[soundIndex], planetCoordinate[2][0], planetCoordinate[2][1], null);
			break;
		case 2:		//显示加载中
			Paint p = new Paint();
			p.setTextSize(28);				//设置字体大小
			p.setColor(Color.RED);			//设置画笔颜色
			p.setAntiAlias(true);			//设置抗锯齿
			p.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("加载中...", 160, 240, p);
			break;
		}		
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {									//重写surfaceChanged方法		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {		//重写surfaceCreated方法
		if(!wdt.isAlive()){									//判断WelcomeDrawThread是否已启动
			wdt.start();									//启动WelcomeDrawThread
		}
		if(!wt.isAlive()){									//判断WelcomeThread是否已启动
			wt.start();										//启动WelcomeThread
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {	//重写surfaceDestroyed方法
		wt.flag = false;			//停止WelcomeThread的执行
		wdt.flag = false;			//停止WelcomeDrawThread的执行
	}
	
}