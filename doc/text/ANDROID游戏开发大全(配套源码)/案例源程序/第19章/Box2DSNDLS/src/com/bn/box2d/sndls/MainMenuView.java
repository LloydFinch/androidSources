package com.bn.box2d.sndls;

import static com.bn.box2d.sndls.Constant.*;
import static com.bn.box2d.sndls.MainMenuDrawThread.*;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainMenuView extends SurfaceView implements SurfaceHolder.Callback
{
	Paint paint;
	List<Taj> tlist=new ArrayList<Taj>();	//飞头的集合
	int flag;//按钮的标志位
	boolean isTouch;//设置按钮是否被按下的标志位
	boolean syisTouch;//上移按钮中的声音按钮是否被按下的标志位
	boolean ckisTouch;//上移按钮中的公司信息查看按钮是否被按下的标志位
	boolean szisTouch;//上移按钮中的分数信息查看按钮是否被按下的标志位
	boolean isClose;  //是否关闭按钮
	TJThread thd;//控制动物飞出的线程
	MainMenuDrawThread mmt;//界面刷帧线程
	MyBox2dActivity activity;
	String str;
	boolean degreesFlag=true;//旋转的标志位
	public MainMenuView(MyBox2dActivity activity)
	{
		super(activity);
		this.activity=activity;
		//设置生命周期回调接口的实现者
		this.getHolder().addCallback(this);
		paint=new Paint();
		paint.setAntiAlias(true);//打开抗锯齿
	}
	//绘制    
	public void onDraw(Canvas canvas)
	{
		//绘制背景
		drawSelf(canvas,paint,PE_ARRAY[0],bgxoffset,0,1);
		//绘制强壮的草
		drawSelf(canvas,paint,PE_ARRAY[2],bdxoffset,SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[2].getHeight(),0);
		
		//绘制飞的老鼠
		if(this.tlist.size()!=0)
		{
			float SPEED=1.3f;
			for(Taj tj:this.tlist)  
			{
				tj.timeSpan+=SPEED;
				tj.drawSelf(canvas, paint);
			}
		}
		for(int i=0;i<thd.tlist.size();i++)
		{
			this.tlist.clear();
			if(thd.tlist.size()!=0)
			{
				this.tlist.add(thd.tlist.get(i));
			}
		}
		   
		//绘制的小草
		drawSelf(canvas,paint,PE_ARRAY[3],bdxoffset,SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[3].getHeight(),0);
		//绘制地面
		drawSelf(canvas,paint,PE_ARRAY[1],bdxoffset,SCREEN_HEIGHT-PE_ARRAY[1].getHeight(),0);
		//绘制标题
		
		canvas.drawBitmap(PE_ARRAY[18], (SCREEN_WIDTH-PE_ARRAY[18].getWidth())/2,SCREEN_HEIGHT/2-PE_ARRAY[18].getHeight()*2, paint);
		//绘制开始置按钮
		paint.setAlpha(128);
		canvas.drawBitmap(PE_ARRAY[6], LOCALTION_BUTTON[4][0],LOCALTION_BUTTON[4][1], paint);
		paint.reset();		
		paint.setAlpha(190); 
		canvas.save();
		//平移矩阵
		Matrix m1=new Matrix();
		//旋转矩阵
		Matrix m2=new Matrix();
		//组合以上矩阵
		Matrix m3=new Matrix();
		if(degreesFlag)
		{
			m1.setTranslate(LOCALTION_BUTTON[0][0], LOCALTION_BUTTON[0][1]);
			m2.setRotate(mmt.degrees, LOCALTION_BUTTON[0][0]+PE_ARRAY[7].getWidth()/2, LOCALTION_BUTTON[0][1]+PE_ARRAY[7].getHeight()/2);
			m3.setConcat(m2, m1);
		}
		else
		{
			m3.setTranslate(LOCALTION_BUTTON[0][0], LOCALTION_BUTTON[0][1]);
		}

		//绘制设置按钮		
		canvas.drawBitmap(PE_ARRAY[7], LOCALTION_BUTTON[0][0], LOCALTION_BUTTON[0][1], paint);
		canvas.drawBitmap(PE_ARRAY[17], m3, paint);
		canvas.restore();
		//绘制上移子菜单    
		if(flag==1)  
		{  
			if(syoffset<=PE_ARRAY[9].getHeight())
			{ 
				degreesFlag=true;
				canvas.clipRect(LOCALTION_BUTTON[1][0], 
								SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-syoffset, 
								LOCALTION_BUTTON[1][0]+PE_ARRAY[9].getWidth(), 
								SCREEN_HEIGHT-PE_ARRAY[1].getHeight());
				//绘制背景
				canvas.drawBitmap(PE_ARRAY[9], LOCALTION_BUTTON[3][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-syoffset, paint);
				//绘制分数信息查看
				canvas.drawBitmap(PE_ARRAY[12], LOCALTION_BUTTON[3][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()+SET_BACK_CK_OFFSET*yMainRatio-syoffset, paint);
				//绘制公司信息查看
				canvas.drawBitmap(PE_ARRAY[11], LOCALTION_BUTTON[2][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()+PE_ARRAY[11].getHeight()-syoffset, paint);
				//绘制声音按钮
				canvas.drawBitmap(PE_ARRAY[10], LOCALTION_BUTTON[1][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()+PE_ARRAY[10].getHeight()*2-syoffset, paint);
				if(syisTouch)
				{
					canvas.drawBitmap(PE_ARRAY[16], LOCALTION_BUTTON[1][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()+PE_ARRAY[16].getHeight()*2-syoffset, paint);
				}
			}
			else
			{
				//绘制背景
				canvas.drawBitmap(PE_ARRAY[9], LOCALTION_BUTTON[3][0], LOCALTION_BUTTON[3][1]-SET_BACK_CK_OFFSET*yMainRatio, paint);
				//绘制分数信息查看
				canvas.drawBitmap(PE_ARRAY[12], LOCALTION_BUTTON[3][0], LOCALTION_BUTTON[3][1], paint);
				//绘制公司信息查看
				canvas.drawBitmap(PE_ARRAY[11], LOCALTION_BUTTON[2][0], LOCALTION_BUTTON[2][1], paint);
				//绘制声音按钮
				canvas.drawBitmap(PE_ARRAY[10], LOCALTION_BUTTON[1][0], LOCALTION_BUTTON[1][1], paint);
				if(syisTouch)
				{
					canvas.drawBitmap(PE_ARRAY[16], LOCALTION_BUTTON[1][0], LOCALTION_BUTTON[1][1], paint);
				}
				isTouch=true;
				degreesFlag=false;
			}
		}
		//绘制下移子菜单
		if(flag==2)
		{
			if(xyoffset<=PE_ARRAY[9].getHeight())
			{
				degreesFlag=true;
				canvas.clipRect(LOCALTION_BUTTON[1][0], 
								SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[9].getHeight()+xyoffset, 
								LOCALTION_BUTTON[1][0]+PE_ARRAY[9].getWidth(), 
								SCREEN_HEIGHT-PE_ARRAY[1].getHeight());
				//绘制背景
				canvas.drawBitmap(PE_ARRAY[9], LOCALTION_BUTTON[3][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[9].getHeight()+xyoffset, paint);
				//绘制分数信息查看
				canvas.drawBitmap(PE_ARRAY[12], LOCALTION_BUTTON[3][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[9].getHeight()+xyoffset+SET_BACK_CK_OFFSET*yMainRatio, paint);
				//绘制公司信息查看
				canvas.drawBitmap(PE_ARRAY[11], LOCALTION_BUTTON[2][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[9].getHeight()+xyoffset+PE_ARRAY[11].getHeight(), paint);
				//绘制声音按钮
				canvas.drawBitmap(PE_ARRAY[10], LOCALTION_BUTTON[1][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[9].getHeight()+xyoffset+PE_ARRAY[10].getHeight()*2, paint);
				if(syisTouch)
				{
					canvas.drawBitmap(PE_ARRAY[16], LOCALTION_BUTTON[1][0], SCREEN_HEIGHT-PE_ARRAY[1].getHeight()-PE_ARRAY[9].getHeight()+xyoffset+PE_ARRAY[16].getHeight()*2, paint);
				}
			}
			else
			{
				szisTouch=false;
				isTouch=false;
				degreesFlag=false;
			}
		}
		canvas.save();
		//绘制设置按钮
		canvas.drawBitmap(PE_ARRAY[7], LOCALTION_BUTTON[0][0], LOCALTION_BUTTON[0][1], paint);
		canvas.drawBitmap(PE_ARRAY[17], m3, paint);
		canvas.restore();
		paint.reset();
		
		//此处点击可以查看百纳公司的信息
		if(ckisTouch)
		{     
			isTouch=false;  
			if(zyoffset<=PE_ARRAY[14].getWidth())
			{
				canvas.clipRect(0, 0, zyoffset+PE_ARRAY[15].getWidth()/2, SCREEN_HEIGHT);
				canvas.drawBitmap(PE_ARRAY[14], -PE_ARRAY[14].getWidth()+zyoffset, 0, paint);
				canvas.drawBitmap(PE_ARRAY[15], -PE_ARRAY[15].getWidth()/2+zyoffset, SCREEN_HEIGHT-PE_ARRAY[15].getHeight()*3/2, paint);
			}
			else
			{
				canvas.drawBitmap(PE_ARRAY[14], 0, 0, paint);
				canvas.drawBitmap(PE_ARRAY[15], PE_ARRAY[14].getWidth()-PE_ARRAY[15].getWidth()/2, SCREEN_HEIGHT-PE_ARRAY[15].getHeight()*3/2, paint);
			}
		} 
		if(isClose)
		{
			if(yyoffset<=PE_ARRAY[14].getWidth()+PE_ARRAY[15].getWidth()/2)
			{
				canvas.clipRect(0, 0, PE_ARRAY[14].getWidth()+PE_ARRAY[15].getWidth()/2-yyoffset, SCREEN_HEIGHT);
				canvas.drawBitmap(PE_ARRAY[14], -yyoffset, 0, paint);
				canvas.drawBitmap(PE_ARRAY[15], PE_ARRAY[14].getWidth()-PE_ARRAY[15].getWidth()/2-yyoffset, SCREEN_HEIGHT-PE_ARRAY[15].getHeight()*3/2, paint);
			}
		}
		
		//绘制分数查看对话框  
		if(szisTouch)
		{   
			//背景
			canvas.drawBitmap(OTHER_PIC_ARRAY[6], (SCREEN_WIDTH-OTHER_PIC_ARRAY[6].getWidth())/2, (SCREEN_HEIGHT-OTHER_PIC_ARRAY[6].getHeight())/2, paint);
			final float yoffset=OTHER_PIC_ARRAY[0].getHeight()*73/100;
			for(int i=0;i<3;i++)    
			{      
				float xsStart=SCREEN_WIDTH/2;
				float ysStart=(SCREEN_HEIGHT+OTHER_PIC_ARRAY[6].getHeight())/2-yoffset*(3-i)+5*yMainRatio;
				str=HH_SCORE[i]+"";
				for(int j=0;j<str.length();j++)
				{
					canvas.drawBitmap(NUM_ARRAY[str.charAt(j)-'0'], xsStart+j*NUM_ARRAY[0].getWidth() ,ysStart, paint);
				}
			}
		}
	}
	
	//自定义的绘制方法
	public void drawSelf(Canvas canvas,Paint paint,Bitmap bm,float xlocation,float ylocation,int flag)
	{//上一图片已经绘制完毕
		if(xlocation-bm.getWidth()>=0)
		{
			if(flag==0){
				bdxoffset=0;
			} 
			if(flag==1){  
				bgxoffset=0;
			}
			xlocation=0;
			canvas.drawBitmap(bm, 0, ylocation, paint);
		}//开始绘制接下来的图片
		else if(xlocation>bm.getWidth()-SCREEN_WIDTH)
		{
			canvas.drawBitmap(bm, 0-xlocation, ylocation, paint);
			canvas.drawBitmap(bm, bm.getWidth()-xlocation-XOFFSET, ylocation, paint);
		}
		else
		{
			canvas.drawBitmap(bm, 0-xlocation, ylocation, paint);
		}
	}
	//设置监听
	public boolean onTouchEvent(MotionEvent e)
	{
		int currentNUm=e.getAction();
		float x=e.getX();
		float y=e.getY();		
		switch(currentNUm)
		{
			case MotionEvent.ACTION_DOWN:
				//按下设置按钮
				if(x>LOCALTION_BUTTON[0][0]&&x<LOCALTION_BUTTON[0][0]+LOCALTION_BUTTON[0][2]&&
				   y>LOCALTION_BUTTON[0][1]&&y<LOCALTION_BUTTON[0][1]+LOCALTION_BUTTON[0][3])
				{
					if(!isTouch)
					{
						flag=1;//点击的是设置按钮
						xyoffset=0;
					}
					else
					{
						flag=2;//已经点击了一次，再次点击设置按钮
						syoffset=0;
					}
				}
				//点击的是声音按钮
				if(	isTouch&&x>LOCALTION_BUTTON[1][0]&&x<LOCALTION_BUTTON[1][0]+LOCALTION_BUTTON[1][2]&&
					y>LOCALTION_BUTTON[1][1]&&y<LOCALTION_BUTTON[1][1]+LOCALTION_BUTTON[1][3])
				{
					syisTouch=!syisTouch;
				}
				//点击的是公司信息按钮
				else if(isTouch&&!szisTouch&&x>LOCALTION_BUTTON[2][0]&&x<LOCALTION_BUTTON[2][0]+LOCALTION_BUTTON[2][2]&&
				   y>LOCALTION_BUTTON[2][1]&&y<LOCALTION_BUTTON[2][1]+LOCALTION_BUTTON[2][3])
				{
					ckisTouch=!ckisTouch;
					yyoffset=0;
					isClose=false;
				}
				//点击的是分数信息按钮
				else if(isTouch&&!ckisTouch&&x>LOCALTION_BUTTON[3][0]&&x<LOCALTION_BUTTON[3][0]+LOCALTION_BUTTON[3][2]&&
				   y>LOCALTION_BUTTON[3][1]&&y<LOCALTION_BUTTON[3][1]+LOCALTION_BUTTON[3][3])
				{
					szisTouch=!szisTouch;
				}
				//如果点击的是关闭按钮
				if(ckisTouch&&x>(PE_ARRAY[14].getWidth()-PE_ARRAY[15].getWidth()/2)&&x<(PE_ARRAY[14].getWidth()+PE_ARRAY[15].getWidth()/2)&&
				   y>(SCREEN_HEIGHT-PE_ARRAY[15].getHeight()*3/2)&&y<(SCREEN_HEIGHT-PE_ARRAY[15].getHeight()/2))
				{
					isClose=true;
					ckisTouch=false;
					zyoffset=0;
				}
				if(!szisTouch&&x>LOCALTION_BUTTON[4][0]&&x<LOCALTION_BUTTON[4][0]+LOCALTION_BUTTON[4][2]&&
				   y>LOCALTION_BUTTON[4][1]&&y<LOCALTION_BUTTON[4][1]+LOCALTION_BUTTON[4][3])
				{
					activity.hd.sendEmptyMessage(0);
					MAIN_DRAW_THREAD_FLAG=false;
					TJ_CONTROL_FLAG=false; 
				}
			break;
		}
		return true;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height)
	{
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{ 
		//线程标志位//开启刷帧线程
		MAIN_DRAW_THREAD_FLAG=true;
		TJ_CONTROL_FLAG=true;
		if(mmt==null)
		{
			mmt=new MainMenuDrawThread(this);
			mmt.start();
		}
		if(thd==null)
		{
			thd=new TJThread(this);
			thd.start();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		MAIN_DRAW_THREAD_FLAG=false;
		TJ_CONTROL_FLAG=false;
		mmt=null;
		thd=null;
	}
	
	public void repaint()
	{
		SurfaceHolder holder=this.getHolder();
		Canvas canvas = holder.lockCanvas();//获取画布
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
}