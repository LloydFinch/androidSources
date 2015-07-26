package wyf.wpf;			//���������
import java.util.ArrayList;				//���������
import android.content.res.Resources;	//���������
import android.graphics.Bitmap;	//���������
import android.graphics.BitmapFactory;	//���������
import android.graphics.Canvas;	//���������
import android.view.SurfaceHolder;	//���������
import android.view.SurfaceView;	//���������
import android.view.SurfaceHolder.Callback;	//���������
import static wyf.wpf.DriftBall.*;	//���������
/*
 * ����̳���SurfaceView����Ҫ�����ǻ�����Ϸ��Ļ���Ժ�̨�Ļ��ƻ������߳�
 * ���п��ơ�
 */
public class GameView extends SurfaceView implements Callback{
	int screenWidth = 320;		//��Ļ���
	int screenHeight = 480;		//��Ļ�߶�
	int backY;					//x��������Ϊ��
	int nebulaX;				//���Ƶ�x����
	int nebulaY;				//���Ƶ�y����
	int ballX;					//С��ĺ�����
	int ballY;					//С���������
	int tileSize = 20;			//ͼԪ�Ĵ�С
	int direction = -1;			//С���˶�������Ϊ0��˳ʱ������Ϊ1~7
	int velocity = 4;			//С���˶��ٶ�
	int eatIndex;				//���˶���֡����
	int status;					//��Ϸ״̬
	int trapIndex;				//���嶯������
	
	boolean showMenu;			//�Ƿ���ʾ�˵�
	
	DriftBall father;			//DriftBall����
	DrawThread dt;				//�����߳�
	GameThread gt;				//��̨���ݵ��޸��߳�
	CannonThread ct;			//�����߳�
	GameMenuThread gmt;			//��Ϸ�˵��߳�
	Meteorolite [] meteoArray;	//��ʯ����
	ArrayList<Missile> alMissile = new ArrayList<Missile>();		//��ŵ�������
	ArrayList<Cannon> alCannon = new ArrayList<Cannon>();			//��Ŵ��ڼ���
		
	Bitmap bmpStar;			//�ǿ�ͼƬ
	Bitmap bmpNebula;		//����ͼƬ
	Bitmap bmpBall;			//С��ͼƬ
	Bitmap bmpTile;			//��ͼͼԪ����·��ͼƬ
	Bitmap [] bmpMeteo; 	//��ʯͼƬ
	Bitmap [] bmpEat;		//����ͼƬ
	Bitmap bmpHome;			//�ҵ�ͼƬ
	Bitmap [] bmpTrap;		//����ͼƬ
	Bitmap bmpCannon;		//����ͼƬ
	Bitmap bmpMissile;		//����ͼƬ
	Bitmap bmpPlusLife;		//����ͼƬ
	Bitmap bmpMultiply;		//�˺�ͼƬ��������ʾ����
	Bitmap [] bmpNumber;	//����ͼƬ
	Bitmap [] bmpMenuItem;	//�˵���ͼƬ����
	Bitmap bmpGameWin;		//��Ϸʤ��ͼƬ
	Bitmap bmpGameOver;		//��Ϸ����ͼƬ
	Bitmap bmpGamePass;		//��Ϸͨ��ͼƬ
	
	//��ͼ��Ϊ����գ�Ϊ1��·,2Ϊ��,6�ǳ�С���,7������,8�Ǵ���
	byte [][] currMap;
	int [][] menuCoordinate;	//�˵����˵����λ��
	
	public GameView(DriftBall father) {
		super(father);
		this.father = father;
		//��ʼ��ͼƬ
		initBitmap(father);
		//���SurfaceHolder
		getHolder().addCallback(this);
		dt = new DrawThread(getHolder(),this);
		gt = new GameThread(this);
		//��ʼ����ʯ����
		meteoArray = new Meteorolite[3];
		for(int i=0;i<3;i++){
			meteoArray[i] = new Meteorolite();
		}
		initGame();		
	}
	//��ʼ��ͼƬ��Դ
	public void initBitmap(DriftBall father){
		Resources r = father.getResources();
		bmpStar = BitmapFactory.decodeResource(r, R.drawable.stars);
		bmpNebula = BitmapFactory.decodeResource(r, R.drawable.nebula);
		bmpBall = BitmapFactory.decodeResource(r, R.drawable.ball); 
		bmpTile = BitmapFactory.decodeResource(r, R.drawable.tile);
		bmpMeteo = new Bitmap[8];			//��ʯ����֡
		bmpMeteo[0] = BitmapFactory.decodeResource(r, R.drawable.meteo1);
		bmpMeteo[1] = BitmapFactory.decodeResource(r, R.drawable.meteo2);
		bmpMeteo[2] = BitmapFactory.decodeResource(r, R.drawable.meteo3);
		bmpMeteo[3] = BitmapFactory.decodeResource(r, R.drawable.meteo4);
		bmpMeteo[4] = BitmapFactory.decodeResource(r, R.drawable.meteo5);
		bmpMeteo[5] = BitmapFactory.decodeResource(r, R.drawable.meteo6);
		bmpMeteo[6] = BitmapFactory.decodeResource(r, R.drawable.meteo7);
		bmpMeteo[7] = BitmapFactory.decodeResource(r, R.drawable.meteo8);
		bmpEat = new Bitmap[4];				//���˶���֡
		bmpEat[0] = BitmapFactory.decodeResource(r, R.drawable.eat_1);
		bmpEat[1] = BitmapFactory.decodeResource(r, R.drawable.eat_2);
		bmpEat[2] = BitmapFactory.decodeResource(r, R.drawable.eat_3);
		bmpEat[3] = BitmapFactory.decodeResource(r, R.drawable.eat_4); 
		bmpHome = BitmapFactory.decodeResource(r, R.drawable.home);
		bmpTrap = new Bitmap[8];			//���嶯��֡
		bmpTrap[0] = BitmapFactory.decodeResource(r, R.drawable.trap1);
		bmpTrap[1] = BitmapFactory.decodeResource(r, R.drawable.trap1);
		bmpTrap[2] = BitmapFactory.decodeResource(r, R.drawable.trap1);
		bmpTrap[3] = BitmapFactory.decodeResource(r, R.drawable.trap1);
		bmpTrap[4] = BitmapFactory.decodeResource(r, R.drawable.trap3);
		bmpTrap[5] = BitmapFactory.decodeResource(r, R.drawable.trap5);
		bmpTrap[6] = BitmapFactory.decodeResource(r, R.drawable.trap7);  
		bmpTrap[7] = BitmapFactory.decodeResource(r, R.drawable.trap9);
		bmpCannon = BitmapFactory.decodeResource(r, R.drawable.cannon);  
		bmpMissile = BitmapFactory.decodeResource(r, R.drawable.missile);
		bmpPlusLife = BitmapFactory.decodeResource(r, R.drawable.plus_life);
		bmpMultiply = BitmapFactory.decodeResource(r, R.drawable.multiply);
		bmpNumber = new Bitmap[10];			//��ʼ������ͼƬ��Դ
		bmpNumber[0] = BitmapFactory.decodeResource(r, R.drawable.number0);
		bmpNumber[1] = BitmapFactory.decodeResource(r, R.drawable.number1);
		bmpNumber[2] = BitmapFactory.decodeResource(r, R.drawable.number2);
		bmpNumber[3] = BitmapFactory.decodeResource(r, R.drawable.number3);
		bmpNumber[4] = BitmapFactory.decodeResource(r, R.drawable.number4);
		bmpNumber[5] = BitmapFactory.decodeResource(r, R.drawable.number5);
		bmpNumber[6] = BitmapFactory.decodeResource(r, R.drawable.number6);
		bmpNumber[7] = BitmapFactory.decodeResource(r, R.drawable.number7);
		bmpNumber[8] = BitmapFactory.decodeResource(r, R.drawable.number8);
		bmpNumber[9] = BitmapFactory.decodeResource(r, R.drawable.number9);
		bmpMenuItem = new Bitmap[5];
		bmpMenuItem[0] = BitmapFactory.decodeResource(r, R.drawable.game_menu);
		bmpMenuItem[1] = BitmapFactory.decodeResource(r, R.drawable.menu_continue_game);
		bmpMenuItem[2] = BitmapFactory.decodeResource(r, R.drawable.menu_sound_off);
		bmpMenuItem[3] = BitmapFactory.decodeResource(r, R.drawable.menu_help);
		bmpMenuItem[4] = BitmapFactory.decodeResource(r, R.drawable.menu_back_to_main);
		bmpGameWin = BitmapFactory.decodeResource(r, R.drawable.game_win);
		bmpGameOver = BitmapFactory.decodeResource(r, R.drawable.game_over);
		bmpGamePass = BitmapFactory.decodeResource(r, R.drawable.game_pass);
	}	
	//��ʼ������    
	public void initGame(){
		this.ballX = 0;
		this.ballY = 0;
		this.backY = 0;
		this.nebulaX = (int)(Math.random()*150);
		this.nebulaY = -120;
		this.currMap = GameMap.getMap(father.level);
		//������
		if(checkForCannon()){
			ct = new CannonThread(this);
		}
		System.out.println("*****the size of alCannon:"+alCannon.size()+"***********");
	}
	//�ظ���Ϸ
	public void resumeGame(){
		dt.isGameOn = true;		//
		gt.isGameOn = true;
		if(ct != null){
			ct.isGameOn = true;
		}
		status = STATUS_PLAY;
	}
	//��Ļ���Ʒ���
	public void doDraw(Canvas canvas){
		//�����ǿձ���
		canvas.drawBitmap(bmpStar,0,backY,null);
		if(backY <= -120){
			canvas.drawBitmap(bmpStar, 0, backY+600, null);
		}
		//��������
		canvas.drawBitmap(bmpNebula, nebulaX, nebulaY, null);
		//���������ʯ
		for(Meteorolite m:meteoArray){
			if(!m.up){
				canvas.drawBitmap(bmpMeteo[m.index], m.x,m.y,null);
			} 
		}
		//���Ƶ�ͼ
		drawMap(canvas);
		//����С��
		if(status != STATUS_OVER && status != STATUS_PASS){
			canvas.drawBitmap(bmpBall, ballX, ballY, null);
		}		
		//���������ʯ
		for(Meteorolite m:meteoArray){
			if(m.up){
				canvas.drawBitmap(bmpMeteo[m.index], m.x,m.y,null);
			}
		}
		//�����м�����
		canvas.drawBitmap(bmpBall, 240, 0, null);
		canvas.drawBitmap(bmpMultiply, 265, 2, null);
		drawNumber(canvas,father.life);	
		//���ڵ�
		try{
			if(alMissile.size() != 0){
				for(Missile m:alMissile){
					canvas.drawBitmap(bmpMissile, m.x-5, m.y, null);
				}
			}			
		}
		catch(Exception e){			
		}
		//������������
		switch(status){
		case STATUS_PAUSE:		//��Ϸ��ͣ�����˵�
			if(!father.wantSound){
				bmpMenuItem[2] = BitmapFactory.decodeResource(father.getResources(), R.drawable.menu_sound_on);
			}
			for(int i=0;i<5;i++){		//���Ʋ˵�����
				canvas.drawBitmap(bmpMenuItem[i], menuCoordinate[i][0], menuCoordinate[i][1], null);
			}
			break;
		case STATUS_WIN:		//����һ��
			canvas.drawBitmap(bmpGameWin, 70, 200, null);
			break;
		case STATUS_OVER:		//��������
			canvas.drawBitmap(bmpGameOver, 70, 200, null);
			break;
		case STATUS_PASS:		//��ȫͨ�أ�����һ����ѽ
			canvas.drawBitmap(bmpGamePass, 70, 200, null);
			break;
		}			
	}
	public void drawNumber(Canvas canvas,int life) {//��������
		String sLife = life+"";
		int length = sLife.length();
		for(int i=0;i<length;i++){
			canvas.drawBitmap(bmpNumber[sLife.charAt(i)-'0'], 280+16*i, 0, null);
		} 		
	}
	//���Ƶ�ͼ
	public void drawMap(Canvas canvas){
		for(int i=0;i<currMap.length;i++){
			for(int j=0;j<currMap[i].length;j++){
				switch(currMap[i][j]){
				case 1:	//��ͨ��ͼ
					canvas.drawBitmap(bmpTile, j*tileSize, i*tileSize, null);
					break;
				case 2: //��
					canvas.drawBitmap(bmpHome, j*tileSize, i*tileSize, null);
					break;
				case 3://��������
					canvas.drawBitmap(bmpPlusLife, j*tileSize, i*tileSize, null);
					break;
				case 4:	//���˵�
					canvas.drawBitmap(bmpEat[eatIndex], j*tileSize, i*tileSize, null);
					break;
				case 5:	//����
					canvas.drawBitmap(bmpTrap[trapIndex], j*tileSize, i*tileSize, null);
					break;
				case 6:	//����
					canvas.drawBitmap(bmpCannon, j*tileSize, i*tileSize, null);
					break;
				}
			}
		}
	}
	//����Ƿ��д���
	public boolean  checkForCannon(){
		boolean result = false;
		for(int i=0;i<currMap.length;i++){			//�Ե�ͼ���б���
			for(int j=0;j<currMap[i].length;j++){
				if(currMap[i][j] == 8){				//�����ͼͼԪΪ����
					Cannon c = new Cannon(i,j,this);	//����������ֵ����Cannon����
					alCannon.add(c);					//��Cannon������ӵ�GameView���б���
					result = true;						//��result��ֵΪtrue
				}
			}
		}
		return result;									//����result
	}
	//��ͣ��Ϸ
	public void pauseGame(){
		dt.isGameOn = false;
		gt.isGameOn = false;
		gt.flag = true;
		if(ct != null){
			ct.isGameOn = false;
		}
		status = STATUS_PAUSE;
	}
	//�ر����к�̨�߳�
	public void shutAll(){
		gt.isGameOn = false;		//��ͣGameThread
		gt.flag = false;			//ֹͣGameThread
		if(ct != null){				//����д��ڣ���ֹͣCannonThread
			ct.isGameOn = false;
			ct.flag = false;
		}
		dt.flag = false;			//ֹͣDrawThread
		dt.isGameOn = false;
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {//ʵ�ֽӿڷ���
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//��дsurfaceCreated����
		dt.isGameOn = true;				//����DrawThread
		if(!dt.isAlive()){				
			dt.start();
		}		
		gt.isGameOn = true;
		if(!gt.isAlive()){				//����GameThread
			gt.start();
		}		
		if(ct != null){					//����д��ڣ�������CannonThread
			ct.isGameOn = true;
			if(!ct.isAlive()){
				ct.start();
			}			
		}
		status = DriftBall.STATUS_PLAY;			//������Ϸ״̬
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//��дsurfaceDestroyed����
		pauseGame();
	}
	@Override
	protected void finalize() throws Throwable {
		System.out.println("WWWWWWWWWWWWWWWWWWW   be finallized    wwwwwwwwwwwwwwwwww");
		super.finalize();
	}
	
}