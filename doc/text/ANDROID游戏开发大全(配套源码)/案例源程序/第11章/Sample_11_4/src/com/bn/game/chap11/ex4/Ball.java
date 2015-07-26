package com.bn.game.chap11.ex4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
//���㷨��Բ�������ƶ�����
public class Ball {
	private FloatBuffer   vertexBuffer;//�����������ݻ���
    private FloatBuffer   normalBuffer;//���㷨�������ݻ���
    int vCount=0;//�������
    float size;//�ߴ�
    float angdegColSpan;//�������зֽǶ�
    float angdegRowSpan;//������зֽǶ�
    float xAngle=0;//��z����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
	public Ball(float scale,float r, int nCol ,int nRow) {//��С���뾶������������
		//�ı�ߴ�
		size=Constant.UNIT_SIZE*scale;
		r*=size;
		angdegColSpan=360.0f/nCol;
		angdegRowSpan=180.0f/nRow;
		vCount=3*nCol*nRow*2;//�������������nColumn*nRow*2�������Σ�ÿ�������ζ�����������
		//�������ݳ�ʼ��
		float[] vertices=new float[vCount*3];
		int count=0;
		for(float angdegCol=0;Math.ceil(angdegCol)<360;angdegCol+=angdegColSpan)//����
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
				//��һ�У���ǰ��---2
				vertices[count++]=(float) (-rCircleNext*Math.sin(angradCol));
				vertices[count++]=(float) (r*Math.cos(angradRowNext));
				vertices[count++]=(float) (-rCircleNext*Math.cos(angradCol));
				//��һ�У���һ��---3
				vertices[count++]=(float) (-rCircleNext*Math.sin(angradColNext));
				vertices[count++]=(float) (r*Math.cos(angradRowNext));
				vertices[count++]=(float) (-rCircleNext*Math.cos(angradColNext));
				
				//��ǰ�У���ǰ��---0
				vertices[count++]=(float) (-rCircle*Math.sin(angradCol));
				vertices[count++]=(float) (r*Math.cos(angradRow));
				vertices[count++]=(float) (-rCircle*Math.cos(angradCol));
				//��һ�У���һ��---3
				vertices[count++]=(float) (-rCircleNext*Math.sin(angradColNext));
				vertices[count++]=(float) (r*Math.cos(angradRowNext));
				vertices[count++]=(float) (-rCircleNext*Math.cos(angradColNext));
				//��ǰ�У���һ��---1
				vertices[count++]=(float) (-rCircle*Math.sin(angradColNext));
				vertices[count++]=(float) (r*Math.cos(angradRow));
				vertices[count++]=(float) (-rCircle*Math.cos(angradColNext));
			}
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        vertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        vertexBuffer.put(vertices);//�򻺳����з��붥����������
        vertexBuffer.position(0);//���û�������ʼλ��
        
        //���������ݳ�ʼ��        
        ByteBuffer cbb = ByteBuffer.allocateDirect(vertices.length*4);//�������㷨�������ݻ���
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        normalBuffer = cbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        normalBuffer.put(vertices);//�򻺳����з��붥�㷨��������
        normalBuffer.position(0);//���û�������ʼλ��
	}
    public void drawSelf(GL10 gl)
    {        
    	gl.glPushMatrix();
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//���ö��㷨��������
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
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0, 			 			//��ʼ����
        		vCount					//���������
        );
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);//���ö��㷨��������
        gl.glPopMatrix();
    }
}
