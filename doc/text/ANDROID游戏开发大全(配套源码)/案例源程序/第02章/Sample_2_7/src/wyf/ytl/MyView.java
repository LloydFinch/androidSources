package wyf.ytl;
import android.content.Context;//������صİ�
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
public class MyView extends View{
	public MyView(Context context, AttributeSet attrs) {//������
		super(context, attrs);
	}
	protected void onDraw(Canvas canvas) {//��д�Ļ��Ʒ���
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);//���ƺ�ɫ����
		Paint paint = new Paint();//��������
		paint.setColor(Color.WHITE);//���û�����ɫΪ��ɫ
		canvas.drawRect(10, 10, 110, 110, paint);//���ƾ���
		canvas.drawText("�����ַ���", 10, 130, paint);//�ַ��������ַ�������Ϊ��׼
		RectF rf1 = new RectF(10, 130, 110, 230);//����һ������
		canvas.drawArc(rf1, 0, 45, true, paint);//������˳ʱ��
		canvas.drawLine(150, 10, 250, 110, paint);//����
		RectF rf2 = new RectF(150, 130, 250, 230);//����һ������
		canvas.drawOval(rf2, paint);//��Բ 
	}
}
