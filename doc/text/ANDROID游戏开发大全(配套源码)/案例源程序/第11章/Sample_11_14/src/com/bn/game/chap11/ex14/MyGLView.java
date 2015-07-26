package com.bn.game.chap11.ex14;

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
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MyGLView extends GLSurfaceView{
	private final float TOUCH_SCALE_FACTOR = 180.0f/1000;//�Ƕ����ű���
	private SceneRenderer renderer;//������Ⱦ��
	private float previousX;//�ϴεĴ���λ��
	private float previousY; 
	private float cameraX=0;//�������λ��
	private float cameraY=0;
	private float cameraZ=0;
	private float targetX=0;//����
	private float targetY=0;
	private float targetZ=-4;
	private float sightDis=40;//�������Ŀ��ľ���
	private float angdegElevation=45;//����
	private float angdegAzimuth=90;//��λ��
	//���ڵƹ���ת����
	float angdegLight=0;//�ƹ���ת�ĽǶ�
	float angdegZ=45;//����xz���ϵ�ͶӰ��z��ļн�
	float rLight=10;
	LightRatateThread lightRatateThread;//��ת�Ƶ��߳�
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
            angdegAzimuth += dx * TOUCH_SCALE_FACTOR;//������y����ת�Ƕ�
            angdegElevation+= dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            requestRender();//�ػ滭��
        }
        previousY = y;//��¼���ر�λ��
        previousX = x;//��¼���ر�λ��
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer {
		Cube cube;//������
		private int[] textureIds;
		Cylinder cylinder;//Բ��
		Cone cone;//Բ׶
		Ball ball;//��
		Spheroid spheroid;//����
		Capsule capsule;//������
		Platform platform;//��̨
		RegularPyramid regularPyramid;//����׶
		
		private int coneSideTexId;
		private int ballSideTexId;
		private int cylinderSideTexId;
		private int spheroidSideTexId;
		private int capsuleSideTexId;
		private int pyramidSideTexId;
		
		private int topTexIdPlat;
		private int sideTexIdPlat;
		private int bottomTexIdPlat;
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//�����ɫ����Ȼ���
            gl.glMatrixMode(GL10.GL_MODELVIEW);//���õ�ǰ����Ϊģʽ����
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����  
            double angradElevation=Math.toRadians(angdegElevation);//���ǣ����ȣ�
        	double angradAzimuth=Math.toRadians(angdegAzimuth);//��λ��
            cameraX=(float) (targetX+sightDis*Math.cos(angradElevation)*Math.cos(angradAzimuth));
            cameraY=(float) (targetY+sightDis*Math.sin(angradElevation));
            cameraZ=(float) (targetZ+sightDis*Math.cos(angradElevation)*Math.sin(angradAzimuth));
            GLU.gluLookAt(//����cameraλ��
            		gl, 
            		cameraX, //����λ�õ�X
            		cameraY, //����λ�õ�Y
            		cameraZ, //����λ�õ�Z
            		targetX, //�����򿴵ĵ�X
            		targetY, //�����򿴵ĵ�Y
            		targetZ, //�����򿴵ĵ�Z
            		0,  //ͷ�ĳ���
            		1, 
            		0
            );
            //���ö�λ��
            double angradLight=Math.toRadians(angdegLight);
            double angradZ=Math.toRadians(angdegZ);
            float[] positionParams={
            		(float) (-rLight*Math.cos(angradLight)*Math.sin(angradZ)),
            		(float) (rLight*Math.sin(angradLight)),
            		(float) (rLight*Math.cos(angradLight)*Math.cos(angradZ)),
            		1};//���ö�λ��
    		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParams,0);
            gl.glTranslatef(0, 0f, -6f);
            //������
            gl.glPushMatrix();
            gl.glTranslatef(-4.4f, cube.b/2, 1.4f);
            cube.drawSelf(gl);
            gl.glPopMatrix();
            //Բ��
            gl.glPushMatrix();
            gl.glTranslatef(1.5f,0, -4.5f);
            cylinder.drawSelf(gl);
            gl.glPopMatrix();
            //Բ׶
            gl.glPushMatrix();
            gl.glTranslatef(4.4f,0, -1.5f);
            cone.drawSelf(gl);
            gl.glTranslatef(0,cone.h, 0);//����
            gl.glRotatef(180, 1, 0, 0);//��ת
            cone.drawSelf(gl);//�ٻ���һ��Բ׶
            gl.glPopMatrix();
            //��
            gl.glPushMatrix();
            gl.glTranslatef(-1.5f,ball.r, 4.3f);
            ball.drawSelf(gl);
            gl.glPopMatrix();
            //����
            gl.glPushMatrix();
            gl.glTranslatef(4.2f,spheroid.b, 4.2f);
            gl.glRotatef(45, 0, 1, 0);
            spheroid.drawSelf(gl);
            gl.glPopMatrix();
            //������
            gl.glPushMatrix();
            gl.glTranslatef(-1.3f,capsule.bBottom,-1.3f);
            capsule.drawSelf(gl);
            gl.glPopMatrix();
            //��̨
            gl.glPushMatrix();
            gl.glTranslatef(0,-platform.h,0);
            platform.drawSelf(gl);
            gl.glPopMatrix();
            //����׶
            gl.glPushMatrix();
            gl.glTranslatef(1.5f,0,1.4f);
            regularPyramid.drawSelf(gl);
            gl.glPopMatrix();
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //�����Ӵ���С��λ��         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//���õ�ǰ����ΪͶӰ����            
            gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����            
            float ratio = (float) width / height;//����͸��ͶӰ�ı���            
            gl.glFrustumf(-ratio, ratio, -1, 1, 6f, 100);//���ô˷����������͸��ͶӰ����
            
            lightRatateThread=new LightRatateThread(MyGLView.this);//�����������߳�
            lightRatateThread.start();
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {            
        	gl.glDisable(GL10.GL_DITHER);//�رտ�����         	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//�����ض�Hint��Ŀ��ģʽ          
            gl.glClearColor(0,0,0,0); //������Ļ����ɫ��ɫRGBA    
            gl.glEnable(GL10.GL_CULL_FACE);//����Ϊ�򿪱������
            gl.glEnable(GL10.GL_DEPTH_TEST); //������Ȳ���       
            
            gl.glEnable(GL10.GL_LIGHTING); //�������
            initGreenLight(gl);//��ʼ����
            initMaterial(gl);//��ʼ������
            //��ʼ������
            textureIds=new int[]{
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall)
            };
            //����id��ʼ��  
            sideTexIdPlat=initTexture(gl,R.drawable.side);    
            bottomTexIdPlat=initTexture(gl,R.drawable.top);
            topTexIdPlat=initTexture(gl,R.drawable.top);
            //����id
            coneSideTexId=initTexture(gl,R.drawable.cone);
    		ballSideTexId=initTexture(gl,R.drawable.ball);
    		cylinderSideTexId=initTexture(gl,R.drawable.cylinder);
    		spheroidSideTexId=initTexture(gl,R.drawable.spheroid);
    		capsuleSideTexId=initTexture(gl,R.drawable.capsule);
    		pyramidSideTexId=initTexture(gl,R.drawable.pyramid);

            //��������������
            cube=new Cube(1.4f,new float[]{1,1.2f,1.4f},textureIds);//������
            cylinder=new Cylinder(1.5f,0.4f,1.2f,40,//Բ��
            		cylinderSideTexId,cylinderSideTexId,cylinderSideTexId);
            cone=new Cone(2.5f,0.4f,0.9f,30,//Բ׶
            		coneSideTexId,coneSideTexId);
            ball=new Ball(1f,1f,30,30,ballSideTexId);//��
            spheroid=new Spheroid(0.75f,//����
            		1.4f,0.6f,1f,
            		30,30,spheroidSideTexId);
            capsule=new Capsule(//������
            		1.5f,
            		0.4f,0.8f,
            		0.4f,0.4f,
            		40,40,
            		capsuleSideTexId);
            platform=new Platform(//��̨
            		3f,
            		4,4,
            		3,3,
            		0.5f,
            		topTexIdPlat,bottomTexIdPlat,sideTexIdPlat,
            		2,2
            		);
            regularPyramid=new RegularPyramid(2.5f,0.4f,1.2f,6,//����׶
            		pyramidSideTexId,pyramidSideTexId);
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
	private void initGreenLight(GL10 gl){//��ʼ���Ƶķ���
        gl.glEnable(GL10.GL_LIGHT0);//��0�ŵ� 
        float[] ambientParams={0.6f,0.6f,0.6f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0); //����������   
        float[] diffuseParams={1.0f,1.0f,1.0f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); //ɢ������� 
        float[] specularParams={1.0f,1.0f,1.0f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);//���������    
	}
	private void initMaterial(GL10 gl){//��ʼ�����ʵķ���
        float ambientMaterial[] = {0.4f, 0.4f, 0.4f, 1.0f};//������Ϊ��ɫ����
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        float diffuseMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);//ɢ���Ϊ��ɫ����
        float specularMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);//�߹����Ϊ��ɫ
        float shininessMaterial[] = {1.5f}; //�߹ⷴ������
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininessMaterial,0);
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		lightRatateThread.setFlag(false);
	}
}
