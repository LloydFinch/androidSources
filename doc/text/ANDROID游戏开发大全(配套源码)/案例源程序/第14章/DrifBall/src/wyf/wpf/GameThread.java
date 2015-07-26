package wyf.wpf;				//���������
/*
 * �������Ҫ�������Ԫ�ص����ݱ仯���������ʯ�����Ƶ�֡��ʱ�л�
 * ͬʱ�������ƶ�С�򡣲�ͬ��Ԫ�صĲ�ͬ�Ļ�֡�����ͨ��֡���Ƽ�����
 * ��ʵ�ֵġ��ƶ�С���ͬʱ������Ӧ�ؽ�����ײ���
 */
import static wyf.wpf.DriftBall.*;	//����DriftBall��ľ�̬����
public class GameThread extends Thread{
	GameView father;		//GameView��������
	int sleepSpan = 25;		//����ʱ��
	int backCounter;		//����֡���Ƽ�����
	int nebulaCounter;		//����֡���Ƽ�����
	int eatCounter;			//�Ե�С���֡���Ƽ�����
	int trapCounter;		//����֡���Ƽ�����
	int meteoCounter;		//��ʯ֡���Ƽ�����
	boolean flag = false;	//���ѭ��
	boolean isGameOn = false;	//��Ϸ�Ƿ��ڽ���
	//������
	public GameThread(GameView father){
		this.father = father;
		this.flag = true;
	}
	//�߳�ִ�з���
	public void run(){
		while(flag){
			while(isGameOn){
				//�ǿյ��˶�
				backCounter++;
				if(backCounter == 50/sleepSpan){		//�ǿ���500ms�ƶ�һ��
					father.backY -=1;
					backCounter = 0;
					if(father.backY == -600){
						father.backY = 0;
					}
				}
				//���Ƶ��˶�
				nebulaCounter++;
				if(nebulaCounter == 40/sleepSpan){
					father.nebulaY +=1;
					nebulaCounter = 0;
					if(father.nebulaY >= 480){
						father.nebulaY = -150;
						father.nebulaX = (int)(Math.random()*250);
					}
				}
				//��ʯ���˶�
				meteoCounter++;			
				for(Meteorolite m:father.meteoArray){
					m.y +=2;
					if(m.y >= father.screenHeight){			//������Ļ�߽������³�ʼ��
						m.init();				
					}			
				}	
				if(meteoCounter == 150/sleepSpan){
					for(Meteorolite m:father.meteoArray){
						m.index = (m.index + 1)%8;					//-----------------������ħ������---------
					}
					meteoCounter = 0;
				}					
				//���˶���
				eatCounter++;
				if(eatCounter == 200/sleepSpan){
					father.eatIndex = (father.eatIndex+1)%father.bmpEat.length;
					eatCounter = 0;
				}
				//���嶯��
				trapCounter++;
				if(trapCounter == 300/sleepSpan){
					father.trapIndex = (father.trapIndex+1)%father.bmpTrap.length;
					trapCounter = 0;
				}
				//�ƶ��ڵ�
				try{
					if(father.alMissile.size() != 0){
						for(Missile m:father.alMissile){
							m.moveAndCheck();
						}
					}				
				}
				catch(Exception e){
				}	
				//�ƶ�С��
				checkAndMoveBall();
				try{
					Thread.sleep(sleepSpan);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}			
			try{
				Thread.sleep(800);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//����ƶ�С��
	public void checkAndMoveBall() {
		switch(father.direction){
		case 0:				//��������
			father.ballY -= father.velocity;		//Y�����ϵ��ƶ�
			if(checkCollision()){					//����Ƿ�����ײ
				father.ballY += father.velocity;	//��������ײ�ͳ����ƶ�
			}
			break;
		case 1:				//��������
			father.ballY -= father.velocity;			//Y�����ϵ��ƶ�
			if(checkCollision()){						//����Ƿ�����ײ
				father.ballY += father.velocity;		//��������ײ�ͳ����ƶ�
			}
			father.ballX += father.velocity;			//X�����ϵ��ƶ�
			if(checkCollision()){						//����Ƿ�����ײ
				father.ballX -= father.velocity;		//��������ײ�ͳ����ƶ�
			}
			break;
		case 2:				//��������
			father.ballX += father.velocity;			//X�����ϵ��ƶ�
			if(checkCollision()){						//����Ƿ�����ײ
				father.ballX -= father.velocity;		//��������ײ�ͳ����ƶ�
			}
			break;
		case 3:				//��������
			father.ballX += father.velocity;			//X�����ϵ��ƶ�
			if(checkCollision()){						//����Ƿ�����ײ
				father.ballX -= father.velocity;		//��������ײ�ͳ����ƶ�
			}
			father.ballY += father.velocity;			//Y�����ϵ��ƶ�
			if(checkCollision()){						//����Ƿ�����ײ
				father.ballY -= father.velocity;		//��������ײ�ͳ����ƶ�
			}
			break;
		case 4:				//��������
			father.ballY += father.velocity;			//Y�����ϵ��ƶ�
			if(checkCollision()){						//����Ƿ�����ײ
				father.ballY -= father.velocity;		//��������ײ�ͳ����ƶ�
			}
			break;
		case 5:				//��������
			father.ballX -= father.velocity;			//X�����ϵ��ƶ�
			if(checkCollision()){						//����Ƿ�����ײ
				father.ballX += father.velocity;		//��������ײ�ͳ����ƶ�
			}
			father.ballY += father.velocity;			//Y�����ϵ��ƶ�
			if(checkCollision()){						//��������ײ�ͳ����ƶ�
				father.ballY -= father.velocity;		//��������ײ�ͳ����ƶ�
			}
			break;
		case 6:				//��������
			father.ballX -= father.velocity;			//X�����ϵ��ƶ�
			if(checkCollision()){						//��������ײ�ͳ����ƶ�
				father.ballX += father.velocity;		//��������ײ�ͳ����ƶ�
			}
			break;
		case 7:				//��������
			father.ballX -= father.velocity;			//X�����ϵ��ƶ�
			if(checkCollision()){						//��������ײ�ͳ����ƶ�
				father.ballX += father.velocity;		//��������ײ�ͳ����ƶ�
			}
			father.ballY -= father.velocity;			//Y�����ϵ��ƶ�
			if(checkCollision()){						//��������ײ�ͳ����ƶ�
				father.ballY += father.velocity;		//��������ײ�ͳ����ƶ�
			}
			break;
		}
	}
	/*
	 * ������ײ��⣬��ײ����Ƿֿ����еģ���x�����y�����Ƿֿ���
	 * �������㴦�����⡣��ײ�����Ҫ�Ǽ��С���ĸ����Ƿ�λ�ڲ���ͨ��
	 * �ĵط���ͬʱ����Ƿ񳬳���Ļ�߽磬���鿴�Լ����ڵ�λ���Ƿ�
	 * �����塢�ҵȵط�������ֵΪtrue��ʾ����ͨ��
	 */
	public boolean checkCollision() {
		int size = father.tileSize;		//��ȡ��ͼͼԪ��С
		byte [][] map = father.currMap;	//��ȡ��ͼ����
		int row=0;			
		int col=0;		
		//������Ͻ�
		col = father.ballX/size;			//������Ͻ��ڵ�ͼ����ռ������
		row = father.ballY/size;			//������Ͻ��ڵ�ͼ����ռ������
		if(col>=0 && col<map[0].length && row>=0&&row<map.length && map[row][col] == 0){
			return true;
		}
		//������Ͻ�
		col = (father.ballX + size-1)/size;//������Ͻ��ڵ�ͼ����ռ������
		row = father.ballY/size;			//������Ͻ��ڵ�ͼ����ռ������
		if(col>=0 && col<map[0].length && row>=0&&row<map.length && map[row][col] == 0){
			return true;
		}
		//������½�
		col = (father.ballX+size-1)/size;//������½��ڵ�ͼ����ռ������
		row = (father.ballY+size-1)/size;//������½��ڵ�ͼ����ռ������
		if(col>=0 && col<map[0].length && row>=0&&row<map.length && map[row][col] == 0){
			return true;
		}
		//������½�
		col = father.ballX/size;		//������½��ڵ�ͼ����ռ������
		row = (father.ballY+size-1)/size;	//������½��ڵ�ͼ����ռ������
		if(col>=0 && col<map[0].length && row>=0&&row<map.length && map[row][col] == 0){
			return true;
		}
		//�����߽�
		if(father.ballX < 0){
			return true;
		}
		//����ϱ߽�
		if(father.ballY < 0){
			return true;
		}
		//����ұ߽�
		if(father.ballX + size > father.currMap[0].length * size){			//ǰ�� ��sizeָ����С��ĳߴ磬�������ָͼԪ�ߴ�
			return true;
		}
		//����±߽�
		if(father.ballY + size > father.currMap.length*size){
			return true;
		}
		//����Ƿ������������
		col = father.ballX/size;
		row = father.ballY/size;
		if(col == (father.ballX + size-1)/size && row==(father.ballY+size-1)/size){	//�ж�С���Ƿ�������ĳ��������
			switch(father.currMap[row][col]){
			case 2:		//������
				father.status = STATUS_WIN;		//������Ϸ״̬ΪSTATUS_WIN
				if(father.father.wantSound){	//������Ҫ��������
					try {
						father.father.mpGameWin.start();
					} catch (Exception e) {}
				}
				isGameOn = false;				//��ͣGameThread��ִ��
				if(father.father.level == MAX_LEVEL){	//����Ƿ������һ��
					father.status = STATUS_PASS;		//������Ϸ״̬ΪSTATUS_PASS
				}
				else{
					father.father.level += 1;			//����������һ�أ��ͽ��ؿ���1
				}
				break;
			case 3:		//�Ե�������
				if(father.father.wantSound){			//������Ҫ��������
					try{
						father.father.mpPlusLife.start();
					}
					catch(Exception e){}
				}
				father.ballX = col*size;				//����С���X����
				father.ballY = row*size;				//����С���Y����
				father.currMap[row][col] = 1;			//����ͼ�����и�λ�ø�Ϊ��·
				father.father.life += 1;				//С�����������1
				break;
			case 4:		//������
				father.status = STATUS_LOSE;			//������Ϸ״̬ΪSTATUS_LOSE
				if(father.father.wantSound){			//������Ҫ��������
					try {
						father.father.mpBreakOut.start();
					} catch (Exception e) {}
				}
				father.father.life -=1;					//С�����������1
				if(father.father.life <=0){
					father.status=STATUS_OVER;			//���С�������ľ�������״̬λΪSTATUS_OVER
				}
				else{
					father.ballX = 0;					//��λС������
					father.ballY = 0;					
				}
				break;
			case 5:		//����
				if(father.trapIndex  > 3){				//�ж�Ŀǰ����Ķ���֡
					father.status = STATUS_LOSE;		//����״̬λSTATUS_LOSE
					if(father.father.wantSound){		
						try {
							father.father.mpBreakOut.start();
						} catch (Exception e) {}
					}
					father.father.life -= 1;			//��������1
					if(father.father.life<=0){			//��������ľ�
						father.status = STATUS_OVER;	//����״̬ΪSTATUS_OVER
					}
					else{								//�������������λ������
						father.ballX = 0;
						father.ballY = 0;
					}
				}
				break;
			default:
				break;
			}
		}
		return false;
	}
}