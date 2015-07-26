package wyf.wpf;		//���������
import android.graphics.Bitmap;	//���������
import android.graphics.Canvas;	//���������
//������ƶ������Movable��
public class Movable{
	int startX=0;		//��ʼX����
	int startY=0;		//��ʼY����
	int x;				//ʵʱX����
	int y;				//ʵʱY����
	float startVX=0f;	//��ʼ��ֱ������ٶ�
	float startVY=0f;	//��ʼˮƽ������ٶ�
	float v_x=0f;		//ʵʱˮƽ�����ٶ�
	float v_y=0f;		//ʵʱ��ֱ�����ٶ�
	int r;				//���ƶ�����뾶
	double timeX;			//X�����ϵ��˶�ʱ��
	double timeY;			//Y�����ϵ��˶�ʱ��
	Bitmap bitmap=null;	//���ƶ�����ͼƬ
	BallThread bt=null;	//����С���ƶ�ʱ
	boolean bFall=false;//С���Ƿ��Ѿ���ľ��������
	float impactFactor = 0.25f;	//С��ײ�غ��ٶȵ���ʧϵ��
	//������
	public Movable(int x,int y,int r,Bitmap bitmap){
		this.startX = x;		//��ʼ��X����
		this.x = x;				//��ʼ��X����
		this.startY = y;		//��ʼ��Y����
		this.y = y;				//��ʼ��Y����
		this.r = r;				//��ʼ��
		this.bitmap = bitmap;	//��ʼ��ͼƬ
		timeX=System.nanoTime();	//��ȡϵͳʱ���ʼ��
		this.v_x = BallView.V_MIN + (int)((BallView.V_MAX-BallView.V_MIN)*Math.random());		
		bt = new BallThread(this);//����������BallThread
		bt.start();
	}
	//�����������Լ�����Ļ��
	public void drawSelf(Canvas canvas){
		canvas.drawBitmap(this.bitmap,x, y, null);
	}
}