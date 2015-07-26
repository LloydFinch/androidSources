package com.bn.game.chap11.ex1;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLView extends GLSurfaceView{
	private final float TOUCH_SCALE_FACTOR = 180.0f/480;//�Ƕ����ű���
	private SceneRenderer renderer;//������Ⱦ��
	private float previousX;//�ϴεĴ���λ��
	private float previousY;  
	private boolean cullFaceFlag=false;//������ñ�־λ
	private boolean smoothFlag=false;//ƽ����ɫ��־λ
	private boolean cwFlag=false;//�Զ�����Ʊ�־λ	
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
            renderer.diamond.yAngle += dx * TOUCH_SCALE_FACTOR;//������y����ת�Ƕ�
            renderer.diamond.xAngle+= dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            requestRender();//�ػ滭��
        }
        previousY = y;//��¼���ر�λ��
        previousX = x;//��¼���ر�λ��
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer {
		
		Diamond diamond=new Diamond(1,1,1.5f);
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {
        	if(cullFaceFlag){//������ñ�־λ
        		gl.glEnable(GL10.GL_CULL_FACE);//����Ϊ�򿪱������
        	}
        	else{
        		gl.glDisable(GL10.GL_CULL_FACE);//����Ϊ�رձ������
        	}
        	if(smoothFlag){//ƽ����ɫ��־λ
                gl.glShadeModel(GL10.GL_SMOOTH);//������ɫģ��Ϊƽ����ɫ   
        	}
        	else{
        		gl.glShadeModel(GL10.GL_FLAT);//������ɫģ��Ϊ��ƽ����ɫ   
        	}
        	if(cwFlag){//�Զ�����Ʊ�־λ
        		gl.glFrontFace(GL10.GL_CW);//����Ϊ�Զ������˳�򡪡�˳ʱ��Ϊ����
        	}
        	else{
        		gl.glFrontFace(GL10.GL_CCW);//����ΪĬ�Ͼ���˳�򡪡���ʱ��Ϊ����
        	}
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//�����ɫ����Ȼ���
            gl.glMatrixMode(GL10.GL_MODELVIEW);//���õ�ǰ����Ϊģʽ����
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����     
            gl.glTranslatef(0, 0f, -4f);  
            diamond.drawSelf(gl);
		}
		
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //�����Ӵ���С��λ��         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//���õ�ǰ����ΪͶӰ����            
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����            
            float ratio = (float) width / height;//����͸��ͶӰ�ı���            
            gl.glFrustumf(-ratio, ratio, -1, 1, 1.5f, 10);//���ô˷����������͸��ͶӰ����
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {            
        	gl.glDisable(GL10.GL_DITHER);//�رտ�����         	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//�����ض�Hint��Ŀ��ģʽ          
            gl.glClearColor(0,0,0,0); //������Ļ����ɫ��ɫRGBA    
            gl.glEnable(GL10.GL_DEPTH_TEST); //������Ȳ���        
		}		
	}//SceneRenderer
	
	public boolean isCullFaceFlag() {
		return cullFaceFlag;
	}
	public void setCullFaceFlag(boolean cullFaceFlag) {
		this.cullFaceFlag = cullFaceFlag;
	}
	
	public boolean isSmoothFlag() {
		return smoothFlag;
	}
	public void setSmoothFlag(boolean smoothFlag) {
		this.smoothFlag = smoothFlag;
	}
	
	public boolean isCwFlag() {
		return cwFlag;
	}
	public void setCwFlag(boolean cwFlag) {
		this.cwFlag = cwFlag;
	}
}
