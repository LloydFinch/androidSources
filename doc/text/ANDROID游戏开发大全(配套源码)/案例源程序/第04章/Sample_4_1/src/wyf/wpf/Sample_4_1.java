package wyf.wpf;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.http.util.EncodingUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Sample_4_1 extends Activity {
	public static final String ENCODING = "UTF-8";		//常量，代表编码格式
	String fileName = "test.txt";//文件的名称
	String message = "你好，这是一个关于文件I/O的示例。";	//写入和读出的数据信息
	TextView tv;										//TextView对象引用
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前屏幕
		writeFileData(fileName, message);//创建文件并写入数据
        String result = readFileData(fileName);//获得从文件读入的数据
        tv = (TextView)findViewById(R.id.tv);//根据id获取屏幕中TextView对象的引用
        tv.setText(result);//设置TextView的内容
    }
    //方法：向指定文件中写入指定的数据
    public void writeFileData(String fileName,String message){
    	try{
    		FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);//获得FileOutputStream对象
    		byte [] bytes = message.getBytes();//将要写入的字符串转换为byte数组
    		fout.write(bytes);//将byte数组写入文件
    		fout.close();//关闭FileOutputStream对象
    	}
    	catch(Exception e){
    		e.printStackTrace();//捕获异常并打印
    	}
    }    
    //方法：打开指定文件，读取其数据，返回字符串对象
    public String readFileData(String fileName){
    	String result="";
    	try{
    		FileInputStream fin = openFileInput(fileName);//获得FileInputStream对象
    		int length = fin.available();//获取文件长度
    		byte [] buffer = new byte[length];//创建byte数组用于读入数据
    		fin.read(buffer);//将文件内容读入到byte数组中    				
    		result = EncodingUtils.getString(buffer, ENCODING);//将byte数组转换成指定格式的字符串
    		fin.close();					//关闭文件输入流
    	}
    	catch(Exception e){
    		e.printStackTrace();//捕获异常并打印
    	}
    	return result;//返回读到的数据字符串
    }    
}