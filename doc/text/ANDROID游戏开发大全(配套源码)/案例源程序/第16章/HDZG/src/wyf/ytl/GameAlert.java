package wyf.ytl;
/*
 * 该类为抽象类,主要定义的动作是游戏中随时会弹出的提示信息,如大军来袭\粮草危机\科研成功等
 */
import static wyf.ytl.ConstantUtil.*;	//引入相关类
import android.graphics.Bitmap;			//引入相关类
import android.graphics.Canvas;			//引入相关类
import android.graphics.Paint;			//引入相关类
import android.graphics.Typeface;		//引入相关类
import android.view.View;				//引入相关类

public abstract class GameAlert implements View.OnTouchListener{
	Bitmap bmpDialogBack;//对话框背景
	Bitmap bmpDialogButton;//对话框按钮
	GameView gameView;
	public GameAlert(GameView gameView,Bitmap bmpDialogBack,Bitmap bmpDialogButton){
		this.gameView = gameView;
		this.bmpDialogBack = bmpDialogBack;
		this.bmpDialogButton = bmpDialogButton;
	}
	//抽象方法:用于获得监听后绘制对话框
	public abstract void drawDialog(Canvas canvas);
	//方法:绘制给定的字符串到对话框上
	public void drawString(Canvas canvas,String string){
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
		paint.setTextSize(DIALOG_WORD_SIZE);//设置文字大小
		int lines = string.length()/DIALOG_WORD_EACH_LINE+(string.length()%DIALOG_WORD_EACH_LINE==0?0:1);//求出需要画几行文字
		for(int i=0;i<lines;i++){
			String str="";
			if(i == lines-1){//如果是最后一行那个不太整的汉字
				str = string.substring(i*DIALOG_WORD_EACH_LINE);
			}else{
				str = string.substring(i*DIALOG_WORD_EACH_LINE, (i+1)*DIALOG_WORD_EACH_LINE);
			}
			canvas.drawText(str, DIALOG_WORD_START_X, DIALOG_WORD_START_Y+DIALOG_WORD_SIZE*i, paint);
		}
		
	}
}