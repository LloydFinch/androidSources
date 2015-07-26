package wyf.ytl;
import java.io.BufferedInputStream;//引入BufferedInputStream类
import java.net.URL;//引入URL类
import java.net.URLConnection;//引入URLConnection类
import org.apache.http.util.ByteArrayBuffer;//引入ByteArrayBuffer类
import org.apache.http.util.EncodingUtils;//引入EncodingUtils类
import android.app.Activity;//引入Activity类
import android.os.Bundle;//引入Bundle类
import android.widget.ScrollView;//引入ScrollView类
import android.widget.TextView;//引入TextView类
public class Sample_5_2 extends Activity {
	TextView textView = null;//声明一个TextView的引用
	ScrollView scrollView = null;//声明一个ScrollView的引用
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
        super.onCreate(savedInstanceState);
        textView = new TextView(this);//初始化textView
        scrollView = new ScrollView(this);//初始化scrollView
        URLConnection ucon = null;
        BufferedInputStream bin = null;
        ByteArrayBuffer bab = null;
        try {
			URL myURL = new URL("http://www.google.cn/");//初始化URL
			ucon = myURL.openConnection();//打开连接
			bin = new BufferedInputStream(ucon.getInputStream());//通过连接得到输入流
			int current = 0;
			bab = new ByteArrayBuffer(1000);
			while((current=bin.read()) != -1){
				bab.append((char)current);//将收到的信息添加到ByteArrayBuffer中	
			}
		} catch (Exception e) {
			e.printStackTrace();//打印异常信息
		} finally { 
			try{
				if(bin != null){
					bin.close();//关闭输入流
				}
			}
			catch(Exception e){
				e.printStackTrace();//打印异常信息
			}
		}
		textView.setText(EncodingUtils.getString(bab.toByteArray(), "UTF-8"));//设置textView中的内容
		scrollView.addView(textView);//将textView添加到scrollView中
		this.setContentView(scrollView);//设置当前显示的用户界面为scrollView
    }
}