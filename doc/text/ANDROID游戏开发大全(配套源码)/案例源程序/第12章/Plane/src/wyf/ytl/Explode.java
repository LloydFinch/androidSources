package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * 
 * 该类为爆炸类
 * 在制定位置绘制爆炸
 * 其他线程通过调用nextFrame方法换帧
 *
 */

public class Explode {
	int x;//爆炸的x坐标
	int y;//爆炸的y坐标 
	
	Bitmap[] bitmaps;//所有爆炸的帧数组
	int k = 0;//当前帧
	
	Bitmap bitmap;//当前帧
	Paint paint;//画笔
	public Explode(int x, int y, GameView gameView){
		this.x = x;
		this.y = y;
		this.bitmaps = gameView.explodes;
		paint = new Paint();//初始化画笔
	}

	public void draw(Canvas canvas){//绘制方法
		canvas.drawBitmap(bitmap, x, y, paint);
	}
	public boolean nextFrame(){//换帧，成功返回true。否则返回false
		if(k < bitmaps.length){
			bitmap = bitmaps[k];
			k++;
			return true;
		}
		return false;
	}
}