package com.bn.box2d.sndls;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.graphics.Bitmap;
import static com.bn.box2d.sndls.Constant.*;

//����������״�Ĺ�����
public class Box2DUtil 
{	
	//��������Σ���ͼ��
	public static MyPolygonImg createPolygonImg
	(
		float x,//x����
		float y,//y����
	    float[][] vData,//��������
        boolean isStatic,//�Ƿ�Ϊ��ֹ��
        World world,//����
        Bitmap[] bm,//ͼƬ
        float width,//Ŀ����
        float height,//Ŀ��߶�
        BodyType lx,//����
        GameView gv
    )
	{    
		//�����������������
		PolygonDef shape = new PolygonDef();   
		//�����ܶ�
		if(isStatic)
		{
			shape.density = 0;
		}   
		else
		{
			shape.density = 2.0f;
		}   
		//����Ħ��ϵ��
		shape.friction = 0.8f;   
		//����������ʧ�ʣ�������
		shape.restitution = 0.5f;   
		
		for(float[] fa:vData)
		{
			shape.addVertex(new Vec2(fa[0]/RATE,fa[1]/RATE));
		}
		
		//����������������   
		BodyDef bodyDef = new BodyDef();   
		//����λ��
		bodyDef.position.set(x/RATE, y/RATE);   
		//�������д�������
		Body bodyTemp= world.createBody(bodyDef); 
		//ָ��������״
		bodyTemp.createShape(shape);   
		bodyTemp.setMassFromShapes(); 
		
		MyPolygonImg result=null;
		switch(lx)
        {
          case MT://ľͷ��
        	  result=new BodyWood(bodyTemp,bm,width,height,gv);	
          break;
          case BK://����
        	  result=new BodyIce(bodyTemp,bm,width,height,gv);	
          break;
          case XM://Сè
        	  result=new BodyCat(bodyTemp,bm,width,height,gv);	
          break;
          default://����
        	  result=new MyPolygonImg(bodyTemp,bm,width,height,gv);	
        }
		return result;
	}
}
