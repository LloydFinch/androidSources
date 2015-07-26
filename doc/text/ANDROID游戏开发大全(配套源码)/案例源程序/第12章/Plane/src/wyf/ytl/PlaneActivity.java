package wyf.ytl;
import android.app.Activity;//���������
import android.os.Bundle;//���������
import android.os.Handler;//���������
import android.os.Looper;//���������
import android.os.Message;//���������
import android.view.KeyEvent;//���������
import android.view.Window;//���������
import android.view.WindowManager;//���������
public class PlaneActivity extends Activity{
	int action = 0;//���̵�״̬,�����Ʊ�ʾ �������ұ�ʾ��������
	GameView gameView;//GameView������
	WelcomeView welcomeView;//WelcomeView������
	FailView failView;//��Ϸʧ�ܽ��������
	HelpView helpView;//HelpView������
	WinView winView;//��ӭ���������
	ProcessView processView;//���������������
	boolean isSound = true;//�Ƿ񲥷�����
	Handler myHandler = new Handler(){//��������UI�߳��еĿؼ�
        public void handleMessage(Message msg) {
        	if(msg.what == 1){//��Ϸʧ�ܣ���ҷɻ�׹��
        		if(gameView != null){
        			gameView.keyThread.setFlag(false);//ֹͣ���̼���
        			gameView.gameThread.setFlag(false);
        			gameView.moveThread.setFlag(false);
        			gameView = null;
        		}
        		initFailView();//�л���FialView
        	}
        	else if(msg.what == 2){//�л���GameView
        		if(welcomeView != null){
        			welcomeView = null;//�ͷŻ�ӭ����
        		}
        		if(processView != null){
        			processView = null;//�ͷż��ؽ���
        		}
            	processView = new ProcessView(PlaneActivity.this,2);//��ʼ�����������л���������View
            	PlaneActivity.this.setContentView(processView);
            	new Thread(){//�߳�
            		public void run(){
            			Looper.prepare();
            			gameView = new GameView(PlaneActivity.this);//��ʼ��GameView
            			Looper.loop();
            		}
            	}.start();//�����߳�
        	}
        	else if(msg.what == 3){//WelcomeView��������Ϣ���л���HelpView
        		initHelpView();//�л���HelpView����
        	}
        	else if(msg.what == 4){
        		if(helpView != null){
        			helpView = null;
        		}
        		toWelcomeView();//�л���WelcomeView���� 
        	}
        	else if(msg.what == 5){
        		if(gameView != null){
        			gameView.gameThread.setFlag(false);
        			gameView.keyThread.setFlag(false);
        			gameView.moveThread.setFlag(false);
        			gameView.explodeThread.setFlag(false);
        			gameView = null;
        		}
        		initWinView();//�л���WinView���� 
        	}
        	else if(msg.what == 6){
        		toGameView();//ȥ��Ϸ����
        	}
        	else if(msg.what == 7){
        		if(welcomeView != null){//�ͷŻ�ӭ����
        			welcomeView = null;
        		}
        		if(processView != null){//�ͷż��ؽ���
        			processView = null;
        		}
        		processView = new ProcessView(PlaneActivity.this,1);//��ʼ�����������л���������View
        		PlaneActivity.this.setContentView(processView);
            	new Thread(){//�߳�
            		public void run(){//��д��run����
            			Looper.prepare();
            			welcomeView = new WelcomeView(PlaneActivity.this);//��ʼ��WelcomeView
            			Looper.loop();
            		}
            	}.start();//�����߳�
        	}
        }
	};
    public void onCreate(Bundle savedInstanceState) {//�����Ǳ�����
        super.onCreate(savedInstanceState);
		//ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	processView = new ProcessView(this,1);//��ʼ�����������л���������View
    	this.setContentView(processView);//���ü��ؽ���
    	new Thread(){//�߳�
    		public void run(){
    			Looper.prepare();
    			welcomeView = new WelcomeView(PlaneActivity.this);//��ʼ��WelcomeView
    		}
    	}.start();//�����߳�
    }
    public void toWelcomeView(){//�л�����ӭ����     	
    	this.setContentView(welcomeView);
    }
    public void toGameView(){//��ʼ��Ϸ����
    	this.setContentView(gameView);
    }
    public void initHelpView(){//��ʼ��������
    	helpView = new HelpView(this);
    	this.setContentView(helpView);
    }
    public void initFailView(){//��ʼ��Ϸʧ�ܽ���
    	failView = new FailView(this);
    	this.setContentView(failView);
    }
    public void initWinView(){//��ʼʤ������
    	winView = new WinView(this);
    	this.setContentView(winView);
    }
    public boolean onKeyUp(int keyCode, KeyEvent event) {//����̧��
    	if(keyCode == 19){//��
    		action = action & 0x1F;
    	}
    	if(keyCode == 20){//��
    		action = action & 0x2F;
    	}    	
    	if(keyCode == 21){//��
    		action = action & 0x37;
    	}    	
    	if(keyCode == 22){//��
    		action = action & 0x3B;
    	}
    	if(keyCode == KeyEvent.KEYCODE_A){//A
    		action = action & 0x3D;
    	}
    	if(keyCode == 100){//B ���ű���
    		action = action & 0x3E;
    	}
		return false;
	}
    public boolean onKeyDown(int keyCode, KeyEvent event){//���̰��¼���
    	if(keyCode == 19){//��
    		action = action | 0x20;
    	}
    	if(keyCode == 20){//��
    		action = action | 0x10;
    	}
    	if(keyCode == 21){//��
    		action = action | 0x08;
    	}
    	if(keyCode == 22){//��
    		action = action | 0x04;
    	}
    	if(keyCode == KeyEvent.KEYCODE_A){//A 
    		action = action | 0x02;
    	}
    	if(keyCode == 100){//B���ű���
    		action = action | 0x01;
    	}
		return false;
    }
}