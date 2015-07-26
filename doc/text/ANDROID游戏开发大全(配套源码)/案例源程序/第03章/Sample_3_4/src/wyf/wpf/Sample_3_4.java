package wyf.wpf;//声明包
import android.app.Activity;//引入相关包
import android.os.Bundle;//引入相关包
import android.os.Handler;//引入相关包
import android.os.Message;//引入相关包
import android.view.View;//引入相关包
import android.widget.Button;//引入相关包
import android.widget.TextView;//引入相关包
//继承自Activity的子类
public class Sample_3_4 extends Activity {
	public static final int UPDATE_DATA = 0;//常量，代表更新数据
	public static final int UPDATE_COMPLETED = 1;//常量，代表更新数据
	TextView tv;//TextView对象的引用
	Button btnStart;//Button对象的引用
	//主线程的Handler对象
	Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {//重写处理消息方法
			switch(msg.what){//判断消息类别
			case UPDATE_DATA://消息为更新的数据
				tv.setText("正在更新来自线程的数据:"+msg.arg1+"%...");
				break;
			case UPDATE_COMPLETED://消息为更新完毕
				tv.setText("已完成来自线程的更新数据！");
				break;
			}
		}		
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前屏幕为R.layout.main布局文件
        tv = (TextView)findViewById(R.id.tv);//获得屏幕中TextView对象引用
        btnStart = (Button)findViewById(R.id.btnStart);//获得屏幕中Button对象引用
        btnStart.setOnClickListener(new View.OnClickListener() {//为Button添加点击事件监听器			
			@Override
			public void onClick(View v) {
				new Thread(){//启动一个新线程
					public void run(){
						for(int i=0;i<100;i++){
							try{//睡眠一段时间
								Thread.sleep(150);
							}
							catch(Exception e){
								e.printStackTrace();
							}
							Message m = myHandler.obtainMessage();//创建Message对象
							m.what = UPDATE_DATA;//为what字段赋值
							m.arg1=i+1;//为arg1字段赋值
							myHandler.sendMessage(m);//发出Message对象
						}
						myHandler.sendEmptyMessage(UPDATE_COMPLETED);//发出更新完毕消息
					}
				}.start();
			}
		});
    }
}