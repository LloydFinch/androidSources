package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * 
 * ����Ϊ�ӵ��ķ�װ��
 * ��¼���ӵ��������ز���
 * ���ͨ������move�����ƶ��ӵ�
 *
 */
public class Bullet {
	int x;//�ӵ�������
	int y;
	int dir;//�ӵ��ķ���,0��ֹ��1��,2���ϣ�3�ң�4���£�5�£�6���£�7��8����
	int type;//�ӵ�������
	Bitmap bitmap;//��ǰ�ӵ���ͼƬ
	GameView gameView;//gameView������
	private int moveSpan = 10;//�ƶ�������
	public Bullet(int x, int y, int type, int dir,GameView gameView){
		this.gameView = gameView;
		this.x = x;
		this.y = y;
		this.type = type;
		this.dir = dir;
		this.initBitmap();
	}
	public void initBitmap(){
		if(type == 1){//������Ϊ1ʱ
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet1);
		}
		else if(type == 2){//����Ϊ2ʱ
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet2);
		}
		else if(type == 3){//����Ϊ3ʱ
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet3);
		}
		else if(type == 4){//����Ϊ4ʱ
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet4);
		}
		else if(type == 5){//����Ϊ5ʱ
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet5);
		}
	}
	public void draw(Canvas canvas){//���Ƶķ���
		canvas.drawBitmap(bitmap, x, y,new Paint());
	}
	public void move(){//�ƶ��ķ���
		if(dir == ConstantUtil.DIR_RIGHT){//�����ƶ�
			this.x = this.x + moveSpan;
		}
		else if(dir == ConstantUtil.DIR_LEFT){//�����ƶ�
			this.x = this.x - moveSpan;
		}
		else if(dir == ConstantUtil.DIR_LEFT_DOWN){//�������ƶ�
			this.x = this.x - moveSpan;
			this.y = this.y + moveSpan;
		}
		else if(dir == ConstantUtil.DIR_LEFT_UP){//�������ƶ�
			this.x = this.x - moveSpan;
			this.y = this.y - moveSpan;
		}
		else if(dir == ConstantUtil.DIR_RIGHT_UP){//�����ƶ�
			this.x = this.x + moveSpan;
			this.y = this.y - moveSpan;
		}
		else if(dir == ConstantUtil.DIR_RIGHT_DOWN){//�����ƶ�
			this.x = this.x + moveSpan;
			this.y = this.y + moveSpan;
		}
	}
}