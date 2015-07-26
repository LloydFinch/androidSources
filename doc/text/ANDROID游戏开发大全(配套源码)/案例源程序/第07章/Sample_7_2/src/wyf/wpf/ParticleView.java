package wyf.wpf;			//声明包语句
import java.util.ArrayList;		//引入相关类
import android.content.Context;		//引入相关类
import android.graphics.Canvas;	//引入相关类
import android.graphics.Color;	//引入相关类
import android.graphics.Paint;	//引入相关类
import android.graphics.RectF;	//引入相关类
import android.view.SurfaceHolder;	//引入相关类
import android.view.SurfaceView;	//引入相关类
//继承自SurfaceView并实现SurfaceHolder.Callback接口的类
public class ParticleView extends SurfaceView implements SurfaceHolder.Callback{
	public static final int DIE_OUT_LINE = 420;//粒子的Y坐标超过该值会从粒子集合移除
	DrawThread dt;		//后台刷新屏幕线程
	ParticleSet ps;		//ParticleSet对象引用
	ParticleThread pt;	//ParticleThread对象引用
	String fps = "FPS:N/A";		//声明帧速率字符串
	//构造器，初始化主要成员变量
	public ParticleView(Context context) {
		super(context);	//调用父类构造器
		this.getHolder().addCallback(this);	//添加Callback接口
		dt = new DrawThread(this, getHolder());	//创建DrawThread对象
		ps = new ParticleSet();					//创建ParticleSet对象
		pt = new ParticleThread(this);			//创建ParticleThread对象
	}	
	//方法：绘制屏幕
	public void doDraw(Canvas canvas){
		canvas.drawColor(Color.BLACK);		//清屏
		ArrayList<Particle> particleSet = ps.particleSet;	//获得ParticleSet对象中的粒子集合对象
		Paint paint = new Paint();	//创建画笔对象
		for(int i=0;i<particleSet.size();i++){		//遍历粒子集合，绘制每个粒子
			Particle p = particleSet.get(i);
			paint.setColor(p.color);		//设置画笔颜色为粒子颜色
			int tempX = p.x;		//获得粒子X坐标
			int tempY = p.y;		//获得粒子Y坐标
			int tempRadius = p.r;	//获得粒子半径
			RectF oval = new RectF(tempX, tempY, tempX+2*tempRadius, tempY+2*tempRadius);
			canvas.drawOval(oval, paint);		//绘制椭圆粒子
		}
		paint.setColor(Color.WHITE);	//设置画笔颜色
		paint.setTextSize(18);			//设置文字大小
		paint.setAntiAlias(true);		//设置抗锯齿
		canvas.drawText(fps, 15, 15, paint);//画出帧速率字符串
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
		if(!pt.isAlive()){	//如果ParticleThread没有启动，就启动这个线程
			pt.start();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//重写surfaceDestroyed方法
		dt.flag = false;	//停止线程的执行
		dt = null;			//将dt指向的对象声明为垃圾
	}	
}