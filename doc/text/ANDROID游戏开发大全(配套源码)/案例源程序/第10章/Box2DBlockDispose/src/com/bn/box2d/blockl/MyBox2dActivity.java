package com.bn.box2d.blockl;     
import java.util.ArrayList;
import org.jbox2d.collision.AABB;   
import org.jbox2d.common.Vec2;    
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.World;     
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;
import android.app.Activity;    
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;   
import android.util.DisplayMetrics;
import android.view.Window;   
import android.view.WindowManager;  
import static com.bn.box2d.blockl.Constant.*;
  
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
           
        Vec2 gravity = new Vec2(0.0f,0.0f);   
        boolean doSleep = true;     
        //创建世界 
        world = new World(worldAABB, gravity, doSleep);          
        
        //创建4边
        final int kd=20;//宽度或高度
        MyRectColor mrc=Box2DUtil.createBox(kd/2, SCREEN_HEIGHT/2, kd/2, SCREEN_HEIGHT/2, true,world,Color.YELLOW,false);
        bl.add(mrc);
        mrc=Box2DUtil.createBox(SCREEN_WIDTH-kd/2, SCREEN_HEIGHT/2, kd/2, SCREEN_HEIGHT/2, true,world,Color.YELLOW,false);
        bl.add(mrc);
        mrc=Box2DUtil.createBox(SCREEN_WIDTH/2, kd/2, SCREEN_WIDTH/2, kd/2, true,world,Color.YELLOW,false);
        bl.add(mrc);
        mrc=Box2DUtil.createBox(SCREEN_WIDTH/2, SCREEN_HEIGHT-kd/2, SCREEN_WIDTH/2, kd/2, true,world,Color.YELLOW,false);
        bl.add(mrc);
        
        //创建砖块
        final int bs=20;//砖块间距
        final int bw=(SCREEN_WIDTH-2*kd-5*bs)/4;
        for(int i=0;i<4;i++)
        {
        	for(int j=0;j<4;j++)
        	{
        		mrc=Box2DUtil.createBox
        		(
        				kd+bs+bw/2+j*(bw+bs), 
        				kd+bs+kd/2+i*(kd+bs), 
        				bw/2, kd/2, 
        				true,
        				world,
        				Color.RED,
        				true
        		);
                bl.add(mrc);
        	}
        }
        
        //创建球
        MyCircleColor ball=Box2DUtil.createCircle(SCREEN_WIDTH/2, SCREEN_HEIGHT-100, kd, world,Color.MAGENTA);
        bl.add(ball);
        ball.body.setLinearVelocity(new Vec2(5,-40));
        
        //添加监听器
        initContactListener();
           
        GameView gv= new GameView(this);   
        setContentView(gv);   
    }   
    
	//加载碰撞监听器
	public void initContactListener()
	{
		ContactListener cl=new ContactListener()
		{
			@Override
			public void add(ContactPoint arg0) 
			{//从当前的body查找出对应物件并执行事件处理
				BodySearchUtil.doAction(arg0.shape1.getBody(), arg0.shape2.getBody(), bl);				
			}

			@Override
			public void persist(ContactPoint arg0){}

			@Override
			public void remove(ContactPoint arg0) 
			{
			}

			@Override
			public void result(ContactResult arg0) 
			{}			
		};
		world.setContactListener(cl);
	}
}  
