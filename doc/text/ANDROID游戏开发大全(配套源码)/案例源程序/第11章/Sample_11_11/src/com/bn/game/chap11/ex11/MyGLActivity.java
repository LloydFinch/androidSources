package com.bn.game.chap11.ex11;

import android.app.Activity;
import android.os.Bundle;

public class MyGLActivity extends Activity {
	MyGLView myGLView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);//����������
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