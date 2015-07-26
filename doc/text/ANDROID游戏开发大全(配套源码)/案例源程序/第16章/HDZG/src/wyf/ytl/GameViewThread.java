package wyf.ytl;

import java.io.Serializable;

/*
 * ������Ҫ����ʱ��ȡGameView��״̬�����Ϊ�������л����ӵ�֡
 * ʹ���������
 */
public class GameViewThread extends Thread implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8911905788936392333L;
	GameView gv;//��Ϸ��ͼ�������
	int sleepSpan = 300;//����ʱ��
	int waitSpan = 1500;//��תʱ�ĵȴ�ʱ��
	boolean flag;//�߳��Ƿ�ִ�б�־λ
	boolean isChanging;//�Ƿ���Ҫ�����Ӷ���
	
	public GameViewThread(){}
	
	//������
	public GameViewThread(GameView gv){
		super.setName("==GameViewThread");
		this.gv = gv;
		flag = true;
	}
	//�߳�ִ�з���
	public void run(){
		while(flag){//�߳�����ִ��
			while(isChanging){//��Ҫ��֡
				int diceNumber = gv.diceCount;
				for(int i=0;i<diceNumber;i++){
					gv.diceValue[i] = (gv.diceValue[i]+1)%6;				
				}					
				if(gv.showMiniMap){		//�ж��Ƿ���ʾ����ͼ
					gv.miniMapStartY+=30;
					if(gv.miniMapStartY>=0){//�Ѿ��ƶ���λ
						gv.miniMapStartY = 0;
					}
				}
				try{
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			try{//����Ҫ������ʱ�̵߳Ŀ�ת�ȴ�ʱ��
				Thread.sleep(waitSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//�����������Ƿ���Ҫ������
	public void setChanging(boolean isChanging){
		this.isChanging = isChanging;
	}
}