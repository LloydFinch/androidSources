package wyf.ytl;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * 主游戏界面
 *
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	int selectMap = 1;//第几关
	PlaneActivity activity;
	private TutorialThread thread;//刷帧的线程
	GameViewBackGroundThread gameThread;//背景滚动线程
	KeyThread keyThread;//键盘监听线程
	MoveThread moveThread;//移动物体的线程 
	ExplodeThread explodeThread;//爆炸换帧的线程 
	
	int backGroundIX = 0;//核心图的x坐标
	int i = 0;//核心图的索引
	int cloudX = 470;//云彩的X坐标 
	
	Bitmap battleback;//背景的大图元
	Bitmap[] battlebacks = new Bitmap[ConstantUtil.pictureCount];//装分割以后的图片
	Bitmap cloud;//云彩
	Bitmap enemyPlane1;//敌机1
	Bitmap enemyPlane2;//敌机2
	Bitmap enemyPlane3;//敌机3
	Bitmap enemyPlane4;//敌机4
	
	
	Bitmap[] number = new Bitmap[10];//数字数组
	
	int[] explodesID = new int[]{//爆炸的所有帧
		R.drawable.explode1,
		R.drawable.explode2,
		R.drawable.explode3,
		R.drawable.explode4,
		R.drawable.explode5,
		R.drawable.explode6,
	};
	Bitmap[] explodes = new Bitmap[explodesID.length];//爆炸的数组
	
	Bitmap hullBackground;//显示生命的背景图片
	Bitmap hull;//生命的图片
	Bitmap life;//血块的图片
	Bitmap changebullet;
	
	int status = 1;//游戏的状态1表示游戏中,2表示游戏失败即我方飞机没有了生命
	
	Paint paint;//画笔
	Plane plane = new Plane(50, 140, 1, 0, ConstantUtil.life, this);//初始化我方飞机 

	ArrayList<Bullet> badBollets = new ArrayList<Bullet>();//敌方飞机发出的子弹
	ArrayList<Bullet> goodBollets = new ArrayList<Bullet>();//我方飞机发出的子弹
	ArrayList<Explode> explodeList = new ArrayList<Explode>();//爆炸
	ArrayList<ChangeBullet> changeBollets = new ArrayList<ChangeBullet>();//吃了改变枪的物体
	ArrayList<EnemyPlane> enemyPlanes;//敌方的飞机
	ArrayList<Life> lifes;//存放血块
	SoundPool soundPool;//声音
	HashMap<Integer, Integer> soundPoolMap; 
	
	MediaPlayer mMediaPlayer; 
	public GameView(PlaneActivity activity) {//构造器 
		super(activity);
		this.activity = activity;//activity的引用
		initSounds();
		mMediaPlayer = MediaPlayer.create(activity, R.raw.gamestart);
		mMediaPlayer.setLooping(true);
		
        getHolder().addCallback(this);//注册接口
        this.thread = new TutorialThread(getHolder(), this);//初始化刷帧线程
        this.gameThread = new GameViewBackGroundThread(this);//初始化背景滚动线程

        this.keyThread = new KeyThread(activity);
        this.moveThread = new MoveThread(this);
        this.explodeThread = new ExplodeThread(this);
        if(activity.processView != null){
        	activity.processView.process += 20;
        }
        if(this.selectMap == 1){ 
        	enemyPlanes = Maps.getFirst();//取第一关的敌机
        	lifes = Maps.getFirstLife();//取第一关的的血块
        	changeBollets = Maps.getFirstBollet();//取第一关吃了改变枪的物体列表
        }
        initBitmap();//初始化所有图片
        if(activity.isSound){
        	mMediaPlayer.start();
        }
        if(activity.processView != null){
        	activity.processView.process += 20;
        } 
	}
	public void initSounds(){
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     soundPoolMap = new HashMap<Integer, Integer>();   
	     soundPoolMap.put(1, soundPool.load(getContext(), R.raw.bulletsound1, 1));
	     soundPoolMap.put(2, soundPool.load(getContext(), R.raw.explode, 1));
	        if(activity.processView != null){
	        	activity.processView.process += 20;
	        }
	     soundPoolMap.put(3, soundPool.load(getContext(), R.raw.dead, 1));
	        if(activity.processView != null){
	        	activity.processView.process += 20;
	        }
	} 
	public void playSound(int sound, int loop) {
	    AudioManager mgr = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   
	    
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
	public void initBitmap(){//初始化所有图片
        if(activity.processView != null){
        	activity.processView.process += 20;
        }
		paint = new Paint();
		paint.setColor(Color.BLACK);
		battleback = BitmapFactory.decodeResource(getResources(), R.drawable.battleback);//大背景图片
		cloud = BitmapFactory.decodeResource(getResources(), R.drawable.cloud);//云彩
		for(int i=0; i<battlebacks.length; i++){//切成小图片
			battlebacks[i] = Bitmap.createBitmap(battleback, ConstantUtil.pictureWidth*i, 0, ConstantUtil.pictureWidth, ConstantUtil.pictureHeight);
		}
		battleback = null;//释放掉大图
		enemyPlane1 = BitmapFactory.decodeResource(getResources(), R.drawable.plane4);//敌机1的图片
		enemyPlane2 = BitmapFactory.decodeResource(getResources(), R.drawable.plane5);//敌机2的图片
		enemyPlane3 = BitmapFactory.decodeResource(getResources(), R.drawable.plane6);//敌机3的图片
		enemyPlane4 = BitmapFactory.decodeResource(getResources(), R.drawable.plane7);//敌机4的图片
		
		
		hullBackground = BitmapFactory.decodeResource(getResources(),R.drawable.hullbackground);
		hull = BitmapFactory.decodeResource(getResources(), R.drawable.hull);
		life = BitmapFactory.decodeResource(getResources(), R.drawable.life);
		changebullet = BitmapFactory.decodeResource(getResources(), R.drawable.changebullet);
		
		number[0] = BitmapFactory.decodeResource(getResources(), R.drawable.number0);
		number[1] = BitmapFactory.decodeResource(getResources(), R.drawable.number1);
		number[2] = BitmapFactory.decodeResource(getResources(), R.drawable.number2);
		number[3] = BitmapFactory.decodeResource(getResources(), R.drawable.number3);
		number[4] = BitmapFactory.decodeResource(getResources(), R.drawable.number4);
		number[5] = BitmapFactory.decodeResource(getResources(), R.drawable.number5);
		number[6] = BitmapFactory.decodeResource(getResources(), R.drawable.number6);
		number[7] = BitmapFactory.decodeResource(getResources(), R.drawable.number7);
		number[8] = BitmapFactory.decodeResource(getResources(), R.drawable.number8);
		number[9] = BitmapFactory.decodeResource(getResources(), R.drawable.number9);
		
		for(int i=0; i<explodes.length; i++){//初始化爆炸图片
			explodes[i] = BitmapFactory.decodeResource(getResources(), explodesID[i]);
		}
		
		for(EnemyPlane ep : enemyPlanes){//为敌机初始化图片
			if(ep.type == 1){
				ep.bitmap = enemyPlane1;
			}
			else if(ep.type == 2){
				ep.bitmap = enemyPlane2;
			}
			else if(ep.type == 3){
				ep.bitmap = enemyPlane3;
			}
			else if(ep.type == 4){
				ep.bitmap = enemyPlane4;
			}
		}
		for(Life l : lifes){//为血块初始化图片
			l.bitmap = life;
		}
		
		for(ChangeBullet cb : changeBollets){//为吃了改变枪的物体初始化图片
			cb.bitmap = changebullet;
		}
	}
	public void onDraw(Canvas canvas){//自己写的绘制方法,并非重写的
		//画的内容是z轴的，后画的会覆盖前面画的
		int backGroundIX=this.backGroundIX;
		int i=this.i;
		int cloudX = this.cloudX;
		
		//解决i左侧的问题
		if(backGroundIX>0){
			int n=(backGroundIX/ConstantUtil.pictureWidth)+((backGroundIX%ConstantUtil.pictureWidth==0)?0:1);//计算i左面有几幅图
			for(int j=1;j<=n;j++){
				canvas.drawBitmap(
			      battlebacks[(i-j+ConstantUtil.pictureCount)%ConstantUtil.pictureCount], 
			      backGroundIX-ConstantUtil.pictureWidth*j, 
			      ConstantUtil.top, 
			      paint
			     );
			}
		}

		//解决i自己
		canvas.drawBitmap(battlebacks[i], backGroundIX, ConstantUtil.top, paint);
		
		//解决i右侧的问题
		if(backGroundIX<ConstantUtil.screenWidth-ConstantUtil.pictureWidth){
			int k=ConstantUtil.screenWidth-(backGroundIX+ConstantUtil.pictureWidth);
			int n=(k/ConstantUtil.pictureWidth)+((k%ConstantUtil.pictureWidth==0)?0:1);//计算i右面有几幅图
			for(int j=1;j<=n;j++){
				canvas.drawBitmap(
						battlebacks[(i+j)%ConstantUtil.pictureCount], 
						backGroundIX+ConstantUtil.pictureWidth*j, 
						ConstantUtil.top, 
						paint
				);
			}
		}	

		plane.draw(canvas);//画玩家飞机
		if(status == 1 || status == 3){//游戏中时,关口中时
			try{//绘制我方子弹
				for(Bullet b:goodBollets){
					b.draw(canvas);
				}			
			}
			catch(Exception e){  
			}
			try{//绘制敌方飞机
				for(EnemyPlane ep:enemyPlanes){
					if(ep.status == true){
						ep.draw(canvas);
					}
				}
			}
			catch(Exception e){
			} 
			try{//绘制敌方子弹  
				for(Bullet b:badBollets){
					b.draw(canvas);
				}
			}
			catch(Exception e){
			}	
			try{
				for(ChangeBullet cb : changeBollets){//绘制吃了改变枪的物体
					if(cb.status == true){
						cb.draw(canvas);
					}
				}
			}
			catch(Exception e){
			}
			try{//绘制血块
				for(Life l : lifes){
					if(l.status == true){
						l.draw(canvas);
					}
				}
			}
			catch(Exception e){
			}
			try{//绘制爆炸
				for(Explode e : explodeList){
					e.draw(canvas);
				}
			}
			catch(Exception e){
			}
		}
		
		if(cloudX>-cloud.getWidth() && cloudX<ConstantUtil.screenWidth){//绘制云彩
			canvas.drawBitmap(cloud, cloudX, ConstantUtil.top, paint);
		}
		
		canvas.drawBitmap(hullBackground, 0, 10, paint);//画生命处的背景

		for(int j=0; j<((5-plane.life)<0?5:plane.life); j++){//绘制表示生命的小数条
			canvas.drawBitmap(hull, 95+11*j, 13, paint);
		}

		String timeStr = gameThread.touchTime/10+"";//转换成字符串
    	for(int c=0;c<timeStr.length();c++){//循环绘制时间
    		int tempScore=timeStr.charAt(c)-'0';
    		canvas.drawBitmap(number[tempScore], 350+c*22, 12, paint);
    	}

		//绘制屏幕上边沿和下边沿 
		canvas.drawRect(0, 0, 480, 10, paint);
		canvas.drawRect(0, 310, 480, 320, paint);
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {//创建时启动相应进程
        this.thread.setFlag(true);//启动刷帧线程
        this.thread.start();
        
        this.gameThread.setFlag(true);
        this.gameThread.start();//启动背景滚动线程
        
        this.keyThread.setFlag(true);
        keyThread.start();//启动键盘监听线程
        
        this.moveThread.setFlag(true);
        moveThread.start();//启动所以移动物的移动线程
        
        this.explodeThread.setFlag(true);
        explodeThread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {//摧毁时释放相应进程
        boolean retry = true;
        this.thread.setFlag(false);
        this.gameThread.setFlag(false);
        this.keyThread.setFlag(false);
        this.moveThread.setFlag(false);
        this.explodeThread.setFlag(false);
        while (retry) {
            try {
            	keyThread.join();
                thread.join();
                gameThread.join();
                moveThread.join();
                explodeThread.join();
                retry = false;
            } 
            catch (InterruptedException e) {//不断地循环，直到刷帧线程结束
            }
        }
	}
	class TutorialThread extends Thread{//刷帧线程
		private int sleepSpan = 67;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private GameView gameView;
		private boolean flag = false;
        public TutorialThread(SurfaceHolder surfaceHolder, GameView gameView) {//构造器
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }
        public void setFlag(boolean flag) {//设置循环标记位
        	this.flag = flag;
        }
		@Override
		public void run() {
			Canvas c;
            while (this.flag) {
                c = null;
                try {
                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	gameView.onDraw(c);//绘制
                    }
                } finally {
                    if (c != null) {
                    	//更新屏幕显示内容
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(sleepSpan);//睡眠指定毫秒数
                }
                catch(Exception e){
                	e.printStackTrace();//打印堆栈信息
                }
            }
		}
	}
}