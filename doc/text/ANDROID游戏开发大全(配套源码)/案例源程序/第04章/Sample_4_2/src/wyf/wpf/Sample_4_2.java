package wyf.wpf;							//���������
import java.io.InputStream;					//������ذ�
import org.apache.http.util.EncodingUtils;	//������ذ�
import android.app.Activity;				//������ذ�
import android.os.Bundle;					//������ذ�
import android.widget.TextView;				//������ذ�

public class Sample_4_2 extends Activity {
	public static final String ENCODING = "UTF-8";	//��������������ʽ
	TextView tv1;									//TextView������
	TextView tv2;									//TextView������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//������ʾ��Ļ
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv1.setText(getFromRaw("test1.txt"));		//��tv1����ʾ��������ΪResource�е�raw�ļ��е��ļ�
        tv2.setText(getFromAsset("test2.txt"));		//��tv2����ʾ��������ΪAsset�е��ļ�
    }
    //��������resource�е�raw�ļ����л�ȡ�ļ�����ȡ����
    public String getFromRaw(String fileName){
    	String result = "";
        try{
        	InputStream in = getResources().openRawResource(R.raw.test1);	//��Resources��raw�е��ļ���ȡ������
        	int length = in.available();									//��ȡ�ļ����ֽ���
        	byte [] buffer = new byte[length];								//����byte����
        	in.read(buffer);												//���ļ��е����ݶ�ȡ��byte������
        	result = EncodingUtils.getString(buffer, ENCODING);				//��byte����ת����ָ����ʽ���ַ���   
        	in.close();														//�ر�������
        }
        catch(Exception e){
        	e.printStackTrace();											//�����쳣����ӡ
        }
    	return result;
    }
    //��������asset�л�ȡ�ļ�����ȡ����
    public String getFromAsset(String fileName){
    	String result="";
    	try{
    		InputStream in = getResources().getAssets().open(fileName);		//��Assets�е��ļ���ȡ������
    		int length = in.available();									//��ȡ�ļ����ֽ���
        	byte [] buffer = new byte[length];								//����byte����
        	in.read(buffer);												//���ļ��е����ݶ�ȡ��byte������
        	result = EncodingUtils.getString(buffer, ENCODING);				//��byte����ת����ָ����ʽ���ַ���
    	}
    	catch(Exception e){
    		e.printStackTrace();											//�����쳣����ӡ
    	}
    	return result;
    }
}