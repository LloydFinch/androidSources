package wyf.ytl;
public class GameDog {
	Sample_8_2 activity = null;//activity的引用
	private DogState currentState=DogState.COMMON_STATE;//设置宠物的初始当前状态为普通状态
	public GameDog(Sample_8_2 activity){//构造器
		this.activity = activity;
	}
	public boolean updateState(MasterAction ma){//接收条件，更新状态的方法
		boolean result=true;
		switch(currentState){
			case HAPPY_STATE://当前为高兴状态
			    switch(ma){
			    	case ALONE_ACTION://超过指定时间无人搭理
			    	   currentState=DogState.COMMON_STATE;//切换状态
			    	   activity.myImageView.setImageResource(R.drawable.common);//换图
			    	   activity.myTextView.setText("状态：普通");
			    	break;
			    	default:
			    	   result=false;//返回false表示状态切换出错
			    }
			break;
			case COMMON_STATE://当前为普通状态
			    switch(ma){
			    	case ALONE_ACTION://超过指定时间无人搭理
			    	   currentState=DogState.AWAY_STATE;//切换状态
			    	   activity.myImageView.setImageResource(R.drawable.away);//换图
			    	   activity.myTextView.setText("状态：出走");
			    	break;
			    	case BATH_ACTION:  //洗澡
			    	case ENGAGE_ACTION://逗弄
			    	   currentState=DogState.HAPPY_STATE;//切换状态
			    	   activity.myImageView.setImageResource(R.drawable.happy);//换图
			    	   activity.myTextView.setText("状态：高兴");
			    	break;
			    	default:
			    	   result=false;//返回false表示状态切换出错
			    }			
			break;
			case AWAY_STATE://当前为出走状态
			    switch(ma){
			    	case FIND_ACTION://寻找
			    	   currentState=DogState.COMMON_STATE;//切换状态
			    	   activity.myImageView.setImageResource(R.drawable.common);//换图
			    	   activity.myTextView.setText("状态：普通");
			    	break;
			    	default:
			    	   result=false;//返回false表示状态切换出错
			    }			
			break;
		}
		return result;//返回true表示状态切换成功
	}
}