/**
 * 
 * 	用于绘制圆柱
 */
package com.bn.pb;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
//用于绘制圆柱
public class Cylinder
{   
    private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mNormalBuffer;//顶点向量缓冲
    private FloatBuffer mTextureBuffer;//顶点纹理数据缓冲
    public float mAngleX;//沿x轴旋转角度
    public float mAngleY;//沿y轴旋转角度 
    public float mAngleZ;//沿z轴旋转角度   
    int vCount=0;//顶点数量
    
    public Cylinder(float radius,float length,float horizonSpan,float verticalSpan) 
    {
    	//获取切分整图的纹理数组
    	float[] texCoorArray= 
         generateTexCoor
    	 (
    			 (int)(360/horizonSpan), //纹理图切分的列数
    			 (int)(length/verticalSpan)  //纹理图切分的行数
    	);
    	
    	ArrayList<Float> alVertex=new ArrayList<Float>();//存放顶点坐标的ArrayList	
        for(float tempY=length/2;tempY>-length/2;tempY=tempY-verticalSpan)
        {
        	for(float hAngle=360;hAngle>0;hAngle=hAngle-horizonSpan)
        	{
        		//纵向横向各到一个角度后计算对应的此点在球面上的四边形顶点坐标
        		//并构建两个组成四边形的三角形
        		float x1=(float)(radius*Math.cos(Math.toRadians(hAngle)));
        		float z1=(float)(radius*Math.sin(Math.toRadians(hAngle)));
        		float y1=tempY;                                          //第一个点
        		
        		float x2=(float)(radius*Math.cos(Math.toRadians(hAngle)));
        		float z2=(float)(radius*Math.sin(Math.toRadians(hAngle)));
        		float y2=tempY-verticalSpan;                              //第二个点
        		
        		float x3=(float)(radius*Math.cos(Math.toRadians(hAngle-horizonSpan)));
        		float z3=(float)(radius*Math.sin(Math.toRadians(hAngle-horizonSpan)));
        		float y3=tempY-verticalSpan;                              //第三个点
        		
        		float x4=(float)(radius*Math.cos(Math.toRadians(hAngle-horizonSpan)));
        		float z4=(float)(radius*Math.sin(Math.toRadians(hAngle-horizonSpan)));
        		float y4=tempY;                                           //第四个点
        		
        		//构建第一三角形
        		alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        		alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
        		alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);        		
        		//构建第二三角形
        		alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
        		alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
        		alVertex.add(x3);alVertex.add(y3);alVertex.add(z3); 
        	}
        } 	    
        
        vCount=alVertex.size()/3;//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
        float vertices[]=new float[vCount*3];
    	for(int i=0;i<alVertex.size();i++)
    	{
    		vertices[i]=alVertex.get(i);
    	}
        //创建绘制顶点数据缓冲
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为int型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置     
        //创建顶点向量缓冲
        int k=0;
        float normal[]=new float[vCount*3];
        for(int i=0;i<vCount;i++)
        {
        	normal[k++]=vertices[i*3];
        	normal[k++]=0;
        	normal[k++]=vertices[i*3+2];
        }
        ByteBuffer nbb = ByteBuffer.allocateDirect(normal.length*4);
        nbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mNormalBuffer = nbb.asFloatBuffer();//转换为float型缓冲
        mNormalBuffer.put(normal);//向缓冲区中放入顶点坐标数据
        mNormalBuffer.position(0);//设置缓冲区起始位置     
        //创建顶点纹理缓冲
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoorArray.length*4);
        tbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTextureBuffer = tbb.asFloatBuffer();//转换为int型缓冲
        mTextureBuffer.put(texCoorArray);//向缓冲区中放入顶点着色数据
        mTextureBuffer.position(0);//设置缓冲区起始位置
    }

    public void drawSelf(GL10 gl,int texId)
    {
    	gl.glRotatef(mAngleZ, 0, 0, 1);//沿Z轴旋转
    	gl.glRotatef(mAngleX, 1, 0, 0);//沿X轴旋转
        gl.glRotatef(mAngleY, 0, 1, 0);//沿Y轴旋转
        
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );        
		//开启向量
        gl.glEnable(GL10.GL_NORMAL_ARRAY);
        gl.glNormalPointer
        (
        		GL10.GL_FLOAT,
        		0,
        		mNormalBuffer
        );
        //开启纹理
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
   
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0, 			 			//开始点编号
        		vCount					//顶点数量
        );        
   }
    
    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int cols,int rows)
    {
    	float[] result=new float[cols*rows*6*2]; 
    	float sizew=1.0f/cols;//列数
    	float sizeh=1.0f/rows;//行数
    	int c=0;
    	for(int i=0;i<rows;i++)
    	{
    		for(int j=0;j<cols;j++)
    		{
    			//每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;	
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }
    
}
