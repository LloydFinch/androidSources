package com.bn.tag;

import java.util.List;
import java.util.Vector;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import static com.bn.tag.Constant.*;

public class Shell {
	GameView gameView;
	private Bitmap bitmap;//位图
	float shellx;
	float shelly;
	SingleJianta jianta;
	static List<Shell> shl=new Vector<Shell>();
	static List<Target> tas=new Vector<Target>();
	
	Target tartee;
	double direction;
	public Shell(GameView gameView,Bitmap bitmap,float shellx,float shelly,Target tartee,SingleJianta jianta)
	{
		this.gameView=gameView;
		this.bitmap=bitmap;	
		this.shellx=shellx;
		this.shelly=shelly;
		this.tartee=tartee;
		this.jianta=jianta;
		//this.targett=targett;
	}
	//绘制子弹的方法
	public void drawSelf(Canvas canvas,Paint paint)
	{
		go();
		float dnX=shellx;
		float dnY=shelly;		
		canvas.drawBitmap(bitmap, dnX, dnY,paint);
		
	}
	//前进的方法
	public void go()
	{	
		direction=calDirection1(shellx,shelly,tartee.ballx,tartee.bally);
		float llx=(float) (SPEED*Math.sin(direction*Math.PI/180)+shellx);//x坐标
		float lly=(float) (SPEED*Math.cos(direction*Math.PI/180)+shelly);//y坐标	
		if(tas.contains(tartee))
		{
			shl.add(this);
		}		
		if(IsTwoRectCross//一个矩形的四个顶点之一是否在另一个矩形内
			(
					llx,lly,JIAN_TOU_WEIGHT,JIAN_TOU_HEIGHT,//左上点x,y坐标，长，宽
					tartee.ballx-SINGLE_PIC/2+20,tartee.bally-SINGLE_PIC/2,SINGLE_PIC-15,SINGLE_PIC-15
			))
			{
				
				//每击杀一个怪物获得金钱奖励			   
				tartee.bloodsum-=1;
				if(tartee.bloodsum==0)
				{					
					if(!tas.contains(tartee))
					{
						gameView.doller+=5;
						gameView.shaNUM+=1;
						gameView.shuijingMiddleNum+=1;
						tas.add(tartee);
					}				
						
							
				}
				shl.add(this);
							
				if(gameView.activity.isSoundOn())
				{
					gameView.playSound(2, 0);
				}
				
				
				
			}
			else if(getlength(llx,lly,jianta.clo*SINGLE_RODER,jianta.row*SINGLE_RODER)>R_LENGTH*R_LENGTH)
			{
				shl.add(this);				
			}
			else 
			{				
				shellx=llx;//(float) (SPEED*Math.sin(direction*Math.PI/180)+shellx);//x坐标
				shelly=lly;//(float) (SPEED*Math.cos(direction*Math.PI/180)+shelly);//y坐标
			}
	}
	
	public float getlength(float x1,float y1,float x2,float y2)
	{
		float result=(x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
		return result;
		
	}

	
	
	
	//计算方向
    public double calDirection1(float x1,float y1,float x2,float y2)
    {
    	double direction = 0;				
		float dx=x1-x2;
		float dy=y1-y2;          
		if(dx!=0||dy!=0)
		{
				if(dx>0&&dy>0)
				{
				direction=180+Math.toDegrees(Math.atan(dx/dy));
    			}
				else if(dx<0&&dy>0)
				{
					direction=180-Math.toDegrees(Math.atan(-dx/dy));
				} 
				else if(dx<0&&dy<0)
				{
					direction=Math.toDegrees(Math.atan(dx/dy));
				}
				else if(dx>0&&dy<0)
				{
					direction=360-Math.toDegrees(Math.atan(dx/-dy));
				}	
				else if(dx==0)
				{
					if(dy>0)
					{
						direction=180;
					}
					else
					{
						direction=0;
					}
				}
				else if(dy==0)
				{
					if(dx>0)
					{
						direction=270;
					}
					else
					{
						direction=90;
					}
				}
			}		
		return direction;
		
    }
    
    public static boolean IsTwoRectCross//一个矩形的四个顶点之一是否在另一个矩形内
	(
			float xLeftTop1,float yLeftTop1,float length1,float width1,//左上点x,y坐标，长，宽
			float xLeftTop2,float yLeftTop2,float length2,float width2
	)
	{
		if
		(
				isPointInRect(xLeftTop1,yLeftTop1,xLeftTop2,yLeftTop2,length2,width2)||	//左上顶点
				isPointInRect(xLeftTop1+length1,yLeftTop1,xLeftTop2,yLeftTop2,length2,width2)||	//右上顶点
				isPointInRect(xLeftTop1,yLeftTop1+width1,xLeftTop2,yLeftTop2,length2,width2)||	//左下顶点
				isPointInRect(xLeftTop1+length1,yLeftTop1+width1,xLeftTop2,yLeftTop2,length2,width2)||	//右下顶点
				
				isPointInRect(xLeftTop2,yLeftTop2,xLeftTop1,yLeftTop1,length1,width1)||	//左上顶点
				isPointInRect(xLeftTop2+length2,yLeftTop2,xLeftTop1,yLeftTop1,length1,width1)||	//右上顶点
				isPointInRect(xLeftTop2,yLeftTop2+width2,xLeftTop1,yLeftTop1,length1,width1)||	//左下顶点
				isPointInRect(xLeftTop2+length2,yLeftTop2+width2,xLeftTop1,yLeftTop1,length1,width1)	//右下顶点
		)
		{
			return true;
		}
		return false;
	}
	public static boolean isPointInRect//一个点是否在矩形内（包括边界）
	(
			float pointx,float pointy,
			float xLeftTop,float yLeftTop,float length,float width
	)
	{
		if(
				pointx>=xLeftTop&&pointx<=xLeftTop+length&&
				pointy>=yLeftTop&&pointy<=yLeftTop+width
		  )
		  {
			  return true;
		  }
		return false;
	}		
}
