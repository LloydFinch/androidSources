package com.bn.box2d.bheap;     
import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.collision.AABB;   
import org.jbox2d.common.Vec2;    
import org.jbox2d.dynamics.World;     
import android.app.Activity;    
import android.content.pm.ActivityInfo;
import android.os.Bundle;   
import android.util.DisplayMetrics;
import android.view.Window;   
import android.view.WindowManager;  
import static com.bn.box2d.bheap.Constant.*;
  
public class MyBox2dActivity extends Activity 
{   
    AABB worldAABB;//创建 一个管理碰撞的世界   
    World world;
    Random random=new Random();
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
        
        //创建4边
        final int kd=40;//宽度或高度
        MyRectColor mrc=Box2DUtil.createBox(kd/4, SCREEN_HEIGHT/2, kd/4, SCREEN_HEIGHT/2, true,world,0xFFe6e4FF);
        bl.add(mrc);
        mrc=Box2DUtil.createBox(SCREEN_WIDTH-kd/4, SCREEN_HEIGHT/2, kd/4, SCREEN_HEIGHT/2, true,world,0xFFe6e4FF);
        bl.add(mrc);
        mrc=Box2DUtil.createBox(SCREEN_WIDTH/2, kd/4, SCREEN_WIDTH/2, kd/4, true,world,0xFFe6e4FF);
        bl.add(mrc);
        mrc=Box2DUtil.createBox(SCREEN_WIDTH/2, SCREEN_HEIGHT-kd/4, SCREEN_WIDTH/2, kd/4, true,world,0xFFe6e4FF);
        bl.add(mrc);
        
        //创建砖块
        //砖块间距	行间距为20     模块宽度为10 	最多一行为9块
        final int bs=20;
        final int bw=(int)((SCREEN_WIDTH-2*kd-11*bs)/18);
        //============================================================
        for(int i=2;i<10;i++)
        {
        	if((i%2)==0)
        	{
        		//左侧蓝木块
        		for(int j=0;j<9-i;j++)
        		{
        			mrc=Box2DUtil.createBox
        			(
        				kd/2+bs+bw/2+i*(kd+5)/2+j*(kd+5)+3,
        				SCREEN_HEIGHT+bw-i*(bw+kd)/2,
        				bw/2,
        				kd/2,
        				false,
        				world,
        				ColorUtil.getColor(Math.abs(random.nextInt()))
        			);
        			bl.add(mrc);
        		}
        		//右侧蓝木块
        		for(int j=0;j<9-i;j++)
        		{
        			mrc=Box2DUtil.createBox
        			(
        				3*kd/2+bs-bw/2+i*(kd+5)/2+j*(kd+5)-3,
        				SCREEN_HEIGHT+bw-i*(bw+kd)/2,
        				bw/2,
        				kd/2,
        				false,
        				world,
        				ColorUtil.getColor(Math.abs(random.nextInt()))
        			);
        			bl.add(mrc);
        		}
        	}   
        	if((i%2)!=0)
        	{
        		for(int j=0;j<10-i;j++)
        		{
        			mrc=Box2DUtil.createBox
        			(
        				kd/2+bs+kd/2+(i-1)*(kd+5)/2+j*(kd+5),
        				SCREEN_HEIGHT-(kd-bw)/2-(i-1)*(bw+kd)/2,
        				kd/2,
        				bw/2,
        				false,
        				world,
        				ColorUtil.getColor(Math.abs(random.nextInt()))
        			);
        			bl.add(mrc);
        		}
        	}
        }
        mrc=Box2DUtil.createBox
		(
			5*kd+bs+20,
			SCREEN_HEIGHT-(kd+bw)*4-kd,
			bw/2,
			kd/2,
			false,
			world,
			ColorUtil.getColor(Math.abs(random.nextInt()))
		);
		bl.add(mrc);
        //创建球
        MyCircleColor ball=Box2DUtil.createCircle(SCREEN_WIDTH/2-24, kd, kd/2, world,ColorUtil.getColor(Math.abs(random.nextInt())));
        bl.add(ball);
        ball.body.setLinearVelocity(new Vec2(0,50));
           
        GameView gv= new GameView(this);   
        setContentView(gv);   
    }   
}  
