package com.bn.pb;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

enum WhichView {WELLCOM_VIEW,MAIN_VIEW,GAME_VIEW,SOUND_VIEW,LAST_VIEW}

public class MyActivity extends Activity {
	
	WhichView curr;//枚举类的引用
	WellcomeSurfaceView wellcomView;//欢迎界面
	MySurfaceView gameView;//进入游戏界面
	MainView mainView;//主菜单界面
	SoundView soundView;//背景音乐控制界面
	LastView lastView;//最后的界面
	
	boolean soundFlag=true;//背景音乐播放标志位
	boolean soundPoolFlag=true;//声音池的标志位
	
	SoundPool soundPool;//声音池
	HashMap<Integer, Integer> soundPoolMap; //声音池中声音ID与自定义声音ID的Map
	
	Handler myHandler = new Handler(){//处理各个SurfaceView发送的消息
        public void handleMessage(Message msg) {
        	switch(msg.what)
        	{
        	case 0:
        		Constant.COUNT=0;
        		goToMainView();//进入“选择主界面”
        		break;
        	case 1:
        		goToGameView();//进入"游戏界面"
        		break;
        	case 2:
        		goToSoundView();//进入背景音乐控制界面
        		break;
        	case 3:
        		Constant.COUNT=0;//关数清零
        		goToLastView();
        		break;
        	}
        }
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState); 

    	//游戏过程中只允许调整多媒体音量，而不允许调整通话音量
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉标头
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制横屏
        
        //获取分辨率
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //给常量类中的屏幕高和宽赋值
        if(dm.widthPixels>dm.heightPixels)
        {
        	Constant.SCREEN_WIDTH=dm.widthPixels;
        	Constant.SCREEN_HEIGHT=dm.heightPixels;
        }else
        {
        	Constant.SCREEN_HEIGHT=dm.widthPixels;
        	Constant.SCREEN_WIDTH=dm.heightPixels;
        }
        
        goToWellcomView();//进入欢迎界面
        
        initSound();
        
    }
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		if(keyCode==4)
		{
			switch(curr)
			 {
			 case GAME_VIEW://如果游戏进行中，返回到主界面
				 gameView.overGame();
				 
			 case SOUND_VIEW://得分界面，跳转到主控制界面
			 case LAST_VIEW://最后的界面，跳转到主控制界面
			 
				 goToMainView();
				 break;
			 case WELLCOM_VIEW://欢迎界面，退出游戏
			 case MAIN_VIEW://主控制界面，退出游戏
				 System.exit(0);
				 break;
			 
			 }
			return true;
		}
		return false;
	}
	
    @Override
	protected void onResume() {//重写onResume方法
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {//重写onPause方法
		super.onPause();
	}
	
	public void initSound()
    {	
		//声音池
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    
	    //吃东西音乐
	    soundPoolMap.put(1, soundPool.load(this, R.raw.sound2, 1));
	    
    }
    
    public void playSound(int sound, int loop) 
    {
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   
	    
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
	
	//向Handler发送信息的方法
    public void sendMessage(int what)
    {
		Message msg = myHandler.obtainMessage(what); 
    	myHandler.sendMessage(msg);
    } 
	
	//进入"百纳科技"界面
	public void goToWellcomView()
	{
		if(wellcomView==null)
    	{
			wellcomView=new WellcomeSurfaceView(this);
    	}
		setContentView(wellcomView);
		curr=WhichView.WELLCOM_VIEW;
	}
	//进入"游戏界面"
	public void goToGameView()
	{
		gameView=new MySurfaceView(this);
    	setContentView(gameView);
    	gameView.requestFocus();//获取焦点
    	gameView.setFocusableInTouchMode(true);//设为可触控
    	curr=WhichView.GAME_VIEW;
	}
	//进入“选择主界面”
	public void goToMainView()
	{
		if(mainView==null){
			mainView=new MainView(this);
    	}
    	setContentView(mainView);
    	mainView.requestFocus();//获取焦点
    	mainView.setFocusableInTouchMode(true);//设为可触控
    	curr=WhichView.MAIN_VIEW;
	}
	//进入背景音乐控制界面
	public void goToSoundView()
	{
		if(soundView==null){
			soundView=new SoundView(this);
    	}
    	setContentView(soundView);
    	soundView.requestFocus();//获取焦点
    	soundView.setFocusableInTouchMode(true);//设为可触控
    	curr=WhichView.SOUND_VIEW;
	}
	//进入游戏结束界面
	public void goToLastView()
	{
		if(lastView==null){
			lastView=new LastView(this);
    	}
    	setContentView(lastView);
    	lastView.requestFocus();//获取焦点
    	lastView.setFocusableInTouchMode(true);//设为可触控
    	curr=WhichView.LAST_VIEW;
	}
	
}