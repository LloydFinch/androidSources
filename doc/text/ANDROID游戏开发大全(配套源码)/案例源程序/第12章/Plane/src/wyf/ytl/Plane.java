package wyf.ytl;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Message;
/**
 * 该类为飞机的封装类
 */
public class Plane {
	private int x;//飞机的坐标
	private int y;
	int life;//生命
	private int dir;//飞机的方向,0静止，1上,2右上，3右，4右下，5下，6左下，7左，8左上
	private int type;//飞机的类型
	Bitmap bitmap1;//当前向下飞机的图片
	Bitmap bitmap2;//当前向上飞机的图片
	Bitmap bitmap3;//当前飞机的图片
	GameView gameView;//GameView的引用
	private int span = 10;//飞机走一步的像素
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
		if(type == 1){//当类型为1时
			bitmap1 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plane1);
			bitmap2 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plane2);
			bitmap3 = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plane3);
		}
	}
	public void draw(Canvas canvas){
		if(dir == ConstantUtil.DIR_UP){//向上
			canvas.drawBitmap(bitmap2, x, y,new Paint());
		}
		else if(dir == ConstantUtil.DIR_DOWN){//向下
			canvas.drawBitmap(bitmap1, x, y,new Paint());
		}
		else{//其他情况使用的图片
			canvas.drawBitmap(bitmap3, x, y,new Paint());
		}
	}
	public void fire(){//打子弹的方法
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
			gameView.playSound(1,0);//播放音乐
		}
	}
	public boolean contain(Bullet b){
		if(isContain(b.x, b.y, b.bitmap.getWidth(), b.bitmap.getHeight())){//检测成功
			this.life--;//自己的生命减1
			if(this.life<0){//当生命小于0时
				gameView.status = 2;
				if(gameView.mMediaPlayer.isPlaying()){
					gameView.mMediaPlayer.stop();
				}
				if(gameView.activity.isSound){
					gameView.playSound(3,0);
				}
				Message msg1 = gameView.activity.myHandler.obtainMessage(1);
				gameView.activity.myHandler.sendMessage(msg1);//向主activity发送Handler消息
			}
			return true;
		}
		return false;
	}
	public boolean contain(ChangeBullet cb){
		if(isContain(cb.x, cb.y, cb.bitmap.getWidth(), cb.bitmap.getHeight())){//检测成功
			this.bulletType += 1;
			return true;
		}
		return false;
	}
	public boolean contain(EnemyPlane ep){
		if(isContain(ep.x, ep.y, ep.bitmap.getWidth(), ep.bitmap.getHeight())){//检测成功
			this.life--;//自己的生命减1
			if(this.life<0){//当生命小于0时
				gameView.status = 2;
				if(gameView.mMediaPlayer.isPlaying()){
					gameView.mMediaPlayer.stop();
				}
				if(gameView.activity.isSound){
					gameView.playSound(3,0);
				}
				gameView.activity.myHandler.sendEmptyMessage(1);//向主activity发送Handler消息
			}
			return true;
		}
		return false;
	}
	public boolean contain(Life l){//检测玩家飞机是否撞血块
		if(isContain(l.x, l.y, l.bitmap.getWidth(), l.bitmap.getHeight())){//检测成功
			if(this.life<ConstantUtil.life){
				this.life++;//生命加一
			}
			return true;
		}
		return false;
	}
	private boolean isContain(int otherX, int otherY, int otherWidth, int otherHeight){//判断两个矩形是否碰撞
		int xd = 0;//大的x
		int yd = 0;//大大y
		int xx = 0;//小的x
		int yx = 0;//小的y
		int width = 0;
		int height = 0;
		boolean xFlag = true;//玩家飞机x是否在前
		boolean yFlag = true;//玩家飞机y是否在前
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
				yd>=yx&&yd<=yx+height-1){//首先判断两个矩形有否重叠
		    double Dwidth=width-xd+xx;   //重叠区域宽度		
			double Dheight=height-yd+yx; //重叠区域高度
			if(Dwidth*Dheight/(otherWidth*otherHeight)>=0.20){//重叠面积超20%则判定为碰撞
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