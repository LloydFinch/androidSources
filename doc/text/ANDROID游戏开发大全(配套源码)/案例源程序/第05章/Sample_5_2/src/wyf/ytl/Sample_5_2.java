package wyf.ytl;
import java.io.BufferedInputStream;//����BufferedInputStream��
import java.net.URL;//����URL��
import java.net.URLConnection;//����URLConnection��
import org.apache.http.util.ByteArrayBuffer;//����ByteArrayBuffer��
import org.apache.http.util.EncodingUtils;//����EncodingUtils��
import android.app.Activity;//����Activity��
import android.os.Bundle;//����Bundle��
import android.widget.ScrollView;//����ScrollView��
import android.widget.TextView;//����TextView��
public class Sample_5_2 extends Activity {
	TextView textView = null;//����һ��TextView������
	ScrollView scrollView = null;//����һ��ScrollView������
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
        super.onCreate(savedInstanceState);
        textView = new TextView(this);//��ʼ��textView
        scrollView = new ScrollView(this);//��ʼ��scrollView
        URLConnection ucon = null;
        BufferedInputStream bin = null;
        ByteArrayBuffer bab = null;
        try {
			URL myURL = new URL("http://www.google.cn/");//��ʼ��URL
			ucon = myURL.openConnection();//������
			bin = new BufferedInputStream(ucon.getInputStream());//ͨ�����ӵõ�������
			int current = 0;
			bab = new ByteArrayBuffer(1000);
			while((current=bin.read()) != -1){
				bab.append((char)current);//���յ�����Ϣ��ӵ�ByteArrayBuffer��	
			}
		} catch (Exception e) {
			e.printStackTrace();//��ӡ�쳣��Ϣ
		} finally { 
			try{
				if(bin != null){
					bin.close();//�ر�������
				}
			}
			catch(Exception e){
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}
		}
		textView.setText(EncodingUtils.getString(bab.toByteArray(), "UTF-8"));//����textView�е�����
		scrollView.addView(textView);//��textView��ӵ�scrollView��
		this.setContentView(scrollView);//���õ�ǰ��ʾ���û�����ΪscrollView
    }
}