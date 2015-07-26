package wyf.wpf;				//声明包语句
/*
 * 这个类主要负责绘制元素的数据变化，如控制陨石和星云的帧何时切换
 * 同时还负责，移动小球。不同的元素的不同的换帧间隔是通过帧控制计数器
 * 来实现的。移动小球的同时还会相应地进行碰撞检测
 */
import static wyf.wpf.DriftBall.*;	//引入DriftBall类的静态常量
public class GameThread extends Thread{
	GameView father;		//GameView对象引用
	int sleepSpan = 25;		//休眠时间
	int backCounter;		//背景帧控制计数器
	int nebulaCounter;		//星云帧控制计数器
	int eatCounter;			//吃掉小球的帧控制计数器
	int trapCounter;		//陷阱帧控制计数器
	int meteoCounter;		//陨石帧控制计数器
	boolean flag = false;	//外层循环
	boolean isGameOn = false;	//游戏是否在进行
	//构造器
	public GameThread(GameView father){
		this.father = father;
		this.flag = true;
	}
	//线程执行方法
	public void run(){
		while(flag){
			while(isGameOn){
				//星空的运动
				backCounter++;
				if(backCounter == 50/sleepSpan){		//星空是500ms移动一下
					father.backY -=1;
					backCounter = 0;
					if(father.backY == -600){
						father.backY = 0;
					}
				}
				//星云的运动
				nebulaCounter++;
				if(nebulaCounter == 40/sleepSpan){
					father.nebulaY +=1;
					nebulaCounter = 0;
					if(father.nebulaY >= 480){
						father.nebulaY = -150;
						father.nebulaX = (int)(Math.random()*250);
					}
				}
				//陨石的运动
				meteoCounter++;			
				for(Meteorolite m:father.meteoArray){
					m.y +=2;
					if(m.y >= father.screenHeight){			//超出屏幕边界则重新初始化
						m.init();				
					}			
				}	
				if(meteoCounter == 150/sleepSpan){
					for(Meteorolite m:father.meteoArray){
						m.index = (m.index + 1)%8;					//-----------------这里有魔法数字---------
					}
					meteoCounter = 0;
				}					
				//吃人动画
				eatCounter++;
				if(eatCounter == 200/sleepSpan){
					father.eatIndex = (father.eatIndex+1)%father.bmpEat.length;
					eatCounter = 0;
				}
				//陷阱动画
				trapCounter++;
				if(trapCounter == 300/sleepSpan){
					father.trapIndex = (father.trapIndex+1)%father.bmpTrap.length;
					trapCounter = 0;
				}
				//移动炮弹
				try{
					if(father.alMissile.size() != 0){
						for(Missile m:father.alMissile){
							m.moveAndCheck();
						}
					}				
				}
				catch(Exception e){
				}	
				//移动小球
				checkAndMoveBall();
				try{
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}			
			try{
				Thread.sleep(800);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//检查移动小球
	public void checkAndMoveBall() {
		switch(father.direction){
		case 0:				//方向向上
			father.ballY -= father.velocity;		//Y方向上的移动
			if(checkCollision()){					//检测是否发生碰撞
				father.ballY += father.velocity;	//若发生碰撞就撤销移动
			}
			break;
		case 1:				//方向右上
			father.ballY -= father.velocity;			//Y方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				father.ballY += father.velocity;		//若发生碰撞就撤销移动
			}
			father.ballX += father.velocity;			//X方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				father.ballX -= father.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 2:				//方向向右
			father.ballX += father.velocity;			//X方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				father.ballX -= father.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 3:				//方向右下
			father.ballX += father.velocity;			//X方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				father.ballX -= father.velocity;		//若发生碰撞就撤销移动
			}
			father.ballY += father.velocity;			//Y方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				father.ballY -= father.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 4:				//方向向下
			father.ballY += father.velocity;			//Y方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				father.ballY -= father.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 5:				//方向左下
			father.ballX -= father.velocity;			//X方向上的移动
			if(checkCollision()){						//检测是否发生碰撞
				father.ballX += father.velocity;		//若发生碰撞就撤销移动
			}
			father.ballY += father.velocity;			//Y方向上的移动
			if(checkCollision()){						//若发生碰撞就撤销移动
				father.ballY -= father.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 6:				//方向向左
			father.ballX -= father.velocity;			//X方向上的移动
			if(checkCollision()){						//若发生碰撞就撤销移动
				father.ballX += father.velocity;		//若发生碰撞就撤销移动
			}
			break;
		case 7:				//方向左上
			father.ballX -= father.velocity;			//X方向上的移动
			if(checkCollision()){						//若发生碰撞就撤销移动
				father.ballX += father.velocity;		//若发生碰撞就撤销移动
			}
			father.ballY -= father.velocity;			//Y方向上的移动
			if(checkCollision()){						//若发生碰撞就撤销移动
				father.ballY += father.velocity;		//若发生碰撞就撤销移动
			}
			break;
		}
	}
	/*
	 * 进行碰撞检测，碰撞检测是分开进行的，即x方向和y方向是分开的
	 * 这样方便处理和理解。碰撞检测主要是检查小球四个角是否位于不可通过
	 * 的地方，同时检查是否超出屏幕边界，最后查看自己所在的位置是否
	 * 是陷阱、家等地方。返回值为true表示不可通过
	 */
	public boolean checkCollision() {
		int size = father.tileSize;		//获取地图图元大小
		byte [][] map = father.currMap;	//获取地图矩阵
		int row=0;			
		int col=0;		
		//检查左上角
		col = father.ballX/size;			//求出左上角在地图中所占的列数
		row = father.ballY/size;			//求出左上角在地图中所占的行数
		if(col>=0 && col<map[0].length && row>=0&&row<map.length && map[row][col] == 0){
			return true;
		}
		//检查右上角
		col = (father.ballX + size-1)/size;//求出右上角在地图中所占的列数
		row = father.ballY/size;			//求出右上角在地图中所占的行数
		if(col>=0 && col<map[0].length && row>=0&&row<map.length && map[row][col] == 0){
			return true;
		}
		//检查右下角
		col = (father.ballX+size-1)/size;//求出右下角在地图中所占的列数
		row = (father.ballY+size-1)/size;//求出右下角在地图中所占的行数
		if(col>=0 && col<map[0].length && row>=0&&row<map.length && map[row][col] == 0){
			return true;
		}
		//检查左下角
		col = father.ballX/size;		//求出左下角在地图中所占的列数
		row = (father.ballY+size-1)/size;	//求出左下角在地图中所占的行数
		if(col>=0 && col<map[0].length && row>=0&&row<map.length && map[row][col] == 0){
			return true;
		}
		//检查左边界
		if(father.ballX < 0){
			return true;
		}
		//检查上边界
		if(father.ballY < 0){
			return true;
		}
		//检查右边界
		if(father.ballX + size > father.currMap[0].length * size){			//前面 的size指的是小球的尺寸，后面的是指图元尺寸
			return true;
		}
		//检查下边界
		if(father.ballY + size > father.currMap.length*size){
			return true;
		}
		//检查是否遇到其他情况
		col = father.ballX/size;
		row = father.ballY/size;
		if(col == (father.ballX + size-1)/size && row==(father.ballY+size-1)/size){	//判断小球是否正好在某个格子里
			switch(father.currMap[row][col]){
			case 2:		//到家了
				father.status = STATUS_WIN;		//设置游戏状态为STATUS_WIN
				if(father.father.wantSound){	//如有需要播放声音
					try {
						father.father.mpGameWin.start();
					} catch (Exception e) {}
				}
				isGameOn = false;				//暂停GameThread的执行
				if(father.father.level == MAX_LEVEL){	//检查是否是最后一关
					father.status = STATUS_PASS;		//设置游戏状态为STATUS_PASS
				}
				else{
					father.father.level += 1;			//如果不是最后一关，就将关卡加1
				}
				break;
			case 3:		//吃到加命的
				if(father.father.wantSound){			//如有需要播放声音
					try{
						father.father.mpPlusLife.start();
					}
					catch(Exception e){}
				}
				father.ballX = col*size;				//修正小球的X坐标
				father.ballY = row*size;				//修正小球的Y坐标
				father.currMap[row][col] = 1;			//将地图矩阵中该位置改为道路
				father.father.life += 1;				//小球的生命数加1
				break;
			case 4:		//被吃了
				father.status = STATUS_LOSE;			//设置游戏状态为STATUS_LOSE
				if(father.father.wantSound){			//如有需要播放声音
					try {
						father.father.mpBreakOut.start();
					} catch (Exception e) {}
				}
				father.father.life -=1;					//小球的生命数减1
				if(father.father.life <=0){
					father.status=STATUS_OVER;			//如果小球生命耗尽，设置状态位为STATUS_OVER
				}
				else{
					father.ballX = 0;					//复位小球坐标
					father.ballY = 0;					
				}
				break;
			case 5:		//陷阱
				if(father.trapIndex  > 3){				//判断目前陷阱的动画帧
					father.status = STATUS_LOSE;		//设置状态位STATUS_LOSE
					if(father.father.wantSound){		
						try {
							father.father.mpBreakOut.start();
						} catch (Exception e) {}
					}
					father.father.life -= 1;			//生命数减1
					if(father.father.life<=0){			//如果生命耗尽
						father.status = STATUS_OVER;	//设置状态为STATUS_OVER
					}
					else{								//如果还有命，复位接着玩
						father.ballX = 0;
						father.ballY = 0;
					}
				}
				break;
			default:
				break;
			}
		}
		return false;
	}
}