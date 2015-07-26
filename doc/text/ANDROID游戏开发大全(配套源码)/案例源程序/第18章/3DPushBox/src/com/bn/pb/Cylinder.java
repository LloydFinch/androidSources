/**
 * 
 * 	���ڻ���Բ��
 */
package com.bn.pb;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
//���ڻ���Բ��
public class Cylinder
{   
    private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mNormalBuffer;//������������
    private FloatBuffer mTextureBuffer;//�����������ݻ���
    public float mAngleX;//��x����ת�Ƕ�
    public float mAngleY;//��y����ת�Ƕ� 
    public float mAngleZ;//��z����ת�Ƕ�   
    int vCount=0;//��������
    
    public Cylinder(float radius,float length,float horizonSpan,float verticalSpan) 
    {
    	//��ȡ�з���ͼ����������
    	float[] texCoorArray= 
         generateTexCoor
    	 (
    			 (int)(360/horizonSpan), //����ͼ�зֵ�����
    			 (int)(length/verticalSpan)  //����ͼ�зֵ�����
    	);
    	
    	ArrayList<Float> alVertex=new ArrayList<Float>();//��Ŷ��������ArrayList	
        for(float tempY=length/2;tempY>-length/2;tempY=tempY-verticalSpan)
        {
        	for(float hAngle=360;hAngle>0;hAngle=hAngle-horizonSpan)
        	{
        		//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ��ı��ζ�������
        		//��������������ı��ε�������
        		float x1=(float)(radius*Math.cos(Math.toRadians(hAngle)));
        		float z1=(float)(radius*Math.sin(Math.toRadians(hAngle)));
        		float y1=tempY;                                          //��һ����
        		
        		float x2=(float)(radius*Math.cos(Math.toRadians(hAngle)));
        		float z2=(float)(radius*Math.sin(Math.toRadians(hAngle)));
        		float y2=tempY-verticalSpan;                              //�ڶ�����
        		
        		float x3=(float)(radius*Math.cos(Math.toRadians(hAngle-horizonSpan)));
        		float z3=(float)(radius*Math.sin(Math.toRadians(hAngle-horizonSpan)));
        		float y3=tempY-verticalSpan;                              //��������
        		
        		float x4=(float)(radius*Math.cos(Math.toRadians(hAngle-horizonSpan)));
        		float z4=(float)(radius*Math.sin(Math.toRadians(hAngle-horizonSpan)));
        		float y4=tempY;                                           //���ĸ���
        		
        		//������һ������
        		alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
        		alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
        		alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);        		
        		//�����ڶ�������
        		alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
        		alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
        		alVertex.add(x3);alVertex.add(y3);alVertex.add(z3); 
        	}
        } 	    
        
        vCount=alVertex.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
        float vertices[]=new float[vCount*3];
    	for(int i=0;i<alVertex.size();i++)
    	{
    		vertices[i]=alVertex.get(i);
    	}
        //�������ƶ������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��     
        //����������������
        int k=0;
        float normal[]=new float[vCount*3];
        for(int i=0;i<vCount;i++)
        {
        	normal[k++]=vertices[i*3];
        	normal[k++]=0;
        	normal[k++]=vertices[i*3+2];
        }
        ByteBuffer nbb = ByteBuffer.allocateDirect(normal.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mNormalBuffer.put(normal);//�򻺳����з��붥����������
        mNormalBuffer.position(0);//���û�������ʼλ��     
        //��������������
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoorArray.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer = tbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mTextureBuffer.put(texCoorArray);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
    }

    public void drawSelf(GL10 gl,int texId)
    {
    	gl.glRotatef(mAngleZ, 0, 0, 1);//��Z����ת
    	gl.glRotatef(mAngleX, 1, 0, 0);//��X����ת
        gl.glRotatef(mAngleY, 0, 1, 0);//��Y����ת
        
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );        
		//��������
        gl.glEnable(GL10.GL_NORMAL_ARRAY);
        gl.glNormalPointer
        (
        		GL10.GL_FLOAT,
        		0,
        		mNormalBuffer
        );
        //��������
        //Ϊ����ָ������ST���껺��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //�󶨵�ǰ����
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
   
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0, 			 			//��ʼ����
        		vCount					//��������
        );        
   }
    
    //�Զ��з����������������ķ���
    public float[] generateTexCoor(int cols,int rows)
    {
    	float[] result=new float[cols*rows*6*2]; 
    	float sizew=1.0f/cols;//����
    	float sizeh=1.0f/rows;//����
    	int c=0;
    	for(int i=0;i<rows;i++)
    	{
    		for(int j=0;j<cols;j++)
    		{
    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
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
