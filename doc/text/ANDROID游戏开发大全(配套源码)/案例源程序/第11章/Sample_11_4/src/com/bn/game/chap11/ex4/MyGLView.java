package com.bn.game.chap11.ex4;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLView extends GLSurfaceView{
	private SceneRenderer renderer;//场景渲染器
	private boolean isPosition=true;//是否为定位光的标志位
	private float lightOffset=-4;//灯光的位置或方向的偏移量	
	public MyGLView(Context context) {
		super(context);
		renderer=new SceneRenderer();//创建渲染器
		this.setRenderer(renderer);//设置渲染器
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染   
	}
	private class SceneRenderer implements GLSurfaceView.Renderer {
		Ball ball=new Ball(1.0f,0.5f,80,80);
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {
	        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清除颜色与深度缓存
	            gl.glMatrixMode(GL10.GL_MODELVIEW);//设置当前矩阵为模式矩阵
	            gl.glLoadIdentity();//设置当前矩阵为单位矩阵
	            gl.glTranslatef(0, 0f, -7f);
	        	if(isPosition){//设定定位光的位置
	        		float[] positionParams={0,0,lightOffset,1};//z轴上的定位光
	        		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParams,0);                
	        	}
	        	else{//设定定向光的方向
	        		float[] directionParams={0,lightOffset,0,0};//延y轴某方向的定向光
	        		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, directionParams,0);        		
	        	} 
	        	float xyDis=1.7f;//球与z轴的距离
	        	float xyDis1=1.7f/1.414f;
	        	
	        	gl.glPushMatrix();//绘制右面的球
	        	gl.glTranslatef(xyDis, 0f, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//绘制左面的球
	        	gl.glTranslatef(-xyDis, 0f, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//绘制上面的球
	        	gl.glTranslatef( 0f,xyDis, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//绘制下面的球
	        	gl.glTranslatef( 0f,-xyDis, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	           
	            gl.glPushMatrix();//绘制右上角的球
	        	gl.glTranslatef(xyDis1, xyDis1, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//绘制右下角的球
	        	gl.glTranslatef(xyDis1, -xyDis1, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//绘制左上角的球
	        	gl.glTranslatef(-xyDis1, xyDis1, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
	            
	            gl.glPushMatrix();//绘制左下角的球
	        	gl.glTranslatef(-xyDis1, -xyDis1, 0);
	            ball.drawSelf(gl);
	            gl.glPopMatrix();
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //设置视窗大小及位置         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//设置当前矩阵为投影矩阵            
            gl.glLoadIdentity();//设置当前矩阵为单位矩阵            
            float ratio = (float) width / height;//计算透视投影的比例            
            gl.glFrustumf(-ratio, ratio, -1, 1, 2.5f, 100);//调用此方法计算产生透视投影矩阵
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {           
        	gl.glDisable(GL10.GL_DITHER);//关闭抗抖动         	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置特定Hint项目的模式          
            gl.glClearColor(0,0,0,0); //设置屏幕背景色黑色RGBA    
            gl.glShadeModel(GL10.GL_SMOOTH);//设置着色模型为平滑着色
            gl.glEnable(GL10.GL_DEPTH_TEST); //启用深度测试        
            gl.glEnable(GL10.GL_CULL_FACE);//设置为打开背面剪裁
            gl.glEnable(GL10.GL_LIGHTING); //允许光照           
            initGreenLight(gl);//初始化灯
            initMaterial(gl);//初始化材质
		}		
	}//SceneRenderer
	private void initGreenLight(GL10 gl){//初始化灯的方法
        gl.glEnable(GL10.GL_LIGHT0);//打开0号灯 
        float[] ambientParams={0.1f,0.1f,0.1f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0); //环境光设置   
        float[] diffuseParams={1.0f,1.0f,1.0f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); //散射光设置 
        float[] specularParams={1.0f,1.0f,1.0f,1.0f};//光参数 RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);//反射光设置    
	}
	private void initMaterial(GL10 gl){//初始化材质的方法
        float ambientMaterial[] = {0.4f, 0.4f, 0.4f, 1.0f};//环境光为白色材质
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        float diffuseMaterial[] = {1.0f, 1.0f, 0.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);//散射光为白色材质
        float specularMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);//高光材质为白色
        float shininessMaterial[] = {1.5f}; //高光反射区域
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininessMaterial,0);
	}
	public void setPosition(boolean isPosition) {
		this.isPosition = isPosition;
	}
	public void setLightOffset(float lightOffset) {
		this.lightOffset = lightOffset;
	}
}
