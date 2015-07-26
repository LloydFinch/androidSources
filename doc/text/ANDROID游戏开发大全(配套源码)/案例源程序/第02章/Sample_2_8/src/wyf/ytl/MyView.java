package wyf.ytl;
import android.content.Context;//������ص���
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
public class MyView extends View{//�̳���View
	Bitmap myBitmap;//ͼƬ������
	Paint paint;//���ʵ�����
	public MyView(Context context, AttributeSet attrs) {//������
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.initBitmap(); 
	}
	public void initBitmap(){ 
		paint = new Paint();//����һ������
		myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);//���ͼƬ��Դ
	}
	@Override
	protected void onDraw(Canvas canvas) {//��д�Ļ��Ʒ���
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		paint.setAntiAlias(true);//�򿪿����
		paint.setColor(Color.WHITE);//���û��ʵ���ɫ
		paint.setTextSize(15);
		canvas.drawBitmap(myBitmap, 10, 10, paint);//����ͼƬ
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
		canvas.drawText("ͼƬ�Ŀ��: "+myBitmap.getWidth(), 20, 380, paint);//�����ַ�����ͼƬ�Ŀ��
		canvas.drawText("ͼƬ�ĸ߶�: "+myBitmap.getHeight(), 20, 430, paint);//�����ַ�����ͼƬ�ĸ߶�
		paint.reset();
	}
}
