package wyf.wpf;
/*
 * 该类继承自Thread类，是Cannon的后台线程主要功能是监视小球，
 * 一旦小球进入大炮的射程，就像小球发射一发炮弹
 */
public class CannonThread extends Thread{
	GameView father;			//GameView类引用
	int sleepSpan = 2000;		//休眠时间
	boolean flag = false;		//主循环变量
	boolean isGameOn = false;	//游戏是否进行中
	//构造器
	public CannonThread(GameView father){
		this.father = father;
		if(father.alCannon.size()!=0){		//检 查Cannono对象集合是否为空，设置相应标志位
			flag = true;
			isGameOn = true;
		}
	}
	//线程的执行方法
	public void run(){
		while(flag){
			while(isGameOn){
				//遍历Cannon对象集合
				for(Cannon c:father.alCannon){
					//求出大炮和小球之间的距离
					int distance = Math.abs(c.col*father.tileSize-father.ballX) + Math.abs(c.row*father.tileSize-father.ballY);
					if(distance <= c.range){			//如果小球处于射程之内
						Missile m = c.fire();			//创建炮弹对象
						father.alMissile.add(m);		//将炮弹对象添加到炮弹对象的集合中
					}
				}
				try{		//线程休眠
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}				
			}
			try{		//外层循环休眠
				Thread.sleep(1000);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}