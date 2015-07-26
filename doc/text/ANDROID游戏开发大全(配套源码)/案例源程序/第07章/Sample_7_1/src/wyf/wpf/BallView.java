package wyf.wpf;		//声明包语句
import java.util.ArrayList;		//引入相关类
import java.util.Random;		//引入相关类
import android.content.Context;	//引入相关类
import android.content.res.Resources;	//引入相关类
import android.graphics.Bitmap;			//引入相关类
import android.graphics.BitmapFactory;	//引入相关类
import android.graphics.Canvas;			//引入相关类
import android.graphics.Color;			//引入相关类
import android.graphics.Paint;			//引入相关类
import android.view.SurfaceHolder;		//引入相关类
import android.view.SurfaceView;		//引入相关类
//继承自SurfaceView的子类	
public class BallView extends SurfaceView implements SurfaceHolder.Callback{
	public static final int V_MAX=35;	//小球水平速度的最大值
	public static final int V_MIN=15;	//小球竖直速度的最大值
	public static final int WOOD_EDGE = 60;	//木板的右边沿的x坐标
	public static final int GROUND_LING = 450;//游戏中代表地面y坐标，小球下落到此会弹起 
	public static final int UP_ZERO = 30;	//小球在上升过程中，如果速度大小小于该值就算为0
	public static final int DOWN_ZERO = 60;	//小球在撞击地面后，如果速度大小小于该值就算为0
	Bitmap [] bitmapArray = new Bitmap[6];	//各种颜色形状的小球图片引用
	Bitmap bmpBack;		//背景图片对象
	Bitmap bmpWood;		//木板图片对象
	String fps="FPS:N/A";		//用于显示帧速率的字符串，调试使用	
	int ballNumber =8;	//小球数目
	ArrayList<Movable> alMovable = new ArrayList<Movable>();	//小球对象数组
	DrawThread dt;	//后台屏幕绘制线程

	public BallView(Context activity){
		super(activity);				//调用父类构造器		
		getHolder().addCallback(this);
		initBitmaps(getResources());	//初始化图片		
		initMovables();					//初始化小球		
		dt = new DrawThread(this,getHolder());		//初始化重绘线程		
	}
	//方法：初始化图片
	public void initBitmaps(Resources r){
		bitmapArray[0] = BitmapFactory.decodeResource(r, R.drawable.ball_red_small);	//红色较小球
		bitmapArray[1] = BitmapFactory.decodeResource(r, R.drawable.ball_purple_small);	//紫色较小球
		bitmapArray[2] = BitmapFactory.decodeResource(r, R.drawable.ball_green_small);	//绿色较小球		
		bitmapArray[3] = BitmapFactory.decodeResource(r, R.drawable.ball_red);			//红色较大球
		bitmapArray[4] = BitmapFactory.decodeResource(r, R.drawable.ball_purple);		//紫色较大球
		bitmapArray[5] = BitmapFactory.decodeResource(r, R.drawable.ball_green);		//绿色较大球
		bmpBack = BitmapFactory.decodeResource(r, R.drawable.back);						//背景砖墙
		bmpWood = BitmapFactory.decodeResource(r, R.drawable.wood);						//木板
	}
	//方法：初始化小球
	public void initMovables(){
		Random r = new Random();	//创建一个Random对象
		for(int i=0;i<ballNumber;i++){
			int index = r.nextInt(32);		//产生随机数
			Bitmap tempBitmap=null;			//声明一个Bitmap图片引用
			if(i<ballNumber/2){		
				tempBitmap = bitmapArray[3+index%3];//如果是初始化前一半球，就从大球中随机找一个
			}
			else{
				tempBitmap = bitmapArray[index%3];//如果是初始化后一半球，就从小球中随机找一个
			}
			Movable m = new Movable(0,70-tempBitmap.getHeight(),tempBitmap.getWidth()/2,tempBitmap);	//创建Movable对象
			alMovable.add(m);	//将新建的Movable对象添加到ArrayList列表中
		}
	}
	//方法：绘制程序中所需要的图片等信息
	public void doDraw(Canvas canvas) {		
		canvas.drawBitmap(bmpBack, 0, 0, null);	//绘制背景图片
		canvas.drawBitmap(bmpWood, 0, 60, null);//绘制木板图片
		for(Movable m:alMovable){	//遍历Movable列表，绘制每个Movable对象
			m.drawSelf(canvas);
		}
		Paint p = new Paint();	//创建画笔对象
		p.setColor(Color.BLUE);	//为画笔设置颜色
		p.setTextSize(18);		//为画笔设置字体大小
		p.setAntiAlias(true);	//设置抗锯齿 
		canvas.drawText(fps, 30, 30, p);	//画出帧速率字符串
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {//重写surfaceChanged方法	
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//从写surfaceCreated方法
		if(!dt.isAlive()){	//如果DrawThread没有启动，就启动这个线程
			dt.start();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//重写surfaceDestroyed方法
		dt.flag = false;	//停止线程的执行
		dt = null;			//将dt指向的对象声明为垃圾
	}	
}