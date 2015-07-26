package com.bn.game.chap11.ex11;

//向量规格化的工具类
public class VectorUtil {
	//将向量规格化的方法
	public static float[] normalizeVector(float x, float y, float z){
		float mod=module(x,y,z);
		return new float[]{x/mod, y/mod, z/mod};//返回规格化后的向量
	}
	//求向量的模的方法
	public static float module(float x, float y, float z){
		return (float) Math.sqrt(x*x+y*y+z*z);
	}
}
