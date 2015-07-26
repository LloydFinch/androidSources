package com.bn.game.chap11.ex14;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
//���㷨��Բ�������ƶ�����
public class Circle {
	private FloatBuffer   vertexBuffer;//�����������ݻ���
	private FloatBuffer   normalBuffer;//���㷨�������ݻ���
	private FloatBuffer   textureBuffer;//�����������ݻ���
    int vCount=0;//�������
    float size;//�ߴ�
    float angdegSpan;//ÿ�������ζ���
    float xAngle=0;//��z����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    int textureId;//����id
	public Circle(float scale,float r, int n, int textureId) {//��С���뾶������
		this.textureId=textureId;
		//�ı�ߴ�
		size=Constant.UNIT_SIZE*scale;
		r*=size;
		angdegSpan=360.0f/n;
		vCount=3*n;//�������������n�������Σ�ÿ�������ζ�����������
		
		float[] vertices=new float[vCount*3];//��������
		float[] textures=new float[vCount*2];//��������S��T����ֵ����
		//�������ݳ�ʼ��
		int count=0;
		int stCount=0;
		for(float angdeg=0;Math.ceil(angdeg)<360;angdeg+=angdegSpan)
		{
			double angrad=Math.toRadians(angdeg);//��ǰ����
			double angradNext=Math.toRadians(angdeg+angdegSpan);//��һ����
			//���ĵ�
			vertices[count++]=0;//��������
			vertices[count++]=0; 
			vertices[count++]=0;
			
			textures[stCount++]=0.5f;//st����
			textures[stCount++]=0.5f;
			//��ǰ��
			vertices[count++]=(float) (-r*Math.sin(angrad));//��������
			vertices[count++]=(float) (r*Math.cos(angrad));
			vertices[count++]=0;
			
			textures[stCount++]=(float) (0.5f-0.5f*Math.sin(angrad));//st����
			textures[stCount++]=(float) (0.5f-0.5f*Math.cos(angrad));
			//��һ��
			vertices[count++]=(float) (-r*Math.sin(angradNext));//��������
			vertices[count++]=(float) (r*Math.cos(angradNext));
			vertices[count++]=0;
			
			textures[stCount++]=(float) (0.5f-0.5f*Math.sin(angradNext));//st����
			textures[stCount++]=(float) (0.5f-0.5f*Math.cos(angradNext));
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        vertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        vertexBuffer.put(vertices);//�򻺳����з��붥����������
        vertexBuffer.position(0);//���û�������ʼλ��
        //���������ݳ�ʼ�� 
        float[] normals=new float[vertices.length];
        for(int i=0;i<normals.length;i+=3){
        	normals[i]=0;
        	normals[i+1]=0;
        	normals[i+2]=1;
        }
        VectorUtil.normalizeAllVectors(normals);//��񻯷�����
        
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);//�������㷨�������ݻ���
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        normalBuffer = nbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        normalBuffer.put(normals);//�򻺳����з��붥�㷨��������
        normalBuffer.position(0);//���û�������ʼλ��
        //st�������ݳ�ʼ��
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
