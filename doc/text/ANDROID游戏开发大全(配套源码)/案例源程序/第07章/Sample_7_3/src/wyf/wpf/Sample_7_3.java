package wyf.wpf;		//声明包语句
import android.app.Activity;		//引入相关类
import android.os.Bundle;			//引入相关类
import android.view.Window;			//引入相关类
import android.view.WindowManager;	//引入相关类
//继承自Activity的子类
public class Sample_7_3 extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {		//重写onCreate方法
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);		//设置不显示标题
        getWindow().setFlags(									//设置为全屏模式
        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ParticleView lz = new ParticleView(this);		//创建一个LiZiView对象
        setContentView(lz);								//将屏幕设置为ParticleView对象
    }
}