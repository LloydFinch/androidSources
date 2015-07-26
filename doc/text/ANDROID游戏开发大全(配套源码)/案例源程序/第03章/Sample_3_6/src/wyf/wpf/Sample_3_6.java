package wyf.wpf;//声明包语句
import android.app.Activity;//引入相关包
import android.content.BroadcastReceiver;//引入相关包
import android.content.Context;//引入相关包
import android.content.Intent;//引入相关包
import android.content.IntentFilter;//引入相关包
import android.os.Bundle;//引入相关包
import android.view.View;//引入相关包
import android.view.View.OnClickListener;//引入相关包
import android.widget.Button;//引入相关包
import android.widget.TextView;//引入相关包
//继承自Activity的子类
public class Sample_3_6 extends Activity {
	public static final int CMD_STOP_SERVICE = 0;
	Button btnStart;//开始服务Button对象应用
	Button btnStop;//停止服务Button对象应用
	TextView tv;//TextView对象应用
	DataReceiver dataReceiver;//BroadcastReceiver对象
	@Override
    public void onCreate(Bundle savedInstanceState) {//重写onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置显示的屏幕
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);
        tv = (TextView)findViewById(R.id.tv);
        btnStart.setOnClickListener(new OnClickListener() {//为按钮添加点击事件监听		
			@Override
			public void onClick(View v) {//重写onClick方法
				Intent myIntent = new Intent(Sample_3_6.this, wyf.wpf.MyService.class);
				Sample_3_6.this.startService(myIntent);//发送Intent启动Service
			}
		});
        btnStop.setOnClickListener(new OnClickListener() {//为按钮添加点击事件监听	
			@Override
			public void onClick(View v) {//重写onClick方法
				Intent myIntent = new Intent();//创建Intent对象
				myIntent.setAction("wyf.wpf.MyService");
				myIntent.putExtra("cmd", CMD_STOP_SERVICE);
				sendBroadcast(myIntent);//发送广播
			}
		});
    }	
	private class DataReceiver extends BroadcastReceiver{//继承自BroadcastReceiver的子类
		@Override
		public void onReceive(Context context, Intent intent) {//重写onReceive方法
			double data = intent.getDoubleExtra("data", 0);
			tv.setText("Service的数据为:"+data);			
		}		
	}
	@Override
	protected void onStart() {//重写onStart方法
		dataReceiver = new DataReceiver();
		IntentFilter filter = new IntentFilter();//创建IntentFilter对象
		filter.addAction("wyf.wpf.Sample_3_6");
		registerReceiver(dataReceiver, filter);//注册Broadcast Receiver
		super.onStart();
	}
	@Override
	protected void onStop() {//重写onStop方法
		unregisterReceiver(dataReceiver);//取消注册Broadcast Receiver
		super.onStop();
	}
}