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
	private final float TOUCH_SCALE_FACTOR = 180.0f/1000;//角度缩放比例
	private SceneRenderer renderer;//场景渲染器
	private float previousX;//上次的触控位置
	private float previousY; 
	private float cameraX=0;//摄像机的位置
	private float cameraY=0;
	private float cameraZ=0;
	private float targetX=0;//看点
	private float targetY=0;
	private float targetZ=-4;
	private float sightDis=40;//摄像机和目标的距离
	private float angdegElevation=45;//仰角
	private float angdegAzimuth=90;//方位角
	//关于灯光旋转的量
	float angdegLight=0;//灯光旋转的角度
	float angdegZ=45;//灯在xz面上的投影与z轴的夹角
	float rLight=10;
	LightRatateThread lightRatateThread;//旋转灯的线程
	public MyGLView(Context context) {
		super(context);
		renderer=new SceneRenderer();//创建渲染器
		this.setRenderer(renderer);//设置渲染器
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
	}
	//触摸事件回调方法
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - previousY;//计算触控笔Y位移
            float dx = x - previousX;//计算触控笔Y位移
            angdegAzimuth += dx * TOUCH_SCALE_FACTOR;//设置沿y轴旋转角度
            angdegElevation+= dy * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
            requestRender();//重绘画面
        }
        previousY = y;//记录触控笔位置
        previousX = x;//记录触控笔位置
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer {
		Cube cube;//立方体
		private int[] textureIds;
		Cylinder cylinder;//圆柱
		Cone cone;//圆锥
		Ball ball;//球
		Spheroid spheroid;//椭球
		Capsule capsule;//胶囊体
		Platform platform;//梯台
		RegularPyramid regularPyramid;//正棱锥
		
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
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清除颜色与深度缓存
            gl.glMatrixMode(GL10.GL_MODELVIEW);//设置当前矩阵为模式矩阵
            gl.glLoadIdentity();//设置当前矩阵为单位矩阵  
            double angradElevation=Math.toRadians(angdegElevation);//仰角（弧度）
        	double angradAzimuth=Math.toRadians(angdegAzimuth);//方位角
            cameraX=(float) (targetX+sightDis*Math.cos(angradElevation)*Math.cos(angradAzimuth));
            cameraY=(float) (targetY+sightDis*Math.sin(angradElevation));
            cameraZ=(float) (targetZ+sightDis*Math.cos(angradElevation)*Math.sin(angradAzimuth));
            GLU.gluLookAt(//设置camera位置
            		gl, 
            		cameraX, //人眼位置的X
            		cameraY, //人眼位置的Y
            		cameraZ, //人眼位置的Z
            		targetX, //人眼球看的点X
            		targetY, //人眼球看的点Y
            		targetZ, //人眼球看的点Z
            		0,  //头的朝向
            		1, 
            		0
            );
            //设置定位光
            double angradLight=Math.toRadians(angdegLight);
            double angradZ=Math.toRadians(angdegZ);
            float[] positionParams={
            		(float) (-rLight*Math.cos(angradLight)*Math.sin(angradZ)),
            		(float) (rLight*Math.sin(angradLight)),
            		(float) (rLight*Math.cos(angradLight)*Math.cos(angradZ)),
            		1};//设置定位光
    		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParams,0);
            gl.glTranslatef(0, 0f, -6f);
            //立方体
            gl.glPushMatrix();
            gl.glTranslatef(-4.4f, cube.b/2, 1.4f);
            cube.drawSelf(gl);
            gl.glPopMatrix();
            //圆柱
            gl.glPushMatrix();
            gl.glTranslatef(1.5f,0, -4.5f);
            cylinder.drawSelf(gl);
            gl.glPopMatrix();
            //圆锥
            gl.glPushMatrix();
            gl.glTranslatef(4.4f,0, -1.5f);
            cone.drawSelf(gl);
            gl.glTranslatef(0,cone.h, 0);//推移
            gl.glRotatef(180, 1, 0, 0);//旋转
            cone.drawSelf(gl);//再绘制一次圆锥
            gl.glPopMatrix();
            //球
            gl.glPushMatrix();
            gl.glTranslatef(-1.5f,ball.r, 4.3f);
            ball.drawSelf(gl);
            gl.glPopMatrix();
            //椭球
            gl.glPushMatrix();
            gl.glTranslatef(4.2f,spheroid.b, 4.2f);
            gl.glRotatef(45, 0, 1, 0);
            spheroid.drawSelf(gl);
            gl.glPopMatrix();
            //胶囊体
            gl.glPushMatrix();
            gl.glTranslatef(-1.3f,capsule.bBottom,-1.3f);
            capsule.drawSelf(gl);
            gl.glPopMatrix();
            //梯台
            gl.glPushMatrix();
            gl.glTranslatef(0,-platform.h,0);
            platform.drawSelf(gl);
            gl.glPopMatrix();
            //正棱锥
            gl.glPushMatrix();
            gl.glTranslatef(1.5f,0,1.4f);
            regularPyramid.drawSelf(gl);
            gl.glPopMatrix();
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //设置视窗大小及位置         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//设置当前矩阵为投影矩阵            
            gl.glLoadIdentity();//设置当前矩阵为单位矩阵            
            float ratio = (float) width / height;//计算透视投影的比例            
            gl.glFrustumf(-ratio, ratio, -1, 1, 6f, 100);//调用此方法计算产生透视投影矩阵
            
            lightRatateThread=new LightRatateThread(MyGLView.this);//创建并开启线程
            lightRatateThread.start();
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {            
        	gl.glDisable(GL10.GL_DITHER);//关闭抗抖动         	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置特定Hint项目的模式          
            gl.glClearColor(0,0,0,0); //设置屏幕背景色黑色RGBA    
            gl.glEnable(GL10.GL_CULL_FACE);//设置为打开背面剪裁
            gl.glEnable(GL10.GL_DEPTH_TEST); //启用深度测试       
            
            gl.glEnable(GL10.GL_LIGHTING); //允许光照
            initGreenLight(gl);//初始化灯
            initMaterial(gl);//初始化材质
            //初始化纹理
            textureIds=new int[]{
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall),
            	initTexture(gl,R.drawable.wall)
            };
            //纹理id初始化  
            sideTexIdPlat=initTexture(gl,R.drawable.side);    
            bottomTexIdPlat=initTexture(gl,R.drawable.top);
            topTexIdPlat=initTexture(gl,R.drawable.top);
            //侧面id
            coneSideTexId=initTexture(gl,R.drawable.cone);
    		ballSideTexId=initTexture(gl,R.drawable.ball);
    		cylinderSideTexId=initTexture(gl,R.drawable.cylinder);
    		spheroidSideTexId=initTexture(gl,R.drawable.spheroid);
    		capsuleSideTexId=initTexture(gl,R.drawable.capsule);
    		pyramidSideTexId=initTexture(gl,R.drawable.pyramid);

            //创建各个几何体
            cube=new Cube(1.4f,new float[]{1,1.2f,1.4f},textureIds);//立方体
            cylinder=new Cylinder(1.5f,0.4f,1.2f,40,//圆柱
            		cylinderSideTexId,cylinderSideTexId,cylinderSideTexId);
            cone=new Cone(2.5f,0.4f,0.9f,30,//圆锥
            		coneSideTexId,coneSideTexId);
            ball=new Ball(1f,1f,30,30,ballSideTexId);//球
            spheroid=new Spheroid(0.75f,//椭球
            		1.4f,0.6f,1f,
            		30,30,spheroidSideTexId);
            capsule=new Capsule(//胶囊体
            		1.5f,
            		0.4f,0.8f,
            		0.4f,0.4f,
            		40,40,
            		capsuleSideTexId);
            platform=new Platform(//梯台
            		3f,
            		4,4,
            		3,3,
            		0.5f,
            		topTexIdPlat,bottomTexIdPlat,sideTexIdPlat,
            		2,2
            		);
            regularPyramid=new RegularPyramid(2.5f,0.4f,1.2f,6,//正棱锥
            		pyramidSideTexId,pyramidSideTexId);
		}
	}//SceneRenderer
	//初始化纹理
	public int initTexture(GL10 gl,int drawableId){
		//生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);//生成一个纹理id放在textures数组中的0位置
		int currTextureId=textures[0];   //获取生成的纹理id 
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);//绑定该纹理id，后面的操作都是针对该id
		//设置MIN_FILTER与MAG_FILTER为MIPMAP纹理过滤方式
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR_MIPMAP_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR_MIPMAP_LINEAR);
		//生成MIPMAP纹理
		((GL11)gl).glTexParameterf(GL10.GL_TEXTURE_2D,GL11.GL_GENERATE_MIPMAP, GL10.GL_TRUE);
		//设置纹理拉伸方式为REPEAT 
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
        InputStream is = this.getResources().openRawResource(drawableId);//获取图片资源的输入流
        Bitmap bitmapTmp; 
        try{
        	bitmapTmp = BitmapFactory.decodeStream(is);//通过输入流生成位图
        } 
        finally{
            try {
                is.close();//关闭流
            }catch(IOException e){
                e.printStackTrace();//打印异常
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);//自动设置图片的格式和类型
        bitmapTmp.recycle(); //回收图片资源
        return currTextureId;
	}
	private void initGreenLight(GL10 gl){//初始化灯的方法
        gl.glEnable(GL10.GL_LIGHT0);//打开0号灯 
        float[] ambientParams={0.6f,0.6f,0.6f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0); //环境光设置   
        float[] diffuseParams={1.0f,1.0f,1.0f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); //散射光设置 
        float[] specularParams={1.0f,1.0f,1.0f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);//反射光设置    
	}
	private void initMaterial(GL10 gl){//初始化材质的方法
        float ambientMaterial[] = {0.4f, 0.4f, 0.4f, 1.0f};//环境光为白色材质
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        float diffuseMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);//散射光为白色材质
        float specularMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);//高光材质为白色
        float shininessMaterial[] = {1.5f}; //高光反射区域
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininessMaterial,0);
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		lightRatateThread.setFlag(false);
	}
}
