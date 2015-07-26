package wyf.wpf;				//���������
import android.graphics.Canvas;		//���������
import android.view.SurfaceHolder;	//���������
public class DrawThread extends Thread{
	BallView bv;				//BallView��������
	SurfaceHolder surfaceHolder;//SurfaceHolder��������
	boolean flag=false;			//�߳�ִ�б�־λ
	int sleepSpan = 30;			//����ʱ��
	long start = System.nanoTime();	//��¼��ʼʱ�䣬�ñ������ڼ���֡����
	int count=0;		//��¼֡�����ñ������ڼ���֡����
	//������
	public DrawThread(BallView bv,SurfaceHolder surfaceHolder){
		this.bv = bv;		//ΪBallView����Ӧ�ø�ֵ
		this.surfaceHolder = surfaceHolder;	//ΪSurfaceHolder����Ӧ�ø�ֵ
		this.flag = true;		//���ñ�־λ
	}
	//�������̵߳�ִ�з��������ڻ�����Ļ�ͼ���֡����
	public void run(){
		Canvas canvas = null;//����һ��Canvas����
		while(flag){
			try{
				canvas = surfaceHolder.lockCanvas(null);//��ȡBallView�Ļ���
				synchronized(surfaceHolder){
					bv.doDraw(canvas);		//����BallView��doDraw�������л���
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
				bv.fps = "FPS:"+fps;//���������֡�������õ�BallView����Ӧ�ַ���������
			}
			try{
				Thread.sleep(sleepSpan);		//�߳�����һ��ʱ��
			}
			catch(Exception e){					
				e.printStackTrace();		//���񲢴�ӡ�쳣
			}
		}
	}	
}