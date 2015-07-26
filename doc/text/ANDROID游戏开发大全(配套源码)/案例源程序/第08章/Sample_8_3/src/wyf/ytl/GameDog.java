package wyf.ytl;
public class GameDog {
	Sample_8_3 activity = null;		//activity的引用
	private State currentState;		//设置宠物的初始当前状态为普通状态
	public GameDog(Sample_8_3 activity){//构造器
		this.activity = activity;
		currentState=new CommonState(activity);		//宠物的初始当前状态
	}
	public boolean updateState(MasterAction ma){	//接收条件，更新状态的方法
		State beforeState=currentState;
		currentState=currentState.toNextState(ma);
		return !(beforeState==currentState);	//返回true表示状态切换成功
	}
}