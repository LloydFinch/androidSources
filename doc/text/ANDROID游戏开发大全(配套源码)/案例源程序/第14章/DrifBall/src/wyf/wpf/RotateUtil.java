package wyf.wpf;					//���������
/*
 * ����Ϊ��̬�����࣬�ṩ��̬����������
 * С��Ӧ�õ��˶�����
 */
public class RotateUtil{			
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] XRotate(double angle,double[] gVector){
		double[][] matrix={//��x����ת�任����		
		   {1,0,0,0},
		   {0,Math.cos(angle),Math.sin(angle),0},		   
		   {0,-Math.sin(angle),Math.cos(angle),0},		   //ԭ��Ϊ��{0,-Math.sin(angle),Math.cos(angle),0},
		   {0,0,0,1}	
		};		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++){
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}		
		return gVector;
	}
	
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] YRotate(double angle,double[] gVector){
		double[][] matrix={//��y����ת�任����		
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
	
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] ZRotate(double angle,double[] gVector){
		double[][] matrix={//��z����ת�任����		
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
	//����������С��ķ���
	public static int getDirectionCase(double[] values){
		double zAngle=-Math.toRadians(values[0]);//��ȡz����ת�ǶȻ���
		double xAngle=-Math.toRadians(values[1]);//��ȡx����ת�ǶȻ���
		double yAngle=-Math.toRadians(values[2]);//��ȡy����ת�ǶȻ���
		/*
		 * �㷨˼��Ϊ�ֻ���һ����̬�����������һ������������
		 * Ȼ������ѡװ���ֻ��ָ���ԭʼ��̬���ڼ�������������
		 * �仯����������������ֻ�ƽ����һͶӰ������ͶӰ���
		 * λ�ü��ɵõ�λ�ñ��0-7
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
		
		
		//����һ����������
		double[] gVector={0,0,-100,1};		
		/*
		 * ��������Ҫע���������ռ䷽��x,y,z������ת�ĽǶȵĻָ�˳������Yaw��ʼ��ָ����ֱ���ϣ��������ٶȷ��򣩣���
		 * ��׼�Ŀռ�����ϵ��z��һ�������� ����ͨ��������תֱ�ӽ��нǶȻָ�����yaw�Ὣת���ĽǶȻָ��󣬴�ʱ��pitch��
		 * �ͱ���˿ռ�����ϵ�е�x�ᣬ��pitch��x���Ὣ ת�� �ĽǶȻָ�����ʱ��roll�������Ϊ�˿ռ�����ϵ�е�y�ᣬ���
		 * ����y�Ὣת���ĽǶȻָ������ʱ�ֻ�ƽ�����ڵ�ƽ�����˿ռ����� ϵ��x-yƽ�棬���������ֻ�ƽ���ϵ��������ٶ�
		 * ������һ�����ֻ�ƽ���ཻ����������������ͶӰ���ֻ�ƽ�棬ͨ��ͶӰ��Ϳ��Լ����С��Ҫ�����ķ���
		 * �������������˳����нǶȻָ�����ռ�����ļ���ת������ǳ����ӣ�������������ÿһ���ĽǶȻָ����ǻ��ڱ�׼
		 * ������ϵ�ᣬ���Ա�׼�������ת���ڼ����ͼ��
		 * ѧ�к�����ʵ��
		 */		
		//yaw��ָ�
		gVector=RotateUtil.ZRotate(zAngle,gVector);		
		//pitch��ָ�
		gVector=RotateUtil.XRotate(xAngle,gVector);			
		//roll��ָ�
		gVector=RotateUtil.YRotate(yAngle,gVector);		
		double mapX=gVector[0];		//mapXΪ����������X��Y��ƽ���ϵ�ͶӰ��X����
		double mapY=gVector[1];		//mapYΪ����������X��Y��ƽ���ϵ�ͶӰ��Y����
		if(mapX==0){				//���mapX����0
			if(mapY>0){				//mapY����0
				return 0;			//���ط���Ϊ0������
			}
			else{					//mapYС��0
				return 4;			//����4������Ϊ����
			}
		}
		else{
			if(mapY==0){			//���mapY����0
				if(mapX>0){			//���mapX����0
					return 2;		//����2����������
				}
				else{				//���mapXС��0
					return 6;		//����6����������
				}
			}
			else{
				if(mapX>0&&mapY>0){	//�������λ�ڵ�һ����
					return 1;		//����1����������
				}
				else if(mapX>0&&mapY<0){	//�������λ�ڵ�������
					return 3;				//����3����������
				}
				else if(mapX<0&&mapY>0){	//�������λ�ڵڶ�����
					return 7;				//����7����������
				}
				else{						//����λ�ڵ�������
					return 5;				//����5����������
				}
			}
		}
	}	
}