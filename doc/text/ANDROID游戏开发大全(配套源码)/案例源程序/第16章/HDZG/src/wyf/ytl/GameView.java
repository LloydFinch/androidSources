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
 * ����Ϊ��Ϸ��������
 *
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener {
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		System.out.println("-------------GameView--------------finalize---------");
	}

	private int status = 0;//����ʱ��״̬��0������Ϸ��1,Ӣ�����߶�,2�����˵����֣�3,ѡ�񽫾���ս��4��ս������
							//5������������Ϸ��6��ͳһ�й�
						   //100-�������Խ���,99-�佫�鱨 98-ʹ�ü�ı 97-�ǳع��� 96-���¾���

	HDZGActivity activity;//activity������
	DrawThread drawThread;//ˢ֡���߳�
	Hero hero;//Ӣ�۶���
	
	static Bitmap dialogBack;//��ŶԻ��򱳾�
	static Bitmap dialogButton;//��ŶԻ���ť
	static Bitmap [][] heroAnimationSegments;//���Ӣ�����ж����ε�ͼƬ
	static Bitmap [] bmpDice;//������ӵ�ͼƬ����  
	static Bitmap dashboardBitmap;//�������ڹ����ͼƬ
	static Bitmap darkBitmap;//��ɫ����Ч��ͼ
	static Bitmap[] smallGameMenuOptions = new Bitmap[6];//װ���˵��ָ��Ժ��ͼƬ
	static Bitmap[] bmpDigit;//�����������ֵ�ͼƬ
	static Bitmap bmpBattleFiled;//ս������ͼƬ
	static Bitmap[] bmpHero;//���Ӣ�۵�ͼƬ,��Ϊ��̬�Ͷ�̬,����Ӣ�۾�ֹ������·
	
	int [][] notInMatrix=new int[MAP_ROWS][MAP_COLS];//����������ͼ�Ĳ���ͨ������
	int miniMapStartX = MINI_MAP_START_X;	//��ʼ��������Ե�ͼ��ʼ��X����
	int miniMapStartY = -160;		//��ʼ��������Ե�ͼ��ʼ��Y����
	boolean showMiniMap;//�Ƿ���ʾ�����ͼ
	
	int startRow = 0;//��Ļ�ڴ��ͼ�е�����
	int startCol = 0;//��Ļ�ڴ��ͼ�е�����
	int offsetX = 0;//��Ļ��λ���ڴ��ͼ�ϵ�x����ƫ�ƣ�����ʵ���޼�����
	int offsetY = 0;//��Ļ��λ���ڴ��ͼ�ϵ�y����ƫ�ƣ�����ʵ���޼�����

	LayerList layerList;//���еĲ�	
	MyMeetableDrawable [][] meetableMatrix;//��Ŵ��ͼ�Ŀ�������
	MyMeetableDrawable currentDrawable;//��¼��ǰ�����Ŀ���Drawable��������
	MyMeetableDrawable previousDrawable;//��¼��һ�������Ŀ���Drawable��������
	MeetableLayer meetableChecker;//
	GameViewThread gvt;//��̨�޸����ݵ��߳�
	BattleField battleField;
	int suiXinBu=0;//��¼Ӣ��ʩ�����Ĳ�ʱѡ��Ĳ���
	General fightingGeneral = null;//��ŵ�ǰ��ս�Ľ���
	
	ArrayList<Skill> skillToLearn;//��Ҫѧϰ�ļ���
	int diceCount=2;//�����õ����ӵĸ���,ѧϰ������������,�����
	int []diceValue = {1,3,5};//������ӵ�ֵ,��ʵ������ͼƬ������±�,0-5����1-6������ͼƬ
	int currentSteps;//��¼������������Ҫ�߼���
	ManPanelView manPanelView;//�������Դ��� 
	WuJiangView wuJiangView;//�佫�鱨����
	UseSkillView useSkillView;//ʹ�ü�ı����
	CityManageView cityManageView;//�ǳع�����
	SelectGeneral selectGeneral;//ѡ�г����佫����
	TianXiaView tianXiaView;//���¾��ƴ���
	GameAlert currentGameAlert;//��¼��ǰ����Ϣ��ʾ
	ArrayList<CityDrawable> allCityDrawable = new ArrayList<CityDrawable>();//������ез��ĳǳ�
	ArrayList<General> freeGeneral;//���ɵĽ���,���������ҷ��͵з���
	Paint paint;//����
	
	public static Resources resources;  //������Դ��������
	
	MediaPlayer mMediaPlayer; 
	SoundPool soundPool;//����
	HashMap<Integer, Integer> soundPoolMap; 
	//������ 
	public GameView(HDZGActivity activity) {
		super(activity);
		resources = this.getResources();
		if(activity.loadingView != null){//�߽�����
        	activity.loadingView.process += 30;
        }
		this.activity = activity;//activity������
		initSounds();
		mMediaPlayer = MediaPlayer.create(activity, R.raw.backsound);
		mMediaPlayer.setLooping(true);
		if(activity.loadingView != null){//�߽�����
        	activity.loadingView.process += 30;
        }
		paint = new Paint();
		paint.setColor(Color.RED);
		if(activity.loadingView != null){//�߽�����
        	activity.loadingView.process += 30;
        }
		
        getHolder().addCallback(this);
        this.drawThread = new DrawThread(getHolder(), this);//��ʼ��ˢ֡�߳�
        this.gvt = new GameViewThread(this);//��ʼ����̨�����޸��߳�        
        this.battleField = new BattleField(this);
        
        hero = new Hero(this, 3, 3);//����Ӣ��
        hero.initAnimationSegment(heroAnimationSegments);//ΪӢ�۳�ʼ���������б�
        hero.setAnimationDirection(RIGHT%4);//��ʼ��Ӣ�۳����ң���̬��
        hero.startAnimation();//����Ӣ�۶���
        initMap();//��ʼ����ͼ
        initClass();//��ʼ�������õ�����
    	if(activity.isBackSound){
    		mMediaPlayer.start();
    	}
        if(activity.loadingView != null){//�߽�����
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
	
	//��ʼ�������õ�����
	public void initClass(){
		manPanelView = new ManPanelView(this);
		wuJiangView = new WuJiangView(this);
		useSkillView = new UseSkillView(this);
		cityManageView = new CityManageView(this);
		selectGeneral = new SelectGeneral(this);
		tianXiaView = new TianXiaView(this);
	}
	
	//����:��ʼ����ͼ
	public void initMap(){
		layerList = new LayerList(getResources());
		this.notInMatrix = layerList.getTotalNotIn();//�õ��ܲ���ͨ������
		this.meetableChecker = (MeetableLayer)layerList.layers.get(1);
		
		//�õ����гǳ�
		MyMeetableDrawable[][] myMeetableDrawable = (MyMeetableDrawable[][]) meetableChecker.getMapMatrix();
		Collections.shuffle(CITY_INFO);//����˳��
		int k = 0;//����
		for(int i=0; i<myMeetableDrawable.length; i++){
			for(int j=0; j<myMeetableDrawable[i].length; j++){
				if(myMeetableDrawable[i][j] == null){
					continue;
				}
				else if(myMeetableDrawable[i][j] instanceof CityDrawable){//����Ǹ��ǳ�
					CityDrawable cityDrawable  = (CityDrawable)myMeetableDrawable[i][j];
					cityDrawable.addCityInfo(CITY_INFO.get(k));//���óǳ���Ϣ 
					allCityDrawable.add(cityDrawable);//��ӵ����ǵĹ����б���
					k++;
					ArrayList<General> al = cityDrawable.getGuardGeneral();//�õ��ó��������е��佫
					for(General g : al){
						g.cityDrawable = cityDrawable;
					}
				}
			}
		}
		//��ʼ��ʱ��Ӣ��һ���ǳ�
		CityDrawable cd = allCityDrawable.get((int)(Math.random()*allCityDrawable.size()));
		this.hero.cityList.add(cd);	
		
		allCityDrawable.remove(cd);
		cd.setCountry(8);
		
		//��ʼ�����ɵĽ���
		freeGeneral = Hero.MY_GENERAL;
		
		//��ʼ��ʱ��Ӣ��һ���������
		if(freeGeneral.size()==15){
			General general = freeGeneral.get((int)(Math.random()*freeGeneral.size()));
			this.hero.setHeroGeneral(general);
			freeGeneral.remove(general);
		}
		
		//��ʼ��Ӣ�ۿ�ѧ�ļ���
		skillToLearn = new ArrayList<Skill>();
		skillToLearn.add(new SuiXinBuSkill(HUI_TOU_SHI_AN, "��ͷ�ǰ�", -1, 1, this.hero));
		skillToLearn.add(new SuiXinBuSkill(WU_ZHONG_SHENG_YOU, "��������", -1, 1, this.hero));
	}
	
	//��ʼ��ͼƬ��Դ�ķ���
	public static void initBitmap(Resources r){		
		dashboardBitmap = BitmapFactory.decodeResource(r, R.drawable.dashboard);//�Ǳ���ͼƬ
		Bitmap bigGameMenuOptions = BitmapFactory.decodeResource(r, R.drawable.game_menu_options);
		for(int i=0; i<smallGameMenuOptions.length; i++){//�˵�ͼƬ�����ʼ��
			smallGameMenuOptions[i] = Bitmap.createBitmap(bigGameMenuOptions, 0, (bigGameMenuOptions.getHeight()/smallGameMenuOptions.length)*i, bigGameMenuOptions.getWidth(), bigGameMenuOptions.getHeight()/smallGameMenuOptions.length);
		}
		bigGameMenuOptions = null;//�ͷŵ���ͼ
		
		Bitmap tempBmp = BitmapFactory.decodeResource(r, R.drawable.hero); //���ذ���Ӣ�۶����Ĵ�ͼ
		heroAnimationSegments = new Bitmap[HERO_ANIMATION_SEGMENTS][HERO_ANIMATION_FRAMES];
		for(int i=0;i<HERO_ANIMATION_SEGMENTS;i++){//�Դ�ͼ�����иת����Bitmap�Ķ�ά����
			for(int j=0;j<HERO_ANIMATION_FRAMES;j++){
				heroAnimationSegments[i][j] = Bitmap.createBitmap
												(
												tempBmp, 
												j*HERO_WIDTH, i*HERO_HEIGHT,
												HERO_WIDTH, HERO_HEIGHT
												);
			}
		}
		tempBmp = null;//�ͷŵ���ͼ
		Bitmap bmpTemp = BitmapFactory.decodeResource(r, R.drawable.dice);//��ȡ���Ӵ�ͼƬ
		bmpDice = new Bitmap[6];//��������
		for(int i=0;i<6;i++){
			bmpDice[i] = Bitmap.createBitmap(bmpTemp, DICE_SIZE*i, 0, DICE_SIZE, DICE_SIZE);
		}
		bmpTemp = null;//�ͷŵ���ͼ
		bmpBattleFiled = BitmapFactory.decodeResource(r, R.drawable.battle_field);
		//��ʼ������ܵ�ͼƬ
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
		bmpHero[0] = BitmapFactory.decodeResource(r, R.drawable.head_static);//Ӣ�۾�̬ͷ��
		bmpHero[1] = BitmapFactory.decodeResource(r, R.drawable.head_dynamic);//Ӣ�۶�̬ͷ��
		bmpBattleFiled = BitmapFactory.decodeResource(r, R.drawable.battle_field);
		dialogBack = BitmapFactory.decodeResource(r, R.drawable.dialog_back);
		Bitmap temp = BitmapFactory.decodeResource(r, R.drawable.buttons);
		dialogButton = Bitmap.createBitmap(temp, 0, 0, 60, 30);
	}
	
	//����:������Ļ
	public void onDraw(Canvas canvas){//�Լ�д�Ļ��Ʒ���
		//����������z��ģ��󻭵ĻḲ��ǰ�滭��
		if(status == 0 || status == 1 || status == 2){//��Ϸ������ʱ
			canvas.drawColor(Color.BLACK);
			int heroX=hero.x;//Ӣ�����ĵ��x����
			int heroY=hero.y;//Ӣ�����ĵ��y����
			int hCol = heroX/TILE_SIZE;//����Ӣ�����ĵ�λ���ĸ�����
			int hRow = heroY/TILE_SIZE;//����Ӣ�����ĵ�λ���ĸ�����
			int tempStartRow = this.startRow;//��¼���λ���ʱ�Ķ�λ��
			int tempStartCol = this.startCol;//��¼���λ���ʱ�Ķ�λ��
			int tempOffsetX = this.offsetX;//��¼���λ���ʱ��xƫ��
			int tempOffsetY = this.offsetY;//��¼���λ���ʱ��yƫ��
			/*
			 * ���Ƶײ�
			 * ����Ϊ��ʵ���޼�������������Ļ�������ĵ�ͼʱ��������һȦ��������ʱ��λ����Ȼ�Ǳ�Ե�ˣ���continueһ��
			 */
			for(int i=-1; i<=GAME_VIEW_SCREEN_ROWS; i++){     
				if(tempStartRow+i < 0 || tempStartRow+i>MAP_ROWS){//����໭����һ�в����ڣ��ͼ���
					continue;
				}
				for(int j=-1; j<=GAME_VIEW_SCREEN_COLS; j++){
					if(tempStartCol+j <0 || tempStartCol+j>MAP_COLS){//����໭����һ�в����ڣ��ͼ���
						continue;
					}
					Layer l = (Layer)layerList.layers.get(0);//��õײ��ͼ��
					MyDrawable[][] mapMatrix=l.getMapMatrix();
					if(mapMatrix[i+tempStartRow][j+tempStartCol] != null){
						mapMatrix[i+tempStartRow][j+tempStartCol].drawSelf(canvas,i,j,tempOffsetX,tempOffsetY);
					}		
				}
			} 

			//�����ϲ�
			for(int i=-1; i<=GAME_VIEW_SCREEN_ROWS; i++){
				if(tempStartRow+i < 0 || tempStartRow+i>MAP_ROWS){//����໭����һ�в����ڣ��ͼ���
					continue;
				}
				for(int j=-1; j<=GAME_VIEW_SCREEN_COLS; j++){
					if(tempStartCol+j <0 || tempStartCol+j>MAP_COLS){//����໭����һ�в����ڣ��ͼ���
						continue;
					}
					Layer l = (Layer)layerList.layers.get(1);//����ϲ��ͼ��
					MyDrawable[][] mapMatrix=l.getMapMatrix();
					if(mapMatrix[i+tempStartRow][j+tempStartCol] != null){
						mapMatrix[i+tempStartRow][j+tempStartCol].drawSelf(canvas,i,j,tempOffsetX,tempOffsetY);
					}		
					if(hRow-tempStartRow == i && hCol-tempStartCol == j){//Ӣ��������
						hero.drawSelf(canvas,tempStartRow,tempStartCol,tempOffsetX,tempOffsetY);
					}
				}
			}
			
			//�����Ǳ���
			canvas.drawBitmap(dashboardBitmap, 0, ConstantUtil.SCREEN_HEIGHT-dashboardBitmap.getHeight(), paint);			
			drawDice(canvas);//��������
			drawHeroData(canvas);//�����Ǳ����ϵ�����
			
			canvas.drawBitmap(bmpHero[status==1?1:0], 0, HERO_FACE_START_Y, null);//��Ӣ�۵�ͷ��
			if(showMiniMap){//����Ƿ���Ҫ��ʾС��ͼ
				drawMiniMap(canvas,hCol,hRow);
			}
			
			if(status == 2){//����Ҫ�������˵�ʱ
				if(darkBitmap != null){
					canvas.drawBitmap(darkBitmap, 0, 0, paint);
				}
				for(int i=0; i<smallGameMenuOptions.length; i++){//���Ʋ˵�
					canvas.drawBitmap(smallGameMenuOptions[i], ConstantUtil.GAME_VIEW_MEMU_LEFT_SPACE, ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+(smallGameMenuOptions[i].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)*i, paint);
				}
			}
			if(currentDrawable != null){//�ж��Ƿ���Ҫ���ƿ���ʵ��ĶԻ���
				currentDrawable.drawDialog(canvas, hero);
			}
			if(currentGameAlert != null && currentDrawable == null){//�ж��Ƿ���Ҫ������Ϸ��ʾ
				currentGameAlert.drawDialog(canvas);		//���ƶԻ���
				setOnTouchListener(currentGameAlert);		//����GameView�ļ�����
			}
		}
		else if(status == 4){
			battleField.onDraw(canvas);
		}		
		else if(status == 100){//�������Խ���
			manPanelView.onDraw(canvas);
		}
		else if(status == 99){//�佫�鱨 
			wuJiangView.onDraw(canvas);
		}
		else if(status == 98){//ʹ�ü�ı
			useSkillView.onDraw(canvas);
		}
		else if(status == 97){//�ǳع���
			cityManageView.onDraw(canvas);
		}
		else if(status == 3){//ѡ�г�������
			selectGeneral.onDraw(canvas);
		}
		else if(status == 96){//���¾���
			tianXiaView.onDraw(canvas);
		}
	}

	public void drawMiniMap(Canvas canvas,int col,int row){
		Paint paint = new Paint();		
		int rows = notInMatrix.length;		//����
		int cols = notInMatrix[0].length;	//����
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
				else{//���ϰ���
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
		//��Ӣ��Ҳ����ȥ
		paint.setARGB(255, 255, 0, 0);
		canvas.drawRect(
				tempStartX+col*MINI_MAP_SPAN, 
				tempStartY+row*MINI_MAP_SPAN, 
				tempStartX+col*MINI_MAP_SPAN+MINI_MAP_SPAN, 
				tempStartY+row*MINI_MAP_SPAN+MINI_MAP_SPAN, 
				paint
				);
	}
	
	
	//������������
	public void drawDice(Canvas canvas){
		for(int i=0;i<diceCount;i++){//�������������ӵĸ���������
			canvas.drawBitmap(bmpDice[diceValue[i]], DICE_START_X+i*DICE_SPAN, DICE_START_Y, null);
		}
	}
	
	//��������Ҫ����Ϸ����
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
	//����:���һ�����ݵ���λ�������ʾ����
	public int[] getDigitSequence(int data){
		int [] result = new int[6];
		String sData = data+"";		
		int zeroSpan = 6-sData.length();//���ǰ�滭������
		int i=0;
		for(;i<zeroSpan;i++){//����
			result[i] = 0;
		}
		for(;i<6;i++){//����Ч����
			result[i] = sData.charAt(i-zeroSpan)-'0';
		}
		return result;
	}
	
	//����������������������л���6������
	public void drawDigits(Canvas canvas,int [] sequence,int x,int y){
		for(int i=0;i<6;i++){
			canvas.drawBitmap(bmpDigit[sequence[i]], x+i*DIGIT_SPAN, y, null);
		}
	}
	
	public void setCurrentDrawable(MyMeetableDrawable currentDrawable) {
		this.currentDrawable = currentDrawable;
	}
	
	//����������GameView��״̬
	public void setStatus(int status){
		this.status = status;
	}
	
	//��д�ļ�����ʵ�ַ���
	public boolean onTouch(View view, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//֮��׽��Ļ�����µ��¼�
			int x = (int)event.getX();//�õ�����
			int y = (int)event.getY();
			//��Ϸ��������
			if(this.status == 0){
				if(x>282 && x<310  && y>400 && y<470){//������˵�
					darkBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dark);
					this.status = 2;
				}
				else if(x>62 && x<80 && y>408 && y<426){//������������԰�ť
					this.manPanelView.initData();
					this.status = 100;
				}
				else if(x>62 && x<80 && y>427 && y<445){//�佫�鱨
					this.wuJiangView.initData();
					this.status = 99;
				}
				else if(x>42 && x<61 && y>427 && y<445){//�ǳع���
					this.cityManageView.initData();
					this.status = 97;
				}
				else if(x>22 && x<41 && y>427 && y<445){//���¾���
					this.tianXiaView.initData();
					this.status = 96;
				}
				else if(x>2 && x<21 && y>427 && y<445){//ʹ�ü�ı
					this.useSkillView.initData();
					this.status = 98;
				}
				//�ж��Ƿ���������ͷ��
				else if(x >0 && x<HERO_FACE_WIDTH && y>HERO_FACE_START_Y && y< HERO_FACE_START_Y+HERO_FACE_HEIGHT){
					if(this.status == 0){//�Ƿ��Ǵ���״̬
						this.gvt.setChanging(false);//��ͣ�任���ӵ��߳�
						this.diceValue = hero.throwDice(this.diceCount);
						currentSteps = 0;//��¼Ҫ�߼���
						for(int i=0;i<diceCount;i++){
							currentSteps = currentSteps+diceValue[i]+1;
						}
						hero.startToGo(currentSteps);
					}
				}
				else if(x > MAP_BUTTON_START_X && x< MAP_BUTTON_START_X+MAP_BUTTON_SIZE 
						&& y> MAP_BUTTON_START_Y && y<MAP_BUTTON_START_Y+MAP_BUTTON_SIZE){//��������ǵ�ͼ����
					showMiniMap = !showMiniMap;
					miniMapStartY = -160;	//��λ
				}
				else if(x > DICE_START_X+DICE_SPAN && x<DICE_START_X+DICE_SPAN+DICE_SIZE
						&& y>DICE_START_Y && y<DICE_START_Y+DICE_SIZE){//�����˵ڶ�������
					if(this.diceCount == 1){//��ǰ���ֻ��1�����ӻ�Ծ���ͱ��2��
						this.diceCount++;
					}
					else if(this.diceCount == 2){//��Ǯ�����2�����ӻ�Ծ���ͱ��1��
						this.diceCount --;
					}
				}
				else if(x > DICE_START_X+DICE_SPAN*2 && x<DICE_START_X+DICE_SPAN*2+DICE_SIZE
						&& y>DICE_START_Y && y<DICE_START_Y+DICE_SIZE){//�����˵���������
					if(this.diceCount == 2){//��ǰ���ֻ��2�����ӻ�Ծ���ͱ��3��
						this.diceCount ++;
					}
					else if(this.diceCount == 3){//��ǰ�����3�����ӻ�Ծ���ͱ��2��
						this.diceCount --;
					}
				}
			}			
			else if(this.status == 2){//���˵�����ʱ
				if(x>ConstantUtil.GAME_VIEW_MEMU_LEFT_SPACE && x<ConstantUtil.GAME_VIEW_MEMU_LEFT_SPACE+smallGameMenuOptions[0].getWidth()){//x����Ҫ��
					if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE && y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+smallGameMenuOptions[0].getHeight()){
						//�������������Ϸ
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
						//���������Ϸ
						this.status = 0;
						SerializableGame.saveGameStatus(this);
					}
					else if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+2*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE) 
							&& y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+2*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)+smallGameMenuOptions[1].getHeight()){
						//�����Ч����
						this.status = 0;
						activity.myHandler.sendEmptyMessage(10);
					}
					else if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+3*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE) 
							&& y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+3*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)+smallGameMenuOptions[1].getHeight()){
						//�������
						this.status = 0;
						this.activity.myHandler.sendEmptyMessage(8);
					}
					else if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+4*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE) 
							&& y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+4*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)+smallGameMenuOptions[1].getHeight()){
						//����˳���Ϸ
						this.mMediaPlayer.stop();
						System.exit(0);
					}
					else if(y>ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+5*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE) 
							&& y<ConstantUtil.GAME_VIEW_MEMU_UP_SPACE+5*(smallGameMenuOptions[0].getHeight()+ConstantUtil.GAME_VIEW_MEMU_WORD_SPACE)+smallGameMenuOptions[1].getHeight()){
						//���������Ϸ
						if(darkBitmap != null){
							darkBitmap = null;
						}
						this.status = 0;
					}
				}
			}
			else if(status == 100){//�������Խ������ 
				manPanelView.onTouchEvent(event);
			}
			else if(status == 99){//�佫�鱨����
				wuJiangView.onTouchEvent(event);
			}
			else if(status == 98){//ʹ�ü�ı����
				useSkillView.onTouchEvent(event);
			}
			else if(status == 97){//�ǳع������
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
		this.mMediaPlayer.stop();//ֹͣ��������
		this.drawThread.isViewOn = false;
		this.drawThread.flag = false;
		hero.hbdt.flag = false;//�غ�̨�߳�
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
        if(! drawThread.isAlive()){//�����̨�ػ��߳�û����,��������
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
        this.gvt.isChanging = true;//���û����ӱ�־λΪ��
        if(!gvt.isAlive()){//�����̨�߳�û����,��������        	
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
	
	class DrawThread extends Thread{//ˢ֡�߳�

		private int sleepSpan = ConstantUtil.GAME_VIEW_SLEEP_SPAN;//˯�ߵĺ����� 
		private SurfaceHolder surfaceHolder;
		private GameView gameView;
		private boolean isViewOn = false;
		private boolean flag = true;
        public DrawThread(SurfaceHolder surfaceHolder, GameView gameView) {//������
        	super.setName("==GameView.DrawThread");
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }
        
        public void setFlag(boolean flag) {//����ѭ�����λ
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
	                	// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
	                    c = this.surfaceHolder.lockCanvas(null);
	                    synchronized (this.surfaceHolder) {
	                    	gameView.onDraw(c);
	                    }
	                } finally {
	                    if (c != null) {
	                    	//������Ļ��ʾ����
	                        this.surfaceHolder.unlockCanvasAndPost(c);
	                    }
	                }
	                try{
	                	Thread.sleep(sleepSpan);//˯��ָ��������
	                }
	                catch(Exception e){
	                	e.printStackTrace();
	                }
	            }
	            try{
	            	Thread.sleep(1500);//˯��ָ��������
	            }
	            catch(Exception e){
	            	e.printStackTrace();
	            }
			}
		}
	}
}