package wyf.wpf;			//声明包语句
import java.util.ArrayList;				//引入相关类
import android.content.res.Resources;	//引入相关类
import android.graphics.Bitmap;	//引入相关类
import android.graphics.BitmapFactory;	//引入相关类
import android.graphics.Canvas;	//引入相关类
import android.view.SurfaceHolder;	//引入相关类
import android.view.SurfaceView;	//引入相关类
import android.view.SurfaceHolder.Callback;	//引入相关类
import static wyf.wpf.DriftBall.*;	//引入相关类
/*
 * 该类继承自SurfaceView，主要功能是绘制游戏屏幕，对后台的绘制或其他线程
 * 进行控制。
 */
public class GameView extends SurfaceView implements Callback{
	int screenWidth = 320;		//屏幕宽度
	int screenHeight = 480;		//屏幕高度
	int backY;					//x坐标总是为零
	int nebulaX;				//星云的x坐标
	int nebulaY;				//星云的y坐标
	int ballX;					//小球的横坐标
	int ballY;					//小球的纵坐标
	int tileSize = 20;			//图元的大小
	int direction = -1;			//小球运动方向，上为0，顺时针依次为1~7
	int velocity = 4;			//小球运动速度
	int eatIndex;				//吃人动画帧索引
	int status;					//游戏状态
	int trapIndex;				//陷阱动画索引
	
	boolean showMenu;			//是否显示菜单
	
	DriftBall father;			//DriftBall引用
	DrawThread dt;				//绘制线程
	GameThread gt;				//后台数据的修改线程
	CannonThread ct;			//大炮线程
	GameMenuThread gmt;			//游戏菜单线程
	Meteorolite [] meteoArray;	//陨石数组
	ArrayList<Missile> alMissile = new ArrayList<Missile>();		//存放导弹集合
	ArrayList<Cannon> alCannon = new ArrayList<Cannon>();			//存放大炮集合
		
	Bitmap bmpStar;			//星空图片
	Bitmap bmpNebula;		//星云图片
	Bitmap bmpBall;			//小球图片
	Bitmap bmpTile;			//地图图元，即路的图片
	Bitmap [] bmpMeteo; 	//陨石图片
	Bitmap [] bmpEat;		//吃人图片
	Bitmap bmpHome;			//家的图片
	Bitmap [] bmpTrap;		//陷阱图片
	Bitmap bmpCannon;		//大炮图片
	Bitmap bmpMissile;		//导弹图片
	Bitmap bmpPlusLife;		//加命图片
	Bitmap bmpMultiply;		//乘号图片，用以显示命数
	Bitmap [] bmpNumber;	//数字图片
	Bitmap [] bmpMenuItem;	//菜单项图片数组
	Bitmap bmpGameWin;		//游戏胜利图片
	Bitmap bmpGameOver;		//游戏结束图片
	Bitmap bmpGamePass;		//游戏通关图片
	
	//地图，为零则空，为1则路,2为家,6是吃小球的,7是陷阱,8是大炮
	byte [][] currMap;
	int [][] menuCoordinate;	//菜单及菜单项的位置
	
	public GameView(DriftBall father) {
		super(father);
		this.father = father;
		//初始化图片
		initBitmap(father);
		//获得SurfaceHolder
		getHolder().addCallback(this);
		dt = new DrawThread(getHolder(),this);
		gt = new GameThread(this);
		//初始化陨石数组
		meteoArray = new Meteorolite[3];
		for(int i=0;i<3;i++){
			meteoArray[i] = new Meteorolite();
		}
		initGame();		
	}
	//初始化图片资源
	public void initBitmap(DriftBall father){
		Resources r = father.getResources();
		bmpStar = BitmapFactory.decodeResource(r, R.drawable.stars);
		bmpNebula = BitmapFactory.decodeResource(r, R.drawable.nebula);
		bmpBall = BitmapFactory.decodeResource(r, R.drawable.ball); 
		bmpTile = BitmapFactory.decodeResource(r, R.drawable.tile);
		bmpMeteo = new Bitmap[8];			//陨石动画帧
		bmpMeteo[0] = BitmapFactory.decodeResource(r, R.drawable.meteo1);
		bmpMeteo[1] = BitmapFactory.decodeResource(r, R.drawable.meteo2);
		bmpMeteo[2] = BitmapFactory.decodeResource(r, R.drawable.meteo3);
		bmpMeteo[3] = BitmapFactory.decodeResource(r, R.drawable.meteo4);
		bmpMeteo[4] = BitmapFactory.decodeResource(r, R.drawable.meteo5);
		bmpMeteo[5] = BitmapFactory.decodeResource(r, R.drawable.meteo6);
		bmpMeteo[6] = BitmapFactory.decodeResource(r, R.drawable.meteo7);
		bmpMeteo[7] = BitmapFactory.decodeResource(r, R.drawable.meteo8);
		bmpEat = new Bitmap[4];				//吃人动画帧
		bmpEat[0] = BitmapFactory.decodeResource(r, R.drawable.eat_1);
		bmpEat[1] = BitmapFactory.decodeResource(r, R.drawable.eat_2);
		bmpEat[2] = BitmapFactory.decodeResource(r, R.drawable.eat_3);
		bmpEat[3] = BitmapFactory.decodeResource(r, R.drawable.eat_4); 
		bmpHome = BitmapFactory.decodeResource(r, R.drawable.home);
		bmpTrap = new Bitmap[8];			//陷阱动画帧
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
		bmpNumber = new Bitmap[10];			//初始化数字图片资源
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
	//初始化参数    
	public void initGame(){
		this.ballX = 0;
		this.ballY = 0;
		this.backY = 0;
		this.nebulaX = (int)(Math.random()*150);
		this.nebulaY = -120;
		this.currMap = GameMap.getMap(father.level);
		//检查大炮
		if(checkForCannon()){
			ct = new CannonThread(this);
		}
		System.out.println("*****the size of alCannon:"+alCannon.size()+"***********");
	}
	//回复游戏
	public void resumeGame(){
		dt.isGameOn = true;		//
		gt.isGameOn = true;
		if(ct != null){
			ct.isGameOn = true;
		}
		status = STATUS_PLAY;
	}
	//屏幕绘制方法
	public void doDraw(Canvas canvas){
		//绘制星空背景
		canvas.drawBitmap(bmpStar,0,backY,null);
		if(backY <= -120){
			canvas.drawBitmap(bmpStar, 0, backY+600, null);
		}
		//绘制星云
		canvas.drawBitmap(bmpNebula, nebulaX, nebulaY, null);
		//画下面的陨石
		for(Meteorolite m:meteoArray){
			if(!m.up){
				canvas.drawBitmap(bmpMeteo[m.index], m.x,m.y,null);
			} 
		}
		//绘制地图
		drawMap(canvas);
		//绘制小球
		if(status != STATUS_OVER && status != STATUS_PASS){
			canvas.drawBitmap(bmpBall, ballX, ballY, null);
		}		
		//画上面的陨石
		for(Meteorolite m:meteoArray){
			if(m.up){
				canvas.drawBitmap(bmpMeteo[m.index], m.x,m.y,null);
			}
		}
		//画还有几条命
		canvas.drawBitmap(bmpBall, 240, 0, null);
		canvas.drawBitmap(bmpMultiply, 265, 2, null);
		drawNumber(canvas,father.life);	
		//画炮弹
		try{
			if(alMissile.size() != 0){
				for(Missile m:alMissile){
					canvas.drawBitmap(bmpMissile, m.x-5, m.y, null);
				}
			}			
		}
		catch(Exception e){			
		}
		//绘制其他界面
		switch(status){
		case STATUS_PAUSE:		//游戏暂停，出菜单
			if(!father.wantSound){
				bmpMenuItem[2] = BitmapFactory.decodeResource(father.getResources(), R.drawable.menu_sound_on);
			}
			for(int i=0;i<5;i++){		//绘制菜单子项
				canvas.drawBitmap(bmpMenuItem[i], menuCoordinate[i][0], menuCoordinate[i][1], null);
			}
			break;
		case STATUS_WIN:		//过了一关
			canvas.drawBitmap(bmpGameWin, 70, 200, null);
			break;
		case STATUS_OVER:		//死完命了
			canvas.drawBitmap(bmpGameOver, 70, 200, null);
			break;
		case STATUS_PASS:		//完全通关，不是一般人呀
			canvas.drawBitmap(bmpGamePass, 70, 200, null);
			break;
		}			
	}
	public void drawNumber(Canvas canvas,int life) {//绘制数字
		String sLife = life+"";
		int length = sLife.length();
		for(int i=0;i<length;i++){
			canvas.drawBitmap(bmpNumber[sLife.charAt(i)-'0'], 280+16*i, 0, null);
		} 		
	}
	//绘制地图
	public void drawMap(Canvas canvas){
		for(int i=0;i<currMap.length;i++){
			for(int j=0;j<currMap[i].length;j++){
				switch(currMap[i][j]){
				case 1:	//普通地图
					canvas.drawBitmap(bmpTile, j*tileSize, i*tileSize, null);
					break;
				case 2: //家
					canvas.drawBitmap(bmpHome, j*tileSize, i*tileSize, null);
					break;
				case 3://加命奖励
					canvas.drawBitmap(bmpPlusLife, j*tileSize, i*tileSize, null);
					break;
				case 4:	//吃人的
					canvas.drawBitmap(bmpEat[eatIndex], j*tileSize, i*tileSize, null);
					break;
				case 5:	//陷阱
					canvas.drawBitmap(bmpTrap[trapIndex], j*tileSize, i*tileSize, null);
					break;
				case 6:	//大炮
					canvas.drawBitmap(bmpCannon, j*tileSize, i*tileSize, null);
					break;
				}
			}
		}
	}
	//检查是否有大炮
	public boolean  checkForCannon(){
		boolean result = false;
		for(int i=0;i<currMap.length;i++){			//对地图进行遍历
			for(int j=0;j<currMap[i].length;j++){
				if(currMap[i][j] == 8){				//如果地图图元为大炮
					Cannon c = new Cannon(i,j,this);	//根据其行列值创建Cannon对象
					alCannon.add(c);					//将Cannon对象添加到GameView的列表中
					result = true;						//将result赋值为true
				}
			}
		}
		return result;									//返回result
	}
	//暂停游戏
	public void pauseGame(){
		dt.isGameOn = false;
		gt.isGameOn = false;
		gt.flag = true;
		if(ct != null){
			ct.isGameOn = false;
		}
		status = STATUS_PAUSE;
	}
	//关闭所有后台线程
	public void shutAll(){
		gt.isGameOn = false;		//暂停GameThread
		gt.flag = false;			//停止GameThread
		if(ct != null){				//如果有大炮，就停止CannonThread
			ct.isGameOn = false;
			ct.flag = false;
		}
		dt.flag = false;			//停止DrawThread
		dt.isGameOn = false;
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {//实现接口方法
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//重写surfaceCreated方法
		dt.isGameOn = true;				//启动DrawThread
		if(!dt.isAlive()){				
			dt.start();
		}		
		gt.isGameOn = true;
		if(!gt.isAlive()){				//启动GameThread
			gt.start();
		}		
		if(ct != null){					//如果有大炮，就启动CannonThread
			ct.isGameOn = true;
			if(!ct.isAlive()){
				ct.start();
			}			
		}
		status = DriftBall.STATUS_PLAY;			//设置游戏状态
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {//重写surfaceDestroyed方法
		pauseGame();
	}
	@Override
	protected void finalize() throws Throwable {
		System.out.println("WWWWWWWWWWWWWWWWWWW   be finallized    wwwwwwwwwwwwwwwwww");
		super.finalize();
	}
	
}