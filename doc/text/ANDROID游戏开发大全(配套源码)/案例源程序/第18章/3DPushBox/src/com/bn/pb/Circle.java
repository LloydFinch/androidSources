/**
 * 
 * 	���ڻ���Բ
 */
package com.bn.pb;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
//��triangle_fan��ʽ����Բ��
public class Circle
{
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//�����������ݻ���
    private int vCount;
 
    public Circle
    (
    		float angleSpan,//�зֽǶ�
    		float radius,//Բ�뾶
    		float[] centerTexCoor,//�������ĵ�
    		float textureRadius//����뾶
    )
    {
    	
    	//���������������ݵĳ�ʼ��================begin============================
    	vCount=1+(int)(360/angleSpan)+1;//����ĸ���
    	
    	float[] vertices=new float[vCount*3];//��ʼ����������
    	float[] textures=new float[vCount*2];//��ʼ��������������
    	
    	//������ĵ�����
    	vertices[0]=0;
    	vertices[1]=0;
    	vertices[2]=0;
    	
    	//������ĵ�����
    	textures[0]=centerTexCoor[0];
    	textures[1]=centerTexCoor[1];
    	
    	int vcount=3;//��ǰ������������
    	int tcount=2;//��ǰ������������
    	
    	for(float angle=0;angle<=360;angle=angle+angleSpan)
    	{
    		double angleRadian=Math.toRadians(angle);
    		//��������
    		vertices[vcount++]=radius*(float)Math.cos(angleRadian);
    		vertices[vcount++]=radius*(float)Math.sin(angleRadian);
    		vertices[vcount++]=0;
    		//��������
    		textures[tcount++]=centerTexCoor[0]+textureRadius*(float)Math.cos(angleRadian);
    		textures[tcount++]=centerTexCoor[1]+textureRadius*(float)Math.sin(angleRadian);
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

    public void drawSelf(GL10 gl,int textureId)
    {        
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ�� /���
        		mVertexBuffer	//������������
        );
        //Ϊ����ָ������ST���껺��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //�󶨵�ǰ����
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLE_FAN, 		//��TRIANGLE_FAN��ʽ���
        		0,
        		vCount
        );
    }
}
