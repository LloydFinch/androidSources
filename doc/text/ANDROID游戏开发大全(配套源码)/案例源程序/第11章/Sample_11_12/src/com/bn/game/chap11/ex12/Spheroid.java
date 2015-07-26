package com.bn.game.chap11.ex12;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
//���㷨��Բ�������ƶ�����
public class Spheroid {
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
    float b;
	public Spheroid(float scale, float a, float b, float c, int nCol ,int nRow, int textureId) {//��С���뾶���߶ȣ�����������id
		this.textureId=textureId;
		//�ı�ߴ�
		size=Constant.UNIT_SIZE*scale;
		a*=size;
		b*=size;
		c*=size;
		this.b=b;
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
		{//---����
			double angradCol=Math.toRadians(angdegCol);//��ǰ�л���
			double angradColNext=Math.toRadians(angdegCol+angdegColSpan);//��һ�л���
			for(float angdegRow=-90;Math.ceil(angdegRow)<90;angdegRow+=angdegRowSpan)
			{//---γ��
				double angradRow=Math.toRadians(angdegRow);//��ǰ�л���
				double angradRowNext=Math.toRadians(angdegRow+angdegRowSpan);//��һ�л���
				//��ǰ�У���ǰ��-----------0
				vertices[count++]=(float) (a*Math.cos(angradRow)*Math.cos(angradCol));
				vertices[count++]=(float) (b*Math.sin(angradRow));
				vertices[count++]=(float) (c*Math.cos(angradRow)*Math.sin(angradCol));
				
				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st����
				textures[stCount++]=(float) (1-(angradRow+Math.PI/2)/Math.PI);				
				//��һ�У���ǰ��-----------2
				vertices[count++]=(float) (a*Math.cos(angradRowNext)*Math.cos(angradCol));
				vertices[count++]=(float) (b*Math.sin(angradRowNext));
				vertices[count++]=(float) (c*Math.cos(angradRowNext)*Math.sin(angradCol));
				
				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st����
				textures[stCount++]=(float) (1-(angradRowNext+Math.PI/2)/Math.PI);
				//��һ�У���һ��-----------3
				vertices[count++]=(float) (a*Math.cos(angradRowNext)*Math.cos(angradColNext));
				vertices[count++]=(float) (b*Math.sin(angradRowNext));
				vertices[count++]=(float) (c*Math.cos(angradRowNext)*Math.sin(angradColNext));
				
				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st����
				textures[stCount++]=(float) (1-(angradRowNext+Math.PI/2)/Math.PI);
				
				
				//��ǰ�У���ǰ��-----------0
				vertices[count++]=(float) (a*Math.cos(angradRow)*Math.cos(angradCol));
				vertices[count++]=(float) (b*Math.sin(angradRow));
				vertices[count++]=(float) (c*Math.cos(angradRow)*Math.sin(angradCol));

				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st����
				textures[stCount++]=(float) (1-(angradRow+Math.PI/2)/Math.PI);
				//��һ�У���һ��-----------3
				vertices[count++]=(float) (a*Math.cos(angradRowNext)*Math.cos(angradColNext));
				vertices[count++]=(float) (b*Math.sin(angradRowNext));
				vertices[count++]=(float) (c*Math.cos(angradRowNext)*Math.sin(angradColNext));

				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st����
				textures[stCount++]=(float) (1-(angradRowNext+Math.PI/2)/Math.PI);
				//��ǰ�У���һ��-----------1
				vertices[count++]=(float) (a*Math.cos(angradRow)*Math.cos(angradColNext));
				vertices[count++]=(float) (b*Math.sin(angradRow));
				vertices[count++]=(float) (c*Math.cos(angradRow)*Math.sin(angradColNext));
				
				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st����
				textures[stCount++]=(float) (1-(angradRow+Math.PI/2)/Math.PI);
			}
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        vertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        vertexBuffer.put(vertices);//�򻺳����з��붥����������
        vertexBuffer.position(0);//���û�������ʼλ��
        //���������ݳ�ʼ�� 
		float[] normals=new float[vertices.length];//����������
		int norCount=0;
		for(int i=0;i<vertices.length;i+=3){
			normals[norCount++]=vertices[i+0]/(a*a);
			normals[norCount++]=vertices[i+1]/(b*b);
			normals[norCount++]=vertices[i+2]/(c*c);
		}
		VectorUtil.normalizeAllVectors(vertices);//��񻯷�����
		
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
