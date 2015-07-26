package wyf.ytl;

import static wyf.ytl.ConstantUtil.CITY_INFO;
import static wyf.ytl.ConstantUtil.DICE_SIZE;
import static wyf.ytl.ConstantUtil.DICE_SPAN;
import static wyf.ytl.ConstantUtil.DICE_START_X;
import static wyf.ytl.ConstantUtil.DICE_START_Y;
import static wyf.ytl.ConstantUtil.DIGIT_SPAN;
import static wyf.ytl.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static wyf.ytl.ConstantUtil.GAME_VIEW_SCREEN_ROWS;
import static wyf.ytl.ConstantUtil.HERO_ANIMATION_FRAMES;
import static wyf.ytl.ConstantUtil.HERO_ANIMATION_SEGMENTS;
import static wyf.ytl.ConstantUtil.HERO_ARMY_DRAW_X;
import static wyf.ytl.ConstantUtil.HERO_ARMY_DRAW_Y;
import static wyf.ytl.ConstantUtil.HERO_FACE_HEIGHT;
import static wyf.ytl.ConstantUtil.HERO_FACE_START_Y;
import static wyf.ytl.ConstantUtil.HERO_FACE_WIDTH;
import static wyf.ytl.ConstantUtil.HERO_FOOD_DRAW_X;
import static wyf.ytl.ConstantUtil.HERO_FOOD_DRAW_Y;
import static wyf.ytl.ConstantUtil.HERO_HEIGHT;
import static wyf.ytl.ConstantUtil.HERO_MONEY_DRAW_X;
import static wyf.ytl.ConstantUtil.HERO_MONEY_DRAW_Y;
import static wyf.ytl.ConstantUtil.HERO_STRENGTH_DRAW_X;
import static wyf.ytl.ConstantUtil.HERO_STRENGTH_DRAW_Y;
import static wyf.ytl.ConstantUtil.HERO_WIDTH;
import static wyf.ytl.ConstantUtil.HUI_TOU_SHI_AN;
import static wyf.ytl.ConstantUtil.MAP_BUTTON_SIZE;
import static wyf.ytl.ConstantUtil.MAP_BUTTON_START_X;
import static wyf.ytl.ConstantUtil.MAP_BUTTON_START_Y;
import static wyf.ytl.ConstantUtil.MAP_COLS;
import static wyf.ytl.ConstantUtil.MAP_ROWS;
import static wyf.ytl.ConstantUtil.MINI_MAP_SPAN;
import static wyf.ytl.ConstantUtil.MINI_MAP_START_X;
import static wyf.ytl.ConstantUtil.RIGHT;
import static wyf.ytl.ConstantUtil.TILE_SIZE;
import static wyf.ytl.ConstantUtil.WU_ZHONG_SHENG_YOU;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * 
 * 该类为游戏的主界面
 *
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener {
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		System.out.println("-------------GameView--------------finalize---------");
	}

	private int status = 0;//绘制时的状态，0正常游戏，1,英雄在走动,2，主菜单出现，3,选择将军出战，4，战斗动画
							//5，穷死结束游戏，6，统一中国
						   //100-人物属性界面,99-武将情报 98-使用计谋 97-城池管理 96-天下局势

	HDZGActivity activity;//activity的引用
	DrawThread drawThread;//刷帧的线程
	Hero hero;//英雄对象
	
	static Bitmap dialogBack;//存放对话框背景
	static Bitmap dialogButton;//存放对话框按钮
	static Bitmap [][] heroAnimationSegments;//存放英雄所有动画段的图片
	static Bitmap [] bmpDice;//存放骰子的图片数组  
	static Bitmap dashboardBitmap;//下面用于管理的图片
	static Bitmap darkBitmap;//黑色外框的效果图
	static Bitmap[] smallGameMenuOptions = new Bitmap[6];//装主菜单分割以后的图片
	static Bitmap[] bmpDigit;//存放数码管数字的图片
	static Bitmap bmpBattleFiled;//战场背景图片
	static Bitmap[] bmpHero;//存放英雄的图片,分为静态和动态,代表英雄静止还是走路
	
	int [][] notInMatrix=new int[MAP_ROWS][MAP_COLS];//存放整个大地图的不可通过矩阵
	int miniMapStartX = MINI_MAP_START_X;	//初始情况下缩略地图开始的X坐标
	int miniMapStartY = -160;		//初始情况下缩略地图开始的Y坐标
	boolean showMiniMap;//是否显示迷你地图
	
	int startRow = 0;//屏幕在大地图中的行数
	int startCol = 0;//屏幕在大地图中的列数
	int offsetX = 0;//屏幕定位点在大地图上的x方向偏移，用来实现无级滚屏
	int offsetY = 0;//屏幕定位点在大地图上的y方向偏移，用来实现无级滚屏

	LayerList layerList;//所有的层	
	MyMeetableDrawable [][] meetableMatrix;//存放大地图的可遇矩阵
	MyMeetableDrawable currentDrawable;//记录当前碰到的可遇Drawable对象引用
	MyMeetableDrawable previousDrawable;//记录上一个碰到的可遇Drawable对象引用
	MeetableLayer meetableChecker;//
	GameViewThread gvt;//后台修改数据的线程
	BattleField battleField;
	int suiXinBu=0;//记录英雄施放随心步时选择的步数
	General fightingGeneral = null;//存放当前出战的将军
	
	ArrayList<Skill> skillToLearn;//将要学习的技能
	int diceCount=2;//起作用的骰子的个数,学习了骑术会增加,最大到三
	int []diceValue = {1,3,5};//存放骰子的值,其实是骰子图片数组的下标,0-5代表1-6的骰子图片
	int currentSteps;//记录本次掷骰子需要走几步
	ManPanelView manPanelView;//人物属性窗口 
	WuJiangView wuJiangView;//武将情报窗口
	UseSkillView useSkillView;//使用计谋窗口
	CityManageView cityManageView;//城池管理窗口
	SelectGeneral selectGeneral;//选中出征武将窗口
	TianXiaView tianXiaView;//天下局势窗口
	GameAlert currentGameAlert;//记录当前的消息提示
	ArrayList<CityDrawable> allCityDrawable = new ArrayList<CityDrawable>();//存放所有敌方的城池
	ArrayList<General> freeGeneral;//自由的将领,即不属于我方和敌方的
	Paint paint;//画笔
	
	public static Resources resources;  //声明资源对象引用
	
	MediaPlayer mMediaPlayer; 
	SoundPool soundPool;//声音
	HashMap<Integer, Integer> soundPoolMap; 
	//构造器 
	public GameView(HDZGActivity activity) {
		super(activity);
		resources = this.getResources();
		if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 30;
        }
		this.activity = activity;//activity的引用
		initSounds();
		mMediaPlayer = MediaPlayer.create(activity, R.raw.backsound);
		mMediaPlayer.setLooping(true);
		if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 30;
        }
		paint = new Paint();
		paint.setColor(Color.RED);
		if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 30;
        }
		
        getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);//初始化刷帧线程
        this.gvt = new GameViewThread(this);//初始化后台数据修改线程        
        this.battleField = new BattleField(this);
        
        hero = new Hero(this, 3, 3);//创建英雄
        hero.initAnimationSegment(heroAnimationSegments);//为英雄初始化动画段列表
        hero.setAnimationDirection(RIGHT%4);//初始化英雄朝向右，静态右
        hero.startAnimation();//启动英雄动画
        initMap();//初始化地图
        initClass();//初始化所有用到的类
    	if(activity.isBackSound){
    		mMediaPlayer.start();
    	}
        if(activity.loadingView != null){//走进度条
        	activity.loadingView.process += 40;
        }
	}
	
	public void initSounds(){
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     soundPoolMap = new HashMap<Integer, Integer>();   
	     soundPoolMap.put(1, soundPool.load(getContext(), R.raw.dingdong, 1));
	     soundPoolMap.put(2, soundPool.load(getContext(), R.raw.battle, 1)); 
	} 
	public void playSound(int sound, int loop) {
	    AudioManager mgr = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   
	    
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
	
	//初始化所有用到的类
	public void initClass(){
		manPanelView = new ManPanelView(this);
		wuJiangView = new WuJiangView(this);
		useSkillView = new UseSkillView(this);
		cityManageView = new CityManageView(this);
		selectGeneral = new SelectGeneral(this);
		tianXiaView = new TianXiaView(this);
	}
	
	//方法:初始化地图
	public void initMap(){
		layerList = new LayerList(getResources());
		this.notInMatrix = layerList.getTotalNotIn();//得到总不可通过矩阵
		this.meetableChecker = (MeetableLayer)layerList.layers.get(1);
		
		//得到所有城池
		MyMeetableDrawable[][] myMeetableDrawable = (MyMeetableDrawable[][]) meetableChecker.getMapMatrix();
		Collections.shuffle(CITY_INFO);//打乱顺序
		int k = 0;//计数
		for(int i=0; i<myMeetableDrawable.length; i++){
			for(int j=0; j<myMeetableDrawable[i].length; j++){
				if(myMeetableDrawable[i][j] == null){
					continue;
				}
				else if(myMeetableDrawable[i][j] instanceof CityDrawable){//如果是个城池
					CityDrawable cityDrawable  = (CityDrawable)myMeetableDrawable[i][j];
					cityDrawable.addCityInfo(CITY_INFO.get(k));//设置城池信息 
					allCityDrawable.add(cityDrawable);//添加到我们的管理列表中
					k++;
					ArrayList<General> al = cityDrawable.getGuardGeneral();//得到该城市中所有的武将
					for(General g : al){
						g.cityDrawable = cityDrawable;
					}
				}
			}
		}
		//初始化时给英雄一个城池
		CityDrawable cd = allCityDrawable.get((int)(Math.random()*allCityDrawable.size()));
		this.hero.cityList.add(cd);	
		
		allCityDrawable.remove(cd);
		cd.setCountry(8);
		
		//初始化自由的将领
		freeGeneral = Hero.MY_GENERAL;
		
		//初始化时给英雄一个随机将领
		if(freeGeneral.size()==15){
			General general = freeGeneral.get((int)(Math.random()*freeGeneral.size()));
			this.hero.setHeroGeneral(general);
			freeGeneral.remove(general);
		}
		
		//初始化英雄可学的技能
		skillToLearn = new ArrayList<Skill>();
		skillToLearn.add(new SuiXinBuSkill(HUI_TOU_SHI_AN, "回头是岸", -1, 1, this.hero));
		skillToLearn.add(new SuiXinBuSkill(WU_ZHONG_SHENG_YOU, "无中生有", -1, 1, this.hero));
	}
	
	//初始化图片资源的方法
	public static void initBitmap(Resources r){		
		dashboardBitmap = BitmapFactory.decodeResource(r, R.drawable.dashboard);//仪表盘图片
		Bitmap bigGameMenuOptions = BitmapFactory.decodeResource(r, R.drawable.game_menu_options);
		for(int i=0; i<smallGameMenuOptions.length; i++){//菜单图片数组初始化
			smallGameMenuOptions[i] = Bitmap.createBitmap(bigGameMenuOptions, 0, (bigGameMenuOptions.getHeight()/smallGameMenuOptions.length)*i, bigGameMenuOptions.getWidth(), bigGameMenuOptions.getHeight()/smallGameMenuOptions.length);
		}
		bigGameMenuOptions = null;//释放掉大图
		
		Bitmap tempBmp = BitmapFactory.decodeResource(r, R.drawable.hero); //加载包含英雄动画的大图
		heroAnimationSegments = new Bitmap[HERO_ANIMATION_SEGMENTS][HERO_ANIMATION_FRAMES];
		for(int i=0;i<HERO_ANIMATION_SEGMENTS;i++){//对大图进行切割，转换成Bitmap的二维数组
			for(int j=0;j<HERO_ANIMATION_FRAMES;j++){
				heroAnimationSegments[i][j] = Bitmap.createBitmap
												(
												tempBmp, 
												j*HERO_WIDTH, i*HERO_HEIGHT,
												HERO_WIDTH, HERO_HEIGHT
												);
			}
		}
		tempBmp = null;//释放掉大图
		Bitmap bmpTemp = BitmapFactory.decodeResource(r, R.drawable.dice);//获取骰子大图片
		bmpDice = new Bitmap[6];//声明数组
		for(int i=0;i<6;i++){
			bmpDice[i] = Bitmap.createBitmap(bmpTemp, DICE_SIZE*i, 0, DICE_SIZE, DICE_SIZE);
		}
		bmpTemp = null;//释放掉大图
		bmpBattleFiled = BitmapFactory.decodeResource(r, R.drawable.battle_field);
		//初始画数码管的图片
		bmpDigit = new Bitmap[10];
		bmpDigit[0] = BitmapFactory.decodeResource(r, R.drawable.digit_0);
		bmpDigit[1] = BitmapFactory.decodeResource(r, R.drawable.digit_1);
		bmpDigit[2] = BitmapFactory.decodeResource(r, R.drawable.digit_2);
		bmpDigit[3] = BitmapFactory.decodeResource(r, R.drawable.digit_3);
		bmpDigit[4] = BitmapFactory.decodeResource(r, R.drawable.digit_4);
		bmpDigit[5] = BitmapFactory.decodeResource(r, R.drawable.digit_5);
		bmpDigit[6] = BitmapFactory.decodeResource(r, R.drawable.digit_6);
		bmpDigit[7] = BitmapFactory.decodeResource(r, R.drawable.digit_7);
		bmpDigit[8] = BitmapFactory.decodeResource(r, R.drawable.digit_8);
		bmpDigit[9] = BitmapFactory.decodeResource(r, R.drawable.digit_9); 

		bmpHero = new Bitmap[2];
		bmpHero[0] = BitmapFactory.decodeResource(r, R.drawable.head_static);//英雄静态头像
		bmpHero[1] = BitmapFactory.decodeResource(r, R.drawable.head_dynamic);//英雄动态头像
		bmpBattleFiled = BitmapFactory.decodeResource(r, R.drawable.battle_field);
		dialogBack = BitmapFactory.decodeResource(r, R.drawable.dialog_back);
		Bitmap temp = BitmapFactory.decodeResource(r, R.drawable.buttons);
		dialogButton = Bitmap.createBitmap(temp, 0, 0, 60, 30);
	}
	
	//方法:绘制屏幕
	public void onDraw(Canvas canvas){//自己写的绘制方法
		//画的内容是z轴的，后画的会覆盖前面画的
		if(status == 0 || status == 1 || status == 2){//游戏主界面时
			canvas.drawColor(Color.BLACK);
			int heroX=hero.x;//英雄中心点的x坐标
			int heroY=hero.y;//英雄中心点的y坐标
			int hCol = heroX/TILE_SIZE;//计算英雄中心点位于哪个格子
			int hRow = heroY/TILE_SIZE;//计算英雄中心点位于哪个格子
			int tempStartRow = this.startRow;//记录本次绘制时的定位行
			int tempStartCol = this.startCol;//记录本次绘制时的定位列
			int tempOffsetX = this.offsetX;//记录本次绘制时的x偏移
			int tempOffsetY = this.offsetY;//记录本次绘制时的y偏移
			/*
			 * 绘制底层
			 * 这里为了实现无级滚屏，绘制屏幕所囊括的地图时必须多绘制一圈，但是有时定位点已然是边缘了，就continue一下
			 */
			for(int i=-1; i<=GAME_VIEW_SCREEN_ROWS; i++){     
				if(tempStartRow+i < 0 || tempStartRow+i>MAP_ROWS){//如果多画的那一行不存在，就继续
					continue;
				}
				for(int j=-1; j<=GAME_VIEW_SCREEN_COLS; j++){
					if(tempStartCol+j <0 || tempStartCol+j>MAP_COLS){//如果多画的那一列不存在，就继续
						continue;
					}
					Layer l = (Layer)layerList.layers.get(0);//获得底层的图层
					MyDrawable[][] mapMatrix=l.getMapMatrix();
					if(mapMatrix[i+tempStartRow][j+tempStartCol] != null){
						mapMatrix[i+tempStartRow][j+tempStartCol].drawSelf(canvas,i,j,tempOffsetX,tempOffsetY);
					}		
				}
			} 

			//绘制上层
			for(int i=-1; i<=GAME_VIEW_SCREEN_ROWS; i++){
				if(tempStartRow+i < 0 || tempStartRow+i>MAP_ROWS){//如果多画的那一行不存在，就继续
					continue;
				}
				for(int j=-1; j<=GAME_VIEW_SCREEN_COLS; j++){
					if(tempStartCol+j <0 || tempStartCol+j>MAP_COLS){//如果多画的那一列不存在，就继续
						continue;
					}
					Layer l = (Layer)layerList.layers.get(1);//获得上层的图层
					MyDrawable[][] mapMatrix=l.getMapMatrix();
					if(mapMatrix[i+tempStartRow][j+tempStartCol] != null){
						mapMatrix[i+tempStartRow][j+tempStartCol].drawSelf(canvas,i,j,tempOffsetX,tempOffsetY);
					}		
					if(hRow-tempStartRow == i && hCol-tempStartCol == j){//英雄在这里
						hero.drawSelf(canvas,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
					}
				}
			}
			
			//绘制仪表盘
			canvas.drawBitmap(dashboardBitmap, 0, ConstantUtil.SCREEN_HEIGHT-dashboardBitmap.getHeight(), paint);			
			drawDice(canvas);//绘制骰子
			drawHeroData(canvas);//绘制仪表盘上的数据
			
			canvas.drawBitmap(bmpHero[status==1?1:0], 0, HERO_FACE_START_Y, null);//画英雄的头像
			if(showMiniMap){//检查是否需要显示小地图
				drawMiniMap(canvas,hCol,hRow);
			}
			
			if(status == 2){//当需要绘制主菜单时
				if(darkBitmap != null){
					canvas.drawBitmap(darkBitmap, 0, 0, paint);
				}
				for(int i=0; i<smallGameMenuOptions.length; i++){//绘制菜单
					canvas.drawBitmap(smallGameMenuOptions[i], ConstantUtil.GAME_VIEW_MEMU_LEFT_SPACE, ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+(smallGameMenuOptions[i].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)*i, paint);
				}
			}
			if(currentDrawable != null){//判断是否需要绘制可遇实体的对话框
				currentDrawable.drawDialog(canvas, hero);
			}
			if(currentGameAlert != null && currentDrawable == null){//判断是否需要绘制游戏提示
				currentGameAlert.drawDialog(canvas);		//绘制对话框
				setOnTouchListener(currentGameAlert);		//设置GameView的监听器
			}
		}
		else if(status == 4){
			battleField.onDraw(canvas);
		}		
		else if(status == 100){//人物属性界面
			manPanelView.onDraw(canvas);
		}
		else if(status == 99){//武将情报 
			wuJiangView.onDraw(canvas);
		}
		else if(status == 98){//使用计谋
			useSkillView.onDraw(canvas);
		}
		else if(status == 97){//城池管理
			cityManageView.onDraw(canvas);
		}
		else if(status == 3){//选中出征将领
			selectGeneral.onDraw(canvas);
		}
		else if(status == 96){//天下局势
			tianXiaView.onDraw(canvas);
		}
	}

	public void drawMiniMap(Canvas canvas,int col,int row){
		Paint paint = new Paint();		
		int rows = notInMatrix.length;		//行数
		int cols = notInMatrix[0].length;	//列数
		int tempStartX = miniMapStartX;
		int tempStartY = miniMapStartY;
		for(int i=0;i<this.notInMatrix.length;i++){
			for(int j=0;j<this.notInMatrix[i].length;j++){
				if(notInMatrix[i][j] == 0){
					paint.setARGB(128, 50, 80, 224);
					canvas.drawRect(
							tempStartX+j*MINI_MAP_SPAN, 
							tempStartY+i*MINI_MAP_SPAN, 
							tempStartX+j*MINI_MAP_SPAN+MINI_MAP_SPAN, 
							tempStartY+i*MINI_MAP_SPAN+MINI_MAP_SPAN, 
							paint
							);					
				}
				else{//画障碍物
					paint.setARGB(128, 0, 0, 0);
					if(i==0&&j==0 || i==0&&j==cols-1 ||i==rows-1&&j==0 || i==rows-1&&j==cols-1){
						int newI = (i==0?i:i-1);
						int newJ = (j==0?j:j-1);
						int startAngle = 0;
						RectF r = new RectF(
								tempStartX+newJ*MINI_MAP_SPAN,
								tempStartY+newI*MINI_MAP_SPAN,
								tempStartX+newJ*MINI_MAP_SPAN+MINI_MAP_SPAN*2,
								tempStartY+newI*MINI_MAP_SPAN+MINI_MAP_SPAN*2);
						if(i==0){
							if(j==0){
								startAngle = 180;
							}
							else{
								startAngle = 270;
							}
						} 
						else{
							if(j==0){
								startAngle = 90;
							}		
							else{
								startAngle = 0;
							}
						}
						canvas.drawArc(r, startAngle, 90, true, paint);
					}
					else{
						canvas.drawRect(
								tempStartX+j*MINI_MAP_SPAN, 
								tempStartY+i*MINI_MAP_SPAN, 
								tempStartX+j*MINI_MAP_SPAN+MINI_MAP_SPAN, 
								tempStartY+i*MINI_MAP_SPAN+MINI_MAP_SPAN, 
								paint
								);							
					}
				}
			}
		}
		//把英雄也画上去
		paint.setARGB(255, 255, 0, 0);
		canvas.drawRect(
				tempStartX+col*MINI_MAP_SPAN, 
				tempStartY+row*MINI_MAP_SPAN, 
				tempStartX+col*MINI_MAP_SPAN+MINI_MAP_SPAN, 
				tempStartY+row*MINI_MAP_SPAN+MINI_MAP_SPAN, 
				paint
				);
	}
	
	
	//方法：画骰子
	public void drawDice(Canvas canvas){
		for(int i=0;i<diceCount;i++){//按照起作用骰子的个数画骰子
			canvas.drawBitmap(bmpDice[diceValue[i]], DICE_START_X+i*DICE_SPAN, DICE_START_Y, null);
		}
	}
	
	//方法：主要的游戏数据
	public void drawHeroData(Canvas canvas){
		int [] strengthValue = getDigitSequence(hero.getStrength());
		drawDigits(canvas, strengthValue, HERO_STRENGTH_DRAW_X, HERO_STRENGTH_DRAW_Y);
		int [] moneyValue = getDigitSequence(hero.getTotalMoney());
		drawDigits(canvas, moneyValue, HERO_MONEY_DRAW_X, HERO_MONEY_DRAW_Y);
		int [] armyValue = getDigitSequence(hero.getArmyWithMe());
		drawDigits(canvas, armyValue, HERO_ARMY_DRAW_X, HERO_ARMY_DRAW_Y);
		int [] foodValue = getDigitSequence(hero.getFood());
		drawDigits(canvas, foodValue, HERO_FOOD_DRAW_X, HERO_FOOD_DRAW_Y);
	}
	//方法:获得一项数据的六位数码管显示序列
	public int[] getDigitSequence(int data){
		int [] result = new int[6];
		String sData = data+"";		
		int zeroSpan = 6-sData.length();//求出前面画多少零
		int i=0;
		for(;i<zeroSpan;i++){//补零
			result[i] = 0;
		}
		for(;i<6;i++){//画有效数字
			result[i] = sData.charAt(i-zeroSpan)-'0';
		}
		return result;
	}
	
	//方法：按照数码管数字序列画出6个数字
	public void drawDigits(Canvas canvas,int [] sequence,int x,int y){
		for(int i=0;i<6;i++){
			canvas.drawBitmap(bmpDigit[sequence[i]], x+i*DIGIT_SPAN, y, null);
		}
	}
	
	public void setCurrentDrawable(MyMeetableDrawable currentDrawable) {
		this.currentDrawable = currentDrawable;
	}
	
	//方法：设置GameView的状态
	public void setStatus(int status){
		this.status = status;
	}
	
	//重写的监听器实现方法
	public boolean onTouch(View view, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//之捕捉屏幕被按下的事件
			int x = (int)event.getX();//得到坐标
			int y = (int)event.getY();
			//游戏主界面中
			if(this.status == 0){
				if(x>282 && x<310  && y>400 && y<470){//点击主菜单
					darkBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dark);
					this.status = 2;
				}
				else if(x>62 && x<80 && y>408 && y<426){//点击了人物属性按钮
					this.manPanelView.initData();
					this.status = 100;
				}
				else if(x>62 && x<80 && y>427 && y<445){//武将情报
					this.wuJiangView.initData();
					this.status = 99;
				}
				else if(x>42 && x<61 && y>427 && y<445){//城池管理
					this.cityManageView.initData();
					this.status = 97;
				}
				else if(x>22 && x<41 && y>427 && y<445){//天下局势
					this.tianXiaView.initData();
					this.status = 96;
				}
				else if(x>2 && x<21 && y>427 && y<445){//使用计谋
					this.useSkillView.initData();
					this.status = 98;
				}
				//判断是否点击了人物头像
				else if(x >0 && x<HERO_FACE_WIDTH && y>HERO_FACE_START_Y && y< HERO_FACE_START_Y+HERO_FACE_HEIGHT){
					if(this.status == 0){//是否是待命状态
						this.gvt.setChanging(false);//暂停变换骰子的线程
						this.diceValue = hero.throwDice(this.diceCount);
						currentSteps = 0;//记录要走几格
						for(int i=0;i<diceCount;i++){
							currentSteps = currentSteps+diceValue[i]+1;
						}
						hero.startToGo(currentSteps);
					}
				}
				else if(x > MAP_BUTTON_START_X && x< MAP_BUTTON_START_X+MAP_BUTTON_SIZE 
						&& y> MAP_BUTTON_START_Y && y<MAP_BUTTON_START_Y+MAP_BUTTON_SIZE){//如果点下是地图开关
					showMiniMap = !showMiniMap;
					miniMapStartY = -160;	//复位
				}
				else if(x > DICE_START_X+DICE_SPAN && x<DICE_START_X+DICE_SPAN+DICE_SIZE
						&& y>DICE_START_Y && y<DICE_START_Y+DICE_SIZE){//点下了第二颗骰子
					if(this.diceCount == 1){//当前如果只有1个骰子活跃，就变成2个
						this.diceCount++;
					}
					else if(this.diceCount == 2){//当钱如果有2个骰子活跃，就变成1个
						this.diceCount --;
					}
				}
				else if(x > DICE_START_X+DICE_SPAN*2 && x<DICE_START_X+DICE_SPAN*2+DICE_SIZE
						&& y>DICE_START_Y && y<DICE_START_Y+DICE_SIZE){//点下了第三颗骰子
					if(this.diceCount == 2){//当前如果只有2个骰子活跃，就变成3个
						this.diceCount ++;
					}
					else if(this.diceCount == 3){//当前如果有3个骰子活跃，就变成2个
						this.diceCount --;
					}
				}
			}			
			else if(this.status == 2){//主菜单出现时
				if(x>ConstantUtil.GAME_VIEW_MEMU_LEFT_SPACE && x<ConstantUtil.GAME_VIEW_MEMU_LEFT_SPACE+smallGameMenuOptions[0].getWidth()){//x满足要求
					if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE && y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+smallGameMenuOptions[0].getHeight()){
						//点击的是载入游戏
						this.status = 0;
						this.mMediaPlayer.stop();
						this.hero.ht.isGameOn = false;
						this.hero.ht.flag = false;
						this.hero.ht.interrupt();
						
						this.hero.hbdt.flag = false;
						this.hero.hbdt.interrupt();
						
						this.hero.hgt.isMoving = false;
						this.hero.hgt.flag = false;
						this.hero.hgt.interrupt();
						
						try{
							Thread.sleep(200);
						}
						catch(Exception e){
							e.printStackTrace();
						}						
						
						activity.myHandler.sendEmptyMessage(99);
					}
					else if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+1*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE) 
							&& y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+1*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)+smallGameMenuOptions[1].getHeight()){
						//点击保存游戏
						this.status = 0;
						SerializableGame.saveGameStatus(this);
					}
					else if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+2*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE) 
							&& y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+2*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)+smallGameMenuOptions[1].getHeight()){
						//点击音效设置
						this.status = 0;
						activity.myHandler.sendEmptyMessage(10);
					}
					else if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+3*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE) 
							&& y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+3*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)+smallGameMenuOptions[1].getHeight()){
						//点击帮助
						this.status = 0;
						this.activity.myHandler.sendEmptyMessage(8);
					}
					else if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+4*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE) 
							&& y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+4*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)+smallGameMenuOptions[1].getHeight()){
						//点击退出游戏
						this.mMediaPlayer.stop();
						System.exit(0);
					}
					else if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+5*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE) 
							&& y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+5*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)+smallGameMenuOptions[1].getHeight()){
						//点击返回游戏
						if(darkBitmap != null){
							darkBitmap = null;
						}
						this.status = 0;
					}
				}
			}
			else if(status == 100){//人物属性界面出现 
				manPanelView.onTouchEvent(event);
			}
			else if(status == 99){//武将情报界面
				wuJiangView.onTouchEvent(event);
			}
			else if(status == 98){//使用计谋界面
				useSkillView.onTouchEvent(event);
			}
			else if(status == 97){//城池管理界面
				cityManageView.onTouchEvent(event);
			}
			else if(status == 3){//
				selectGeneral.onTouchEvent(event);
			}			
			else if(status == 96){
				tianXiaView.onTouchEvent(event);
			}
		}
		return true;
	}
	
	public void stopGame(){
		this.mMediaPlayer.stop();//停止播放声音
		this.drawThread.isViewOn = false;
		this.drawThread.flag = false;
		hero.hbdt.flag = false;//关后台线程
		this.gvt.setChanging(false);
		this.gvt.flag = false;
	}
	
	public GameAlert getCurrentGameAlert() {
		return currentGameAlert;
	}

	public void setCurrentGameAlert(GameAlert currentGameAlert) {
		this.currentGameAlert = currentGameAlert;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}
	
	public void surfaceCreated(SurfaceHolder holder) {

		this.drawThread.setFlag(true);
		drawThread.setIsViewOn(true);
        if(! drawThread.isAlive()){//如果后台重绘线程没起来,就启动它
        	try
        	{
        		drawThread.start();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}        	
        }
        
        this.setOnTouchListener(this);
        this.gvt.isChanging = true;//设置换骰子标志位为真
        if(!gvt.isAlive()){//如果后台线程没起来,就启动它        	
        	try
        	{
        		gvt.start();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}           	
        }
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		this.drawThread.setIsViewOn(false);
		
	}
	
	class DrawThread extends Thread{//刷帧线程

		private int sleepSpan = ConstantUtil.GAME_VIEW_SLEEP_SPAN;//睡眠的毫秒数 
		private SurfaceHolder surfaceHolder;
		private GameView gameView;
		private boolean isViewOn = false;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder, GameView gameView) {//构造器
        	super.setName("==GameView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }
        
        public void setFlag(boolean flag) {//设置循环标记位
        	this.flag = flag;
        }
        
        public void setIsViewOn(boolean isViewOn){
        	this.isViewOn = isViewOn;
        }
        
		public void run() {
			Canvas c;
			while(flag){
	            while (isViewOn) {
	                c = null;
	                try {
	                	// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
	                    c = this.surfaceHolder.lockCanvas(null);
	                    synchronized (this.surfaceHolder) {
	                    	gameView.onDraw(c);
	                    }
	                } finally {
	                    if (c != null) {
	                    	//更新屏幕显示内容
	                        this.surfaceHolder.unlockCanvasAndPost(c);
	                    }
	                }
	                try{
	                	Thread.sleep(sleepSpan);//睡眠指定毫秒数
	                }
	                catch(Exception e){
	                	e.printStackTrace();
	                }
	            }
	            try{
	            	Thread.sleep(1500);//睡眠指定毫秒数
	            }
	            catch(Exception e){
	            	e.printStackTrace();
	            }
			}
		}
	}
}