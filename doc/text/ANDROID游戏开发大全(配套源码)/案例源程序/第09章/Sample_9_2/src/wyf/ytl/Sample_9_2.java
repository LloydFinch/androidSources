package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class Sample_9_2 extends Activity implements OnClickListener{
	TextView myTextView1;//��ʾ�˿��Ƶ�
	TextView myTextView2;//��ʾ��Ӯ�ĸ��ʵ�
	Button button;//���ư�ť
	String[] puKePai = new String[]{//��ʾ�˿��Ƶ�����
		"2","3","4","5","6","7","8","9","10","J","Q","K","A"
	};
	int[] liShuDu = new int[]{//��ʾʤ����������
			0,8,16,24,32,41,49,
			58,66,75,84,91,100
		};	
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate�ص�����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myTextView1 = (TextView) this.findViewById(R.id.myTextView1);//�õ��ı��ؼ�������
        myTextView2 = (TextView) this.findViewById(R.id.myTextView2);//�õ��ı��ؼ�������
        button = (Button) this.findViewById(R.id.button);//�õ���ť������
        button.setOnClickListener(this);
    }

	public void onClick(View v) {
		if(v == button){//����˷��ư�ť
			int temp = (int)(Math.random()*puKePai.length);//�õ������
			myTextView1.setText("������Ϊ��"+puKePai[temp]);//ȡ�˿���
			myTextView2.setText("Ӯ�ĸ���Ϊ��"+liShuDu[temp]+"%");//ȡ������
		}
	}
}