package wyf.ytl;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class TreeDrawable extends MyMeetableDrawable implements Serializable{
	
	public TreeDrawable(){}

	public TreeDrawable(Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,int width,int height,int col,int row,
			int refCol,int refRow,int [][] noThrough,
			int [][] meetableMatrix) {
		super(bmpSelf, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, bmpDialogBack, bmpDialogButton);
	}

	@Override
	public void drawDialog(Canvas canvas, Hero hero) {
		this.tempHero = hero;
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		tempHero.father.setOnTouchListener(tempHero.father);
		tempHero.father.setCurrentDrawable(null);//�ÿռ�¼���õı���
		tempHero.father.setStatus(0);//��������GameViewΪ����״̬
		tempHero.father.gvt.setChanging(true);//����ת����
		return true;
	}
}
