package wyf.ytl;

import java.util.HashMap;
import java.util.Set;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class ManPanelView{
	GameView gameView;
	
	int yeSpan = 11;//ÿҳ��ʾ�ĸ���
	int yeSpan2 = 10;//ÿҳ��ʾ�ĸ���
	
	int currentI = 0;//��ǰ���Ƶ���Ļ���ϱߵ�Ԫ���±�
	int selectI = 0;
	
	int startY = 120;
	int startX = 25;
	
	int status = 0;//��ʾ�ı��λ��0-�����б�  1-�����б�
	
	String[][] items1;//��Ҫ�г������� 
	String[][] items2;//��Ҫ�г��ļ��� 
	
	static Bitmap threeBitmap;//���Ͻǵ�������ť
	static Bitmap panel_back;//����ͼ
	static Bitmap selectBackground;//ѡ�к�ı���
	static Bitmap buttonBackGround;//��ť����
	static Bitmap upBitmap;//���ϵ�С��ͷ
	static Bitmap downBitmap;//���µ�С��ͷ
	static Bitmap menuTitle;//���ı��ⱳ��
	static Bitmap logo;
	
	Paint paint;
	public ManPanelView(GameView gameView) {
		this.gameView = gameView;
		paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//����������ɫ
		paint.setAntiAlias(true);//�����
		initData();//��ʼ������
	}
	
	public static void initBitmap(Resources r){//��ʼ��ͼƬ��Դ  		
		Bitmap menu_item = BitmapFactory.decodeResource(r, R.drawable.buttons); 
		threeBitmap = Bitmap.createBitmap(menu_item, 60, 0, 90, 30);
		buttonBackGround = Bitmap.createBitmap(menu_item, 0, 0, 60, 30);
		upBitmap = Bitmap.createBitmap(menu_item, 210, 15, 15, 15);
		downBitmap = Bitmap.createBitmap(menu_item, 210, 0, 15, 15);
		menu_item = null;//�ͷŵ���ͼ
		panel_back = BitmapFactory.decodeResource(r, R.drawable.panel_back);
		logo = BitmapFactory.decodeResource(r, R.drawable.logo);
		selectBackground = BitmapFactory.decodeResource(r, R.drawable.select_back);
		menuTitle = BitmapFactory.decodeResource(r, R.drawable.menu_title);//���ⱳ��
	}
	
	public void initData(){//��ʼ������
		//��ʼ����������
		this.items1 = new String[][]
		{
			{"����:", gameView.hero.getName()},
			{"�ȼ�:", gameView.hero.getLevel()+" ��"},
			{"����:", gameView.hero.getTitle()},
			{"����:", gameView.hero.getGeneralNumber()+" ��"},
			{"�ǳ�:", gameView.hero.getCityList().size()+" ��"},
			{"�ƽ�:", gameView.hero.getTotalMoney()+" ��"},
			{"����:", gameView.hero.getTotalFood()+" ʯ"},
			{"����:", gameView.hero.getTotalArmy()+""},
			{"���˿�:", gameView.hero.getTotalCitizen()+" ��"},
			{"Ͷʯ��:", gameView.hero.getTotalWarTank()+" ��"},
			{"����:", gameView.hero.getTotalWarTower()+" ��"},
		};
		
		//��ʼ������
		HashMap<Integer,Skill> heroSkill = gameView.hero.getHeroSkill();//�õ�Ӣ�۵����м���
		items2 = new String[heroSkill.size()][3]; 
		Set<Integer> s = heroSkill.keySet();
		int k = 0;
		for(Integer i : s){
			Skill skill = heroSkill.get(i);
			items2[k][0] = skill.getName();
			items2[k][1] = skill.getProficiencyLevel()+"";
			items2[k][2] = skill.getStrengthCost()+"";
			k++;
		}
	}
	
	public void onDraw(Canvas canvas){//���Ƶķ��� 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//����logo
		paint.setTextSize(23);//�������ִ�С
		canvas.drawText("��������", 50, 40, paint);
		paint.setTextSize(18);//�������ִ�С
		
		canvas.drawBitmap(buttonBackGround, 15, 57, paint);
		canvas.drawBitmap(buttonBackGround, 85, 57, paint);
		canvas.drawText("����", 27, 78, paint);
		canvas.drawText("����", 97, 78, paint);
		
		if(status == 0){//������ʱ
			int tempCurrentI = currentI;
			if(items1.length < yeSpan){//��һ����Ļ������ʾȫʱ
				for(int i=0; i<items1.length; i++){
					for(int j=0; j<items1[i].length; j++){
						canvas.drawText(items1[i][j], startX+j*115, startY + 30*i, paint);
					}
				}
			} 
			else{
				for(int i=tempCurrentI; i<tempCurrentI+yeSpan; i++){
					for(int j=0; j<items1[i].length; j++){
						canvas.drawText(items1[i][j], startX+j*115, startY + 30*(i-tempCurrentI), paint);
					}
				}			
			}
			canvas.drawBitmap(selectBackground, 10, startY + 30*selectI - 22, paint);
			if(tempCurrentI != 0){//����С�����ϼ�ͷ
				canvas.drawBitmap(upBitmap, 150, 85, paint);
			}
			if(items1.length>yeSpan && (tempCurrentI+yeSpan) < items1.length){//����С�����¼�ͷ
				canvas.drawBitmap(downBitmap, 150, 455, paint);
			}
		}
		else if(status == 1){//���Ƽ���
			canvas.drawBitmap(menuTitle, 10, 100, paint);
			canvas.drawText("������", 15, 120, paint);
			canvas.drawText("�ȼ�", 125, 120, paint);
			canvas.drawText("����", 240, 120, paint);
			
			int tempCurrentI = currentI;
			if(items2.length < yeSpan2){//��һ����Ļ������ʾȫʱ
				for(int i=0; i<items2.length; i++){
					
					canvas.drawText(items2[i][0], 15, startY + 35 + 30*i, paint);
					canvas.drawText(items2[i][1], 125, startY + 35 + 30*i, paint);
					canvas.drawText(items2[i][2], 240, startY + 35 + 30*i, paint);
				}
			} 
			else{
				for(int i=tempCurrentI; i<tempCurrentI+yeSpan2; i++){
					
					canvas.drawText(items2[i][0], 15, startY + 35 + 30*(i-tempCurrentI), paint);
					canvas.drawText(items2[i][1], 125, startY + 35 + 30*(i-tempCurrentI), paint);
					canvas.drawText(items2[i][2], 240, startY + 35 + 30*(i-tempCurrentI), paint);
				}			
			}
			canvas.drawBitmap(selectBackground, 10, startY + 35 + 30*selectI - 22, paint);
			if(tempCurrentI != 0){//����С�����ϼ�ͷ
				canvas.drawBitmap(upBitmap, 150, 85, paint);
			}
			if(items2.length>yeSpan2 && (tempCurrentI+yeSpan2) < items2.length){//����С�����¼�ͷ
				canvas.drawBitmap(downBitmap, 150, 455, paint);
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//��Ļ������
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(y>15 && y<45){
				if(x>212 && x<242){//��������·�ҳ��ť
					if(status == 0){//��������ʱ 
						selectI++;
						if(items1.length < yeSpan){//��һ����Ļ����ȫ����ʾʱ��������Ҫ���� 
							if(selectI > items1.length-1){ 
								selectI = items1.length-1;
							}
						}
						else {//��һ����ʾ��ȫ����Ҫ����ʱ
							if(selectI > yeSpan-1){ 
								selectI = yeSpan-1;
								currentI++;
								if((currentI+yeSpan) > items1.length){
									currentI--;
								}
							}						
						}						
					}
					else if(status == 1){//������ʾʱ
						selectI++;
						if(items2.length < yeSpan2){//��һ����Ļ����ȫ����ʾʱ��������Ҫ���� 
							if(selectI > items2.length-1){ 
								selectI = items2.length-1;
							}
						}
						else {//��һ����ʾ��ȫ����Ҫ����ʱ
							if(selectI > yeSpan2-1){ 
								selectI = yeSpan2-1;
								currentI++;
								if((currentI+yeSpan2) > items2.length){
									currentI--;
								}
							}						
						}
					}
				}
				else if(x>243 && x<273){//��������Ϸ�ҳ��ť
					selectI--;
					if(selectI < 0){
						selectI = 0;
						currentI--;
						if(currentI < 0){
							currentI = 0;
						}
					}					
				}
				else if(x>274 && x<304){//����˹رհ�ť
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					gameView.setStatus(0);
				}
			}
			else if(y>57 && y<57+buttonBackGround.getHeight() && x>15 && x<15 + buttonBackGround.getWidth()){//��������԰�ť
				this.status = 0;
				this.selectI = 0;
				this.currentI = 0;
			}
			else if(y>57 && y<57+buttonBackGround.getHeight() && x>85 && x<85 + buttonBackGround.getWidth()){//����˼��ܰ�ť
				this.status = 1;
				this.selectI = 0;
				this.currentI = 0;
			}
		}
		return true;
	}
}
