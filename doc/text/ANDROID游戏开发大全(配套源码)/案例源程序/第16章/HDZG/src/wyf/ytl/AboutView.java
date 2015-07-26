package wyf.ytl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AboutView extends SurfaceView implements SurfaceHolder.Callback{
	Bitmap bmpAbout;
	Bitmap bmpButton;
	HDZGActivity activity;
	public AboutView(HDZGActivity activity) {
		super(activity);
		this.activity = activity;
		this.getHolder().addCallback(this);
		bmpAbout = BitmapFactory.decodeResource(getResources(), R.drawable.about);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.buttons);
		bmpButton = Bitmap.createBitmap(bmp, 0, 0, 60, 30);
	} 
	
	public void onDraw(Canvas canvas){
		Paint paint = new Paint();
		paint.setTextSize(18);
		paint.setAntiAlias(true);
		canvas.drawBitmap(bmpAbout, 0, 0, null);
		canvas.drawBitmap(bmpButton, 240,430,null);
		canvas.drawText("返回", 250, 450, paint);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用
		Canvas canvas = holder.lockCanvas();
		try{
			synchronized(holder){
				onDraw(canvas);//绘制
			}			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {

	}

	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//如果是按下事件
			int x = (int)event.getX();
			int y = (int)event.getY();
			if(x>240 && x<300 && y>430&& y<460){//按下了返回键
				activity.myHandler.sendEmptyMessage(12);
			}
		}
		return true;
	}
}