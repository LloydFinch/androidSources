package com.bn.box2d.sndls;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class Constant 
{
	public static float RATE = 10;//屏幕到现实世界的比例 10px：1m;   
	public static boolean DRAW_THREAD_FLAG=true;//绘制线程工作标志位
	public static boolean START_SCORE=false;//开始记分标志位 
	
	public static final float TIME_STEP = 2f/60.0f;//模拟的的频率   
	public static final int ITERA = 10;//迭代越大，模拟约精确，但性能越低   
	
	public static int SCREEN_WIDTH;  //屏幕宽度
	public static int SCREEN_HEIGHT; //屏幕高度	
	   
	public static float SCREEN_WIDTH_STANDARD=854;  //屏幕标准宽度	
	public static float SCREEN_HEIGHT_STANDARD=480;  //屏幕标准高度	
	
	public static float xMainRatio;//X缩放比例	
	public static float yMainRatio;//Y缩放比例
	
	//MainMenuDrawThread与TJThread线程的标志位
	public static boolean MAIN_DRAW_THREAD_FLAG=false;
	public static boolean TJ_CONTROL_FLAG=false;
	
	//绘制偏移量
	public static float xOffset=0;
	public static float yOffset=0;
	
	//场景宽度
	public static float CJ_WIDTH=2000;
	
	//得分
	public static int SCORE=0;
	
	//预加载的图片ID数组列表
	public static int[] PIC_ID=
	{
		R.drawable.ground,//0-地面
		R.drawable.ls_zy,//1-睁眼睛的老鼠
		R.drawable.xm,//2-小猫   
		R.drawable.wood_hen,//3-横木头   
		R.drawable.wood_hen_1,//4-横木头
		R.drawable.wood_hen_2,//5-横木头
		R.drawable.stone_shu,//6-竖石头
		R.drawable.ice_hen,//7-横冰块
		R.drawable.ice_hen_1,//8-横冰块
		R.drawable.ice_hen_2,//9-横冰块
		R.drawable.ice_hen_3,//10-横冰块
		R.drawable.ice_shu,//11-竖冰块
		R.drawable.ice_shu_1,//12-竖冰块 
		R.drawable.ice_shu_2,//13-竖冰块
		R.drawable.ice_shu_3,//14-竖冰块
		R.drawable.bottom_left_ntz,//15-左侧灰泥台子
		R.drawable.bottom_right_ntz,//16-左侧灰泥台子
		R.drawable.bg,//17-场景总背景
		R.drawable.stone_hen,//18-横石头
		R.drawable.wood_shu,//19-竖直木头
		R.drawable.wood_shu_1,//20-竖直木头
		R.drawable.wood_shu_2,//21-竖直木头
		R.drawable.dg,//22-弹弓
		R.drawable.ls_by,//23-闭眼老鼠
		R.drawable.pj,//24-皮筋
		R.drawable.c_xd,//25-小草
		R.drawable.c_dd,//26-长势强壮的草
	};
	//预加载白色数字图片ID数组列表
	public static int[] NUM_BS_ID=    
	{
		R.drawable.num_bs_0,//白色数字0
		R.drawable.num_bs_1,//白色数字1  
		R.drawable.num_bs_2,//白色数字2
		R.drawable.num_bs_3,//白色数字3
		R.drawable.num_bs_4,//白色数字4
		R.drawable.num_bs_5,//白色数字5
		R.drawable.num_bs_6,//白色数字6
		R.drawable.num_bs_7,//白色数字7
		R.drawable.num_bs_8,//白色数字8
		R.drawable.num_bs_9//白色数字9
	};
	
	//加载黄色数字图片ID数组列表
	public static int[] NUM_HS_ID=
	{
		R.drawable.num_hs_0,//黄色数字0
		R.drawable.num_hs_1,//黄色数字1
		R.drawable.num_hs_2,//黄色数字2
		R.drawable.num_hs_3,//黄色数字3
		R.drawable.num_hs_4,//黄色数字4
		R.drawable.num_hs_5,//黄色数字5
		R.drawable.num_hs_6,//黄色数字6
		R.drawable.num_hs_7,//黄色数字7
		R.drawable.num_hs_8,//黄色数字8
		R.drawable.num_hs_9//黄色数字9
	};
	
	//加载紫色数字图片ID数组列表
	public static int[] NUM_ZS_ID=
	{
		R.drawable.num_zs_0,//紫色数字0
		R.drawable.num_zs_1,//紫色数字1
		R.drawable.num_zs_2,//紫色数字2
		R.drawable.num_zs_3,//紫色数字3
		R.drawable.num_zs_4,//紫色数字4
		R.drawable.num_zs_5,//紫色数字5
		R.drawable.num_zs_6,//紫色数字6
		R.drawable.num_zs_7,//紫色数字7
		R.drawable.num_zs_8,//紫色数字8
		R.drawable.num_zs_9//紫色数字9
	};
	//其余自己自定义图片的ID数组列表
	public static int[] OTHER_PIC_ID=
	{
		R.drawable.gk_1,	//0第一关
		R.drawable.gk_2,	//1第二关
		R.drawable.gk_3,	//2第三关
		R.drawable.dhk,		//3对话框   
		R.drawable.v_zjm,	//4主界面
		R.drawable.v_xyg,	//5下一关
		R.drawable.fs_back,	//6开始界面中分数的对话框
		R.drawable.level_cleared,	//7过关后的界面提示
		R.drawable.level_failure	//8未过关后的界面提示
	};
	//其余自己自定义的图片数组
	public static Bitmap[] OTHER_PIC_ARRAY;
	
	//预加载的图片数组
	public static Bitmap[] PIC_ARRAY;   
	//预加载的白色数字图片数组
	public static Bitmap[] NUM_ARRAY;
	//预加载的黄色数字图片数组
	public static Bitmap[] Y_NUM_ARRAY;
	//预加载的紫色数字图片数组
	public static Bitmap[] P_NUM_ARRAY;
	//加载图片的方法
	public static void loadPic(Resources res)
	{
		 PIC_ARRAY=new Bitmap[PIC_ID.length];
		 NUM_ARRAY=new Bitmap[NUM_BS_ID.length];
		 Y_NUM_ARRAY=new Bitmap[NUM_HS_ID.length];
		 P_NUM_ARRAY=new Bitmap[NUM_ZS_ID.length];
		 OTHER_PIC_ARRAY=new Bitmap[OTHER_PIC_ID.length];
		 for(int i=0;i<PIC_ID.length;i++)
		 {
			 PIC_ARRAY[i]=PicLoadUtil.loadBM(res, PIC_ID[i]);			 
		 }
		 for(int i=0;i<NUM_BS_ID.length;i++)
		 {
			 NUM_ARRAY[i]=PicLoadUtil.loadBM(res, NUM_BS_ID[i]);
			 NUM_ARRAY[i]=PicLoadUtil.scaleToFitXYRatio(NUM_ARRAY[i], yMainRatio,yMainRatio);
		 }
		 for(int i=0;i<NUM_HS_ID.length;i++)
		 {
			 Y_NUM_ARRAY[i]=PicLoadUtil.loadBM(res, NUM_HS_ID[i]);
			 Y_NUM_ARRAY[i]=PicLoadUtil.scaleToFitXYRatio(Y_NUM_ARRAY[i], yMainRatio,yMainRatio);
		 }
		 for(int i=0;i<NUM_ZS_ID.length;i++)
		 {
			 P_NUM_ARRAY[i]=PicLoadUtil.loadBM(res, NUM_ZS_ID[i]);
			 P_NUM_ARRAY[i]=PicLoadUtil.scaleToFitXYRatio(P_NUM_ARRAY[i], yMainRatio,yMainRatio);
		 }
		 //自己自定义的图片数组
		 for(int i=0;i<OTHER_PIC_ID.length;i++)
		 {
			 OTHER_PIC_ARRAY[i]=PicLoadUtil.loadBM(res, OTHER_PIC_ID[i]);
			 OTHER_PIC_ARRAY[i]=PicLoadUtil.scaleToFitXYRatio(OTHER_PIC_ARRAY[i], yMainRatio,yMainRatio);
		 }
		 
		 //背景图按照高度缩放
		 PIC_ARRAY[17]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[17], yMainRatio,yMainRatio);
		 //弹弓图片按照高度缩放
		 PIC_ARRAY[22]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[22], yMainRatio*0.7f,yMainRatio*0.7f);
		 //鼠头图片按照高度缩放
		 PIC_ARRAY[1]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[1], yMainRatio,yMainRatio);
		 //两种草缩放
		 PIC_ARRAY[25]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[25], yMainRatio,yMainRatio);
		 PIC_ARRAY[26]=PicLoadUtil.scaleToFitXYRatio(PIC_ARRAY[26], yMainRatio,yMainRatio);
	}	 
	
	public static float DMGD=40.0f;//地面高度
	
	public static int currStage=0;//当前关卡 0-第一关
	
	public static int[] GG_SCORE={10000,15000,20000};//过关分
	
	public static int[] HH_SCORE={0,0,0};
	
	//=======================================================================================
	//贴图物体是否静止列表	true表示的是静态false表示的是动态
	public static boolean[][] IS_MOVE=
	{
		//第一关的是否静止列表
		{	
			true,true,true,
			false,false,false,false,false,
			false,false,false,false,false,
			false,false,false,false,false,
			false,false,false,false,false,
			false,false,false
		},
		//第二关的是否静止列表
		{
			true,true,true,
			false,false,false,false,false,false,
			false,false,false,false,
			false,false,false,false,false,
			false,false,false
		},
		//第三关的是否静止列表
		{
			true,true,
			true,true,false,false,
			false,false,false,false,false,false,
			true
		}
	};
	
	//贴图物体贴图的图片数组索引列表[地面0 ][睁眼睛的老鼠1][小猫2][横木头345][竖石头6]
	public static int[][][] IMG_ID=
	{
		//第一关的物体贴图数组
		{
			{0},//地面
			{15},//左侧灰泥台子   
			{16},//右侧灰泥台子
			{6},//左侧物件中的石条
			{2},//左侧小猫
			{11,12,13,14},//左侧竖着的冰块
			{3,4,5},//左侧横着的木条
			{18},//左侧上方横着的石头
			{6},//右侧物件中的石条
			{2},//右侧小猫
			{19,20,21},//右侧竖着的木条
			{7,8,9,10},//右侧横着的冰块
			{18},//右侧上方横着的石头
			{18},//右侧上方横着的石头
			{18},//左侧上方横着的石头
			{18},//右侧上方横着的石头
			{18},//左侧上方横着的石头
			{6},//左侧竖着的石条
			{6},//左侧竖着的石条
			{18},//左侧竖着的石条
			{6},//左侧竖着的石条
			{6},//左侧竖着的石条
			{18},//左侧竖着的石条
			{19,20,21},//右侧竖着的木条
			{19,20,21},//右侧竖着的木条
			{7,8,9,10},//右侧横着的冰块
		},
		//第二关的物体贴图数组
		{
			{0},//地面
			{15},//左侧灰泥台子   
			{16},//右侧灰泥台子
			{11,12,13,14},//左侧竖着的冰块
			{2},//左侧小猫
			{19,20,21},//左侧竖直的木条
			{7,8,9,10},//左侧横着的冰块
			{19,20,21},//左侧上方竖着的木条
			{6},//左侧竖着的石条
			{2},//缝隙中的小猫
			{6},//右侧竖着的石条
			{2},//右侧的小猫
			{6},//左侧竖着的石条
			{6},//左侧竖着的石条
			{18},//左侧竖着的石条
			{6},//左侧竖着的石条
			{6},//左侧竖着的石条
			{18},//左侧竖着的石条
			{19,20,21},//右侧竖着的木条
			{19,20,21},//右侧竖着的木条
			{7,8,9,10},//右侧横着的冰块
		},
		//第三关的物体贴图数组
		{
			{0},//地面
			{15},//左侧灰泥台子   
			{6},//竖着的石条
			{18},//左侧上方横着的石头
			{2},//小猫
			{2},//小猫
			{19,20,21},//左侧上方竖着的木条
			{11,12,13,14},//左侧上方竖着的冰块
			{7,8,9,10},//左侧上方横着的冰块
			{6},//左侧竖着的石条
			{2},//小猫
			{2},//小猫
			{6},//右侧竖着的石条
		}
	};  
	
	//贴图物体的类型
	public static BodyType[][] typeA=
	{
		//第一关的贴图物体类型
		{
			BodyType.DM,BodyType.HNTZ,BodyType.HNTZ,
			BodyType.ST,BodyType.XM,BodyType.BK,BodyType.MT,BodyType.ST,		
			BodyType.ST,BodyType.XM,BodyType.MT,BodyType.BK,BodyType.ST,
			BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,
			BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,
			BodyType.MT,BodyType.MT,BodyType.BK,
		},
		//第二关的贴图物体类型
		{
			BodyType.DM,BodyType.HNTZ,BodyType.HNTZ,
			BodyType.BK,BodyType.XM,BodyType.MT,BodyType.BK,BodyType.MT,BodyType.ST,
			BodyType.XM,BodyType.ST,BodyType.XM,BodyType.ST,
			BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,BodyType.ST,
			BodyType.MT,BodyType.MT,BodyType.BK,
		},
		//第三关的贴图物体类型
		{
			BodyType.DM,BodyType.HNTZ,
			BodyType.ST,BodyType.ST,BodyType.XM,BodyType.XM,
			BodyType.MT,BodyType.BK,BodyType.BK,BodyType.ST,BodyType.XM,BodyType.XM,
			BodyType.ST
		}
	};
	
	//贴图物体初始速度列表(X正向右，Y正向下)
	public static float[][][] SPEED=
	{
		//第一关的贴图物体初始速度
		{
			{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0}
		},
		//第二关的贴图物体初始速度
		{
			{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0}
		},
		//第三关的贴图物体初始速度
		{
			{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},
			{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},
			{0,0}
		}
	};
	
	//贴图物体的初始位置
	public static float[][][] LOCATION=
	{
		//第一关贴图物体的初始位置
		{
			{0,440},{760,400},{1000,360},
			{840,340},{870,360},{920,340},{840,320},{920,300},		
			{1000,300},{1030,320},{1080,300},{1000,280},{1080,260},
			{840,300},{1000,260},{880,300},{1040,260},{1240,260},
			{1340,260},{1240,240},{1240,60},{1340,60},{1240,40},			
			{1120,300},{1180,300},{1120,280}
		},
		//第二关贴图物体的初始位置
		{
			{0,440},{760,400},{1000,400},
			{820,340},{850,360},{900,340},{820,320},{860,300},{940,340},
			{960,400},{1010,320},{1030,360},{1240,260},
			{1340,260},{1240,240},{1240,60},{1340,60},{1240,40},
			{1080,300},{1140,300},{1080,280}
		},
		//第三关贴图物体的初始位置
		{
			{0,440},{760,400},
			{730,320},{730,300},{920,360},{880,360},
			{770,200},{800,260},{800,240},{840,260},{800,200},{860,260},
			{960,100}
		}
	};
	
	//贴图物体的目标尺寸
	public static float[][][] SIZE=
	{
		//第一关贴图物体的目标尺寸
		{  
			{2000,40},{200,40},{240,80},
			{20,60},{40,40},{20,60},{100,20},{20,20},
			{20,60},{40,40},{20,60},{100,20},{20,20},
			{20,20},{20,20},{20,20},{20,20},{20,180},
			{20,180},{120,20},{20,180},{20,180},{120,20},			
			{20,60},{20,60},{80,20}
		},
		//第二关贴图物体的目标尺寸
		{
			{2000,40},{200,40},{200,40},
			{20,60},{40,40},{20,60},{100,20},{20,20},{20,60},
			{40,40},{20,80},{40,40},{20,180},
			{20,180},{120,20},{20,180},{20,180},{120,20},
			{20,60},{20,60},{80,20}
		},
		//第三关贴图物体的目标尺寸
		{
			{2000,40},{300,40},
			{20,120},{170,20},{40,40},{40,40},
			{20,100},{20,40},{60,20},{20,40},{40,40},{40,40},
			{20,300}
		}
	};
	   
	//贴图物体相对坐标列表（给定的顶点要顺时针	）   
	public static float[][][][] STONE_VERTEX=
	{
		//第一关贴图物体相对坐标列表（给定的顶点要顺时针	）   
		{
			//地面
			{
		    	{0,0},{2000,0},{2000,40},{0,40}
			},
		    //左侧灰泥台子 
			{ 
				{20,0},{200,0},{200,40},{0,40}
			},
			//右侧灰泥台子
			{
				{0,0},{220,0},{240,80},{0,80}
			},
			//左侧物件中的石条
			{
				{0,0},{20,0},{20,60},{0,60}
			},
			//左侧小猫
		    {
		    	{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
		    },	 
			//左侧物件中的冰块
		    {
				{0,0},{20,0},{20,60},{0,60}
		    },
		    //左侧物件中上方的木条
		    {
				{0,0},{100,0},{100,20},{0,20}
		    },
		    //左侧上方横着的石头
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },		    
		    //右侧物件中的石条
			{
				{0,0},{20,0},{20,60},{0,60}
			},
			//右侧小猫
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
		    },	 
			//右侧物件中的木条  
		    {
				{0,0},{20,0},{20,60},{0,60}
		    },
		    //右侧物件中上方的冰块
		    {
				{0,0},{100,0},{100,20},{0,20}
		    },
		    //右侧上方横着的石头
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
		    //左侧上方横着的石头
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
		    //右侧上方横着的石头
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
		    //左侧上方中间横着的石头
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
		    //右侧上方中间横着的石头
		    {
		    	{0,0},{20,0},{20,20},{0,20}
		    },
		    //右侧石头
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//右侧石头
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//右侧上方横着的石头
		    {
		    	{0,0},{120,0},{120,20},{0,20}
		    },
		    //右侧石头
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//右侧石头
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//右侧上方横着的石头
		    {
		    	{0,0},{120,0},{120,20},{0,20}
		    },
		    //右侧物件中的木条
			{
				{0,0},{20,0},{20,60},{0,60}
			}, 
			//右侧物件中的木条
		    {
				{0,0},{20,0},{20,60},{0,60}
		    },
		    //右侧物件中上方的冰块
		    {
				{0,0},{80,0},{80,20},{0,20}
		    }
		},
		//第二关贴图物体相对坐标列表（给定的顶点要顺时针	）   
		{
			//地面
			{
		    	{0,0},{2000,0},{2000,30},{0,30}
			},
			 //左侧灰泥台子 
			{ 
				{20,0},{200,0},{200,40},{0,40}
			},
			//左侧灰泥台子
			{
				{0,0},{180,0},{200,40},{0,40}
			},
			//左侧竖直冰块
			{
				{0,0},{20,0},{20,60},{0,60}
			},
			//左侧小猫
			{
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//左侧木块
			{
				{0,0},{20,0},{20,60},{0,60}
			},
			//左侧上方冰块
			{
				{0,0},{100,0},{100,20},{0,20}
			},
			//左侧上方木块
			{
				{0,0},{20,0},{20,20},{0,20}
			},
			//左侧石头
			{
				{0,0},{20,0},{20,60},{0,60}
			},
			//缝隙中的小猫
			{
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//右侧石头
			{
				{0,0},{20,0},{20,80},{0,80}
			},
			//右侧小猫
			{
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//右侧石头
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//右侧石头
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//右侧上方横着的石头
		    {
		    	{0,0},{120,0},{120,20},{0,20}
		    },
		    //右侧石头
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//右侧石头
			{
				{0,0},{20,0},{20,180},{0,180}
			},
			//右侧上方横着的石头
		    {
		    	{0,0},{120,0},{120,20},{0,20}
		    },
		  //右侧物件中的木条
			{
				{0,0},{20,0},{20,60},{0,60}
			}, 
			//右侧物件中的木条
		    {
				{0,0},{20,0},{20,60},{0,60}
		    },
		    //右侧物件中上方的冰块
		    {
				{0,0},{80,0},{80,20},{0,20}
		    }
		},
		//第三关贴图物体相对坐标列表（给定的顶点要顺时针	）   
		{
			//地面
			{
		    	{0,0},{2000,0},{2000,30},{0,30}
			},
			 //左侧灰泥台子 
			{ 
				{20,0},{300,0},{300,40},{0,40}
			},
			//左侧竖直石头
			{
				{0,0},{20,0},{20,120},{0,120}
			},
			//左侧横着的石头
			{
		    	{0,0},{170,0},{170,20},{0,20}
		    },
			//小猫
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}   
			},
			//小猫
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//左侧上方竖直木条
			{
				{0,0},{20,0},{20,100},{0,100}
			},
			//左侧上方竖直冰块
			{
				{0,0},{20,0},{20,40},{0,40}
			},
			//左侧上方横着的冰块
			{
				{0,0},{60,0},{60,20},{0,20}
			},
			//左侧上方竖直石头
			{
				{0,0},{20,0},{20,40},{0,40}
			},
			//左侧上方小猫
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//左侧上方小猫
		    {
				{0,0},{38,0},{40,20},{32,36},{6,36},{0,20}
			},
			//左侧上方竖直石头
			{
				{0,0},{20,0},{20,300},{0,300}
			}
		}
	};
	
	
	//是否已经执行过changeRatio方法 
	public static boolean changeRatioOkFlag=false;
	//动态自适应屏幕的方法
	public static void changeRatio()
	{
		if(changeRatioOkFlag)
		{
			return;
		}		
		changeRatioOkFlag=true;
		
		xMainRatio=SCREEN_WIDTH/SCREEN_WIDTH_STANDARD;
		yMainRatio=SCREEN_HEIGHT/SCREEN_HEIGHT_STANDARD;
		
		CJ_WIDTH=CJ_WIDTH*yMainRatio;	
		RATE=RATE*yMainRatio;
		DMGD=DMGD*yMainRatio;
		
		for(int k=0;k<SIZE.length;k++)
		{
			for(int i=0;i<SIZE[k].length;i++)
			{
				SIZE[k][i][0]=SIZE[k][i][0]*yMainRatio;
				SIZE[k][i][1]=SIZE[k][i][1]*yMainRatio;
			}
			
			for(int i=0;i<STONE_VERTEX[k].length;i++)
			{
				for(int j=0;j<STONE_VERTEX[k][i].length;j++)
				{
					STONE_VERTEX[k][i][j][0]=STONE_VERTEX[k][i][j][0]*yMainRatio;
					STONE_VERTEX[k][i][j][1]=STONE_VERTEX[k][i][j][1]*yMainRatio;
				}
			}
			
			for(int i=0;i<LOCATION[k].length;i++)
			{
				LOCATION[k][i][0]=LOCATION[k][i][0]*yMainRatio;
				LOCATION[k][i][1]=LOCATION[k][i][1]*yMainRatio;
			}
		}
	}
	
	//========================================================================================
	//线程休眠时间
	public static int SLEEPTIME=15;
	
	//图片连接处的修正值  
	public static float XOFFSET=1;
	
	//设置按钮距离上下的位置
	public static float SET_X_OFFSET=10;
	public static float SET_Y_OFFSET=10;
	//偏移量
	public static float SET_BACK_CK_OFFSET=10;
	
	//得到图片的编号
	public static int[] PIC_NUM=
	{
		R.drawable.bg,		//0背景图片
		R.drawable.ground,	//1地面图片
		R.drawable.c_dd,	//2长势强壮的草
		R.drawable.c_xd,	//3小草
		R.drawable.ls_zy,	//4睁眼老鼠
		R.drawable.ls_by,	//5闭眼老鼠
		R.drawable.play,	//6开始按钮
		R.drawable.set,		//7设置按钮
		R.drawable.xm,		//8小猫
		R.drawable.set_back,	//9设置中的背景
		R.drawable.set_sy,		//10设置中的子选项——声音
		R.drawable.set_xx,		//11设置中的子选项———信息查看
		R.drawable.set_ck,		//12设置中的子选项——获得分数的信息
		R.drawable.bg_fg,		//13覆盖背景的透明图层
		R.drawable.about,		//14关于界面   
		R.drawable.close,		//15 关闭按钮
		R.drawable.set_close,	//16关闭声音按钮
		R.drawable.set_cl,		//17齿轮
		R.drawable.sndls		//18标题
	};	
	//图片数组
	public static Bitmap[] PIC_BITMAP;
	//缩放后的图片数组
	public static Bitmap[] PE_ARRAY;	
	//加载图片数组的方法
	public static void initBitmap(Resources e)
	{
		//计算比例
		xMainRatio=SCREEN_WIDTH/SCREEN_WIDTH_STANDARD;
		yMainRatio=SCREEN_HEIGHT/SCREEN_HEIGHT_STANDARD;
		PIC_BITMAP=new Bitmap[PIC_NUM.length];
		//缩放数组
		PE_ARRAY=new Bitmap[PIC_NUM.length];		
		
		for(int i=0;i<PIC_NUM.length;i++)
		{
			PIC_BITMAP[i]=PicLoadUtil.loadBM(e, PIC_NUM[i]);
		}		
		for(int j=0;j<PIC_NUM.length;j++)
		{
			PE_ARRAY[j]=PicLoadUtil.scaleToFitXYRatio(PIC_BITMAP[j], yMainRatio, yMainRatio);
		}
	}	
	//坐标的二维数组
	public static float[][] LOCALTION_BUTTON;
	//得到各个间距
	public static float[][] getLocaltionAndWH()
	{ 
		//设置按钮与弹出按钮的间距
		float SET_SPACE=(PE_ARRAY[7].getWidth()-PE_ARRAY[9].getWidth())/2;
		//各个按钮的坐标(包括子按钮)(对应的为——x,y,w,h)
		LOCALTION_BUTTON=new float[][]
		{
			//设置按钮
			{
				SET_X_OFFSET,SCREEN_HEIGHT-PE_ARRAY[7].getHeight()-SET_Y_OFFSET,
				PE_ARRAY[7].getWidth(),PE_ARRAY[7].getHeight()
			},			 
			 //点击设置按钮后弹出的声音按钮
			{
				SET_X_OFFSET+SET_SPACE,SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[10].getHeight()*2,
				PE_ARRAY[10].getWidth(),PE_ARRAY[10].getHeight()
			},			 
			 //点击设置按钮后弹出的查看公司信息按钮
			{
				SET_X_OFFSET+SET_SPACE,SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[11].getHeight()*3,
				PE_ARRAY[11].getWidth(),PE_ARRAY[11].getHeight()
			},			
			//点击设置按钮后弹出的查看分数信息按钮
			{
				SET_X_OFFSET+SET_SPACE,SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[12].getHeight()*4,
				PE_ARRAY[12].getWidth(),PE_ARRAY[12].getHeight()
			},			 
			 //点击的是“PLAY”按钮
			{
				(SCREEN_WIDTH-PE_ARRAY[6].getWidth())/2,(SCREEN_HEIGHT-PE_ARRAY[6].getHeight())/2,
				PE_ARRAY[6].getWidth(),PE_ARRAY[6].getHeight()
			}
		};
		return LOCALTION_BUTTON;
	}
}