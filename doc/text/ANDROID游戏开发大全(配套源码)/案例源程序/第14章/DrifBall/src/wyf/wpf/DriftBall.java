package wyf.wpf;				//声明包语句
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;//引入相关类
import android.app.Activity;				//引入相关类
import android.graphics.Rect;			//引入相关类
import android.hardware.SensorManager;	//引入相关类
import android.media.MediaPlayer;	//引入相关类
import android.os.Bundle;	//引入相关类
import android.os.Handler;	//引入相关类
import android.os.Message;	//引入相关类
import android.view.KeyEvent;	//引入相关类
import android.view.MotionEvent;	//引入相关类
import android.view.View;	//引入相关类
import android.view.Window;	//引入相关类
import android.view.WindowManager;	//引入相关类
/*
 * 该类为游戏的主类，所有的View对象在这里有所引用，主要的功能是实现游戏
 * 的流程控制，提供游戏需要的常量，在视图之间进行切换。
 */
public class DriftBall extends Activity {
	public static final int STATUS_PLAY = 0;		//游戏进行中
	public static final int STATUS_PAUSE = 1;		//游戏暂停
	public static final int STATUS_WIN = 2;			//通过一关
	public static final int STATUS_LOSE = 3;		//丢掉一条命
	public static final int STATUS_OVER = 4;		//送完命了，游戏结束
	public static final int STATUS_PASS = 5;		//通全关
	public static final int MAX_LIFE = 5;			//最大生命数
	public static final int MAX_LEVEL = 5;			//最大关卡数	
	int level = 1;									//初始状态等级为1
	int life = MAX_LIFE;							//初始状态生命数最大	
	Rect rectStart;				//开始圆球按钮的矩形框
	Rect rectSoundOption;		//声音选项圆球按钮的矩形框
	Rect rectQuit;				//推出圆形按钮的矩形框
	Rect rectContinue;			//继续游戏菜单项的矩形框
	Rect rectSoundAlter;		//声音选项菜单项的矩形框
	Rect rectHelp;				//帮助菜单项的矩形框
	Rect rectBackToMain;		//回到主菜单菜单项的矩形框
	Rect rectGameMsgBox;		//屏幕中间提示消息的矩形框	
	MediaPlayer mpGameMusic;	//游戏背景音乐
	MediaPlayer mpPlusLife;		//加命的音乐
	MediaPlayer mpMissileHit;	//导弹打中的音乐
	MediaPlayer mpGameWin;		//通过了一关的音乐
	MediaPlayer mpGameLose;		//损失一条命的音乐
	MediaPlayer mpBreakOut;		//显示菜单和掉入陷阱以及被吃掉的音乐	
	boolean wantSound = true;	//标志位，记录是否播放音乐	
	View currView;				//记录当前显示的View
	GameView gv;				//游戏视图的引用
	WelcomeView wv;				//欢迎视图的引用
	BallListener bl;			//继承自SensorListener的监听器
	HelpView hv;				//帮助视图
	//@1======对源代码进行如下修改以连接SensorSimulator
	SensorManager mySensorManager;
    //SensorManagerSimulator mySensorManager; 
	
	Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {	//重写handleMessage方法
			switch(msg.what){
			case 0:			//0为收到来自WelcomeView的开始游戏命令
		        gv = new GameView(DriftBall.this);
				setContentView(gv);		//设置当前View
				currView = gv;			//记录当前View
				startSensor();			//开启传感器
				wv = null;
				break;
			case 1:			//1为收到来自WelcomeView的退出游戏命令
				System.exit(0);			//退出程序
				break;
			}
		}		
	};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        wv = new WelcomeView(this);
        setContentView(wv);
        currView = wv;       
        bl = new BallListener(this); //创建SensorManager
        //@2=========修改创建SensorManager对象的代码；之后是@3：去AndroidManifest.xml中添加INTERNET权限
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
        //mySensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
        //mySensorManager.connectSimulator();       
        initRect(); 		//初始化矩形区域
        initSound();		//初始化声音
    }
	 //方法：启动传感器
    public void startSensor(){
		mySensorManager.registerListener(bl, SensorManager.SENSOR_ORIENTATION,SensorManager.SENSOR_DELAY_GAME);   	
    }
    //暂停传感器
    public void pauseSensor(){
    	mySensorManager.unregisterListener(bl);
    }
    //初始化声音资源
    public void initSound(){
    	mpBreakOut = MediaPlayer.create(this, R.raw.break_out);  
    	mpGameMusic = MediaPlayer.create(this, R.raw.game_music);
		mpGameLose = MediaPlayer.create(this, R.raw.game_lose);
		mpGameWin = MediaPlayer.create(this, R.raw.game_win);
		mpMissileHit = MediaPlayer.create(this, R.raw.missile_hit); 
		mpPlusLife = MediaPlayer.create(this, R.raw.plus_life);			
    	mpGameMusic.setLooping(true);
    	try {
			mpGameMusic.start(); 
		} catch (Exception e) {}    	 
    }
    //初始化矩形区域Rect
    public void initRect(){
    	rectStart = new Rect(60,120,195,255);
    	rectQuit = new Rect(120,300,210,390);
    	rectSoundOption = new Rect(180,220,282,321);
    	rectContinue = new Rect(165,30,315,80);
    	rectSoundAlter = new Rect(165,80,315,130);
    	rectHelp = new Rect(165,130,315,180);
    	rectBackToMain = new Rect(165,180,315,230);
    	rectGameMsgBox = new Rect(70,200,250,310);
    }
	@Override
	protected void onPause() {	//重写onPause方法
		pauseSensor();			//调用pauseSensor方法
		super.onPause();
	}
	//重写的方法，用于接收和处理用户点击屏幕事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){		
			int x=(int)event.getX();
			int y=(int)event.getY();
			if(currView == wv){			//当前为欢迎界面
				if(rectStart.contains(x, y)){	//点下开始的圆球		
					wv.status = 1;				//设置状态为1，有按钮按下
					wv.selectedIndex = 0;		//设置被选中的按钮为开始按钮
				}
				else if(rectSoundOption.contains(x, y)){	//点下切换声音的圆球
					wantSound = !wantSound;			//切换标志位
					if(!wantSound){					//如果当前在播放声音，停
						if(mpGameMusic.isPlaying()){
							try {
								mpGameMusic.pause();		//暂停游戏背景音乐
							} catch (Exception e) {}
						}						
					}
					else if(wantSound){				//如果当前没有播放声音，播放
						if(!mpGameMusic.isPlaying()){
							try {
								mpGameMusic.start();		//播放游戏背景音乐
							} catch (Exception e) {}
						}
					}
				}
				else if(rectQuit.contains(x, y)){			//点下退出的圆球
					wv.status = 1;				//设置状态为1，有按钮按下
					wv.selectedIndex = 1;		//设置被选中的按钮为退出按钮
				}
			}
			else if(currView == gv){	//当前为游戏界面在显示
				if(gv.status == STATUS_PAUSE){
					if(rectContinue.contains(x,y)){			//点下继续游戏菜单
						gv.gmt.isOut = true;	//菜单开始退出屏幕
					}
					else if(rectSoundAlter.contains(x, y)){	//点下切换声音菜单
						wantSound = !wantSound;
						if(!wantSound){						//判断记录值的真假
							if(mpGameMusic.isPlaying()){
								try {
									mpGameMusic.pause();	//暂停声音
								} catch (Exception e) {}
							}
						}
						else if(wantSound){
							if(!mpGameMusic.isPlaying()){
								try {
									mpGameMusic.start();	//播放声音
								} catch (Exception e) {}
							}
						}
						gv.gmt.isOut = true;
					}
					else if(rectHelp.contains(x, y)){		//点下帮助菜单
						gv.gmt.isOut = true;		//淡出菜单
						gv.pauseGame();				//暂停游戏
						hv = new HelpView(this);	//创建HelpView对象
						setContentView(hv);			//切屏
						currView = hv;
					}
					else if(rectBackToMain.contains(x, y)){	//点下回主菜单
						gv.gmt.isOut = true;		//淡出菜单
						wv = new WelcomeView(this);	//创建欢迎界面
						setContentView(wv);			//切屏
						currView = wv;				//记录当前View
						pauseSensor();				//暂停Sensor
						gv.shutAll();				//关闭gv的附属线程
						gv = null;
					}					
				}
				else if(gv.status == STATUS_WIN){	//闯过一关
					if(rectGameMsgBox.contains(x, y)){
						gv.initGame();
						gv.resumeGame();			//开始下一关的游戏
					}					
				}
				else if(gv.status == STATUS_PASS || gv.status == STATUS_OVER){	//当前状态为通全关或死完了
					if(rectGameMsgBox.contains(x, y)){		//判断点击的位置
						wv = new WelcomeView(this);			//创建欢迎界面
						setContentView(wv);					//切屏
						currView = wv;						//记录View
						pauseSensor();						//暂停监听器
						gv.shutAll();						//关闭gv附属线程
						gv = null;
					}
				}
			}
		}
		return true;
	}
	//重写的okKeyUp方法，接收并处理键盘按下事件
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == 82){		//按下的是手机屏幕的menu键
			if(wantSound){		//若需要则播放声音
				try {
					mpBreakOut.start();
				} catch (Exception e) {}
			}
			gv.pauseGame();	//暂停下游戏
			gv.dt.isGameOn = true;
			gv.gmt = new GameMenuThread(this);
			gv.gmt.start();		//淡入菜单
		}       
		else if(keyCode == 4){			//按下返回键
			if(currView == hv){				
				setContentView(gv);		//切屏
				gv.resumeGame();		//恢复游戏
				currView = gv;			//记录View
			}
		}
		return true;
	}
}