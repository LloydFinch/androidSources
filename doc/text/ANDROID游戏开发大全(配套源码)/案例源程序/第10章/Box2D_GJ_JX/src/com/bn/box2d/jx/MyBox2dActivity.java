package com.bn.box2d.jx;     
import java.util.ArrayList;
import org.jbox2d.collision.AABB;   
import org.jbox2d.common.Vec2;    
import org.jbox2d.dynamics.World;     
import org.jbox2d.dynamics.joints.GearJointDef;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.PulleyJointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import android.app.Activity;    
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;   
import android.util.DisplayMetrics;
import android.view.Window;   
import android.view.WindowManager;  
import static com.bn.box2d.jx.Constant.*;
  
public class MyBox2dActivity extends Activity 
{   
    AABB worldAABB;//���� һ��������ײ������   
    World world;     
    //�����б�
    ArrayList<MyBody> bl=new ArrayList<MyBody>();
    //�����ֵ���ת�ؽ�
    RevoluteJoint rj1;
    //��ഷ����ƶ��ؽ�
    PrismaticJoint pj1;
    //��������ϳ��ֵľ��ο�
    MyPolygonColor clt2;
    //�����Ҳ����
    MyCircleColor ball;
    
    public void onCreate(Bundle savedInstanceState) 
    {   
        super.onCreate(savedInstanceState);   
        //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,   
        WindowManager.LayoutParams. FLAG_FULLSCREEN); 
        //����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//��ȡ��Ļ�ߴ�
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        if(dm.widthPixels<dm.heightPixels)
        {
        	 SCREEN_WIDTH=dm.widthPixels;
             SCREEN_HEIGHT=dm.heightPixels;   
        }
        else
        {
        	SCREEN_WIDTH=dm.heightPixels;
            SCREEN_HEIGHT=dm.widthPixels;    
        }
           
        worldAABB = new AABB();   
            
        //���½磬����Ļ�����Ϸ�Ϊ ԭ�㣬��������ĸ��嵽����Ļ�ı�Ե�Ļ�����ֹͣģ��   
        worldAABB.lowerBound.set(-100.0f,-100.0f);
        worldAABB.upperBound.set(100.0f, 100.0f);//ע������ʹ�õ�����ʵ����ĵ�λ   
           
        Vec2 gravity = new Vec2(0.0f,0.0f);   
        boolean doSleep = true;     
        //�������� 
        world = new World(worldAABB, gravity, doSleep);          
        
        //��������
        MyPolygonColor mrc=Box2DUtil.createPolygon
        (
        	0, 
        	SCREEN_HEIGHT-kd,
        	new float[][]
        	{
        	  {0,0},{SCREEN_WIDTH,0},{SCREEN_WIDTH,kd},{0,kd}
        	},
        	true,
        	world,
        	0xff0000ff
        );
        bl.add(mrc);        
        
        //�������̶ֹ�����
        MyPolygonColor clt1=Box2DUtil.createPolygon
        (
        	2*kd, 
        	SCREEN_HEIGHT-7*kd,
        	new float[][]
        	{
        	  {0,0},{2*kd,0},{2*kd,2*kd},{0,2*kd}
        	},
        	true,
        	world,
        	0xff0000ff
        );
        bl.add(clt1); 
        
        //����������
        MyCircleColor cl1=Box2DUtil.createCircle
        (3*kd, SCREEN_HEIGHT-6*kd, 2*kd, world,Color.MAGENTA,false);
        bl.add(cl1);
        
        //���������ֵ���ת�ؽ�
        RevoluteJointDef rjd1=new RevoluteJointDef();
        rjd1.initialize
        (
        	clt1.body,
        	cl1.body, 
        	cl1.body.getWorldCenter()
        );
        rjd1.lowerAngle=(float) (-0.95);
        rjd1.upperAngle=(float) (0.95);
        rjd1.enableLimit=true;
        rjd1.maxMotorTorque=100000;
        rjd1.motorSpeed=(float) (-Math.PI/2);
        rjd1.enableMotor=true;
        rj1=(RevoluteJoint) world.createJoint(rjd1);
        
        //��������ϳ��ֵľ��ο�
        clt2=Box2DUtil.createPolygon
        (
        	5*kd, 
        	SCREEN_HEIGHT-10*kd,
        	new float[][]
        	{
        	  {0,0},{kd,0},{kd,6*kd},{0,6*kd}
        	},
        	false,
        	world,
        	0xff0000ff
        );
        bl.add(clt2); 
        
        //����������ϳ��ֵľ��ο鴷��ľ��ο�      
        MyPolygonColor clt3=Box2DUtil.createPolygon
        (
        	5*kd, 
        	SCREEN_HEIGHT-2*kd-1,
        	new float[][]
        	{
        	  {0,0},{kd,0},{kd,kd},{0,kd}
        	},
        	true,
        	world,
        	Color.RED
        );
        bl.add(clt3); 
        
        //��������ϳ��ֵľ��ο鼰�䴷��ľ��ο���ƶ��ؽ�
        PrismaticJointDef pjd=new PrismaticJointDef();
        pjd.initialize(clt3.body, clt2.body, clt3.body.getWorldCenter(), new Vec2(0,1));
        pj1=(PrismaticJoint) world.createJoint(pjd);
        
        //�������ĳ��ֹؽ�
        GearJointDef gjd=new GearJointDef();
        gjd.body1=cl1.body;
        gjd.body2=clt2.body;
        gjd.joint1=rj1;
        gjd.joint2=pj1;
        gjd.ratio=(-1.0f/(2.0f*kd)*RATE);
        world.createJoint(gjd);
        
        //�����Ҳ����
        ball=Box2DUtil.createCircle
        (11*kd, SCREEN_HEIGHT-6*kd-1, kd, world,Color.RED,false);
        bl.add(ball);        
        
        //�������Ҳ�Ļ��ֹؽ�
        PulleyJointDef pjd2=new PulleyJointDef();
        pjd2.initialize
        (
        	clt2.body,
        	ball.body,
        	new Vec2(3*kd,SCREEN_HEIGHT-16*kd), 
        	new Vec2(11*kd,SCREEN_HEIGHT-16*kd), 
        	clt2.body.getWorldCenter(), 
        	ball.body.getWorldCenter(),
        	1f
        );
        world.createJoint(pjd2);
        
        //�����Ҳ�ڷ�һ�����ƾ���
        MyPolygonColor xzjx=Box2DUtil.createPolygon
        (
        	12*kd, 
        	SCREEN_HEIGHT-10*kd-1,
        	new float[][]
        	{
        	  {0,0},{kd,0},{kd,9*kd},{0,9*kd}
        	},
        	true,
        	world,
        	0xff0000ff
        );
        bl.add(xzjx); 
        //�������ڷ�һ�����ƾ���
        xzjx=Box2DUtil.createPolygon
        (
        	9*kd, 
        	SCREEN_HEIGHT-10*kd-1,
        	new float[][]
        	{
        	  {0,0},{kd,0},{kd,9*kd},{0,9*kd}
        	},
        	true,
        	world,
        	0xff0000ff
        );
        bl.add(xzjx); 
        
        GameView gv= new GameView(this);   
        setContentView(gv);   
    }   
}  
