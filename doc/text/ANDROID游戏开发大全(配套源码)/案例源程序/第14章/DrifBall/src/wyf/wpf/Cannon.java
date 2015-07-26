package wyf.wpf;			//声明包语句
/*
 * 该类对应地图中的大炮，地图模型中每有一个大炮，就会有一个Cannon对象创建
 * Cannon对象主要的方法是fire，此方法会根据当时小球的位置，创建并返回一个Missile对象，
 * 该Missile对象会被添加到一个集合中并被绘制
 */
public class Cannon{
	GameView father;		//GameView对象引用
	int row;				//大炮在地图上的行数
	int col;				//大炮在地图上的列数
	int range;				//射程
	//构造器，初始化成员变量
	public Cannon(int row,int col,GameView father){
		this.row = row;
		this.col = col;
		this.father = father;
		this.range = 840;
	}
	//方法：产生一个Missile对象
	public Missile fire(){
		int missileX = col*father.tileSize + 10;			//确定炮弹的起始X坐标
		int missileY = row*father.tileSize + 10;			//确定炮弹的起始Y坐标
		float k = (float)(missileY-father.ballY)/(missileX-father.ballX);	//计算炮弹的斜率
		int span = missileX>father.ballX?-2:2;								//判断是向左运动还是向右移动
		return new Missile(missileX,missileY,k,span,father);				//创建Missile对象并返回
	}
}