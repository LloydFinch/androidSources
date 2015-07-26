package com.bn.game.chap11.ex14;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Quadrangle {
	private FloatBuffer   vertexBuffer;//�����������ݻ���
	private FloatBuffer   normalBuffer;//���㷨�������ݻ���
	private FloatBuffer   textureBuffer;//�����������ݻ���
    int vCount=0;//�������
    float size;//�ߴ�
    float xAngle=0;//��z����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    int textureId;//����id
	public Quadrangle(
			float scale,//��С
			float x0,float y0,float z0,//�ı����ĸ������꣬�����Ͻǿ�ʼ��ʱ�����
			float x1,float y1,float z1,
			float x2,float y2,float z2,
			float x3,float y3,float z3,
			int textureId, //����id
			int nS, int nT) //�����зַ���
	{
		this.textureId=textureId;
		//�ı�ߴ�
		size=Constant.UNIT_SIZE*scale;
		x0*=size; y0*=size; z0*=size;
		x1*=size; y1*=size; z1*=size;
		x2*=size; y2*=size; z2*=size;
		x3*=size; y3*=size; z3*=size;
		vCount=6;
		//�������ݳ�ʼ��
		float[] vertices=new float[]{
			x0, y0, z0,
			x1, y1, z1,
			x2, y2, z2,
        	
			x0, y0, z0,
			x2, y2, z2,
			x3, y3, z3,
		};
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        vertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        vertexBuffer.put(vertices);//�򻺳����з��붥����������
        vertexBuffer.position(0);//���û�������ʼλ��
		//���������ݳ�ʼ��
		float[] normals=new float[vertices.length];//����������
		int norCount=0;
		
		for(int i=0;i<vertices.length;i+=9){
			float [] norXYZ=VectorUtil.calTriangleNormal(//ͨ�������������������
					vertices[i+0], vertices[i+1], vertices[i+2], 
					vertices[i+3], vertices[i+4], vertices[i+5], 
					vertices[i+6], vertices[i+7], vertices[i+8]);
			for(int k=0;k<3;k++){//������������ķ���������ͬ
				normals[norCount++]=norXYZ[0];
				normals[norCount++]=norXYZ[1];
				normals[norCount++]=norXYZ[2];
			}
		}
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);//�������㷨�������ݻ���
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        normalBuffer = nbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        normalBuffer.put(normals);//�򻺳����з��붥�㷨��������
        normalBuffer.position(0);//���û�������ʼλ��
        //�������ݵĳ�ʼ��
        float[] textures=new float[]{//��������S��T����ֵ����
        		0,0,	//0
        		0,nT,	//1
        		nS,nT,	//2
        		
        		0,0,	//0
        		nS,nT,	//2
        		nS,0,	//3
        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(textures.length*4);//���������������ݻ���
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        textureBuffer = cbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        textureBuffer.put(textures);//�򻺳����з��붥����������
        textureBuffer.position(0);//���û�������ʼλ��
	}
    public void drawSelf(GL10 gl)
    {        
    	gl.glPushMatrix();
    	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//���ö��㷨��������
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//���ö�����������
        //������ת
        gl.glRotatef(xAngle, 1, 0, 0);
        gl.glRotatef(yAngle, 0, 1, 0);
        gl.glRotatef(zAngle, 0, 0, 1);
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FLOAT
        		0, 				//����������������֮��ļ��
        		vertexBuffer	//������������
        );
        //Ϊ����ָ�����㷨��������
        gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
        //Ϊ����ָ������ST���껺��
        gl.glEnable(GL10.GL_TEXTURE_2D); //��������
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);//Ϊ����ָ������ST���껺��
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);//�󶨵�ǰ����
		
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0, 			 			//��ʼ����
        		vCount					//���������
        );
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//���ö��㷨��������
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//���ö�����������
        gl.glPopMatrix();
    }
}
