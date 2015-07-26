package wyf.ytl;
/**
 * 
 * ����Ϊ���̼����߳���
 * ��ʱ��⵱ǰ���̵�״̬
 * Ȼ�����״̬������Ӧ�Ĵ���
 * 
 */
public class KeyThread extends Thread{
	int span = 20;//˯�ߵĺ�����
	int countMove = 0;//�ɻ��ƶ��ļ�����
	int countFine = 0;//�ɻ����ӵ��ļ�����
	int moveN = 3;//ÿ����ѭ���ƶ�һ��
	int fineN = 5;//ÿ���ѭ����һ���ӵ�
	PlaneActivity activity;//Activity������
	private boolean flag = true;//ѭ����־
	int action;//����״̬��
	private boolean KEY_UP = false;//���ϼ��Ƿ񱻰���
	private boolean KEY_DOWN = false;//���¼��Ƿ񱻰���
	private boolean KEY_LEFT = false;//����ļ�������
	private boolean KEY_RIGHT = false;//���ҵļ�������
	private boolean KEY_A = false;//A�ļ�������
	private boolean KEY_B = false;//B�ļ�������
	public KeyThread(PlaneActivity activity){//������
		this.activity = activity;
	}
	public void setFlag(boolean flag){//���ñ�־λ
		this.flag = flag;
	}
	public void run(){//��д�ķ���
		while(flag){
			action = activity.action;//�õ���ǰ���̵�״̬��
			if((action & 0x20) != 0){//��
				KEY_UP = true;
			}else{
				KEY_UP = false;
			}
			if((action & 0x10) != 0){//��
				KEY_DOWN = true;
			}else{
				KEY_DOWN = false;
			}
			if((action & 0x08) != 0){//��
				KEY_LEFT = true;
			}else{
				KEY_LEFT = false;
			}
			if((action & 0x04) != 0){//��
				KEY_RIGHT = true;
			}else{
				KEY_RIGHT = false;
			}
			if((action & 0x02) != 0){//A
				KEY_A = true;
			}else{
				KEY_A = false;
			}
			if((action & 0x01) != 0){//B
				KEY_B = true;
			}else{
				KEY_B = false;
			}
			if(activity.gameView.status == 1 || activity.gameView.status == 3){
				if(countMove == 0){//ÿmoveN���ƶ�һ��
					if(KEY_UP == true){//���ϼ�������
						if(!((activity.gameView.plane.getY() - activity.gameView.plane.getSpan()) < ConstantUtil.top)){
							activity.gameView.plane.setY(activity.gameView.plane.getY() - activity.gameView.plane.getSpan());
						}
						activity.gameView.plane.setDir(ConstantUtil.DIR_UP);
					}
					if(KEY_DOWN == true){//���¼�������
						if(!((activity.gameView.plane.getY() + activity.gameView.plane.getSpan()) > ConstantUtil.screenHeight - activity.gameView.plane.bitmap1.getHeight())){
							activity.gameView.plane.setY(activity.gameView.plane.getY() + activity.gameView.plane.getSpan());
						}
						activity.gameView.plane.setDir(ConstantUtil.DIR_DOWN);
					}
					if(KEY_LEFT == true){//�����������
						if(!((activity.gameView.plane.getX() - activity.gameView.plane.getSpan()) < -40)){
							activity.gameView.plane.setX(activity.gameView.plane.getX() - activity.gameView.plane.getSpan());
						}
					}
					if(KEY_RIGHT == true){//���Ҽ�������
						if(!((activity.gameView.plane.getX() + activity.gameView.plane.getSpan()) > ConstantUtil.screenWidth - activity.gameView.plane.bitmap1.getWidth())){
							activity.gameView.plane.setX(activity.gameView.plane.getX() + activity.gameView.plane.getSpan());
						}
					}
					if(KEY_RIGHT == false && KEY_LEFT == false && KEY_DOWN == false && KEY_UP == false){
						activity.gameView.plane.setDir(ConstantUtil.DIR_STOP);
					}
					if(countFine == 0){//ÿfineN��һ���ӵ�
						if(KEY_A == true){//A��������
							activity.gameView.plane.fire();
						}
						if(KEY_B == true){//B��������
		
						}
					}
				}
				countMove = (countMove+1)%moveN;
				countFine = (countFine+1)%fineN;
			}
			try{
				Thread.sleep(span);//˯��ָ��������
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}