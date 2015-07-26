package com.bn.box2d.sndls;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundUtil
{
	SoundPool soundPool;
	HashMap<Integer, Integer> soundPoolMap;
	MyBox2dActivity activity;
	public SoundUtil(MyBox2dActivity activity)
	{
		this.activity=activity;
	}
	//声音缓冲池的初始化
    public void initSounds()
    {
    	 //创建声音缓冲池
	     soundPool = new SoundPool
	     (
	    		 6, 							//同时能最多播放的个数
	    		 AudioManager.STREAM_MUSIC,     //音频的类型
	    		 100							//声音的播放质量，目前无效
	     );
	     
	     //创建声音资源Map	     
	     soundPoolMap = new HashMap<Integer, Integer>();   
	     //将加载的声音资源id放进此Map
	     soundPoolMap.put(0, soundPool.load(activity, R.raw.pijin, 1));//皮筋拉伸的声音
	     soundPoolMap.put(1, soundPool.load(activity, R.raw.ls_fx, 1));//老鼠飞
	     soundPoolMap.put(2, soundPool.load(activity, R.raw.wood, 1));//木头
	     soundPoolMap.put(3, soundPool.load(activity, R.raw.boli, 1));//玻璃
	     soundPoolMap.put(4, soundPool.load(activity, R.raw.stond, 1));//石头
	     soundPoolMap.put(5, soundPool.load(activity, R.raw.cat, 1));//小猫
	     //有几个音效就有当前这个几句  R.raw.gamestart返回编号 不定     后面的1为优先级 目前不考虑
	} 
       
   //播放声音的方法
   public void playSound(int sound, int loop) {
	   if(!activity.mmv.syisTouch)
	   {
		   AudioManager mgr = (AudioManager)activity.getSystemService(Context.AUDIO_SERVICE);
		    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);//当前音量   
		    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//最大音量       
		    float volume = streamVolumeCurrent / streamVolumeMax;   
		    
		    soundPool.play
		    (
	    		soundPoolMap.get(sound), //声音资源id
	    		volume, 				 //左声道音量
	    		volume, 				 //右声道音量
	    		1, 						 //优先级				 
	    		loop, 					 //循环次数 -1带表永远循环
	    		1f					 //回放速度0.5f～2.0f之间
		    );
	   }
	}
}