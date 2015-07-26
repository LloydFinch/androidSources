package wyf.ytl;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
/** 
 * 该类为Activity类 
 */
public class HDZGActivity extends Activity {
	LoadingView loadingView;//进度条界面的引用
	MenuView menuView;//开始游戏的主菜单界面   
	GameView gameView;//游戏主界面
	HelpView helpView;//帮助界面
	SoundView soundView;//刚开始时选择是否开启声音的界面
	SoundManageView soundManageView;//声音设置界面
	ScreenRollView screenRoll;//竹简滚屏界面
	AboutView aboutView;//关于界面
	
	boolean isBackSound = true;//是否有背景声音
	boolean isStartSound = true;//开场声音
	boolean isBattleSound = true;//战斗声音
	boolean isEnvironmentSound = true;//环境音
	
	View currentView = null;//当前的View
	
	Handler myHandler = new Handler(){//用来更新UI线程中的控件
        public void handleMessage(Message msg) {
        	switch(msg.what){
        		case 1://收到LoadingView的消息，切到MenuView
        			if(loadingView != null){
        				loadingView = null;//释放LoadingView
        			}
        			
        			toMenuView();//切换到开始游戏时的主菜单
        			break;
        		case 2://收到MenuView的消息，切换到ScreenRoll竹简滚屏
        			if(loadingView != null){
        				loadingView = null;
        			}
        			if(menuView != null){//释放
        				menuView = null;
        			}       			
        			screenRoll = new ScreenRollView(HDZGActivity.this);
        			setContentView(screenRoll);
        			break;
        		case 3://收到LoadingView的消息，切到GameView
        			if(loadingView != null){
        				loadingView = null;//释放LoadingView
        			}
        			toGameView();//切换到游戏的主界面
        			break; 
        		case 4://收到MenuView界面的消息，切换到HelpView
        	    	helpView = new HelpView(HDZGActivity.this,5);
        	    	HDZGActivity.this.currentView = helpView;//设置当前的View为HelpView
        	    	HDZGActivity.this.setContentView(helpView);
        			break;
        		case 5:
        			menuView = new MenuView(HDZGActivity.this);
        			toMenuView();
        			break;
        		case 6:
        			toGameView();//切换到游戏的主界面
        			break;
        		case 7://开始时的声音选择界面传来的消息
        	    	toLoadingView(1);
        	    	new Thread(){//后台启动加载需要加载的View
        	    		public void run(){
        	    			Looper.prepare();
        	    			menuView = new MenuView(HDZGActivity.this);//初始化MenuView
        	    		}
        	    	}.start();
        			break;
        		case 8:
        	    	helpView = new HelpView(HDZGActivity.this,6);
        	    	HDZGActivity.this.currentView = helpView;//设置当前的View为HelpView
        	    	HDZGActivity.this.setContentView(helpView);
        			break;
        		case 9:
        			soundManageView = new SoundManageView(HDZGActivity.this,5);
        	    	HDZGActivity.this.currentView = soundManageView; 
        	    	HDZGActivity.this.setContentView(soundManageView);
        			break;
        		case 10:
        			soundManageView = new SoundManageView(HDZGActivity.this,6);
        	    	HDZGActivity.this.currentView = soundManageView;
        	    	HDZGActivity.this.setContentView(soundManageView);
        			break;
        		case 11:
        			if(loadingView != null){
        				loadingView = null;
        			}
        			if(screenRoll != null){//释放
        				screenRoll = null;
        			}  
        			toLoadingView(3);
                	new Thread(){ 
                		public void run(){
                			Looper.prepare();
                			gameView = new GameView(HDZGActivity.this);//初始化GameView
                		}
                	}.start();
                	break;
        		case 12://从AboutView传来的消息，需要到MenuView
        			menuView = new MenuView(HDZGActivity.this);        		
        			HDZGActivity.this.setContentView(menuView);
        			HDZGActivity.this.currentView = menuView;
        			break;
        		case 13://从MenuView传来的消息，要到AboutView
        			if(aboutView == null){
        				aboutView = new AboutView(HDZGActivity.this);
        			}
        			HDZGActivity.this.setContentView(aboutView);
        			HDZGActivity.this.currentView = aboutView;
        			break;
        		case 14://从GameView传来的消息，要到MenuView
        			menuView = new MenuView(HDZGActivity.this);
        			HDZGActivity.this.setContentView(menuView);
        			HDZGActivity.this.currentView = menuView;
        			break;                	
        		case 99:
        			if(loadingView != null){
        				loadingView = null;
        			}
        			currentView = null;
        			if(SerializableGame.check(HDZGActivity.this)){
	        			toLoadingView(101);
						new Thread(){
							public void run(){
								try{
									Thread.sleep(2000);
								}
								catch(Exception e){
									e.printStackTrace();
								}
								Looper.prepare();
								if(gameView==null)
								{
									gameView = new GameView(HDZGActivity.this);
								}
								SerializableGame.loadingGameStatus(gameView);
							}
						}.start();
        			}
        			break;
        		case 100:
        			if(gameView != null){
        				HDZGActivity.this.setContentView(gameView);
        			}
        			break;
        			
        	}
        }
	};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		soundView = new SoundView(this);
		this.setContentView(soundView);//先切到简单的声音开关界面
		
		GameData.resources=this.getResources();
		GameData.initMapImage();
		GameData.initMapData();
		
		GameData2.resources=this.getResources();
		GameData2.initBitmap();
		GameData2.initMapData();
		
		GameView.initBitmap(this.getResources());
		
		BattleField.initBitmap(this.getResources());
		
		ManPanelView.initBitmap(this.getResources());
		
		WuJiangView.initBitmap(this.getResources());
		
		UseSkillView.initBitmap(this.getResources());
		
		CityManageView.initBitmap(this.getResources());
		
		SelectGeneral.initBitmap(this.getResources());
		
		TianXiaView.initBitmap(this.getResources());
    }
    
    public void toLoadingView(int toViewID){
		loadingView = new LoadingView(this,toViewID);//初始化进度条
		this.currentView = loadingView;
    	this.setContentView(loadingView);//切换到进度条View
    }

    //切换到开始游戏的主菜单
    public void toMenuView(){
    	if(this.menuView != null){//当menuView不为空时切屏
    		this.currentView = menuView;
    		this.setContentView(menuView);
    	}
    	else{//当为空时打印并退出
    		System.exit(0);
    	}
    }
    
    //切换到游戏主界面
    public void toGameView(){ 
    	if(this.gameView != null){//当gameView不为空时
    		this.currentView = gameView;
    		this.setContentView(gameView);
    	}
    	else{//当为空时打印并退出
    		System.exit(0);
    	}
    }

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(currentView != null){
			currentView.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}
}