package com.bn.game.chap11.ex8;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
public class MyGLActivity extends Activity {
	MyGLView myGLView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);//����������
        myGLView=new MyGLView(this);//����myGLView
        LinearLayout ll=(LinearLayout) this.findViewById(R.id.mainLinear);//�õ������ļ���LinearLayout������
        ll.addView(myGLView);//��LinearLayout�м���myGLView
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