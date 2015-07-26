package com.bn.chess;
import static com.bn.chess.ViewConstant.*;

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
enum WhichView{WELCOME_VIEW,GAME_VIEW};
public class Chess_DJB_Activity extends Activity {
	GameView gameView;
	WhichView wv;
	WelcomeView wvv;
	SoundPool soundPool;//������
	HashMap<Integer, Integer> soundPoolMap; //������������ID���Զ�������ID��Map
	Handler hd=new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case 0:
				wv=WhichView.GAME_VIEW;
				goToGameView();
				
				break;
			}
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //����ȫ����ʾ
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(
        		WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
      //���ú���ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        hd.sendEmptyMessage(0);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);//��Ϸ������ֻ�����ý������,��������ͨ������
        initPm();//������Ļ�ֱ���
        initSound();
        goToWelcomeView();
//        hd.sendEmptyMessage(0);
    }
    public void initPm()
    {
    	//��ȡ��Ļ�ֱ���
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int tempHeight=(int) (height=dm.heightPixels);
        int tempWidth=(int) (width=dm.widthPixels); 
//        
        if(tempHeight>tempWidth)
        {
        	height=tempHeight;
        	width=tempWidth;
        }
        else
        {
        	height=tempWidth;
        	width=tempHeight;
        }
        float zoomx=width/480;
		float zoomy=height/800;
		if(zoomx>zoomy){
			xZoom=yZoom=zoomy;
			
		}else
		{
			xZoom=yZoom=zoomx;
		}
		sXtart=(width-480*xZoom)/2;
		sYtart=(height-800*yZoom)/2;
		initChessViewFinal();
    }
    public void goToGameView()
    {
    		gameView=new GameView(Chess_DJB_Activity.this);    		
    		
    	setContentView(gameView);
    	wv=WhichView.GAME_VIEW;
    }
  

  //���뻶ӭ����
    public void goToWelcomeView()
    {
    	if(wvv==null)
    	{
    		wvv=new WelcomeView(this);
    	}
    	setContentView(wvv);
    	wv=WhichView.WELCOME_VIEW;
    }
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent e)
    {
    	if(keyCode!=4)
    	{
    		return true;  
    	}
    	if(wv==WhichView.WELCOME_VIEW)
    	{
    		return true;
    	}
    	
    	if(wv==WhichView.GAME_VIEW)
    	{
    		gameView.threadFlag=false;
    		System.exit(0);
    		return true;
    	}
    	System.exit(0);
    	return true;
    }
    public void initSound()
    {
		//������
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    //�Զ�������
	    soundPoolMap.put(1, soundPool.load(this, R.raw.noxiaqi, 1)); 
	    soundPoolMap.put(2, soundPool.load(this, R.raw.dong, 1)); //�������	    
	    soundPoolMap.put(4, soundPool.load(this, R.raw.win, 1)); //Ӯ��
	    soundPoolMap.put(5, soundPool.load(this, R.raw.loss, 1)); //���� 
    }
    //��������
    public void playSound(int sound, int loop) 
    {
    	if(!isnoPlaySound)
    	{
    		return;
    	}
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
}