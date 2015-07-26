package com.bn.game.chap11.ex14;

//计算三角形法向量的工具类
public class VectorUtil {
	//计算三角形法向量的方法
	public static float[] calTriangleNormal
	(//三角形三个顶点坐标，（要求以逆时针卷绕）
			float x0,float y0,float z0,//A
			float x1,float y1,float z1,//B
			float x2,float y2,float z2 //C
	)
	{
		float[] a={x1-x0, y1-y0, z1-z0};//向量AB
		float[] b={x2-x1, y2-y1, z2-z1};//向量BC
		/*
		 * 计算三角形的法向量，
		 * 向量AB、BC做叉乘，
		 * 若ABC三点按逆时针卷绕，
		 * 向量AB和BC首尾相连，
		 * 则AB叉乘BC所得法向量的方向符合右手定则
		 */
		float[] c=crossTwoVectors(a,b);
		return normalizeVector(c);//返回规格化后的法向量
	}
	//计算棱锥侧面法向量的方法
	public static float[] calConeNormal
	(//三角形三个顶点坐标
			float x0,float y0,float z0,//A，中心点
			float x1,float y1,float z1,//B，底面圆上一点
			float x2,float y2,float z2 //C，顶点
	)
	{
		float[] a={x1-x0, y1-y0, z1-z0};//向量AB
		float[] b={x2-x1, y2-y1, z2-z1};//向量BC
		//先球垂直于平面ABC的向量
		float[] c=crossTwoVectors(a,b);
		//将b和c做叉乘，得出所球向量d
		float[] d=crossTwoVectors(b,c);
		return normalizeVector(d);//返回规格化后的法向量
	}
	//将一个向量规格化的方法
	public static float[] normalizeVector(float x, float y, float z){
		float mod=module(x,y,z);
		return new float[]{x/mod, y/mod, z/mod};//返回规格化后的向量
	}
	public static float[] normalizeVector(float [] vec){
		float mod=module(vec);
		return new float[]{vec[0]/mod, vec[1]/mod, vec[2]/mod};//返回规格化后的向量
	}
	//将一组向量规格化的方法，数组中元素个数应是3的倍数
	public static void normalizeAllVectors(float[] allVectors){
		for(int i=0;i<allVectors.length;i+=3){
			float[] result=VectorUtil.normalizeVector(allVectors[i],allVectors[i+1],allVectors[i+2]);
			allVectors[i]=result[0];
			allVectors[i+1]=result[1];
			allVectors[i+2]=result[2];
		}
	}
	//求向量的模的方法
	public static float module(float x, float y, float z){
		return (float) Math.sqrt(x*x+y*y+z*z);
	}
	public static float module(float [] vec){
		return (float) Math.sqrt(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
	}
	//两个向量叉乘的方法
	public static float[] crossTwoVectors(float[] a, float[] b)
	{
		float x=a[1]*b[2]-a[2]*b[1];
		float y=a[2]*b[0]-a[0]*b[2];
		float z=a[0]*b[1]-a[1]*b[0];
		return new float[]{x, y, z};//返回法向量
	}
}
