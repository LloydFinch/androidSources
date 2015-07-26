package com.bn.game.chap11.ex3;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLView extends GLSurfaceView{
	private final float TOUCH_SCALE_FACTOR = 30.0f/480;//�Ƕ����ű���
	private SceneRenderer renderer;//������Ⱦ��
	private float previousX;//�ϴεĴ���λ��
	private float previousY;  
	private boolean perspectiveFlag=false;//͸��ͶӰ��־λ	
	float ratio;//��Ļ��߱�
	float xAngle;
	float yAngle;
	public MyGLView(Context context) {
		super(context);
		renderer=new SceneRenderer();//������Ⱦ��
		this.setRenderer(renderer);//������Ⱦ��
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
	}
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - previousY;//���㴥�ر�Yλ��
            float dx = x - previousX;//���㴥�ر�Yλ��   
            yAngle += dx * TOUCH_SCALE_FACTOR;//������y����ת�Ƕ�
            xAngle+= dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            requestRender();//�ػ滭��
        }
        previousY = y;//��¼���ر�λ��
        previousX = x;//��¼���ر�λ��
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer {
		Circle[] circles=new Circle[6];
		
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {  
         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//���õ�ǰ����ΪͶӰ����            
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����            
			if(perspectiveFlag){
            	//���ô˷����������͸��ͶӰ����
                gl.glFrustumf(-ratio, ratio, -1, 1, 13.5f, 100);
            }
            else{
            	//���ô˷��������������ͶӰ����
            	gl.glOrthof(-ratio, ratio, -1, 1, 1.5f, 100);
            }
        	gl.glEnable(GL10.GL_CULL_FACE);//����Ϊ�򿪱������
        	gl.glShadeModel(GL10.GL_SMOOTH);//������ɫģ��Ϊƽ����ɫ   
        	gl.glFrontFace(GL10.GL_CCW);//����ΪĬ�Ͼ���˳�򡪡���ʱ��Ϊ����
        	
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//�����ɫ����Ȼ���
            gl.glMatrixMode(GL10.GL_MODELVIEW);//���õ�ǰ����Ϊģʽ����
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����     
            gl.glTranslatef(0, 0f, -15f);
            gl.glRotatef(xAngle, 1, 0, 0);
            gl.glRotatef(yAngle, 0, 1, 0);
            
            for(Circle c:renderer.circles){
            	c.drawSelf(gl);
            }
		}
		
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //�����Ӵ���С��λ��         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//���õ�ǰ����ΪͶӰ����            
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����            
            ratio = (float) width / height;//����͸��ͶӰ�ı���    
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {            
        	gl.glDisable(GL10.GL_DITHER);//�رտ�����         	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//�����ض�Hint��Ŀ��ģʽ          
            gl.glClearColor(0,0,0,0); //������Ļ����ɫ��ɫRGBA    
            gl.glEnable(GL10.GL_DEPTH_TEST); //������Ȳ���     
            for(int i=0;i<circles.length;i++){//��������Բ
            	circles[i]=new Circle(0.3f,1,8,-i*3f);
            }
		}		
	}//SceneRenderer
	public boolean isPerspectiveFlag() {
		return perspectiveFlag;
	}
	public void setPerspectiveFlag(boolean perspectiveFlag) {
		this.perspectiveFlag = perspectiveFlag;
	}
}
