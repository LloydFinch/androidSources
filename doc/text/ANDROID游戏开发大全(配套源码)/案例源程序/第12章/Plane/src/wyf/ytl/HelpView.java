package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class HelpView extends SurfaceView implements SurfaceHolder.Callback {
	PlaneActivity activity;
	private TutorialThread thread;//ˢ֡���߳�
	Paint paint;
	Bitmap background;
	Bitmap help2;
	Bitmap help3;
	Bitmap ok;
	public HelpView(PlaneActivity activity) {//������ 
		super(activity);
		this.activity = activity;
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);
        initBitmap();//��ʼ��ͼƬ��Դ
	}
	public void initBitmap(){//��ʼ��ͼƬ��Դ�ķ���
		paint = new Paint(); 
		background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
		help2 = BitmapFactory.decodeResource(getResources(), R.drawable.help2);
		help3 = BitmapFactory.decodeResource(getResources(), R.drawable.help3);
		ok = BitmapFactory.decodeResource(getResources(), R.drawable.ok);
	}
	public void onDraw(Canvas canvas){//�Լ�д�Ļ��Ʒ���
		//����������z��ģ��󻭵ĻḲ��ǰ�滭��
		canvas.drawColor(Color.WHITE);//����Ļˢ�ɰ�ɫ 
		canvas.drawBitmap(background, 0,0, paint);
		canvas.drawBitmap(help2, -30, 30, paint);   
		canvas.drawBitmap(help3, 260, 70, paint);
		canvas.drawRect(0, 0, 480, 48, paint);//�������µĺڿ�
		canvas.drawRect(0, 270, 480, 320, paint); 
		canvas.drawBitmap(ok, 360, 255, paint);  
 
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {
        this.thread.setFlag(true);
        this.thread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setFlag(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } 
            catch (InterruptedException e) {//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
            }
        }
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {//��Ļ����
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(event.getX()>360 && event.getX()<360+ok.getWidth()
					&& event.getY()>250 && event.getY()<250+ok.getHeight()){//�����ȷ����ť
				Message msg1 = activity.myHandler.obtainMessage(7); 
				activity.myHandler.sendMessage(msg1);
			}  
		}
		return super.onTouchEvent(event);
	}
	class TutorialThread extends Thread{//ˢ֡�߳�
		private int span = 100;//˯�ߵ� ������ 
		private SurfaceHolder surfaceHolder;
		private HelpView helpView;
		private boolean flag = false;
        public TutorialThread(SurfaceHolder surfaceHolder, HelpView helpView) {//������
            this.surfaceHolder = surfaceHolder;
            this.helpView = helpView;
        }
        public void setFlag(boolean flag) {
        	this.flag = flag;
        }
		@Override
		public void run() {
			Canvas c;
            while (this.flag) {
                c = null;
                try {
                	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	helpView.onDraw(c);
                    }
                } finally {
                    if (c != null) {
                    	//������Ļ��ʾ����
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                try{
                	Thread.sleep(span);
                }
                catch(Exception e){
                	e.printStackTrace();
                }
            }
		}
	}
}