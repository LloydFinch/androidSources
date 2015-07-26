package wyf.wpf;				//���������
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;//���������
import android.app.Activity;				//���������
import android.graphics.Rect;			//���������
import android.hardware.SensorManager;	//���������
import android.media.MediaPlayer;	//���������
import android.os.Bundle;	//���������
import android.os.Handler;	//���������
import android.os.Message;	//���������
import android.view.KeyEvent;	//���������
import android.view.MotionEvent;	//���������
import android.view.View;	//���������
import android.view.Window;	//���������
import android.view.WindowManager;	//���������
/*
 * ����Ϊ��Ϸ�����࣬���е�View�����������������ã���Ҫ�Ĺ�����ʵ����Ϸ
 * �����̿��ƣ��ṩ��Ϸ��Ҫ�ĳ���������ͼ֮������л���
 */
public class DriftBall extends Activity {
	public static final int STATUS_PLAY = 0;		//��Ϸ������
	public static final int STATUS_PAUSE = 1;		//��Ϸ��ͣ
	public static final int STATUS_WIN = 2;			//ͨ��һ��
	public static final int STATUS_LOSE = 3;		//����һ����
	public static final int STATUS_OVER = 4;		//�������ˣ���Ϸ����
	public static final int STATUS_PASS = 5;		//ͨȫ��
	public static final int MAX_LIFE = 5;			//���������
	public static final int MAX_LEVEL = 5;			//���ؿ���	
	int level = 1;									//��ʼ״̬�ȼ�Ϊ1
	int life = MAX_LIFE;							//��ʼ״̬���������	
	Rect rectStart;				//��ʼԲ��ť�ľ��ο�
	Rect rectSoundOption;		//����ѡ��Բ��ť�ľ��ο�
	Rect rectQuit;				//�Ƴ�Բ�ΰ�ť�ľ��ο�
	Rect rectContinue;			//������Ϸ�˵���ľ��ο�
	Rect rectSoundAlter;		//����ѡ��˵���ľ��ο�
	Rect rectHelp;				//�����˵���ľ��ο�
	Rect rectBackToMain;		//�ص����˵��˵���ľ��ο�
	Rect rectGameMsgBox;		//��Ļ�м���ʾ��Ϣ�ľ��ο�	
	MediaPlayer mpGameMusic;	//��Ϸ��������
	MediaPlayer mpPlusLife;		//����������
	MediaPlayer mpMissileHit;	//�������е�����
	MediaPlayer mpGameWin;		//ͨ����һ�ص�����
	MediaPlayer mpGameLose;		//��ʧһ����������
	MediaPlayer mpBreakOut;		//��ʾ�˵��͵��������Լ����Ե�������	
	boolean wantSound = true;	//��־λ����¼�Ƿ񲥷�����	
	View currView;				//��¼��ǰ��ʾ��View
	GameView gv;				//��Ϸ��ͼ������
	WelcomeView wv;				//��ӭ��ͼ������
	BallListener bl;			//�̳���SensorListener�ļ�����
	HelpView hv;				//������ͼ
	//@1======��Դ������������޸�������SensorSimulator
	SensorManager mySensorManager;
    //SensorManagerSimulator mySensorManager; 
	
	Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {	//��дhandleMessage����
			switch(msg.what){
			case 0:			//0Ϊ�յ�����WelcomeView�Ŀ�ʼ��Ϸ����
		        gv = new GameView(DriftBall.this);
				setContentView(gv);		//���õ�ǰView
				currView = gv;			//��¼��ǰView
				startSensor();			//����������
				wv = null;
				break;
			case 1:			//1Ϊ�յ�����WelcomeView���˳���Ϸ����
				System.exit(0);			//�˳�����
				break;
			}
		}		
	};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //����ȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        wv = new WelcomeView(this);
        setContentView(wv);
        currView = wv;       
        bl = new BallListener(this); //����SensorManager
        //@2=========�޸Ĵ���SensorManager����Ĵ��룻֮����@3��ȥAndroidManifest.xml�����INTERNETȨ��
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
        //mySensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
        //mySensorManager.connectSimulator();       
        initRect(); 		//��ʼ����������
        initSound();		//��ʼ������
    }
	 //����������������
    public void startSensor(){
		mySensorManager.registerListener(bl, SensorManager.SENSOR_ORIENTATION,SensorManager.SENSOR_DELAY_GAME);   	
    }
    //��ͣ������
    public void pauseSensor(){
    	mySensorManager.unregisterListener(bl);
    }
    //��ʼ��������Դ
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
    //��ʼ����������Rect
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
	protected void onPause() {	//��дonPause����
		pauseSensor();			//����pauseSensor����
		super.onPause();
	}
	//��д�ķ��������ڽ��պʹ����û������Ļ�¼�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){		
			int x=(int)event.getX();
			int y=(int)event.getY();
			if(currView == wv){			//��ǰΪ��ӭ����
				if(rectStart.contains(x, y)){	//���¿�ʼ��Բ��		
					wv.status = 1;				//����״̬Ϊ1���а�ť����
					wv.selectedIndex = 0;		//���ñ�ѡ�еİ�ťΪ��ʼ��ť
				}
				else if(rectSoundOption.contains(x, y)){	//�����л�������Բ��
					wantSound = !wantSound;			//�л���־λ
					if(!wantSound){					//�����ǰ�ڲ���������ͣ
						if(mpGameMusic.isPlaying()){
							try {
								mpGameMusic.pause();		//��ͣ��Ϸ��������
							} catch (Exception e) {}
						}						
					}
					else if(wantSound){				//�����ǰû�в�������������
						if(!mpGameMusic.isPlaying()){
							try {
								mpGameMusic.start();		//������Ϸ��������
							} catch (Exception e) {}
						}
					}
				}
				else if(rectQuit.contains(x, y)){			//�����˳���Բ��
					wv.status = 1;				//����״̬Ϊ1���а�ť����
					wv.selectedIndex = 1;		//���ñ�ѡ�еİ�ťΪ�˳���ť
				}
			}
			else if(currView == gv){	//��ǰΪ��Ϸ��������ʾ
				if(gv.status == STATUS_PAUSE){
					if(rectContinue.contains(x,y)){			//���¼�����Ϸ�˵�
						gv.gmt.isOut = true;	//�˵���ʼ�˳���Ļ
					}
					else if(rectSoundAlter.contains(x, y)){	//�����л������˵�
						wantSound = !wantSound;
						if(!wantSound){						//�жϼ�¼ֵ�����
							if(mpGameMusic.isPlaying()){
								try {
									mpGameMusic.pause();	//��ͣ����
								} catch (Exception e) {}
							}
						}
						else if(wantSound){
							if(!mpGameMusic.isPlaying()){
								try {
									mpGameMusic.start();	//��������
								} catch (Exception e) {}
							}
						}
						gv.gmt.isOut = true;
					}
					else if(rectHelp.contains(x, y)){		//���°����˵�
						gv.gmt.isOut = true;		//�����˵�
						gv.pauseGame();				//��ͣ��Ϸ
						hv = new HelpView(this);	//����HelpView����
						setContentView(hv);			//����
						currView = hv;
					}
					else if(rectBackToMain.contains(x, y)){	//���»����˵�
						gv.gmt.isOut = true;		//�����˵�
						wv = new WelcomeView(this);	//������ӭ����
						setContentView(wv);			//����
						currView = wv;				//��¼��ǰView
						pauseSensor();				//��ͣSensor
						gv.shutAll();				//�ر�gv�ĸ����߳�
						gv = null;
					}					
				}
				else if(gv.status == STATUS_WIN){	//����һ��
					if(rectGameMsgBox.contains(x, y)){
						gv.initGame();
						gv.resumeGame();			//��ʼ��һ�ص���Ϸ
					}					
				}
				else if(gv.status == STATUS_PASS || gv.status == STATUS_OVER){	//��ǰ״̬Ϊͨȫ�ػ�������
					if(rectGameMsgBox.contains(x, y)){		//�жϵ����λ��
						wv = new WelcomeView(this);			//������ӭ����
						setContentView(wv);					//����
						currView = wv;						//��¼View
						pauseSensor();						//��ͣ������
						gv.shutAll();						//�ر�gv�����߳�
						gv = null;
					}
				}
			}
		}
		return true;
	}
	//��д��okKeyUp���������ղ�������̰����¼�
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == 82){		//���µ����ֻ���Ļ��menu��
			if(wantSound){		//����Ҫ�򲥷�����
				try {
					mpBreakOut.start();
				} catch (Exception e) {}
			}
			gv.pauseGame();	//��ͣ����Ϸ
			gv.dt.isGameOn = true;
			gv.gmt = new GameMenuThread(this);
			gv.gmt.start();		//����˵�
		}       
		else if(keyCode == 4){			//���·��ؼ�
			if(currView == hv){				
				setContentView(gv);		//����
				gv.resumeGame();		//�ָ���Ϸ
				currView = gv;			//��¼View
			}
		}
		return true;
	}
}