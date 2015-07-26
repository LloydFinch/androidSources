package com.bn.box2d.bheap;

import org.jbox2d.dynamics.Body;
import android.graphics.Canvas;
import android.graphics.Paint;

//自定义刚体根类
public abstract class MyBody 
{
	Body body;//对应物理引擎中的刚体
	int color;//刚体的颜色
	public abstract void drawSelf(Canvas canvas,Paint paint);
}
