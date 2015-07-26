package wyf.ytl;

import java.util.ArrayList;
/**
 * 
 * 除了我方飞机外所有移动物的移动线程
 *
 */
public class MoveThread extends Thread{
	private int sleepSpan = 10;//睡眠的毫秒数
	private boolean flag = true;//循环标志位 
	GameView gameView;//GameView的引用
	ArrayList<Bullet> deleteBollet = new ArrayList<Bullet>();
	ArrayList<EnemyPlane> deleteEnemy = new ArrayList<EnemyPlane>();
	ArrayList<Life> deleteLife = new ArrayList<Life>();
	ArrayList<ChangeBullet> deleteChangeBollets = new ArrayList<ChangeBullet>();
	private int countBolletMove = 0;//我方子弹移动的计数器
	private int countMyBolletN = 3;//每多少次循环移动一下
	private int countEnemyMove = 0;//敌机移动计数器
	private int countEnemyN = 8;//每多少次循环移动一下
	private int countEnemyFire = 0;//敌机发子弹计数器
	private int countEnemyFireN = 20;//每多少次循环大一发子弹 
	private int countEnemyBolletMove = 0;//敌方子弹移动的计数器
	private int countEnemyBolletN = 3;//每多少次循环移动一下 
	public MoveThread(GameView gameView){//构造器
		this.gameView = gameView;
	}
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	public void run(){
		while(flag){
			if(countBolletMove == 0){//我方子弹移动
				try{
					for(Bullet b : gameView.goodBollets){
						b.move();
						if(b.x<0 || b.x>ConstantUtil.screenWidth
								|| b.y<0 || b.y>ConstantUtil.screenHeight){
							deleteBollet.add(b);//当出了屏幕范围后删除子弹
						}
						else{//在屏幕范围内
							for(EnemyPlane ep : gameView.enemyPlanes){
								if(ep.status == true){
									if(ep.contain(b,gameView)){//打中敌机
										Explode e = new Explode(ep.x, ep.y, gameView);
										gameView.explodeList.add(e);
										deleteBollet.add(b);
									}
								}
							}
						}
					}	
					gameView.goodBollets.removeAll(deleteBollet);
					deleteBollet.clear();
				}catch(Exception e){}				 
			}
			if(countEnemyMove == 0){//敌机移动、血块移动、吃了变枪的物体的移动
				try{
					for(EnemyPlane ep : gameView.enemyPlanes){
						if(ep.status == true){
							ep.move();
							if(ep.getX()<-ep.bitmap.getWidth() || ep.getX()>ConstantUtil.screenWidth
									|| ep.getY()<-ep.bitmap.getHeight() || ep.getY()>ConstantUtil.screenHeight){
								deleteEnemy.add(ep);
							}
							else{
								if(gameView.plane.contain(ep)){
									Explode e = new Explode(ep.x, ep.y, gameView);
									gameView.explodeList.add(e);
									ep.life--;
									if(ep.life <=0){
										deleteEnemy.add(ep);
									}
								}
							}
						}
					}
					gameView.enemyPlanes.removeAll(deleteEnemy);
					deleteEnemy.clear();
				}catch(Exception e){}
				try{//移动血块
					for(Life l : gameView.lifes){
						if(l.status == true){
							l.move();
							if(l.x<-l.bitmap.getWidth() || l.x>ConstantUtil.screenWidth
									|| l.y<-l.bitmap.getHeight() || l.y>ConstantUtil.screenHeight){
								deleteLife.add(l);
							}
							else{
								if(gameView.plane.contain(l)){
									deleteLife.add(l);
								}
							}
						}
					}
					gameView.lifes.removeAll(deleteLife);
					deleteLife.clear();
				}catch(Exception e){}
				try{//移动吃了改变枪的物体
					for(ChangeBullet cb : gameView.changeBollets){
						if(cb.status == true){
							cb.move();
							if(cb.x<-cb.bitmap.getWidth() || cb.x>ConstantUtil.screenWidth
									|| cb.y<-cb.bitmap.getHeight() || cb.y>ConstantUtil.screenHeight){
								deleteChangeBollets.add(cb);
							}
							else{
								if(gameView.plane.contain(cb)){
									deleteChangeBollets.add(cb);
								}
							}
						}
					}
					gameView.changeBollets.removeAll(deleteChangeBollets);
					deleteChangeBollets.clear();
				}catch(Exception e){}
			}
			if(countEnemyFire == 0){//敌机打子弹
				try{
					for(EnemyPlane ep : gameView.enemyPlanes){
						if(ep.status == true){
							ep.fire(gameView);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(countEnemyBolletMove == 0){//敌方子弹移动
				try{
					for(Bullet b : gameView.badBollets){
						b.move();
						if(b.x<0 || b.x>ConstantUtil.screenWidth
								|| b.y<0 || b.y>ConstantUtil.screenHeight){
							deleteBollet.add(b);
						}
						else{
							if(gameView.plane.contain(b)){//碰撞检测是否打中我方飞机
								Explode e = new Explode(b.x, b.y, gameView);
								gameView.explodeList.add(e);
								deleteBollet.add(b);//打中后删除子弹
							}
						}
					}
					gameView.badBollets.removeAll(deleteBollet);
					deleteBollet.clear();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			countBolletMove = (countBolletMove+1)%countMyBolletN;//循环的自加
			countEnemyMove = (countEnemyMove+1)%countEnemyN;//循环的自加
			countEnemyFire = (countEnemyFire+1)%countEnemyFireN;//循环的自加
			countEnemyBolletMove = (countEnemyBolletMove+1)%countEnemyBolletN;//循环的自加
			try{//睡眠休息
				Thread.sleep(sleepSpan);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}