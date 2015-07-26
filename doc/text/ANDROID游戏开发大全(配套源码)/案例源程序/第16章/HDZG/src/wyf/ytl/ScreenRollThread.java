package wyf.ytl;

public class ScreenRollThread extends Thread{
	boolean flag;//ѭ�����Ʊ���
	int sleepSpan = 100;
	ScreenRollView screenRoll;
	int characterControl;//����������ʾ�ٶȱ���
	int charNumber;//��¼��ʾ���ֵĸ�����������ʾ�˺󱸹����ڸǵ�
	
	public ScreenRollThread(ScreenRollView screenRoll){
		super.setName("==ScreenRollThread");
		this.screenRoll = screenRoll;
		flag = true;
	}
	//�߳�ִ�з���
	public void run(){
		while(flag){//ѭ����־λΪ��
			switch(screenRoll.status){
			case 1://�����
				screenRoll.scrollStartX -= 5;
				if(screenRoll.scrollStartX == 20){
					screenRoll.status = 2;//����������ʾ�׶�//ͣס������
				}				
				break;
			case 2://������ʾ
				characterControl ++;
				if(characterControl == 3){
					screenRoll.characterNumber ++;//ÿ����ʾ�����ֶ���һ��
					characterControl =0;
					charNumber ++;
					if(charNumber == screenRoll.msg.length()){//��ʾ����
						screenRoll.status = 3;//3�Ǵ���,һ��Ҫ���ⷢHandler
					}
				}				
				break;
			case 3://����״̬
				screenRoll.alpha -=5;
				if(screenRoll.alpha == 0){
					screenRoll.status = 4;///����
					screenRoll.activity.myHandler.sendEmptyMessage(11);
					this.flag = false;
				}
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