package wyf.wpf;//���������
import android.app.Activity;//������ذ�
import android.content.BroadcastReceiver;//������ذ�
import android.content.Context;//������ذ�
import android.content.Intent;//������ذ�
import android.content.IntentFilter;//������ذ�
import android.os.Bundle;//������ذ�
import android.view.View;//������ذ�
import android.view.View.OnClickListener;//������ذ�
import android.widget.Button;//������ذ�
import android.widget.TextView;//������ذ�
//�̳���Activity������
public class Sample_3_6 extends Activity {
	public static final int CMD_STOP_SERVICE = 0;
	Button btnStart;//��ʼ����Button����Ӧ��
	Button btnStop;//ֹͣ����Button����Ӧ��
	TextView tv;//TextView����Ӧ��
	DataReceiver dataReceiver;//BroadcastReceiver����
	@Override
    public void onCreate(Bundle savedInstanceState) {//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//������ʾ����Ļ
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);
        tv = (TextView)findViewById(R.id.tv);
        btnStart.setOnClickListener(new OnClickListener() {//Ϊ��ť��ӵ���¼�����		
			@Override
			public void onClick(View v) {//��дonClick����
				Intent myIntent = new Intent(Sample_3_6.this, wyf.wpf.MyService.class);
				Sample_3_6.this.startService(myIntent);//����Intent����Service
			}
		});
        btnStop.setOnClickListener(new OnClickListener() {//Ϊ��ť��ӵ���¼�����	
			@Override
			public void onClick(View v) {//��дonClick����
				Intent myIntent = new Intent();//����Intent����
				myIntent.setAction("wyf.wpf.MyService");
				myIntent.putExtra("cmd", CMD_STOP_SERVICE);
				sendBroadcast(myIntent);//���͹㲥
			}
		});
    }	
	private class DataReceiver extends BroadcastReceiver{//�̳���BroadcastReceiver������
		@Override
		public void onReceive(Context context, Intent intent) {//��дonReceive����
			double data = intent.getDoubleExtra("data", 0);
			tv.setText("Service������Ϊ:"+data);			
		}		
	}
	@Override
	protected void onStart() {//��дonStart����
		dataReceiver = new DataReceiver();
		IntentFilter filter = new IntentFilter();//����IntentFilter����
		filter.addAction("wyf.wpf.Sample_3_6");
		registerReceiver(dataReceiver, filter);//ע��Broadcast Receiver
		super.onStart();
	}
	@Override
	protected void onStop() {//��дonStop����
		unregisterReceiver(dataReceiver);//ȡ��ע��Broadcast Receiver
		super.onStop();
	}
}