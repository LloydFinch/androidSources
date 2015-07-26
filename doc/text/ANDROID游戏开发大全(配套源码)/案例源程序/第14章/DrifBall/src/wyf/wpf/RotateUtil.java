package wyf.wpf;					//声明包语句
/*
 * 该类为静态工具类，提供静态方法来计算
 * 小球应该的运动方向
 */
public class RotateUtil{			
	//angle为弧度 gVector  为重力向量[x,y,z,1]
	//返回值为旋转后的向量
	public static double[] XRotate(double angle,double[] gVector){
		double[][] matrix={//绕x轴旋转变换矩阵		
		   {1,0,0,0},
		   {0,Math.cos(angle),Math.sin(angle),0},		   
		   {0,-Math.sin(angle),Math.cos(angle),0},		   //原来为：{0,-Math.sin(angle),Math.cos(angle),0},
		   {0,0,0,1}	
		};		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++){
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}		
		return gVector;
	}
	
	//angle为弧度 gVector  为重力向量[x,y,z,1]
	//返回值为旋转后的向量
	public static double[] YRotate(double angle,double[] gVector){
		double[][] matrix={//绕y轴旋转变换矩阵		
		   {Math.cos(angle),0,-Math.sin(angle),0},
		   {0,1,0,0},
		   {Math.sin(angle),0,Math.cos(angle),0},
		   {0,0,0,1}	
		};		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++){
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}		
		return gVector;
	}		
	
	//angle为弧度 gVector  为重力向量[x,y,z,1]
	//返回值为旋转后的向量
	public static double[] ZRotate(double angle,double[] gVector){
		double[][] matrix={//绕z轴旋转变换矩阵		
		   {Math.cos(angle),Math.sin(angle),0,0},		   
		   {-Math.sin(angle),Math.cos(angle),0,0},
		   {0,0,1,0},
		   {0,0,0,1}	
		};		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++){
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}		
		return gVector;
	}	
	//方法：计算小球的方向
	public static int getDirectionCase(double[] values){
		double zAngle=-Math.toRadians(values[0]);//获取z轴旋转角度弧度
		double xAngle=-Math.toRadians(values[1]);//获取x轴旋转角度弧度
		double yAngle=-Math.toRadians(values[2]);//获取y轴旋转角度弧度
		/*
		 * 算法思想为手机在一个姿态后首先虚拟出一个重力向量，
		 * 然后三次选装把手机恢复到原始姿态，期间重力向量伴随
		 * 变化，最后重力向量往手机平面上一投影，根据投影点的
		 * 位置即可得到位置编号0-7
		 *                 0
		 *			  7	   |	1
		 *              \  |  /
		 *				 \ | /
		 *          6------|------2
     *                   / | \	 
		 *              /  |  \
		 *			   5   |   3
		 *                 4
		 */
		
		
		//虚拟一个重力向量
		double[] gVector={0,0,-100,1};		
		/*
		 * 在这里需要注意沿三个空间方向x,y,z轴所旋转的角度的恢复顺序，由于Yaw轴始终指向竖直向上（重力加速度反向），和
		 * 标准的空间坐标系的z轴一样，所以 可以通过负向旋转直接进行角度恢复；沿yaw轴将转过的角度恢复后，此时的pitch轴
		 * 就变成了空间坐标系中的x轴，沿pitch（x）轴将 转过 的角度恢复，此时的roll轴就修正为了空间坐标系中的y轴，最后
		 * 按照y轴将转过的角度恢复，则此时手机平面所在的平面变成了空间坐标 系中x-y平面，而附着于手机平面上的重力加速度
		 * 的则是一个与手机平面相交的向量，将该向量投影到手机平面，通过投影点就可以计算出小球要滚动的方向
		 * 如果不按照上述顺序进行角度恢复，则空间坐标的计算转换将会非常复杂，而上述方法中每一步的角度恢复都是基于标准
		 * 的坐标系轴，而对标准坐标轴的转换在计算机图形
		 * 学中很容易实现
		 */		
		//yaw轴恢复
		gVector=RotateUtil.ZRotate(zAngle,gVector);		
		//pitch轴恢复
		gVector=RotateUtil.XRotate(xAngle,gVector);			
		//roll轴恢复
		gVector=RotateUtil.YRotate(yAngle,gVector);		
		double mapX=gVector[0];		//mapX为重力向量在X、Y轴平面上的投影的X坐标
		double mapY=gVector[1];		//mapY为重力向量在X、Y轴平面上的投影的Y坐标
		if(mapX==0){				//如果mapX等于0
			if(mapY>0){				//mapY大于0
				return 0;			//返回方向为0，向上
			}
			else{					//mapY小于0
				return 4;			//返回4，方向为向下
			}
		}
		else{
			if(mapY==0){			//如果mapY等于0
				if(mapX>0){			//如果mapX大于0
					return 2;		//返回2，方向向右
				}
				else{				//如果mapX小于0
					return 6;		//返回6，方向向左
				}
			}
			else{
				if(mapX>0&&mapY>0){	//如果向量位于第一象限
					return 1;		//返回1，方向右上
				}
				else if(mapX>0&&mapY<0){	//如果向量位于第四象限
					return 3;				//返回3，方向右下
				}
				else if(mapX<0&&mapY>0){	//如果向量位于第二象限
					return 7;				//返回7，方向左上
				}
				else{						//向量位于第三象限
					return 5;				//返回5，方向左下
				}
			}
		}
	}	
}