package com.bn.box2d.block;

import org.jbox2d.dynamics.Body;

import android.graphics.Canvas;
import android.graphics.Paint;

//�Զ���������
public abstract class MyBody 
{
	Body body;//��Ӧ���������еĸ���
	int color;//�������ɫ
	public abstract void drawSelf(Canvas canvas,Paint paint);
	
}
