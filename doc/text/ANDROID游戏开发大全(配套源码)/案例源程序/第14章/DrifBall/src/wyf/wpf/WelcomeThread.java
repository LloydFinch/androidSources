package wyf.wpf;			//���������
/*
 * ����̳���Thread��Ϊ��ӭ����ĺ�̨�̣߳�
 * �����޸�������ݣ��ﵽ����Ч��
 */
public class WelcomeThread extends Thread{
	WelcomeView father;				//WelcomeView����
	int sleepSpan = 100;			//����ʱ��
	boolean flag = false;
	//������������WelcomeView��������
	public WelcomeThread(WelcomeView father){
		this.father = father;
		this.flag = true;
	}
	//�߳�ִ�з���
	public void run(){
		while(flag){
			switch(father.status){
			case 2:		//����״̬
			case 0:	//����״̬
				father.backIndex = (father.backIndex+1)%father.bmpBackScreen.length;//�޸�֡����
				break;
			case 1:	//��ť����״̬
				father.m.postScale(0.9f, 0.9f);	//��Matrix��������
				father.m.postRotate(30);			//��Matrix������ת
				father.backIndex = (father.backIndex+1)%father.bmpBackScreen.length;//�޸�֡����
				break;
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