package wyf.ytl;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
/** 
 * ����ΪActivity�� 
 */
public class HDZGActivity extends Activity {
	LoadingView loadingView;//���������������
	MenuView menuView;//��ʼ��Ϸ�����˵�����   
	GameView gameView;//��Ϸ������
	HelpView helpView;//��������
	SoundView soundView;//�տ�ʼʱѡ���Ƿ��������Ľ���
	SoundManageView soundManageView;//�������ý���
	ScreenRollView screenRoll;//����������
	AboutView aboutView;//���ڽ���
	
	boolean isBackSound = true;//�Ƿ��б�������
	boolean isStartSound = true;//��������
	boolean isBattleSound = true;//ս������
	boolean isEnvironmentSound = true;//������
	
	View currentView = null;//��ǰ��View
	
	Handler myHandler = new Handler(){//��������UI�߳��еĿؼ�
        public void handleMessage(Message msg) {
        	switch(msg.what){
        		case 1://�յ�LoadingView����Ϣ���е�MenuView
        			if(loadingView != null){
        				loadingView = null;//�ͷ�LoadingView
        			}
        			
        			toMenuView();//�л�����ʼ��Ϸʱ�����˵�
        			break;
        		case 2://�յ�MenuView����Ϣ���л���ScreenRoll������
        			if(loadingView != null){
        				loadingView = null;
        			}
        			if(menuView != null){//�ͷ�
        				menuView = null;
        			}       			
        			screenRoll = new ScreenRollView(HDZGActivity.this);
        			setContentView(screenRoll);
        			break;
        		case 3://�յ�LoadingView����Ϣ���е�GameView
        			if(loadingView != null){
        				loadingView = null;//�ͷ�LoadingView
        			}
        			toGameView();//�л�����Ϸ��������
        			break; 
        		case 4://�յ�MenuView�������Ϣ���л���HelpView
        	    	helpView = new HelpView(HDZGActivity.this,5);
        	    	HDZGActivity.this.currentView = helpView;//���õ�ǰ��ViewΪHelpView
        	    	HDZGActivity.this.setContentView(helpView);
        			break;
        		case 5:
        			menuView = new MenuView(HDZGActivity.this);
        			toMenuView();
        			break;
        		case 6:
        			toGameView();//�л�����Ϸ��������
        			break;
        		case 7://��ʼʱ������ѡ����洫������Ϣ
        	    	toLoadingView(1);
        	    	new Thread(){//��̨����������Ҫ���ص�View
        	    		public void run(){
        	    			Looper.prepare();
        	    			menuView = new MenuView(HDZGActivity.this);//��ʼ��MenuView
        	    		}
        	    	}.start();
        			break;
        		case 8:
        	    	helpView = new HelpView(HDZGActivity.this,6);
        	    	HDZGActivity.this.currentView = helpView;//���õ�ǰ��ViewΪHelpView
        	    	HDZGActivity.this.setContentView(helpView);
        			break;
        		case 9:
        			soundManageView = new SoundManageView(HDZGActivity.this,5);
        	    	HDZGActivity.this.currentView = soundManageView; 
        	    	HDZGActivity.this.setContentView(soundManageView);
        			break;
        		case 10:
        			soundManageView = new SoundManageView(HDZGActivity.this,6);
        	    	HDZGActivity.this.currentView = soundManageView;
        	    	HDZGActivity.this.setContentView(soundManageView);
        			break;
        		case 11:
        			if(loadingView != null){
        				loadingView = null;
        			}
        			if(screenRoll != null){//�ͷ�
        				screenRoll = null;
        			}  
        			toLoadingView(3);
                	new Thread(){ 
                		public void run(){
                			Looper.prepare();
                			gameView = new GameView(HDZGActivity.this);//��ʼ��GameView
                		}
                	}.start();
                	break;
        		case 12://��AboutView��������Ϣ����Ҫ��MenuView
        			menuView = new MenuView(HDZGActivity.this);        		
        			HDZGActivity.this.setContentView(menuView);
        			HDZGActivity.this.currentView = menuView;
        			break;
        		case 13://��MenuView��������Ϣ��Ҫ��AboutView
        			if(aboutView == null){
        				aboutView = new AboutView(HDZGActivity.this);
        			}
        			HDZGActivity.this.setContentView(aboutView);
        			HDZGActivity.this.currentView = aboutView;
        			break;
        		case 14://��GameView��������Ϣ��Ҫ��MenuView
        			menuView = new MenuView(HDZGActivity.this);
        			HDZGActivity.this.setContentView(menuView);
        			HDZGActivity.this.currentView = menuView;
        			break;                	
        		case 99:
        			if(loadingView != null){
        				loadingView = null;
        			}
        			currentView = null;
        			if(SerializableGame.check(HDZGActivity.this)){
	        			toLoadingView(101);
						new Thread(){
							public void run(){
								try{
									Thread.sleep(2000);
								}
								catch(Exception e){
									e.printStackTrace();
								}
								Looper.prepare();
								if(gameView==null)
								{
									gameView = new GameView(HDZGActivity.this);
								}
								SerializableGame.loadingGameStatus(gameView);
							}
						}.start();
        			}
        			break;
        		case 100:
        			if(gameView != null){
        				HDZGActivity.this.setContentView(gameView);
        			}
        			break;
        			
        	}
        }
	};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		//ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		soundView = new SoundView(this);
		this.setContentView(soundView);//���е��򵥵��������ؽ���
		
		GameData.resources=this.getResources();
		GameData.initMapImage();
		GameData.initMapData();
		
		GameData2.resources=this.getResources();
		GameData2.initBitmap();
		GameData2.initMapData();
		
		GameView.initBitmap(this.getResources());
		
		BattleField.initBitmap(this.getResources());
		
		ManPanelView.initBitmap(this.getResources());
		
		WuJiangView.initBitmap(this.getResources());
		
		UseSkillView.initBitmap(this.getResources());
		
		CityManageView.initBitmap(this.getResources());
		
		SelectGeneral.initBitmap(this.getResources());
		
		TianXiaView.initBitmap(this.getResources());
    }
    
    public void toLoadingView(int toViewID){
		loadingView = new LoadingView(this,toViewID);//��ʼ��������
		this.currentView = loadingView;
    	this.setContentView(loadingView);//�л���������View
    }

    //�л�����ʼ��Ϸ�����˵�
    public void toMenuView(){
    	if(this.menuView != null){//��menuView��Ϊ��ʱ����
    		this.currentView = menuView;
    		this.setContentView(menuView);
    	}
    	else{//��Ϊ��ʱ��ӡ���˳�
    		System.exit(0);
    	}
    }
    
    //�л�����Ϸ������
    public void toGameView(){ 
    	if(this.gameView != null){//��gameView��Ϊ��ʱ
    		this.currentView = gameView;
    		this.setContentView(gameView);
    	}
    	else{//��Ϊ��ʱ��ӡ���˳�
    		System.exit(0);
    	}
    }

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(currentView != null){
			currentView.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}
}