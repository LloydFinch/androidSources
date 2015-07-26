package wyf.ytl;
import android.app.Activity;							//引入相关类
import android.os.Bundle;
import android.util.Log;
public class HelloAndroid extends Activity {			//定义一个Activity
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {	//重写的onCreate回调方法
        super.onCreate(savedInstanceState);				//调用基类的onCreate方法
        Log.d("TAG", "This is message!");				//打印调试信息
        setContentView(R.layout.main);					//指定当前显示的布局
    }
}