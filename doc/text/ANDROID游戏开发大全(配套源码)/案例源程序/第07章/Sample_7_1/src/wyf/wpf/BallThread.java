package wyf.wpf;				//���������
//�̳���Thread���߳��࣬�����޸����λ������
public class BallThread extends Thread{	
	Movable father;		//Movable��������
	boolean flag = false;	//�߳�ִ�б�־λ
	int sleepSpan = 30;		//����ʱ��
	float g = 200;		//������ļ��ٶ�
	double current;	//��¼��ǰʱ��
	//����������ʼ��Movable�������ü��߳�ִ�б�־λ
	public BallThread(Movable father){
		this.father = father;
		this.flag = true;		//�����߳�ִ�еı�־λΪtrue
	}
	//�����������������ʽ�޸�С��λ��
	public void run(){
		while(flag){
			current = System.nanoTime();//��ȡ��ǰʱ�䣬��λΪ����
			double timeSpanX = (double)((current-father.timeX)/1000/1000/1000);//��ȡ����ҿ�ʼ������ˮƽ�����߹���ʱ��
			//����ˮƽ�����ϵ��˶�
			father.x = (int)(father.startX + father.v_x * timeSpanX);
			//������ֱ�����ϵ��˶�			
			if(father.bFall){//�ж����Ƿ��Ѿ��Ƴ�����
				double timeSpanY = (double)((current - father.timeY)/1000/1000/1000);	
				father.y = (int)(father.startY + father.startVY * timeSpanY + timeSpanY*timeSpanY*g/2);
				father.v_y = (float)(father.startVY + g*timeSpanY);				
				//�ж�С���Ƿ񵽴���ߵ�
				if(father.startVY < 0 && Math.abs(father.v_y) <= BallView.UP_ZERO){
					father.timeY = System.nanoTime();			//�����µ��˶��׶���ֱ�����ϵĿ�ʼʱ��
					father.v_y = 0;								//�����µ��˶��׶���ֱ�����ϵ�ʵʱ�ٶ�
					father.startVY = 0;							//�����µ��˶��׶���ֱ�����ϵĳ�ʼ�ٶ�
					father.startY = father.y;					//�����µ��˶��׶���ֱ�����ϵĳ�ʼλ��
				}
				//�ж�С���Ƿ�ײ��
				if(father.y + father.r*2 >= BallView.GROUND_LING && father.v_y >0){//�ж�ײ������
					//�ı�ˮƽ������ٶ�
					father.v_x = father.v_x * (1-father.impactFactor);	//˥��ˮƽ�����ϵ��ٶ�
					//�ı���ֱ������ٶ�
					father.v_y = 0 - father.v_y * (1-father.impactFactor);		//˥����ֱ�����ϵ��ٶȲ��ı䷽��
					if(Math.abs(father.v_y) < BallView.DOWN_ZERO){	//�ж�ײ�غ���ٶȣ�̫С��ֹͣ
						this.flag = false;
					}
					else{	//ײ�غ���ٶȻ����Ե��������һ�׶ε��˶�
						//ײ��֮��ˮƽ����ı仯
						father.startX = father.x;			//�����µ��˶��׶ε�ˮƽ�������ʼλ��
						father.timeX = System.nanoTime();	//�����µ��˶��׶ε�ˮƽ����Ŀ�ʼʱ��
						//ײ��֮����ֱ����ı仯						
						father.startY = father.y;		//�����µ��˶��׶���ֱ�����ϵ���ʼλ��
						father.timeY = System.nanoTime();	//�����µ��˶��׶���ֱ����ʼ�˶���ʱ��
						father.startVY = father.v_y;	//�����µ��˶��׶���ֱ�����ϵĳ��ٶ�
					}
				}				
			}
			else if(father.x + father.r/2 >= BallView.WOOD_EDGE){//�ж����Ƿ��Ƴ��˵���			
				father.timeY = System.nanoTime();		//��¼����ֱ�����ϵĿ�ʼ�˶�ʱ��
				father.bFall = true;				//���ñ�ʾ�Ƿ�ʼ�����־λ
			}			
			try{
				Thread.sleep(sleepSpan);		//����һ��ʱ��				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}