package com.bn.tag;



import java.util.List;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import static com.bn.tag.Constant.*;

public class Target {
	GameView gameView;
	Bitmap bitmap;//���ڻ��Ƶ�ͼƬ
	float xspan=SINGLE_FOOT;//Ŀ�경��
	float yspan=SINGLE_FOOT;//Ŀ�경��
	double direction;
	float ballx=20;
	float bally=180;
	int ii=0;
	int jj=0;
	Bitmap bitmapma;
	static List<Target> ta=new Vector<Target>();
	double num=0;
	boolean workflag=true;
	int bloodsum;
	int bloodsumNO;
	int state;//0�����1 1�����2 2�����3
	
	
	public Target(GameView gameView,Bitmap bitmap,float ballx,float bally,int bloodsum,int bloodsumNO,int state,double direction,int ii)
	{
		this.gameView=gameView;
		this.bitmap=bitmap;	
		this.ballx=ballx;
		this.bally=bally;
		this.bloodsum=bloodsum;
		this.bloodsumNO=bloodsumNO;
		this.state=state;
		this.direction=direction;
		this.ii=ii;
	}
	public void drawSelf(Canvas canvas,Paint paint)
	{		
		Bitmap[] bit=new Bitmap[4];
		bit=getbitmap(direction,state);
		bitmapma=getbitmapma(bit);
		float x1=ballx-19+(SINGLE_RODER-GUAI_BLOOD_WEIGHT)/2;
		float y1=bally-19-10-GUAI_BLOOD_HEIGHT-5;
		
		canvas.drawBitmap(bitmapma, ballx-19,bally-19-10,paint);
		if(bloodsum>0)
		{				
			canvas.drawBitmap(gameView.shengming01,x1,y1,paint);
			canvas.save();
			canvas.clipPath(makePathDashTarget(bloodsumNO,bloodsum));
			canvas.drawBitmap(gameView.shengming, x1,y1, null);
			canvas.restore();
		}
		
	}
	
	public void goRode()
	{
			float x1=(float) (ballx+xspan*Math.cos(direction));
			float y1=(float)(bally+xspan*Math.sin(direction));
			boolean backFlag=false;//��־λ
			
			//���ϲ�
			float tempX=(float) (x1-SINGLE_PIC/2+0.01);
			float tempY=(float) (y1-SINGLE_PIC/2+0.01);
			int tempCol=(int)((tempX/SINGLE_RODER>=0)?(tempX/SINGLE_RODER):-1);
			int tempRow=(int)((tempY/SINGLE_RODER>=0)?(tempY/SINGLE_RODER):-1);
			if(tempCol==-1||
					tempRow==-1||
					MAP[tempRow][tempCol]==0)
			{//����ײ�����ûָ����
				backFlag=true;
			}
		    //��ײ�����ϲ��=======================begin	
			tempX=(float) (x1+SINGLE_PIC/2-0.01);
			tempY=(float) (y1-SINGLE_PIC/2+0.01);
		    tempCol=(int)((tempX/SINGLE_RODER>=0)?(tempX/SINGLE_RODER):-1);//���ϲ������
		    tempRow=(int)((tempY/SINGLE_RODER>=0)?(tempY/SINGLE_RODER):-1);
		    if(tempCol==-1||
			   tempRow==-1||
		       MAP[tempRow][tempCol]==0)
		    {//����ײ�����ûָ����
		    	backFlag=true;
		    }
////		    //��ײ�����²��=
		    tempX=(float) (x1-SINGLE_PIC/2+0.01);//���²��XY����
		    tempY=(float) (y1+SINGLE_PIC/2-0.01);
		    tempCol=(int)((tempX/SINGLE_RODER>=0)?(tempX/SINGLE_RODER):-1);//���²������
		    tempRow=(int)((tempY/SINGLE_RODER>=0)?(tempY/SINGLE_RODER):-1);
		    if(tempCol==-1||
			   tempRow==-1||
		       MAP[tempRow][tempCol]==0)
		    {//����ײ�����ûָ����
		    	backFlag=true;
		    }
//		    //��ײ�����²��=======================begin
		    tempX=(float) (x1+SINGLE_PIC/2-0.01);//���²��XY����
		    tempY=(float) (y1+SINGLE_PIC/2-0.01); 
		    tempCol=(int)((tempX/SINGLE_RODER>=0)?(tempX/SINGLE_RODER):-1);//���²������
		    tempRow=(int)((tempY/SINGLE_RODER>=0)?(tempY/SINGLE_RODER):-1);
		    if(tempCol==-1||
			   tempRow==-1||
		       MAP[tempRow][tempCol]==0)
		    {//����ײ�����ûָ����
		    	backFlag=true;
		    }
		    
		    //�жϹ����Ƿ񵽴����
		    if(tempCol==homeLocations[0][1]&&tempRow==homeLocations[0][0])
			{
		    	gameView.homeTh.setFlag(true);
		    	gameView.activity.shake();
		    	ta.add(this);//���б���ɾ��Ŀ��
		    	if(gameView.bloodNUM==0)
		    	{
		    		gameView.bloodNUM=0;
		    	}
		    	else if(gameView.bloodNUM>=1)
		    	{
		    		gameView.bloodNUM-=1;
		    	}
		    	
				//break;
			}
		   
		    if(!backFlag)
		    {//�����ƶ���ָ�
		    	ballx=x1;
		    	bally=y1;
		    	num++;
		    	
		    } 
		    else if(backFlag)
		    {		    	
		    	ii++;
			 	direction=CallDirection(MIDDLE_MAP[ii+1][0]-MIDDLE_MAP[ii][0],MIDDLE_MAP[ii+1][1]-MIDDLE_MAP[ii][1]);		    			    	
		    }		   		    
	}
	
	//���ö�����֤��־λ
	public void setWorkFlag(boolean workflag) {
		this.workflag = workflag;
	}

	//�������
	public double CallDirection(float x,float y)
	{
		double result = 0.0;
		if(x==0&&y>0)
		{
			result = (0.5*Math.PI);
		}
		else if(x==0&&y<0)
		{
			result=(1.5*Math.PI);
		}
		else if(y==0&&x>0)
		{
			result=(0.0*Math.PI);
		}
		else if(y==0&&x<0)
		{
			result=(1*Math.PI);
		}
		return  result;
		
	}
	//���ݹ�������ߵķ�����������ͼƬ
	public Bitmap[] getbitmap(double direction,int state)
	{
		Bitmap[] result=new Bitmap[4];
		if(direction==0.5*Math.PI)
		{
			result=gameView.targetbottom[state];
		}
		if(direction==1.5*Math.PI)
		{
			result=gameView.targettop[state];
		}
		if(direction==0.0*Math.PI)
		{
			result=gameView.targetstraight[state];
		}
		return result;
		
	}
	//���ù������߹����еĻ�֡����workflag��־λΪ��Ϸ�в˵���ť���º�ֹͣ������֡��
	public Bitmap getbitmapma(Bitmap[] bitmap)
	{
		Bitmap result = null;
		if(workflag)
		{
			result=bitmap[(jj++)%bitmap.length];
		}
		else if(!workflag)
		{
			result=bitmap[0];
		}			
		return result;
		
	}		
	
	private  Path makePathDashTarget(float sumblood,float currentblood) {
        Path p = new Path();
        //�ı���˳ʱ��
        float x1=ballx-19+(SINGLE_RODER-GUAI_BLOOD_WEIGHT)/2;
		float y1=bally-19-10-GUAI_BLOOD_HEIGHT-5;
                
        float x2=ballx-19+(SINGLE_RODER-GUAI_BLOOD_WEIGHT)/2+currentblood/sumblood*GUAI_BLOOD_WEIGHT;
        float y2=bally-19-10-GUAI_BLOOD_HEIGHT-5;
        
        float x3=ballx-19+(SINGLE_RODER-GUAI_BLOOD_WEIGHT)/2+currentblood/sumblood*GUAI_BLOOD_WEIGHT;
        float y3=bally-19-10-GUAI_BLOOD_HEIGHT-5+GUAI_BLOOD_HEIGHT;
        
        float x4=ballx-19+(SINGLE_RODER-GUAI_BLOOD_WEIGHT)/2;
        float y4=bally-19-10-GUAI_BLOOD_HEIGHT-5+GUAI_BLOOD_HEIGHT;                        
        	
        p.moveTo(x1, y1);
    	p.lineTo(x2, y2);
    	p.lineTo(x3, y3);
     	p.lineTo(x4, y4);
    	p.lineTo(x1, y1);
    	return p;     
    }	
}
