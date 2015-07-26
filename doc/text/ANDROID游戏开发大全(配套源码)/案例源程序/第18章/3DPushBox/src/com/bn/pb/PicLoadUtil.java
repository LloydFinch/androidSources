package com.bn.pb;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class PicLoadUtil 
{
   //����ͼƬ�ķ���
   public static Bitmap scaleToFit(Bitmap bm,float targetWidth,float targetHeight)//����ͼƬ�ķ���
   {
   	float width = bm.getWidth(); //ͼƬ���
   	float height = bm.getHeight();//ͼƬ�߶�	
   	
   	Matrix m1 = new Matrix(); 
   	m1.postScale(targetWidth/width, targetHeight/height);//����ָ����Ŀ�����򣨲���Ŀ��ͼƬ��   	
   	Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, (int)width, (int)height, m1, true);//����λͼ        	
   	return bmResult;
   }
}