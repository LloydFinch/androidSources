package wyf.wpf;		//���������
import java.util.ArrayList;	//���������
//�̳���Thread���࣬��Ҫ������������Ӳ��޸����ӵ�λ��
public class ParticleThread extends Thread{
	boolean flag;		//�߳�ִ�б�־λ
	ParticleView father;		//ParticleView��������
	int sleepSpan = 80;		//�߳�����ʱ��
	double time = 0;		//���������ʱ����
	double span = 0.15;		//ÿ�μ������ӵ�λ��ʱ���õ�ʱ����
	//ParticleThread��Ĺ�����
	public ParticleThread(ParticleView father){
		this.father = father;
		this.flag = true;		//�����߳�ִ�б�־λΪtrue
	}
	//�̵߳�ִ�з���
	public void run(){
		while(flag){
			father.ps.add(5, time);	//ÿ�����10������
			ArrayList<Particle> tempSet = father.ps.particleSet;//��ȡ���Ӽ���
			int count = tempSet.size();		//��¼���Ӽ��ϵĴ�С
			
			for(int i=0; i<count; i++){		//�������Ӽ��ϣ��޸���켣
				Particle particle = tempSet.get(i);
				double timeSpan = time - particle.startTime;	//����ӳ���ʼ�����ھ�����ʱ����
				int tempx = (int)(particle.startX+particle.horizontal_v*timeSpan);	//��������ӵ�X����
				int tempy = (int)(particle.startY + 4.9*timeSpan*timeSpan + particle.vertical_v*timeSpan);
				if(tempy>ParticleView.DIE_OUT_LINE){						//���������Ļ�±���
					tempSet.remove(particle);		//�����Ӽ��ϳ��Ƴ���Particle����
					count = tempSet.size();			//�����������Ӹ���
				}
				particle.x = tempx;					//�޸����ӵ�X����
				particle.y = tempy;					//�޸����ӵ�Y����
			}
			time += span;								//��ʱ���ӳ�
			try{
				Thread.sleep(sleepSpan);					//����һ��ʱ��
			}
			catch(Exception e){
				e.printStackTrace();				//���񲢴�ӡ�쳣
			}
		}
	}
}