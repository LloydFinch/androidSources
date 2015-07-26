package wyf.ytl;

import java.io.Serializable;

/*
 * 该类主要负责定时读取GameView的状态，如果为待命则切换骰子的帧
 * 使其产生动画
 */
public class GameViewThread extends Thread implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8911905788936392333L;
	GameView gv;//游戏视图类的引用
	int sleepSpan = 300;//休眠时间
	int waitSpan = 1500;//空转时的等待时间
	boolean flag;//线程是否执行标志位
	boolean isChanging;//是否需要换骰子动画
	
	public GameViewThread(){}
	
	//构造器
	public GameViewThread(GameView gv){
		super.setName("==GameViewThread");
		this.gv = gv;
		flag = true;
	}
	//线程执行方法
	public void run(){
		while(flag){//线程正在执行
			while(isChanging){//需要换帧
				int diceNumber = gv.diceCount;
				for(int i=0;i<diceNumber;i++){
					gv.diceValue[i] = (gv.diceValue[i]+1)%6;				
				}					
				if(gv.showMiniMap){		//判断是否显示缩略图
					gv.miniMapStartY+=30;
					if(gv.miniMapStartY>=0){//已经移动到位
						gv.miniMapStartY = 0;
					}
				}
				try{
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			try{//不需要换骰子时线程的空转等待时间
				Thread.sleep(waitSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//方法：设置是否需要换骰子
	public void setChanging(boolean isChanging){
		this.isChanging = isChanging;
	}
}