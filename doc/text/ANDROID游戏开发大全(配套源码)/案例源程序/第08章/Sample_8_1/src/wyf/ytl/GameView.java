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
	private static final int VIEW_WIDTH = 320;//此View的宽度
	private static final int VIEW_HEIGHT = 340;//此View的高度
	Game game;
	Spinner mySpinner;
	TextView CDTextView;
	int span = 13;
	Bitmap source = BitmapFactory.decodeResource(getResources(), R.drawable.source);
	Bitmap target = BitmapFactory.decodeResource(getResources(), R.drawable.target)	;
	Paint paint = new Paint();
	private Handler myHandler = new Handler(){//用来更新UI线程的控件
        public void handleMessage(Message msg) {
        	if(msg.what == 1){//改变长度的TextView的值 
        		CDTextView.setText("路径长度：" + (Integer)msg.obj);
        	}
        }
	};	
	public GameView(Context context, AttributeSet attrs) {//构造器
		super(context, attrs);
	}
	protected void onDraw(Canvas canvas) {//重写的绘制方法
		try{
			onMyDraw(canvas);//调用自己的绘制方法
		}
		catch(Exception e){}
	}
	protected void onMyDraw(Canvas canvas){//自己的绘制方法
		super.onDraw(canvas);
		canvas.drawColor(Color.GRAY);//背景色
		paint.setColor(Color.BLACK);//设置颜色
		paint.setStyle(Style.STROKE);//设置风格
		canvas.drawRect(5, 5, 313, 327, paint);//绘制矩形
		int[][] map = game.map;
		int row = map.length;
		int col = map[0].length;
		for(int i=0; i<row; i++){//绘制地图块
			for(int j=0; j<col; j++){
				if(map[i][j] == 0){//白色
					paint.setColor(Color.WHITE);
					paint.setStyle(Style.FILL);
					canvas.drawRect(6+j*(span+1), 6+i*(span+1), 6+j*(span+1)+span, 6+i*(span+1)+span, paint);
				}
				else if(map[i][j] == 1){//黑色
					paint.setColor(Color.BLACK);
					paint.setStyle(Style.FILL);
					canvas.drawRect(6+j*(span+1), 6+i*(span+1), 6+j*(span+1)+span, 6+i*(span+1)+span, paint);					
				}
			}
		}
		ArrayList<int[][]> searchProcess=game.searchProcess;
		for(int k=0;k<searchProcess.size();k++){//绘制寻找过程
			int[][] edge=searchProcess.get(k);  
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(1);
			canvas.drawLine(
				edge[0][0]*(span+1)+span/2+6,edge[0][1]*(span+1)+span/2+6,
				edge[1][0]*(span+1)+span/2+6,edge[1][1]*(span+1)+span/2+6,
				paint
			);
		}
		//绘制结果路径
		if(
			mySpinner.getSelectedItemId()==0||
			mySpinner.getSelectedItemId()==1||
			mySpinner.getSelectedItemId()==2
		){//"深度优先","广度优先","广度优先 A*" 绘制
			if(game.pathFlag){
				HashMap<String,int[][]> hm=game.hm;		
				int[] temp=game.target;
				int count=0;//路径长度计数器			
				while(true){
					int[][] tempA=hm.get(temp[0]+":"+temp[1]);
					paint.setColor(Color.BLACK);
					paint.setStyle(Style.STROKE);//加粗
					paint.setStrokeWidth(2);//设置画笔粗度为2px
					canvas.drawLine(	
						tempA[0][0]*(span+1)+span/2+6,tempA[0][1]*(span+1)+span/2+6,
						tempA[1][0]*(span+1)+span/2+6,tempA[1][1]*(span+1)+span/2+6, 
						paint
					);
					count++;
					if(tempA[1][0]==game.source[0]&&tempA[1][1]==game.source[1]){//判断有否到出发点
						break;
					}
					temp=tempA[1];			
				}
				Message msg1 = myHandler.obtainMessage(1, count);//改变TextView文字
				myHandler.sendMessage(msg1);
			}			
		}
		else if(
			mySpinner.getSelectedItemId()==3||
			mySpinner.getSelectedItemId()==4
		){//"Dijkstra"绘制
		    if(game.pathFlag){
		    	HashMap<String,ArrayList<int[][]>> hmPath=game.hmPath;
				ArrayList<int[][]> alPath=hmPath.get(game.target[0]+":"+game.target[1]);
				for(int[][] tempA:alPath){
					paint.setColor(Color.BLACK);
					paint.setStyle(Style.STROKE);//加粗
					paint.setStrokeWidth(2);//设置画笔粗度为2px						    
					canvas.drawLine(	
						tempA[0][0]*(span+1)+span/2+6,tempA[0][1]*(span+1)+span/2+6,
						tempA[1][0]*(span+1)+span/2+6,tempA[1][1]*(span+1)+span/2+6, 
						paint
					);			
				}
				Message msg1 = myHandler.obtainMessage(1, alPath.size());//改变TextView文字
				myHandler.sendMessage(msg1);
		    }
		}
		//绘制出发点
		canvas.drawBitmap(source, 6+game.source[0]*(span+1), 6+game.source[1]*(span+1), paint);
		//绘制目标点
		canvas.drawBitmap(target, 6+game.target[0]*(span+1), 6+game.target[1]*(span+1), paint);
	}
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){//重新的方法，返回的是此View的大小
        setMeasuredDimension(VIEW_WIDTH,VIEW_HEIGHT);
    }
}