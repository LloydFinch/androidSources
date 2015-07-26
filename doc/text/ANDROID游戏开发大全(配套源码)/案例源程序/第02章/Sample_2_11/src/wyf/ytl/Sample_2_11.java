package wyf.ytl;
import android.app.Activity;//引入Activity类
import android.graphics.PixelFormat;//引入PixelFormat类
import android.media.AudioManager;//引入AudioManager类
import android.media.MediaPlayer;//引入MediaPlayer类
import android.os.Bundle;//引入Bundle类
import android.view.SurfaceHolder;//引入SurfaceHolder类
import android.view.SurfaceView;//引入SurfaceView类
import android.view.View;//引入View类
import android.view.View.OnClickListener;//引入OnClickListener类
import android.widget.Button;//引入Button类
public class Sample_2_11 extends Activity implements OnClickListener,SurfaceHolder.Callback{
	String path = "/sdcard/bbb.3gp";
	Button play_Button;
	Button pause_Button;
	boolean isPause = false;
	SurfaceHolder surfaceHolder;
	MediaPlayer mediaPlayer;
	SurfaceView surfaceView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        play_Button = (Button) findViewById(R.id.play2_Button);
        play_Button.setOnClickListener(this);
        pause_Button = (Button) findViewById(R.id.pause2_Button);
        pause_Button.setOnClickListener(this);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFixedSize(176,144);		 
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mediaPlayer = new MediaPlayer();
    }
	public void onClick(View v) {
		if(v == play_Button){//按下播放电影按钮
			isPause = false;
			playVideo(path);
		}
		else if(v == pause_Button){//按下暂停按钮，
			if(isPause == false){//如果正在播放则将其暂停
				mediaPlayer.pause();
				isPause = true;
			}
			else{//如果暂停中怎继续播放
				mediaPlayer.start();
				isPause = false;
			}
		}
	}
	private void playVideo(String strPath){//自定义播放影片函数 
		if(mediaPlayer.isPlaying()==true){
			mediaPlayer.reset();
		}
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setDisplay(surfaceHolder);//设置Video影片以SurfaceHolder播放 
		try{ 
			mediaPlayer.setDataSource(strPath);
			mediaPlayer.prepare();
		}
		catch (Exception e){ 
			e.printStackTrace();
		}
		mediaPlayer.start();
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}
	public void surfaceCreated(SurfaceHolder arg0) {
	}
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}
}