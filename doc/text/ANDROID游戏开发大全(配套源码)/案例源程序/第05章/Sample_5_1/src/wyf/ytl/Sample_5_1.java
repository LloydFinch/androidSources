package wyf.ytl;
import java.io.DataInputStream;//引入DataInputStream类
import java.io.DataOutputStream;//引入DataOutputStream类
import java.net.Socket;//引入Socket类
import android.app.Activity;//引入Activity类
import android.os.Bundle;//引入Bundle类
import android.view.View;//引入View类
import android.view.View.OnClickListener;//引入OnClickListener类
import android.widget.Button;//引入Button类
import android.widget.EditText;//引入EditText类
import android.widget.TextView;//引入TextView类
public class Sample_5_1 extends Activity implements OnClickListener{
	Button button1;//按钮的引用
	EditText editText;//文本框的引用
	TextView textView;//文本的引用
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate回调方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前的用户界面
        button1 = (Button) findViewById(R.id.button1);//得到布局中的按钮引用
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button1.setOnClickListener(this);//添加监听
    }
	public void onClick(View v) {
		Socket s = null;
		DataOutputStream dout = null;
		DataInputStream din = null;
		if(v == button1){//点击的是按钮
			try {
				s = new Socket("192.168.9.102", 8888);//连接服务器
				dout = new DataOutputStream(s.getOutputStream());//得到输出流
				din = new DataInputStream(s.getInputStream());//得到输入流
				dout.writeUTF(editText.getText().toString());//向服务器发送消息
				textView.setText("服务器发来的消息：" + din.readUTF());//接收服务器发来的消息
			} catch (Exception e) {
				e.printStackTrace();//打印异常信息
			} finally {
				try{
					if(dout != null){
						dout.close();//关闭输入流
					}
					if(din != null){
						din.close();//关闭输入流
					}
					if(s != null){
						s.close();//关闭Socket连接
					}					
				}
				catch(Exception e){
					e.printStackTrace();//打印异常信息
				}
			}
		}
	}
}