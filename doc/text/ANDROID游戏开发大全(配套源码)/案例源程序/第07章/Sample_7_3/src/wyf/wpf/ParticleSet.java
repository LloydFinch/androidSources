package wyf.wpf;		//���������
import java.util.ArrayList;		//���������
import android.graphics.Color;	//���������

public class ParticleSet{
	ArrayList<Particle> particleSet;		//���ڴ��Particle����ļ���
	//����������ʼ�����Ӽ���
	public ParticleSet(){
		particleSet = new ArrayList<Particle>();
	}
	//�����������Ӽ��������ָ�����������Ӷ���
	public void add(int count, double startTime){
		for(int i=0; i<count; i++){		//����count������Particle����
			int tempColor = this.getColor(i);	//���������ɫ
			int tempR = 1;		//���Ӱ뾶
			double tempv_v = 0;	//������ֱ�������ٶ�����Ϊ��
			double tempv_h = 20 + 10*(Math.random());	//�����������ˮƽ�������ٶ�
			int tempX = 50;	//���ӵ�X�����ǹ̶���
			int tempY = (int)(50 - 10*(Math.random()));	//�����������Y����
			//����Particle����
			Particle particle = new Particle(tempColor, tempR, tempv_v, tempv_h, tempX, tempY, startTime);
			particleSet.add(particle);//�������õ�Particle������ӵ��б���
		}
	}
	//��������ȡָ����������ɫ
	public int getColor(int i){
		int color = Color.RED;
		switch(i%4){	//��i���з�֧�ж�
		case 0:
			color = Color.RED;	//����ɫ��Ϊ��ɫ
			break;
		case 1:
			color = Color.GREEN;//����ɫ��Ϊ��ɫ
			break;
		case 2:
			color = Color.YELLOW;//����ɫ��Ϊ��ɫ
			break;
		case 3:
			color = Color.GRAY;//����ɫ��Ϊ��ɫ
			break;
		}
		return color;		//���صõ�����ɫ
	}
}