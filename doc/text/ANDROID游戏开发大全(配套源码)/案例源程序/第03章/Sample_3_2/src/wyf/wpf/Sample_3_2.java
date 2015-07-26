package wyf.wpf;//声明包
import android.app.Activity;//引入相关包
import android.os.Bundle;//引入相关包
public class Sample_3_2 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//调用父类onCreate方法
        MyContentView mcv = new MyContentView(this);//创建View对象
        setContentView(mcv);//设置当前屏幕
    }
}