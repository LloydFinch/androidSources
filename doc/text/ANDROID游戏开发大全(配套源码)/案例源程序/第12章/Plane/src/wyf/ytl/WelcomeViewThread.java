package wyf.ytl;
/**
 * 
 * 该类是为WelcomeView服务的线程类，
 * 通过对WelcomeView中status的判断执行不同的操作
 * 与WelcomeView共同实现动画效果
 * 主要的操作是换帧，与修改图片坐标
 *
 */
public class WelcomeViewThread extends Thread{
	private boolean flag = true;//循环标记位 
	private int span = 100;//循环睡眠时间
	WelcomeView welcomeView;
	public WelcomeViewThread(WelcomeView welcomeView){//构造器
		this.welcomeView = welcomeView;
	}
	public void setFlag(boolean flag){//设置循环标记位 
		this.flag = flag;
	}
	public void run(){
		while(flag){
			if(welcomeView.status == 1){
				welcomeView.backgroundY += 8;
				if(welcomeView.backgroundY>80){
					welcomeView.status = 2;
				}
			}
			else if(welcomeView.status == 2){
				welcomeView.k++;
				if(welcomeView.k == 11){
					welcomeView.status = 3;
				}
			}
			else if(welcomeView.status == 3){
				welcomeView.background2Y -= 2;
				welcomeView.alpha -= 2;
				if(welcomeView.alpha < 125){
					welcomeView.alpha = 125;
				}
				if(welcomeView.background2Y < -90){
					welcomeView.status = 4;
				}
			}
			try{
				Thread.sleep(span);//睡眠制定毫秒数 
			}
			catch(Exception e){
				e.printStackTrace();//打印异常信息
			}
		}
	}
}