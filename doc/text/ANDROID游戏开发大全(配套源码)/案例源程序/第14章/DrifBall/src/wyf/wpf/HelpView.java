package wyf.wpf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/*
 * 继承自View，用于显示帮助文本，在现实情况下
 * 按手机键盘上的返回按钮返回到游戏界面
 */
public class HelpView extends SurfaceView implements SurfaceHolder.Callback{
	DriftBall father;
	String [] helpText = {
			"移动手机方向使小球进行移动，",
			"小球遇到陷阱和漩涡会掉下去，",
			"将小球移动到家算胜利，在游戏",
			"进行中，按键盘上的”menu“键来",
			"暂停游戏并显示菜单。按”返回“",
			"键返回到游戏界面。"
	};
	int startX=25;
	int startY=125;
	//构造器
	public HelpView(DriftBall father) {		
		super(father);
		getHolder().addCallback(this);
		this.father = father;
	}
	//绘制方法，显示帮助文本
	protected void doDraw(Canvas canvas) {
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(Color.GREEN);
		p.setTextSize(20f);
		for(int i=0;i<helpText.length;i++){
			canvas.drawText(helpText[i], startX, startY+30*i, p);
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Canvas canvas = holder.lockCanvas();
		doDraw(canvas);
		if(canvas != null){
			holder.unlockCanvasAndPost(canvas);
		}
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	


	
}