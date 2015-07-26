package wyf.wpf;//声明包
import android.content.Context;import android.graphics.Canvas;//引入相关包
import android.graphics.Color;import android.graphics.Paint;//引入相关包
import android.view.View;//引入相关包

public class MyContentView extends View{
	public MyContentView(Context context) {//构造器
		super(context);
	}
	@Override
	protected void onDraw(Canvas canvas) {//重写View类绘制时的回调方法
		Paint paint = new Paint();//创建画笔
		paint.setTextSize(18);//设置字体大小
		paint.setAntiAlias(true);//设置抗锯齿
		paint.setColor(Color.RED);//设置字体颜色
		canvas.drawText("这是通过继承和扩展View类来显示的。", 0, 50, paint);//绘制字体到屏幕
	}
}