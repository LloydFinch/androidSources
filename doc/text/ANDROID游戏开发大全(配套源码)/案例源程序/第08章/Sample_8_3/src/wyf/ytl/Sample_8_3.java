package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class Sample_8_3 extends Activity implements OnClickListener{
	ImageView myImageView = null;//ImageView控件的引用
	TextView myTextView = null;//TextView控件的引用
	Button bath = null;//洗澡按钮
	Button engage = null;//逗弄按钮
	Button find = null;//寻找按钮 
	GameDog gameDog;//GameDog的引用
	ActionThread actionThread;//后台线程的引用
	Handler myHandler = new Handler(){//用来更新UI线程中的控件
        public void handleMessage(Message msg) {
        	switch(msg.what){
        		case 1://为后台线程发来的消息
        			gameDog.updateState(MasterAction.ALONE_ACTION);//长时间无人搭理
        			break;
        	}
        }
	};	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);
        myImageView = (ImageView) this.findViewById(R.id.myImageView);//得到myImageView的引用
        myTextView = (TextView) this.findViewById(R.id.myTextView);//得到myTextView的引用
        bath = (Button) this.findViewById(R.id.bath);//得到bath的引用
        engage = (Button) this.findViewById(R.id.engage);//得到engage的引用
        find = (Button) this.findViewById(R.id.find);//得到find的引用
        bath.setOnClickListener(this);//添加监听
        engage.setOnClickListener(this);//添加监听
        find.setOnClickListener(this);//添加监听
        gameDog = new GameDog(this);
        actionThread = new ActionThread(this);
        actionThread.start();//启动后台线程        
    }
	public void onClick(View v) {//实现监听器中的方法 
		if(v == bath){//按下的是洗澡按钮
			gameDog.updateState(MasterAction.BATH_ACTION);
		}
		else if(v == engage){//按下的是逗弄按钮
			gameDog.updateState(MasterAction.ENGAGE_ACTION);
		}
		else if(v == find){//按下的是查找按钮
			gameDog.updateState(MasterAction.FIND_ACTION);
		}
	}
}
enum MasterAction {//表示主人动作的枚举
	BATH_ACTION,		//洗澡
	ENGAGE_ACTION,		//逗弄
	FIND_ACTION,		//寻找
	ALONE_ACTION;		//无人搭理
}
abstract class State {  //所有状态类型的基类
	public Sample_8_3 activity;
	public State(Sample_8_3 activity){//构造器
		this.activity = activity;
	}
	public abstract State toNextState(MasterAction ma);	
}
class HappyState extends State{ //表示高兴状态的类
	public HappyState(Sample_8_3 activity) {//构造器
		super(activity);
	}
	public State toNextState(MasterAction ma){//去下一状态
		State result=this;		
		switch(ma){
	    	case ALONE_ACTION://超过指定时间无人搭理
	    	   result= new CommonState(activity);
	    	   activity.myImageView.setImageResource(R.drawable.common);//换图
	    	   activity.myTextView.setText("状态：普通");	    	   
	    	break;	    	
	    }				
		return result; 
	}
}
class CommonState extends State{ //表示普通状态的类
	public CommonState(Sample_8_3 activity) {//构造器
		super(activity);
	}
	public State toNextState(MasterAction ma){//去下一状态
		State result=this;		
	    switch(ma){
	    	case ALONE_ACTION://超过指定时间无人搭理
	    	   result= new AwayState(activity);
	    	   activity.myImageView.setImageResource(R.drawable.away);//换图
	    	   activity.myTextView.setText("状态：出走");	    	   
	    	break;
	    	case BATH_ACTION:  //洗澡
	    	case ENGAGE_ACTION://逗弄
	    	   result= new HappyState(activity);
	    	   activity.myImageView.setImageResource(R.drawable.happy);//换图
	    	   activity.myTextView.setText("状态：高兴");	    	   
	    	break;
	    }
		return result; 
	}
}
class AwayState extends State {//表示出走状态的类
	public AwayState(Sample_8_3 activity) {//构造器
		super(activity);
	}
	public State toNextState(MasterAction ma){//去下一状态
		State result=this;		
	    switch(ma){
	    	case FIND_ACTION://寻找
	    	   result=new CommonState(activity);
	    	   activity.myImageView.setImageResource(R.drawable.common);//换图
	    	   activity.myTextView.setText("状态：普通");	    	   
	    	break;
	    }							
		return result; 
	}
}