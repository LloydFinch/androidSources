package com.bn.game.chap11.ex4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;

public class MyGLActivity extends Activity {
	MyGLView myGLView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);//����������
        myGLView=new MyGLView(this);//����myGLView
        LinearLayout ll=(LinearLayout)findViewById(R.id.main_liner); 
        ll.addView(myGLView);
        //ΪRadioButton��Ӽ����������ص�ҵ�����
        RadioButton rb=(RadioButton)findViewById(R.id.position);
        rb.setOnCheckedChangeListener(
            new OnCheckedChangeListener()
            {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				myGLView.setPosition(isChecked);
     			}        	   
            }         		
        ); 
        //��ͨ�������������Ĵ������
        SeekBar sb=(SeekBar)this.findViewById(R.id.SeekBar01);
        sb.setOnSeekBarChangeListener(
            new SeekBar.OnSeekBarChangeListener()
            {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					myGLView.setLightOffset((progress-seekBar.getMax()/2.0f)/(seekBar.getMax()/2.0f)*4);
				}
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {	}
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) { }            	
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