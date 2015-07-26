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
        	  {0,0},{SCREEN_WIDTH,0},{SCREEN_WIDTH,kd},{0,kd}
        	},
        	true,
        	world,
        	0xff0000ff
        );
        bl.add(mrc);
        
        //��������ǽ��
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
        
        //��������ķ���
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
        
        //ѭ������ÿ������
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
        
        //����������
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
        
        //ѭ�������ؽ�
        for(int i=0;i<ltCount;i++)
        {
        	if(i==0)
        	{
        		//������ת�ؽ�
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
        
        //��������������������Ĺؽ�
        RevoluteJointDef rjd=new RevoluteJointDef();
        rjd.initialize(ltA[ltCount-1].body,sjx.body, new Vec2((SCREEN_WIDTH/2)/RATE,(4.5f*kd+ltCount*3.5f*kd)/RATE));
        world.createJoint(rjd);
        
        //������
        MyCircleColor ball=Box2DUtil.createCircle(SCREEN_WIDTH/4, (5.5f*kd+ltCount*3.5f*kd), kd*2/3, world,Color.MAGENTA);
        bl.add(ball);
        ball.body.setLinearVelocity(new Vec2(30,0));
           
        GameView gv= new GameView(this);   
        setContentView(gv);   
    }   
}  
