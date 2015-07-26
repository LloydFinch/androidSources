package com.bn.game.chap11.ex2;

import com.bn.game.chap11.ex2.R;

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
        setContentView(R.layout.main);//����������
        myGLView=new MyGLView(this);//����myGLView
        LinearLayout ll=(LinearLayout) this.findViewById(R.id.mainLinear);//�õ������ļ���LinearLayout������
        ll.addView(myGLView);//��LinearLayout�м���myGLView
        //Ϊ������ð�ť��Ӽ���
        ToggleButton btn1=(ToggleButton) this.findViewById(R.id.ToggleButton01);
        btn1.setOnCheckedChangeListener(
                new OnCheckedChangeListener() {
    	 			@Override
    	 			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    	 				myGLView.setCullFaceFlag(!myGLView.isCullFaceFlag());
    	 			}        	   
                }        
            );
        //Ϊƽ����ɫ��ť��Ӽ���
        ToggleButton btn2=(ToggleButton) this.findViewById(R.id.ToggleButton02);
        btn2.setOnCheckedChangeListener(
                new OnCheckedChangeListener() {
    	 			@Override
    	 			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    	 				myGLView.setSmoothFlag(!myGLView.isSmoothFlag());
    	 			}        	   
                }        
            );
        //Ϊ���Ʒ�ʽ��ť��Ӽ���
        ToggleButton btn3=(ToggleButton) this.findViewById(R.id.ToggleButton03);
        btn3.setOnCheckedChangeListener(
                new OnCheckedChangeListener() {
    	 			@Override
    	 			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
    	 				myGLView.setCwFlag(!myGLView.isCwFlag());
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