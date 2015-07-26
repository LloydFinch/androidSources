package com.bn.game.chap11.ex1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Diamond {
	private FloatBuffer   vertexBuffer;//�����������ݻ���
    private FloatBuffer   colorBuffer;//������ɫ���ݻ���
    private ByteBuffer  indexBuffer;//���㹹���������ݻ���
    int vCount=0;//�������
    int iCount=0;
    float size;//�ߴ�
    float xAngle=0;//��z����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
	public Diamond(float scale,float a, float b) {
		size=Constant.UNIT_SIZE*scale;
		vCount=3;
		//�������ݳ�ʼ��
		float[] vertices=new float[]{
			-a*size, 0*size, 0*size,
			0*size, -b*size, 0*size,
			a*size, 0*size, 0*size,
			0*size, b*size, 0*size,
		};
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        vertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        vertexBuffer.put(vertices);//�򻺳����з��붥����������
        vertexBuffer.position(0);//���û�������ʼλ��
        
        //��ɫ���ݵĳ�ʼ��
        float[] colors=new float[]{//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
        		1,0,0,0,
        		0,0,1,0,
        		0,1,0,0,
        		1,1,1,0,
        };    
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);//����������ɫ���ݻ���
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        colorBuffer = cbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        colorBuffer.put(colors);//�򻺳����з��붥����ɫ����
        colorBuffer.position(0);//���û�������ʼλ��
        
        //�����ι����������ݳ�ʼ��
        iCount=6;
        byte indexes[]=new byte[]{//��������������
        	0,2,3,//��ʱ��
        	0,2,1,//˳ʱ��
        };
        indexBuffer = ByteBuffer.allocateDirect(indexes.length);//���������ι����������ݻ���
        indexBuffer.put(indexes);//�򻺳����з��������ι�����������
        indexBuffer.position(0);//���û�������ʼλ��
	}
    public void drawSelf(GL10 gl)
    {        
    	gl.glPushMatrix();
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//���ö�����ɫ����
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
		
        //Ϊ����ָ��������ɫ����
        gl.glColorPointer
        (
        		4, 				//������ɫ����ɳɷ֣�����Ϊ4��RGBA
        		GL10.GL_FLOAT, 	//������ɫֵ������Ϊ GL_FLOAT
        		0, 				//����������ɫ����֮��ļ��
        		colorBuffer		//������ɫ����
        );
		
        //����ͼ��
        gl.glDrawElements
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		iCount, 			 	//�������
        		GL10.GL_UNSIGNED_BYTE, 	//����ֵ�ĳߴ�
        		indexBuffer				//����ֵ����
        ); 
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//���ö�����ɫ����
        gl.glPopMatrix();
    }
}
