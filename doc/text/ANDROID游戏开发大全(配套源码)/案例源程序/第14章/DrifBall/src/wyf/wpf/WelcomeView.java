package wyf.wpf;					//���������
import android.content.Context;				//���������
import android.content.res.Resources;		//���������
import android.graphics.Bitmap;			//���������
import android.graphics.BitmapFactory;		//���������
import android.graphics.Canvas;			//���������
import android.graphics.Color;		//���������
import android.graphics.Matrix;		//���������
import android.graphics.Paint;		//���������
import android.view.SurfaceHolder;		//���������
import android.view.SurfaceView;		//���������
/*
 * ����̳���View������ӭ����Ļ���
 */
public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback{
	DriftBall father;
	WelcomeThread wt;
	WelcomeDrawThread wdt;
	
	Bitmap [] bmpBackScreen;		//��̨�����Ķ���֡
	Bitmap [] bmpStartOrQuit;		//��ʼ���˳�����������ͼƬ
	Bitmap [] bmpSoundOption;		//����ѡ�����������ͼƬ
	
	int status = -1;				//״ֵ̬��Ϊ0��ʾ����,1��ʾ��ʼ���˳���ť���£�2Ϊ��ʾ������
	int selectedIndex = -1;			//��ѡ�е����ֻ���ǿ�ʼ���˳�
	int backIndex;					//��̨֡����������
	Matrix m;						//Matrix���󣬸���
	int [][] planetCoordinate={		//�������λ������
			{60,120},
			{120,300},
			{180,220}
	};
	//����������ʼ����Ա����
	public WelcomeView(DriftBall father) {
		super(father);
		wt = new WelcomeThread(this);
		getHolder().addCallback(this);
		wdt = new WelcomeDrawThread(this,getHolder());
		initBitmap(father);					//��ʼ��ͼƬ
		this.father = father;
		status = 0;
		m = new Matrix();
	}
	//��������ʼ��ͼƬ��Դ
	public void initBitmap(Context context){
		Resources r = context.getResources();
		bmpBackScreen = new Bitmap[9];
		bmpBackScreen[0] = BitmapFactory.decodeResource(r, R.drawable.back_1);
		bmpBackScreen[1] = BitmapFactory.decodeResource(r, R.drawable.back_2);
		bmpBackScreen[2] = BitmapFactory.decodeResource(r, R.drawable.back_3);
		bmpBackScreen[3] = BitmapFactory.decodeResource(r, R.drawable.back_4);
		bmpBackScreen[4] = BitmapFactory.decodeResource(r, R.drawable.back_5);
		bmpBackScreen[5] = BitmapFactory.decodeResource(r, R.drawable.back_6);
		bmpBackScreen[6] = BitmapFactory.decodeResource(r, R.drawable.back_7);
		bmpBackScreen[7] = BitmapFactory.decodeResource(r, R.drawable.back_8);
		bmpBackScreen[8] = BitmapFactory.decodeResource(r, R.drawable.back_9);
		bmpStartOrQuit = new Bitmap[2];
		bmpStartOrQuit[0] = BitmapFactory.decodeResource(r, R.drawable.start);
		bmpStartOrQuit[1] = BitmapFactory.decodeResource(r, R.drawable.quit);
		bmpSoundOption = new Bitmap[2];
		bmpSoundOption[0] = BitmapFactory.decodeResource(r, R.drawable.sound_on);
		bmpSoundOption[1] = BitmapFactory.decodeResource(r, R.drawable.sound_off);
	}
	//��Ļ���Ʒ���
	public void doDraw(Canvas canvas){		
		canvas.drawBitmap(bmpBackScreen[backIndex], 0, 0, null);//������
		switch(status){					//����״̬���Ʋ�ͬ������
		case 1:		//��ť����״̬
			Bitmap tmpBmp = bmpStartOrQuit[selectedIndex];
			//�����С���ͼƬ
			bmpStartOrQuit[selectedIndex] = Bitmap.createBitmap(tmpBmp, 0, 0, tmpBmp.getWidth(), tmpBmp.getHeight(),m,true);
			if(tmpBmp.getWidth() <= 5){				//���ͼƬ�Ѿ���С��һ���̶�
				father.myHandler.sendEmptyMessage(selectedIndex);		//��Activity��Handler
				status = 2;								//����״̬Ϊ2������ʾ��������ʾ
			}
		case 0:		//��������״̬,���������Ŀ�ʼ���˳���ť
			canvas.drawBitmap(bmpStartOrQuit[0], planetCoordinate[0][0], planetCoordinate[0][1], null);
			canvas.drawBitmap(bmpStartOrQuit[1], planetCoordinate[1][0], planetCoordinate[1][1], null);
			int soundIndex = (father.wantSound?1:0);
			canvas.drawBitmap(bmpSoundOption[soundIndex], planetCoordinate[2][0], planetCoordinate[2][1], null);
			break;
		case 2:		//��ʾ������
			Paint p = new Paint();
			p.setTextSize(28);				//���������С
			p.setColor(Color.RED);			//���û�����ɫ
			p.setAntiAlias(true);			//���ÿ����
			p.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("������...", 160, 240, p);
			break;
		}		
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {									//��дsurfaceChanged����		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {		//��дsurfaceCreated����
		if(!wdt.isAlive()){									//�ж�WelcomeDrawThread�Ƿ�������
			wdt.start();									//����WelcomeDrawThread
		}
		if(!wt.isAlive()){									//�ж�WelcomeThread�Ƿ�������
			wt.start();										//����WelcomeThread
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {	//��дsurfaceDestroyed����
		wt.flag = false;			//ֹͣWelcomeThread��ִ��
		wdt.flag = false;			//ֹͣWelcomeDrawThread��ִ��
	}
	
}