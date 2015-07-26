package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
public class Sample_9_2 extends Activity implements OnClickListener{
	TextView myTextView1;//表示扑克牌的
	TextView myTextView2;//表示输赢的概率的
	Button button;//发牌按钮
	String[] puKePai = new String[]{//表示扑克牌的数组
		"2","3","4","5","6","7","8","9","10","J","Q","K","A"
	};
	int[] liShuDu = new int[]{//表示胜利的隶属度
			0,8,16,24,32,41,49,
			58,66,75,84,91,100
		};	
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate回调方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myTextView1 = (TextView) this.findViewById(R.id.myTextView1);//得到文本控件的引用
        myTextView2 = (TextView) this.findViewById(R.id.myTextView2);//得到文本控件的引用
        button = (Button) this.findViewById(R.id.button);//得到按钮的引用
        button.setOnClickListener(this);
    }

	public void onClick(View v) {
		if(v == button){//点击了发牌按钮
			int temp = (int)(Math.random()*puKePai.length);//得到随机数
			myTextView1.setText("您的牌为："+puKePai[temp]);//取扑克牌
			myTextView2.setText("赢的概率为："+liShuDu[temp]+"%");//取隶属度
		}
	}
}