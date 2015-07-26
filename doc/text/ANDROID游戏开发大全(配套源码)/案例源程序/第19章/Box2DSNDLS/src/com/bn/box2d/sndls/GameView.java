package com.bn.box2d.sndls;
import static com.bn.box2d.sndls.Constant.*;

import java.util.ArrayList;
import java.util.List;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView 
implements SurfaceHolder.Callback  //实现生命周期回调接口
{
	MyBox2dActivity activity;
	Paint paint;//画笔		
	DrawThread dt;//绘制线程
    AABB worldAABB;//创建 一个管理碰撞的世界   
    World world;     
    Pijin pjc;//皮筋
    String hightScore;//最高分
    //最后选择关卡菜单减去两个按钮后的平均值
    float x_pj;
    //物体列表
    ArrayList<MyPolygonImg> bl=new ArrayList<MyPolygonImg>();
    //英雄
    MyPolygonImg hero;
    //得分列表
    List<Score> scoreList=new ArrayList<Score>();
    
    //是否已经释放鼠头的标志位
    boolean flagSf=false;
    //鼠头初始位置
    float xst=180*yMainRatio;
    float yst=SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight();
    final float ysXst=xst;
    final float ysYst=yst;
	
	public GameView(MyBox2dActivity activity) 
	{
		super(activity);
		this.activity = activity;	
		this.loadGameData();
		this.initContactListener();
		
		//设置生命周期回调接口的实现者
		this.getHolder().addCallback(this);
		//初始化画笔
		paint = new Paint();//创建画笔
		paint.setAntiAlias(true);//打开抗锯齿
		//启动绘制线程
		dt=new DrawThread(this);
		dt.start();
		
	} 
	
	//加载游戏数据
	public void loadGameData()
	{
		worldAABB = new AABB();   
        
        //上下界，以屏幕的左上方为 原点，如果创建的刚体到达屏幕的边缘的话，会停止模拟   
        worldAABB.lowerBound.set(0f,-100.0f);
        worldAABB.upperBound.set(200.0f, 100.0f);//注意这里使用的是现实世界的单位   
           
        Vec2 gravity = new Vec2(0.0f,20.0f);   
        boolean doSleep = true;     
        //创建世界 
        world = new World(worldAABB, gravity, doSleep); 
        
        //创建场景中的物体
        for(int i=0;i<STONE_VERTEX[currStage].length;i++)
        {
            Bitmap[] bma=new Bitmap[IMG_ID[currStage][i].length];
            for(int j=0;j<IMG_ID[currStage][i].length;j++)
            {
            	bma[j]=PIC_ARRAY[IMG_ID[currStage][i][j]];
            }     
            MyPolygonImg myBodyTemp=Box2DUtil.createPolygonImg 
            (
              		LOCATION[currStage][i][0], 
              		LOCATION[currStage][i][1], 
              		STONE_VERTEX[currStage][i],
              		IS_MOVE[currStage][i], 
              		world, 
              		bma,
              		SIZE[currStage][i][0],
              		SIZE[currStage][i][1],
              		typeA[currStage][i],
              		this
            );
            myBodyTemp.body.setLinearVelocity(new Vec2(SPEED[currStage][i][0],SPEED[currStage][i][1]));
            bl.add(myBodyTemp); 
        }
	}

	//加载碰撞监听器
	public void initContactListener()
	{
		ContactListener cl=new ContactListener()
		{
			@Override
			public void add(ContactPoint arg0) 
			{
				//从当前的body查找出对应物件并执行事件处理
				BodySearchUtil.doAction
				(
					GameView.this,
					arg0.shape1.getBody(), 
					arg0.shape2.getBody(), 
					bl,
					arg0.position.x*RATE,
					arg0.position.y*RATE
				);
			}

			@Override
			public void persist(ContactPoint arg0){}

			@Override
			public void remove(ContactPoint arg0){}

			@Override
			public void result(ContactResult arg0){}			
		};
		world.setContactListener(cl);
	}

	public void onDraw(Canvas canvas)
	{		
		if(canvas==null)
		{
			return;
		}
		
		//计算场景总偏移量
		if(hero!=null)
		{
			float hx=hero.body.getPosition().x*RATE;
			float tempXOffset=-(hx-SCREEN_WIDTH/2);	
			if(tempXOffset>0)
			{
				tempXOffset=0;
			}
			else if(tempXOffset<SCREEN_WIDTH-CJ_WIDTH)
			{
				tempXOffset=SCREEN_WIDTH-CJ_WIDTH;
			}
			if(tempXOffset<xOffset)
			{
				xOffset=tempXOffset;
			}
		}
		  
		canvas.save();
		canvas.clipRect(new RectF(0,0,SCREEN_WIDTH,SCREEN_HEIGHT));
		//绘制背景
		canvas.drawBitmap(PIC_ARRAY[17], 0+xOffset*0.6f, 0, paint);
		canvas.drawBitmap(PIC_ARRAY[17], 0+xOffset*0.6f+PIC_ARRAY[17].getWidth(), 0, paint);
		//绘制背景草
		canvas.drawBitmap(PIC_ARRAY[26], 0+xOffset,SCREEN_HEIGHT-DMGD-PIC_ARRAY[26].getHeight(), paint);
		canvas.drawBitmap(PIC_ARRAY[26], 0+xOffset+PIC_ARRAY[26].getWidth(),SCREEN_HEIGHT-DMGD-PIC_ARRAY[26].getHeight(), paint);
		
		//绘制场景中的物体
		for(MyPolygonImg mb:bl)
		{
			mb.drawSelf(canvas, paint);
		}  
		 
		//绘制总得分
		String scoreStr=SCORE+"";
		float xsStart=SCREEN_WIDTH-NUM_ARRAY[0].getWidth()*(scoreStr.length()+1);
		float ysStart=NUM_ARRAY[0].getHeight()/2;
		for(int i=0;i<scoreStr.length();i++)
		{
			canvas.drawBitmap(NUM_ARRAY[scoreStr.charAt(i)-'0'], xsStart+i*NUM_ARRAY[0].getWidth() ,ysStart, paint);
		}
		
		//绘制拖动鼠头以及皮筋
		if(!flagSf)
		{			
			pjc=new Pijin(xst-PIC_ARRAY[1].getWidth(),yst,180*yMainRatio,SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);
			//绘制被手拖拉的鼠头  
			canvas.drawBitmap(PIC_ARRAY[1], xst+xOffset,yst, paint);
			//绘制橡皮筋
			pjc=new Pijin(xst-PIC_ARRAY[1].getWidth(),yst,180*yMainRatio-PIC_ARRAY[1].getWidth(),SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);
		} 
		if(flagSf&&!Pijin.flag&&hero!=null) 
		{ 
			pjc=new Pijin(hero.body.getPosition().x*RATE-PIC_ARRAY[1].getWidth(),hero.body.getPosition().y*RATE,180*yMainRatio,SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);
			//绘制橡皮筋
			pjc=new Pijin(hero.body.getPosition().x*RATE-PIC_ARRAY[1].getWidth(),hero.body.getPosition().y*RATE,180*yMainRatio-PIC_ARRAY[1].getWidth(),SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);			
		}
		if(flagSf&&Pijin.flag&&xOffset<=0&&xOffset>-190*yMainRatio)
		{
			pjc=new Pijin(180*yMainRatio-PIC_ARRAY[1].getWidth()+xOffset, SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight(),180*yMainRatio+xOffset, SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight());
			pjc.drawSelf(canvas, paint);
		}
		
		//绘制弹弓
		canvas.drawBitmap(PIC_ARRAY[22], 150*yMainRatio+xOffset,SCREEN_HEIGHT-DMGD-PIC_ARRAY[22].getHeight(), paint);		
		  
		//绘制前景草
		canvas.drawBitmap(PIC_ARRAY[25], 0+xOffset,SCREEN_HEIGHT-DMGD-PIC_ARRAY[25].getHeight(), paint);
		canvas.drawBitmap(PIC_ARRAY[25], 0+xOffset+PIC_ARRAY[25].getWidth(),SCREEN_HEIGHT-DMGD-PIC_ARRAY[25].getHeight(), paint);
		
		//绘制小得分列表
		for(Score sc:scoreList)
		{
			sc.drawSelf(canvas, paint);
		}
		canvas.restore();
		
		//绘制对话框
		if(dt.flag==true)
		{
			//绘制大背景
			canvas.drawBitmap(OTHER_PIC_ARRAY[3],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2,
								(SCREEN_HEIGHT-OTHER_PIC_ARRAY[3].getHeight())/2, paint);
			//绘制当前为第几关   
			canvas.drawBitmap(OTHER_PIC_ARRAY[currStage], (SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2, 
								(SCREEN_HEIGHT-OTHER_PIC_ARRAY[3].getHeight())/2, paint);
			//绘制最高分===================================================现在是以第一关的分数绘制的最高分，记得换掉
			hightScore=SCORE+"";
			xsStart=(SCREEN_WIDTH+OTHER_PIC_ARRAY[3].getWidth())/2-NUM_ARRAY[0].getWidth()*(hightScore.length()+4);
			ysStart=(SCREEN_HEIGHT-OTHER_PIC_ARRAY[3].getHeight())/2+NUM_ARRAY[0].getHeight()+10*yMainRatio;
			for(int i=0;i<scoreStr.length();i++)
			{
				canvas.drawBitmap(NUM_ARRAY[scoreStr.charAt(i)-'0'], xsStart+i*NUM_ARRAY[0].getWidth() ,ysStart, paint);
			}  
			//绘制得分=======================================================这里不需要换
			xsStart=SCREEN_WIDTH/2-NUM_ARRAY[0].getWidth()*(scoreStr.length()+2);
			ysStart=SCREEN_HEIGHT/2-4*yMainRatio;
			for(int i=0;i<scoreStr.length();i++)
			{   
				canvas.drawBitmap(NUM_ARRAY[scoreStr.charAt(i)-'0'], xsStart+(i+2)*NUM_ARRAY[0].getWidth() ,ysStart+NUM_ARRAY[0].getHeight()+8*yMainRatio, paint);
			}
			//绘制跳转进入主菜单按钮
			x_pj=(OTHER_PIC_ARRAY[3].getWidth()-OTHER_PIC_ARRAY[4].getWidth()*2)/3;
			canvas.drawBitmap(OTHER_PIC_ARRAY[4],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj,
								(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2-OTHER_PIC_ARRAY[4].getHeight()/2, paint);
			//绘制进入下一关按钮
			canvas.drawBitmap(OTHER_PIC_ARRAY[5],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj*2+OTHER_PIC_ARRAY[5].getWidth(),
								(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2-OTHER_PIC_ARRAY[4].getHeight()/2, paint);
			//绘制当前的提示：如：level_Failure与level_cleared
			if(dt.levelFlag)  
			{//level_cleared
				canvas.drawBitmap(OTHER_PIC_ARRAY[7],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth()+OTHER_PIC_ARRAY[7].getWidth()/2)/2-25*yMainRatio,
									SCREEN_HEIGHT/2-OTHER_PIC_ARRAY[7].getHeight(), paint);
			}
			if(!dt.levelFlag)
			{//level_Failure
				canvas.drawBitmap(OTHER_PIC_ARRAY[8],(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth()+OTHER_PIC_ARRAY[8].getWidth()/2)/2-25*yMainRatio,
									SCREEN_HEIGHT/2-OTHER_PIC_ARRAY[8].getHeight(), paint);
			}
		}
	}
	
	//屏幕触控事件	
	boolean isTouched=false;//是否摸到了鼠头
	float startX=0;
	float startY=0;
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		if(!flagSf)
		{
			int action=e.getAction();
			float x=e.getX();
			float y=e.getY();
			float w=PIC_ARRAY[1].getWidth();
			float h=PIC_ARRAY[1].getHeight();
			switch(action)
			{
			   case  MotionEvent.ACTION_DOWN:
				  if(x>xst&&x<xst+w&&y>yst&&y<yst+h)
				  {
					  if(!isTouched)
					  {
						  activity.soundutil.playSound(0, 0);
					  }
					  isTouched=true;
					  startX=x;
					  startY=y;
				  } 
			   break;
			   case  MotionEvent.ACTION_MOVE:
				  if(isTouched)
				  {
					  float dx=x-startX;
					  float dy=y-startY;
					  final float xYZ=-80*yMainRatio;
					  final float yYZ=60*yMainRatio;
					  
					  if(dx>0)
					  {
						  dx=0;
					  }
					  else if(dx<xYZ)
					  {
						  dx=xYZ;
					  }
					  
					  if(dy>yYZ)
					  {
						  dy=yYZ;
					  }
					  else if(dy<-yYZ)
					  {
						  dy=-yYZ;
					  }
					  
					  xst=ysXst+dx;
					  yst=ysYst+dy;
				  }
			   break;
			   case  MotionEvent.ACTION_UP:
				  if(isTouched)
				  {
					  isTouched=false;
					  flagSf=true;				  
					  //添加鼠头刚体
					  dt.xst=xst;  
					  dt.yst=yst;
					  dt.addTask=true;
					  activity.soundutil.playSound(1, 0);
			  }
			   break;
			}
			return true;
		}
		else
		{
			int action=e.getAction();
			float x=e.getX();
			float y=e.getY();
			float w=OTHER_PIC_ARRAY[4].getWidth();
			float h=OTHER_PIC_ARRAY[4].getHeight();
			switch(action)
			{
			   case  MotionEvent.ACTION_DOWN:
				  //点击的是返回主菜单按钮
				  if(dt.flag==true&&x>(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj&&
						  			x<(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj+w&&
						  			y>(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2-h/2&&
						  			y<(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2
					 )
				  {
					  activity.hd.sendEmptyMessage(1);
				  }
				  //点击的是下一关按钮
				  if(dt.flag==true&&x>(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj*2+w&&
						  			x<(SCREEN_WIDTH-OTHER_PIC_ARRAY[3].getWidth())/2+x_pj*2+w*2&&
						  			y>(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2-h/2&&
						  			y<(SCREEN_HEIGHT+OTHER_PIC_ARRAY[3].getHeight())/2
					 )   
				  {
					  if(currStage<2)
					  {
						  hero=null;//hero.body.getPosition().x*RATE;
						  DRAW_THREAD_FLAG=false;
						  flagSf=false;
						  currStage=currStage+1;
						  activity.hd.sendEmptyMessage(0);
					  }
				  }
			   break;
			}
		}
		return true;
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
	{
		
	}

	public void surfaceCreated(SurfaceHolder holder) {//创建时被调用
		repaint();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {//销毁时被调用
		
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