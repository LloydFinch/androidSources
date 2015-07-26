package com.bn.game.chap11.ex14;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
//顶点法画圆，不限制顶点数
public class Circle {
	private FloatBuffer   vertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer   normalBuffer;//顶点法向量数据缓冲
	private FloatBuffer   textureBuffer;//顶点纹理数据缓冲
    int vCount=0;//顶点个数
    float size;//尺寸
    float angdegSpan;//每个三角形顶角
    float xAngle=0;//绕z轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度
    int textureId;//纹理id
	public Circle(float scale,float r, int n, int textureId) {//大小，半径，边数
		this.textureId=textureId;
		//改变尺寸
		size=Constant.UNIT_SIZE*scale;
		r*=size;
		angdegSpan=360.0f/n;
		vCount=3*n;//顶点个数，共有n个三角形，每个三角形都有三个顶点
		
		float[] vertices=new float[vCount*3];//坐标数据
		float[] textures=new float[vCount*2];//顶点纹理S、T坐标值数组
		//坐标数据初始化
		int count=0;
		int stCount=0;
		for(float angdeg=0;Math.ceil(angdeg)<360;angdeg+=angdegSpan)
		{
			double angrad=Math.toRadians(angdeg);//当前弧度
			double angradNext=Math.toRadians(angdeg+angdegSpan);//下一弧度
			//中心点
			vertices[count++]=0;//顶点坐标
			vertices[count++]=0; 
			vertices[count++]=0;
			
			textures[stCount++]=0.5f;//st坐标
			textures[stCount++]=0.5f;
			//当前点
			vertices[count++]=(float) (-r*Math.sin(angrad));//顶点坐标
			vertices[count++]=(float) (r*Math.cos(angrad));
			vertices[count++]=0;
			
			textures[stCount++]=(float) (0.5f-0.5f*Math.sin(angrad));//st坐标
			textures[stCount++]=(float) (0.5f-0.5f*Math.cos(angrad));
			//下一点
			vertices[count++]=(float) (-r*Math.sin(angradNext));//顶点坐标
			vertices[count++]=(float) (r*Math.cos(angradNext));
			vertices[count++]=0;
			
			textures[stCount++]=(float) (0.5f-0.5f*Math.sin(angradNext));//st坐标
			textures[stCount++]=(float) (0.5f-0.5f*Math.cos(angradNext));
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//创建顶点坐标数据缓冲
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        vertexBuffer = vbb.asFloatBuffer();//转换为float型缓冲
        vertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        vertexBuffer.position(0);//设置缓冲区起始位置
        //法向量数据初始化 
        float[] normals=new float[vertices.length];
        for(int i=0;i<normals.length;i+=3){
        	normals[i]=0;
        	normals[i+1]=0;
        	normals[i+2]=1;
        }
        VectorUtil.normalizeAllVectors(normals);//规格化法向量
        
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);//创建顶点法向量数据缓冲
        nbb.order(ByteOrder.nativeOrder());//设置字节顺序
        normalBuffer = nbb.asFloatBuffer();//转换为float型缓冲
        normalBuffer.put(normals);//向缓冲区中放入顶点法向量数据
        normalBuffer.position(0);//设置缓冲区起始位置
        //st坐标数据初始化
        ByteBuffer cbb = ByteBuffer.allocateDirect(textures.length*4);//创建顶点纹理数据缓冲
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        textureBuffer = cbb.asFloatBuffer();//转换为float型缓冲
        textureBuffer.put(textures);//向缓冲区中放入顶点纹理数据
        textureBuffer.position(0);//设置缓冲区起始位置
	}
    public void drawSelf(GL10 gl)
    {        
    	gl.glPushMatrix();
    	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//启用顶点坐标数组
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//启用顶点法向量数组
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//启用顶点纹理数组
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
        //为画笔指定顶点法向量数据
        gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
        //为画笔指定纹理ST坐标缓冲
        gl.glEnable(GL10.GL_TEXTURE_2D); //开启纹理
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);//为画笔指定纹理ST坐标缓冲
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//绑定当前纹理
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//开始点编号
        		vCount					//顶点的数量
        );
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//禁用顶点坐标数组
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//禁用顶点法向量数组
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//禁用顶点纹理数组
        gl.glPopMatrix();
    }
}
