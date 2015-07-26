package wyf.wpf;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.http.util.EncodingUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Sample_4_1 extends Activity {
	public static final String ENCODING = "UTF-8";		//��������������ʽ
	String fileName = "test.txt";//�ļ�������
	String message = "��ã�����һ�������ļ�I/O��ʾ����";	//д��Ͷ�����������Ϣ
	TextView tv;										//TextView��������
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ��Ļ
		writeFileData(fileName, message);//�����ļ���д������
        String result = readFileData(fileName);//��ô��ļ����������
        tv = (TextView)findViewById(R.id.tv);//����id��ȡ��Ļ��TextView���������
        tv.setText(result);//����TextView������
    }
    //��������ָ���ļ���д��ָ��������
    public void writeFileData(String fileName,String message){
    	try{
    		FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);//���FileOutputStream����
    		byte [] bytes = message.getBytes();//��Ҫд����ַ���ת��Ϊbyte����
    		fout.write(bytes);//��byte����д���ļ�
    		fout.close();//�ر�FileOutputStream����
    	}
    	catch(Exception e){
    		e.printStackTrace();//�����쳣����ӡ
    	}
    }    
    //��������ָ���ļ�����ȡ�����ݣ������ַ�������
    public String readFileData(String fileName){
    	String result="";
    	try{
    		FileInputStream fin = openFileInput(fileName);//���FileInputStream����
    		int length = fin.available();//��ȡ�ļ�����
    		byte [] buffer = new byte[length];//����byte�������ڶ�������
    		fin.read(buffer);//���ļ����ݶ��뵽byte������    				
    		result = EncodingUtils.getString(buffer, ENCODING);//��byte����ת����ָ����ʽ���ַ���
    		fin.close();					//�ر��ļ�������
    	}
    	catch(Exception e){
    		e.printStackTrace();//�����쳣����ӡ
    	}
    	return result;//���ض����������ַ���
    }    
}