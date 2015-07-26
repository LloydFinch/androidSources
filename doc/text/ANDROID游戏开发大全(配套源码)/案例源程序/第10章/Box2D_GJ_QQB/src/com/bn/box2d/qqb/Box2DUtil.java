package com.bn.box2d.qqb;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import static com.bn.box2d.qqb.Constant.*;

//生成物理形状的工具类
public class Box2DUtil   
{
	//创建多边形物体(颜色)
	public static MyPolygonColor createPolygon
	(
		float x,//x坐标
		float y,//y坐标
	    float[][] points,//点序列
        boolean isStatic,//是否为静止的
        World world,//世界
        int color//颜色
    )
	{    
		//创建多边形描述对象
		PolygonDef shape = new PolygonDef();   
		//设置密度
		if(isStatic)
		{
			shape.density = 0;
		}   
		else
		{
			shape.density = 1.0f;
		}   
		//设置摩擦系数
		shape.friction = 0.0f;   
		//设置能量损失率（反弹）
		shape.restitution = 1.0f;   

		for(float[] fa:points)
		{
			shape.addVertex(new Vec2(fa[0]/RATE,fa[1]/RATE));
		}
		
		//创建刚体描述对象   
		BodyDef bodyDef = new BodyDef();   
		//设置位置
		bodyDef.position.set(x/RATE, y/RATE);   
		//在世界中创建刚体
		Body bodyTemp= world.createBody(bodyDef); 
		//指定刚体形状
		bodyTemp.createShape(shape);   
		bodyTemp.setMassFromShapes(); 
		
		return new MyPolygonColor(bodyTemp,color,points);
	}   

	//创建圆形（颜色）
	public static MyCircleColor createCircle
	(
		float x,//x坐标
		float y,//y坐标
		float radius,//半径
		World world,//世界
		int color//颜色
	)
	{   
		//创建圆描述对象
		CircleDef shape = new CircleDef();  
		//设置密度
		shape.density = 3;   		
		//设置摩擦系数
		shape.friction = 0.0f;   
		//设置能量损失率（反弹）
		shape.restitution = 1.0f;   
		//设置半径
		shape.radius = radius/RATE;   
		
		//创建刚体描述对象   
		BodyDef bodyDef = new BodyDef(); 
		//设置位置
		bodyDef.position.set(x/RATE, y/RATE);   
		//在世界中创建刚体
		Body bodyTemp = world.createBody(bodyDef); 
		//指定刚体形状
		bodyTemp.createShape(shape);   
		bodyTemp.setMassFromShapes();  		
		return new MyCircleColor(bodyTemp,radius,color);
	}   
}
