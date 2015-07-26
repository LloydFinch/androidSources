package wyf.wpf;//���������
import android.app.Activity;//���������
import android.content.SharedPreferences;	//���������
import android.os.Bundle;					//���������
import android.widget.EditText;				//���������
//�̳���Activity������
public class Sample_4_5 extends Activity {
	EditText etPre;			//EditText���������
	SharedPreferences sp;	//SharedPreference���������
	public final String EDIT_TEXT_KEY = "EDIT_TEXT";	//Preferences�ļ��еļ�
    @Override
    public void onCreate(Bundle savedInstanceState) {	//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        etPre = (EditText)findViewById(R.id.et);	//�����Ļ��EditText��������
        sp = getPreferences(MODE_PRIVATE);		//���SharedPreferences����
        String result = sp.getString(EDIT_TEXT_KEY, null);
        if(result != null){		//�����ȡ��ֵ��Ϊ��
        	etPre.setText(result);	//EditText������ʾ����������Ϊ��ȡ������
        }
    }
	@Override
	protected void onDestroy() {	//��дonDestroy����
		SharedPreferences.Editor editor = sp.edit();	//���SharedPreferences��Editor����
		editor.putString(EDIT_TEXT_KEY, String.valueOf(etPre.getText()));	//�޸�����
		editor.commit();			//������ø÷������ύ�޸�
		super.onDestroy();
	}
}