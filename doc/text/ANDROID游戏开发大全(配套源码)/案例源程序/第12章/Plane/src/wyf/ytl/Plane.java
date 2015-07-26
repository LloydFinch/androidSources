package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
/**
 * ����Ϊ�ɻ��ķ�װ��
 */
public class Plane {
	private int x;//�ɻ�������
	private int y;
	int life;//����
	private int dir;//�ɻ��ķ���,0��ֹ��1��,2���ϣ�3�ң�4���£�5�£�6���£�7��8����
	private int type;//�ɻ�������
	Bitmap bitmap1;//��ǰ���·ɻ���ͼƬ
	Bitmap bitmap2;//��ǰ���Ϸɻ���ͼƬ
	Bitmap bitmap3;//��ǰ�ɻ���ͼƬ
	GameView gameView;//GameView������
	private int span = 10;//�ɻ���һ��������
	int bulletType = 1; 
	public Plane(int x, int y, int type, int dir,int life, GameView gameView){
		this.gameView = gameView;
		this.x = x;
		this.y = y; 
		this.type = type;
		this.dir = dir;
		this.life = life;
		initBitmap();
	}
	public void initBitmap(){
		if(type == 1){//������Ϊ1ʱ
			bitmap1 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plane1);
			bitmap2 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plane2);
			bitmap3 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plane3);
		}
	}
	public void draw(Canvas canvas){
		if(dir == ConstantUtil.DIR_UP){//����
			canvas.drawBitmap(bitmap2, x, y,new Paint());
		}
		else if(dir == ConstantUtil.DIR_DOWN){//����
			canvas.drawBitmap(bitmap1, x, y,new Paint());
		}
		else{//�������ʹ�õ�ͼƬ
			canvas.drawBitmap(bitmap3, x, y,new Paint());
		}
	}
	public void fire(){//���ӵ��ķ���
		if(bulletType == 1){
			Bullet b = new Bullet(this.x+75, this.y+8, 1, ConstantUtil.DIR_RIGHT,gameView);
			gameView.goodBollets.add(b);
		}
		else if(bulletType == 2){
			Bullet b = new Bullet(this.x+75, this.y+4, 3, ConstantUtil.DIR_RIGHT,gameView);
			gameView.goodBollets.add(b);
		}
		else {
			Bullet b = new Bullet(this.x+75, this.y+4, 3, ConstantUtil.DIR_RIGHT,gameView);
			gameView.goodBollets.add(b);
			Bullet b2 = new Bullet(this.x+55, this.y-4, 4, ConstantUtil.DIR_RIGHT_UP,gameView);
			gameView.goodBollets.add(b2);
			Bullet b3 = new Bullet(this.x+55, this.y+12, 5, ConstantUtil.DIR_RIGHT_DOWN,gameView);
			gameView.goodBollets.add(b3);
		}		
		if(gameView.activity.isSound){
			gameView.playSound(1,0);//��������
		}
	}
	public boolean contain(Bullet b){
		if(isContain(b.x, b.y, b.bitmap.getWidth(), b.bitmap.getHeight())){//���ɹ�
			this.life--;//�Լ���������1
			if(this.life<0){//������С��0ʱ
				gameView.status = 2;
				if(gameView.mMediaPlayer.isPlaying()){
					gameView.mMediaPlayer.stop();
				}
				if(gameView.activity.isSound){
					gameView.playSound(3,0);
				}
				Message msg1 = gameView.activity.myHandler.obtainMessage(1);
				gameView.activity.myHandler.sendMessage(msg1);//����activity����Handler��Ϣ
			}
			return true;
		}
		return false;
	}
	public boolean contain(ChangeBullet cb){
		if(isContain(cb.x, cb.y, cb.bitmap.getWidth(), cb.bitmap.getHeight())){//���ɹ�
			this.bulletType += 1;
			return true;
		}
		return false;
	}
	public boolean contain(EnemyPlane ep){
		if(isContain(ep.x, ep.y, ep.bitmap.getWidth(), ep.bitmap.getHeight())){//���ɹ�
			this.life--;//�Լ���������1
			if(this.life<0){//������С��0ʱ
				gameView.status = 2;
				if(gameView.mMediaPlayer.isPlaying()){
					gameView.mMediaPlayer.stop();
				}
				if(gameView.activity.isSound){
					gameView.playSound(3,0);
				}
				gameView.activity.myHandler.sendEmptyMessage(1);//����activity����Handler��Ϣ
			}
			return true;
		}
		return false;
	}
	public boolean contain(Life l){//�����ҷɻ��Ƿ�ײѪ��
		if(isContain(l.x, l.y, l.bitmap.getWidth(), l.bitmap.getHeight())){//���ɹ�
			if(this.life<ConstantUtil.life){
				this.life++;//������һ
			}
			return true;
		}
		return false;
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
			width = this.bitmap1.getWidth();
		}else {
			width = otherWidth;
		}
		if(yFlag == true){
			height = this.bitmap1.getHeight();
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
	public void setDir(int dir){  
		this.dir = dir;
	}
	public int getSpan(){
		return span;
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
		if(this.y < 0){
			this.y = 0;
		}
		if(this.y > ConstantUtil.screenHeight){
			this.y = ConstantUtil.screenHeight;
		}
	}
}