package wyf.ytl;
import java.util.HashMap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback {
	PlaneActivity activity;//activity������
	private TutorialThread thread;//ˢ֡���߳�
	private WelcomeViewThread welcomeThread;//�����߳�
	int status = 1;//��ǰ��״ֵ̬
	int k = 0;//״̬Ϊ2ʱ�õ����л�ͼƬ
	int alpha = 255;//͸����
	
	SoundPool soundPool;//����
	HashMap<Integer, Integer> soundPoolMap; 
	Bitmap welcomeborder;//�������ͼƬ
	Bitmap background;//����ͼƬ
	Bitmap background2;//����ͼƬ2
	Bitmap image1,image2,image3,image4,image5,image6,image7;//����֡
	Bitmap startGame;//��ʼ��Ϸ�˵�
	Bitmap help;//�����˵�
	Bitmap openSound;//�������˵�
	Bitmap closeSound;//�ر������˵�
	Bitmap exit;//�˳��˵�
	
	int backgroundY = -200;//����������
	int background2Y = 40;
	
	Paint paint;//���ڸı�͸����
	Paint paint2;//��������
	public WelcomeView(PlaneActivity activity) {//������ 
		super(activity);
		this.activity = activity;//�õ�activity������

        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);
        this.welcomeThread = new WelcomeViewThread(this);
        
        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
        initSounds();//��ʼ������ 
        initBitmap();//��ʼ��ͼƬ��Դ
        playSound(1);
	}
	public void initSounds(){//��ʼ�������ķ���
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);//��ʼ��SoundPool
	     soundPoolMap = new HashMap<Integer, Integer>();//��ʼ��   HashMap
	     soundPoolMap.put(1, soundPool.load(getContext(), R.raw.welcome1, 1));
	} 
	public void playSound(int sound) {//���������ķ���
	    AudioManager mgr = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);//�����������       
	    float volume = streamVolumeCurrent/streamVolumeMax;   //�豸������
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);//����
	}
	
	public void initBitmap(){//��ʼ��ͼƬ��Դ�ķ���
		paint = new Paint();
		paint2 = new Paint();
        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
		welcomeborder = BitmapFactory.decodeResource(getResources(), R.drawable.welcomeborder);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		image1 = BitmapFactory.decodeResource(getResources(), R.drawable.image1);
        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
		image2 = BitmapFactory.decodeResource(getResources(), R.drawable.image2);//��ʼ������֡
		image3 = BitmapFactory.decodeResource(getResources(), R.drawable.image3);//��ʼ������֡
        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
		image4 = BitmapFactory.decodeResource(getResources(), R.drawable.image4);//��ʼ������֡
		image5 = BitmapFactory.decodeResource(getResources(), R.drawable.image5);//��ʼ������֡
        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
		image6 = BitmapFactory.decodeResource(getResources(), R.drawable.image6);//��ʼ������֡
		image7 = BitmapFactory.decodeResource(getResources(), R.drawable.image7);//��ʼ������֡
		
        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
		background2 = BitmapFactory.decodeResource(getResources(), R.drawable.background2);//��ʼ������ͼƬ
		startGame = BitmapFactory.decodeResource(getResources(), R.drawable.startgame);//��ʼ����ʼ��Ϸ
        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
		help = BitmapFactory.decodeResource(getResources(), R.drawable.help);//����
		openSound = BitmapFactory.decodeResource(getResources(), R.drawable.opensound);//������
        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
		closeSound = BitmapFactory.decodeResource(getResources(), R.drawable.closesound);//�ر�����
		exit = BitmapFactory.decodeResource(getResources(), R.drawable.exit);//�˳���Ϸ
        if(activity.processView != null){//�߼��ؽ������
        	activity.processView.process += 10;
        }
	}
	public void onDraw(Canvas canvas){//�Լ�д�Ļ��Ʒ���
		//����������z��ģ��󻭵ĻḲ��ǰ�滭��
		canvas.drawColor(Color.WHITE);//����ɫ
		if(this.status == 1 || this.status == 2){
			canvas.drawBitmap(background, 0, backgroundY, paint);//���Ʊ���
			if(k == 0){
				canvas.drawBitmap(image1, 0, 48, paint); 
			}else if(k > 0 && k<=1){
				canvas.drawBitmap(image2, 0, 48, paint); 
			}else if(k>1 && k<=2){
				canvas.drawBitmap(image3, 0, 48, paint); 
			}else if(k>2 && k<=3){
				canvas.drawBitmap(image4, 0, 48, paint); 
			}else if(k>3 && k<=4){
				canvas.drawBitmap(image5, 0, 48, paint); 
			}else if(k>4 && k<=5){
				canvas.drawBitmap(image6, 0, 48, paint); 
			}else if(k>5 && k<=6){
				canvas.drawBitmap(image7, 0, 48, paint); 
			}else if(k>6 && k<=7){
				canvas.drawBitmap(image4, 0, 48, paint); 
			}else if(k>7 && k<=8){
				canvas.drawBitmap(image3, 0, 48, paint); 
			}else if(k>8 && k<=10){
				canvas.drawBitmap(image2, 0, 48, paint); 
			}
			canvas.drawRect(0, 0, 480, 48, paint);//�������µĺڿ�
			canvas.drawRect(0, 270, 480, 320, paint);//���ƾ���
			canvas.drawBitmap(welcomeborder, -14, 10, paint);//�������		
		}
		else if(status == 3){
			paint.setAlpha(alpha);//����͸����
			canvas.drawBitmap(background2, 0, background2Y, paint);//���Ʊ���ͼ
			canvas.drawRect(0, 0, 480, 48, paint2);//�������µĺڿ�
			canvas.drawRect(0, 270, 480, 320, paint2);//���ƾ��ο�
		}
		else if(status == 4){//�˵�״̬
			canvas.drawBitmap(background2, 0, background2Y, paint);//���Ʊ���ͼ
			canvas.drawBitmap(startGame, 10, 70, paint2);//���ƿ�ʼ��Ϸ��ť
			canvas.drawBitmap(help, 390, 60, paint2);//���ư�����ť
			canvas.drawBitmap(exit, 380, 230, paint2);//�����˳���ť
			if(activity.isSound){
				canvas.drawBitmap(closeSound, 10, 230, paint2);//���ƹر������˵�
			}else{
				canvas.drawBitmap(openSound, 10, 230, paint2);//���ƴ�����
			}
			canvas.drawRect(0, 0, 480, 48, paint2);//�������µĺڿ�
			canvas.drawRect(0, 270, 480, 320, paint2);
		}
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������
        this.thread.setFlag(true);//����ѭ�����λ
        this.thread.start();//���������߳�
        this.welcomeThread.setFlag(true);//����ѭ�����λ
        this.welcomeThread.start(); //���������߳�
	}
	public void surfaceDestroyed(SurfaceHolder holder) {//�ݻ�ʱ������
        boolean retry = true;//ѭ�����
        thread.setFlag(false);
        while (retry) {
            try {
                thread.join();//�ȴ��̵߳Ľ���
                retry = false;//����ѭ�����ֹͣѭ��
            } 
            catch (InterruptedException e) {}//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
        }
	}
	public boolean onTouchEvent(MotionEvent event) {//��Ļ����
		if(event.getAction() == MotionEvent.ACTION_DOWN){//��Ļ������
			if(this.status != 4){//�����ǲ˵�״̬ʱ����
				return false;
			}
			double x = event.getX();//�õ�X����
			double y = event.getY();//�õ�Y����
			if(x>10 && x<10 + openSound.getWidth()
					&& y>70 && y<70 + openSound.getHeight()){//����˿�ʼ��Щ��ť
				activity.myHandler.sendEmptyMessage(2);//������Ϣ
			}
			else if(x>390 && x<390 + help.getWidth()
					&& y>60 && y<60 + help.getHeight()){//����˰�����ť
				activity.myHandler.sendEmptyMessage(3);//������Ϣ
			}
			else if(x>10 && x<10 + openSound.getWidth()
					&& y>230 && y<230 + openSound.getHeight()){//�����������ť
				activity.isSound = !activity.isSound;//��������־λ�÷�
			}
			else if(x>380 && x<380 + exit.getWidth()
					&& y>230 && y<230 + exit.getHeight()){//������˳���ť
				System.exit(0);//�˳���Ϸ
			}
		}
		return super.onTouchEvent(event);//���û���ķ���
	}
	class TutorialThread extends Thread{//ˢ֡�߳�
		private int span = 100;//˯�ߵĺ����� 
		private SurfaceHolder surfaceHolder;
		private WelcomeView welcomeView;//��ӭ���������
		private boolean flag = false;
        public TutorialThread(SurfaceHolder surfaceHolder, WelcomeView welcomeView) {//������
            this.surfaceHolder = surfaceHolder;//SurfaceHolder������
            this.welcomeView = welcomeView;//��ӭ���������
        }
        public void setFlag(boolean flag) {//���ñ�׼λ
        	this.flag = flag;
        }
		public void run() {//��д��run����
			Canvas c;
            while (this.flag) {//ѭ��
                c = null;
                try {
                	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {//ͬ��
                    	welcomeView.onDraw(c);//���û��Ʒ���
                    }
                } finally {//��finally��֤һ����ִ��
                    if (c != null) {
                    	//������Ļ��ʾ����
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(span);//˯��ָ��������
                }catch(Exception e){//�����쳣
                	e.printStackTrace();//��ӡ�쳣��Ϣ
                }
            }
		}
	}
}