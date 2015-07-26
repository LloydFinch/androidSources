package com.bn.game.chap11.ex12;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MyGLView extends GLSurfaceView{
	private final float TOUCH_SCALE_FACTOR = 180.0f/480;//角度缩放比例
	private SceneRenderer renderer;//场景渲染器
	private float previousX;//上次的触控位置
	private float previousY; 
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
            renderer.spheroid.yAngle += dx * TOUCH_SCALE_FACTOR;//设置沿y轴旋转角度
            renderer.spheroid.xAngle+= dy * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
            requestRender();//重绘画面
        }
        previousY = y;//记录触控笔位置
        previousX = x;//记录触控笔位置
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer {
		private int bottomTexId;
		Spheroid spheroid;
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清除颜色与深度缓存
            gl.glMatrixMode(GL10.GL_MODELVIEW);//设置当前矩阵为模式矩阵
            gl.glLoadIdentity();//设置当前矩阵为单位矩阵     
            float[] positionParams={//设置定位光
            		0,
            		30,
            		0,
            		1};
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParams,0);
            gl.glTranslatef(0, 0f, -4f);
            spheroid.drawSelf(gl);
		}
		
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //设置视窗大小及位置         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//设置当前矩阵为投影矩阵            
            gl.glLoadIdentity();//设置当前矩阵为单位矩阵            
            float ratio = (float) width / height;//计算透视投影的比例            
            gl.glFrustumf(-ratio, ratio, -1, 1, 1.5f, 100);//调用此方法计算产生透视投影矩阵
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
            bottomTexId=initTexture(gl,R.drawable.beauty2);
            spheroid=new Spheroid(0.75f,//椭球
            		1.4f,0.6f,1f,
            		30,30,bottomTexId);
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
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
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
        float[] ambientParams={0.15f,0.15f,0.15f,1.0f};//光参数 RGBA
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
	}
}
