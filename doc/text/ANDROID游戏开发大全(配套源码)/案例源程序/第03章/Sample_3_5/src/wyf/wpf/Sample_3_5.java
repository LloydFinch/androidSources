package wyf.wpf;//������
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//�̳���Activity������
public class Sample_3_5 extends Activity {
	Button btnDial;
    @Override
    public void onCreate(Bundle savedInstanceState) {//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//������Ļ��ʾ����
        btnDial = (Button)this.findViewById(R.id.btDial);//�����Ļ�ϰ�ť��������
        btnDial.setOnClickListener(new Button.OnClickListener(){//Ϊ��ť��ӵ���¼��ļ�����
			@Override
			public void onClick(View v) {//��дonClick����
				Intent myIntent = new Intent(Intent.ACTION_DIAL);//����Intent����
				Sample_3_5.this.startActivity(myIntent);//����Android���õĲ��ų���
			}        	
        });
    }
}