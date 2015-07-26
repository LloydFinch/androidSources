package com.bn.pb;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.pb.Constant.*;

//表示墙的类
public class Wall {
	private FloatBuffer   mVertexBuffer;//顶点坐标数据缓冲
    private FloatBuffer   mTextureBuffer;//顶点纹理数据缓冲
    private FloatBuffer   mNormalBuffer;//顶点法向量数据缓冲
    int vCount;//顶点数量
    
    public Wall()
    {
    	//顶点坐标数据的初始化================begin============================
        int rows=MAP[COUNT].length;//得到行数
        int cols=MAP[COUNT][0].length;//得到列数
        
        ArrayList<Float> alVertex=new ArrayList<Float>();//顶点坐标数组
        ArrayList<Float> alNormal=new ArrayList<Float>();//纹理坐标数组
        ArrayList<Float> alTexture=new ArrayList<Float>();//顶点法向量
        
        for(int i=0;i<rows;i++)
        {
        	for(int j=0;j<cols;j++)
        	{//对地图中的每一块进行处理
        		if(MAP[COUNT][i][j]!=1)//若是可通过区域则要考虑围墙问题
        		{
        	//************************解决围墙问题Begin**************************************
        			//先考虑此块上一行是否需要围墙
        			if(i==0||MAP[COUNT][i-1][j]==1)
        			{//若是最上面一行或上面一块不可通过则此块上面需要围墙
        				//此矩形的四个顶点
        				float x1=j*UNIT_SIZE;
        				float y1=FLOOR_Y;
        				float z1=i*UNIT_SIZE;
        				
        				float x2=j*UNIT_SIZE;
        				float y2=FLOOR_Y+WALL_HEIGHT;
        				float z2=i*UNIT_SIZE;
        				
        				float x3=(j+1)*UNIT_SIZE;
        				float y3=FLOOR_Y+WALL_HEIGHT;
        				float z3=i*UNIT_SIZE;
        				
        				float x4=(j+1)*UNIT_SIZE;
        				float y4=FLOOR_Y;
        				float z4=i*UNIT_SIZE;
        				//第一个三角形的三个点放入数组
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
        				//第二个三角形的三个点放入数组
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				//第一个三角形的纹理
        				alTexture.add(0f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(0f);
        				alTexture.add(0f);alTexture.add(0f);
        				//第二个三角形的纹理
        				alTexture.add(0f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(0f);
        				//第一个三角形的顶点法向量
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				//第二个三角形的顶点法向量
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        			} 
        			
        			//再考虑此块下一行是否需要围墙
        			if(i==rows-1||MAP[COUNT][i+1][j]==1)
        			{//若是最下面一行或下面一块不可通过则此块下面需要围墙
        				float x1=j*UNIT_SIZE;
        				float y1=FLOOR_Y;
        				float z1=(i+1)*UNIT_SIZE;
        				
        				float x2=j*UNIT_SIZE;
        				float y2=FLOOR_Y+WALL_HEIGHT;
        				float z2=(i+1)*UNIT_SIZE;
        				
        				float x3=(j+1)*UNIT_SIZE;
        				float y3=FLOOR_Y+WALL_HEIGHT;
        				float z3=(i+1)*UNIT_SIZE;
        				
        				float x4=(j+1)*UNIT_SIZE;
        				float y4=FLOOR_Y;
        				float z4=(i+1)*UNIT_SIZE;
        				
        				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				
        				alTexture.add(0f);alTexture.add(0f);
        				alTexture.add(1f);alTexture.add(0f);
        				alTexture.add(0f);alTexture.add(1f);
        				
        				alTexture.add(1f);alTexture.add(0f);
        				alTexture.add(1f);alTexture.add(1f);
        				alTexture.add(0f);alTexture.add(1f);
        				
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
        				
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
        			} 
        			
        			//再考虑此块左面一列是否需要围墙
        			if(j==0||MAP[COUNT][i][j-1]==1)
        			{//若是最左面一列或左面一块不可通过则此块左面需要围墙
        				float x1=j*UNIT_SIZE;
        				float y1=FLOOR_Y;
        				float z1=(i+1)*UNIT_SIZE;
        				
        				float x2=j*UNIT_SIZE;
        				float y2=FLOOR_Y+WALL_HEIGHT;
        				float z2=(i+1)*UNIT_SIZE;
        				
        				float x3=j*UNIT_SIZE;
        				float y3=FLOOR_Y+WALL_HEIGHT;
        				float z3=i*UNIT_SIZE;
        				
        				float x4=j*UNIT_SIZE;
        				float y4=FLOOR_Y;
        				float z4=i*UNIT_SIZE;
        				
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
        				
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				
        				alTexture.add(0f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(0f);
        				alTexture.add(0f);alTexture.add(0f);
        				
        				alTexture.add(0f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(0f);
        				
        				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
        				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
        				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
        				
        				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
        				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
        				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
        			} 
        			
        			//再考虑此块右面一列是否需要围墙
        			if(j==cols-1||MAP[COUNT][i][j+1]==1)
        			{//若是最右面一列或右面一块不可通过则此块右面需要围墙
        				
        				float x1=(j+1)*UNIT_SIZE;
        				float y1=FLOOR_Y;
        				float z1=(i+1)*UNIT_SIZE;
        				
        				float x2=(j+1)*UNIT_SIZE;
        				float y2=FLOOR_Y+WALL_HEIGHT;
        				float z2=(i+1)*UNIT_SIZE;
        				
        				float x3=(j+1)*UNIT_SIZE;
        				float y3=FLOOR_Y+WALL_HEIGHT;
        				float z3=i*UNIT_SIZE;
        				
        				float x4=(j+1)*UNIT_SIZE;
        				float y4=FLOOR_Y;
        				float z4=i*UNIT_SIZE;
        				
        				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				
        				alTexture.add(0f);alTexture.add(0f);
        				alTexture.add(1f);alTexture.add(0f);
        				alTexture.add(0f);alTexture.add(1f);
        				
        				alTexture.add(1f);alTexture.add(0f);
        				alTexture.add(1f);alTexture.add(1f);
        				alTexture.add(0f);alTexture.add(1f);
        				
        				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
        				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
        				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
        				
        				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
        				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
        				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
        			}
        	//************************解决围墙问题End**************************************
        		}
        		else if(MAP[COUNT][i][j]==1){
        	//************************解决与地板平行的墙面问题Begin**************************************
            			float xx1=j*UNIT_SIZE;
            			float y=FLOOR_Y+WALL_HEIGHT;
            			float zz1=i*UNIT_SIZE;
            			
            			float xx2=j*UNIT_SIZE;
            			float zz2=(i+1)*UNIT_SIZE;
            			
            			float xx3=(j+1)* UNIT_SIZE;
            			float zz3=(i+1)*UNIT_SIZE;
            			
            			float xx4=(j+1)*UNIT_SIZE;
            			float zz4=i*UNIT_SIZE;
            			
            			alVertex.add(xx1);alVertex.add(y);alVertex.add(zz1);
        				alVertex.add(xx2);alVertex.add(y);alVertex.add(zz2);
        				alVertex.add(xx3);alVertex.add(y);alVertex.add(zz3);

        				alVertex.add(xx3);alVertex.add(y);alVertex.add(zz3);
        				alVertex.add(xx4);alVertex.add(y);alVertex.add(zz4);
        				alVertex.add(xx1);alVertex.add(y);alVertex.add(zz1);

        				alTexture.add(0f);alTexture.add(0f);
        				alTexture.add(0f);alTexture.add(1f);        				
        				alTexture.add(1f);alTexture.add(1f);
        				
        				alTexture.add(1f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(0f);
        				alTexture.add(0f);alTexture.add(0f);
        				
        				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
        				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
        				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
        				
        				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
        				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
        				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
        	//************************解决与地板平行的墙面问题Begin**************************************
        		}
        	}
        }
    	
    	
    	
    	vCount=alVertex.size()/3;        
        float vertices[]=new float[alVertex.size()];
        for(int i=0;i<alVertex.size();i++)
        {
        	vertices[i]=alVertex.get(i);
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
        
        //顶点法向量数据初始化================begin============================
        float normals[]=new float[vCount*3];
        for(int i=0;i<vCount*3;i++)
        {
        	normals[i]=alNormal.get(i);
        }
        
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);
        nbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mNormalBuffer = nbb.asFloatBuffer();//转换为int型缓冲
        mNormalBuffer.put(normals);//向缓冲区中放入顶点着色数据
        mNormalBuffer.position(0);//设置缓冲区起始位置
        //顶点法向量数据初始化================end============================ 
        
        //顶点纹理数据的初始化================begin============================
        float textures[]=new float[alTexture.size()];
        for(int i=0;i<alTexture.size();i++)
        {
        	textures[i]=alTexture.get(i);
        }

        
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

    public void drawSelf(GL10 gl,int texId)
    {               
		//为画笔指定顶点坐标数据
        gl.glVertexPointer
        (
        		3,				//每个顶点的坐标数量为3  xyz 
        		GL10.GL_FLOAT,	//顶点坐标值的类型为 GL_FIXED
        		0, 				//连续顶点坐标数据之间的间隔
        		mVertexBuffer	//顶点坐标数据
        );
        
        //为画笔指定顶点法向量数据
        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
		
        
        //为画笔指定纹理ST坐标缓冲
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //绑定当前纹理
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //绘制图形
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//以三角形方式填充
        		0,
        		vCount 
        );
        
       
    }
}
