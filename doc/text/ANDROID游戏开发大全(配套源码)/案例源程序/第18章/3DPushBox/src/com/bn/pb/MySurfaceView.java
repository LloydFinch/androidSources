package com.bn.pb;

import static com.bn.pb.Constant.*;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

class MySurfaceView extends GLSurfaceView 
{		
	MyActivity father;//声明Activity
	private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置Y坐标
    
    public float yAngle=0f;//方位角
    public float xAngle=90f;//仰角 
    public float cx;//摄像机x坐标
    public float cy;//摄像机y坐标
    public float cz;//摄像机z坐标
    
    public int floorId;//地板纹理ID
    public int floor_backId;//地板的反面纹理
    public int wallId;//墙纹理
    public int woodbinId0;//木箱纹理Id
    public int woodbinId1;//红色木箱纹理Id
    public int targetId;//目标纹理Id
    public int robot_texId;//机器人纹理Id
    public int robot_head;//机器人的头纹理
    
    public int button_up;//上下左右键的虚拟按钮纹理Id
    public int button_down;
    public int button_left;
    public int button_right;
    public int viewId;//视角纹理
    
    public int tempRow=0;//机器人将要运动到的行和列
    public int tempCol=0;
    boolean flag=true;//使tempRow和tempCol的赋值语句只执行一次的标志位
    boolean viewFlag=false;//摄像机视角标志位
    
    private SceneRenderer mRenderer;//场景渲染器
    
    public Floor floor;//地板类
	public Wall wall;//墙类
	public WallOutSide walloutside;//墙体的最外层类
	public CubeGroup cubegroup;//木箱组类
	public TextureRectGroup texturerectgroup;//矩形纹理组类
	public RobotGroup robotgroup;//机器人组类
	public CubeGo cubego;//木箱走动的动画类
	
	MediaPlayer mMediaPlayer;//背景音乐播放器类
	
	public TextureRect buttonUp;//上下左右键的纹理矩形
	public TextureRect buttonDown;
	public TextureRect buttonLeft;
	public TextureRect buttonRight;
	public TextureRect view;//视角纹理
	
	KeyThread kt;//键盘监控线程
	
	float screenWidth;//屏幕宽度
    float screenHeight;//屏幕高度
    
    int keyState=0;//键盘状态  1-up
	
    //----------------------复制当前关的二维数组数组-------------------------
	int objectMap[][];//用于复制当前关的地图的二维数组
	int rows=0;//当前二维数组的行数
	int cols=0;//当前二维数组的列数
	//----------------------复制当前关的二维数组数组-------------------------
    
    
	
	public MySurfaceView(Context context)
	{
        super(context);
        this.father=(MyActivity)context;        
        
        
        
        //设置摄像机的位置
        cx=(float)(Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//摄像机x坐标 
        cz=(float)(Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//摄像机z坐标 
        cy=(float)(Math.sin(Math.toRadians(xAngle))*DISTANCE);//摄像机y坐标 
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染    
        
        
        //----------------------复制当前关的二维数组数组-------------------------
        rows=Constant.MAP[Constant.COUNT].length;
		cols=Constant.MAP[Constant.COUNT][0].length;
		objectMap=new int[rows][cols];
		
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				objectMap[i][j]=Constant.MAP[Constant.COUNT][i][j];
			}
		}
		//----------------------复制当前关的二维数组数组-------------------------
        
        
    }
	
	@Override 
    public boolean onTouchEvent(MotionEvent e) 
	{
        float y = e.getY();
        float x = e.getX();
        
        switch (e.getAction())
        {
        case MotionEvent.ACTION_DOWN://按下动作
        	float yRatio=y/screenHeight;
            float xRatio=x/screenWidth;
            
            if(flag)//整个游戏过程中，次句只执行一次
			{
            	tempRow=robotgroup.m;//得到当前关机器人所在的行数和列数
    			tempCol=robotgroup.n;
				flag=false;
			}
            
            //***********************前进按钮***********************************************
            //视角变换虚拟按钮
            if( yRatio>viewYMin&&yRatio<viewYMax&&xRatio>viewXMin&&xRatio<viewXMax )
            {
            	viewFlag=!viewFlag;
            }else
            //上下左右的虚拟按钮
        	if( (yRatio>upYMin&&yRatio<upYMax
               &&xRatio>upXMin&&xRatio<upXMax )&&robotgroup.robot.flag==true)
            {
        		keyState=1;//前进
            }//***********************后转身按钮***********************************************
            else if( ( yRatio>downYMin&&yRatio<downYMax
 	               &&xRatio>downXMin&&xRatio<downXMax) &&robotgroup.robot.flag==true)
            {//按向下虚拟按钮
				   if(robotgroup.yAngle==0)//机器人转变方向
	            	{
					   robotgroup.backRotate(0);//后转弯动画
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.backRotate(90);//后转弯动画
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.backRotate(180);//后转弯动画
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.backRotate(270);//后转弯动画
	            	}
            //***********************左转身按钮***********************************************
            }else if( (yRatio>leftYMin&&yRatio<leftYMax
 	               &&xRatio>leftXMin&&xRatio<leftXMax) &&robotgroup.robot.flag==true)
            {//按下向左虚拟按钮
				   if(robotgroup.yAngle==0)//机器人转变方向
	            	{
					   robotgroup.leftRotate(0);//后转弯动画
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.leftRotate(90);//后转弯动画
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.leftRotate(180);//后转弯动画
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.leftRotate(270);//后转弯动画
	            	}
            //***********************右转身按钮***********************************************	
            }else if( ( yRatio>rightYMin&&yRatio<rightYMax
 	               &&xRatio>rightXMin&&xRatio<rightXMax) &&robotgroup.robot.flag==true)
            {//按下向右虚拟按钮
            	
 				   if(robotgroup.yAngle==0)//机器人转变方向
	            	{
					   robotgroup.rightRotate(0);
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.rightRotate(90);
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.rightRotate(180);
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.rightRotate(270);
	            	}
 	        }

            break;
        case MotionEvent.ACTION_UP://抬起动作
        	keyState=0;//键盘状态
        	break;

        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//计算触控笔Y位移
            float dx = x - mPreviousX;//计算触控笔X位移
            
            yAngle += dx * TOUCH_SCALE_FACTOR;//仰角改变    
            xAngle += dy * TOUCH_SCALE_FACTOR;//方位角改变 
            if(xAngle<0)//限制，不可以看到反面
            {
            	xAngle=0;
            }
            if(xAngle>=90)
            {
            	xAngle=90;
            }
            if(xAngle<=-90)
            {
            	xAngle=-90;
            }
            requestRender();//重绘画面
            break;
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置    
        cx=(float)(Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//摄像机x坐标 
        cz=(float)(Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//摄像机z坐标 
        cy=(float)(Math.sin(Math.toRadians(xAngle))*DISTANCE);//摄像机y坐标 
        return true;
    }

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent e)
	{	
		if(keyCode==4){
			return false;//按下return键
		}
		
		if(flag)//只执行一次，为机器人的行数和列数赋值
		{
			tempRow=robotgroup.m;//得到当前关机器人所在的行数和列数
			tempCol=robotgroup.n;
			flag=false;
		}
		
		if((keyCode==19||keyCode==20||keyCode==21||keyCode==22)&&robotgroup.robot.flag==true)
		{//当按下的是上下左右键，并且动画没有进行，按键才会有效
			switch(keyCode)
			{
			   case 19://向上键代表前进
				 //按下向上虚拟按钮
				   if(robotgroup.yAngle==0)//z轴正方向运动
	        		{
					   zRightDirection();
	        		}
	        		else if(robotgroup.yAngle==180)//z轴负方向
	        		{
	        			znegativeDirection();
	        			
	        		}else if(robotgroup.yAngle==270)//x轴负方向
	        		{
	        			xnegativeDirection();
	        			
	        		}else if(robotgroup.yAngle==90)//x轴正方向
	        		{
	        			xRightDirection();
	        		}

			   break;
			   case 20://向下键代表后转身
				   if(robotgroup.yAngle==0)//机器人转变方向
	            	{
					   robotgroup.backRotate(0);//后转弯动画
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.backRotate(90);//后转弯动画
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.backRotate(180);//后转弯动画
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.backRotate(270);//后转弯动画
	            	}
				   
			   break;
			   case 21:  //向左代表左转身
				   if(robotgroup.yAngle==0)//机器人转变方向
	            	{
					   robotgroup.leftRotate(0);//后转弯动画
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.leftRotate(90);//后转弯动画
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.leftRotate(180);//后转弯动画
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.leftRotate(270);//后转弯动画
	            	}
				   
				   break;
			   case 22:  //向右代表右转身
				   if(robotgroup.yAngle==0)//机器人转变方向
	            	{
					   robotgroup.rightRotate(0);
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.rightRotate(90);
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.rightRotate(180);
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.rightRotate(270);
	            	}
				   
				   break;
			}
		}
    	return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
	{
		float ratio=0;
		
        public void onDrawFrame(GL10 gl) 
        {  
        	//------------视角变化后，第一视角和第三视角的视角大小-------------------
        	visualAngle(gl,ratio);
        	//------------视角变化后，第一视角和第三视角的视角大小-------------------
        	
        	//采用平滑着色
            gl.glShadeModel(GL10.GL_SMOOTH);            
        	//清除颜色缓存于深度缓存
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);        	
        	//设置当前矩阵为模式矩阵
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();    

            //-------------------视角变化后，第一视角和第三视角，摄像机的位置------------------
        	cameraPosition(gl);
        	//-------------------视角变化后，第一视角和第三视角，摄像机的位置------------------
            
            //顶点数组
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            //顶点坐标
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            //允许使用法向量数组
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            //启用纹理 
            gl.glEnable(GL10.GL_TEXTURE_2D);          
            
            
            //绘制地板
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            floor.drawSelf(gl, floorId);
            gl.glPopMatrix();
            
            //绘制墙面
            gl.glPushMatrix();
            gl.glTranslatef((-Constant.MAP[Constant.COUNT][0].length/2)*UNIT_SIZE, 0,-Constant.MAP[Constant.COUNT].length/2*UNIT_SIZE);
            wall.drawSelf(gl, wallId);
            walloutside.drawSelf(gl, wallId);//绘制墙体的最外层
            gl.glPopMatrix();
            
            
            
            //绘制木箱
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            cubegroup.drawSelf(gl);
            gl.glPopMatrix();
            
            //绘制矩形纹理（即地面和目标）
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            texturerectgroup.drawSelf(gl);
            gl.glPopMatrix();
            
            //绘制机器人
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            robotgroup.drawSelf(gl);
            gl.glPopMatrix();
            
            //绘制木箱运动动画
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            cubego.drawSelf(gl);
            gl.glPopMatrix();
            
            //绘制虚拟按钮
            draw_button(gl);
            
            
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//禁止顶点法向量
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//禁止顶点数组            
            gl.glDisable(GL10.GL_TEXTURE_2D);//禁止纹理
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//禁止顶点坐标 
            
            //-------------------------游戏结束,判读进入下一关或者是进入通关界面-------------------------------
            judgeGoToLastViewOrGoToNext();
            //-----------------------游戏结束,判读进入下一关或者是进入游戏通关界面-------------------------------
          
         }
        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
        	screenWidth=width;
        	screenHeight=height;
            //设置视窗大小及位置 
        	gl.glViewport(0, 0, width, height);
        	//设置当前矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //设置当前矩阵为单位矩阵
            gl.glLoadIdentity();
            //计算透视投影的比例
            ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {
            //关闭抗抖动 
        	gl.glDisable(GL10.GL_DITHER);
        	//设置特定Hint项目的模式，这里为设置为使用快速模式
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //设置为打开背面剪裁
    		gl.glEnable(GL10.GL_CULL_FACE);
    		 //设置着色模型为平滑着色   
            gl.glShadeModel(GL10.GL_SMOOTH);
            //开启混合   
            gl.glEnable(GL10.GL_BLEND); 
            //设置源混合因子与目标混合因子
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            //设置屏幕背景色黑色RGBA
            gl.glClearColor(0,0,0,0);           
            //启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);
            
            
            loadTexture(gl);//调用自己写的方法，加载本游戏用到的所有纹理                    
        	initObject();//本游戏用到的全部组件对象的初始化
            initSound();//初始化，并播放声音
            
            //创建并启动键盘监控线程
            kt=new KeyThread(father.gameView);
            kt.flag=true;
            kt.start();
        }
	 }
	//视角变化后，第一视角和第三视角的视角大小
	public void visualAngle(GL10 gl, float ratio)
	{
		if(viewFlag)//第一视角
    	{
    		gl.glFrustumf(-ratio*0.3f, ratio*0.3f, -1, 1, 0.45f, 100);
        	
    	}else//第三视角
    	{
    		//调用此方法计算产生透视投影矩阵
            gl.glFrustumf(-ratio, ratio, -1, 1, 3, 1000);
    	}
	}
	
	//视角变化后，第一视角和第三视角，摄像机的位置
	public void cameraPosition(GL10 gl)
	{
		if(viewFlag)//第一视角，永远更在机器人的后上方
        {
        	GLU.gluLookAt
          ( gl,
           (float) (robotgroup.positionx+6*Math.sin(Math.toRadians(robotgroup.yAngle+180))),//摄像机的位置
           8*CUBE_SIZE+0.3f,//3/2倍的木箱高度
           (float) (robotgroup.positionz+6*Math.cos(Math.toRadians(robotgroup.yAngle+180))), //机器人的后方
           
           (float) (robotgroup.positionx+0.5f*Math.sin(Math.toRadians(robotgroup.yAngle+180))),//摄像机看向的位置
           2*CUBE_SIZE+0.3f, 
           (float) (robotgroup.positionz+0.5f*Math.cos(Math.toRadians(robotgroup.yAngle+180))),
           0,
           1, 
           0);
        	
        }else//第三视角
        {	
        	GLU.gluLookAt
            (gl, cx,cy,cz, 0,0, 0,0,1, 0);//第三视角，永远看向原点
        }
	}
	
	//如果机器人向Z轴正方向运动
	public void zRightDirection()
	{
		tempRow+=1;//行数加1(将要运动到的行)
		
		switch(objectMap[tempRow][tempCol])//判断前方是什么，然后产生不同的结果
		{
		case 1://遇到墙
		case 6://遇到已经摆好的木箱（红色木箱），不执行走路动画
			tempRow=tempRow-1;//既然不能走，那么提前加的行数减回来
			break;
		case 2://遇到路
		case 3://遇到目标
		case 5://遇到机器人
			robotgroup.robot.rotate(0);//开启机器人走路动画
			break;
		case 4://遇到箱子
			if(objectMap[tempRow+1][tempCol]==1||
					objectMap[tempRow+1][tempCol]==4
					||objectMap[tempRow+1][tempCol]==6)//箱子将要被推到的地方是墙或者是推好和未推好的箱子
			{tempRow=tempRow-1;}//推不动箱子
			else if(objectMap[tempRow+1][tempCol]==3)//箱子将要推到的地方是目标点
			{
				
				robotgroup.robot.pushbox(0);//机器人推箱子动画
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//推箱子的声音
				}
				
				objectMap[tempRow][tempCol]=2;//以前有箱子的地方画路
				
				cubego.flag=true;
				cubego.m=tempRow;//当前木箱所在的位置绘制木箱
				cubego.n=tempCol;
				cubego.cubeGo(0);//播放箱子运动动画
				
			}else//箱子遇到的是路，机器人
			{
				robotgroup.robot.pushbox(0);//机器人推箱子动画
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//推箱子的声音
				}
				
				objectMap[tempRow][tempCol]=2;//以前有箱子的地方画路
				
				cubego.flag=true;
				cubego.m=tempRow;//当前木箱所在的位置绘制木箱
				cubego.n=tempCol;
				cubego.cubeGo(0);//播放箱子运动动画
			}
			break;
		}
		
		
	}
	//如果机器人向Z轴负方向运动
	public void znegativeDirection()
	{
		tempRow-=1;//行数减1(将要运动到的行)
		
		switch(objectMap[tempRow][tempCol])//判断前方是什么，然后产生不同的结果
		{
		case 1://遇到墙
		case 6://遇到已经摆好的木箱（红色木箱），不执行走路动画
			tempRow=tempRow+1;//既然不能走，那么提前加的行数减回来
			break;
		case 2://遇到路
		case 3://遇到目标
		case 5://遇到机器人
			robotgroup.robot.rotate(180);//开启机器人走路动画
			break;
		case 4://遇到箱子
			if(objectMap[tempRow-1][tempCol]==1||
					objectMap[tempRow-1][tempCol]==4
					||objectMap[tempRow-1][tempCol]==6)//箱子将要被推到的地方是墙或者是推好和未推好的箱子
			{tempRow=tempRow+1;}//推不动箱子
			else if(objectMap[tempRow-1][tempCol]==3)//箱子将要推到的地方是目标点
			{
				robotgroup.robot.pushbox(180);//机器人推箱子动画
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//推箱子的声音
				}
				
				objectMap[tempRow][tempCol]=2;//以前有箱子的地方画路
				
				cubego.flag=true;
				cubego.m=tempRow;//当前木箱所在的位置绘制木箱
				cubego.n=tempCol;
				cubego.cubeGo(180);//播放箱子运动动画
				
			}else//箱子遇到的是路，机器人
			{
				robotgroup.robot.pushbox(180);//机器人推箱子动画
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//推箱子的声音
				}
				
				objectMap[tempRow][tempCol]=2;//以前有箱子的地方画路
				   						
				cubego.flag=true;
				cubego.m=tempRow;//当前木箱所在的位置绘制木箱
				cubego.n=tempCol;
				cubego.cubeGo(180);//播放箱子运动动画
			}
			break;
		}
		
		
	}
	//如果机器人向x轴正方向运动
	public void xRightDirection()
	{
		tempCol+=1;//列数减1(将要运动到的列)
		switch(objectMap[tempRow][tempCol])//判断前方是什么，然后产生不同的结果
		{
		case 1://遇到墙
		case 6://遇到已经摆好的木箱（红色木箱），不执行走路动画
			tempCol=tempCol-1;//既然不能走，那么提前加的行数减回来
			break;
		case 2://遇到路
		case 3://遇到目标
		case 5://遇到机器人
			robotgroup.robot.rotate(90);//开启机器人走路动画
			break;
		case 4://遇到箱子
			if(objectMap[tempRow][tempCol+1]==1||
					objectMap[tempRow][tempCol+1]==4
					||objectMap[tempRow][tempCol+1]==6)//箱子将要被推到的地方是墙或者是推好和未推好的箱子
			{tempCol=tempCol-1;}//推不动箱子
			else if(objectMap[tempRow][tempCol+1]==3)//箱子将要推到的地方是目标点
			{
				robotgroup.robot.pushbox(90);//机器人推箱子动画
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//推箱子的声音
				}
				
				objectMap[tempRow][tempCol]=2;//以前有箱子的地方画路
				
				cubego.flag=true;
				cubego.m=tempRow;//当前木箱所在的位置绘制木箱
				cubego.n=tempCol;
				cubego.cubeGo(90);//播放箱子运动动画
				
			}else//箱子遇到的是路，机器人
			{
				robotgroup.robot.pushbox(90);//机器人推箱子动画
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//推箱子的声音
				}
				
				objectMap[tempRow][tempCol]=2;//以前有箱子的地方画路
				
				cubego.flag=true;
				cubego.m=tempRow;//当前木箱所在的位置绘制木箱
				cubego.n=tempCol;
				cubego.cubeGo(90);//播放箱子运动动画
			}
			break;
		}
		
		
	}
	//如果机器人向x轴负方向运动
	public void xnegativeDirection()
	{
		tempCol-=1;//列数减1(将要运动到的列)
		switch(objectMap[tempRow][tempCol])//判断前方是什么，然后产生不同的结果
		{
		case 1://遇到墙
		case 6://遇到已经摆好的木箱（红色木箱），不执行走路动画
			tempCol=tempCol+1;//既然不能走，那么提前加的行数减回来
			break;
		case 2://遇到路
		case 3://遇到目标
		case 5://遇到机器人
			robotgroup.robot.rotate(270);//开启机器人走路动画
			break;
		case 4://遇到箱子
			if(objectMap[tempRow][tempCol-1]==1||
					objectMap[tempRow][tempCol-1]==4
					||objectMap[tempRow][tempCol-1]==6)//箱子将要被推到的地方是墙或者是推好和未推好的箱子
			{tempCol=tempCol+1;}//推不动箱子
			else if(objectMap[tempRow][tempCol-1]==3)//箱子将要推到的地方是目标点
			{
				
				robotgroup.robot.pushbox(270);//机器人推箱子动画
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//推箱子的声音
				}
				
				objectMap[tempRow][tempCol]=2;//以前有箱子的地方画路
				
				cubego.flag=true;
				cubego.m=tempRow;//当前木箱所在的位置绘制木箱
				cubego.n=tempCol;
				cubego.cubeGo(270);//播放箱子运动动画
				
			}else//箱子遇到的是路，机器人
			{
				robotgroup.robot.pushbox(270);//机器人推箱子动画
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//推箱子的声音
				}
				
				objectMap[tempRow][tempCol]=2;//以前有箱子的地方画路
				
				cubego.flag=true;
				cubego.m=tempRow;//当前木箱所在的位置绘制木箱
				cubego.n=tempCol;
				cubego.cubeGo(270);//播放箱子运动动画
			}
			break;
		}
		
		
	}
	

	//绘制虚拟按钮
	public void draw_button(GL10 gl)
	{
		 gl.glMatrixMode(GL10.GL_MODELVIEW);
	     gl.glLoadIdentity(); 
	     gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	     gl.glEnable(GL10.GL_TEXTURE_2D);   
	     gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	     
	     //up
	     gl.glPushMatrix();
	     gl.glTranslatef(1.3f, -0.3f, -4);
	     buttonUp.drawSelf(gl);
	     gl.glPopMatrix();
	     //down
	     gl.glPushMatrix();
	     gl.glTranslatef(1.3f, -1.0f, -4);
	     buttonDown.drawSelf(gl);
	     gl.glPopMatrix();
	     //left
	     gl.glPushMatrix();
	     gl.glTranslatef(0.9f, -0.65f, -4);
	     buttonLeft.drawSelf(gl);
	     gl.glPopMatrix();
	     //right
	     gl.glPushMatrix();
	     gl.glTranslatef(1.7f, -0.65f, -4);
	     buttonRight.drawSelf(gl);
	     gl.glPopMatrix();
	     
	     gl.glPushMatrix();
	     gl.glTranslatef(1.3f, 1.0f, -4);
	     view.drawSelf(gl);
	     gl.glPopMatrix();
	     
	     gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	     gl.glDisable(GL10.GL_TEXTURE_2D);
	     gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	//加载本游戏用到的所有纹理
	public void loadTexture(GL10 gl)
	{
		floorId=initTexture(gl,R.drawable.floor);//地面纹理ID
        wallId=initTexture(gl,R.drawable.wall);    //墙纹理ID
        woodbinId0=initTexture(gl,R.drawable.wood_bin0);//木箱纹理ID
        woodbinId1=initTexture(gl,R.drawable.wood_bin1);//目标纹理ID
        targetId=initTexture(gl,R.drawable.target);//目标纹理ID
        robot_texId=initTexture(gl,R.drawable.robot_texid);//机器人纹理ID
        robot_head=initTexture(gl,R.drawable.robot_head);
        
        button_up=initTexture(gl,R.drawable.b_up);//上下左右键的纹理ID
        button_down=initTexture(gl,R.drawable.b_down);//上下左右键的纹理ID
        button_left=initTexture(gl,R.drawable.b_left);//上下左右键的纹理ID
        button_right=initTexture(gl,R.drawable.b_right);//上下左右键的纹理ID
        viewId=initTexture(gl,R.drawable.view);//视角纹理
        
		
	}
	//本游戏用到的全部组件对象的初始化
	public void initObject()
	{
		//组件对象
		
        floor=new Floor((int)(-Constant.MAP[Constant.COUNT][0].length/2),(int)(-Constant.MAP[Constant.COUNT].length/2),
        		1,0,Constant.MAP[Constant.COUNT][0].length,Constant.MAP[Constant.COUNT].length);//地板类的对象
        wall=new Wall();//墙类的对象
        walloutside=new WallOutSide();//墙体的最外层类的对象
		
		cubegroup=new CubeGroup(father.gameView,woodbinId0,woodbinId1);//木箱组类的对象
        texturerectgroup=new TextureRectGroup(father.gameView,targetId);//（目标组）矩形纹理组类的对象
        robotgroup=new RobotGroup(father.gameView,robot_texId,robot_head);
        cubego=new CubeGo(father.gameView,Constant.CUBE_SIZE,woodbinId0);
        
        //虚拟按钮纹理对象
        buttonUp=new TextureRect(0.25f,0.25f,button_up);//虚拟按钮类的声明
        buttonDown=new TextureRect(0.25f,0.25f,button_down);
        buttonLeft=new TextureRect(0.25f,0.25f,button_left);
        buttonRight=new TextureRect(0.25f,0.25f,button_right);
        view=new TextureRect(0.25f,0.25f,viewId);//视角纹理
	}
	//初始化声音
	public void initSound()
	{
		if(mMediaPlayer==null)
		{
			//背景音乐
			mMediaPlayer=MediaPlayer.create(father, R.raw.sound1);//创建背景音乐播放器
			mMediaPlayer.setLooping(true);//循环播放
			//开启背景音乐
			if(father.soundFlag)
			{
				mMediaPlayer.start();//开启音乐
			}
		}
		
	}
	//初始化纹理
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		
		//在MIN_FILTER MAG_FILTER中使用MIPMAP纹理
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR_MIPMAP_LINEAR);
		// 生成Mipmap纹理
		((GL11)gl).glTexParameterf(GL10.GL_TEXTURE_2D,GL11.GL_GENERATE_MIPMAP,GL10.GL_TRUE);
        //纹理拉伸方式
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
        
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp; 
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle(); 
        return currTextureId;
	}
	
	//当前关的游戏结束，关闭线程和声音等
	public void overGame()
	{
		boolean retry = true;
		kt.flag=false;
		//关闭所有线程
	    while (retry) {
	        try {
	        	kt.join();

	            retry = false;
	        } 
	        catch (InterruptedException e) {e.printStackTrace();}//不断地循环，直到其它线程结束
	    }
		
		if(mMediaPlayer.isPlaying())
		{
			mMediaPlayer.stop();//关闭媒体播放器
		}
	}
	//游戏结束，判断是进入下一关游戏，还是进入最后的通关界面
	public void judgeGoToLastViewOrGoToNext()
	{
		int row=0;
        int col=0;
        int targetCount=0;
        row=objectMap.length;
		col=objectMap[0].length;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				if(objectMap[i][j]==3)//目标数量
				{
					targetCount++;
				}
			}
		}
		
		if(Constant.COUNT==MAP.length-1&&targetCount==0)//游戏结束
		{
			overGame();
			father.sendMessage(3);//进入最后的界面
		}else if(Constant.COUNT<MAP.length-1&&targetCount==0)//目标的数量为0游戏结束
		{
			overGame();
			Constant.COUNT++;
			father.sendMessage(1);//进入下一关游戏界面
			
			//使主线程休眠一会
			try
			{
				Thread.sleep(100);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
