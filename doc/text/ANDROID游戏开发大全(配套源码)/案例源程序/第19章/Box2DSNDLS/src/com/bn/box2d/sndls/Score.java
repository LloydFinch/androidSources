package com.bn.box2d.sndls;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import static com.bn.box2d.sndls.Constant.*;

//µÃ·ÖÀà
public class Score
{
   Bitmap[] bmNum;
   char[] score;
   float x;
   float y;
   int count=0;
   final float YZ=25;
   public Score(Bitmap[] bmNum,int score,float x,float y)
   {
	   this.bmNum=bmNum;
	   this.score=(score+"").toCharArray();
	   this.x=x;
	   this.y=y;
   }
   public void drawSelf(Canvas canvas,Paint paint)
   {
	   if(count>YZ)
	   {
		   return;
	   }
	   else    
	   {
		   count++;
	   }
	   int alpha=(int) (255-255.0f*(count/YZ));
	   if(alpha<=0)
	   {
		   alpha=0;
	   }
	   paint.setAlpha(alpha);
	   float width=score.length*bmNum[0].getWidth();
	   float realX=x-width/2;
	   float realY=y-bmNum[0].getHeight()/2;
	   for(int i=0;i<score.length;i++)
	   {
		   int index=score[i]-'0';
		   canvas.drawBitmap(bmNum[index], xOffset+realX+i*bmNum[0].getWidth(),yOffset+realY, paint);
	   }
	   paint.reset();
   }
}