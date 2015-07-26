package wyf.ytl;

import java.util.ArrayList;
import java.util.Collections;

public class ConstantUtil {
	public static final int SCREEN_WIDTH = 320;//屏幕宽度    
	public static final int SCREEN_HEIGHT = 480;//屏幕宽度    
	 
	/**
	 * MenuView界面中用到的常量
	 */
	public static final int MENU_VIEW_SLEEP_SPAN = 200;//MenuView界面刷帧线程睡眠时间
	public static final int MENU_VIEW_WORD_SPACE = 15;//MenuView界面菜单之间的上下间距
	public static final int MENU_VIEW_UP_SPACE = 110;//MenuView界面菜单距上边沿的距离
	public static final int MENU_VIEW_LEFT_SPACE = 110;//MenuView界面菜单距左边沿的距离
	public static final int PICTURECOUNT = 18;//滚动背景切割数 
	public static final int PICTUREWIDTH = 50;
	public static final int PICTUREHEIGHT = 480;
	
	/**
	 * LoadingView界面中用到的常量
	 */
	public static final int LOADING_VIEW_WORD_SIZE = 12;//进度条界面中“加载中”三个字的大小
	public static final int LOADING_VIEW_START_X = 20;//进度条界面中进度条的x坐标
	public static final int LOADING_VIEW_START_Y = 230;//进度条界面中进度条的y坐标
	public static final int LOADING_VIEW_SLEEP_SPAN = 400;//进度条界面刷帧线程睡眠时间
	
	/**
	 * GameView界面中用到的常量
	 */
	public static final int GAME_VIEW_SLEEP_SPAN = 100;//GameView界面刷帧线程睡眠时间
	public static final int DIALOG_START_Y = 360;//游戏中对话框的绘制y坐标，x坐标为零
	//对话框中的文字大小如果为16，那么开始x坐标为24，1行的汉字数为17
	//对话框中的文字大小如果为18，那么开始x坐标为24 ，1行的汉字数为15
	public static final int DIALOG_WORD_SIZE = 16;//对话框中文字的大小
	public static final int DIALOG_WORD_START_X = 24;//对话框中文字开始的x坐标
	public static final int DIALOG_WORD_START_Y = 390;//对话框文字开始的y坐标
	public static final int DIALOG_WORD_EACH_LINE = 17;//对话框每行的文字个数
	public static final int DIALOG_BTN_START_X = 50;//对话框中按钮的开始x坐标
	public static final int DIALOG_BTN_START_Y = 440;//对话框中按钮的开始y坐标
	public static final int DIALOG_BTN_WIDTH = 60;//对话框中按钮的宽度
	public static final int DIALOG_BTN_HEIGHT = 30;//对话框中按钮的高度
	public static final int DIALOG_BTN_SPAN = 160;//对话框中两个按钮的x方向上的间距
	public static final int DIALOG_BTN_WORD_LEFT = 12;//对话框中按钮上文字距按钮左边的距离
	public static final int DIALOG_BTN_WORD_UP = 6;//对话框中按钮上文字距按钮上边沿的距离
	public static final int DICE_START_X = 2;//骰子开始的x坐标
	public static final int DICE_START_Y = 452;//骰子开始的y坐标
	public static final int DICE_SPAN = 28;//骰子之间的间距
	public static final int GAME_VIEW_MEMU_WORD_SPACE = 10;//GameView界面中出现的主菜单之间的上下间距
	public static final int GAME_VIEW_MEMU_LEFT_SPACE = 100;//GameView界面中出现的主菜单距左边距的距离
	public static final int GAME_VIEW_MEMU_UP_SPACE = 90;//GameView界面中出现的主菜单距上边沿的距离	
	public static final int GAME_VIEW_SCREEN_ROWS= 14;//GameView总共的行数
	public static final int GAME_VIEW_SCREEN_COLS = 11;//GameView总共的列数
	public static final int MIN_FOOD = 2000;//城池小于该值则报警
	public static final int HERO_FACE_START_Y = 365;//英雄头像起始的y坐标，x坐标为零。英雄的头像可以被点击从而进行掷骰子
	public static final int HERO_FACE_WIDTH = 60;//英雄头像的宽度
	public static final int HERO_FACE_HEIGHT = 60;//英雄头像的高度
	public static final int DASHBOARD_START_Y = 365;
	public static final int DICE_SIZE = 25;
	public static final int ROLL_SCREEN_SPACE_RIGHT =149;//英雄距屏幕右边界149个像素时就应该滚屏了320-(31*5+16)
	public static final int ROLL_SCREEN_SPACE_DOWN = 216;//英雄距屏幕下边界216个像素时就应该滚屏了480-(31*8+16)
	public static final int ROLL_SCREEN_SPACE_LEFT =140;//英雄距屏幕右边界140个像素时就应该滚屏了31*4+16
	public static final int ROLL_SCREEN_SPACE_UP = 140;//英雄距屏幕下边界140个像素时就应该滚屏了31*4+16
	public static final int MINI_MAP_SPAN = 4;//迷你地图的块大小
	public static final int MINI_MAP_START_X = 80;//迷你地图开始的x坐标,
	public static final int MAP_BUTTON_START_X = 62;//迷你地图开关的开始x坐标
	public static final int MAP_BUTTON_START_Y = 392;//迷你地图开关的开始y坐标
	public static final int MAP_BUTTON_SIZE = 17;//迷你地图开关的大小
	public static final int STRENGTH_COST_DECREMENT = 2;//每次技能升级减小的体力消耗值
	/**
	 * 英雄类中中用到的常量
	 */
	public static final int HERO_ANIMATION_SEGMENTS = 8;//英雄总共的动画段个数
	public static final int HERO_ANIMATION_FRAMES =2;//英雄每个动画段的总帧数
	public static final int HERO_WIDTH = 31;//英雄图片的宽度
	public static final int HERO_HEIGHT = 62;//英雄图片的高度
	public static final int HERO_MOVING_SPAN = 4;//英雄走路的步进!!!!!!!!!注意，如果这个改了，那么无级滚屏那里也要检查需不需要该！！！！！
	public static final int HERO_MOVING_SLEEP_SPAN = 50;//英雄无级每走一小步休眠的时间
	public static final int HERO_WAIT_SPAN = 1000;//英雄没有在走，走路线程空转的等待时间
	public static final int HERO_ANIMATION_SLEEP_SPAN = 300;//英雄动画变换休眠的时间
	public static final int HERO_NO_ANIMATION_SLEEP_SPAN = 2000;//英雄没有动画变换时，换帧线程空转的睡眠时间
	public static final int UP = 7;//英雄的移动方向向上，也代表相应动画段
	public static final int DOWN = 4;//英雄的移动方向向下，也代表相应动画段
	public static final int LEFT = 5;//英雄的移动方向向左，也代表相应动画段
	public static final int RIGHT = 6;//英雄的移动方向向右，也代表相应动画段
	
	
	//英雄的官衔
	public static final String[] HERO_TITLE = new String[]
	{
		"公爵",
		"侯爵",
		"伯爵",
		"子爵",
		"男爵"
	};
	
	//将领的职位
	public static final String[] GENERAL_TITLE = new String[]
	{
		"总兵",
		"国宗",
		"镇远将军",
		"保国将军",
		"镇国将军",
		"辅国将军",
		"护国将军"
	};
	
	//科研的项目
	public static final String[] RESEARCH_PROJECT = new String[]
	{
		"战车",
		"箭垛"  
	};
	
	//敌方城池中的将领
	public static final ArrayList<General> ENEMY_GENERAL = new ArrayList<General>();
	static{
		ENEMY_GENERAL.add(new General("赵奢", 5, 94, 80, 69, 69, 78, 80, 8));
		ENEMY_GENERAL.add(new General("田忌", 4, 92, 68, 63, 72, 73, 90, 7));
		ENEMY_GENERAL.add(new General("田单", 5, 92, 70, 84, 52, 68, 82, 5));
		ENEMY_GENERAL.add(new General("晏婴", 3, 91, 69, 84, 52, 68, 70, 2));
		ENEMY_GENERAL.add(new General("乐毅", 2, 92, 67, 63, 57, 67, 88, 1));
		ENEMY_GENERAL.add(new General("吴起", 1, 97, 20, 44, 54, 67, 95, 4));
		ENEMY_GENERAL.add(new General("商鞅", 6, 97, 40, 84, 82, 88, 55, 3));
		ENEMY_GENERAL.add(new General("苏秦", 5, 97, 70, 74, 78, 82, 95, 5));
		ENEMY_GENERAL.add(new General("张仪", 3, 97, 70, 54, 52, 58, 28, 2));
		ENEMY_GENERAL.add(new General("白起", 5, 97, 50, 84, 52, 68, 63, 3));
		ENEMY_GENERAL.add(new General("王翦", 2, 97, 70, 84, 52, 68, 56, 4));
		ENEMY_GENERAL.add(new General("田文", 4, 97, 60, 84, 52, 68, 95, 5));
		ENEMY_GENERAL.add(new General("赵胜", 6, 97, 77, 94, 42, 48, 75, 1));
		ENEMY_GENERAL.add(new General("魏无忌", 4, 97, 70, 84, 52, 68, 85, 1));
		ENEMY_GENERAL.add(new General("黄歇", 2, 97, 64, 24, 32, 48, 87, 1));
		ENEMY_GENERAL.add(new General("范雎", 2, 97, 30, 54, 52, 38, 88, 2));
		ENEMY_GENERAL.add(new General("蔡泽", 3, 97, 30, 64, 56, 53, 89, 4));
		ENEMY_GENERAL.add(new General("廉颇", 5, 97, 76, 84, 72, 63, 49, 5));
		ENEMY_GENERAL.add(new General("蒙恬", 4, 97, 74, 64, 52, 70, 59, 5));
		ENEMY_GENERAL.add(new General("蔺相如", 5, 97, 70, 84, 52, 68, 94, 6));
		ENEMY_GENERAL.add(new General("吕不韦", 1, 97, 32, 24, 42, 28, 45, 8));
		ENEMY_GENERAL.add(new General("李斯", 1, 56, 52, 62, 59, 68, 68, 2));
	}
	
	//所有的城市信息
	public static final ArrayList<CityInfo> CITY_INFO = new ArrayList<CityInfo>();
	static{
		Collections.shuffle(ENEMY_GENERAL);//打乱顺序
		CITY_INFO.add(new CityInfo("洛阳", 0, 5000, 5000, 2, 22, 15, 80000, 5, 8, ENEMY_GENERAL.get(0),1));
		CITY_INFO.add(new CityInfo("下蔡", 1, 6000, 6000, 3, 22, 15, 40000, 3, 4, ENEMY_GENERAL.get(1),2));
		CITY_INFO.add(new CityInfo("楚庭", 1, 7000, 7000, 4, 21, 16, 50000, 6, 5, ENEMY_GENERAL.get(2),3));
		CITY_INFO.add(new CityInfo("寿春", 2, 5000, 5000, 4, 20, 15, 50000, 7, 6, ENEMY_GENERAL.get(3),4));
		CITY_INFO.add(new CityInfo("陈城", 2, 6000, 6000, 5, 22, 15, 60000, 5, 7, ENEMY_GENERAL.get(4),5));
		CITY_INFO.add(new CityInfo("九江", 2, 7000, 7000, 3, 24, 15, 40000, 6, 8, ENEMY_GENERAL.get(5),6));
		CITY_INFO.add(new CityInfo("南平", 2, 8000, 4000, 4, 22, 15, 60000, 7, 4, ENEMY_GENERAL.get(6),7));
		CITY_INFO.add(new CityInfo("获鹿", 3, 5000, 5000, 5, 21, 14, 50000, 14, 9, ENEMY_GENERAL.get(7),8));
		CITY_INFO.add(new CityInfo("江州", 3, 6000, 7000, 4, 22, 11, 50000, 6, 4, ENEMY_GENERAL.get(8),9));
		CITY_INFO.add(new CityInfo("金城", 3, 7000, 4000, 3, 22, 14, 40000, 12, 7, ENEMY_GENERAL.get(9),10));
		CITY_INFO.add(new CityInfo("新郑", 4, 5000, 5000, 1, 24, 18, 60000, 5, 7, ENEMY_GENERAL.get(10),11));
		CITY_INFO.add(new CityInfo("承德", 4, 6000, 7000, 4, 22, 15, 70000, 5, 4, ENEMY_GENERAL.get(11),12));
		CITY_INFO.add(new CityInfo("东里", 4, 8000, 4000, 3, 22, 15, 30000, 7, 8, ENEMY_GENERAL.get(12),13));
		CITY_INFO.add(new CityInfo("长安", 4, 8000, 5000, 4, 22, 15, 30000, 3, 6, ENEMY_GENERAL.get(13),14));
		CITY_INFO.add(new CityInfo("巨鹿", 5, 5000, 7000, 3, 23, 13, 40000, 6, 5, ENEMY_GENERAL.get(14),15));
		CITY_INFO.add(new CityInfo("晋阳", 5, 6000, 5000, 5, 22, 13, 40000, 8, 6, ENEMY_GENERAL.get(15),16));
		CITY_INFO.add(new CityInfo("武安", 5, 8000, 4000, 3, 25, 15, 70000, 9, 6, ENEMY_GENERAL.get(16),17));
		CITY_INFO.add(new CityInfo("咸阳", 6, 5000, 5000, 2, 22, 15, 80000, 6, 4, ENEMY_GENERAL.get(17),18));
		CITY_INFO.add(new CityInfo("宛城", 6, 6000, 7000, 1, 22, 15, 20000, 5, 0, ENEMY_GENERAL.get(18),19));
		CITY_INFO.add(new CityInfo("野王", 6, 6000, 6000, 2, 22, 15, 30000, 4, 4, ENEMY_GENERAL.get(19),20));
		CITY_INFO.add(new CityInfo("临淄", 7, 5000, 5000, 4, 23, 14, 40000, 5, 8, ENEMY_GENERAL.get(20),21));
		CITY_INFO.add(new CityInfo("宁波", 7, 6000, 6000, 5, 22, 15, 50000, 7, 12, ENEMY_GENERAL.get(21),22));
	}
	
	//所有的国家
	public static final String[] COUNTRY_NAME = new String[]
	{
		"周国",
		"齐国",
		"楚国",
		"燕国",
		"韩国",
		"赵国",
		"魏国",
		"秦国",
		"自己"
	};
	
	/**
	 * 技能类中中用到的常量
	 */
	public static final int LUMBER = 0;//代表伐木技能，作为技能HashMap的键
	public static final int FISHING = 1;//代表打渔技能，作为技能HashMap的键
	public static final int FARMING = 2;//代表农耕技能，作为技能HashMap的键
	public static final int MINING = 3;//代表采矿技能，作为技能HashMap的键
	public static final int SUI_XIN_BU = 4;//代表随心步
	public static final int HUI_TOU_SHI_AN = 5;//代表回头是岸
	public static final int WU_ZHONG_SHENG_YOU = 6;//代表无中生有
	public static final int SKILL_LEVEL_MAX = 3;	//技能能够升到的最大等级
	
	/**
	 * 其他常量
	 */
	public static final int TILE_SIZE = 31;//地图图元的大小
	public static final int MAP_ROWS = 40;//地图有多少行
	public static final int MAP_COLS = 60;//地图有多少列
	public static final int PROFICIENCY_INCREMENT = 5;//英雄每使用一次技能增加的熟练度
	public static final int PROFICIENCY_UPGRADE_SPAN = 50;//英雄每升一级所需要技能熟练度上限增加，比如升第一级要100，升第二季要150
	public static final int HERO_STRENGTH_DRAW_X = 119;//英雄的体力显示x坐标
	public static final int HERO_STRENGTH_DRAW_Y = 438;//英雄的体力显示y坐标
	public static final int HERO_MONEY_DRAW_X = 215;//英雄的金钱那显示x坐标
	public static final int HERO_MONEY_DRAW_Y = 438;//英雄的金钱显示y坐标
	public static final int HERO_ARMY_DRAW_X = 119;//英雄的军队显示x坐标
	public static final int HERO_ARMY_DRAW_Y = 458;//英雄的军队显示y坐标
	public static final int HERO_FOOD_DRAW_X = 215;//英雄的粮草显示x坐标
	public static final int HERO_FOOD_DRAW_Y = 458;//英雄的粮草显示y坐标
	public static final int DIGIT_SPAN = 10;//两个数码管左边沿的距离
}
