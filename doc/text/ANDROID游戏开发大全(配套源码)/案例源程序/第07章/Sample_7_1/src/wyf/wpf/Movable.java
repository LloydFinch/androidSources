package wyf.wpf;		//声明包语句
import android.graphics.Bitmap;	//引入相关类
import android.graphics.Canvas;	//引入相关类
//代表可移动物体的Movable类
public class Movable{
	int startX=0;		//初始X坐标
	int startY=0;		//初始Y坐标
	int x;				//实时X坐标
	int y;				//实时Y坐标
	float startVX=0f;	//初始竖直方向的速度
	float startVY=0f;	//初始水平方向的速度
	float v_x=0f;		//实时水平方向速度
	float v_y=0f;		//实时竖直方向速度
	int r;				//可移动物体半径
	double timeX;			//X方向上的运动时间
	double timeY;			//Y方向上的运动时间
	Bitmap bitmap=null;	//可移动物体图片
	BallThread bt=null;	//负责小球移动时
	boolean bFall=false;//小球是否已经从木板上下落
	float impactFactor = 0.25f;	//小球撞地后速度的损失系数
	//构造器
	public Movable(int x,int y,int r,Bitmap bitmap){
		this.startX = x;		//初始化X坐标
		this.x = x;				//初始化X坐标
		this.startY = y;		//初始化Y坐标
		this.y = y;				//初始化Y坐标
		this.r = r;				//初始化
		this.bitmap = bitmap;	//初始化图片
		timeX=System.nanoTime();	//获取系统时间初始化
		this.v_x = BallView.V_MIN + (int)((BallView.V_MAX-BallView.V_MIN)*Math.random());		
		bt = new BallThread(this);//创建并启动BallThread
		bt.start();
	}
	//方法：绘制自己到屏幕上
	public void drawSelf(Canvas canvas){
		canvas.drawBitmap(this.bitmap,x, y, null);
	}
}