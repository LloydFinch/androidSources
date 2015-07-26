package wyf.ytl;
/**
 * 
 * 该类为背景滚动、物品出现的类
 *
 */
public class GameViewBackGroundThread extends Thread{
	private int sleepSpan = 100;//睡眠的毫秒数
	private int span = 3;//图片移动的步长
	private boolean flag = true;//循环标志位
	GameView gameView;//GameView的引用 	
	long touchTime = 0;//当前所到的时间
	public GameViewBackGroundThread(GameView gameView){//构造器
		this.gameView = gameView;
	}
	public void setFlag(boolean flag){//设置标记位
		this.flag = flag;
	}
	public void run(){
		while(flag){
			if(gameView.status == 1){//游戏中时
				gameView.backGroundIX -= span;
				if(gameView.backGroundIX <-ConstantUtil.pictureWidth){
					gameView.i = (gameView.i+1)%ConstantUtil.pictureCount;
					gameView.backGroundIX+=ConstantUtil.pictureWidth;
				}
				gameView.cloudX -= span;//移动云彩
				if(gameView.cloudX<-1000){
					gameView.cloudX = 1000;
				}
				touchTime++;//时间自加
				try{
					for(EnemyPlane ep : gameView.enemyPlanes){//到时间出现敌机
						if(ep.touchPoint == touchTime){ 
							ep.status = true;
							if(ep.type == 3){//到关口了
								gameView.status = 3;
							}  
						} 
					}
					for(Life l : gameView.lifes){//到时间出现血块
						if(l.touchPoint == touchTime){
							l.status = true;
						}
					}
					for(ChangeBullet cb : gameView.changeBollets){//到时间出现吃了改变枪的物体
						if(cb.touchPoint == touchTime){
							cb.status = true;
						}
					}
				}catch(Exception e){//捕获异常
					e.printStackTrace();//打印异常信息
				}
				if(touchTime == 641){//到关口时
					this.flag = false;
				}
			}try{
				Thread.sleep(sleepSpan);//睡眠
			}catch(Exception e){//捕获异常
				e.printStackTrace();//打印异常信息
			}
		}
	}
}