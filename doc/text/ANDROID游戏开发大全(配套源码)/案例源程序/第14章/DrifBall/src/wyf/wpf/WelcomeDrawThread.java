package wyf.wpf;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/*
 * ����Ϊ��ӭ����ĺ�̨�̣߳�����ʱ������Ļ
 */
public class WelcomeDrawThread extends Thread{
	WelcomeView father;			//WelcomeView����
	SurfaceHolder surfaceHolder;	//WelcomeView��SurfaceHolder
	boolean flag;				//ѭ����־λ
	int sleepSpan = 30;			//����ʱ��
	//������
	public WelcomeDrawThread(WelcomeView father,SurfaceHolder surfaceHolder){
		this.father = father;
		this.surfaceHolder = surfaceHolder;
		this.flag = true;
	}
	//�߳�ִ�з���
	public void run(){
		Canvas canvas = null;
		while(flag){
			try{
				canvas = surfaceHolder.lockCanvas(null);
				synchronized(surfaceHolder){
					father.doDraw(canvas);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
				if(canvas != null){
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			try{
				Thread.sleep(sleepSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}