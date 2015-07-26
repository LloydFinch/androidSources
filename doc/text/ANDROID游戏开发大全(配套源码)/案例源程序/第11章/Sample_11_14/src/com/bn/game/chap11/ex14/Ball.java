package com.bn.game.chap11.ex14;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
//���㷨��Բ�������ƶ�����
public class Ball {
	private FloatBuffer   vertexBuffer;//�����������ݻ���
	private FloatBuffer   textureBuffer;//�����������ݻ���
	private FloatBuffer   normalBuffer;//���㷨�������ݻ���
    int vCount=0;//�������
    float size;//�ߴ�
    float angdegColSpan;//�������зֽǶ�
    float angdegRowSpan;//������зֽǶ�
    float xAngle=0;//��z����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    int textureId;//����id
    float r;
	public Ball(float scale,float r, int nCol ,int nRow, int textureId) {//��С���뾶���߶ȣ�����������id
		this.textureId=textureId;
		//�ı�ߴ�
		size=Constant.UNIT_SIZE*scale;
		r*=size;
		//��Ա������ʼ��
		this.r=r;
		angdegColSpan=360.0f/nCol;
		angdegRowSpan=180.0f/nRow;
		vCount=3*nCol*nRow*2;//�������������nColumn*nRow*2�������Σ�ÿ�������ζ�����������
		//�������ݳ�ʼ��
		float[] vertices=new float[vCount*3];
		float[] textures=new float[vCount*2];//��������S��T����ֵ����
		//�������ݳ�ʼ��
		int count=0;
		int stCount=0;
		for(float angdegCol=0;Math.ceil(angdegCol)<360;angdegCol+=angdegColSpan)
		{
			double angradCol=Math.toRadians(angdegCol);//��ǰ�л���
			double angradColNext=Math.toRadians(angdegCol+angdegColSpan);//��һ�л���
			for(float angdegRow=0;Math.ceil(angdegRow)<180;angdegRow+=angdegRowSpan)
			{
				double angradRow=Math.toRadians(angdegRow);//��ǰ�л���
				double angradRowNext=Math.toRadians(angdegRow+angdegRowSpan);//��һ�л���
				float rCircle=(float) (r*Math.sin(angradRow));//��ǰ����Բ�İ뾶
				float rCircleNext=(float) (r*Math.sin(angradRowNext));//��һ����Բ�İ뾶
				
				//��ǰ�У���ǰ��---0
				vertices[count++]=(float) (-rCircle*Math.sin(angradCol));
				vertices[count++]=(float) (r*Math.cos(angradRow));
				vertices[count++]=(float) (-rCircle*Math.cos(angradCol));
				
				textures[stCount++]=(float) (angradCol/(2*Math.PI));//st����
				textures[stCount++]=(float) (angradRow/Math.PI);
				//��һ�У���ǰ��---2
				vertices[count++]=(float) (-rCircleNext*Math.sin(angradCol));
				vertices[count++]=(float) (r*Math.cos(angradRowNext));
				vertices[count++]=(float) (-rCircleNext*Math.cos(angradCol));
				
				textures[stCount++]=(float) (angradCol/(2*Math.PI));//st����
				textures[stCount++]=(float) (angradRowNext/Math.PI);
				//��һ�У���һ��---3
				vertices[count++]=(float) (-rCircleNext*Math.sin(angradColNext));
				vertices[count++]=(float) (r*Math.cos(angradRowNext));
				vertices[count++]=(float) (-rCircleNext*Math.cos(angradColNext));
				
				textures[stCount++]=(float) (angradColNext/(2*Math.PI));//st����
				textures[stCount++]=(float) (angradRowNext/Math.PI);
				
				
				//��ǰ�У���ǰ��---0
				vertices[count++]=(float) (-rCircle*Math.sin(angradCol));
				vertices[count++]=(float) (r*Math.cos(angradRow));
				vertices[count++]=(float) (-rCircle*Math.cos(angradCol));

				textures[stCount++]=(float) (angradCol/(2*Math.PI));//st����
				textures[stCount++]=(float) (angradRow/Math.PI);
				//��һ�У���һ��---3
				vertices[count++]=(float) (-rCircleNext*Math.sin(angradColNext));
				vertices[count++]=(float) (r*Math.cos(angradRowNext));
				vertices[count++]=(float) (-rCircleNext*Math.cos(angradColNext));

				textures[stCount++]=(float) (angradColNext/(2*Math.PI));//st����
				textures[stCount++]=(float) (angradRowNext/Math.PI);
				//��ǰ�У���һ��---1
				vertices[count++]=(float) (-rCircle*Math.sin(angradColNext));
				vertices[count++]=(float) (r*Math.cos(angradRow));
				vertices[count++]=(float) (-rCircle*Math.cos(angradColNext));
				
				textures[stCount++]=(float) (angradColNext/(2*Math.PI));//st����
				textures[stCount++]=(float) (angradRow/Math.PI);
			}
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        vertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        vertexBuffer.put(vertices);//�򻺳����з��붥����������
        vertexBuffer.position(0);//���û�������ʼλ��
        //���������ݳ�ʼ��        
        VectorUtil.normalizeAllVectors(vertices);//��񻯷�����
        
        ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length*4);//�������㷨�������ݻ���
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        normalBuffer = nbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        normalBuffer.put(vertices);//�򻺳����з��붥�㷨��������
        normalBuffer.position(0);//���û�������ʼλ��
        //st�������ݳ�ʼ��
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);//���������������ݻ���
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        textureBuffer = tbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
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
