package wyf.ytl;
/*
 * 需要注意的是，英雄的动画段的改变是每次无级走之前设置 成动态的方向动画段
 * 而当走完一个格子检查是否需要拐弯时，再设置为静态方向的动画段
 */
import static wyf.ytl.ConstantUtil.DOWN;
import static wyf.ytl.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static wyf.ytl.ConstantUtil.GAME_VIEW_SCREEN_ROWS;
import static wyf.ytl.ConstantUtil.HERO_MOVING_SLEEP_SPAN;
import static wyf.ytl.ConstantUtil.HERO_MOVING_SPAN;
import static wyf.ytl.ConstantUtil.HERO_WAIT_SPAN;
import static wyf.ytl.ConstantUtil.LEFT;
import static wyf.ytl.ConstantUtil.MAP_COLS;
import static wyf.ytl.ConstantUtil.MAP_ROWS;
import static wyf.ytl.ConstantUtil.RIGHT;
import static wyf.ytl.ConstantUtil.ROLL_SCREEN_SPACE_DOWN;
import static wyf.ytl.ConstantUtil.ROLL_SCREEN_SPACE_LEFT;
import static wyf.ytl.ConstantUtil.ROLL_SCREEN_SPACE_RIGHT;
import static wyf.ytl.ConstantUtil.ROLL_SCREEN_SPACE_UP;
import static wyf.ytl.ConstantUtil.SCREEN_HEIGHT;
import static wyf.ytl.ConstantUtil.SCREEN_WIDTH;
import static wyf.ytl.ConstantUtil.TILE_SIZE;
import static wyf.ytl.ConstantUtil.UP;

import java.io.Serializable;

public class HeroGoThread extends Thread implements Serializable{
	private static final long serialVersionUID = -5456204729671665857L;//指定版本号
	Hero hero;//英雄的引用
	GameView gv;//游戏试图对象的引用
	boolean flag;//线程是否执行标志位
	boolean isMoving;//英雄是否在走标志位
	int sleepSpan = HERO_MOVING_SLEEP_SPAN;//英雄走路时没一小步的休眠时间
	int waitSpan = HERO_WAIT_SPAN;//英雄不走时线程空转的等待时间
	int steps =0;//记录需要走的步数，即格子数
	int [][] notIn;//不可通过矩阵的引用
	
	public HeroGoThread(){}
	
	//构造器
	public HeroGoThread(GameView gv,Hero hero){
		super.setName("==HeroGoThread");
		this.gv = gv;
		this.hero = hero;
		this.flag = true;
	}
	//线程执行方法
	public void run(){
		while(flag){
			while(isMoving){
				for(int i=0;i<steps;i++){//对每一格子进行无极移动
					int moves = TILE_SIZE/HERO_MOVING_SPAN;//求出这个格子需要几个小步来完成
					int hCol = hero.col;//英雄当前在大地图中的列
					int hRow = hero.row;//英雄当前在大地图中的行
					int destCol=hCol;//目标格子列数
					int destRow=hRow;//目标格子行数
					//先求出目的点的格子行和列,之所以模4是因为动态向上和静止朝上正好差4
					switch(hero.direction%4){
					case 0://向下
						destRow = hRow+1;
						hero.setAnimationDirection(DOWN);//将英雄的方向和动画段设置为动态向下
						break;
					case 1://向左
						destCol = hCol-1;
						hero.setAnimationDirection(LEFT);//将英雄的方向和动画段设置为动态向左
						break;
					case 2://向右
						destCol = hCol+1;
						hero.setAnimationDirection(RIGHT);//将英雄的方向和动画段设置为动态向右
						break;
					case 3://向上
						destRow = hRow-1;
						hero.setAnimationDirection(UP);//将英雄的方向和动画段设置为动态向上
						break;						
					}
					int destX=destCol*TILE_SIZE+TILE_SIZE/2+1;//目的点x坐标，已经转换成中心的了
					int destY=destRow*TILE_SIZE+TILE_SIZE/2+1;//目的点y坐标,已经转换成中心点的了
					int hx = hero.x;
					int hy = hero.y;
					//从下面开始无级从一个格子走到另一个格子
					for(int j=0;j<moves;j++){
						try{//先睡一下
							Thread.sleep(HERO_MOVING_SLEEP_SPAN);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						//计算英雄的x位移
						if(hx<destX){
							hero.x = hx+j*HERO_MOVING_SPAN;
							hero.col = hero.x/TILE_SIZE;//及时更新英雄的行列值
							checkIfRollScreen(hero.direction);
						}
						else if(hx>destX){
							hero.x = hx-j*HERO_MOVING_SPAN;
							hero.col = hero.x/TILE_SIZE;//及时更新英雄的行列值
							checkIfRollScreen(hero.direction);
						}
						//计算英雄的y位移
						if(hy<destY){
							hero.y = hy+j*HERO_MOVING_SPAN;
							hero.row = hero.y/TILE_SIZE;//及时更新英雄的行列值
							checkIfRollScreen(hero.direction);
						}
						else if(hy>destY){
							hero.y = hy-j*HERO_MOVING_SPAN;
							hero.row = hero.y/TILE_SIZE;//及时更新英雄的行列值
							checkIfRollScreen(hero.direction);
						}						
					}
					//修正x和y坐标,修改英雄的占位格子
					hero.x = destX;
					hero.y = destY;
					hero.col = destCol;
					hero.row = destRow;
					//修正offsetX、y
					if(gv.offsetX<HERO_MOVING_SPAN){//舍去
						gv.offsetX = 0;
					}
					else if(gv.offsetX>TILE_SIZE - HERO_MOVING_SPAN){//进位
						if(gv.startCol + GAME_VIEW_SCREEN_COLS < MAP_COLS -1){
							gv.offsetX=0;
							gv.startCol+=1;
						}						
					}
					if(gv.offsetY<HERO_MOVING_SPAN){//舍去
						gv.offsetY = 0;
					}
					else if(gv.offsetY>TILE_SIZE - HERO_MOVING_SPAN){//进位
						if(gv.startRow + GAME_VIEW_SCREEN_ROWS < MAP_ROWS -1){
							gv.startRow+=1;
							gv.offsetY = 0;
						}						
					}					
					hero.direction = checkIfTurn();//检查是否需要拐弯
					
				}//到此走完了指定的格子数，应该检查有没有遇到东西了				
				//先停下来
				this.setMoving(false);//停止走动
				hero.setAnimationDirection(hero.direction%4);//设置动画段为相应的静止态
				//检查停留点有没有可遇的东西
				if(!checkIfMeet()){//如果没遇到，就设置状态待命，骰子继续变换					
					this.gv.setStatus(0);//重新设置GameView的状态0为待命状态
					gv.gvt.setChanging(true);	//继续让骰子变换
				}				
				hero.growStrength(gv.currentSteps);		//增加英雄体力	
			} 
			try{//线程空转等待
				Thread.sleep(waitSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//方法：设置需要走的步数,使用时先设置要走的步数，再设置走路标志位
	public void setSteps(int steps){
		this.steps = steps;
	}
	//方法：设置是否走路标志位
	public void setMoving(boolean isMoving){
		this.isMoving = isMoving;
	}
	/*
	 * 方法：检查是否需要滚屏，以像素为单位
	 */
	public void checkIfRollScreen(int direction){//方向，下0，左1，右2，上3
		int heroX = hero.x;
		int heroY = hero.y;
		int tempOffsetX = gv.offsetX;
		int tempOffsetY = gv.offsetY;
		switch(direction%4){
		case 0://向下检查
			if(heroY - gv.startRow*TILE_SIZE -tempOffsetY + ROLL_SCREEN_SPACE_DOWN >= SCREEN_HEIGHT){//检查是否需要下滚
				if(gv.startRow + GAME_VIEW_SCREEN_ROWS < MAP_ROWS -1){//可以接受进位就加
					gv.offsetY += HERO_MOVING_SPAN;
					if(gv.offsetY > TILE_SIZE){//需要进位
						gv.startRow += 1;
						gv.offsetY = 0;
					}
				}
			}
			break;
		case 1://向左检查
			if(heroX - gv.startCol*TILE_SIZE - tempOffsetX <= ROLL_SCREEN_SPACE_LEFT){//检查是否需要左滚屏
				if(gv.startCol > 0){//startCol还够减
					gv.offsetX -= HERO_MOVING_SPAN;//向左偏移英雄步进的像素数
					if(gv.offsetX < 0){					
						gv.startCol -=1;//
						gv.offsetX = TILE_SIZE-HERO_MOVING_SPAN;//有待商议		
					}
				}
				else if(gv.offsetX > HERO_MOVING_SPAN){//如果格子数不够减，但是偏移量还有
					gv.offsetX -= HERO_MOVING_SPAN;//向左偏移英雄步进的像素数
				}
			}			
			break;
		case 2://向右检查
			if(heroX - gv.startCol*TILE_SIZE - tempOffsetX + ROLL_SCREEN_SPACE_RIGHT >= SCREEN_WIDTH){//检查是否需要右滚屏
				if(gv.startCol + GAME_VIEW_SCREEN_COLS < MAP_COLS -1){//startCol还能加
					gv.offsetX += HERO_MOVING_SPAN;//向右偏移英雄步进的像素数
					if(gv.offsetX > TILE_SIZE){//需要进位
						gv.startCol += 1;
						gv.offsetX = 0;/////////有待商议
					}
				}
			}
			break;
		case 3://向上检查
			if(heroY - gv.startRow*TILE_SIZE - tempOffsetY <= ROLL_SCREEN_SPACE_UP){//检查是否需要左滚屏
				if(gv.startRow >0){//startRow 还能借
					gv.offsetY -= HERO_MOVING_SPAN;
					if(gv.offsetY < 0){
						gv.startRow -=1;;
						gv.offsetY = TILE_SIZE-HERO_MOVING_SPAN;
					}
				}
				else if(gv.offsetY > HERO_MOVING_SPAN){//格子数不够减了，但是偏移量还有
					gv.offsetY -= HERO_MOVING_SPAN;
				}
			}
			break;
		}
	}
	/*
	 * 方法：检查是否需要拐弯,根据英雄的运动方向检查左右和前方3个格子是否可通过
	 * 如果多于一个格子可通过，则随机选取一个。最后返回一个方向赋值给英雄
	 */	
	public int checkIfTurn(){
		int [] directions = new int[3];//存放可选方向，最多3个，分别代表相对于当前方向的左、右和前
		int choices=0;//记录可选方向的个数
		int col = hero.col;
		int row = hero.row;
		switch(hero.direction%4){
		case 0://向下
			if(gv.notInMatrix[row][col-1] == 0){//检查左边是否可通过
				directions[choices++] = 1;//设置为静态向左
			}
			if(gv.notInMatrix[row][col+1] == 0){//检查右边是否可通过
				directions[choices++] = 2;//设置为静态向右
			}
			if(gv.notInMatrix[row+1][col] == 0){//检查下边是否可通过
				directions[choices++] = 4;//设置为静态向下
			}
			break;
		case 1://向左
			if(gv.notInMatrix[row][col-1] == 0){//检查左边是否可通过
				directions[choices++] = 1;//设置为静态向左
			}
			if(gv.notInMatrix[row-1][col] == 0){//检查上边是否可通过
				directions[choices++] = 3;//设置为静态向上
			}
			if(gv.notInMatrix[row+1][col] == 0){//检查下边是否可通过
				directions[choices++] = 4;//设置为静态向下
			}
			break;
		case 2://向右
			if(gv.notInMatrix[row][col+1] == 0){//检查右边是否可通过
				directions[choices++] = 2;//设置为静态向右
			}
			if(gv.notInMatrix[row-1][col] == 0){//检查上边是否可通过
				directions[choices++] = 3;//设置为静态向上
			}
			if(gv.notInMatrix[row+1][col] == 0){//检查下边是否可通过
				directions[choices++] = 4;//设置为静态向下
			}
			break;
		case 3://向上
			if(gv.notInMatrix[row][col-1] == 0){//检查左边是否可通过
				directions[choices++] = 1;//设置为静态向左
			}
			if(gv.notInMatrix[row][col+1] == 0){//检查右边是否可通过
				directions[choices++] = 2;//设置为静态向右
			}
			if(gv.notInMatrix[row-1][col] == 0){//检查上边是否可通过
				directions[choices++] = 3;//设置为静态向上
			}
			break;						
		}
		return directions[(int)(Math.random()*100)%choices];//从可选方向中随机选取一个返回
	}
	/*
	 * 该方法检测英雄停留的位置左右(相对于英雄当前方向的左右)有没有什么可遇的东西
	 * 如渔场，森林等等
	 */
	public boolean checkIfMeet(){
		MyMeetableDrawable mmd = gv.meetableChecker.check(hero);
		if(mmd != null && mmd!=gv.previousDrawable){//如果碰到了可遇物，且该可遇物并不是上一次同样的那个
			gv.setOnTouchListener(mmd);
			gv.currentDrawable = mmd;
			gv.previousDrawable = mmd;//记录为前一个，以防下次走一步遇到了同样的那个
			if(gv.activity.isEnvironmentSound){
				gv.playSound(1, 0);
			}
			return true;
		}
		gv.previousDrawable = mmd;
		return false;
	}
}