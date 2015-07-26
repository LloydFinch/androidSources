package com.bn.pb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
/**
 * 
 * ���˵��ϵİ�ť��
 *
 */
public class MainMenuButton {
	MyActivity activity;
	int x;//ͼƬ�����ϵ�x����
	int y;//ͼƬ�����ϵ�y����
	int width;//���ⰴť�Ŀ�
	int height;//���ⰴť�ĸ�
	Bitmap onBitmap;//����ͼƬ
	Bitmap offBitmap;//̧��ͼƬ
	boolean isOn=true;//����״̬Ϊfalse
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
	public void drawSelf(Canvas canvas,Paint paint)//�������ⰴť
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
	public boolean isOnflag()//����isOn��״̬
	{
		return isOn;
	}
	
	public void switchOn()//������ʾ��ͼƬ
	{
		isOn=true;
	}
	public void switchOff()
	{
		isOn=false;
	}
	//һ�����Ƿ��ھ����ڣ������߽磩
	public boolean isPointInRect(float pointx,float pointy)
	{
		if(//����ھ����ڣ�����true
				pointx>=x&&pointx<=x+width&&
				pointy>=y&&pointy<=y+height
		  )
		  {
			  return true;//����ڣ�����true
		  }
		return false;//���򷵻�false
	}	
}
