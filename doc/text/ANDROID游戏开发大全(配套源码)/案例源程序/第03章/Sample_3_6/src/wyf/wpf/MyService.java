package wyf.wpf;//���������
import android.app.Service;//������ذ�
import android.content.BroadcastReceiver;//������ذ�
import android.content.Context;//������ذ�
import android.content.Intent;//������ذ�
import android.content.IntentFilter;//������ذ�
import android.os.IBinder;//������ذ�
//�̳���Service������
public class MyService extends Service{
	CommandReceiver cmdReceiver;
	boolean flag;
	@Override
	public void onCreate() {//��дonCreate����
		flag = true;
		cmdReceiver = new CommandReceiver();
		super.onCreate();
	}
	@Override
	public IBinder onBind(Intent intent) {//��дonBind����
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {//��дonStartCommand����
		IntentFilter filter = new IntentFilter();//����IntentFilter����
		filter.addAction("wyf.wpf.MyService");
		registerReceiver(cmdReceiver, filter);//ע��Broadcast Receiver
		doJob();//���÷��������߳�
		return super.onStartCommand(intent, flags, startId);
	}
	//������
	public void doJob(){
		new Thread(){
			public void run(){
				while(flag){
					try{//˯��һ��ʱ��
						Thread.sleep(1000);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					Intent intent = new Intent();//����Intent����
					intent.setAction("wyf.wpf.Sample_3_6");
					intent.putExtra("data", Math.random());
					sendBroadcast(intent);//���͹㲥
				}				
			}
			
		}.start();
	}	
	private class CommandReceiver extends BroadcastReceiver{//�̳���BroadcastReceiver������
		@Override
		public void onReceive(Context context, Intent intent) {//��дonReceive����
			int cmd = intent.getIntExtra("cmd", -1);//��ȡExtra��Ϣ
			if(cmd == Sample_3_6.CMD_STOP_SERVICE){//�����������Ϣ��ֹͣ����				
				flag = false;//ֹͣ�߳�
				stopSelf();//ֹͣ����
			}
		}		
	}
	@Override
	public void onDestroy() {//��дonDestroy����
		this.unregisterReceiver(cmdReceiver);//ȡ��ע���CommandReceiver
		super.onDestroy();
	}	
}