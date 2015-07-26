package wyf.ytl;
import android.app.Activity;	//引入Activity类
import android.os.Bundle;//引入Bundle类
import android.view.animation.Animation;//引入Animation类
import android.view.animation.AnimationUtils;//引入AnimationUtils类
import android.widget.ImageView;//引入ImageView类
public class Sample_2_9 extends Activity {
	Animation myAnimation;//动画的引用
	ImageView myImageView;//ImageView的引用
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate回调方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前显示的View
        myAnimation= AnimationUtils.loadAnimation(this,R.anim.myanim);//加载动画
        myImageView = (ImageView) this.findViewById(R.id.myImageView);//得到ImageView的引用
        myImageView.startAnimation(myAnimation);//启动动画
    }    
}