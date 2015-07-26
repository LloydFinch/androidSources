package com.bn.game.chap11.ex5;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.bn.game.chap11.ex5.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

public class MyGLView extends GLSurfaceView{
	private final float TOUCH_SCALE_FACTOR = 180.0f/480;//�Ƕ����ű���
	private SceneRenderer renderer;//������Ⱦ��
	private float previousX;//�ϴεĴ���λ��
	private float previousY; 
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
            renderer.cube.yAngle += dx * TOUCH_SCALE_FACTOR;//������y����ת�Ƕ�
            renderer.cube.xAngle+= dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            requestRender();//�ػ滭��
        }
        previousY = y;//��¼���ر�λ��
        previousX = x;//��¼���ر�λ��
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer {
		Cube cube;
		private int[] textureIds;
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//�����ɫ����Ȼ���
            gl.glMatrixMode(GL10.GL_MODELVIEW);//���õ�ǰ����Ϊģʽ����
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����     
            gl.glTranslatef(0, 0f, -6f);  
            cube.drawSelf(gl);
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //�����Ӵ���С��λ��         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//���õ�ǰ����ΪͶӰ����            
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����            
            float ratio = (float) width / height;//����͸��ͶӰ�ı���            
            gl.glFrustumf(-ratio, ratio, -1, 1, 2.5f, 10);//���ô˷����������͸��ͶӰ����
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
            cube=new Cube(1.4f,new float[]{1,1.2f,1.4f},textureIds);
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
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
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
}
