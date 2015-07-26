package com.bn.pb;

public class Constant {
	//***********************地图的行数和列数，最好是偶数**********************
	public static final int[][][] MAP=//1墙2地3目标4箱子5人
	{
//		{
//			{1,1,1,1,1,1,1,1,1,1},
//			{1,1,2,2,2,2,2,2,1,1},
//			{1,1,2,2,2,2,2,2,1,1},
//			{1,1,2,2,2,2,2,2,2,1},
//			{1,1,2,2,2,5,2,2,2,1},
//			{1,1,2,2,2,2,2,2,2,1},
//			{1,1,2,2,2,4,2,2,2,1},
//			{1,1,2,2,2,3,2,2,2,1},
//			{1,1,2,2,2,2,2,2,2,1},
//			{1,1,1,1,1,1,1,1,1,1}
//		}
		
		//第一关
		{
			{1,1,1,1,1,1,1,1,1,1,1,1},
			{1,2,2,2,2,2,2,3,2,2,2,1},
			{1,2,2,4,2,4,2,2,2,1,2,1},
			{1,1,1,2,2,1,2,2,2,1,2,1},
			{1,2,3,1,2,5,2,1,2,2,2,1},
			{1,4,2,1,2,1,2,2,3,1,2,1},
			{1,3,2,2,2,1,2,4,2,2,2,1},
			{1,2,2,4,2,1,2,2,1,1,2,1},
			{1,2,2,1,1,1,2,2,3,1,2,1},
			{1,1,1,1,1,1,1,1,1,1,1,1}
		},
		//第二关
		{
			{1,1,1,1,1,1,1,1,1,1,1,1},
			{1,2,1,2,2,2,2,2,2,3,2,1},
			{1,2,2,2,1,4,2,1,2,2,2,1},
			{1,2,4,3,1,2,2,2,1,2,2,1},
			{1,2,2,2,2,2,1,5,1,2,2,1},
			{1,2,1,2,2,3,2,1,2,4,2,1},
			{1,2,3,2,1,2,2,2,4,2,2,1},
			{1,2,4,2,1,1,2,2,1,1,2,1},
			{1,2,2,2,2,2,2,2,2,3,2,1},
			{1,1,1,1,1,1,1,1,1,1,1,1}
		}

		
	};

	public static int COUNT=0;//现在第几关
	
	public static int SCREEN_WIDTH;//屏幕的分辨率
	public static int SCREEN_HEIGHT;
	
	public static final float WALL_HEIGHT=1.0f;//墙的高度	
	public static final float UNIT_SIZE=1.0f;//地面每个格子的大小
	public static final float CUBE_SIZE=0.5f;//立方体木箱的半边长
	public static final float FLOOR_Y=0f;//地面的Y坐标
	
	public static final float TOUCH_SCALE_FACTOR=180.0f/480f;//角度缩放
	public static final float DISTANCE=14f;//摄像机与目标点的距离
	
	//****************************机器人信息Begin*************************************
	public static final float ANTENNA_RADIUS=0.1F/5;//天线半径
	public static final float ANTENNA_LENGTH=1.0f/5;//天线长度
	public static final float HEAD_RADIUS=1.5F/5;//头半径
	public static final float ARM_RADIUS=0.4F/5;//胳膊半径
	public static final float ARM_LENGTH=1.8F/5;//胳膊的长度
	public static final float BODY_RADIUS=1.5F/5;//身体半径
	public static final float BODY_LENGTH=2.5F/5;//身体长度
	public static final float LEG_RADIUS=0.5F/5;//腿半径
	public static final float LEG_LENGTH=1.4f/5;//腿长度
	public static final float HEAD_Y=3.0f/5;//头Y坐标
	public static final float BODY_Y=1.5f/5;//身体Y坐标
	public static final float ARM_Y=2.5f/5;//胳膊Y坐标
	public static final float LEG_Y=-0.5F/5;//腿Y坐标
	
	
//	public static final float ANTENNA_RADIUS=0.1F/2;//天线半径
//	public static final float ANTENNA_LENGTH=1.0f/2;//天线长度
//	public static final float HEAD_RADIUS=1.5F/2;//头半径
//	public static final float ARM_RADIUS=0.4F/2;//胳膊半径
//	public static final float ARM_LENGTH=1.8F/2;//胳膊的长度
//	public static final float BODY_RADIUS=1.5F/2;//身体半径
//	public static final float BODY_LENGTH=2.5F/2;//身体长度
//	public static final float LEG_RADIUS=0.5F/2;//腿半径
//	public static final float LEG_LENGTH=1.4f/2;//腿长度
//	public static final float HEAD_Y=3.0f/2;//头Y坐标
//	public static final float BODY_Y=1.5f/2;//身体Y坐标
//	public static final float ARM_Y=2.5f/2;//胳膊Y坐标
//	public static final float LEG_Y=-0.5F/2;//腿Y坐标
	
	
	
	
	
	//****************************机器人信息End*************************************
	
	//*************************虚拟按钮Begin*****************************
	//上虚拟按钮的有效触控范围
	public static final float upYMax=0.666f;
	public static final float upYMin=0.519f;
	public static final float upXMax=0.871f;
	public static final float upXMin=0.773f;
	
	//下虚拟按钮的有效触控范围
	public static final float downYMax=0.95f;
	public static final float downYMin=0.806f;
	public static final float downXMax=0.871f;
	public static final float downXMin=0.773f;
	
	//左虚拟按钮的有效触控范围
	public static final float leftYMax=0.806f;
	public static final float leftYMin=0.666f;
	public static final float leftXMax=0.772f;
	public static final float leftXMin=0.666f;
	
	//右虚拟按钮的有效触控范围
	public static final float rightYMax=0.806f;
	public static final float rightYMin=0.666f;
	public static final float rightXMax=0.977f;
	public static final float rightXMin=0.872f;
	
	//视角变换虚拟按钮
	public static final float viewXMax=0.877f;
	public static final float viewXMin=0.771f;
	public static final float viewYMax=0.194f;
	public static final float viewYMin=0.041f;
	
	//*************************虚拟按钮End*****************************
}
