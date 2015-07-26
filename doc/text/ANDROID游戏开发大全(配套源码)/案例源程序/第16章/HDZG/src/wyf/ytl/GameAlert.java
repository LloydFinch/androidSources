package wyf.ytl;
/*
 * ����Ϊ������,��Ҫ����Ķ�������Ϸ����ʱ�ᵯ������ʾ��Ϣ,������Ϯ\����Σ��\���гɹ���
 */
import static wyf.ytl.ConstantUtil.*;	//���������
import android.graphics.Bitmap;			//���������
import android.graphics.Canvas;			//���������
import android.graphics.Paint;			//���������
import android.graphics.Typeface;		//���������
import android.view.View;				//���������

public abstract class GameAlert implements View.OnTouchListener{
	Bitmap bmpDialogBack;//�Ի��򱳾�
	Bitmap bmpDialogButton;//�Ի���ť
	GameView gameView;
	public GameAlert(GameView gameView,Bitmap bmpDialogBack,Bitmap bmpDialogButton){
		this.gameView = gameView;
		this.bmpDialogBack = bmpDialogBack;
		this.bmpDialogButton = bmpDialogButton;
	}
	//���󷽷�:���ڻ�ü�������ƶԻ���
	public abstract void drawDialog(Canvas canvas);
	//����:���Ƹ������ַ������Ի�����
	public void drawString(Canvas canvas,String string){
		Paint paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//����������ɫ
		paint.setAntiAlias(true);//�����
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
		paint.setTextSize(DIALOG_WORD_SIZE);//�������ִ�С
		int lines = string.length()/DIALOG_WORD_EACH_LINE+(string.length()%DIALOG_WORD_EACH_LINE==0?0:1);//�����Ҫ����������
		for(int i=0;i<lines;i++){
			String str="";
			if(i == lines-1){//��������һ���Ǹ���̫���ĺ���
				str = string.substring(i*DIALOG_WORD_EACH_LINE);
			}else{
				str = string.substring(i*DIALOG_WORD_EACH_LINE, (i+1)*DIALOG_WORD_EACH_LINE);
			}
			canvas.drawText(str, DIALOG_WORD_START_X, DIALOG_WORD_START_Y+DIALOG_WORD_SIZE*i, paint);
		}
		
	}
}