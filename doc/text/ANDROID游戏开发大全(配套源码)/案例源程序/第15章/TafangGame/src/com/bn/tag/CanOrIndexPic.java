package com.bn.tag;



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import static com.bn.tag.Constant.*;

public class CanOrIndexPic {
	GameView gameView;
	Bitmap bitmap;//用于绘制的图片
	//Bitmap[]
	int clo;
	int row;
	static Boolean ifdraw;
	

	public CanOrIndexPic(GameView gameView,Bitmap bitmap)
	{
		this.gameView=gameView;
		this.bitmap=bitmap;	
//		this.clo=x;
//		this.row=y;
	}
	public void drawSelf(Canvas canvas,Paint paint)
	{	
		float x1=SINGLE_RODER*clo+SINGLE_RODER/2-R_LENGTH/2;
		float y1=SINGLE_RODER*row+SINGLE_RODER/2-R_LENGTH/2;
		canvas.drawBitmap(bitmap, x1,y1,paint);
	}
	public void getXY(float x,float y)
	{
		clo=(int)(x/SINGLE_RODER);
	    row=(int)(y/SINGLE_RODER);
	}

}
