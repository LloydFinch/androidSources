package com.bn.game.chap11.ex8;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
public class MyGLActivity extends Activity {
	MyGLView myGLView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);//设置主界面
        myGLView=new MyGLView(this);//创建myGLView
        LinearLayout ll=(LinearLayout) this.findViewById(R.id.mainLinear);//得到配制文件中LinearLayout的引用
        ll.addView(myGLView);//在LinearLayout中加入myGLView
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