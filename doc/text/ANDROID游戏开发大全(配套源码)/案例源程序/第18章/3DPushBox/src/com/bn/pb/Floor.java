package com.bn.pb;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import static com.bn.pb.Constant.*;
//��ʾ�ذ����
public class Floor {
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer mTextureBuffer;//�����������ݻ���
    private FloatBuffer mNormalBuffer;
    int vCount=0;//��������
    float yAngle;//y����ת�Ƕ�
    int width;//�ذ����width����λ
    int height;//�ذ�����height����λ
    
    int xOffset;
    int zOffset;
    
    public Floor(int xOffset,int zOffset,float scale,float yAngle,int width,int height)
    {
    	this.yAngle=yAngle;
    	
    	this.xOffset=xOffset;
    	this.zOffset=zOffset;
    	
    	this.width=width;
    	this.height=height;
    	
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=width*height*6;//ÿ���ذ��6������
       
        float vertices[]=new float[vCount*3];
        int k=0;                           
        for(int i=0;i<width;i++)
        	for(int j=0;j<height;j++)
	        {//ÿ���ذ��������������6�����㹹��	        	
	        	vertices[k++]=i*UNIT_SIZE*scale;
	        	vertices[k++]=0;
	        	vertices[k++]=j*UNIT_SIZE*scale;
	        	
	        	vertices[k++]=i*UNIT_SIZE*scale;
	        	vertices[k++]=0;
	        	vertices[k++]=(j+1)*UNIT_SIZE*scale;
	        	
	        	vertices[k++]=(i+1)*UNIT_SIZE*scale;
	        	vertices[k++]=0;
	        	vertices[k++]=(j+1)*UNIT_SIZE*scale;
	        	
	        	vertices[k++]=(i+1)*UNIT_SIZE*scale;
	        	vertices[k++]=0;
	        	vertices[k++]=(j+1)*UNIT_SIZE*scale;
	        	
	        	vertices[k++]=(i+1)*UNIT_SIZE*scale;
	        	vertices[k++]=0;
	        	vertices[k++]=j*UNIT_SIZE*scale;
	        	
	        	vertices[k++]=i*UNIT_SIZE*scale;
	        	vertices[k++]=0;
	        	vertices[k++]=j*UNIT_SIZE*scale;
	        };
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ��Float�ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        
        //���㷨�������ݵĳ�ʼ��================begin============================
        float normals[]=new float[vCount*3];
        for(int i=0;i<vCount;i++)
        {
        	normals[i*3]=0;
        	normals[i*3+1]=1;
        	normals[i*3+2]=0;
        }

        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥����ɫ����
        mNormalBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //������ɫ���ݵĳ�ʼ��================end============================
        
        //���� �������ݳ�ʼ��
        float[] texST=new float[vCount*2];
        for(int i=0;i<vCount*2/12;i++)
        {
        	texST[i*12]=0;
        	texST[i*12+1]=0;
        	
        	texST[i*12+2]=0;
        	texST[i*12+3]=1;
        	
        	texST[i*12+4]=1;
        	texST[i*12+5]=1;
        	
        	texST[i*12+6]=1;
        	texST[i*12+7]=1;
        	
        	texST[i*12+8]=1;
        	texST[i*12+9]=0;
        	
        	texST[i*12+10]=0;
        	texST[i*12+11]=0;
        };
        ByteBuffer tbb = ByteBuffer.allocateDirect(texST.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer = tbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mTextureBuffer.put(texST);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��         
    }

    public void drawSelf(GL10 gl,int texId)
    {        
        
        gl.glPushMatrix();//�����ֳ�
        gl.glTranslatef(xOffset*UNIT_SIZE, 0, 0);//�ƶ�����ϵ
        gl.glTranslatef(0, 0, zOffset*UNIT_SIZE);//�ƶ�����ϵ
        gl.glRotatef(yAngle, 0, 1, 0);//��ת����ϵ
        
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );      
        
        //Ϊ����ָ������ST���껺��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //�󶨵�ǰ����
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0, 			 			//��ʼ����
        		vCount					//���������
        );
        
        gl.glPopMatrix();//�ָ��ֳ�
    }
}
