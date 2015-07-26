package wyf.wpf;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
/*
 * ����̳���Thread����Ҫ�������ṩGameView�Ķ�ʱ����
 * ���õķ�ʽ��SurfaceHolder���󽫻������ϣ�Ȼ��������
 * ���ơ�
 */
public class DrawThread extends Thread{
	SurfaceHolder surfaceHolder;			//SurfaceHolder����
	GameView gv;							//GameView��������
	boolean flag;							//��־λִ�б�־λ
	boolean isGameOn;						//ˢ����־λ
	int sleepSpan=25;						//����ʱ�䣨����Ϊ��λ��
	//������
	public DrawThread(SurfaceHolder surfaceHolder,GameView gv){
		this.surfaceHolder = surfaceHolder;
		this.gv = gv;
		this.flag = true;
	}
	//�������̵߳�ִ�з���
	public void run(){
		while(flag){
			Canvas canvas;		//����һ��Canvas����
			while(isGameOn){	//�ж���Ϸ�Ƿ��ڽ�����
				canvas = null;
				//������������Ļ
				try{
					canvas = surfaceHolder.lockCanvas(null);		//������Ļ
					synchronized(surfaceHolder){
						gv.doDraw(canvas);					//������Ӧ�Ļ��Ʒ���
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally{		//��finally������ͷ���
					if(canvas != null){
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
				//�߳�����
				try{
					Thread.sleep(sleepSpan);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			try{//�ڲ�ѭ��ִ����Ϻ������
				Thread.sleep(1000);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

	}
}