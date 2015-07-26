package com.bn.game.chap11.ex8;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;

public class MyGLView extends GLSurfaceView{
	private SceneRenderer renderer;//������Ⱦ��
	public MyGLView(Context context) {
		super(context);
		renderer=new SceneRenderer();//������Ⱦ��
		this.setRenderer(renderer);//������Ⱦ��
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
	}
	private class SceneRenderer implements GLSurfaceView.Renderer {
		Cube cube;
		TextureRect desertRect;//ɳĮ����
		private int[] textureIds;
		private int desertTexId;
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//�����ɫ����Ȼ���
            gl.glMatrixMode(GL10.GL_MODELVIEW);//���õ�ǰ����Ϊģʽ����
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����     
            gl.glTranslatef(0, 0f, -6f);  
            //�м��������
            gl.glPushMatrix();
            gl.glTranslatef(-1.5f, cube.b/2, 0.5f);
            cube.drawSelf(gl);
            gl.glPopMatrix();
            //Զ����������
            gl.glPushMatrix();
            gl.glTranslatef(1.5f, cube.b/2, -5.5f);
            cube.drawSelf(gl);
            gl.glPopMatrix();
            //������������
            gl.glPushMatrix();
            gl.glTranslatef(0.5f, cube.b/2, 5.5f);
            cube.drawSelf(gl);
            gl.glPopMatrix();
            //ɳĮ
            gl.glPushMatrix();
            gl.glRotatef(-90, 1, 0, 0);
            desertRect.drawSelf(gl);//����ɳĮ
            gl.glPopMatrix();
            
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //�����Ӵ���С��λ��         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//���õ�ǰ����ΪͶӰ����            
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����            
            float ratio = (float) width / height;//����͸��ͶӰ�ı���            
            gl.glFrustumf(-ratio, ratio, -1, 1, 2.5f, 100);//���ô˷����������͸��ͶӰ����
            
            GLU.gluLookAt(//����cameraλ��
            		gl, 
            		0f,   //����λ�õ�X
            		4f, 	//����λ�õ�Y
            		5f,   //����λ�õ�Z����ֵ����-25f,���Ƶ���������
            		0, 	//�����򿴵ĵ�X
            		0f,   //�����򿴵ĵ�Y
            		-4,   //�����򿴵ĵ�Z
            		0, 
            		1, 
            		0
            );
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {            
        	gl.glDisable(GL10.GL_DITHER);//�رտ�����         	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//�����ض�Hint��Ŀ��ģʽ          
            gl.glClearColor(0,0,0,0); //������Ļ����ɫ��ɫRGBA    
            gl.glEnable(GL10.GL_CULL_FACE);//����Ϊ�򿪱������
            gl.glEnable(GL10.GL_DEPTH_TEST); //������Ȳ���       
            //��ʼ������
            textureIds=new int[]{
            	initTexture(gl,R.drawable.beauty0),
            	initTexture(gl,R.drawable.beauty1),
            	initTexture(gl,R.drawable.beauty2),
            	initTexture(gl,R.drawable.beauty3),
            	initTexture(gl,R.drawable.beauty4),
            	initTexture(gl,R.drawable.beauty5)
            };
            desertTexId=initTexture(gl,R.drawable.desert);
            cube=new Cube(1.4f,new float[]{1,1.2f,1.4f},textureIds);
            desertRect=new TextureRect(6,1.5f,2.4f,desertTexId,4,5);
            
            gl.glEnable(GL10.GL_FOG);//������
            initFog(gl);//��ʼ����
		}		
	}//SceneRenderer
	//��ʼ������
	public int initTexture(GL10 gl,int drawableId){
		//��������ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);//����һ������id����textures�����е�0λ��
		int currTextureId=textures[0];   //��ȡ���ɵ�����id 
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);//�󶨸�����id������Ĳ���������Ը�id
		//����MIN_FILTER��MAG_FILTERΪMIPMAP������˷�ʽ
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR_MIPMAP_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR_MIPMAP_LINEAR);
		//����MIPMAP����
		((GL11)gl).glTexParameterf(GL10.GL_TEXTURE_2D,GL11.GL_GENERATE_MIPMAP, GL10.GL_TRUE);
		//�����������췽ʽΪREPEAT 
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
        InputStream is = this.getResources().openRawResource(drawableId);//��ȡͼƬ��Դ��������
        Bitmap bitmapTmp; 
        try{
        	bitmapTmp = BitmapFactory.decodeStream(is);//ͨ������������λͼ
        } 
        finally{
            try {
                is.close();//�ر���
            }catch(IOException e){
                e.printStackTrace();//��ӡ�쳣
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);//�Զ�����ͼƬ�ĸ�ʽ������
        bitmapTmp.recycle(); //����ͼƬ��Դ
        return currTextureId;
	}
	//��ʼ����
	public void initFog(GL10 gl)
	{
		float[] fogColor={1,0.91765f,0.66667f,0};//�����ɫ
		gl.glFogfv(GL10.GL_FOG_COLOR, fogColor, 0);//���������ɫ
		gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_EXP2);//�������ģʽ
		gl.glFogf(GL10.GL_FOG_DENSITY, 0.13f);//�������Ũ��
		gl.glFogf(GL10.GL_FOG_START, 0.5f);//������Ŀ�ʼ����
		gl.glFogf(GL10.GL_FOG_END, 100.0f);//������Ľ�������
	}
}
