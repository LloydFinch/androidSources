package wyf.ytl;
public class ActionThread extends Thread{
	private int sleepSpan = 5000;//睡眠的毫秒数
	boolean flag = true;//循环标记位
	Sample_8_2 activity;//activity的引用
	public ActionThread(Sample_8_2 activity){//构造器
		this.activity = activity;
	}	
	public void run(){//重写的run方法
		while(flag){//循环
			try{
				Thread.sleep(sleepSpan);//睡眠指定毫秒数
			}
			catch(Exception e ){
				e.printStackTrace();//打印异常信息
			}
			activity.myHandler.sendEmptyMessage(1);//想activity发生handler消息
		}
	}
}