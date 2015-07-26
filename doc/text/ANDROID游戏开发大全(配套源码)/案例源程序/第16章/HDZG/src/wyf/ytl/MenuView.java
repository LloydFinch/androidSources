package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * 
 * ����Ϊ��ʼ��Ϸʱ�����˵�
 *
 */

public class MenuView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener{
	HDZGActivity activity;//activity������
	private DrawThread drawThread;//ˢ֡���߳�
	MenuViewBackgroundThread gameThread;//���������߳�
	private int status = 0;//��ǰѡ�е�״̬
	
	Bitmap bigBitmap;//��ͼԪ
	Bitmap menuBackground;//�����Ĵ�ͼԪ
	Bitmap[] menuBackgrounds = new Bitmap[ConstantUtil.PICTURECOUNT];//װ�ָ��Ժ󱳾���ͼƬ
	Bitmap[] smallBitmaps = new Bitmap[12];//װ�ָ��Ժ��ͼƬ
	
	int backGroundIX = 0;//����ͼ��x����
	int i = 0;//����ͼ������
	MediaPlayer mMediaPlayer; 
	Paint paint;
	public MenuView(HDZGActivity activity) {//������ 
		super(activity);
		this.activity = activity;
		mMediaPlayer = MediaPlayer.create(activity, R.raw.startsound);
		mMediaPlayer.setLooping(true);
        if(activity.loadingView != null){//�߽�����
        	activity.loadingView.process += 20;
        }
        getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);
        this.gameThread = new MenuViewBackgroundThread(this);//��ʼ�����������߳�
        initBitmap();//��ʼ��ͼƬ��Դ
    	if(activity.isStartSound){
    		mMediaPlayer.start();
    	}
    	setOnTouchListener(this);
        if(activity.loadingView != null){//�߽�����
        	activity.loadingView.process += 30;
        }
	}
	
	public void initBitmap(){//��ʼ��ͼƬ��Դ�ķ���
		paint = new Paint();
        if(activity.loadingView != null){//�߽�����
        	activity.loadingView.process += 10;
        }
        bigBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.menu_options);//��ʼ���˵����õ�������ͼԪ
		for(int i=0; i<smallBitmaps.length; i++){//�г�СͼƬ
			smallBitmaps[i] = Bitmap.createBitmap(bigBitmap, 100*(i%6), 25*(i>5?1:0), bigBitmap.getWidth()/6, bigBitmap.getHeight()/2);
		}
		bigBitmap = null;//�ͷŵ���ͼ
        if(activity.loadingView != null){//�߽�����
        	activity.loadingView.process += 20;
        }
		menuBackground = BitmapFactory.decodeResource(getResources(), R.drawable.menu_back);//�󱳾�ͼƬ
		for(int i=0; i<menuBackgrounds.length; i++){//�г�СͼƬ
			menuBackgrounds[i] = Bitmap.createBitmap(menuBackground, ConstantUtil.PICTUREWIDTH*i, 0, ConstantUtil.PICTUREWIDTH, ConstantUtil.PICTUREHEIGHT);
		}
		menuBackground = null;
        if(activity.loadingView != null){//�߽�����
        	activity.loadingView.process += 20;
        }
	}
	
	//���Ʒ���
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.WHITE);
		
		int backGroundIX=this.backGroundIX;
		int i=this.i;
		
		//���i��������
		if(backGroundIX>0){
			int n=(backGroundIX/ConstantUtil.PICTUREWIDTH)+((backGroundIX%ConstantUtil.PICTUREWIDTH==0)?0:1);//����i�����м���ͼ
			for(int j=1;j<=n;j++){
				canvas.drawBitmap(
						menuBackgrounds[(i-j+ConstantUtil.PICTURECOUNT)%ConstantUtil.PICTURECOUNT], 
			      backGroundIX-ConstantUtil.PICTUREWIDTH*j, 
			      0, 
			      paint
			     );
			}
		}

		//���i�Լ�
		canvas.drawBitmap(menuBackgrounds[i], backGroundIX, 0, paint);
		
		//���i�Ҳ������
		if(backGroundIX<ConstantUtil.SCREEN_WIDTH-ConstantUtil.PICTUREWIDTH){
			int k=ConstantUtil.SCREEN_WIDTH-(backGroundIX+ConstantUtil.PICTUREWIDTH);
			int n=(k/ConstantUtil.PICTUREWIDTH)+((k%ConstantUtil.PICTUREWIDTH==0)?0:1);//����i�����м���ͼ
			for(int j=1;j<=n;j++){
				canvas.drawBitmap(
						menuBackgrounds[(i+j)%ConstantUtil.PICTURECOUNT], 
						backGroundIX+ConstantUtil.PICTUREWIDTH*j, 
						0, 
						paint
				);
			}
		}	
		for(int j=0; j<6; j++){
			if(status == j){
				canvas.drawBitmap(smallBitmaps[j+6], ConstantUtil.MENU_VIEW_LEFT_SPACE, ConstantUtil.MENU_VIEW_UP_SPACE+(smallBitmaps[j].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)*j, paint);
			}
			else{
				canvas.drawBitmap(smallBitmaps[j], ConstantUtil.MENU_VIEW_LEFT_SPACE, ConstantUtil.MENU_VIEW_UP_SPACE+(smallBitmaps[j].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)*j, paint);
			}
		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(x>ConstantUtil.MENU_VIEW_LEFT_SPACE && x<ConstantUtil.MENU_VIEW_LEFT_SPACE+smallBitmaps[0].getWidth()){//x��������Ҫ�ķ�Χ��
				if(y>ConstantUtil.MENU_VIEW_UP_SPACE && y<ConstantUtil.MENU_VIEW_UP_SPACE+smallBitmaps[0].getHeight()){
					//������ǳ��뽭���˵�
					mMediaPlayer.stop();
		    		activity.myHandler.sendEmptyMessage(2);//����activity����Handler��Ϣ
					status = 0;
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+1*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) 
						&& y<ConstantUtil.MENU_VIEW_UP_SPACE+1*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//�����������
					mMediaPlayer.stop();
					activity.myHandler.sendEmptyMessage(99);
					status = 1;
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+2*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) 
						&& y<ConstantUtil.MENU_VIEW_UP_SPACE+2*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//�����Ч����
					activity.myHandler.sendEmptyMessage(9);//����activity����Handler��Ϣ
					status = 2;
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+3*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) 
						&& y<ConstantUtil.MENU_VIEW_UP_SPACE+3*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//�������ָ��
					mMediaPlayer.stop();
					status = 3;
					activity.myHandler.sendEmptyMessage(4);//����activity����Handler��Ϣ
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+4*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) 
						&& y<ConstantUtil.MENU_VIEW_UP_SPACE+4*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//���ȺӢ����
					mMediaPlayer.stop(); 
					status = 4;
					activity.myHandler.sendEmptyMessage(13);//����activity����Handler��Ϣ
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+5*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) 
						&& y<ConstantUtil.MENU_VIEW_UP_SPACE+5*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					//����˷⽣����
					mMediaPlayer.stop();
					status = 5;
					System.exit(0);//�˳�
				}
			}			
		}

		return super.onTouchEvent(event);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	public void surfaceCreated(SurfaceHolder holder) {//����ʱ������
        this.drawThread.setFlag(true);
        drawThread.setIsViewOn(true);
        if(!drawThread.isAlive()){
        	this.drawThread.start();
        }
        
        this.gameThread.setFlag(true);
        if(!gameThread.isAlive()){
        	this.gameThread.start();//�������������߳�
        }
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {//�ݻ�ʱ������
		drawThread.setIsViewOn(false);
	}
	
	class DrawThread extends Thread{//ˢ֡�߳�
		private int sleepSpan = ConstantUtil.MENU_VIEW_SLEEP_SPAN;//˯�ߵĺ����� 
		private SurfaceHolder surfaceHolder;
		private MenuView menuView;
		private boolean flag = false;
		private boolean isViewOn = false;
        public DrawThread(SurfaceHolder surfaceHolder, MenuView menuView) {//������
        	super.setName("==MenuView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.menuView = menuView;
        }
        public void setIsViewOn(boolean isViewOn){
        	this.isViewOn = isViewOn;
        }
        public void setFlag(boolean flag) {
        	this.flag = flag;
        }
        
		public void run() {
			Canvas c;
            while (this.flag) {
            	while (isViewOn) {
                    c = null;
                    try {
                    	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
                        c = this.surfaceHolder.lockCanvas(null);
                        synchronized (this.surfaceHolder) {
                        	menuView.onDraw(c);
                        }
                    } finally {
                        if (c != null) {
                        	//������Ļ��ʾ����
                            this.surfaceHolder.unlockCanvasAndPost(c);
                        }
                    }
                    try{
                    	Thread.sleep(sleepSpan);//˯��ָ��������
                    }
                    catch(Exception e){
                    	e.printStackTrace();
                    }
            	}
            	try{
            		Thread.sleep(1500);
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
            }
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(x>ConstantUtil.MENU_VIEW_LEFT_SPACE && x<ConstantUtil.MENU_VIEW_LEFT_SPACE+smallBitmaps[0].getWidth()){//x��������Ҫ�ķ�Χ��
				if(y>ConstantUtil.MENU_VIEW_UP_SPACE && y<ConstantUtil.MENU_VIEW_UP_SPACE+smallBitmaps[0].getHeight()){
					//������ǳ��뽭���˵�
					status = 0;
					activity.myHandler.sendEmptyMessage(status);
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+1*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) && y<ConstantUtil.MENU_VIEW_UP_SPACE+1*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					status = 1;
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+2*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) && y<ConstantUtil.MENU_VIEW_UP_SPACE+2*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					status = 2;
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+3*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) && y<ConstantUtil.MENU_VIEW_UP_SPACE+3*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					status = 3;
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+4*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) && y<ConstantUtil.MENU_VIEW_UP_SPACE+4*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					status = 4;
				}
				else if(y>ConstantUtil.MENU_VIEW_UP_SPACE+5*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE) && y<ConstantUtil.MENU_VIEW_UP_SPACE+5*(smallBitmaps[0].getHeight()+ConstantUtil.MENU_VIEW_WORD_SPACE)+smallBitmaps[1].getHeight()){
					status = 5;
				}
			}
			return super.onTouchEvent(event);
		}
		return true;
	}
}
