package wyf.ytl;
import java.util.HashMap;//引入HashMap类
import android.app.Activity;//引入Activity类
import android.content.Context;//引入Context类
import android.media.AudioManager;//引入AudioManager类
import android.media.MediaPlayer;//引入MediaPlayer类
import android.media.SoundPool;//引入SoundPool类
import android.os.Bundle;//引入Bundle类
import android.view.View;//引入View类
import android.view.View.OnClickListener;//引入OnClickListener类
import android.widget.Button;//引入Button类
import android.widget.TextView;//引入TextView类
public class Sample_2_10 extends Activity implements OnClickListener{
    Button button1;//四个按钮的引用
    Button button2;
    Button button3;
    Button button4; 
    TextView textView;//TextView的引用
	MediaPlayer mMediaPlayer; 
	SoundPool soundPool;//声音
	HashMap<Integer, Integer> soundPoolMap; 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//重写onCreate回调方法
        super.onCreate(savedInstanceState);
        initSounds();//初始化声音
        setContentView(R.layout.main);				//设置显示的用户界面
        textView = (TextView) this.findViewById(R.id.textView);//得到TextView的引用
        button1 = (Button) this.findViewById(R.id.button1);//得到button的引用
        button2 = (Button) this.findViewById(R.id.button2);
        button3 = (Button) this.findViewById(R.id.button3);
        button4 = (Button) this.findViewById(R.id.button4);
        button1.setOnClickListener(this);//为四个按钮添加监听
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }
	public void initSounds(){//初始化声音的方法
		mMediaPlayer = MediaPlayer.create(this, R.raw.backsound);//初始化MediaPlayer
	    soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    soundPoolMap.put(1, soundPool.load(this, R.raw.dingdong, 1));
	} 
	public void playSound(int sound, int loop) {//用SoundPoll播放声音的方法
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent/streamVolumeMax;   
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);//播放声音
	}    
	public void onClick(View v) {//实现接口中的方法
		// TODO Auto-generated method stub
		if(v == button1){//点击了使用MediaPlayer播放声音按钮
			textView.setText("使用MediaPlayer播放声音");
			if(!mMediaPlayer.isPlaying()){
				mMediaPlayer.start();//播放声音
			}
		}
		else if(v == button2){//点击了暂停MediaPlayer声音按钮
			textView.setText("暂停了MediaPlayer播放的声音");
			if(mMediaPlayer.isPlaying()){
				mMediaPlayer.pause();//暂停声音
			}
		}
		else if(v == button3){//点击了使用SoundPool播放声音按钮
			textView.setText("使用SoundPool播放声音");
			this.playSound(1, 0);
		}
		else if(v == button4){//点击了暂停SoundPool声音按钮
			textView.setText("暂停了SoundPool播放的声音");
			soundPool.pause(1);//暂停SoundPool的声音
		}		
	}
}