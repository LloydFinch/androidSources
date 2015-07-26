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
    AABB worldAABB;//创建 一个管理碰撞的世界   
    World world;     
    //物体列表
    ArrayList<MyBody> bl=new ArrayList<MyBody>();
    //左侧齿轮的旋转关节
    RevoluteJoint rj1;
    //左侧捶打的移动关节
    PrismaticJoint pj1;
    //创建左侧结合齿轮的矩形块
    MyPolygonColor clt2;
    //创建右侧的球
    MyCircleColor ball;
    
    public void onCreate(Bundle savedInstanceState) 
    {   
        super.onCreate(savedInstanceState);   
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,   
        WindowManager.LayoutParams. FLAG_FULLSCREEN); 
        //设置为横屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//获取屏幕尺寸
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
            
        //上下界，以屏幕的左上方为 原点，如果创建的刚体到达屏幕的边缘的话，会停止模拟   
        worldAABB.lowerBound.set(-100.0f,-100.0f);
        worldAABB.upperBound.set(100.0f, 100.0f);//注意这里使用的是现实世界的单位   
           
        Vec2 gravity = new Vec2(0.0f,0.0f);   
        boolean doSleep = true;     
        //创建世界 
        world = new World(worldAABB, gravity, doSleep);          
        
        //创建地面
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
        
        //创建齿轮固定矩形
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
        
        //创建左侧齿轮
        MyCircleColor cl1=Box2DUtil.createCircle
        (3*kd, SCREEN_HEIGHT-6*kd, 2*kd, world,Color.MAGENTA,false);
        bl.add(cl1);
        
        //创建左侧齿轮的旋转关节
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
        
        //创建左侧结合齿轮的矩形块
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
        
        //创建被左侧结合齿轮的矩形块捶打的矩形块      
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
        
        //创建左侧结合齿轮的矩形块及其捶打的矩形块的移动关节
        PrismaticJointDef pjd=new PrismaticJointDef();
        pjd.initialize(clt3.body, clt2.body, clt3.body.getWorldCenter(), new Vec2(0,1));
        pj1=(PrismaticJoint) world.createJoint(pjd);
        
        //创建左侧的齿轮关节
        GearJointDef gjd=new GearJointDef();
        gjd.body1=cl1.body;
        gjd.body2=clt2.body;
        gjd.joint1=rj1;
        gjd.joint2=pj1;
        gjd.ratio=(-1.0f/(2.0f*kd)*RATE);
        world.createJoint(gjd);
        
        //创建右侧的球
        ball=Box2DUtil.createCircle
        (11*kd, SCREEN_HEIGHT-6*kd-1, kd, world,Color.RED,false);
        bl.add(ball);        
        
        //创建左右侧的滑轮关节
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
        
        //在球右侧摆放一个限制矩形
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
        //在球左侧摆放一个限制矩形
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
