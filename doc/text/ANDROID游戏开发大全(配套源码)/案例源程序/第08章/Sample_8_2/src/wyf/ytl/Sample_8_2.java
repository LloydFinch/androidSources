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
	ImageView myImageView = null;//ImageView�ؼ�������
	TextView myTextView = null;//TextView�ؼ�������
	Button bath = null;//ϴ�谴ť
	Button engage = null;//��Ū��ť
	Button find = null;//Ѱ�Ұ�ť 
	GameDog gameDog;//GameDog������
	ActionThread actionThread;//��̨�̵߳�����
	Handler myHandler = new Handler(){//��������UI�߳��еĿؼ�
        public void handleMessage(Message msg) {
        	switch(msg.what){
        		case 1://Ϊ��̨�̷߳�������Ϣ
        			gameDog.updateState(MasterAction.ALONE_ACTION);//��ʱ�����˴���
        			break;
        	}
        }
	};
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myImageView = (ImageView) this.findViewById(R.id.myImageView);//�õ�myImageView������
        myTextView = (TextView) this.findViewById(R.id.myTextView);//�õ�myTextView������
        bath = (Button) this.findViewById(R.id.bath);//�õ�bath������
        engage = (Button) this.findViewById(R.id.engage);//�õ�engage������
        find = (Button) this.findViewById(R.id.find);//�õ�find������
        bath.setOnClickListener(this);//��Ӽ���
        engage.setOnClickListener(this);//��Ӽ���
        find.setOnClickListener(this);//��Ӽ���
        gameDog = new GameDog(this);
        actionThread = new ActionThread(this);
        actionThread.start();//������̨�߳�
    }
	public void onClick(View v) {//ʵ�ּ������еķ��� 
		if(v == bath){//���µ���ϴ�谴ť
			gameDog.updateState(MasterAction.BATH_ACTION);
		}
		else if(v == engage){//���µ��Ƕ�Ū��ť
			gameDog.updateState(MasterAction.ENGAGE_ACTION);
		}
		else if(v == find){//���µ��ǲ��Ұ�ť
			gameDog.updateState(MasterAction.FIND_ACTION);
		}
	}
}
enum DogState {//��ʾ״̬��ö������
	HAPPY_STATE,    		//����״̬
	COMMON_STATE,   		//��ͨ״̬
	AWAY_STATE;				//����״̬
}
enum MasterAction {//��ʾ���˶�����ö��
	BATH_ACTION,		//ϴ��
	ENGAGE_ACTION,		//��Ū
	FIND_ACTION,		//Ѱ��
	ALONE_ACTION;		//���˴���
}