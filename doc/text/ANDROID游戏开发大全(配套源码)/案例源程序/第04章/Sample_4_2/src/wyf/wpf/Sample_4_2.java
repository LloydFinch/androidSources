package wyf.wpf;							//声明包语句
import java.io.InputStream;					//引入相关包
import org.apache.http.util.EncodingUtils;	//引入相关包
import android.app.Activity;				//引入相关包
import android.os.Bundle;					//引入相关包
import android.widget.TextView;				//引入相关包

public class Sample_4_2 extends Activity {
	public static final String ENCODING = "UTF-8";	//常量，代表编码格式
	TextView tv1;									//TextView的引用
	TextView tv2;									//TextView的引用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);			//设置显示屏幕
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv1.setText(getFromRaw("test1.txt"));		//将tv1的显示内容设置为Resource中的raw文件夹的文件
        tv2.setText(getFromAsset("test2.txt"));		//将tv2的显示内容设置为Asset中的文件
    }
    //方法：从resource中的raw文件夹中获取文件并读取数据
    public String getFromRaw(String fileName){
    	String result = "";
        try{
        	InputStream in = getResources().openRawResource(R.raw.test1);	//从Resources中raw中的文件获取输入流
        	int length = in.available();									//获取文件的字节数
        	byte [] buffer = new byte[length];								//创建byte数组
        	in.read(buffer);												//将文件中的数据读取到byte数组中
        	result = EncodingUtils.getString(buffer, ENCODING);				//将byte数组转换成指定格式的字符串   
        	in.close();														//关闭输入流
        }
        catch(Exception e){
        	e.printStackTrace();											//捕获异常并打印
        }
    	return result;
    }
    //方法：从asset中获取文件并读取数据
    public String getFromAsset(String fileName){
    	String result="";
    	try{
    		InputStream in = getResources().getAssets().open(fileName);		//从Assets中的文件获取输入流
    		int length = in.available();									//获取文件的字节数
        	byte [] buffer = new byte[length];								//创建byte数组
        	in.read(buffer);												//将文件中的数据读取到byte数组中
        	result = EncodingUtils.getString(buffer, ENCODING);				//将byte数组转换成指定格式的字符串
    	}
    	catch(Exception e){
    		e.printStackTrace();											//捕获异常并打印
    	}
    	return result;
    }
}