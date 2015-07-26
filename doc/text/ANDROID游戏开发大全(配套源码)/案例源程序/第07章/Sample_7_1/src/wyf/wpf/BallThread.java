package wyf.wpf;				//声明包语句
//继承自Thread的线程类，负责修改球的位置坐标
public class BallThread extends Thread{	
	Movable father;		//Movable对象引用
	boolean flag = false;	//线程执行标志位
	int sleepSpan = 30;		//休眠时间
	float g = 200;		//球下落的加速度
	double current;	//记录当前时间
	//构造器：初始化Movable对象引用及线程执行标志位
	public BallThread(Movable father){
		this.father = father;
		this.flag = true;		//设置线程执行的标志位为true
	}
	//方法：负责根据物理公式修改小球位置
	public void run(){
		while(flag){
			current = System.nanoTime();//获取当前时间，单位为纳秒
			double timeSpanX = (double)((current-father.timeX)/1000/1000/1000);//获取从玩家开始到现在水平方向走过的时间
			//处理水平方向上的运动
			father.x = (int)(father.startX + father.v_x * timeSpanX);
			//处理竖直方向上的运动			
			if(father.bFall){//判断球是否已经移出挡板
				double timeSpanY = (double)((current - father.timeY)/1000/1000/1000);	
				father.y = (int)(father.startY + father.startVY * timeSpanY + timeSpanY*timeSpanY*g/2);
				father.v_y = (float)(father.startVY + g*timeSpanY);				
				//判断小球是否到达最高点
				if(father.startVY < 0 && Math.abs(father.v_y) <= BallView.UP_ZERO){
					father.timeY = System.nanoTime();			//设置新的运动阶段竖直方向上的开始时间
					father.v_y = 0;								//设置新的运动阶段竖直方向上的实时速度
					father.startVY = 0;							//设置新的运动阶段竖直方向上的初始速度
					father.startY = father.y;					//设置新的运动阶段竖直方向上的初始位置
				}
				//判断小球是否撞地
				if(father.y + father.r*2 >= BallView.GROUND_LING && father.v_y >0){//判断撞地条件
					//改变水平方向的速度
					father.v_x = father.v_x * (1-father.impactFactor);	//衰减水平方向上的速度
					//改变竖直方向的速度
					father.v_y = 0 - father.v_y * (1-father.impactFactor);		//衰减竖直方向上的速度并改变方向
					if(Math.abs(father.v_y) < BallView.DOWN_ZERO){	//判断撞地后的速度，太小就停止
						this.flag = false;
					}
					else{	//撞地后的速度还可以弹起继续下一阶段的运动
						//撞地之后水平方向的变化
						father.startX = father.x;			//设置新的运动阶段的水平方向的起始位置
						father.timeX = System.nanoTime();	//设置新的运动阶段的水平方向的开始时间
						//撞地之后竖直方向的变化						
						father.startY = father.y;		//设置新的运动阶段竖直方向上的起始位置
						father.timeY = System.nanoTime();	//设置新的运动阶段竖直方向开始运动的时间
						father.startVY = father.v_y;	//设置新的运动阶段竖直方向上的初速度
					}
				}				
			}
			else if(father.x + father.r/2 >= BallView.WOOD_EDGE){//判断球是否移出了挡板			
				father.timeY = System.nanoTime();		//记录球竖直方向上的开始运动时间
				father.bFall = true;				//设置表示是否开始下落标志位
			}			
			try{
				Thread.sleep(sleepSpan);		//休眠一段时间				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}