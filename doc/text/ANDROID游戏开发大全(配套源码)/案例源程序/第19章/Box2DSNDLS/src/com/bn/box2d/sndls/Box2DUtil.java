package com.bn.box2d.sndls;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.graphics.Bitmap;
import static com.bn.box2d.sndls.Constant.*;

//生成物理形状的工具类
public class Box2DUtil 
{	
	//创建多边形（贴图）
	public static MyPolygonImg createPolygonImg
	(
		float x,//x坐标
		float y,//y坐标
	    float[][] vData,//顶点坐标
        boolean isStatic,//是否为静止的
        World world,//世界
        Bitmap[] bm,//图片
        float width,//目标宽度
        float height,//目标高度
        BodyType lx,//类型
        GameView gv
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
			shape.density = 2.0f;
		}   
		//设置摩擦系数
		shape.friction = 0.8f;   
		//设置能量损失率（反弹）
		shape.restitution = 0.5f;   
		
		for(float[] fa:vData)
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
		
		MyPolygonImg result=null;
		switch(lx)
        {
          case MT://木头条
        	  result=new BodyWood(bodyTemp,bm,width,height,gv);	
          break;
          case BK://冰块
        	  result=new BodyIce(bodyTemp,bm,width,height,gv);	
          break;
          case XM://小猫
        	  result=new BodyCat(bodyTemp,bm,width,height,gv);	
          break;
          default://其他
        	  result=new MyPolygonImg(bodyTemp,bm,width,height,gv);	
        }
		return result;
	}
}
