package wyf.ytl;
import java.util.HashMap;//����HashMap��
import android.app.Activity;//����Activity��
import android.content.Context;//����Context��
import android.media.AudioManager;//����AudioManager��
import android.media.MediaPlayer;//����MediaPlayer��
import android.media.SoundPool;//����SoundPool��
import android.os.Bundle;//����Bundle��
import android.view.View;//����View��
import android.view.View.OnClickListener;//����OnClickListener��
import android.widget.Button;//����Button��
import android.widget.TextView;//����TextView��
public class Sample_2_10 extends Activity implements OnClickListener{
    Button button1;//�ĸ���ť������
    Button button2;
    Button button3;
    Button button4; 
    TextView textView;//TextView������
	MediaPlayer mMediaPlayer; 
	SoundPool soundPool;//����
	HashMap<Integer, Integer> soundPoolMap; 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//��дonCreate�ص�����
        super.onCreate(savedInstanceState);
        initSounds();//��ʼ������
        setContentView(R.layout.main);				//������ʾ���û�����
        textView = (TextView) this.findViewById(R.id.textView);//�õ�TextView������
        button1 = (Button) this.findViewById(R.id.button1);//�õ�button������
        button2 = (Button) this.findViewById(R.id.button2);
        button3 = (Button) this.findViewById(R.id.button3);
        button4 = (Button) this.findViewById(R.id.button4);
        button1.setOnClickListener(this);//Ϊ�ĸ���ť��Ӽ���
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }
	public void initSounds(){//��ʼ�������ķ���
		mMediaPlayer = MediaPlayer.create(this, R.raw.backsound);//��ʼ��MediaPlayer
	    soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    soundPoolMap.put(1, soundPool.load(this, R.raw.dingdong, 1));
	} 
	public void playSound(int sound, int loop) {//��SoundPoll���������ķ���
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent/streamVolumeMax;   
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);//��������
	}    
	public void onClick(View v) {//ʵ�ֽӿ��еķ���
		// TODO Auto-generated method stub
		if(v == button1){//�����ʹ��MediaPlayer����������ť
			textView.setText("ʹ��MediaPlayer��������");
			if(!mMediaPlayer.isPlaying()){
				mMediaPlayer.start();//��������
			}
		}
		else if(v == button2){//�������ͣMediaPlayer������ť
			textView.setText("��ͣ��MediaPlayer���ŵ�����");
			if(mMediaPlayer.isPlaying()){
				mMediaPlayer.pause();//��ͣ����
			}
		}
		else if(v == button3){//�����ʹ��SoundPool����������ť
			textView.setText("ʹ��SoundPool��������");
			this.playSound(1, 0);
		}
		else if(v == button4){//�������ͣSoundPool������ť
			textView.setText("��ͣ��SoundPool���ŵ�����");
			soundPool.pause(1);//��ͣSoundPool������
		}		
	}
}