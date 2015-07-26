package com.bn.box2d.lt;     
import java.util.ArrayList;
import org.jbox2d.collision.AABB;   
import org.jbox2d.common.Vec2;    
import org.jbox2d.dynamics.World;     
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import android.app.Activity;    
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;   
import android.util.DisplayMetrics;
import android.view.Window;   
import android.view.WindowManager;  
import static com.bn.box2d.lt.Constant.*;
  
public class MyBox2dActivity extends Activity 
{   
    AABB worldAABB;//创建 一个管理碰撞的世界   
    World world;     
    //物体列表
    ArrayList<MyBody> bl=new ArrayList<MyBody>();

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
           
        Vec2 gravity = new Vec2(0.0f,10.0f);   
        boolean doSleep = true;     
        //创建世界 
        world = new World(worldAABB, gravity, doSleep);          
        
        //创建地面
        final int kd=20;//宽度或高度
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
        
        //创建左右墙面
        mrc=Box2DUtil.createPolygon
        (
        	0, 
        	0,
        	new float[][]
        	{
        	  {0,0},{kd,0},{kd,SCREEN_HEIGHT},{0,SCREEN_HEIGHT}
        	},
        	true,
        	world,
        	0xff0000ff
        );
        bl.add(mrc);
        mrc=Box2DUtil.createPolygon
        (
        	SCREEN_WIDTH-kd, 
        	0,
        	new float[][]
        	{
        	  {0,0},{kd,0},{kd,SCREEN_HEIGHT},{0,SCREEN_HEIGHT}
        	},
        	true,
        	world,
        	0xff0000ff
        );
        bl.add(mrc);
        
        //创建上面的方块
        MyPolygonColor fk=Box2DUtil.createPolygon
        (
        	SCREEN_WIDTH/2-kd, 
        	2*kd,
        	new float[][]
        	{
        	  {0,0},{2*kd,0},{2*kd,2*kd},{0,2*kd}
        	},
        	true,
        	world,
        	0xff0000ff
        );
        bl.add(fk);
        
        //循环创建每个链条
        int ltCount=6;
        MyPolygonColor[] ltA=new MyPolygonColor[ltCount];        
        for(int i=0;i<ltCount;i++)
        {
        	ltA[i]=Box2DUtil.createPolygon
            (
                	SCREEN_WIDTH/2-kd/2, 
                	4.5f*kd+i*3.5f*kd,
                	new float[][]
                	{
                	  {0,0},{kd,0},{kd,3*kd},{0,3*kd}
                	},
                	false,
                	world,
                	0xff0000ff
            );
            bl.add(ltA[i]);            
        }
        
        //创建三角形
        MyPolygonColor sjx=Box2DUtil.createPolygon
        (
        	SCREEN_WIDTH/2-30, 
        	4.5f*kd+ltCount*3.5f*kd,
        	new float[][]
        	{
        	  {30,0},
        	  {60,60},
        	  {0,60}
        	},
        	false,
        	world,
        	Color.RED
        );
        bl.add(sjx);
        
        //循环创建关节
        for(int i=0;i<ltCount;i++)
        {
        	if(i==0)
        	{
        		//创建旋转关节
                RevoluteJointDef rjd=new RevoluteJointDef();
                rjd.initialize(fk.body,ltA[i].body, new Vec2((SCREEN_WIDTH/2)/RATE,(4.25f*kd+i*3.5f*kd)/RATE));
                world.createJoint(rjd);
        	}
        	else
        	{
        		RevoluteJointDef rjd=new RevoluteJointDef();
                rjd.initialize(ltA[i-1].body,ltA[i].body, new Vec2((SCREEN_WIDTH/2)/RATE,(4.25f*kd+i*3.5f*kd)/RATE));
                world.createJoint(rjd);
        	}
        }
        
        //创建三角形与最后链条的关节
        RevoluteJointDef rjd=new RevoluteJointDef();
        rjd.initialize(ltA[ltCount-1].body,sjx.body, new Vec2((SCREEN_WIDTH/2)/RATE,(4.5f*kd+ltCount*3.5f*kd)/RATE));
        world.createJoint(rjd);
        
        //创建球
        MyCircleColor ball=Box2DUtil.createCircle(SCREEN_WIDTH/4, (5.5f*kd+ltCount*3.5f*kd), kd*2/3, world,Color.MAGENTA);
        bl.add(ball);
        ball.body.setLinearVelocity(new Vec2(30,0));
           
        GameView gv= new GameView(this);   
        setContentView(gv);   
    }   
}  
