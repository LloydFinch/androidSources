package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class Sample_9_3 extends Activity implements OnClickListener{
	TextView myTextView1;//TextView������
	TextView myTextView2;//TextView������
	Button myButton;//Button������
	double[] values = new double[]{0,0.707,1,0.707,0,-0.707,-1,-0.707};//���ұ�����
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myTextView1 = (TextView) findViewById(R.id.myEditText1);//�õ�myTextView1������
        myTextView2 = (TextView) findViewById(R.id.myEditText2);//�õ�myEditText2������
        myButton = (Button) findViewById(R.id.myButton);//�õ�myButton������
        myButton.setOnClickListener(this);//��Ӽ���
    }
	public void onClick(View v) {
		if(v == myButton){//���¼��㰴ť
			int temp = 0;//�û�����Ƕ�
			int index = 0;
			try{
				temp =  Integer.parseInt(myTextView1.getText().toString());//��myTextView1�е�ֵת��������
			}
			catch(Exception e){}//�������ת����������ת��
			temp = temp%360;//���ǶȻ����0~360��
			index = temp/45;//�õ�����ֵ
			myTextView2.setText("������Ϊ��"+values[index]);
		}
	}
    
}