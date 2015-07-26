package wyf.wpf;			//���������
import java.util.ArrayList;		//���������
import android.content.Context;		//���������
import android.graphics.Canvas;	//���������
import android.graphics.Color;	//���������
import android.graphics.Paint;	//���������
import android.graphics.RectF;	//���������
import android.view.SurfaceHolder;	//���������
import android.view.SurfaceView;	//���������
//�̳���SurfaceView��ʵ��SurfaceHolder.Callback�ӿڵ���
public class ParticleView extends SurfaceView implements SurfaceHolder.Callback{
	public static final int DIE_OUT_LINE = 420;//���ӵ�Y���곬����ֵ������Ӽ����Ƴ�
	DrawThread dt;		//��̨ˢ����Ļ�߳�
	ParticleSet ps;		//ParticleSet��������
	ParticleThread pt;	//ParticleThread��������
	String fps = "FPS:N/A";		//����֡�����ַ���
	//����������ʼ����Ҫ��Ա����
	public ParticleView(Context context) {
		super(context);	//���ø��๹����
		this.getHolder().addCallback(this);	//���Callback�ӿ�
		dt = new DrawThread(this, getHolder());	//����DrawThread����
		ps = new ParticleSet();					//����ParticleSet����
		pt = new ParticleThread(this);			//����ParticleThread����
	}	
	//������������Ļ
	public void doDraw(Canvas canvas){
		canvas.drawColor(Color.BLACK);		//����
		ArrayList<Particle> particleSet = ps.particleSet;	//���ParticleSet�����е����Ӽ��϶���
		Paint paint = new Paint();	//�������ʶ���
		for(int i=0;i<particleSet.size();i++){		//�������Ӽ��ϣ�����ÿ������
			Particle p = particleSet.get(i);
			paint.setColor(p.color);		//���û�����ɫΪ������ɫ
			int tempX = p.x;		//�������X����
			int tempY = p.y;		//�������Y����
			int tempRadius = p.r;	//������Ӱ뾶
			RectF oval = new RectF(tempX, tempY, tempX+2*tempRadius, tempY+2*tempRadius);
			canvas.drawOval(oval, paint);		//������Բ����
		}
		paint.setColor(Color.WHITE);	//���û�����ɫ
		paint.setTextSize(18);			//�������ִ�С
		paint.setAntiAlias(true);		//���ÿ����
		canvas.drawText(fps, 15, 15, paint);//����֡�����ַ���
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {//��дsurfaceChanged����	
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//��дsurfaceCreated����
		if(!dt.isAlive()){	//���DrawThreadû������������������߳�
			dt.start();
		}
		if(!pt.isAlive()){	//���ParticleThreadû������������������߳�
			pt.start();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//��дsurfaceDestroyed����
		dt.flag = false;	//ֹͣ�̵߳�ִ��
		dt = null;			//��dtָ��Ķ�������Ϊ����
	}	
}