package wyf.wpf;//������
import android.app.Activity;//������ذ�
import android.os.Bundle;//������ذ�
public class Sample_3_2 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//���ø���onCreate����
        MyContentView mcv = new MyContentView(this);//����View����
        setContentView(mcv);//���õ�ǰ��Ļ
    }
}