package wyf.wpf;//������
import android.content.Context;import android.graphics.Canvas;//������ذ�
import android.graphics.Color;import android.graphics.Paint;//������ذ�
import android.view.View;//������ذ�

public class MyContentView extends View{
	public MyContentView(Context context) {//������
		super(context);
	}
	@Override
	protected void onDraw(Canvas canvas) {//��дView�����ʱ�Ļص�����
		Paint paint = new Paint();//��������
		paint.setTextSize(18);//���������С
		paint.setAntiAlias(true);//���ÿ����
		paint.setColor(Color.RED);//����������ɫ
		canvas.drawText("����ͨ���̳к���չView������ʾ�ġ�", 0, 50, paint);//�������嵽��Ļ
	}
}