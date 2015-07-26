package com.bn.tag;

import android.app.Dialog;
import android.os.Bundle;

public class MyDialogue extends Dialog 
{
	public MyDialogue(TafangGameActivity tafangGameActivity) {
        super(tafangGameActivity,R.style.FullHeightDialog);
    }
	
	@Override
	public void onCreate (Bundle savedInstanceState) 
	{
		this.setContentView(R.layout.dialog_name_input);
	}
	
	@Override
	public String toString()
	{
		return "MyDialog";
	}
}
