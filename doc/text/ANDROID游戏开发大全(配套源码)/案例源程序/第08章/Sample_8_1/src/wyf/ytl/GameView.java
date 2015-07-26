package wyf.ytl;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
public class GameView extends View{
	private static final int VIEW_WIDTH = 320;//��View�Ŀ��
	private static final int VIEW_HEIGHT = 340;//��View�ĸ߶�
	Game game;
	Spinner mySpinner;
	TextView CDTextView;
	int span = 13;
	Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.source);
	Bitmap target = BitmapFactory.decodeResource(getResources(), R.drawable.target)	;
	Paint paint = new Paint();
	private Handler myHandler = new Handler(){//��������UI�̵߳Ŀؼ�
        public void handleMessage(Message msg) {
        	if(msg.what == 1){//�ı䳤�ȵ�TextView��ֵ 
        		CDTextView.setText("·�����ȣ�" + (Integer)msg.obj);
        	}
        }
	};	
	public GameView(Context context, AttributeSet attrs) {//������
		super(context, attrs);
	}
	protected void onDraw(Canvas canvas) {//��д�Ļ��Ʒ���
		try{
			onMyDraw(canvas);//�����Լ��Ļ��Ʒ���
		}
		catch(Exception e){}
	}
	protected void onMyDraw(Canvas canvas){//�Լ��Ļ��Ʒ���
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);//����ɫ
		paint.setColor(Color.BLACK);//������ɫ
		paint.setStyle(Style.STROKE);//���÷��
		canvas.drawRect(5, 5, 313, 327, paint);//���ƾ���
		int[][] map = game.map;
		int row = map.length;
		int col = map[0].length;
		for(int i=0; i<row; i++){//���Ƶ�ͼ��
			for(int j=0; j<col; j++){
				if(map[i][j] == 0){//��ɫ
					paint.setColor(Color.WHITE);
					paint.setStyle(Style.FILL);
					canvas.drawRect(6+j*(span+1), 6+i*(span+1), 6+j*(span+1)+span, 6+i*(span+1)+span, paint);
				}
				else if(map[i][j] == 1){//��ɫ
					paint.setColor(Color.BLACK);
					paint.setStyle(Style.FILL);
					canvas.drawRect(6+j*(span+1), 6+i*(span+1), 6+j*(span+1)+span, 6+i*(span+1)+span, paint);					
				}
			}
		}
		ArrayList<int[][]> searchProcess=game.searchProcess;
		for(int k=0;k<searchProcess.size();k++){//����Ѱ�ҹ���
			int[][] edge=searchProcess.get(k);  
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(1);
			canvas.drawLine(
				edge[0][0]*(span+1)+span/2+6,edge[0][1]*(span+1)+span/2+6,
				edge[1][0]*(span+1)+span/2+6,edge[1][1]*(span+1)+span/2+6,
				paint
			);
		}
		//���ƽ��·��
		if(
			mySpinner.getSelectedItemId()==0||
			mySpinner.getSelectedItemId()==1||
			mySpinner.getSelectedItemId()==2
		){//"�������","�������","������� A*" ����
			if(game.pathFlag){
				HashMap<String,int[][]> hm=game.hm;		
				int[] temp=game.target;
				int count=0;//·�����ȼ�����			
				while(true){
					int[][] tempA=hm.get(temp[0]+":"+temp[1]);
					paint.setColor(Color.BLACK);
					paint.setStyle(Style.STROKE);//�Ӵ�
					paint.setStrokeWidth(2);//���û��ʴֶ�Ϊ2px
					canvas.drawLine(	
						tempA[0][0]*(span+1)+span/2+6,tempA[0][1]*(span+1)+span/2+6,
						tempA[1][0]*(span+1)+span/2+6,tempA[1][1]*(span+1)+span/2+6, 
						paint
					);
					count++;
					if(tempA[1][0]==game.source[0]&&tempA[1][1]==game.source[1]){//�ж��з񵽳�����
						break;
					}
					temp=tempA[1];			
				}
				Message msg1 = myHandler.obtainMessage(1, count);//�ı�TextView����
				myHandler.sendMessage(msg1);
			}			
		}
		else if(
			mySpinner.getSelectedItemId()==3||
			mySpinner.getSelectedItemId()==4
		){//"Dijkstra"����
		    if(game.pathFlag){
		    	HashMap<String,ArrayList<int[][]>> hmPath=game.hmPath;
				ArrayList<int[][]> alPath=hmPath.get(game.target[0]+":"+game.target[1]);
				for(int[][] tempA:alPath){
					paint.setColor(Color.BLACK);
					paint.setStyle(Style.STROKE);//�Ӵ�
					paint.setStrokeWidth(2);//���û��ʴֶ�Ϊ2px						    
					canvas.drawLine(	
						tempA[0][0]*(span+1)+span/2+6,tempA[0][1]*(span+1)+span/2+6,
						tempA[1][0]*(span+1)+span/2+6,tempA[1][1]*(span+1)+span/2+6, 
						paint
					);			
				}
				Message msg1 = myHandler.obtainMessage(1, alPath.size());//�ı�TextView����
				myHandler.sendMessage(msg1);
		    }
		}
		//���Ƴ�����
		canvas.drawBitmap(source, 6+game.source[0]*(span+1), 6+game.source[1]*(span+1), paint);
		//����Ŀ���
		canvas.drawBitmap(target, 6+game.target[0]*(span+1), 6+game.target[1]*(span+1), paint);
	}
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){//���µķ��������ص��Ǵ�View�Ĵ�С
        setMeasuredDimension(VIEW_WIDTH,VIEW_HEIGHT);
    }
}