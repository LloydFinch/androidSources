package wyf.ytl;
/**
 * 
 * ����Ϊ������������Ʒ���ֵ���
 *
 */
public class GameViewBackGroundThread extends Thread{
	private int sleepSpan = 100;//˯�ߵĺ�����
	private int span = 3;//ͼƬ�ƶ��Ĳ���
	private boolean flag = true;//ѭ����־λ
	GameView gameView;//GameView������ 	
	long touchTime = 0;//��ǰ������ʱ��
	public GameViewBackGroundThread(GameView gameView){//������
		this.gameView = gameView;
	}
	public void setFlag(boolean flag){//���ñ��λ
		this.flag = flag;
	}
	public void run(){
		while(flag){
			if(gameView.status == 1){//��Ϸ��ʱ
				gameView.backGroundIX -= span;
				if(gameView.backGroundIX <-ConstantUtil.pictureWidth){
					gameView.i = (gameView.i+1)%ConstantUtil.pictureCount;
					gameView.backGroundIX+=ConstantUtil.pictureWidth;
				}
				gameView.cloudX -= span;//�ƶ��Ʋ�
				if(gameView.cloudX<-1000){
					gameView.cloudX = 1000;
				}
				touchTime++;//ʱ���Լ�
				try{
					for(EnemyPlane ep : gameView.enemyPlanes){//��ʱ����ֵл�
						if(ep.touchPoint == touchTime){ 
							ep.status = true;
							if(ep.type == 3){//���ؿ���
								gameView.status = 3;
							}  
						} 
					}
					for(Life l : gameView.lifes){//��ʱ�����Ѫ��
						if(l.touchPoint == touchTime){
							l.status = true;
						}
					}
					for(ChangeBullet cb : gameView.changeBollets){//��ʱ����ֳ��˸ı�ǹ������
						if(cb.touchPoint == touchTime){
							cb.status = true;
						}
					}
				}catch(Exception e){//�����쳣
					e.printStackTrace();//��ӡ�쳣��Ϣ
				}
				if(touchTime == 641){//���ؿ�ʱ
					this.flag = false;
				}
			}try{
				Thread.sleep(sleepSpan);//˯��
			}catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣��Ϣ
			}
		}
	}
}