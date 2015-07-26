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
public class Sample_8_2 extends Activity implements OnClickListener{
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
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
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
enum DogState {//表示状态的枚举类型
	HAPPY_STATE,    		//高兴状态
	COMMON_STATE,   		//普通状态
	AWAY_STATE;				//出走状态
}
enum MasterAction {//表示主人动作的枚举
	BATH_ACTION,		//洗澡
	ENGAGE_ACTION,		//逗弄
	FIND_ACTION,		//寻找
	ALONE_ACTION;		//无人搭理
}