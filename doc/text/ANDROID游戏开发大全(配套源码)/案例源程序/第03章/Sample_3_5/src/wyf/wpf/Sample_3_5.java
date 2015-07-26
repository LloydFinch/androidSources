package wyf.wpf;//声明包
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//继承自Activity的子类
public class Sample_3_5 extends Activity {
	Button btnDial;
    @Override
    public void onCreate(Bundle savedInstanceState) {//重写onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置屏幕显示内容
        btnDial = (Button)this.findViewById(R.id.btDial);//获得屏幕上按钮对象引用
        btnDial.setOnClickListener(new Button.OnClickListener(){//为按钮添加点击事件的监听器
			@Override
			public void onClick(View v) {//重写onClick方法
				Intent myIntent = new Intent(Intent.ACTION_DIAL);//创建Intent对象
				Sample_3_5.this.startActivity(myIntent);//启动Android内置的拨号程序
			}        	
        });
    }
}