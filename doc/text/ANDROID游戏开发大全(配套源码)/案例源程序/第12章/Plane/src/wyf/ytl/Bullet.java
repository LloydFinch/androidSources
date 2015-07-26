package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * 
 * 该类为子弹的封装类
 * 记录了子弹自身的相关参数
 * 外界通过调用move方法移动子弹
 *
 */
public class Bullet {
	int x;//子弹的坐标
	int y;
	int dir;//子弹的方向,0静止，1上,2右上，3右，4右下，5下，6左下，7左，8左上
	int type;//子弹的类型
	Bitmap bitmap;//当前子弹的图片
	GameView gameView;//gameView的引用
	private int moveSpan = 10;//移动的像素
	public Bullet(int x, int y, int type, int dir,GameView gameView){
		this.gameView = gameView;
		this.x = x;
		this.y = y;
		this.type = type;
		this.dir = dir;
		this.initBitmap();
	}
	public void initBitmap(){
		if(type == 1){//当类型为1时
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet1);
		}
		else if(type == 2){//类型为2时
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet2);
		}
		else if(type == 3){//类型为3时
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet3);
		}
		else if(type == 4){//类型为4时
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet4);
		}
		else if(type == 5){//类型为5时
			bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.bullet5);
		}
	}
	public void draw(Canvas canvas){//绘制的方法
		canvas.drawBitmap(bitmap, x, y,new Paint());
	}
	public void move(){//移动的方法
		if(dir == ConstantUtil.DIR_RIGHT){//向右移动
			this.x = this.x + moveSpan;
		}
		else if(dir == ConstantUtil.DIR_LEFT){//向左移动
			this.x = this.x - moveSpan;
		}
		else if(dir == ConstantUtil.DIR_LEFT_DOWN){//向左下移动
			this.x = this.x - moveSpan;
			this.y = this.y + moveSpan;
		}
		else if(dir == ConstantUtil.DIR_LEFT_UP){//向左上移动
			this.x = this.x - moveSpan;
			this.y = this.y - moveSpan;
		}
		else if(dir == ConstantUtil.DIR_RIGHT_UP){//右上移动
			this.x = this.x + moveSpan;
			this.y = this.y - moveSpan;
		}
		else if(dir == ConstantUtil.DIR_RIGHT_DOWN){//右下移动
			this.x = this.x + moveSpan;
			this.y = this.y + moveSpan;
		}
	}
}