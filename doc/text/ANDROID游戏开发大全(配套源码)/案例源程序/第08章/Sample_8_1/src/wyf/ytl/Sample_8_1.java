package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
public class Sample_8_1 extends Activity {
	private static final String[] mySpinner_str = {//搜索下拉列表的内容
		"深度优先","广度优先","广度优先A*","Dijkstra","Dijkstra A*"
	}; 
	Spinner mySpinner;//搜索下拉列表框
	Spinner targetSpinner;//目标下拉列表框
	Button goButton;//开始按钮
	GameView gameView;//自己实现的地图View
	TextView BSTextView;//使用步数的文本
	TextView CDTextView;//路径长度的文本
	Game game;
	private ArrayAdapter<String> adapter;//搜索下拉列表的模型
	private ArrayAdapter<String> adapter2;//目标下拉列表的模型
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置当前显示的用户界面
        mySpinner = (Spinner)findViewById(R.id.mySpinner);//得到搜索下拉列表的引用
        targetSpinner = (Spinner)findViewById(R.id.target);//得到目标下拉列表的引用
        gameView = (GameView) findViewById(R.id.gameView);//得到GameView的引用
        BSTextView = (TextView)findViewById(R.id.bushu);//得到使用步数的文本控件的引用
        CDTextView = (TextView)findViewById(R.id.changdu);//得到路径长度的文本控件的引用
        goButton = (Button) findViewById(R.id.go);//得到开始按钮的引用
        game = new Game();//初始化算法类
        //创建搜索下拉列表的模型
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mySpinner_str);
        String[] target_str = new String[MapList.target.length];//根据目标点的个数创建一个数组
        for(int i=0; i<MapList.target.length; i++){
        	target_str[i] = "目标"+i;
        }
        //创建目标下拉列表的模型
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, target_str);
        mySpinner.setAdapter(adapter);//设置模型
        targetSpinner.setAdapter(adapter2);//设置模型
        goButton.setOnClickListener(//按钮监听器
        	new Button.OnClickListener(){
				public void onClick(View v) {
					game.runAlgorithm();//调用运算方法
					goButton.setEnabled(false);
				}
	        }
        );
        targetSpinner.setOnItemSelectedListener(//目标选择的下拉列表监听
        	new Spinner.OnItemSelectedListener(){
				public void onItemSelected(AdapterView<?> a, View v,int arg2, long arg3){
					game.target = MapList.target[arg2];
					game.clearState();//将game的状态清空
					gameView.postInvalidate();//重写绘制gameView
				}
				public void onNothingSelected(AdapterView<?> arg0){
				}
        	}
        );
        mySpinner.setOnItemSelectedListener(//算法选择的下拉列表监听
            	new Spinner.OnItemSelectedListener(){
    				public void onItemSelected(AdapterView<?> ada, View v,int arg2, long arg3){
    					game.clearState();//将game的状态清空
    					game.algorithmId =  (int) ada.getSelectedItemId();//得到选择的算法ID
    					gameView.postInvalidate();//重写绘制gameView
    				}
    				public void onNothingSelected(AdapterView<?> arg0) {
    				}
            	}
         );
        this.initIoc();//调用依赖注入方法
    }
    public void initIoc()
    {//依赖注入
    	gameView.game = this.game;
    	gameView.mySpinner = this.mySpinner;
    	gameView.CDTextView = this.CDTextView;
    	game.gameView = this.gameView;
    	game.goButton = this.goButton;
    	game.BSTextView = this.BSTextView;
    }
}