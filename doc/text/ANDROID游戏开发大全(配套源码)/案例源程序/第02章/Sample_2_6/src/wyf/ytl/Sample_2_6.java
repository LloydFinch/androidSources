package wyf.ytl;
import android.app.Activity;//引入相关的包
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;
public class Sample_2_6 extends Activity implements OnClickListener{
	Button button;//普通按钮
	ImageButton imageButton;//图片按钮
	ToggleButton toggleButton;//开关按钮
	TextView textView;//文本控件
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//回调方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置显示的View
        textView = (TextView) this.findViewById(R.id.textView);
        button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(this);//为button添加监听器
        imageButton = (ImageButton) this.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);//为imageButton添加监听器
        toggleButton = (ToggleButton) this.findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(this);//为toggleButton添加监听器
    }
	public void onClick(View v) {//重写的事件处理回调方法
		if(v == button){//点击的是普通按钮
			textView.setText("您点击的是普通按钮");
		}
		else if(v == imageButton){//点击的是图片按钮
			textView.setText("您点击的是图片按钮");
		}
		else if(v == toggleButton){//点击的是开关按钮
			textView.setText("您点击的是开关按钮");
		}		
	}
}