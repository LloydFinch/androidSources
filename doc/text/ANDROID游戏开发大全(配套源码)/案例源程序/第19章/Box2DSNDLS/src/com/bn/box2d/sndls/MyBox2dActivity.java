package com.bn.box2d.sndls;         
import java.util.HashMap;
import android.app.Activity;    
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;   
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;   
import android.view.WindowManager;  
import static com.bn.box2d.sndls.Constant.*;

enum WhichView{GAME_VIEW,MAIN_MENU_VIEW};
public class MyBox2dActivity extends Activity 
{   
	WhichView curr;//当前的界面
	MainMenuView mmv;//主菜单界面
	GameView gv;//游戏界面
	//获取SharedPreferences
	SharedPreferences sp;
	//向SharedPreferences中写回数据
    SharedPreferences.Editor editor;
    //声音类
    SoundUtil soundutil;
	HashMap<Integer, Integer> soundPoolMap;
	Handler hd=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case 0:
					gotoGameView();					
				break;
				case 1:
					gotoMainMenuView();
				break;
			}
		}
	};
    public void onCreate(Bundle savedInstanceState) 
    {   
        super.onCreate(savedInstanceState);   
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,   
        WindowManager.LayoutParams. FLAG_FULLSCREEN); 
        //设置为横屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//获取屏幕尺寸
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        if(dm.widthPixels>dm.heightPixels)
        {
        	 SCREEN_WIDTH=dm.widthPixels;
             SCREEN_HEIGHT=dm.heightPixels;
        }
        else
        {
        	SCREEN_WIDTH=dm.heightPixels;
            SCREEN_HEIGHT=dm.widthPixels;
        }
        //初始化声音资源
        soundutil=new SoundUtil(this);
        soundutil.initSounds();   
        
        Constant.changeRatio();
        Constant.loadPic(this.getResources());
           
        //加载图片资源
        Constant.initBitmap(getResources());
        //得到图片的位置
        Constant.getLocaltionAndWH();  
        
        //从记录中加载得分
        sp=this.getSharedPreferences("actm", Context.MODE_PRIVATE);
    	String lastStr=sp.getString("score", null);
    	if(lastStr!=null)
    	{
    		String[] sa=lastStr.split("\\|");
    		HH_SCORE=new int[sa.length];
    		HH_SCORE[0]=Integer.parseInt(sa[0]);
    		HH_SCORE[1]=Integer.parseInt(sa[1]);
    		HH_SCORE[2]=Integer.parseInt(sa[2]);
    	}    	
    	
        gotoMainMenuView();
    }
    //进入开始界面
    public void gotoMainMenuView()
    {
		mmv=new MainMenuView(this);   
    	setContentView(mmv);
    	curr=WhichView.MAIN_MENU_VIEW;
    }
    //进入游戏界面
    public void gotoGameView()
    {
    	//停止线程
    	DRAW_THREAD_FLAG=true;
    	START_SCORE=false;
    	xOffset=0;
    	yOffset=0;
    	SCORE=0;
    	gv=new GameView(this);   
        setContentView(gv);
        curr=WhichView.GAME_VIEW;  
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent e)
    {    	
    	if(keyCode==4)
    	{	
    		if(curr==WhichView.MAIN_MENU_VIEW)
    		{
    			sp=this.getSharedPreferences("actm", Context.MODE_PRIVATE);
    			SharedPreferences.Editor editor=sp.edit();
    			String str=HH_SCORE[0]+"|"+HH_SCORE[1]+"|"+HH_SCORE[2];
        		editor.putString("score", str);
        		editor.commit();
    			System.exit(0);
    		}
    		if(curr==WhichView.GAME_VIEW)
    		{
    			DRAW_THREAD_FLAG=false; 
    			gotoMainMenuView();
    		}
    	}    	
    	return true;
    }
}