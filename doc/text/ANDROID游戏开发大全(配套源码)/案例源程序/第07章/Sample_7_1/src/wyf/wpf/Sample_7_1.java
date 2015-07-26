package wyf.wpf;		//声明包语句
import android.app.Activity;	//引入相关类
import android.os.Bundle;	//引入相关类
import android.view.Window;//引入相关类
import android.view.WindowManager;//引入相关类
//继承自Activity的引用
public class Sample_7_1 extends Activity {
	BallView bv;		//BallView对象引用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);		//设置不显示标题
        getWindow().setFlags(									//设置为全屏模式
        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bv = new BallView(this);	//创建BallView对象
        setContentView(bv);			//将屏幕设置为BallView对象
    }
}