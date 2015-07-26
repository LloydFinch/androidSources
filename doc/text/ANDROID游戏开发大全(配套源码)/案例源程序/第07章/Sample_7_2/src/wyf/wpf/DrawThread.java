package wyf.wpf;//���������
import android.graphics.Canvas;//���������
import android.view.SurfaceHolder;//���������
//�̳���Thread�����࣬����ˢ����Ļ
public class DrawThread extends Thread{
	ParticleView pv;		//ParticleView��������
	SurfaceHolder surfaceHolder;	//SurfaceHolder��������
	boolean flag;			//�߳�ִ�б�־λ
	int sleepSpan = 15;			//˯��ʱ��
	long start = System.nanoTime();	//��¼��ʼʱ�䣬�ñ������ڼ���֡����
	int count=0;		//��¼֡�����ñ������ڼ���֡����
	//����������ʼ����Ҫ��Ա����
	public DrawThread(ParticleView pv,SurfaceHolder surfaceHolder){
		this.pv = pv;
		this.surfaceHolder = surfaceHolder;
		this.flag = true;	//�����߳�ִ�б�־λΪtrue
	}
	//�߳�ִ�з���
	public void run(){
		Canvas canvas = null;		//����һ��Canvas����
		while(flag){
			try{
				canvas = surfaceHolder.lockCanvas(null);//��ȡParticleView�Ļ���
				synchronized(surfaceHolder){
					pv.doDraw(canvas);			//����ParticleView��doDraw�������л���
				}
			}
			catch(Exception e){
				e.printStackTrace();			//���񲢴�ӡ�쳣
			}
			finally{
				if(canvas != null){		//���canvas��Ϊ��
					surfaceHolder.unlockCanvasAndPost(canvas);//surfaceHolder���������������󴫻�
				}
			}
			this.count++;
			if(count == 20){	//�������20֡
				count = 0;		//��ռ�����
				long tempStamp = System.nanoTime();//��ȡ��ǰʱ��
				long span = tempStamp - start;		//��ȡʱ����
				start = tempStamp;					//Ϊstart���¸�ֵ
				double fps = Math.round(100000000000.0/span*20)/100.0;//����֡����
				pv.fps = "FPS:"+fps;//���������֡�������õ�BallView����Ӧ�ַ���������
			}
			try{
				Thread.sleep(sleepSpan);//�߳�����һ��ʱ��
			}
			catch(Exception e){
				e.printStackTrace();//���񲢴�ӡ�쳣
			}
		}
	}
}