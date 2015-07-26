package com.bn.box2d.bheap;
public class Constant 
{
	public static final float RATE = 10;//屏幕到现实世界的比例 10px：1m;
	public static final boolean DRAW_THREAD_FLAG=true;//绘制线程工作标志位
	public static final float TIME_STEP = 2.0f/60.0f;//模拟的的频率   
	public static final int ITERA = 10;//迭代越大，模拟约精确，但性能越低   
	public static int SCREEN_WIDTH;  //屏幕宽度
	public static int SCREEN_HEIGHT; //屏幕高度	
}