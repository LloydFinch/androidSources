package com.bn.pb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * 
 * 主菜单上的按钮类
 *
 */
public class MainMenuButton {
	MyActivity activity;
	int x;//图片的左上点x坐标
	int y;//图片的左上点y坐标
	int width;//虚拟按钮的宽
	int height;//虚拟按钮的高
	Bitmap onBitmap;//按下图片
	Bitmap offBitmap;//抬起图片
	boolean isOn=true;//按下状态为false
	public MainMenuButton(MyActivity activity,Bitmap onBitmap,Bitmap offBitmap,int x,int y)
	{
		this.activity=activity;
		
		this.onBitmap=onBitmap;
		this.offBitmap=offBitmap;
		this.x=x;
		this.y=y;
		this.width=offBitmap.getWidth();
		this.height=offBitmap.getHeight();
	}
	public void drawSelf(Canvas canvas,Paint paint)//绘制虚拟按钮
	{
		if(isOn)
		{
			canvas.drawBitmap(onBitmap, x, y, paint);
		}
		else
		{
			canvas.drawBitmap(offBitmap, x, y, paint);
		}
	}
	public void setswitch()
	{
		isOn=!isOn;
	}
	public boolean isOnflag()//返回isOn的状态
	{
		return isOn;
	}
	
	public void switchOn()//设置显示的图片
	{
		isOn=true;
	}
	public void switchOff()
	{
		isOn=false;
	}
	//一个点是否在矩形内（包括边界）
	public boolean isPointInRect(float pointx,float pointy)
	{
		if(//如果在矩形内，返回true
				pointx>=x&&pointx<=x+width&&
				pointy>=y&&pointy<=y+height
		  )
		  {
			  return true;//如果在，返回true
		  }
		return false;//否则返回false
	}	
}
