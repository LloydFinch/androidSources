package wyf.wpf;			//���������
/*
 * ����̳���Thread�࣬��Ҫ���𽫲˵����˵�������Ļ�ϵ�λ��
 * ���иı��Դﵽ����Ч��
 */
public class GameMenuThread extends Thread{
	DriftBall father;		//��������	
	boolean flag;			//ѭ�����Ʊ���
	boolean isIn;			//�Ƿ��ǵ���˵�
	boolean isOut;			//�Ƿ��ǵ����˵�
	int sleepSpan = 20;		//˯��ʱ��
	int [][] menuCoordinate={	//�˵���û����֮ǰ��λ�ã��ֱ��ǲ˵�������4���˵���
			{350,0},
			{400,30},
			{450,80},
			{500,130},
			{550,180}
	};
	//������
	public GameMenuThread(DriftBall father){
		this.father = father;
		father.gv.menuCoordinate = this.menuCoordinate;
		flag = true;
		isIn = true;
	}
	//�߳�ִ�з���
	public void run(){
		int inIndex=0;			//���������˳��
		int outIndex = 4;		//����ʱ������˳��
		while(flag){
			try{
			if(isIn){		//�˵�����Ļ
				for(int i=inIndex;i<father.gv.menuCoordinate.length;i++){
					father.gv.menuCoordinate[i][0]-= 25;		//����Ļ���ƶ��˵���
					if(father.gv.menuCoordinate[i][0] == 150){		//�жϲ˵����Ƿ��ѵ�λ
						inIndex=i+1;			//����ʼ�������ƣ������ٸı䵽λ�Ĳ˵�������
					}
				}				
				if(inIndex == 5){		//������в˵���ƶ���λ
					isIn = false;		//����isIn��־λΪfalse
				}
			}
			else if(isOut){			//�˵�����Ļ
				father.gv.menuCoordinate[outIndex][0] +=10;		//����Ļ���ƶ��˵���
				if(father.gv.menuCoordinate[outIndex][0] >=320){	//�ж��Ƿ񽫲˵����Ƴ���Ļ
					outIndex--;									//���ƶ�ǰһ���˵���
					if(outIndex < 0){		//�ж��Ƿ����еĲ˵���Ƴ���Ļ
						if(father.currView == father.gv){	//�����ǰView��GameView
							father.gv.resumeGame();			//����resumeGame�����ָ���Ϸ
						}						
						flag = false;			//ֹͣ�̵߳�ִ��						
					}
				}
			}		
				//�߳�����
				Thread.sleep(sleepSpan);
			}
			catch(Exception e)
			{
				flag=false;
				e.printStackTrace();
			}
		}
	}
}