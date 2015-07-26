package wyf.ytl;
import android.app.Activity;//������صİ�
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;
public class Sample_2_6 extends Activity implements OnClickListener{
	Button button;//��ͨ��ť
	ImageButton imageButton;//ͼƬ��ť
	ToggleButton toggleButton;//���ذ�ť
	TextView textView;//�ı��ؼ�
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//�ص�����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//������ʾ��View
        textView = (TextView) this.findViewById(R.id.textView);
        button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(this);//Ϊbutton��Ӽ�����
        imageButton = (ImageButton) this.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);//ΪimageButton��Ӽ�����
        toggleButton = (ToggleButton) this.findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(this);//ΪtoggleButton��Ӽ�����
    }
	public void onClick(View v) {//��д���¼�����ص�����
		if(v == button){//���������ͨ��ť
			textView.setText("�����������ͨ��ť");
		}
		else if(v == imageButton){//�������ͼƬ��ť
			textView.setText("���������ͼƬ��ť");
		}
		else if(v == toggleButton){//������ǿ��ذ�ť
			textView.setText("��������ǿ��ذ�ť");
		}		
	}
}