package wyf.ytl;
import android.app.Activity;							//���������
import android.os.Bundle;
import android.util.Log;
public class HelloAndroid extends Activity {			//����һ��Activity
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {	//��д��onCreate�ص�����
        super.onCreate(savedInstanceState);				//���û����onCreate����
        Log.d("TAG", "This is message!");				//��ӡ������Ϣ
        setContentView(R.layout.main);					//ָ����ǰ��ʾ�Ĳ���
    }
}