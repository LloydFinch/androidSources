package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class Sample_9_3 extends Activity implements OnClickListener{
	TextView myTextView1;//TextView的引用
	TextView myTextView2;//TextView的引用
	Button myButton;//Button的引用
	double[] values = new double[]{0,0.707,1,0.707,0,-0.707,-1,-0.707};//查找表数组
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myTextView1 = (TextView) findViewById(R.id.myEditText1);//得到myTextView1的引用
        myTextView2 = (TextView) findViewById(R.id.myEditText2);//得到myEditText2的引用
        myButton = (Button) findViewById(R.id.myButton);//得到myButton的引用
        myButton.setOnClickListener(this);//添加监听
    }
	public void onClick(View v) {
		if(v == myButton){//按下计算按钮
			int temp = 0;//用户输入角度
			int index = 0;
			try{
				temp =  Integer.parseInt(myTextView1.getText().toString());//将myTextView1中的值转换成整数
			}
			catch(Exception e){}//如果不能转换成整数则不转换
			temp = temp%360;//将角度换算成0~360度
			index = temp/45;//得到索引值
			myTextView2.setText("运算结果为："+values[index]);
		}
	}
    
}