package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * 
 * ����Ϊ��ը��
 * ���ƶ�λ�û��Ʊ�ը
 * �����߳�ͨ������nextFrame������֡
 *
 */

public class Explode {
	int x;//��ը��x����
	int y;//��ը��y���� 
	
	Bitmap[] bitmaps;//���б�ը��֡����
	int k = 0;//��ǰ֡
	
	Bitmap bitmap;//��ǰ֡
	Paint paint;//����
	public Explode(int x, int y, GameView gameView){
		this.x = x;
		this.y = y;
		this.bitmaps = gameView.explodes;
		paint = new Paint();//��ʼ������
	}

	public void draw(Canvas canvas){//���Ʒ���
		canvas.drawBitmap(bitmap, x, y, paint);
	}
	public boolean nextFrame(){//��֡���ɹ�����true�����򷵻�false
		if(k < bitmaps.length){
			bitmap = bitmaps[k];
			k++;
			return true;
		}
		return false;
	}
}