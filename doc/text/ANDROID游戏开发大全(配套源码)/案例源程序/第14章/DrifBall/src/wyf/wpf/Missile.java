package wyf.wpf;					//���������
import static wyf.wpf.DriftBall.*;	//����DriftBall��ľ�̬��Ա
/*
 * ��������������з�װ�˵���·����б��
 * �����Ĳ���������������ȣ�����һ����Ա
 * ���������ƶ�������λ�ã������ж��Ƿ�
 * ը��С��
 */
public class Missile{
	float k;				//б��
	int span=2;				//����
	int x;					//��������x����
	int y;					//��������y����		
	GameView father;		//GameView ����
	//����������ʼ��С���λ�á��ٶȺͷ���
	public Missile(int x,int y,float k,int span,GameView father){
		this.x = x;
		this.y = y;
		this.k = k;
		this.span = span;
		this.father = father;
	}
	//�������ƶ�����������Ƿ���С����ײ
	public void moveAndCheck(){
		//�ƶ�����
		x = x+span;
		y = (int)(y+ k*span);
		//����Ƿ�����Ŀ��
		int bx = father.ballX+10;		//����x����
		int by = father.ballY+10;		//����y����
		if((bx-x)*(bx-x)+(by-y)*(by-y) <= 15*15){
			father.father.life -=1;
			if(father.father.life <=0){		//���������Ѿ�����
				father.gt.flag = false;	
				father.gt.isGameOn = false;
				father.status = STATUS_OVER;
			}
			else{							//���������Ǿʹ�ͷ��ʼ
				father.ballX = 0;
				father.ballY = 0;
			}
			father.alMissile.remove(this);	//�����м������Ƴ��Լ�
		}
		//����Ƿ������߽�
		if(x > father.screenWidth || x<0 || y>father.screenHeight || y<0){
			father.alMissile.remove(this);	
		}
	}
}