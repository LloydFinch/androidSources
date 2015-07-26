package com.bn.pb;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import static com.bn.pb.Constant.*;

//��ʾǽ����
public class WallOutSide {
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//�����������ݻ���
    private FloatBuffer   mNormalBuffer;//���㷨�������ݻ���
    int vCount;//��������
    
    public WallOutSide()
    {
    	//�����������ݵĳ�ʼ��================begin============================
        int rows=MAP[COUNT].length;//�õ�����
        int cols=MAP[COUNT][0].length;//�õ�����
        
        ArrayList<Float> alVertex=new ArrayList<Float>();//������������
        ArrayList<Float> alNormal=new ArrayList<Float>();//������������
        ArrayList<Float> alTexture=new ArrayList<Float>();//���㷨����
        
        for(int i=0;i<rows;i++)
        {
        	for(int j=0;j<cols;j++)
        	{//�Ե�ͼ�е�ÿһ����д���
        		if(MAP[COUNT][i][j]==1)
        		{
        	//************************�������ΧΧǽ����Begin**************************************
        			//�ȿ���������һ���Ƿ���Ҫ���Χǽ
        			if(i==0&&MAP[COUNT][i][j]==1)
        			{
        				float x1=j*UNIT_SIZE;
        				float y1=FLOOR_Y;
        				float z1=0;
        				
        				float x2=j*UNIT_SIZE;
        				float y2=FLOOR_Y+WALL_HEIGHT;
        				float z2=0;
        				
        				float x3=(j+1)*UNIT_SIZE;
        				float y3=FLOOR_Y+WALL_HEIGHT;
        				float z3=0;
        				
        				float x4=(j+1)*UNIT_SIZE;
        				float y4=FLOOR_Y;
        				float z4=0;
        				
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
        			
        			//�ٿ��Ǵ˿���һ��������Ƿ���ҪΧǽ
        			if(i==rows-1&&MAP[COUNT][i][j]==1)
        			{
        				//����������һ�л�����һ�鲻��ͨ����˿�������ҪΧǽ
        				//�˾��ε��ĸ�����
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
        				//��һ�������ε��������������
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
        				//�ڶ��������ε��������������
        				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
        				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
        				//��һ�������ε�����
        				alTexture.add(0f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(0f);
        				alTexture.add(0f);alTexture.add(0f);
        				//�ڶ��������ε�����
        				alTexture.add(0f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(1f);
        				alTexture.add(1f);alTexture.add(0f);
        				//��һ�������εĶ��㷨����
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				//�ڶ��������εĶ��㷨����
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
        			} 
        			
        			//�ٿ��Ǵ˿�����һ��������Ƿ���ҪΧǽ
        			if(j==0&&MAP[COUNT][i][j]==1)
        			{
        				float x1=0;
        				float y1=FLOOR_Y;
        				float z1=(i+1)*UNIT_SIZE;
        				
        				float x2=0;
        				float y2=FLOOR_Y+WALL_HEIGHT;
        				float z2=(i+1)*UNIT_SIZE;
        				
        				float x3=0;
        				float y3=FLOOR_Y+WALL_HEIGHT;
        				float z3=i*UNIT_SIZE;
        				
        				float x4=0;
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
        			
        			//�ٿ��Ǵ˿�����һ��������Ƿ���ҪΧǽ
        			if(j==cols-1&&MAP[COUNT][i][j]==1)
        			{
        				//����������һ�л�����һ�鲻��ͨ����˿�������ҪΧǽ
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
        	//************************�������ΧΧǽ����End**************************************
        		}
        	}
        }
    	
    	
    	
    	vCount=alVertex.size()/3;        
        float vertices[]=new float[alVertex.size()];
        for(int i=0;i<alVertex.size();i++)
        {
        	vertices[i]=alVertex.get(i);
        }
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        
        //���㷨�������ݳ�ʼ��================begin============================
        float normals[]=new float[vCount*3];
        for(int i=0;i<vCount*3;i++)
        {
        	normals[i]=alNormal.get(i);
        }
        
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥����ɫ����
        mNormalBuffer.position(0);//���û�������ʼλ��
        //���㷨�������ݳ�ʼ��================end============================ 
        
        //�����������ݵĳ�ʼ��================begin============================
        float textures[]=new float[alTexture.size()];
        for(int i=0;i<alTexture.size();i++)
        {
        	textures[i]=alTexture.get(i);
        }

        
        //���������������ݻ���
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
    }

    public void drawSelf(GL10 gl,int texId)
    {               
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
        
        //Ϊ����ָ�����㷨��������
        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
		
        
        //Ϊ����ָ������ST���껺��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //�󶨵�ǰ����
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0,
        		vCount 
        );
        
       
    }
}
