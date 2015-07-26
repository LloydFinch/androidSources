package com.bn.game.chap11.ex4;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLView extends GLSurfaceView{
	private SceneRenderer renderer;//������Ⱦ��
	private boolean isPosition=true;//�Ƿ�Ϊ��λ��ı�־λ
	private float lightOffset=-4;//�ƹ��λ�û����ƫ����	
	public MyGLView(Context context) {
		super(context);
		renderer=new SceneRenderer();//������Ⱦ��
		this.setRenderer(renderer);//������Ⱦ��
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
	}
	private class SceneRenderer implements GLSurfaceView.Renderer {
		Ball ball=new Ball(1.0f,0.5f,80,80);
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {
	        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//�����ɫ����Ȼ���
	            gl.glMatrixMode(GL10.GL_MODELVIEW);//���õ�ǰ����Ϊģʽ����
	            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����
	            gl.glTranslatef(0, 0f, -7f);
	        	if(isPosition){//�趨��λ���λ��
	        		float[] positionParams={0,0,lightOffset,1};//z���ϵĶ�λ��
	        		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParams,0);                
	        	}
	        	else{//�趨�����ķ���
	        		float[] directionParams={0,lightOffset,0,0};//��y��ĳ����Ķ����
	        		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, directionParams,0);        		
	        	} 
	        	float xyDis=1.7f;//����z��ľ���
	        	float xyDis1=1.7f/1.414f;
	        	
	        	gl.glPushMatrix();//�����������
	        	gl.glTranslatef(xyDis, 0f, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//�����������
	        	gl.glTranslatef(-xyDis, 0f, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//�����������
	        	gl.glTranslatef( 0f,xyDis, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//�����������
	        	gl.glTranslatef( 0f,-xyDis, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	           
	            gl.glPushMatrix();//�������Ͻǵ���
	        	gl.glTranslatef(xyDis1, xyDis1, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//�������½ǵ���
	        	gl.glTranslatef(xyDis1, -xyDis1, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//�������Ͻǵ���
	        	gl.glTranslatef(-xyDis1, xyDis1, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//�������½ǵ���
	        	gl.glTranslatef(-xyDis1, -xyDis1, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //�����Ӵ���С��λ��         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//���õ�ǰ����ΪͶӰ����            
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����            
            float ratio = (float) width / height;//����͸��ͶӰ�ı���            
            gl.glFrustumf(-ratio, ratio, -1, 1, 2.5f, 100);//���ô˷����������͸��ͶӰ����
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {           
        	gl.glDisable(GL10.GL_DITHER);//�رտ�����         	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//�����ض�Hint��Ŀ��ģʽ          
            gl.glClearColor(0,0,0,0); //������Ļ����ɫ��ɫRGBA    
            gl.glShadeModel(GL10.GL_SMOOTH);//������ɫģ��Ϊƽ����ɫ
            gl.glEnable(GL10.GL_DEPTH_TEST); //������Ȳ���        
            gl.glEnable(GL10.GL_CULL_FACE);//����Ϊ�򿪱������
            gl.glEnable(GL10.GL_LIGHTING); //�������           
            initGreenLight(gl);//��ʼ����
            initMaterial(gl);//��ʼ������
		}		
	}//SceneRenderer
	private void initGreenLight(GL10 gl){//��ʼ���Ƶķ���
        gl.glEnable(GL10.GL_LIGHT0);//��0�ŵ� 
        float[] ambientParams={0.1f,0.1f,0.1f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0); //����������   
        float[] diffuseParams={1.0f,1.0f,1.0f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); //ɢ������� 
        float[] specularParams={1.0f,1.0f,1.0f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);//���������    
	}
	private void initMaterial(GL10 gl){//��ʼ�����ʵķ���
        float ambientMaterial[] = {0.4f, 0.4f, 0.4f, 1.0f};//������Ϊ��ɫ����
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        float diffuseMaterial[] = {1.0f, 1.0f, 0.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);//ɢ���Ϊ��ɫ����
        float specularMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);//�߹����Ϊ��ɫ
        float shininessMaterial[] = {1.5f}; //�߹ⷴ������
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininessMaterial,0);
	}
	public void setPosition(boolean isPosition) {
		this.isPosition = isPosition;
	}
	public void setLightOffset(float lightOffset) {
		this.lightOffset = lightOffset;
	}
}
