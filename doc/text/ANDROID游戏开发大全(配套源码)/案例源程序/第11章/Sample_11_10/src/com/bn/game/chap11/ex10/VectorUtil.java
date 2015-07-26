package com.bn.game.chap11.ex10;

//���������η������Ĺ�����
public class VectorUtil {
	//���������η������ķ���
	public static float[] calTriangleNormal
	(//�����������������꣬��Ҫ������ʱ����ƣ�
			float x0,float y0,float z0,//A
			float x1,float y1,float z1,//B
			float x2,float y2,float z2 //C
	)
	{
		float[] a={x1-x0, y1-y0, z1-z0};//����AB
		float[] b={x2-x1, y2-y1, z2-z1};//����BC
		/*
		 * ���������εķ�������
		 * ����AB��BC����ˣ�
		 * ��ABC���㰴��ʱ����ƣ�
		 * ����AB��BC��β������
		 * ��AB���BC���÷������ķ���������ֶ���
		 */
		float[] c=crossTwoVectors(a,b);
		return normalizeVector(c);//���ع�񻯺�ķ�����
	}
	//������׶���淨�����ķ���
	public static float[] calConeNormal
	(//������������������
			float x0,float y0,float z0,//A�����ĵ�
			float x1,float y1,float z1,//B������Բ��һ��
			float x2,float y2,float z2 //C������
	)
	{
		float[] a={x1-x0, y1-y0, z1-z0};//����AB
		float[] b={x2-x1, y2-y1, z2-z1};//����BC
		//����ֱ��ƽ��ABC������
		float[] c=crossTwoVectors(a,b);
		//��b��c����ˣ��ó���������d
		float[] d=crossTwoVectors(b,c);
		return normalizeVector(d);//���ع�񻯺�ķ�����
	}
	//��������񻯵ķ���
	public static float[] normalizeVector(float x, float y, float z){
		float mod=module(x,y,z);
		return new float[]{x/mod, y/mod, z/mod};//���ع�񻯺������
	}
	public static float[] normalizeVector(float [] vec){
		float mod=module(vec);
		return new float[]{vec[0]/mod, vec[1]/mod, vec[2]/mod};//���ع�񻯺������
	}
	//��������ģ�ķ���
	public static float module(float x, float y, float z){
		return (float) Math.sqrt(x*x+y*y+z*z);
	}
	public static float module(float [] vec){
		return (float) Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
	}
	//����������˵ķ���
	public static float[] crossTwoVectors(
			float[] a,
			float[] b)
	{
		float x=a[1]*b[2]-a[2]*b[1];
		float y=a[2]*b[0]-a[0]*b[2];
		float z=a[0]*b[1]-a[1]*b[0];
		return new float[]{x, y, z};//���ط�����
	}
}
