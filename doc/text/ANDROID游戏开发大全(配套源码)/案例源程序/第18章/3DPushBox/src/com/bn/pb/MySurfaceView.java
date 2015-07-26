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
	MyActivity father;//����Activity
	private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��Y����
    
    public float yAngle=0f;//��λ��
    public float xAngle=90f;//���� 
    public float cx;//�����x����
    public float cy;//�����y����
    public float cz;//�����z����
    
    public int floorId;//�ذ�����ID
    public int floor_backId;//�ذ�ķ�������
    public int wallId;//ǽ����
    public int woodbinId0;//ľ������Id
    public int woodbinId1;//��ɫľ������Id
    public int targetId;//Ŀ������Id
    public int robot_texId;//����������Id
    public int robot_head;//�����˵�ͷ����
    
    public int button_up;//�������Ҽ������ⰴť����Id
    public int button_down;
    public int button_left;
    public int button_right;
    public int viewId;//�ӽ�����
    
    public int tempRow=0;//�����˽�Ҫ�˶������к���
    public int tempCol=0;
    boolean flag=true;//ʹtempRow��tempCol�ĸ�ֵ���ִֻ��һ�εı�־λ
    boolean viewFlag=false;//������ӽǱ�־λ
    
    private SceneRenderer mRenderer;//������Ⱦ��
    
    public Floor floor;//�ذ���
	public Wall wall;//ǽ��
	public WallOutSide walloutside;//ǽ����������
	public CubeGroup cubegroup;//ľ������
	public TextureRectGroup texturerectgroup;//������������
	public RobotGroup robotgroup;//����������
	public CubeGo cubego;//ľ���߶��Ķ�����
	
	MediaPlayer mMediaPlayer;//�������ֲ�������
	
	public TextureRect buttonUp;//�������Ҽ����������
	public TextureRect buttonDown;
	public TextureRect buttonLeft;
	public TextureRect buttonRight;
	public TextureRect view;//�ӽ�����
	
	KeyThread kt;//���̼���߳�
	
	float screenWidth;//��Ļ���
    float screenHeight;//��Ļ�߶�
    
    int keyState=0;//����״̬  1-up
	
    //----------------------���Ƶ�ǰ�صĶ�ά��������-------------------------
	int objectMap[][];//���ڸ��Ƶ�ǰ�صĵ�ͼ�Ķ�ά����
	int rows=0;//��ǰ��ά���������
	int cols=0;//��ǰ��ά���������
	//----------------------���Ƶ�ǰ�صĶ�ά��������-------------------------
    
    
	
	public MySurfaceView(Context context)
	{
        super(context);
        this.father=(MyActivity)context;        
        
        
        
        //�����������λ��
        cx=(float)(Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//�����x���� 
        cz=(float)(Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//�����z���� 
        cy=(float)(Math.sin(Math.toRadians(xAngle))*DISTANCE);//�����y���� 
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ    
        
        
        //----------------------���Ƶ�ǰ�صĶ�ά��������-------------------------
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
		//----------------------���Ƶ�ǰ�صĶ�ά��������-------------------------
        
        
    }
	
	@Override 
    public boolean onTouchEvent(MotionEvent e) 
	{
        float y = e.getY();
        float x = e.getX();
        
        switch (e.getAction())
        {
        case MotionEvent.ACTION_DOWN://���¶���
        	float yRatio=y/screenHeight;
            float xRatio=x/screenWidth;
            
            if(flag)//������Ϸ�����У��ξ�ִֻ��һ��
			{
            	tempRow=robotgroup.m;//�õ���ǰ�ػ��������ڵ�����������
    			tempCol=robotgroup.n;
				flag=false;
			}
            
            //***********************ǰ����ť***********************************************
            //�ӽǱ任���ⰴť
            if( yRatio>viewYMin&&yRatio<viewYMax&&xRatio>viewXMin&&xRatio<viewXMax )
            {
            	viewFlag=!viewFlag;
            }else
            //�������ҵ����ⰴť
        	if( (yRatio>upYMin&&yRatio<upYMax
               &&xRatio>upXMin&&xRatio<upXMax )&&robotgroup.robot.flag==true)
            {
        		keyState=1;//ǰ��
            }//***********************��ת��ť***********************************************
            else if( ( yRatio>downYMin&&yRatio<downYMax
 	               &&xRatio>downXMin&&xRatio<downXMax) &&robotgroup.robot.flag==true)
            {//���������ⰴť
				   if(robotgroup.yAngle==0)//������ת�䷽��
	            	{
					   robotgroup.backRotate(0);//��ת�䶯��
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.backRotate(90);//��ת�䶯��
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.backRotate(180);//��ת�䶯��
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.backRotate(270);//��ת�䶯��
	            	}
            //***********************��ת��ť***********************************************
            }else if( (yRatio>leftYMin&&yRatio<leftYMax
 	               &&xRatio>leftXMin&&xRatio<leftXMax) &&robotgroup.robot.flag==true)
            {//�����������ⰴť
				   if(robotgroup.yAngle==0)//������ת�䷽��
	            	{
					   robotgroup.leftRotate(0);//��ת�䶯��
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.leftRotate(90);//��ת�䶯��
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.leftRotate(180);//��ת�䶯��
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.leftRotate(270);//��ת�䶯��
	            	}
            //***********************��ת��ť***********************************************	
            }else if( ( yRatio>rightYMin&&yRatio<rightYMax
 	               &&xRatio>rightXMin&&xRatio<rightXMax) &&robotgroup.robot.flag==true)
            {//�����������ⰴť
            	
 				   if(robotgroup.yAngle==0)//������ת�䷽��
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
        case MotionEvent.ACTION_UP://̧����
        	keyState=0;//����״̬
        	break;

        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
            
            yAngle += dx * TOUCH_SCALE_FACTOR;//���Ǹı�    
            xAngle += dy * TOUCH_SCALE_FACTOR;//��λ�Ǹı� 
            if(xAngle<0)//���ƣ������Կ�������
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
            requestRender();//�ػ滭��
            break;
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��    
        cx=(float)(Math.cos(Math.toRadians(xAngle))*Math.sin(Math.toRadians(yAngle))*DISTANCE);//�����x���� 
        cz=(float)(Math.cos(Math.toRadians(xAngle))*Math.cos(Math.toRadians(yAngle))*DISTANCE);//�����z���� 
        cy=(float)(Math.sin(Math.toRadians(xAngle))*DISTANCE);//�����y���� 
        return true;
    }

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent e)
	{	
		if(keyCode==4){
			return false;//����return��
		}
		
		if(flag)//ִֻ��һ�Σ�Ϊ�����˵�������������ֵ
		{
			tempRow=robotgroup.m;//�õ���ǰ�ػ��������ڵ�����������
			tempCol=robotgroup.n;
			flag=false;
		}
		
		if((keyCode==19||keyCode==20||keyCode==21||keyCode==22)&&robotgroup.robot.flag==true)
		{//�����µ����������Ҽ������Ҷ���û�н��У������Ż���Ч
			switch(keyCode)
			{
			   case 19://���ϼ�����ǰ��
				 //�����������ⰴť
				   if(robotgroup.yAngle==0)//z���������˶�
	        		{
					   zRightDirection();
	        		}
	        		else if(robotgroup.yAngle==180)//z�Ḻ����
	        		{
	        			znegativeDirection();
	        			
	        		}else if(robotgroup.yAngle==270)//x�Ḻ����
	        		{
	        			xnegativeDirection();
	        			
	        		}else if(robotgroup.yAngle==90)//x��������
	        		{
	        			xRightDirection();
	        		}

			   break;
			   case 20://���¼������ת��
				   if(robotgroup.yAngle==0)//������ת�䷽��
	            	{
					   robotgroup.backRotate(0);//��ת�䶯��
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.backRotate(90);//��ת�䶯��
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.backRotate(180);//��ת�䶯��
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.backRotate(270);//��ת�䶯��
	            	}
				   
			   break;
			   case 21:  //���������ת��
				   if(robotgroup.yAngle==0)//������ת�䷽��
	            	{
					   robotgroup.leftRotate(0);//��ת�䶯��
	            	}else if(robotgroup.yAngle==90)
	            	{
	            		robotgroup.leftRotate(90);//��ת�䶯��
	            	}else if(robotgroup.yAngle==180)
	            	{
	            		robotgroup.leftRotate(180);//��ת�䶯��
	            	}else if(robotgroup.yAngle==270)
	            	{
	            		robotgroup.leftRotate(270);//��ת�䶯��
	            	}
				   
				   break;
			   case 22:  //���Ҵ�����ת��
				   if(robotgroup.yAngle==0)//������ת�䷽��
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
        	//------------�ӽǱ仯�󣬵�һ�ӽǺ͵����ӽǵ��ӽǴ�С-------------------
        	visualAngle(gl,ratio);
        	//------------�ӽǱ仯�󣬵�һ�ӽǺ͵����ӽǵ��ӽǴ�С-------------------
        	
        	//����ƽ����ɫ
            gl.glShadeModel(GL10.GL_SMOOTH);            
        	//�����ɫ��������Ȼ���
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);        	
        	//���õ�ǰ����Ϊģʽ����
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();    

            //-------------------�ӽǱ仯�󣬵�һ�ӽǺ͵����ӽǣ��������λ��------------------
        	cameraPosition(gl);
        	//-------------------�ӽǱ仯�󣬵�һ�ӽǺ͵����ӽǣ��������λ��------------------
            
            //��������
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            //��������
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            //����ʹ�÷���������
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            //�������� 
            gl.glEnable(GL10.GL_TEXTURE_2D);          
            
            
            //���Ƶذ�
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            floor.drawSelf(gl, floorId);
            gl.glPopMatrix();
            
            //����ǽ��
            gl.glPushMatrix();
            gl.glTranslatef((-Constant.MAP[Constant.COUNT][0].length/2)*UNIT_SIZE, 0,-Constant.MAP[Constant.COUNT].length/2*UNIT_SIZE);
            wall.drawSelf(gl, wallId);
            walloutside.drawSelf(gl, wallId);//����ǽ��������
            gl.glPopMatrix();
            
            
            
            //����ľ��
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            cubegroup.drawSelf(gl);
            gl.glPopMatrix();
            
            //���ƾ��������������Ŀ�꣩
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            texturerectgroup.drawSelf(gl);
            gl.glPopMatrix();
            
            //���ƻ�����
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            robotgroup.drawSelf(gl);
            gl.glPopMatrix();
            
            //����ľ���˶�����
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            cubego.drawSelf(gl);
            gl.glPopMatrix();
            
            //�������ⰴť
            draw_button(gl);
            
            
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//��ֹ���㷨����
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//��ֹ��������            
            gl.glDisable(GL10.GL_TEXTURE_2D);//��ֹ����
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//��ֹ�������� 
            
            //-------------------------��Ϸ����,�ж�������һ�ػ����ǽ���ͨ�ؽ���-------------------------------
            judgeGoToLastViewOrGoToNext();
            //-----------------------��Ϸ����,�ж�������һ�ػ����ǽ�����Ϸͨ�ؽ���-------------------------------
          
         }
        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
        	screenWidth=width;
        	screenHeight=height;
            //�����Ӵ���С��λ�� 
        	gl.glViewport(0, 0, width, height);
        	//���õ�ǰ����ΪͶӰ����
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();
            //����͸��ͶӰ�ı���
            ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config) 
        {
            //�رտ����� 
        	gl.glDisable(GL10.GL_DITHER);
        	//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //����Ϊ�򿪱������
    		gl.glEnable(GL10.GL_CULL_FACE);
    		 //������ɫģ��Ϊƽ����ɫ   
            gl.glShadeModel(GL10.GL_SMOOTH);
            //�������   
            gl.glEnable(GL10.GL_BLEND); 
            //����Դ���������Ŀ��������
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            //������Ļ����ɫ��ɫRGBA
            gl.glClearColor(0,0,0,0);           
            //������Ȳ���
            gl.glEnable(GL10.GL_DEPTH_TEST);
            
            
            loadTexture(gl);//�����Լ�д�ķ��������ر���Ϸ�õ�����������                    
        	initObject();//����Ϸ�õ���ȫ���������ĳ�ʼ��
            initSound();//��ʼ��������������
            
            //�������������̼���߳�
            kt=new KeyThread(father.gameView);
            kt.flag=true;
            kt.start();
        }
	 }
	//�ӽǱ仯�󣬵�һ�ӽǺ͵����ӽǵ��ӽǴ�С
	public void visualAngle(GL10 gl, float ratio)
	{
		if(viewFlag)//��һ�ӽ�
    	{
    		gl.glFrustumf(-ratio*0.3f, ratio*0.3f, -1, 1, 0.45f, 100);
        	
    	}else//�����ӽ�
    	{
    		//���ô˷����������͸��ͶӰ����
            gl.glFrustumf(-ratio, ratio, -1, 1, 3, 1000);
    	}
	}
	
	//�ӽǱ仯�󣬵�һ�ӽǺ͵����ӽǣ��������λ��
	public void cameraPosition(GL10 gl)
	{
		if(viewFlag)//��һ�ӽǣ���Զ���ڻ����˵ĺ��Ϸ�
        {
        	GLU.gluLookAt
          ( gl,
           (float) (robotgroup.positionx+6*Math.sin(Math.toRadians(robotgroup.yAngle+180))),//�������λ��
           8*CUBE_SIZE+0.3f,//3/2����ľ��߶�
           (float) (robotgroup.positionz+6*Math.cos(Math.toRadians(robotgroup.yAngle+180))), //�����˵ĺ�
           
           (float) (robotgroup.positionx+0.5f*Math.sin(Math.toRadians(robotgroup.yAngle+180))),//����������λ��
           2*CUBE_SIZE+0.3f, 
           (float) (robotgroup.positionz+0.5f*Math.cos(Math.toRadians(robotgroup.yAngle+180))),
           0,
           1, 
           0);
        	
        }else//�����ӽ�
        {	
        	GLU.gluLookAt
            (gl, cx,cy,cz, 0,0, 0,0,1, 0);//�����ӽǣ���Զ����ԭ��
        }
	}
	
	//�����������Z���������˶�
	public void zRightDirection()
	{
		tempRow+=1;//������1(��Ҫ�˶�������)
		
		switch(objectMap[tempRow][tempCol])//�ж�ǰ����ʲô��Ȼ�������ͬ�Ľ��
		{
		case 1://����ǽ
		case 6://�����Ѿ��ںõ�ľ�䣨��ɫľ�䣩����ִ����·����
			tempRow=tempRow-1;//��Ȼ�����ߣ���ô��ǰ�ӵ�����������
			break;
		case 2://����·
		case 3://����Ŀ��
		case 5://����������
			robotgroup.robot.rotate(0);//������������·����
			break;
		case 4://��������
			if(objectMap[tempRow+1][tempCol]==1||
					objectMap[tempRow+1][tempCol]==4
					||objectMap[tempRow+1][tempCol]==6)//���ӽ�Ҫ���Ƶ��ĵط���ǽ�������ƺú�δ�ƺõ�����
			{tempRow=tempRow-1;}//�Ʋ�������
			else if(objectMap[tempRow+1][tempCol]==3)//���ӽ�Ҫ�Ƶ��ĵط���Ŀ���
			{
				
				robotgroup.robot.pushbox(0);//�����������Ӷ���
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//�����ӵ�����
				}
				
				objectMap[tempRow][tempCol]=2;//��ǰ�����ӵĵط���·
				
				cubego.flag=true;
				cubego.m=tempRow;//��ǰľ�����ڵ�λ�û���ľ��
				cubego.n=tempCol;
				cubego.cubeGo(0);//���������˶�����
				
			}else//������������·��������
			{
				robotgroup.robot.pushbox(0);//�����������Ӷ���
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//�����ӵ�����
				}
				
				objectMap[tempRow][tempCol]=2;//��ǰ�����ӵĵط���·
				
				cubego.flag=true;
				cubego.m=tempRow;//��ǰľ�����ڵ�λ�û���ľ��
				cubego.n=tempCol;
				cubego.cubeGo(0);//���������˶�����
			}
			break;
		}
		
		
	}
	//�����������Z�Ḻ�����˶�
	public void znegativeDirection()
	{
		tempRow-=1;//������1(��Ҫ�˶�������)
		
		switch(objectMap[tempRow][tempCol])//�ж�ǰ����ʲô��Ȼ�������ͬ�Ľ��
		{
		case 1://����ǽ
		case 6://�����Ѿ��ںõ�ľ�䣨��ɫľ�䣩����ִ����·����
			tempRow=tempRow+1;//��Ȼ�����ߣ���ô��ǰ�ӵ�����������
			break;
		case 2://����·
		case 3://����Ŀ��
		case 5://����������
			robotgroup.robot.rotate(180);//������������·����
			break;
		case 4://��������
			if(objectMap[tempRow-1][tempCol]==1||
					objectMap[tempRow-1][tempCol]==4
					||objectMap[tempRow-1][tempCol]==6)//���ӽ�Ҫ���Ƶ��ĵط���ǽ�������ƺú�δ�ƺõ�����
			{tempRow=tempRow+1;}//�Ʋ�������
			else if(objectMap[tempRow-1][tempCol]==3)//���ӽ�Ҫ�Ƶ��ĵط���Ŀ���
			{
				robotgroup.robot.pushbox(180);//�����������Ӷ���
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//�����ӵ�����
				}
				
				objectMap[tempRow][tempCol]=2;//��ǰ�����ӵĵط���·
				
				cubego.flag=true;
				cubego.m=tempRow;//��ǰľ�����ڵ�λ�û���ľ��
				cubego.n=tempCol;
				cubego.cubeGo(180);//���������˶�����
				
			}else//������������·��������
			{
				robotgroup.robot.pushbox(180);//�����������Ӷ���
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//�����ӵ�����
				}
				
				objectMap[tempRow][tempCol]=2;//��ǰ�����ӵĵط���·
				   						
				cubego.flag=true;
				cubego.m=tempRow;//��ǰľ�����ڵ�λ�û���ľ��
				cubego.n=tempCol;
				cubego.cubeGo(180);//���������˶�����
			}
			break;
		}
		
		
	}
	//�����������x���������˶�
	public void xRightDirection()
	{
		tempCol+=1;//������1(��Ҫ�˶�������)
		switch(objectMap[tempRow][tempCol])//�ж�ǰ����ʲô��Ȼ�������ͬ�Ľ��
		{
		case 1://����ǽ
		case 6://�����Ѿ��ںõ�ľ�䣨��ɫľ�䣩����ִ����·����
			tempCol=tempCol-1;//��Ȼ�����ߣ���ô��ǰ�ӵ�����������
			break;
		case 2://����·
		case 3://����Ŀ��
		case 5://����������
			robotgroup.robot.rotate(90);//������������·����
			break;
		case 4://��������
			if(objectMap[tempRow][tempCol+1]==1||
					objectMap[tempRow][tempCol+1]==4
					||objectMap[tempRow][tempCol+1]==6)//���ӽ�Ҫ���Ƶ��ĵط���ǽ�������ƺú�δ�ƺõ�����
			{tempCol=tempCol-1;}//�Ʋ�������
			else if(objectMap[tempRow][tempCol+1]==3)//���ӽ�Ҫ�Ƶ��ĵط���Ŀ���
			{
				robotgroup.robot.pushbox(90);//�����������Ӷ���
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//�����ӵ�����
				}
				
				objectMap[tempRow][tempCol]=2;//��ǰ�����ӵĵط���·
				
				cubego.flag=true;
				cubego.m=tempRow;//��ǰľ�����ڵ�λ�û���ľ��
				cubego.n=tempCol;
				cubego.cubeGo(90);//���������˶�����
				
			}else//������������·��������
			{
				robotgroup.robot.pushbox(90);//�����������Ӷ���
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//�����ӵ�����
				}
				
				objectMap[tempRow][tempCol]=2;//��ǰ�����ӵĵط���·
				
				cubego.flag=true;
				cubego.m=tempRow;//��ǰľ�����ڵ�λ�û���ľ��
				cubego.n=tempCol;
				cubego.cubeGo(90);//���������˶�����
			}
			break;
		}
		
		
	}
	//�����������x�Ḻ�����˶�
	public void xnegativeDirection()
	{
		tempCol-=1;//������1(��Ҫ�˶�������)
		switch(objectMap[tempRow][tempCol])//�ж�ǰ����ʲô��Ȼ�������ͬ�Ľ��
		{
		case 1://����ǽ
		case 6://�����Ѿ��ںõ�ľ�䣨��ɫľ�䣩����ִ����·����
			tempCol=tempCol+1;//��Ȼ�����ߣ���ô��ǰ�ӵ�����������
			break;
		case 2://����·
		case 3://����Ŀ��
		case 5://����������
			robotgroup.robot.rotate(270);//������������·����
			break;
		case 4://��������
			if(objectMap[tempRow][tempCol-1]==1||
					objectMap[tempRow][tempCol-1]==4
					||objectMap[tempRow][tempCol-1]==6)//���ӽ�Ҫ���Ƶ��ĵط���ǽ�������ƺú�δ�ƺõ�����
			{tempCol=tempCol+1;}//�Ʋ�������
			else if(objectMap[tempRow][tempCol-1]==3)//���ӽ�Ҫ�Ƶ��ĵط���Ŀ���
			{
				
				robotgroup.robot.pushbox(270);//�����������Ӷ���
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//�����ӵ�����
				}
				
				objectMap[tempRow][tempCol]=2;//��ǰ�����ӵĵط���·
				
				cubego.flag=true;
				cubego.m=tempRow;//��ǰľ�����ڵ�λ�û���ľ��
				cubego.n=tempCol;
				cubego.cubeGo(270);//���������˶�����
				
			}else//������������·��������
			{
				robotgroup.robot.pushbox(270);//�����������Ӷ���
				if(father.soundPoolFlag)
				{
					father.playSound(1, 0);//�����ӵ�����
				}
				
				objectMap[tempRow][tempCol]=2;//��ǰ�����ӵĵط���·
				
				cubego.flag=true;
				cubego.m=tempRow;//��ǰľ�����ڵ�λ�û���ľ��
				cubego.n=tempCol;
				cubego.cubeGo(270);//���������˶�����
			}
			break;
		}
		
		
	}
	

	//�������ⰴť
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
	
	//���ر���Ϸ�õ�����������
	public void loadTexture(GL10 gl)
	{
		floorId=initTexture(gl,R.drawable.floor);//��������ID
        wallId=initTexture(gl,R.drawable.wall);    //ǽ����ID
        woodbinId0=initTexture(gl,R.drawable.wood_bin0);//ľ������ID
        woodbinId1=initTexture(gl,R.drawable.wood_bin1);//Ŀ������ID
        targetId=initTexture(gl,R.drawable.target);//Ŀ������ID
        robot_texId=initTexture(gl,R.drawable.robot_texid);//����������ID
        robot_head=initTexture(gl,R.drawable.robot_head);
        
        button_up=initTexture(gl,R.drawable.b_up);//�������Ҽ�������ID
        button_down=initTexture(gl,R.drawable.b_down);//�������Ҽ�������ID
        button_left=initTexture(gl,R.drawable.b_left);//�������Ҽ�������ID
        button_right=initTexture(gl,R.drawable.b_right);//�������Ҽ�������ID
        viewId=initTexture(gl,R.drawable.view);//�ӽ�����
        
		
	}
	//����Ϸ�õ���ȫ���������ĳ�ʼ��
	public void initObject()
	{
		//�������
		
        floor=new Floor((int)(-Constant.MAP[Constant.COUNT][0].length/2),(int)(-Constant.MAP[Constant.COUNT].length/2),
        		1,0,Constant.MAP[Constant.COUNT][0].length,Constant.MAP[Constant.COUNT].length);//�ذ���Ķ���
        wall=new Wall();//ǽ��Ķ���
        walloutside=new WallOutSide();//ǽ����������Ķ���
		
		cubegroup=new CubeGroup(father.gameView,woodbinId0,woodbinId1);//ľ������Ķ���
        texturerectgroup=new TextureRectGroup(father.gameView,targetId);//��Ŀ���飩������������Ķ���
        robotgroup=new RobotGroup(father.gameView,robot_texId,robot_head);
        cubego=new CubeGo(father.gameView,Constant.CUBE_SIZE,woodbinId0);
        
        //���ⰴť�������
        buttonUp=new TextureRect(0.25f,0.25f,button_up);//���ⰴť�������
        buttonDown=new TextureRect(0.25f,0.25f,button_down);
        buttonLeft=new TextureRect(0.25f,0.25f,button_left);
        buttonRight=new TextureRect(0.25f,0.25f,button_right);
        view=new TextureRect(0.25f,0.25f,viewId);//�ӽ�����
	}
	//��ʼ������
	public void initSound()
	{
		if(mMediaPlayer==null)
		{
			//��������
			mMediaPlayer=MediaPlayer.create(father, R.raw.sound1);//�����������ֲ�����
			mMediaPlayer.setLooping(true);//ѭ������
			//������������
			if(father.soundFlag)
			{
				mMediaPlayer.start();//��������
			}
		}
		
	}
	//��ʼ������
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//��������ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		
		//��MIN_FILTER MAG_FILTER��ʹ��MIPMAP����
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR_MIPMAP_LINEAR);
		// ����Mipmap����
		((GL11)gl).glTexParameterf(GL10.GL_TEXTURE_2D,GL11.GL_GENERATE_MIPMAP,GL10.GL_TRUE);
        //�������췽ʽ
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
	
	//��ǰ�ص���Ϸ�������ر��̺߳�������
	public void overGame()
	{
		boolean retry = true;
		kt.flag=false;
		//�ر������߳�
	    while (retry) {
	        try {
	        	kt.join();

	            retry = false;
	        } 
	        catch (InterruptedException e) {e.printStackTrace();}//���ϵ�ѭ����ֱ�������߳̽���
	    }
		
		if(mMediaPlayer.isPlaying())
		{
			mMediaPlayer.stop();//�ر�ý�岥����
		}
	}
	//��Ϸ�������ж��ǽ�����һ����Ϸ�����ǽ�������ͨ�ؽ���
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
				if(objectMap[i][j]==3)//Ŀ������
				{
					targetCount++;
				}
			}
		}
		
		if(Constant.COUNT==MAP.length-1&&targetCount==0)//��Ϸ����
		{
			overGame();
			father.sendMessage(3);//�������Ľ���
		}else if(Constant.COUNT<MAP.length-1&&targetCount==0)//Ŀ�������Ϊ0��Ϸ����
		{
			overGame();
			Constant.COUNT++;
			father.sendMessage(1);//������һ����Ϸ����
			
			//ʹ���߳�����һ��
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
