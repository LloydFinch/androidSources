package wyf.ytl;
import android.app.Activity;//����Activity��
import android.graphics.PixelFormat;//����PixelFormat��
import android.media.AudioManager;//����AudioManager��
import android.media.MediaPlayer;//����MediaPlayer��
import android.os.Bundle;//����Bundle��
import android.view.SurfaceHolder;//����SurfaceHolder��
import android.view.SurfaceView;//����SurfaceView��
import android.view.View;//����View��
import android.view.View.OnClickListener;//����OnClickListener��
import android.widget.Button;//����Button��
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
		if(v == play_Button){//���²��ŵ�Ӱ��ť
			isPause = false;
			playVideo(path);
		}
		else if(v == pause_Button){//������ͣ��ť��
			if(isPause == false){//������ڲ���������ͣ
				mediaPlayer.pause();
				isPause = true;
			}
			else{//�����ͣ������������
				mediaPlayer.start();
				isPause = false;
			}
		}
	}
	private void playVideo(String strPath){//�Զ��岥��ӰƬ���� 
		if(mediaPlayer.isPlaying()==true){
			mediaPlayer.reset();
		}
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setDisplay(surfaceHolder);//����VideoӰƬ��SurfaceHolder���� 
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