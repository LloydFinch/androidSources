package wyf.wpf;
/*
 * ����̳���Thread�࣬��Cannon�ĺ�̨�߳���Ҫ�����Ǽ���С��
 * һ��С�������ڵ���̣�����С����һ���ڵ�
 */
public class CannonThread extends Thread{
	GameView father;			//GameView������
	int sleepSpan = 2000;		//����ʱ��
	boolean flag = false;		//��ѭ������
	boolean isGameOn = false;	//��Ϸ�Ƿ������
	//������
	public CannonThread(GameView father){
		this.father = father;
		if(father.alCannon.size()!=0){		//�� ��Cannono���󼯺��Ƿ�Ϊ�գ�������Ӧ��־λ
			flag = true;
			isGameOn = true;
		}
	}
	//�̵߳�ִ�з���
	public void run(){
		while(flag){
			while(isGameOn){
				//����Cannon���󼯺�
				for(Cannon c:father.alCannon){
					//������ں�С��֮��ľ���
					int distance = Math.abs(c.col*father.tileSize-father.ballX) + Math.abs(c.row*father.tileSize-father.ballY);
					if(distance <= c.range){			//���С�������֮��
						Missile m = c.fire();			//�����ڵ�����
						father.alMissile.add(m);		//���ڵ�������ӵ��ڵ�����ļ�����
					}
				}
				try{		//�߳�����
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}				
			}
			try{		//���ѭ������
				Thread.sleep(1000);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}