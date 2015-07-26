package wyf.ytl;
import java.io.DataInputStream;//����DataInputStream��
import java.io.DataOutputStream;//����DataOutputStream��
import java.net.Socket;//����Socket��
import android.app.Activity;//����Activity��
import android.os.Bundle;//����Bundle��
import android.view.View;//����View��
import android.view.View.OnClickListener;//����OnClickListener��
import android.widget.Button;//����Button��
import android.widget.EditText;//����EditText��
import android.widget.TextView;//����TextView��
public class Sample_5_1 extends Activity implements OnClickListener{
	Button button1;//��ť������
	EditText editText;//�ı��������
	TextView textView;//�ı�������
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate�ص�����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ���û�����
        button1 = (Button) findViewById(R.id.button1);//�õ������еİ�ť����
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button1.setOnClickListener(this);//��Ӽ���
    }
	public void onClick(View v) {
		Socket s = null;
		DataOutputStream dout = null;
		DataInputStream din = null;
		if(v == button1){//������ǰ�ť
			try {
				s = new Socket("192.168.9.102", 8888);//���ӷ�����
				dout = new DataOutputStream(s.getOutputStream());//�õ������
				din = new DataInputStream(s.getInputStream());//�õ�������
				dout.writeUTF(editText.getText().toString());//�������������Ϣ
				textView.setText("��������������Ϣ��" + din.readUTF());//���շ�������������Ϣ
			} catch (Exception e) {
				e.printStackTrace();//��ӡ�쳣��Ϣ
			} finally {
				try{
					if(dout != null){
						dout.close();//�ر�������
					}
					if(din != null){
						din.close();//�ر�������
					}
					if(s != null){
						s.close();//�ر�Socket����
					}					
				}
				catch(Exception e){
					e.printStackTrace();//��ӡ�쳣��Ϣ
				}
			}
		}
	}
}