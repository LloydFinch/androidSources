package wyf.wpf;//声明包语句
//每个Particle对象代表一个粒子对象
public class Particle{
	int color;//粒子颜色
	int r;//粒子半径
	double vertical_v;//垂直速度
	double horizontal_v;//水平速度
	int startX;//开始x坐标
	int startY;//开始y坐标
	int x;//当前x坐标
	int y;//当前y坐标
	double startTime;//起始时间
	//构造器，初始化成员变量
	public Particle(int color, int r, double vertical_v, 
			double horizontal_v, int x, int y, double startTime){
		this.color = color;	//初始化粒子颜色
		this.r = r;	//初始化粒子半径
		this.vertical_v = vertical_v;	//初始化竖直方向速度
		this.horizontal_v = horizontal_v;	//初始化水平方向上速度
		this.startX = x;		//初始化开始位置的X坐标
		this.startY = y;		//初始化开始位置的Y坐标
		this.x = x;						//初始化实时X坐标
		this.y = y;							//初始化实时Y坐标
		this.startTime = startTime;			//初始化开始运动的时间
	}
}