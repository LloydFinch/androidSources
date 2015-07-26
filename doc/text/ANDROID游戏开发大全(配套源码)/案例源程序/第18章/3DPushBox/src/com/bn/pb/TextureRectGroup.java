package com.bn.pb;
import static com.bn.pb.Constant.*;
import javax.microedition.khronos.opengles.GL10;
//表示立方体木箱组的类
public class TextureRectGroup {
	MySurfaceView father;
	
	TextureRect texturerect1;//目标纹理矩形
	
	float yAngle=0;//旋转角度
	
	int objectMap[][];//用于复制当前关的地图的二维数组
	int rows=0;//当前二维数组的行数
	int cols=0;//当前二维数组的列数
	
	public TextureRectGroup(MySurfaceView father,int texId1)
	{
		this.father=father;
		texturerect1=new TextureRect(CUBE_SIZE,CUBE_SIZE,texId1);
		
		//------------------复制当前关的二维数组-------------------------------------------
		rows=Constant.MAP[Constant.COUNT].length;
		cols=Constant.MAP[Constant.COUNT][0].length;
		
		objectMap=new int[rows][cols];
		
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				objectMap[i][j]=Constant.MAP[Constant.COUNT][i][j];
			}
		}
		//------------------复制当前关的二维数组end-------------------------------------------
	}
	
	public void drawSelf(GL10 gl)
	{	
		//扫描地图的每个格子，若此格有有目标则绘制
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				if(objectMap[i][j]==3)//绘制目标
				{
					gl.glPushMatrix();//保护现场
					//移动到此格对应的位置
				    gl.glTranslatef((j+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT][0].length/2)*UNIT_SIZE, 
				    		FLOOR_Y+0.1f, 
				    		(i+0.5f)*UNIT_SIZE-(int)(Constant.MAP[Constant.COUNT].length/2)*UNIT_SIZE );
				    //绕Y轴旋转
				    gl.glRotatef(yAngle, 0, 1, 0);
				    gl.glRotatef(-90, 1, 0, 0);
				    //绘制
				    texturerect1.drawSelf(gl);
				    gl.glPopMatrix();//恢复现场
				}
			}
		}
	}
}
