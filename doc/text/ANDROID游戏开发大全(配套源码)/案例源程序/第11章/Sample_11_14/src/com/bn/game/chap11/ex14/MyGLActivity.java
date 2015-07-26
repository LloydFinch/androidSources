package com.bn.game.chap11.ex14;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
public class MyGLActivity extends Activity {
	MyGLView myGLView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        //ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//����Ϊ����
        myGLView=new MyGLView(this);//����myGLView
        this.setContentView(myGLView);//��LinearLayout�м���myGLView
    }
    @Override
    protected void onResume() {
        super.onResume();
        myGLView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        myGLView.onPause();
    }
}