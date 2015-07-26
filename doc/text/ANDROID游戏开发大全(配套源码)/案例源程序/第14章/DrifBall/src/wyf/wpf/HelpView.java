package wyf.wpf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/*
 * �̳���View��������ʾ�����ı�������ʵ�����
 * ���ֻ������ϵķ��ذ�ť���ص���Ϸ����
 */
public class HelpView extends SurfaceView implements SurfaceHolder.Callback{
	DriftBall father;
	String [] helpText = {
			"�ƶ��ֻ�����ʹС������ƶ���",
			"С��������������л����ȥ��",
			"��С���ƶ�������ʤ��������Ϸ",
			"�����У��������ϵġ�menu������",
			"��ͣ��Ϸ����ʾ�˵����������ء�",
			"�����ص���Ϸ���档"
	};
	int startX=25;
	int startY=125;
	//������
	public HelpView(DriftBall father) {		
		super(father);
		getHolder().addCallback(this);
		this.father = father;
	}
	//���Ʒ�������ʾ�����ı�
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