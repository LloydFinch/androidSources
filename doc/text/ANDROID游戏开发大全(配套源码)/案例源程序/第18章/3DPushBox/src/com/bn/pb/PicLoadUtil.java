package com.bn.pb;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class PicLoadUtil 
{
   //缩放图片的方法
   public static Bitmap scaleToFit(Bitmap bm,float targetWidth,float targetHeight)//缩放图片的方法
   {
   	float width = bm.getWidth(); //图片宽度
   	float height = bm.getHeight();//图片高度	
   	
   	Matrix m1 = new Matrix(); 
   	m1.postScale(targetWidth/width, targetHeight/height);//这里指的是目标区域（不是目标图片）   	
   	Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, (int)width, (int)height, m1, true);//声明位图        	
   	return bmResult;
   }
}