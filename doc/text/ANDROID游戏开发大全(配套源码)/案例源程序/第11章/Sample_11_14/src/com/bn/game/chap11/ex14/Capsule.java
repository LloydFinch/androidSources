package com.bn.game.chap11.ex14;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
//���㷨��Բ�������ƶ�����
public class Capsule {
	private FloatBuffer   vertexBuffer;//�����������ݻ���
	private FloatBuffer   textureBuffer;//�����������ݻ���
	private FloatBuffer   normalBuffer;//���㷨�������ݻ���
    int vCount=0;//�������
    float size;//�ߴ�
    float angdegColSpan;//�����зֽǶ�
    float angdegRowSpan;//�����зֽǶ�
    float xAngle=0;//��z����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    int textureId;//����id
    float bBottom;
    public Capsule(float scale, float r, float h, float bTop, float bBottom, 
			 int nCol ,int nRow, int textureId) {//��С��x,z���᳤��y���᳤��Բ����߶ȣ�����������
    	this.textureId=textureId;
		//�ı�ߴ�
		size=Constant.UNIT_SIZE*scale;
		r*=size;
		bTop*=size;
		bBottom*=size;
		h*=size;
		this.bBottom=bBottom;
		//����ͼ�ָ��
		float hTotal=h+bTop+bBottom;
		float topTexBegin=0f;
		float topTexEnd=bTop/hTotal;
		float centerTexBegin=topTexEnd;
		float centerTexEnd=(bTop+h)/hTotal;
		float bottomTexBegin=centerTexEnd;
		float bottomTexEnd=1;
		angdegColSpan=360.0f/nCol;
		angdegRowSpan=90.0f/nRow;
		vCount=3*(nCol*nRow*4+nCol*2);//�������������nCol*nRow*4+nCol*2�������Σ�ÿ�������ζ�����������
		//�������ݳ�ʼ��
		float[] vertices=new float[vCount*3];
		float[] textures=new float[vCount*2];//��������S��T����ֵ����	
		float[] normals=new float[vertices.length];//����������
		int count=0;
		int stCount=0;
		int norCount=0;
		for(float angdegCol=0;Math.ceil(angdegCol)<360;angdegCol+=angdegColSpan)//�ϰ�������
		{//---����
			double angradCol=Math.toRadians(angdegCol);//��ǰ�л���
			double angradColNext=Math.toRadians(angdegCol+angdegColSpan);//��һ�л���
			for(float angdegRow=0;Math.ceil(angdegRow)<90;angdegRow+=angdegRowSpan)
			{//---γ��
				double angradRow=Math.toRadians(angdegRow);//��ǰ�л���
				double angradRowNext=Math.toRadians(angdegRow+angdegRowSpan);//��һ�л���
				//��ǰ�У���ǰ��-----------0
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.cos(angradCol));
				vertices[count++]=(float) (bTop*Math.sin(angradRow))+h;
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.sin(angradCol));
				
				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st����
				textures[stCount++]=topTexBegin+(float) ((Math.PI/2-angradRow)/((Math.PI/2)/(topTexEnd-topTexBegin)));					
				//��һ�У���ǰ��-----------2
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.cos(angradCol));
				vertices[count++]=(float) (bTop*Math.sin(angradRowNext))+h;
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.sin(angradCol));
				
				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st����
				textures[stCount++]=topTexBegin+(float) ((Math.PI/2-angradRowNext)/((Math.PI/2)/(topTexEnd-topTexBegin)));
				//��һ�У���һ��-----------3
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.cos(angradColNext));
				vertices[count++]=(float) (bTop*Math.sin(angradRowNext))+h;
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.sin(angradColNext));
				
				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st����
				textures[stCount++]=topTexBegin+(float) ((Math.PI/2-angradRowNext)/((Math.PI/2)/(topTexEnd-topTexBegin)));
				
				
				//��ǰ�У���ǰ��-----------0
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.cos(angradCol));
				vertices[count++]=(float) (bTop*Math.sin(angradRow))+h;
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.sin(angradCol));

				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st����
				textures[stCount++]=topTexBegin+(float) ((Math.PI/2-angradRow)/((Math.PI/2)/(topTexEnd-topTexBegin)));	
				//��һ�У���һ��-----------3
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.cos(angradColNext));
				vertices[count++]=(float) (bTop*Math.sin(angradRowNext))+h;
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.sin(angradColNext));

				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st����
				textures[stCount++]=topTexBegin+(float) ((Math.PI/2-angradRowNext)/((Math.PI/2)/(topTexEnd-topTexBegin)));
				//��ǰ�У���һ��-----------1
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.cos(angradColNext));
				vertices[count++]=(float) (bTop*Math.sin(angradRow))+h;
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.sin(angradColNext));
				
				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st����
				textures[stCount++]=topTexBegin+(float) ((Math.PI/2-angradRow)/((Math.PI/2)/(topTexEnd-topTexBegin)));	
			}
		}
		for(int i=norCount;i<count;i+=3){//�ϰ���������ʼ��
			normals[norCount++]=vertices[i+0]/(r*r);
			normals[norCount++]=(vertices[i+1]-h)/(bTop*bTop);
			normals[norCount++]=vertices[i+2]/(r*r);
		}
		for(float angdegCol=0;Math.ceil(angdegCol)<360;angdegCol+=angdegColSpan)//�°�������
		{//---����
			double angradCol=Math.toRadians(angdegCol);//��ǰ�л���
			double angradColNext=Math.toRadians(angdegCol+angdegColSpan);//��һ�л���
			for(float angdegRow=-90;Math.ceil(angdegRow)<0;angdegRow+=angdegRowSpan)
			{//---γ��
				double angradRow=Math.toRadians(angdegRow);//��ǰ�л���
				double angradRowNext=Math.toRadians(angdegRow+angdegRowSpan);//��һ�л���
				//��ǰ�У���ǰ��-----------0
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.cos(angradCol));
				vertices[count++]=(float) (bBottom*Math.sin(angradRow));
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.sin(angradCol));
				
				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st����
				textures[stCount++]=(float) ((angradRow/(Math.PI/2)+1)*(bottomTexBegin-bottomTexEnd))+bottomTexEnd;
				//��һ�У���ǰ��-----------2
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.cos(angradCol));
				vertices[count++]=(float) (bBottom*Math.sin(angradRowNext));
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.sin(angradCol));
				
				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st����
				textures[stCount++]=(float) ((angradRowNext/(Math.PI/2)+1)*(bottomTexBegin-bottomTexEnd))+bottomTexEnd;
				//��һ�У���һ��-----------3
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.cos(angradColNext));
				vertices[count++]=(float) (bBottom*Math.sin(angradRowNext));
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.sin(angradColNext));
				
				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st����
				textures[stCount++]=(float) ((angradRowNext/(Math.PI/2)+1)*(bottomTexBegin-bottomTexEnd))+bottomTexEnd;
				
				
				//��ǰ�У���ǰ��-----------0
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.cos(angradCol));
				vertices[count++]=(float) (bBottom*Math.sin(angradRow));
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.sin(angradCol));

				textures[stCount++]=(float) (1-angradCol/(2*Math.PI));//st����
				textures[stCount++]=(float) ((angradRow/(Math.PI/2)+1)*(bottomTexBegin-bottomTexEnd))+bottomTexEnd;
				//��һ�У���һ��-----------3
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.cos(angradColNext));
				vertices[count++]=(float) (bBottom*Math.sin(angradRowNext));
				vertices[count++]=(float) (r*Math.cos(angradRowNext)*Math.sin(angradColNext));

				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st����
				textures[stCount++]=(float) ((angradRowNext/(Math.PI/2)+1)*(bottomTexBegin-bottomTexEnd))+bottomTexEnd;
				//��ǰ�У���һ��-----------1
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.cos(angradColNext));
				vertices[count++]=(float) (bBottom*Math.sin(angradRow));
				vertices[count++]=(float) (r*Math.cos(angradRow)*Math.sin(angradColNext));
				
				textures[stCount++]=(float) (1-angradColNext/(2*Math.PI));//st����
				textures[stCount++]=(float) ((angradRow/(Math.PI/2)+1)*(bottomTexBegin-bottomTexEnd))+bottomTexEnd;
			}
		}
		for(int i=norCount;i<count;i+=3){//�°�����������ʼ��
			normals[norCount++]=vertices[i+0]/(r*r);
			normals[norCount++]=vertices[i+1]/(bBottom*bBottom);
			normals[norCount++]=vertices[i+2]/(r*r);
		}
		for(float angdeg=0;Math.ceil(angdeg)<360;angdeg+=angdegColSpan)//����
		{
			double angrad=Math.toRadians(angdeg);//��ǰ����
			double angradNext=Math.toRadians(angdeg+angdegColSpan);//��һ����
			//��Բ��ǰ��---0
			vertices[count++]=(float) (r*Math.cos(angrad));
			vertices[count++]=0;
			vertices[count++]=(float) (r*Math.sin(angrad));
			
			textures[stCount++]=(float) (1-angrad/(2*Math.PI));//st����
			textures[stCount++]=centerTexEnd;
			//��Բ��ǰ��---2
			vertices[count++]=(float) (r*Math.cos(angrad));
			vertices[count++]=h;
			vertices[count++]=(float) (r*Math.sin(angrad));
			
			textures[stCount++]=(float) (1-angrad/(2*Math.PI));//st����
			textures[stCount++]=centerTexBegin;
			//��Բ��һ��---3
			vertices[count++]=(float) (r*Math.cos(angradNext));
			vertices[count++]=h;
			vertices[count++]=(float) (r*Math.sin(angradNext));
			
			textures[stCount++]=(float) (1-angradNext/(2*Math.PI));//st����
			textures[stCount++]=centerTexBegin;
			
			
			//��Բ��ǰ��---0
			vertices[count++]=(float) (r*Math.cos(angrad));
			vertices[count++]=0;
			vertices[count++]=(float) (r*Math.sin(angrad));
			
			textures[stCount++]=(float) (1-angrad/(2*Math.PI));//st����
			textures[stCount++]=centerTexEnd;
			//��Բ��һ��---3
			vertices[count++]=(float) (r*Math.cos(angradNext));
			vertices[count++]=h;
			vertices[count++]=(float) (r*Math.sin(angradNext));
			
			textures[stCount++]=(float) (1-angradNext/(2*Math.PI));//st����
			textures[stCount++]=centerTexBegin;
			//��Բ��һ��---1
			vertices[count++]=(float) (r*Math.cos(angradNext));
			vertices[count++]=0;
			vertices[count++]=(float) (r*Math.sin(angradNext));
			
			textures[stCount++]=(float) (1-angradNext/(2*Math.PI));//st����
			textures[stCount++]=centerTexEnd;
		}
		for(int i=norCount;i<count;i+=3){//���淨������ʼ����x,z���䣬yΪ0
			normals[norCount++]=vertices[i+0];
			normals[norCount++]=0;
			normals[norCount++]=vertices[i+2];
		}
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        vertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        vertexBuffer.put(vertices);//�򻺳����з��붥����������
        vertexBuffer.position(0);//���û�������ʼλ��
        //���������ݳ�ʼ�� 
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
