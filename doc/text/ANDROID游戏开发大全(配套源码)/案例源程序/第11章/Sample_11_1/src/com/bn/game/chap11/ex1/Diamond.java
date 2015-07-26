package com.bn.game.chap11.ex1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Diamond {
	private FloatBuffer   vertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   colorBuffer;//顶点着色数据缓冲
    private ByteBuffer  indexBuffer;//顶点构建索引数据缓冲
    int vCount=0;//顶点个数
    int iCount=0;
    float size;//尺寸
    float xAngle=0;//绕z轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度
	public Diamond(float scale,float a, float b) {
		size=Constant.UNIT_SIZE*scale;
		vCount=3;
		//坐标数据初始化
		float[] vertices=new float[]{
			-a*size, 0*size, 0*size,
			0*size, -b*size, 0*size,
			a*size, 0*size, 0*size,
			0*size, b*size, 0*size,
		};
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//创建顶点坐标数据缓冲
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        vertexBuffer = vbb.asFloatBuffer();//转换为float型缓冲
        vertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        vertexBuffer.position(0);//设置缓冲区起始位置
        
        //着色数据的初始化
        float[] colors=new float[]{//顶点颜色值数组，每个顶点4个色彩值RGBA
        		1,0,0,0,
        		0,0,1,0,
        		0,1,0,0,
        		1,1,1,0,
        };    
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);//创建顶点着色数据缓冲
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        colorBuffer = cbb.asFloatBuffer();//转换为float型缓冲
        colorBuffer.put(colors);//向缓冲区中放入顶点着色数据
        colorBuffer.position(0);//设置缓冲区起始位置
        
        //三角形构造索引数据初始化
        iCount=6;
        byte indexes[]=new byte[]{//三角形索引数组
        	0,2,3,//逆时针
        	0,2,1,//顺时针
        };
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
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		iCount, 			 	//顶点个数
        		GL10.GL_UNSIGNED_BYTE, 	//索引值的尺寸
        		indexBuffer				//索引值数据
        ); 
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//禁用顶点坐标数组
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//禁用顶点颜色数组
        gl.glPopMatrix();
    }
}
