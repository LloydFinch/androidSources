package wyf.wpf;			//声明包语句
/*
 * 该类继承自Thread类，主要负责将菜单及菜单项在屏幕上的位置
 * 进行改变以达到动画效果
 */
public class GameMenuThread extends Thread{
	DriftBall father;		//主类引用	
	boolean flag;			//循环控制变量
	boolean isIn;			//是否是淡入菜单
	boolean isOut;			//是否是淡出菜单
	int sleepSpan = 20;		//睡眠时间
	int [][] menuCoordinate={	//菜单在没进来之前的位置，分别是菜单背景和4个菜单项
			{350,0},
			{400,30},
			{450,80},
			{500,130},
			{550,180}
	};
	//构造器
	public GameMenuThread(DriftBall father){
		this.father = father;
		father.gv.menuCoordinate = this.menuCoordinate;
		flag = true;
		isIn = true;
	}
	//线程执行方法
	public void run(){
		int inIndex=0;			//淡入的索引顺序
		int outIndex = 4;		//淡出时的索引顺序
		while(flag){
			try{
			if(isIn){		//菜单进屏幕
				for(int i=inIndex;i<father.gv.menuCoordinate.length;i++){
					father.gv.menuCoordinate[i][0]-= 25;		//向屏幕内移动菜单项
					if(father.gv.menuCoordinate[i][0] == 150){		//判断菜单项是否已到位
						inIndex=i+1;			//将开始索引下移，即不再改变到位的菜单项坐标
					}
				}				
				if(inIndex == 5){		//如果所有菜单项都移动到位
					isIn = false;		//设置isIn标志位为false
				}
			}
			else if(isOut){			//菜单出屏幕
				father.gv.menuCoordinate[outIndex][0] +=10;		//向屏幕外移动菜单项
				if(father.gv.menuCoordinate[outIndex][0] >=320){	//判断是否将菜单项移出屏幕
					outIndex--;									//改移动前一个菜单项
					if(outIndex < 0){		//判断是否所有的菜单项都移出屏幕
						if(father.currView == father.gv){	//如果当前View是GameView
							father.gv.resumeGame();			//调用resumeGame方法恢复游戏
						}						
						flag = false;			//停止线程的执行						
					}
				}
			}		
				//线程休眠
				Thread.sleep(sleepSpan);
			}
			catch(Exception e)
			{
				flag=false;
				e.printStackTrace();
			}
		}
	}
}