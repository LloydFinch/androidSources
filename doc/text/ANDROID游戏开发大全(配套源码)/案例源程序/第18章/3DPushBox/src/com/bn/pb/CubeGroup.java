package com.bn.pb;
import static com.bn.pb.Constant.*;

import javax.microedition.khronos.opengles.GL10;
//表示立方体木箱组的类
public class CubeGroup {
	MySurfaceView father;
	
	Cube cube;//立方体木箱
	Cube cubered;//红颜色立方体
	float yAngle=0;//立方体木箱沿Y轴旋转的角度
	
	int redbincount=0;//红箱子的数量
	int bincount=0;//普通箱子的数量
	int targetcount=0;//目标点的数量

	
	public CubeGroup(MySurfaceView father,int texId,int texId2)
	{
		this.father=father;
		cube=new Cube(CUBE_SIZE,CUBE_SIZE,CUBE_SIZE,texId,texId,texId,texId,texId,texId);
		cubered=new Cube(CUBE_SIZE,CUBE_SIZE,CUBE_SIZE,texId2,texId2,texId2,texId2,texId2,texId2);
	}
	
	
	public void drawSelf(GL10 gl)
	{
		//扫描地图的每个格子
		
		for(int i=0;i<father.rows;i++)
		{
			for(int j=0;j<father.cols;j++)
			{
				if(father.objectMap[i][j]==4)//如果是普通箱子
				{	
					gl.glPushMatrix();//保护现场
					//移动晶体到此格对应的位置
				    gl.glTranslatef((j+0.5f)*UNIT_SIZE-(int)(father.objectMap[0].length/2)*UNIT_SIZE, 
				    		FLOOR_Y+CUBE_SIZE, 
				    		(i+0.5f)*UNIT_SIZE-(int)(father.objectMap.length/2)*UNIT_SIZE );
				    //将晶体绕Y轴旋转
				    gl.glRotatef(yAngle, 0, 1, 0);
				    //绘制晶体
				    cube.drawSelf(gl);
				    gl.glPopMatrix();//恢复现场
				}else if(father.objectMap[i][j]==6)//绘制红颜色的箱子
				{
					gl.glPushMatrix();//保护现场
					//移动晶体到此格对应的位置
				    gl.glTranslatef((j+0.5f)*UNIT_SIZE-(int)(father.objectMap[0].length/2)*UNIT_SIZE, 
				    		FLOOR_Y+CUBE_SIZE, 
				    		(i+0.5f)*UNIT_SIZE-(int)(father.objectMap.length/2)*UNIT_SIZE );
				    //将晶体绕Y轴旋转
				    gl.glRotatef(yAngle, 0, 1, 0);
				    //绘制晶体
				    cubered.drawSelf(gl);
				    gl.glPopMatrix();//恢复现场
					
				}
			}
		}
		
	}
}
