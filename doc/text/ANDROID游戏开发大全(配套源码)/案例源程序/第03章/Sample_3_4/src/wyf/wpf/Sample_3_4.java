package wyf.wpf;//������
import android.app.Activity;//������ذ�
import android.os.Bundle;//������ذ�
import android.os.Handler;//������ذ�
import android.os.Message;//������ذ�
import android.view.View;//������ذ�
import android.widget.Button;//������ذ�
import android.widget.TextView;//������ذ�
//�̳���Activity������
public class Sample_3_4 extends Activity {
	public static final int UPDATE_DATA = 0;//�����������������
	public static final int UPDATE_COMPLETED = 1;//�����������������
	TextView tv;//TextView���������
	Button btnStart;//Button���������
	//���̵߳�Handler����
	Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {//��д������Ϣ����
			switch(msg.what){//�ж���Ϣ���
			case UPDATE_DATA://��ϢΪ���µ�����
				tv.setText("���ڸ��������̵߳�����:"+msg.arg1+"%...");
				break;
			case UPDATE_COMPLETED://��ϢΪ�������
				tv.setText("����������̵߳ĸ������ݣ�");
				break;
			}
		}		
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ��ĻΪR.layout.main�����ļ�
        tv = (TextView)findViewById(R.id.tv);//�����Ļ��TextView��������
        btnStart = (Button)findViewById(R.id.btnStart);//�����Ļ��Button��������
        btnStart.setOnClickListener(new View.OnClickListener() {//ΪButton��ӵ���¼�������			
			@Override
			public void onClick(View v) {
				new Thread(){//����һ�����߳�
					public void run(){
						for(int i=0;i<100;i++){
							try{//˯��һ��ʱ��
								Thread.sleep(150);
							}
							catch(Exception e){
								e.printStackTrace();
							}
							Message m = myHandler.obtainMessage();//����Message����
							m.what = UPDATE_DATA;//Ϊwhat�ֶθ�ֵ
							m.arg1=i+1;//Ϊarg1�ֶθ�ֵ
							myHandler.sendMessage(m);//����Message����
						}
						myHandler.sendEmptyMessage(UPDATE_COMPLETED);//�������������Ϣ
					}
				}.start();
			}
		});
    }
}