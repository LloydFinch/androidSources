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
           
        Vec2 gravity = new Vec2(0.0f,0.0f);   
        boolean doSleep = true;     
        //�������� 
        world = new World(worldAABB, gravity, doSleep);          
        
        //����4��
        final int kd=20;//��Ȼ�߶�
        MyRectColor mrc=Box2DUtil.createBox(kd/2, SCREEN_HEIGHT/2, kd/2, SCREEN_HEIGHT/2, true,world,Color.YELLOW,false);
        bl.add(mrc);
        mrc=Box2DUtil.createBox(SCREEN_WIDTH-kd/2, SCREEN_HEIGHT/2, kd/2, SCREEN_HEIGHT/2, true,world,Color.YELLOW,false);
        bl.add(mrc);
        mrc=Box2DUtil.createBox(SCREEN_WIDTH/2, kd/2, SCREEN_WIDTH/2, kd/2, true,world,Color.YELLOW,false);
        bl.add(mrc);
        mrc=Box2DUtil.createBox(SCREEN_WIDTH/2, SCREEN_HEIGHT-kd/2, SCREEN_WIDTH/2, kd/2, true,world,Color.YELLOW,false);
        bl.add(mrc);
        
        //����ש��
        final int bs=20;//ש����
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
        
        //������
        MyCircleColor ball=Box2DUtil.createCircle(SCREEN_WIDTH/2, SCREEN_HEIGHT-100, kd, world,Color.MAGENTA);
        bl.add(ball);
        ball.body.setLinearVelocity(new Vec2(5,-40));
        
        //��Ӽ�����
        initContactListener();
           
        GameView gv= new GameView(this);   
        setContentView(gv);   
    }   
    
	//������ײ������
	public void initContactListener()
	{
		ContactListener cl=new ContactListener()
		{
			@Override
			public void add(ContactPoint arg0) 
			{//�ӵ�ǰ��body���ҳ���Ӧ�����ִ���¼�����
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
