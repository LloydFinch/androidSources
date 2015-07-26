package wyf.wpf;					//声明包语句
import static wyf.wpf.DriftBall.*;	//引入DriftBall类的静态成员
/*
 * 该类代表导弹，其中封装了导弹路径的斜率
 * 导弹的步进，导弹的坐标等，包含一个成员
 * 方法负责移动导弹的位置，并且判断是否
 * 炸到小球。
 */
public class Missile{
	float k;				//斜率
	int span=2;				//步进
	int x;					//导弹中心x坐标
	int y;					//导弹中心y坐标		
	GameView father;		//GameView 引用
	//构造器，初始化小球的位置、速度和方向
	public Missile(int x,int y,float k,int span,GameView father){
		this.x = x;
		this.y = y;
		this.k = k;
		this.span = span;
		this.father = father;
	}
	//方法：移动导弹并检测是否与小球相撞
	public void moveAndCheck(){
		//移动导弹
		x = x+span;
		y = (int)(y+ k*span);
		//检测是否碰到目标
		int bx = father.ballX+10;		//中心x坐标
		int by = father.ballY+10;		//中心y坐标
		if((bx-x)*(bx-x)+(by-y)*(by-y) <= 15*15){
			father.father.life -=1;
			if(father.father.life <=0){		//所有命都已经耗完
				father.gt.flag = false;	
				father.gt.isGameOn = false;
				father.status = STATUS_OVER;
			}
			else{							//还有命，那就从头开始
				father.ballX = 0;
				father.ballY = 0;
			}
			father.alMissile.remove(this);	//从现有集合中移出自己
		}
		//检测是否碰到边界
		if(x > father.screenWidth || x<0 || y>father.screenHeight || y<0){
			father.alMissile.remove(this);	
		}
	}
}