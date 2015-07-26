package wyf.wpf;			//���������
/*
 * �����Ӧ��ͼ�еĴ��ڣ���ͼģ����ÿ��һ�����ڣ��ͻ���һ��Cannon���󴴽�
 * Cannon������Ҫ�ķ�����fire���˷�������ݵ�ʱС���λ�ã�����������һ��Missile����
 * ��Missile����ᱻ��ӵ�һ�������в�������
 */
public class Cannon{
	GameView father;		//GameView��������
	int row;				//�����ڵ�ͼ�ϵ�����
	int col;				//�����ڵ�ͼ�ϵ�����
	int range;				//���
	//����������ʼ����Ա����
	public Cannon(int row,int col,GameView father){
		this.row = row;
		this.col = col;
		this.father = father;
		this.range = 840;
	}
	//����������һ��Missile����
	public Missile fire(){
		int missileX = col*father.tileSize + 10;			//ȷ���ڵ�����ʼX����
		int missileY = row*father.tileSize + 10;			//ȷ���ڵ�����ʼY����
		float k = (float)(missileY-father.ballY)/(missileX-father.ballX);	//�����ڵ���б��
		int span = missileX>father.ballX?-2:2;								//�ж��������˶����������ƶ�
		return new Missile(missileX,missileY,k,span,father);				//����Missile���󲢷���
	}
}