package wyf.wpf;//声明包语句
import android.app.Service;//引入相关包
import android.content.BroadcastReceiver;//引入相关包
import android.content.Context;//引入相关包
import android.content.Intent;//引入相关包
import android.content.IntentFilter;//引入相关包
import android.os.IBinder;//引入相关包
//继承自Service的子类
public class MyService extends Service{
	CommandReceiver cmdReceiver;
	boolean flag;
	@Override
	public void onCreate() {//重写onCreate方法
		flag = true;
		cmdReceiver = new CommandReceiver();
		super.onCreate();
	}
	@Override
	public IBinder onBind(Intent intent) {//重写onBind方法
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {//重写onStartCommand方法
		IntentFilter filter = new IntentFilter();//创建IntentFilter对象
		filter.addAction("wyf.wpf.MyService");
		registerReceiver(cmdReceiver, filter);//注册Broadcast Receiver
		doJob();//调用方法启动线程
		return super.onStartCommand(intent, flags, startId);
	}
	//方法：
	public void doJob(){
		new Thread(){
			public void run(){
				while(flag){
					try{//睡眠一段时间
						Thread.sleep(1000);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					Intent intent = new Intent();//创建Intent对象
					intent.setAction("wyf.wpf.Sample_3_6");
					intent.putExtra("data", Math.random());
					sendBroadcast(intent);//发送广播
				}				
			}
			
		}.start();
	}	
	private class CommandReceiver extends BroadcastReceiver{//继承自BroadcastReceiver的子类
		@Override
		public void onReceive(Context context, Intent intent) {//重写onReceive方法
			int cmd = intent.getIntExtra("cmd", -1);//获取Extra信息
			if(cmd == Sample_3_6.CMD_STOP_SERVICE){//如果发来的消息是停止服务				
				flag = false;//停止线程
				stopSelf();//停止服务
			}
		}		
	}
	@Override
	public void onDestroy() {//重写onDestroy方法
		this.unregisterReceiver(cmdReceiver);//取消注册的CommandReceiver
		super.onDestroy();
	}	
}