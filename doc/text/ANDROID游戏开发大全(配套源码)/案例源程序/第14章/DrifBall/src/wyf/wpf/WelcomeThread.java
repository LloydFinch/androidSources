package wyf.wpf;			//声明包语句
/*
 * 该类继承自Thread，为欢迎界面的后台线程，
 * 负责修改相关数据，达到动画效果
 */
public class WelcomeThread extends Thread{
	WelcomeView father;				//WelcomeView引用
	int sleepSpan = 100;			//休眠时间
	boolean flag = false;
	//构造器，接收WelcomeView对象引用
	public WelcomeThread(WelcomeView father){
		this.father = father;
		this.flag = true;
	}
	//线程执行方法
	public void run(){
		while(flag){
			switch(father.status){
			case 2:		//加载状态
			case 0:	//待命状态
				father.backIndex = (father.backIndex+1)%father.bmpBackScreen.length;//修改帧索引
				break;
			case 1:	//按钮按下状态
				father.m.postScale(0.9f, 0.9f);	//对Matrix进行缩放
				father.m.postRotate(30);			//对Matrix进行旋转
				father.backIndex = (father.backIndex+1)%father.bmpBackScreen.length;//修改帧索引
				break;
			}			
			try{
				Thread.sleep(sleepSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}