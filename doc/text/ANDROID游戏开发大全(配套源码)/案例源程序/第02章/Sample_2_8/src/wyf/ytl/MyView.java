package wyf.ytl;
import android.content.Context;//引入相关的类
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
public class MyView extends View{//继承自View
	Bitmap myBitmap;//图片的引用
	Paint paint;//画笔的引用
	public MyView(Context context, AttributeSet attrs) {//构造器
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.initBitmap(); 
	}
	public void initBitmap(){ 
		paint = new Paint();//创建一个画笔
		myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);//获得图片资源
	}
	@Override
	protected void onDraw(Canvas canvas) {//重写的绘制方法
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setAntiAlias(true);//打开抗锯齿
		paint.setColor(Color.WHITE);//设置画笔的颜色
		paint.setTextSize(15);
		canvas.drawBitmap(myBitmap, 10, 10, paint);//绘制图片
		canvas.save();
		Matrix m1=new Matrix();
		m1.setTranslate(500, 10);
		Matrix m2=new Matrix();
		m2.setRotate(15);
		Matrix m3=new Matrix();
		m3.setConcat(m1, m2);
		m1.setScale(0.8f, 0.8f);
		m2.setConcat(m3, m1);
		canvas.drawBitmap(myBitmap, m2, paint);
		canvas.restore();
		canvas.save();
		paint.setAlpha(180);
		m1.setTranslate(200,100);
		m2.setScale(1.3f, 1.3f);
		m3.setConcat(m1, m2);
		canvas.drawBitmap(myBitmap, m3,  paint);
		paint.reset();
		canvas.restore();
		paint.setTextSize(40);
		paint.setColor(0xffFFFFFF);
		canvas.drawText("图片的宽度: "+myBitmap.getWidth(), 20, 380, paint);//绘制字符串，图片的宽度
		canvas.drawText("图片的高度: "+myBitmap.getHeight(), 20, 430, paint);//绘制字符串，图片的高度
		paint.reset();
	}
}
