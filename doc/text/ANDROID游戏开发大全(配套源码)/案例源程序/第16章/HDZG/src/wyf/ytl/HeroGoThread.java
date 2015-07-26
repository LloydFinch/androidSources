package wyf.ytl;
/*
 * ��Ҫע����ǣ�Ӣ�۵Ķ����εĸı���ÿ���޼���֮ǰ���� �ɶ�̬�ķ��򶯻���
 * ��������һ�����Ӽ���Ƿ���Ҫ����ʱ��������Ϊ��̬����Ķ�����
 */
import static wyf.ytl.ConstantUtil.DOWN;
import static wyf.ytl.ConstantUtil.GAME_VIEW_SCREEN_COLS;
import static wyf.ytl.ConstantUtil.GAME_VIEW_SCREEN_ROWS;
import static wyf.ytl.ConstantUtil.HERO_MOVING_SLEEP_SPAN;
import static wyf.ytl.ConstantUtil.HERO_MOVING_SPAN;
import static wyf.ytl.ConstantUtil.HERO_WAIT_SPAN;
import static wyf.ytl.ConstantUtil.LEFT;
import static wyf.ytl.ConstantUtil.MAP_COLS;
import static wyf.ytl.ConstantUtil.MAP_ROWS;
import static wyf.ytl.ConstantUtil.RIGHT;
import static wyf.ytl.ConstantUtil.ROLL_SCREEN_SPACE_DOWN;
import static wyf.ytl.ConstantUtil.ROLL_SCREEN_SPACE_LEFT;
import static wyf.ytl.ConstantUtil.ROLL_SCREEN_SPACE_RIGHT;
import static wyf.ytl.ConstantUtil.ROLL_SCREEN_SPACE_UP;
import static wyf.ytl.ConstantUtil.SCREEN_HEIGHT;
import static wyf.ytl.ConstantUtil.SCREEN_WIDTH;
import static wyf.ytl.ConstantUtil.TILE_SIZE;
import static wyf.ytl.ConstantUtil.UP;

import java.io.Serializable;

public class HeroGoThread extends Thread implements Serializable{
	private static final long serialVersionUID = -5456204729671665857L;//ָ���汾��
	Hero hero;//Ӣ�۵�����
	GameView gv;//��Ϸ��ͼ���������
	boolean flag;//�߳��Ƿ�ִ�б�־λ
	boolean isMoving;//Ӣ���Ƿ����߱�־λ
	int sleepSpan = HERO_MOVING_SLEEP_SPAN;//Ӣ����·ʱûһС��������ʱ��
	int waitSpan = HERO_WAIT_SPAN;//Ӣ�۲���ʱ�߳̿�ת�ĵȴ�ʱ��
	int steps =0;//��¼��Ҫ�ߵĲ�������������
	int [][] notIn;//����ͨ�����������
	
	public HeroGoThread(){}
	
	//������
	public HeroGoThread(GameView gv,Hero hero){
		super.setName("==HeroGoThread");
		this.gv = gv;
		this.hero = hero;
		this.flag = true;
	}
	//�߳�ִ�з���
	public void run(){
		while(flag){
			while(isMoving){
				for(int i=0;i<steps;i++){//��ÿһ���ӽ����޼��ƶ�
					int moves = TILE_SIZE/HERO_MOVING_SPAN;//������������Ҫ����С�������
					int hCol = hero.col;//Ӣ�۵�ǰ�ڴ��ͼ�е���
					int hRow = hero.row;//Ӣ�۵�ǰ�ڴ��ͼ�е���
					int destCol=hCol;//Ŀ���������
					int destRow=hRow;//Ŀ���������
					//�����Ŀ�ĵ�ĸ����к���,֮����ģ4����Ϊ��̬���Ϻ;�ֹ�������ò�4
					switch(hero.direction%4){
					case 0://����
						destRow = hRow+1;
						hero.setAnimationDirection(DOWN);//��Ӣ�۵ķ���Ͷ���������Ϊ��̬����
						break;
					case 1://����
						destCol = hCol-1;
						hero.setAnimationDirection(LEFT);//��Ӣ�۵ķ���Ͷ���������Ϊ��̬����
						break;
					case 2://����
						destCol = hCol+1;
						hero.setAnimationDirection(RIGHT);//��Ӣ�۵ķ���Ͷ���������Ϊ��̬����
						break;
					case 3://����
						destRow = hRow-1;
						hero.setAnimationDirection(UP);//��Ӣ�۵ķ���Ͷ���������Ϊ��̬����
						break;						
					}
					int destX=destCol*TILE_SIZE+TILE_SIZE/2+1;//Ŀ�ĵ�x���꣬�Ѿ�ת�������ĵ���
					int destY=destRow*TILE_SIZE+TILE_SIZE/2+1;//Ŀ�ĵ�y����,�Ѿ�ת�������ĵ����
					int hx = hero.x;
					int hy = hero.y;
					//�����濪ʼ�޼���һ�������ߵ���һ������
					for(int j=0;j<moves;j++){
						try{//��˯һ��
							Thread.sleep(HERO_MOVING_SLEEP_SPAN);
						}
						catch(Exception e){
							e.printStackTrace();
						}
						//����Ӣ�۵�xλ��
						if(hx<destX){
							hero.x = hx+j*HERO_MOVING_SPAN;
							hero.col = hero.x/TILE_SIZE;//��ʱ����Ӣ�۵�����ֵ
							checkIfRollScreen(hero.direction);
						}
						else if(hx>destX){
							hero.x = hx-j*HERO_MOVING_SPAN;
							hero.col = hero.x/TILE_SIZE;//��ʱ����Ӣ�۵�����ֵ
							checkIfRollScreen(hero.direction);
						}
						//����Ӣ�۵�yλ��
						if(hy<destY){
							hero.y = hy+j*HERO_MOVING_SPAN;
							hero.row = hero.y/TILE_SIZE;//��ʱ����Ӣ�۵�����ֵ
							checkIfRollScreen(hero.direction);
						}
						else if(hy>destY){
							hero.y = hy-j*HERO_MOVING_SPAN;
							hero.row = hero.y/TILE_SIZE;//��ʱ����Ӣ�۵�����ֵ
							checkIfRollScreen(hero.direction);
						}						
					}
					//����x��y����,�޸�Ӣ�۵�ռλ����
					hero.x = destX;
					hero.y = destY;
					hero.col = destCol;
					hero.row = destRow;
					//����offsetX��y
					if(gv.offsetX<HERO_MOVING_SPAN){//��ȥ
						gv.offsetX = 0;
					}
					else if(gv.offsetX>TILE_SIZE - HERO_MOVING_SPAN){//��λ
						if(gv.startCol + GAME_VIEW_SCREEN_COLS < MAP_COLS -1){
							gv.offsetX=0;
							gv.startCol+=1;
						}						
					}
					if(gv.offsetY<HERO_MOVING_SPAN){//��ȥ
						gv.offsetY = 0;
					}
					else if(gv.offsetY>TILE_SIZE - HERO_MOVING_SPAN){//��λ
						if(gv.startRow + GAME_VIEW_SCREEN_ROWS < MAP_ROWS -1){
							gv.startRow+=1;
							gv.offsetY = 0;
						}						
					}					
					hero.direction = checkIfTurn();//����Ƿ���Ҫ����
					
				}//����������ָ���ĸ�������Ӧ�ü����û������������				
				//��ͣ����
				this.setMoving(false);//ֹͣ�߶�
				hero.setAnimationDirection(hero.direction%4);//���ö�����Ϊ��Ӧ�ľ�ֹ̬
				//���ͣ������û�п����Ķ���
				if(!checkIfMeet()){//���û������������״̬���������Ӽ����任					
					this.gv.setStatus(0);//��������GameView��״̬0Ϊ����״̬
					gv.gvt.setChanging(true);	//���������ӱ任
				}				
				hero.growStrength(gv.currentSteps);		//����Ӣ������	
			} 
			try{//�߳̿�ת�ȴ�
				Thread.sleep(waitSpan);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	//������������Ҫ�ߵĲ���,ʹ��ʱ������Ҫ�ߵĲ�������������·��־λ
	public void setSteps(int steps){
		this.steps = steps;
	}
	//�����������Ƿ���·��־λ
	public void setMoving(boolean isMoving){
		this.isMoving = isMoving;
	}
	/*
	 * ����������Ƿ���Ҫ������������Ϊ��λ
	 */
	public void checkIfRollScreen(int direction){//������0����1����2����3
		int heroX = hero.x;
		int heroY = hero.y;
		int tempOffsetX = gv.offsetX;
		int tempOffsetY = gv.offsetY;
		switch(direction%4){
		case 0://���¼��
			if(heroY - gv.startRow*TILE_SIZE -tempOffsetY + ROLL_SCREEN_SPACE_DOWN >= SCREEN_HEIGHT){//����Ƿ���Ҫ�¹�
				if(gv.startRow + GAME_VIEW_SCREEN_ROWS < MAP_ROWS -1){//���Խ��ܽ�λ�ͼ�
					gv.offsetY += HERO_MOVING_SPAN;
					if(gv.offsetY > TILE_SIZE){//��Ҫ��λ
						gv.startRow += 1;
						gv.offsetY = 0;
					}
				}
			}
			break;
		case 1://������
			if(heroX - gv.startCol*TILE_SIZE - tempOffsetX <= ROLL_SCREEN_SPACE_LEFT){//����Ƿ���Ҫ�����
				if(gv.startCol > 0){//startCol������
					gv.offsetX -= HERO_MOVING_SPAN;//����ƫ��Ӣ�۲�����������
					if(gv.offsetX < 0){					
						gv.startCol -=1;//
						gv.offsetX = TILE_SIZE-HERO_MOVING_SPAN;//�д�����		
					}
				}
				else if(gv.offsetX > HERO_MOVING_SPAN){//���������������������ƫ��������
					gv.offsetX -= HERO_MOVING_SPAN;//����ƫ��Ӣ�۲�����������
				}
			}			
			break;
		case 2://���Ҽ��
			if(heroX - gv.startCol*TILE_SIZE - tempOffsetX + ROLL_SCREEN_SPACE_RIGHT >= SCREEN_WIDTH){//����Ƿ���Ҫ�ҹ���
				if(gv.startCol + GAME_VIEW_SCREEN_COLS < MAP_COLS -1){//startCol���ܼ�
					gv.offsetX += HERO_MOVING_SPAN;//����ƫ��Ӣ�۲�����������
					if(gv.offsetX > TILE_SIZE){//��Ҫ��λ
						gv.startCol += 1;
						gv.offsetX = 0;/////////�д�����
					}
				}
			}
			break;
		case 3://���ϼ��
			if(heroY - gv.startRow*TILE_SIZE - tempOffsetY <= ROLL_SCREEN_SPACE_UP){//����Ƿ���Ҫ�����
				if(gv.startRow >0){//startRow ���ܽ�
					gv.offsetY -= HERO_MOVING_SPAN;
					if(gv.offsetY < 0){
						gv.startRow -=1;;
						gv.offsetY = TILE_SIZE-HERO_MOVING_SPAN;
					}
				}
				else if(gv.offsetY > HERO_MOVING_SPAN){//�������������ˣ�����ƫ��������
					gv.offsetY -= HERO_MOVING_SPAN;
				}
			}
			break;
		}
	}
	/*
	 * ����������Ƿ���Ҫ����,����Ӣ�۵��˶����������Һ�ǰ��3�������Ƿ��ͨ��
	 * �������һ�����ӿ�ͨ���������ѡȡһ������󷵻�һ������ֵ��Ӣ��
	 */	
	public int checkIfTurn(){
		int [] directions = new int[3];//��ſ�ѡ�������3�����ֱ��������ڵ�ǰ��������Һ�ǰ
		int choices=0;//��¼��ѡ����ĸ���
		int col = hero.col;
		int row = hero.row;
		switch(hero.direction%4){
		case 0://����
			if(gv.notInMatrix[row][col-1] == 0){//�������Ƿ��ͨ��
				directions[choices++] = 1;//����Ϊ��̬����
			}
			if(gv.notInMatrix[row][col+1] == 0){//����ұ��Ƿ��ͨ��
				directions[choices++] = 2;//����Ϊ��̬����
			}
			if(gv.notInMatrix[row+1][col] == 0){//����±��Ƿ��ͨ��
				directions[choices++] = 4;//����Ϊ��̬����
			}
			break;
		case 1://����
			if(gv.notInMatrix[row][col-1] == 0){//�������Ƿ��ͨ��
				directions[choices++] = 1;//����Ϊ��̬����
			}
			if(gv.notInMatrix[row-1][col] == 0){//����ϱ��Ƿ��ͨ��
				directions[choices++] = 3;//����Ϊ��̬����
			}
			if(gv.notInMatrix[row+1][col] == 0){//����±��Ƿ��ͨ��
				directions[choices++] = 4;//����Ϊ��̬����
			}
			break;
		case 2://����
			if(gv.notInMatrix[row][col+1] == 0){//����ұ��Ƿ��ͨ��
				directions[choices++] = 2;//����Ϊ��̬����
			}
			if(gv.notInMatrix[row-1][col] == 0){//����ϱ��Ƿ��ͨ��
				directions[choices++] = 3;//����Ϊ��̬����
			}
			if(gv.notInMatrix[row+1][col] == 0){//����±��Ƿ��ͨ��
				directions[choices++] = 4;//����Ϊ��̬����
			}
			break;
		case 3://����
			if(gv.notInMatrix[row][col-1] == 0){//�������Ƿ��ͨ��
				directions[choices++] = 1;//����Ϊ��̬����
			}
			if(gv.notInMatrix[row][col+1] == 0){//����ұ��Ƿ��ͨ��
				directions[choices++] = 2;//����Ϊ��̬����
			}
			if(gv.notInMatrix[row-1][col] == 0){//����ϱ��Ƿ��ͨ��
				directions[choices++] = 3;//����Ϊ��̬����
			}
			break;						
		}
		return directions[(int)(Math.random()*100)%choices];//�ӿ�ѡ���������ѡȡһ������
	}
	/*
	 * �÷������Ӣ��ͣ����λ������(�����Ӣ�۵�ǰ���������)��û��ʲô�����Ķ���
	 * ���泡��ɭ�ֵȵ�
	 */
	public boolean checkIfMeet(){
		MyMeetableDrawable mmd = gv.meetableChecker.check(hero);
		if(mmd != null && mmd!=gv.previousDrawable){//��������˿�����Ҹÿ����ﲢ������һ��ͬ�����Ǹ�
			gv.setOnTouchListener(mmd);
			gv.currentDrawable = mmd;
			gv.previousDrawable = mmd;//��¼Ϊǰһ�����Է��´���һ��������ͬ�����Ǹ�
			if(gv.activity.isEnvironmentSound){
				gv.playSound(1, 0);
			}
			return true;
		}
		gv.previousDrawable = mmd;
		return false;
	}
}