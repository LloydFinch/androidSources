package com.bn.game.chap11.ex3;

import com.bn.game.chap11.ex3.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyGLActivity extends Activity {
	MyGLView myGLView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);//设置主界面
        myGLView=new MyGLView(this);//创建myGLView
        LinearLayout ll=(LinearLayout) this.findViewById(R.id.mainLinear);//得到配制文件中LinearLayout的引用
        ll.addView(myGLView);//在LinearLayout中加入myGLView
        //为透视投影按钮添加监听
        ToggleButton btn1=(ToggleButton) this.findViewById(R.id.ToggleButton01);
        btn1.setOnCheckedChangeListener(
                new OnCheckedChangeListener() {
    	 			@Override
    	 			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    	 				myGLView.setPerspectiveFlag(!myGLView.isPerspectiveFlag());
    	 			}        	   
                }        
            );
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