package com.bn.box2d.lt;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import static com.bn.box2d.lt.Constant.*;

//����������״�Ĺ�����
public class Box2DUtil 
{
	//�������������(��ɫ)
	public static MyPolygonColor createPolygon
	(  
		float x,//x����
		float y,//y����
	    float[][] points,//������
        boolean isStatic,//�Ƿ�Ϊ��ֹ��
        World world,//����
        int color//��ɫ
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
			shape.density = 1.0f;
		}   
		//����Ħ��ϵ��
		shape.friction = 0.0f;   
		//����������ʧ�ʣ�������
		shape.restitution = 1.0f;   

		for(float[] fa:points)
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
		
		return new MyPolygonColor(bodyTemp,color,points);
	}   

	//����Բ�Σ���ɫ��
	public static MyCircleColor createCircle
	(
		float x,//x����
		float y,//y����
		float radius,//�뾶
		World world,//����
		int color//��ɫ
	)
	{   
		//����Բ��������
		CircleDef shape = new CircleDef();  
		//�����ܶ�
		shape.density = 3;   		
		//����Ħ��ϵ��
		shape.friction = 0.0f;   
		//����������ʧ�ʣ�������
		shape.restitution = 1.0f;   
		//���ð뾶
		shape.radius = radius/RATE;   
		
		//����������������   
		BodyDef bodyDef = new BodyDef(); 
		//����λ��
		bodyDef.position.set(x/RATE, y/RATE);   
		//�������д�������
		Body bodyTemp = world.createBody(bodyDef); 
		//ָ��������״
		bodyTemp.createShape(shape);   
		bodyTemp.setMassFromShapes();  		
		return new MyCircleColor(bodyTemp,radius,color);
	}   
}
