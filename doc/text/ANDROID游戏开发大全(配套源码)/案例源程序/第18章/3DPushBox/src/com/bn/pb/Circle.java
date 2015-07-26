/**
 * 
 * 	用于绘制圆
 */
package com.bn.pb;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
//用triangle_fan方式绘制圆面
public class Circle
{
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mTextureBuffer;//顶点纹理数据缓冲
    private int vCount;
 
    public Circle
    (
    		float angleSpan,//切分角度
    		float radius,//圆半径
    		float[] centerTexCoor,//纹理中心点
    		float textureRadius//纹理半径
    )
    {
    	
    	//顶点纹理坐标数据的初始化================begin============================
    	vCount=1+(int)(360/angleSpan)+1;//顶点的个数
    	
    	float[] vertices=new float[vCount*3];//初始化顶点数组
    	float[] textures=new float[vCount*2];//初始化纹理坐标数组
    	
    	//存放中心点坐标
    	vertices[0]=0;
    	vertices[1]=0;
    	vertices[2]=0;
    	
    	//存放中心点纹理
    	textures[0]=centerTexCoor[0];
    	textures[1]=centerTexCoor[1];
    	
    	int vcount=3;//当前顶点坐标索引
    	int tcount=2;//当前纹理坐标索引
    	
    	for(float angle=0;angle<=360;angle=angle+angleSpan)
    	{
    		double angleRadian=Math.toRadians(angle);
    		//顶点坐标
    		vertices[vcount++]=radius*(float)Math.cos(angleRadian);
    		vertices[vcount++]=radius*(float)Math.sin(angleRadian);
    		vertices[vcount++]=0;
    		//纹理坐标
    		textures[tcount++]=centerTexCoor[0]+textureRadius*(float)Math.cos(angleRadian);
    		textures[tcount++]=centerTexCoor[1]+textureRadius*(float)Math.sin(angleRadian);
    	}    	
		
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================
                
        //创建顶点纹理数据缓冲
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTextureBuffer= tbb.asFloatBuffer();//转换为Float型缓冲
        mTextureBuffer.put(textures);//向缓冲区中放入顶点着色数据
        mTextureBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点纹理数据的初始化================end============================
    }

    public void drawSelf(GL10 gl,int textureId)
    {        
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔 /跨度
        		mVertexBuffer	//顶点坐标数据
        );
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLE_FAN, 		//以TRIANGLE_FAN方式填充
        		0,
        		vCount
        );
    }
}
