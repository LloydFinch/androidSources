package com.bn.box2d.qqb;     
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
import static com.bn.box2d.qqb.Constant.*;
  
public class MyBox2dActivity extends Activity 
{   
    AABB worldAABB;//���� һ��������ײ������   
    World world;     
    //�����б�
    ArrayList<MyBody> bl=new ArrayList<MyBody>();

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
           
        Vec2 gravity = new Vec2(0.0f,10.0f);   
        boolean doSleep = true;     
        //�������� 
        world = new World(worldAABB, gravity, doSleep);          
        
        //��������
        final int kd=20;//��Ȼ�߶�
        MyPolygonColor mrc=Box2DUtil.createPolygon
        (
        	0, 
        	SCREEN_HEIGHT-kd,
        	new float[][]
        	{
        	  {0,0},{SCREEN_WIDTH,0},{SCREEN_WIDTH,SCREEN_HEIGHT},{0,SCREEN_HEIGHT}
        	},
        	true,
        	world,
        	Color.YELLOW
        );
        bl.add(mrc);
        
        //����������
        MyPolygonColor mrca=Box2DUtil.createPolygon
        (
        	SCREEN_WIDTH/2-30, 
        	SCREEN_HEIGHT-kd-62,
        	new float[][]
        	{
        	  {30,0},
        	  {60,60},
        	  {0,60}
        	},
        	true,
        	world,
        	Color.RED
        );
        bl.add(mrca);
        
        //������
        MyPolygonColor mrcb=Box2DUtil.createPolygon
        (
        	SCREEN_WIDTH/2-150, 
        	SCREEN_HEIGHT-kd-60-kd,
        	new float[][]
        	{
        	  {0,0},
        	  {300,0},
        	  {300,kd},
        	  {0,kd}
        	}, 
        	false,
        	world, 
        	Color.BLUE
        );
        bl.add(mrcb);  
   
        //������ת�ؽ�
        RevoluteJointDef rjd=new RevoluteJointDef();
        rjd.initialize(mrca.body, mrcb.body, new Vec2(SCREEN_WIDTH/2/RATE,(SCREEN_HEIGHT-kd-60)/RATE));
        world.createJoint(rjd);
        
        //������
        MyCircleColor ball=Box2DUtil.createCircle(SCREEN_WIDTH/2-100, SCREEN_HEIGHT-700, kd*2/3, world,Color.MAGENTA);
        bl.add(ball);
        ball.body.setLinearVelocity(new Vec2(0,0));
           
        GameView gv= new GameView(this);   
        setContentView(gv);   
    }   
}  
