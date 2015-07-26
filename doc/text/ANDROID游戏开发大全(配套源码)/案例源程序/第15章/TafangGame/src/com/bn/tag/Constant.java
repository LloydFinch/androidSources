package com.bn.tag;

public class Constant {
	public static final float  SCREEN_WIDTH=480;//屏幕宽度
	public static final float  SCREEN_HEIGHT=800;//屏幕高度
	//1代表公路 2代表木桩  3代表高的树木  4代表花草
	public static final int[][] MAP=//0 不可通过 1可通过
	{
		{3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
		{3,2,2,2,0,0,0,4,4,4,0,0,0,0,0,2,3,3,3,3},
		{3,4,4,4,0,0,1,1,1,1,1,0,0,0,0,2,3,3,3,3},
		{3,2,0,0,0,4,1,0,0,0,1,0,0,0,0,2,4,4,4,3},
		{1,1,1,1,0,0,1,0,0,0,1,0,3,0,0,0,0,0,0,0},
		{2,2,0,1,0,2,1,0,0,0,1,0,3,0,1,1,1,1,1,0},
		{2,0,0,1,0,2,1,0,3,3,1,0,2,2,1,0,0,0,0,3},
		{2,0,0,1,0,2,1,0,3,3,1,0,2,2,1,0,0,0,0,3},
		{2,0,0,1,0,0,1,0,2,3,1,0,0,0,1,0,0,0,0,3},
		{2,0,0,1,1,1,1,0,2,0,1,1,1,1,1,0,2,2,3,3},
		{3,3,0,0,0,0,0,0,2,0,0,0,0,0,0,2,2,3,3,3},
		{3,3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3}
	};
	
	public static final int[][] MIDDLE_MAP=
	{
		{20,180},
		{140,180},
		{140,380},
		{260,380},
		{260,100},
		{420,100},
		{420,380},
		{580,380},
		{580,220},
		{780,220}
	};

	//箭塔的图片
	public static final float JIAN_TA_WEIGHT=40;
	public static final float JIAN_TA_HEIGHT=79;
	
	public static final float SINGLE_RODER=40;
	//背景图片
	public static final float BACK_WIDTH=480;
	public static final float BACK_HEIGHT=800;
	
	public static final float GONGLU_WEIGHT=40;
	public static final float GONGLU_HEIGHT=40;
	
	//水晶图片
	public static final float SHUIJIAN_STARTX=500;
	public static final float SHUIJIAN_STARTY=5;
	public static final float SHUIJING_WEIGHT=40;
	public static final float SHUIJIAN_HEIGHT=40;
	//水晶的中间位置的坐标
	public static final float SHUIJING_CENTER_X=520;
	public static final float SHUIJING_CENTER_Y=25;
	//金钱图片长度
	public static final float MONEY_WEIGHT=40;
	public static final float MONRY_HEIGHT=40;
	//生命图片
	public static final float BLOOD_WEIGHT=45;
	public static final float BLOOD_HEIGHT=40;
	//关卡
	public static final float LEVEL_WEIGHT=89;
	public static final float LEVEL_HEIGH=29;
	//图标
	public static final float TIBIAO_WEIGHT=70;
	public static final float TIBIAO_HEIGHT=70;
	
	//怪物行走的步长
	public static final float SINGLE_FOOT=0.625f;
	public static final float HALF_SINGLE_RODER=SINGLE_RODER/2+0.01f;
	
	//怪物大小
	public static final float SINGLE_PIC=38;
	//怪物头上天的血槽
	public static final float GUAI_BLOOD_WEIGHT=34;
	public static final float GUAI_BLOOD_HEIGHT=5;
	//箭头的图片
	public static final float JIAN_TOU_WEIGHT=11;
	public static final float JIAN_TOU_HEIGHT=11;
	//当前界面的怪物最大数量
	public static final float TARGET_MAX_NUM=10;
	
	//树木
	//树桩
	public static final float TREE_WEIGHT=40;
	public static final float TREE_HEIGHT=40;
	//高树
	public static final float BIG_TREE_WEIGHT=40;
    public static final float BIG_TREE_HEIGHT=80;
    //花草
    public static final float FLOWER_WEIGHT=40;
    public static final float FLOWER_HEIGHT=40;
    //塔的射击半径
    public static final float R_LENGTH=130;
    //箭头的图片
    public static final float JIANTOU_WEIGHT=40;
    public static final float JIANTOU_HEIGHT=7;
    //箭头的速度
    public static final float SPEED=1.25f;
    
    //主界面中的各个选项按钮的长度和宽度
    public static final float MAIN_LENGTH=170;
    public static final float MAIN_WEIGHT=40;
    //各个选项的起点坐标
    //继续游戏
    public static final float GOON_X=50;
    public static final float GOON_Y=60;
    //新游戏
    public static final float NEW_X=50;
    public static final float NEW_Y=110;
    //积分榜
    public static final float JIFEN_X=50;
    public static final float JIFEN_Y=160;
    //音效设置
    public static final float YINXIAO_X=50;
    public static final float YINXIAO_Y=210;
    //帮助
    public static final float HELP_X=50;
    public static final float HELP_Y=260;
    //退出
    public static final float EXIT_X=50;
    public static final float EXIT_Y=410;
    //游戏界面中的菜单图标
    public static final float CANDAN_WEIGHT=80;
    public static final float CANDAN_HEIGHT=80; 
    //游戏界面中的弹出菜单中的各个选项的坐标范围
    //保存游戏
    public static final float SAVE_GAME_X=50;
    public static final float SAVE_GAME_Y=130;
    //回到游戏
    public static final float GOBACK_GAME_X=50;
    public static final float GOBACK_GAME_Y=200;
    //退出游戏
    public static final float EXIT_GAME_X=50;
    public static final float EXIT_GAME_Y=270;
    //上面各个图标的长度和宽度
    public static final float TANCHU_CAIDAN_WEIGHT=170;
    public static final float TANCHU_CAIDAN_HEIGHT=40;
    //弹出菜单在游戏界面中出现的起点
    public static final float CAIDAN_GAME_START_X=120;
    //背景音乐界面中的按钮图片
    public static final float MUSIC_WEIGHT=220;
    public static final float MUSIC_HEIGHT=50;
    //对话框的ID
    public static final int CUN_DANG_DIALOG_ID=0;//存档名输入对话框id
    public static final int GO_ONGAME_DIALOG_ID=1;//继续游戏对话框ID
	
    //游戏中家的图片
    public static final float HOME_WEIGHT=75;
    public static final float HOME_HEIGHT=106;
    
    //游戏中家的位置
    public static final float HOME_X=720;
    public static final float HOME_Y=138;
 
	//每个关卡家的位置
	public static int [][] homeLocations={
			{5,18}
	};
	//怪物和箭塔的种类标识符
	public static final int GW_STATE01=0;
	public static final int GW_STATE02=1;
	public static final int GW_STATE03=2;
	
	public static final int JT_STATE01=1;
	public static final int JT_STATE02=2;
	
	
	
	public static int[] getHomeLocationByxy(int stage){
		return homeLocations[stage];
	}
}
