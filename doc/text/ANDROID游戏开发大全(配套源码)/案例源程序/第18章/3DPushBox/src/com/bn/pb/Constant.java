package com.bn.pb;

public class Constant {
	//***********************��ͼ�������������������ż��**********************
	public static final int[][][] MAP=//1ǽ2��3Ŀ��4����5��
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
		
		//��һ��
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
		//�ڶ���
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

	public static int COUNT=0;//���ڵڼ���
	
	public static int SCREEN_WIDTH;//��Ļ�ķֱ���
	public static int SCREEN_HEIGHT;
	
	public static final float WALL_HEIGHT=1.0f;//ǽ�ĸ߶�	
	public static final float UNIT_SIZE=1.0f;//����ÿ�����ӵĴ�С
	public static final float CUBE_SIZE=0.5f;//������ľ��İ�߳�
	public static final float FLOOR_Y=0f;//�����Y����
	
	public static final float TOUCH_SCALE_FACTOR=180.0f/480f;//�Ƕ�����
	public static final float DISTANCE=14f;//�������Ŀ���ľ���
	
	//****************************��������ϢBegin*************************************
	public static final float ANTENNA_RADIUS=0.1F/5;//���߰뾶
	public static final float ANTENNA_LENGTH=1.0f/5;//���߳���
	public static final float HEAD_RADIUS=1.5F/5;//ͷ�뾶
	public static final float ARM_RADIUS=0.4F/5;//�첲�뾶
	public static final float ARM_LENGTH=1.8F/5;//�첲�ĳ���
	public static final float BODY_RADIUS=1.5F/5;//����뾶
	public static final float BODY_LENGTH=2.5F/5;//���峤��
	public static final float LEG_RADIUS=0.5F/5;//�Ȱ뾶
	public static final float LEG_LENGTH=1.4f/5;//�ȳ���
	public static final float HEAD_Y=3.0f/5;//ͷY����
	public static final float BODY_Y=1.5f/5;//����Y����
	public static final float ARM_Y=2.5f/5;//�첲Y����
	public static final float LEG_Y=-0.5F/5;//��Y����
	
	
//	public static final float ANTENNA_RADIUS=0.1F/2;//���߰뾶
//	public static final float ANTENNA_LENGTH=1.0f/2;//���߳���
//	public static final float HEAD_RADIUS=1.5F/2;//ͷ�뾶
//	public static final float ARM_RADIUS=0.4F/2;//�첲�뾶
//	public static final float ARM_LENGTH=1.8F/2;//�첲�ĳ���
//	public static final float BODY_RADIUS=1.5F/2;//����뾶
//	public static final float BODY_LENGTH=2.5F/2;//���峤��
//	public static final float LEG_RADIUS=0.5F/2;//�Ȱ뾶
//	public static final float LEG_LENGTH=1.4f/2;//�ȳ���
//	public static final float HEAD_Y=3.0f/2;//ͷY����
//	public static final float BODY_Y=1.5f/2;//����Y����
//	public static final float ARM_Y=2.5f/2;//�첲Y����
//	public static final float LEG_Y=-0.5F/2;//��Y����
	
	
	
	
	
	//****************************��������ϢEnd*************************************
	
	//*************************���ⰴťBegin*****************************
	//�����ⰴť����Ч���ط�Χ
	public static final float upYMax=0.666f;
	public static final float upYMin=0.519f;
	public static final float upXMax=0.871f;
	public static final float upXMin=0.773f;
	
	//�����ⰴť����Ч���ط�Χ
	public static final float downYMax=0.95f;
	public static final float downYMin=0.806f;
	public static final float downXMax=0.871f;
	public static final float downXMin=0.773f;
	
	//�����ⰴť����Ч���ط�Χ
	public static final float leftYMax=0.806f;
	public static final float leftYMin=0.666f;
	public static final float leftXMax=0.772f;
	public static final float leftXMin=0.666f;
	
	//�����ⰴť����Ч���ط�Χ
	public static final float rightYMax=0.806f;
	public static final float rightYMin=0.666f;
	public static final float rightXMax=0.977f;
	public static final float rightXMin=0.872f;
	
	//�ӽǱ任���ⰴť
	public static final float viewXMax=0.877f;
	public static final float viewXMin=0.771f;
	public static final float viewYMax=0.194f;
	public static final float viewYMin=0.041f;
	
	//*************************���ⰴťEnd*****************************
}
