package com.bn.game.chap11.ex3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Circle {
	private FloatBuffer   vertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   colorBuffer;//顶点着色数据缓冲
    private ByteBuffer  indexBuffer;//顶点构建索引数据缓冲
    int vCount=0;//顶点个数
    int iCount=0;
    float size;//尺寸
    float angdegSpan;//每个三角形顶角
    float xAngle=0;//绕z轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度
	public Circle(float scale,float r, int n, float zOffset) {//大小，半径，边数（边数绝不能超过126！！！）
		size=Constant.UNIT_SIZE*scale;
		angdegSpan=360.0f/n;
		vCount=n+1;//顶点和中心点
		//坐标数据初始化
		float[] vertices=new float[vCount*3];
		int count=0;
		vertices[count++]=0; vertices[count++]=0; vertices[count++]=zOffset*size;//中心点
		for(float angdeg=0;Math.ceil(angdeg)<360;angdeg+=angdegSpan)
		{
			double angrad=Math.toRadians(angdeg);
			vertices[count++]=(float) (-r*Math.sin(angrad))*size;
			vertices[count++]=(float) (r*Math.cos(angrad))*size;
			vertices[count++]=zOffset*size;
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//创建顶点坐标数据缓冲
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        vertexBuffer = vbb.asFloatBuffer();//转换为float型缓冲
        vertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        vertexBuffer.position(0);//设置缓冲区起始位置
        
        //着色数据的初始化
        float[] colors=new float[vCount*4];//顶点颜色值数组，每个顶点4个色彩值RGBA
        count=0;
    	colors[count++]=1;//中心点的rgba
    	colors[count++]=1;
    	colors[count++]=1;
    	colors[count++]=0;
        for(int i=1;i<vCount;i++){ //为每个顶点随机分配一种颜色
        	colors[count++]=(float) Math.random();
        	colors[count++]=(float) Math.random();
        	colors[count++]=(float) Math.random();
        	colors[count++]=1;
        }
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);//创建顶点着色数据缓冲
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        colorBuffer = cbb.asFloatBuffer();//转换为float型缓冲
        colorBuffer.put(colors);//向缓冲区中放入顶点着色数据
        colorBuffer.position(0);//设置缓冲区起始位置
        
        //三角形构造索引数据初始化
        iCount=n+2;
        byte indexes[]=new byte[iCount];//三角形索引数组        
        for(byte i=0;i<indexes.length-1;i++){
        	indexes[i]=i;
        }
        indexes[indexes.length-1]=1;//重复第一个点，使圆闭合        
        indexBuffer = ByteBuffer.allocateDirect(indexes.length);//创建三角形构造索引数据缓冲
        indexBuffer.put(indexes);//向缓冲区中放入三角形构造索引数据
        indexBuffer.position(0);//设置缓冲区起始位置
	}
    public void drawSelf(GL10 gl)
    {        
    	gl.glPushMatrix();
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//启用顶点颜色数组
        //绕轴旋转
        gl.glRotatef(xAngle, 1, 0, 0);
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glRotatef(zAngle, 0, 0, 1);
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FLOAT
        		0, 				//连续顶点坐标数据之间的间隔
        		vertexBuffer	//顶点坐标数据
        );
		
        //为画笔指定顶点着色数据
        gl.glColorPointer
        (
        		4, 				//设置颜色的组成成分，必须为4—RGBA
        		GL10.GL_FLOAT, 	//顶点颜色值的类型为 GL_FLOAT
        		0, 				//连续顶点着色数据之间的间隔
        		colorBuffer		//顶点着色数据
        );
		
        //绘制图形
        gl.glDrawElements
        (
        		GL10.GL_TRIANGLE_FAN, 	//以RIANGLE_FAN方式填充
        		iCount, 			 	//顶点个数
        		GL10.GL_UNSIGNED_BYTE, 	//索引值的尺寸
        		indexBuffer				//索引值数据
        ); 
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//禁用顶点坐标数组
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//禁用顶点颜色数组
        gl.glPopMatrix();
    }
}
