package wyf.wpf;			//���������
/*
 * ���������ʯ���󣬽���ʯ�����꣬��ǰ֡�ŷ�װ����
 * ��һ����Ա���������ʼ����ʯ����Ϣ
 */
public class Meteorolite{
	int x;				//��ʯx����
	int y;				//��ʯy����
	boolean up;			//Ϊtrue���ڵ�ͼͼ���ϣ�Ϊfalse���ڵ�ͼͼ����
	int index;			//��ת�Ƕ�
	public Meteorolite(){	//������������init�������г�ʼ��
		init();
	}		
	public void init(){//��������ʼ����Ա����
		x = (int)(Math.random()*290);		 	//�������X����	
		y = -(int)(Math.random()*250);			//�������Y����
		up = (Math.random()>0.5?true:false);	//��������Ƿ����ϲ�ı�־λ
		index = (int)(Math.random()*8);			//���������ʯ������֡����	
	}
}