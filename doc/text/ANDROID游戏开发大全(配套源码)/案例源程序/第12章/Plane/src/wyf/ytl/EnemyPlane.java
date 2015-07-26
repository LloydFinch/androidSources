package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
public class EnemyPlane {
	int x = ConstantUtil.screenWidth;//�ɻ�������
	int y;
	boolean status;//�ɻ���״̬
	long touchPoint;//������
	int type;//�ɻ�������
	int life;//����
	int spanX = 10;//�ɻ��ƶ���X����
	int spanY = 5;//�ɻ��ƶ���X����
	Bitmap bitmap;
	int start;//��ǰ������
	int target;//��ǰĿ���
	int step;//��ǰ���ڵ�ǰ·��Ƭ���еڼ���
	int[][] path; 
	public EnemyPlane(int start,int target,int step,int[][] path, boolean status,long touchPoint, int type, int life){
		this.start=start;
		this.target=target;
		this.step=step;
		this.path=path;
		this.status = status;
		this.touchPoint = touchPoint;
		this.type = type;
		this.life = life;
		this.x=path[0][start];
		this.y=path[1][start];
	}
	public void draw(Canvas canvas){
		canvas.drawBitmap(bitmap, x, y, new Paint());
	}
	public void move(){
		if(step==path[2][start]){//һ��·������,����һ��·����ʼ
			step=0;
			start=(start+1)%(path[0].length);
			target=(target+1)%(path[0].length);
			this.x=path[0][start];
			this.y=path[1][start];
		}else{//һ��·��û�����꣬������
			int xSpan=(path[0][target]-path[0][start])/path[2][start];
			int ySpan=(path[1][target]-path[1][start])/path[2][start];
			this.x=this.x+xSpan;
			this.y=this.y+ySpan;
			step++;
		}
	}
	public void fire(GameView gameView){//���ӵ��ķ���
		if(type == 3 && Math.random()<ConstantUtil.BooletSpan2){
			Bullet b1 = new Bullet(x, y, 2, ConstantUtil.DIR_LEFT,gameView);
			Bullet b2 = new Bullet(x, y, 2, ConstantUtil.DIR_LEFT_DOWN,gameView);
			Bullet b3 = new Bullet(x, y, 2, ConstantUtil.DIR_LEFT_UP,gameView);
			gameView.badBollets.add(b1);
			gameView.badBollets.add(b2);
			gameView.badBollets.add(b3);
		}else if(Math.random()<ConstantUtil.BooletSpan){
			if(this.type == 4){
				Bullet b = new Bullet(x, y, 2, ConstantUtil.DIR_RIGHT,gameView);
				gameView.badBollets.add(b);
			}else{
				Bullet b = new Bullet(x, y, 2, ConstantUtil.DIR_LEFT,gameView);
				gameView.badBollets.add(b);
			}		
		}
	}
	private boolean isContain(int otherX, int otherY, int otherWidth, int otherHeight){//�ж����������Ƿ���ײ
		int xd = 0;//���x
		int yd = 0;//���y
		int xx = 0;//С��x
		int yx = 0;//С��y
		int width = 0;
		int height = 0;
		boolean xFlag = true;//��ҷɻ�x�Ƿ���ǰ
		boolean yFlag = true;//��ҷɻ�y�Ƿ���ǰ
		if(this.x >= otherX){
			xd = this.x;
			xx = otherX;
			xFlag = false;
		}else{
			xd = otherX;
			xx = this.x;
			xFlag = true;
		}
		if(this.y >= otherY){
			yd = this.y;
			yx = otherY;
			yFlag = false;
		}else{
			yd = otherY;
			yx = this.y;
			yFlag = true;
		}
		if(xFlag == true){
			width = this.bitmap.getWidth();
		}else{
			width = otherWidth;
		}
		if(yFlag == true){
			height = this.bitmap.getHeight();
		}else{
			height = otherHeight;
		}
		if(xd>=xx&&xd<=xx+width-1&&
				yd>=yx&&yd<=yx+height-1){//�����ж����������з��ص�
		    double Dwidth=width-xd+xx;   //�ص�������		
			double Dheight=height-yd+yx; //�ص�����߶�
			if(Dwidth*Dheight/(otherWidth*otherHeight)>=0.20){//�ص������20%���ж�Ϊ��ײ
				return true;
			}
		}
		return false;
	}
	
	public boolean contain(Bullet b,GameView gameView){//�ж��ӵ��Ƿ���ел�
		if(isContain(b.x, b.y, b.bitmap.getWidth(), b.bitmap.getHeight())){
			this.life--;//�Լ���������1
			if(this.life<=0){//������С��0ʱ
				if(gameView.activity.isSound){
					gameView.playSound(2,0);
				}
				this.status = false;//ʹ�Լ����ɼ�
				if(this.type == 3){//�ǹؿ�ʱ
					gameView.status = 3;//״̬����ʤ��״̬
					if(gameView.mMediaPlayer.isPlaying()){
						gameView.mMediaPlayer.stop();//����Ϸ��������ֹͣ
					}
					Message msg1 = gameView.activity.myHandler.obtainMessage(5);
					gameView.activity.myHandler.sendMessage(msg1);//����activity����Handler��Ϣ
				}
			}
			return true;
		}
		return false;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}