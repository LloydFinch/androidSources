package wyf.ytl;
import android.graphics.Bitmap;//���������
import android.graphics.BitmapFactory;//���������
import android.graphics.Canvas;//���������
import android.graphics.Paint;//���������
import android.view.SurfaceHolder;//���������
import android.view.SurfaceView;//���������
public class ProcessView extends SurfaceView implements SurfaceHolder.Callback {
	PlaneActivity activity;//activity������
	private TutorialThread thread;//ˢ֡���߳�
	Paint paint;//����
	Bitmap processBitmap;//����
	Bitmap processBackground;//����ͼƬ
	Bitmap processMan;//�˵�ͼƬ
	int process = 0;//0��100��ʾ����
	int startX = 90;
	int startY = 150;
	int type;//��ǰ���ص����ĸ�View
	int k = 0;//�����еĵ��
	public ProcessView(PlaneActivity activity, int type) {//������ 
		super(activity);
		this.activity = activity;//�õ�activity������
        getHolder().addCallback(this);
        this.thread = new TutorialThread(getHolder(), this);//��ʼ���ػ��߳�
        this.type = type;//��ǰ���ص�����ô����
        initBitmap();//��ʼ��ͼƬ��Դ
	}
	public void initBitmap(){//��ʼ��ͼƬ��Դ�ķ���
		paint = new Paint();//��������
		paint.setTextSize(12);//���������С
		processBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.progress);
		processBackground = BitmapFactory.decodeResource(getResources(), R.drawable.processbackground);
		processMan = BitmapFactory.decodeResource(getResources(), R.drawable.processman);
	}
	public void onDraw(Canvas canvas){//�Լ�д�Ļ��Ʒ���
		//����������z��ģ��󻭵ĻḲ��ǰ�滭��
		canvas.drawBitmap(processBackground, -10, 10, paint);//���Ʊ���ͼƬ
		canvas.drawBitmap(processMan, 10, 130, paint);//�����˵�ͼƬ
		canvas.drawBitmap(processBitmap, startX, startY, paint);
		canvas.drawRect(startX+process*(processBitmap.getWidth()/100), startY, startX+processBitmap.getWidth(), startY+processBitmap.getHeight(), paint);
		if(k == 0){
			canvas.drawText("������", startX+(processBitmap.getWidth()/2)-20,
					startY+processBitmap.getHeight()+20, paint);
		}
		else if(k == 1){
			canvas.drawText("������.", startX+(processBitmap.getWidth()/2)-20, 
					startY+processBitmap.getHeight()+20, paint);
		}
		else if(k == 2){
			canvas.drawText("������..", startX+(processBitmap.getWidth()/2)-20, 
					startY+processBitmap.getHeight()+20, paint);
		}
		else{
			canvas.drawText("������...", startX+(processBitmap.getWidth()/2)-20, 
					startY+processBitmap.getHeight()+20, paint);
		}
		canvas.drawRect(0, 0, 480, 48, paint);//�������µĺڿ�
		canvas.drawRect(0, 270, 480, 320, paint);
		k = (k+1)%4;
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {//�����Ǳ�����
        this.thread.setFlag(true);//�����̱߳�־λ
        this.thread.start();//�����߳�
	}
	public void surfaceDestroyed(SurfaceHolder holder) {//�ݻ�ʱ������
        boolean retry = true;//ѭ����־λ
        thread.setFlag(false);//����ѭ����־λ
        while (retry) {
            try {
                thread.join();//�ȴ��߳̽���
                retry = false;
            } 
            catch (InterruptedException e) {}//���ϵ�ѭ����ֱ��ˢ֡�߳̽���
        }
	}
	class TutorialThread extends Thread{//ˢ֡�߳�
		private int span = 400;//˯�ߵĺ����� 
		private SurfaceHolder surfaceHolder;
		private ProcessView processView;//processView����
		private boolean flag = false;//ѭ����־λ
        public TutorialThread(SurfaceHolder surfaceHolder, ProcessView processView) {//������
            this.surfaceHolder = surfaceHolder;
            this.processView = processView;//�õ����ؽ���
        }
        public void setFlag(boolean flag) {//���ñ�־λ
        	this.flag = flag;
        }
		public void run() {//��д��run����
			Canvas c;//����
            while (this.flag) {//ѭ��
                c = null;
                try {
                	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                    	processView.onDraw(c);//���û��Ʒ���
                    }
                } finally {//ʹ��finally��䱣֤�������һ����ִ��
                    if (c != null) {
                    	//������Ļ��ʾ����
                        this.surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
                if(processView.process >= 100){//���������ʱ
                	if(processView.type == 1){//�е�WelcomeView
                		processView.activity.myHandler.sendEmptyMessage(4);//����activity����Handler��Ϣ
                	}
                	else if(processView.type == 2){//�е�GameView
                		processView.activity.myHandler.sendEmptyMessage(6);//����activity����Handler��Ϣ
                	}
                }
                try{
                	Thread.sleep(span);//˯��ָ��������
                }
                catch(Exception e){//�����쳣��Ϣ
                	e.printStackTrace();//��ӡ�쳣��Ϣ
                }
            }
		}
	}
}