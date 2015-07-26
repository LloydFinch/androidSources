package wyf.ytl;
import android.app.Activity;//引入相关类
import android.os.Bundle;//引入相关类
import android.os.Handler;//引入相关类
import android.os.Looper;//引入相关类
import android.os.Message;//引入相关类
import android.view.KeyEvent;//引入相关类
import android.view.Window;//引入相关类
import android.view.WindowManager;//引入相关类
public class PlaneActivity extends Activity{
	int action = 0;//键盘的状态,二进制表示 从左往右表示上下左右
	GameView gameView;//GameView的引用
	WelcomeView welcomeView;//WelcomeView的引用
	FailView failView;//游戏失败界面的引用
	HelpView helpView;//HelpView的引用
	WinView winView;//欢迎界面的引用
	ProcessView processView;//进度条界面的引用
	boolean isSound = true;//是否播放声音
	Handler myHandler = new Handler(){//用来更新UI线程中的控件
        public void handleMessage(Message msg) {
        	if(msg.what == 1){//游戏失败，玩家飞机坠毁
        		if(gameView != null){
        			gameView.keyThread.setFlag(false);//停止键盘监听
        			gameView.gameThread.setFlag(false);
        			gameView.moveThread.setFlag(false);
        			gameView = null;
        		}
        		initFailView();//切换到FialView
        	}
        	else if(msg.what == 2){//切换到GameView
        		if(welcomeView != null){
        			welcomeView = null;//释放欢迎界面
        		}
        		if(processView != null){
        			processView = null;//释放加载界面
        		}
            	processView = new ProcessView(PlaneActivity.this,2);//初始化进度条并切换到进度条View
            	PlaneActivity.this.setContentView(processView);
            	new Thread(){//线程
            		public void run(){
            			Looper.prepare();
            			gameView = new GameView(PlaneActivity.this);//初始化GameView
            			Looper.loop();
            		}
            	}.start();//启动线程
        	}
        	else if(msg.what == 3){//WelcomeView发来的消息，切换到HelpView
        		initHelpView();//切换到HelpView界面
        	}
        	else if(msg.what == 4){
        		if(helpView != null){
        			helpView = null;
        		}
        		toWelcomeView();//切换到WelcomeView界面 
        	}
        	else if(msg.what == 5){
        		if(gameView != null){
        			gameView.gameThread.setFlag(false);
        			gameView.keyThread.setFlag(false);
        			gameView.moveThread.setFlag(false);
        			gameView.explodeThread.setFlag(false);
        			gameView = null;
        		}
        		initWinView();//切换到WinView界面 
        	}
        	else if(msg.what == 6){
        		toGameView();//去游戏界面
        	}
        	else if(msg.what == 7){
        		if(welcomeView != null){//释放欢迎界面
        			welcomeView = null;
        		}
        		if(processView != null){//释放加载界面
        			processView = null;
        		}
        		processView = new ProcessView(PlaneActivity.this,1);//初始化进度条并切换到进度条View
        		PlaneActivity.this.setContentView(processView);
            	new Thread(){//线程
            		public void run(){//重写的run方法
            			Looper.prepare();
            			welcomeView = new WelcomeView(PlaneActivity.this);//初始化WelcomeView
            			Looper.loop();
            		}
            	}.start();//启动线程
        	}
        }
	};
    public void onCreate(Bundle savedInstanceState) {//创建是被创建
        super.onCreate(savedInstanceState);
		//全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	processView = new ProcessView(this,1);//初始化进度条并切换到进度条View
    	this.setContentView(processView);//设置加载界面
    	new Thread(){//线程
    		public void run(){
    			Looper.prepare();
    			welcomeView = new WelcomeView(PlaneActivity.this);//初始化WelcomeView
    		}
    	}.start();//启动线程
    }
    public void toWelcomeView(){//切换到欢迎界面     	
    	this.setContentView(welcomeView);
    }
    public void toGameView(){//初始游戏界面
    	this.setContentView(gameView);
    }
    public void initHelpView(){//初始帮助界面
    	helpView = new HelpView(this);
    	this.setContentView(helpView);
    }
    public void initFailView(){//初始游戏失败界面
    	failView = new FailView(this);
    	this.setContentView(failView);
    }
    public void initWinView(){//初始胜利界面
    	winView = new WinView(this);
    	this.setContentView(winView);
    }
    public boolean onKeyUp(int keyCode, KeyEvent event) {//键盘抬起
    	if(keyCode == 19){//上
    		action = action & 0x1F;
    	}
    	if(keyCode == 20){//下
    		action = action & 0x2F;
    	}    	
    	if(keyCode == 21){//左
    		action = action & 0x37;
    	}    	
    	if(keyCode == 22){//右
    		action = action & 0x3B;
    	}
    	if(keyCode == KeyEvent.KEYCODE_A){//A
    		action = action & 0x3D;
    	}
    	if(keyCode == 100){//B 留着备用
    		action = action & 0x3E;
    	}
		return false;
	}
    public boolean onKeyDown(int keyCode, KeyEvent event){//键盘按下监听
    	if(keyCode == 19){//上
    		action = action | 0x20;
    	}
    	if(keyCode == 20){//下
    		action = action | 0x10;
    	}
    	if(keyCode == 21){//左
    		action = action | 0x08;
    	}
    	if(keyCode == 22){//右
    		action = action | 0x04;
    	}
    	if(keyCode == KeyEvent.KEYCODE_A){//A 
    		action = action | 0x02;
    	}
    	if(keyCode == 100){//B留着备用
    		action = action | 0x01;
    	}
		return false;
    }
}