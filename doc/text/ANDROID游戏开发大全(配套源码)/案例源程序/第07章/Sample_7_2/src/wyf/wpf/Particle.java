package wyf.wpf;//���������
//ÿ��Particle�������һ�����Ӷ���
public class Particle{
	int color;//������ɫ
	int r;//���Ӱ뾶
	double vertical_v;//��ֱ�ٶ�
	double horizontal_v;//ˮƽ�ٶ�
	int startX;//��ʼx����
	int startY;//��ʼy����
	int x;//��ǰx����
	int y;//��ǰy����
	double startTime;//��ʼʱ��
	//����������ʼ����Ա����
	public Particle(int color, int r, double vertical_v, 
			double horizontal_v, int x, int y, double startTime){
		this.color = color;	//��ʼ��������ɫ
		this.r = r;	//��ʼ�����Ӱ뾶
		this.vertical_v = vertical_v;	//��ʼ����ֱ�����ٶ�
		this.horizontal_v = horizontal_v;	//��ʼ��ˮƽ�������ٶ�
		this.startX = x;		//��ʼ����ʼλ�õ�X����
		this.startY = y;		//��ʼ����ʼλ�õ�Y����
		this.x = x;						//��ʼ��ʵʱX����
		this.y = y;							//��ʼ��ʵʱY����
		this.startTime = startTime;			//��ʼ����ʼ�˶���ʱ��
	}
}