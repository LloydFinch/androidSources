package wyf.ytl;
import android.content.Context;//引入相关的包
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
public class MyView extends View{
	public MyView(Context context, AttributeSet attrs) {//构造器
		super(context, attrs);
	}
	protected void onDraw(Canvas canvas) {//重写的绘制方法
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);//绘制黑色背景
		Paint paint = new Paint();//创建画笔
		paint.setColor(Color.WHITE);//设置画笔颜色为红色
		canvas.drawRect(10, 10, 110, 110, paint);//绘制矩形
		canvas.drawText("这是字符串", 10, 130, paint);//字符串，以字符串下面为基准
		RectF rf1 = new RectF(10, 130, 110, 230);//定义一个矩形
		canvas.drawArc(rf1, 0, 45, true, paint);//画弧，顺时针
		canvas.drawLine(150, 10, 250, 110, paint);//画线
		RectF rf2 = new RectF(150, 130, 250, 230);//定义一个矩形
		canvas.drawOval(rf2, paint);//画圆 
	}
}
