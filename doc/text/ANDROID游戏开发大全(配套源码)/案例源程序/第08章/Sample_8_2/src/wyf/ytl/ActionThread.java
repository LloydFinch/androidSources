package wyf.ytl;
public class ActionThread extends Thread{
	private int sleepSpan = 5000;//˯�ߵĺ�����
	boolean flag = true;//ѭ�����λ
	Sample_8_2 activity;//activity������
	public ActionThread(Sample_8_2 activity){//������
		this.activity = activity;
	}	
	public void run(){//��д��run����
		while(flag){//ѭ��
			try{
				Thread.sleep(sleepSpan);//˯��ָ��������
			}
			catch(Exception e ){
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}
			activity.myHandler.sendEmptyMessage(1);//��activity����handler��Ϣ
		}
	}
}