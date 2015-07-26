package com.bn.chess;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ViewConstant {
	public static float sXtart=0;//���̵���ʼ����
	public static float sYtart=0;
	
	public static boolean yingJMflag;//Ӯ�����־
	public static boolean shuJMflag;//������־
	
	public static float height;
     public static float width; 
     public static int huiqiBS=2;
	public static boolean isnoPlaySound=true;//�Ƿ񲥷�����
	public static boolean isComputerPlayChess=false;//�Ƿ�Ϊ��������,��ʼΪ����
	public static boolean isHeqi=false;//�Ƿ�Ϊ����
	public static boolean isnoStart=false;//�Ƿ�Ϊ��ʼ������ͣ
	public static boolean isnoTCNDxuanz;//�Ƿ�Ϊ�������Ѷ�ѡ��
	public static int nanduXS=1;//�Ѷ�ϵ��
	
	public static int thinkDeeplyTime=1;
	
	public static int zTime=900000;
	public static int endTime=zTime;
	public static float xZoom=1F;//���ű���
	public static float yZoom=1F;
	
	public static float xSpan=48.0f*xZoom;
	public static float ySpan=48.0f*yZoom;
	
	public static float scoreWidth = 7*xZoom*4f;//ʱ�����ּ��
	public static float sXtartCk;
	public static float sYtartCk;//�ڶ����ڵ���ʼ����
	
	public static float windowWidth=200*xZoom;//���ڵĴ�С
	public static float windowHeight=400*xZoom;
	
	public static float windowXstartLeft=sXtart+(5*xSpan-windowWidth)/2*xZoom;//С���ڵ���ʼ����
	public static float windowXstartRight=sYtart+5*xSpan+(5*xSpan-windowWidth)/2*xZoom;
	public static float windowYstart=sYtart+ySpan*4*xZoom;
	
	
	public static float chessR=30*xZoom;//���Ӱ뾶
	public static float fblRatio=0.6f*xZoom;//�������ű���
	
	public static Bitmap scaleToFit(Bitmap bm,float fblRatio)//����ͼƬ�ķ���
    {
    	int width = bm.getWidth(); //ͼƬ���
    	int height = bm.getHeight();//ͼƬ�߶�
    	Matrix matrix = new Matrix(); 
    	matrix.postScale((float)fblRatio, (float)fblRatio);//ͼƬ�ȱ�����СΪԭ����fblRatio��
    	Bitmap bmResult = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);//����λͼ        	
    	return bmResult;
    }
	public static void initChessViewFinal()
	{
		xSpan=48.0f*xZoom;
		ySpan=48.0f*yZoom;
		
		scoreWidth = 7*xZoom*4f;//ʱ�����ּ��
		
		
		windowWidth=200*xZoom;//���ڵĴ�С
		windowHeight=250*xZoom;
		
		windowXstartLeft=sXtart+(5*xSpan-windowWidth)/2*xZoom;//С���ڵ���ʼ����
		windowXstartRight=sYtart+5*xSpan+(5*xSpan-windowWidth)/2*xZoom;
		windowYstart=sYtart+ySpan*1*xZoom;

		chessR=30*xZoom;//���Ӱ뾶
		fblRatio=0.6f*xZoom;//�������ű���
	}
}
