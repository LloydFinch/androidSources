package com.bn.game.chap11.ex3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Circle {
	private FloatBuffer   vertexBuffer;//�����������ݻ���
    private FloatBuffer   colorBuffer;//������ɫ���ݻ���
    private ByteBuffer  indexBuffer;//���㹹���������ݻ���
    int vCount=0;//�������
    int iCount=0;
    float size;//�ߴ�
    float angdegSpan;//ÿ�������ζ���
    float xAngle=0;//��z����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
	public Circle(float scale,float r, int n, float zOffset) {//��С���뾶�����������������ܳ���126��������
		size=Constant.UNIT_SIZE*scale;
		angdegSpan=360.0f/n;
		vCount=n+1;//��������ĵ�
		//�������ݳ�ʼ��
		float[] vertices=new float[vCount*3];
		int count=0;
		vertices[count++]=0; vertices[count++]=0; vertices[count++]=zOffset*size;//���ĵ�
		for(float angdeg=0;Math.ceil(angdeg)<360;angdeg+=angdegSpan)
		{
			double angrad=Math.toRadians(angdeg);
			vertices[count++]=(float) (-r*Math.sin(angrad))*size;
			vertices[count++]=(float) (r*Math.cos(angrad))*size;
			vertices[count++]=zOffset*size;
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        vertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        vertexBuffer.put(vertices);//�򻺳����з��붥����������
        vertexBuffer.position(0);//���û�������ʼλ��
        
        //��ɫ���ݵĳ�ʼ��
        float[] colors=new float[vCount*4];//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
        count=0;
    	colors[count++]=1;//���ĵ��rgba
    	colors[count++]=1;
    	colors[count++]=1;
    	colors[count++]=0;
        for(int i=1;i<vCount;i++){ //Ϊÿ�������������һ����ɫ
        	colors[count++]=(float) Math.random();
        	colors[count++]=(float) Math.random();
        	colors[count++]=(float) Math.random();
        	colors[count++]=1;
        }
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);//����������ɫ���ݻ���
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        colorBuffer = cbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        colorBuffer.put(colors);//�򻺳����з��붥����ɫ����
        colorBuffer.position(0);//���û�������ʼλ��
        
        //�����ι����������ݳ�ʼ��
        iCount=n+2;
        byte indexes[]=new byte[iCount];//��������������        
        for(byte i=0;i<indexes.length-1;i++){
        	indexes[i]=i;
        }
        indexes[indexes.length-1]=1;//�ظ���һ���㣬ʹԲ�պ�        
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
        		GL10.GL_TRIANGLE_FAN, 	//��RIANGLE_FAN��ʽ���
        		iCount, 			 	//�������
        		GL10.GL_UNSIGNED_BYTE, 	//����ֵ�ĳߴ�
        		indexBuffer				//����ֵ����
        ); 
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//���ö�����ɫ����
        gl.glPopMatrix();
    }
}
