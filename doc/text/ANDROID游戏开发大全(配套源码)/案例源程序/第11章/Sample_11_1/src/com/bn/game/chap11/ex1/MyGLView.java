package com.bn.game.chap11.ex1;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLView extends GLSurfaceView{
	private final float TOUCH_SCALE_FACTOR = 180.0f/480;//角度缩放比例
	private SceneRenderer renderer;//场景渲染器
	private float previousX;//上次的触控位置
	private float previousY;  
	private boolean cullFaceFlag=false;//背面剪裁标志位
	private boolean smoothFlag=false;//平滑着色标志位
	private boolean cwFlag=false;//自定义卷绕标志位	
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
            renderer.diamond.yAngle += dx * TOUCH_SCALE_FACTOR;//设置沿y轴旋转角度
            renderer.diamond.xAngle+= dy * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度
            requestRender();//重绘画面
        }
        previousY = y;//记录触控笔位置
        previousX = x;//记录触控笔位置
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer {
		
		Diamond diamond=new Diamond(1,1,1.5f);
		public SceneRenderer(){}
		@Override
		public void onDrawFrame(GL10 gl) {
        	if(cullFaceFlag){//背面剪裁标志位
        		gl.glEnable(GL10.GL_CULL_FACE);//设置为打开背面剪裁
        	}
        	else{
        		gl.glDisable(GL10.GL_CULL_FACE);//设置为关闭背面剪裁
        	}
        	if(smoothFlag){//平滑着色标志位
                gl.glShadeModel(GL10.GL_SMOOTH);//设置着色模型为平滑着色   
        	}
        	else{
        		gl.glShadeModel(GL10.GL_FLAT);//设置着色模型为不平滑着色   
        	}
        	if(cwFlag){//自定义卷绕标志位
        		gl.glFrontFace(GL10.GL_CW);//设置为自定义卷绕顺序——顺时针为正面
        	}
        	else{
        		gl.glFrontFace(GL10.GL_CCW);//设置为默认卷绕顺序——逆时针为正面
        	}
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//清除颜色与深度缓存
            gl.glMatrixMode(GL10.GL_MODELVIEW);//设置当前矩阵为模式矩阵
            gl.glLoadIdentity();//设置当前矩阵为单位矩阵     
            gl.glTranslatef(0, 0f, -4f);  
            diamond.drawSelf(gl);
		}
		
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {           
        	gl.glViewport(0, 0, width, height); //设置视窗大小及位置         	
            gl.glMatrixMode(GL10.GL_PROJECTION);//设置当前矩阵为投影矩阵            
            gl.glLoadIdentity();//设置当前矩阵为单位矩阵            
            float ratio = (float) width / height;//计算透视投影的比例            
            gl.glFrustumf(-ratio, ratio, -1, 1, 1.5f, 10);//调用此方法计算产生透视投影矩阵
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {            
        	gl.glDisable(GL10.GL_DITHER);//关闭抗抖动         	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//设置特定Hint项目的模式          
            gl.glClearColor(0,0,0,0); //设置屏幕背景色黑色RGBA    
            gl.glEnable(GL10.GL_DEPTH_TEST); //启用深度测试        
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
