package wyf.ytl;
public class ConstantUtil {
	/**
	 * GameView与GameViewBackGroundThread中用到的常量
	 */
	public static final int pictureWidth = 50;//单元图的宽度
	public static final int pictureHeight = 300;//单元图的宽度
	public static final int screenWidth = 480;//屏幕宽度    
	public static final int screenHeight = 320;//屏幕宽度    
	public static final int pictureCount = 40;//背景图片数量
	public static final int top = 10;//距上边沿的距离
	/**
	 * 下面是游戏中所用到的方向常量
	 * 0静止，1上, 2右上，3右，4右下，5下，6左下，7左，8左上
	 */
	public static final int DIR_STOP = 0;
	public static final int DIR_UP = 1;
	public static final int DIR_RIGHT_UP = 2;
	public static final int DIR_RIGHT = 3;
	public static final int DIR_RIGHT_DOWN = 4;
	public static final int DIR_DOWN = 5;
	public static final int DIR_LEFT_DOWN = 6;
	public static final int DIR_LEFT = 7;
	public static final int DIR_LEFT_UP = 8;
	public static final double BooletSpan = 0.1;//敌机发子弹的概率
	public static final double BooletSpan2 = 0.2;//关口发子弹的概率
	public static final int life = 5;//玩家飞机的生命
}