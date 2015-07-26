package com.bn.game.chap11.ex14;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
//顶点法画圆，不限制顶点数
public class Spheroid {
	private FloatBuffer   vertexBuffer;//顶点坐标数据缓冲
	private FloatBuffer   textureBuffer;//顶点纹理数据缓冲
	private FloatBuffer   normalBuffer;//顶点法向量数据缓冲
    int vCount=0;//顶点个数
    float size;//尺寸
    float angdegColSpan;//球纵向切分角度
    float angdegRowSpan;//球横向切分角度
    float xAngle=0;//绕z轴旋转的角度
    float yAngle=0;//绕y轴旋转的角度
    float zAngle=0;//绕z轴旋转的角度
    int textureId;//纹理id
    float b;
	public Spheroid(float scale, float a, float b, float c, int nCol ,int nRow, int textureId) {//大小，半径，高度，边数，纹理id
		this.textureId=textureId;
		//改变尺寸
		size=Constant.UNIT_SIZE*scale;
		a*=size;
		b*=size;
		c*=size;
		this.b=b;
		angdegColSpan=360.0f/nCol;
		angdegRowSpan=180.0f/nRow;
		vCount=3*nCol*nRow*2;//顶点个数，共有nColumn*nRow*2个三角形，每个三角形都有三个顶点
		//坐标数据初始化
		float[] vertices=new float[vCount*3];
		float[] textures=new float[vCount*2];//顶点纹理S、T坐标值数组
		//坐标数据初始化
		int count=0;
		int stCount=0;
		for(float angdegCol=0;Math.ceil(angdegCol)<360;angdegCol+=angdegColSpan)
		{//---经度
			double angradCol=Math.toRadians(angdegCol);//当前列弧度
			double angradColNext=Math.toRadians(angdegCol+angdegColSpan);//下一列弧度
			for(float angdegRow=-90;Math.ceil(angdegRow)<90;angdegRow+=angdegRowSpan)
			{//---纬度
				double angradRow=Math.toRadians(angdegRow);//当前行弧度
				double angradRowNext=Math.toRadians(angdegRow+angdegRowSpan);//下一行弧度
				//当前行，当前列-----------0
				vertices[count++]=(float) (a*Math.cos(angradRow)*Math.cos(angradCol));
				vertices[count++]=(float) (b*Math.sin(angradRow));
				vertices[count++]=(float) (c*Math.cos(angradRow)*Math.sin(angradCol));
				
				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st坐标
				textures[stCount++]=(float) (1-(angradRow+Math.PI/2)/Math.PI);				
				//下一行，当前列-----------2
				vertices[count++]=(float) (a*Math.cos(angradRowNext)*Math.cos(angradCol));
				vertices[count++]=(float) (b*Math.sin(angradRowNext));
				vertices[count++]=(float) (c*Math.cos(angradRowNext)*Math.sin(angradCol));
				
				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st坐标
				textures[stCount++]=(float) (1-(angradRowNext+Math.PI/2)/Math.PI);
				//下一行，下一列-----------3
				vertices[count++]=(float) (a*Math.cos(angradRowNext)*Math.cos(angradColNext));
				vertices[count++]=(float) (b*Math.sin(angradRowNext));
				vertices[count++]=(float) (c*Math.cos(angradRowNext)*Math.sin(angradColNext));
				
				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st坐标
				textures[stCount++]=(float) (1-(angradRowNext+Math.PI/2)/Math.PI);
				
				
				//当前行，当前列-----------0
				vertices[count++]=(float) (a*Math.cos(angradRow)*Math.cos(angradCol));
				vertices[count++]=(float) (b*Math.sin(angradRow));
				vertices[count++]=(float) (c*Math.cos(angradRow)*Math.sin(angradCol));

				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st坐标
				textures[stCount++]=(float) (1-(angradRow+Math.PI/2)/Math.PI);
				//下一行，下一列-----------3
				vertices[count++]=(float) (a*Math.cos(angradRowNext)*Math.cos(angradColNext));
				vertices[count++]=(float) (b*Math.sin(angradRowNext));
				vertices[count++]=(float) (c*Math.cos(angradRowNext)*Math.sin(angradColNext));

				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st坐标
				textures[stCount++]=(float) (1-(angradRowNext+Math.PI/2)/Math.PI);
				//当前行，下一列-----------1
				vertices[count++]=(float) (a*Math.cos(angradRow)*Math.cos(angradColNext));
				vertices[count++]=(float) (b*Math.sin(angradRow));
				vertices[count++]=(float) (c*Math.cos(angradRow)*Math.sin(angradColNext));
				
				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st坐标
				textures[stCount++]=(float) (1-(angradRow+Math.PI/2)/Math.PI);
			}
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//创建顶点坐标数据缓冲
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        vertexBuffer = vbb.asFloatBuffer();//转换为float型缓冲
        vertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        vertexBuffer.position(0);//设置缓冲区起始位置
        //法向量数据初始化 
		float[] normals=new float[vertices.length];//法向量数组
		int norCount=0;
		for(int i=0;i<vertices.length;i+=3){
			normals[norCount++]=vertices[i+0]/(a*a);
			normals[norCount++]=vertices[i+1]/(b*b);
			normals[norCount++]=vertices[i+2]/(c*c);
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
