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
	
	WhichView curr;//ö���������
	WellcomeSurfaceView wellcomView;//��ӭ����
	MySurfaceView gameView;//������Ϸ����
	MainView mainView;//���˵�����
	SoundView soundView;//�������ֿ��ƽ���
	LastView lastView;//���Ľ���
	
	boolean soundFlag=true;//�������ֲ��ű�־λ
	boolean soundPoolFlag=true;//�����صı�־λ
	
	SoundPool soundPool;//������
	HashMap<Integer, Integer> soundPoolMap; //������������ID���Զ�������ID��Map
	
	Handler myHandler = new Handler(){//�������SurfaceView���͵���Ϣ
        public void handleMessage(Message msg) {
        	switch(msg.what)
        	{
        	case 0:
        		Constant.COUNT=0;
        		goToMainView();//���롰ѡ�������桱
        		break;
        	case 1:
        		goToGameView();//����"��Ϸ����"
        		break;
        	case 2:
        		goToSoundView();//���뱳�����ֿ��ƽ���
        		break;
        	case 3:
        		Constant.COUNT=0;//��������
        		goToLastView();
        		break;
        	}
        }
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState); 

    	//��Ϸ������ֻ���������ý�������������������ͨ������
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ������
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȥ����ͷ
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ǿ�ƺ���
        
        //��ȡ�ֱ���
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //���������е���Ļ�ߺͿ�ֵ
        if(dm.widthPixels>dm.heightPixels)
        {
        	Constant.SCREEN_WIDTH=dm.widthPixels;
        	Constant.SCREEN_HEIGHT=dm.heightPixels;
        }else
        {
        	Constant.SCREEN_HEIGHT=dm.widthPixels;
        	Constant.SCREEN_WIDTH=dm.heightPixels;
        }
        
        goToWellcomView();//���뻶ӭ����
        
        initSound();
        
    }
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		if(keyCode==4)
		{
			switch(curr)
			 {
			 case GAME_VIEW://�����Ϸ�����У����ص�������
				 gameView.overGame();
				 
			 case SOUND_VIEW://�÷ֽ��棬��ת�������ƽ���
			 case LAST_VIEW://���Ľ��棬��ת�������ƽ���
			 
				 goToMainView();
				 break;
			 case WELLCOM_VIEW://��ӭ���棬�˳���Ϸ
			 case MAIN_VIEW://�����ƽ��棬�˳���Ϸ
				 System.exit(0);
				 break;
			 
			 }
			return true;
		}
		return false;
	}
	
    @Override
	protected void onResume() {//��дonResume����
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {//��дonPause����
		super.onPause();
	}
	
	public void initSound()
    {	
		//������
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    
	    //�Զ�������
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
	
	//��Handler������Ϣ�ķ���
    public void sendMessage(int what)
    {
		Message msg = myHandler.obtainMessage(what); 
    	myHandler.sendMessage(msg);
    } 
	
	//����"���ɿƼ�"����
	public void goToWellcomView()
	{
		if(wellcomView==null)
    	{
			wellcomView=new WellcomeSurfaceView(this);
    	}
		setContentView(wellcomView);
		curr=WhichView.WELLCOM_VIEW;
	}
	//����"��Ϸ����"
	public void goToGameView()
	{
		gameView=new MySurfaceView(this);
    	setContentView(gameView);
    	gameView.requestFocus();//��ȡ����
    	gameView.setFocusableInTouchMode(true);//��Ϊ�ɴ���
    	curr=WhichView.GAME_VIEW;
	}
	//���롰ѡ�������桱
	public void goToMainView()
	{
		if(mainView==null){
			mainView=new MainView(this);
    	}
    	setContentView(mainView);
    	mainView.requestFocus();//��ȡ����
    	mainView.setFocusableInTouchMode(true);//��Ϊ�ɴ���
    	curr=WhichView.MAIN_VIEW;
	}
	//���뱳�����ֿ��ƽ���
	public void goToSoundView()
	{
		if(soundView==null){
			soundView=new SoundView(this);
    	}
    	setContentView(soundView);
    	soundView.requestFocus();//��ȡ����
    	soundView.setFocusableInTouchMode(true);//��Ϊ�ɴ���
    	curr=WhichView.SOUND_VIEW;
	}
	//������Ϸ��������
	public void goToLastView()
	{
		if(lastView==null){
			lastView=new LastView(this);
    	}
    	setContentView(lastView);
    	lastView.requestFocus();//��ȡ����
    	lastView.setFocusableInTouchMode(true);//��Ϊ�ɴ���
    	curr=WhichView.LAST_VIEW;
	}
	
}