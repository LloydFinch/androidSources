package com.bn.tag;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import static com.bn.tag.Constant.*;

public class SingleGoJianta {
	GameView gameView;
	Bitmap bitmap;//用于绘制的图片
	int clo;
	int row;
	

	public SingleGoJianta(GameView gameView,Bitmap bitmap)
	{
		this.gameView=gameView;
		this.bitmap=bitmap;	
	}
	public void drawSelf(Canvas canvas,Paint paint)
	{	
		float x1=SINGLE_RODER*clo;
		float y1=SINGLE_RODER*row-JIAN_TA_HEIGHT+SINGLE_RODER;
		canvas.drawBitmap(bitmap, x1,y1,paint);
	}
	public void getXY(float x,float y)
	{
		clo=(int)(x/SINGLE_RODER);
	    row=(int)(y/SINGLE_RODER);
	}

}
