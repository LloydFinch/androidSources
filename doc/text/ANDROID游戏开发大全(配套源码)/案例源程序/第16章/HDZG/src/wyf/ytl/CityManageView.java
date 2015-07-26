package wyf.ytl;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class CityManageView {
	GameView gameView;
	int yeSpan1 = 9;//��ҳ��ʾ�ǳصĸ��� 
	int yeSpan2 = 9;//����ҳ��ʾ�ǳصĸ��� 
	int currentI = 0;//��ǰ���Ƶ���Ļ���ϱߵ�Ԫ���±�
	int selectI = 0;
	
	int startY = 120;
	int startX = 17;
	int status = 0;//0-������  1-��ϸ��Ϣ

	String[][] items1;//�ǳ��б� 
	String[][] items2;//�ǳع�������б�
	
	ArrayList<CityDrawable> cityList;//���Ӣ��ӵ�еĳǳ�
	CityDrawable selectCityDrawable;//��ǰѡ�еĳ���

	static Bitmap menuTitle;//���ı��ⱳ��
	static Bitmap threeBitmap;//���Ͻǵ�������ť
	static Bitmap panel_back;//����ͼ
	static Bitmap selectBackground;//ѡ�к�ı���
	static Bitmap buttonBackGround;//��ť����
	static Bitmap upBitmap;//���ϵ�С��ͷ
	static Bitmap downBitmap;//���µ�С��ͷ
	static Bitmap addBitmap;//С�Ӻ�
	static Bitmap cutBitmap;//С����
	static Bitmap logo;
	
	Paint paint;
	
	public CityManageView(){}
	
	public CityManageView(GameView gameView) {//������
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
		addBitmap = Bitmap.createBitmap(menu_item, 225, 15, 15, 15);
		cutBitmap = Bitmap.createBitmap(menu_item, 225, 0, 15, 15);
		menu_item = null;//�ͷŵ���ͼ
		panel_back = BitmapFactory.decodeResource(r, R.drawable.panel_back);
		logo = BitmapFactory.decodeResource(r, R.drawable.logo);
		selectBackground = BitmapFactory.decodeResource(r, R.drawable.select_back);
		menuTitle = BitmapFactory.decodeResource(r, R.drawable.menu_title);
	}
	
	public void initData(){//��ʼ������
		cityList = gameView.hero.getCityList();//�õ�Ӣ����ӵ�е����гǳ�
		items1 = new String[cityList.size()][4];
		//��ʼ����Ҫ��ʾ�ĳǳ���Ϣ
		for(int i=0; i<cityList.size(); i++){
			items1[i][0] = cityList.get(i).getCityName();//�ǳ�����
			items1[i][1] = cityList.get(i).getGuardGeneral().size()+"��";//�佫�ĸ���
			items1[i][2] = cityList.get(i).getArmy()+"��";//����
			items1[i][3] = cityList.get(i).getFood()+"ʯ";//����
		}
	}

	public void onDraw(Canvas canvas){//���Ƶķ��� 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//����logo
		paint.setTextSize(18);//�������ִ�С

		if(status == 0){//������ʱ
			paint.setTextSize(23);//�������ִ�С
			canvas.drawText("�ǳع���", 50, 40, paint);
			paint.setTextSize(18);//�������ִ�С
			canvas.drawBitmap(menuTitle, 10, 70, paint);
			canvas.drawText("����", 15, 90, paint);
			canvas.drawText("�������", 75, 90, paint);
			canvas.drawText("����", 160, 90, paint);
			canvas.drawText("����", 230, 90, paint);	
			
			int tempCurrentI = currentI;
			if(items1.length < yeSpan1){//һ����Ļ������ʾ��
				for(int i=0; i<items1.length; i++){
					canvas.drawText(items1[i][0], 15, startY + 5 + 30*i, paint);
					canvas.drawText(items1[i][1], 75, startY + 5 + 30*i, paint);
					canvas.drawText(items1[i][2], 160, startY + 5 + 30*i, paint);
					canvas.drawText(items1[i][3], 230, startY + 5 + 30*i, paint);
				}
			}
			else{//һ����Ļ��ʾ����ʱ
				for(int i=tempCurrentI; i<tempCurrentI+yeSpan1; i++){
					canvas.drawText(items1[i][0], 15, startY + 5+ 30*(i-tempCurrentI), paint);
					canvas.drawText(items1[i][1], 75, startY + 5+ 30*(i-tempCurrentI), paint);
					canvas.drawText(items1[i][2], 160, startY + 5+ 30*(i-tempCurrentI), paint);
					canvas.drawText(items1[i][3], 230, startY + 5+ 30*(i-tempCurrentI), paint);
				}
			}
			canvas.drawBitmap(selectBackground, 10, startY + 5 + 30*selectI - 22, paint);//����ѡ��Ч��
			if(currentI != 0){//����С�����ϼ�ͷ
				canvas.drawBitmap(upBitmap, 150, 80, paint);
			}
			if(items1.length>yeSpan1 && (currentI+yeSpan1) < items1.length){//����С�����¼�ͷ
				canvas.drawBitmap(downBitmap, 150, 414, paint);
			}
			
			canvas.drawBitmap(buttonBackGround, 130, 430, paint);//������ϸ��ť����
			canvas.drawText("��ϸ", 142, 452, paint);
		}
		else if(status == 1){//��ϸ��Ϣ���� 
			paint.setTextSize(23);//�������ִ�С
			canvas.drawText("�ǳ���ϸ��Ϣ", 50, 40, paint);
			paint.setTextSize(18);//�������ִ�С
			
			for(int i=0; i<items2.length; i++){
				canvas.drawText((String) items2[i][0], 15, startY-35+30*i, paint);
				canvas.drawText((String) items2[i][1], 65, startY-35+30*i, paint);
				canvas.drawText((String) items2[i][2], 135, startY-35+30*i, paint);
				canvas.drawText((String) items2[i][3], 190, startY-35+30*i, paint);
				if(i==2 || i==3 || i==7 || i==8){
					canvas.drawBitmap(addBitmap, 250, startY-35+30*i-15, paint);
					canvas.drawBitmap(cutBitmap, 270, startY-35+30*i-15, paint);
				}
			}
			canvas.drawBitmap(selectBackground, 10, startY - 35 + 30*selectI - 22, paint);//����ѡ��Ч��
		}
	}

	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){//��Ļ������
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(x>274 && x<304 && y>15 && y<45){//����˹رհ�ť
				if(status == 1){
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					this.initData();
					return true;
				}
				gameView.setStatus(0);
			}
			if(x>212 && x<242 && y>15 && y<45){//��������·�ҳ��ť
				if(status == 0){//������
					selectI++;
					if(items1.length < yeSpan1){//��һ����Ļ����ȫ����ʾʱ��������Ҫ���� 
						if(selectI > items1.length-1){ 
							selectI = items1.length-1;
						}
					}
					else {//��һ����ʾ��ȫ����Ҫ����ʱ
						if(selectI > yeSpan1-1){ 
							selectI = yeSpan1-1;
							currentI++;
							if((currentI+yeSpan1) > items1.length){
								currentI--;
							}
						}						
					}						
				}
				else if(status == 1){//��ϸ��Ϣ
					selectI++;
					if(selectI > items2.length-1){ 
						selectI = items2.length-1;
					}
				}
			}//end��������·�ҳ��ť
			else if(x>243 && x<273 && y>15 && y<45){//��������Ϸ�ҳ��ť
				selectI--;
				if(selectI < 0){
					selectI = 0;
					currentI--;
					if(currentI < 0){
						currentI = 0;
					}
				}	
			}
			
			if(status == 0){//������
				if(x>130 && x<190 && y>430 && y<460){//��ϸ��ť
					if(cityList.size() == 0){
						return true;
					}
					selectCityDrawable = cityList.get((selectI+currentI));//�õ�ѡ�еĳǳ�
					items2 = new String[][]{
						{"���ƣ�", selectCityDrawable.getCityName(),"",""},
						{"�ȼ���", selectCityDrawable.getLevel()+"","",""},
						{"������", selectCityDrawable.getArmy()+"","ʣ�ࣺ",gameView.hero.getArmyWithMe()+""},
						{"���ݣ�", selectCityDrawable.getFood()+"", "ʣ�ࣺ",gameView.hero.getFood()+""},
						{"������", GameFormula.getCityDefence(selectCityDrawable)+"","",""},
						{"������", GameFormula.getCityAttack(selectCityDrawable)+"","",""},
						{"����", selectCityDrawable.getCitizen()+"","","","",""},
						{"���⣺", selectCityDrawable.getWarTower()+"","ʣ�ࣺ",gameView.hero.warTower+""},
						{"ս����", selectCityDrawable.getWarTank()+"","ʣ�ࣺ",gameView.hero.warTank+""	}
					};
//					selectI = 0;
//					currentI = 0;
					status = 1;
				}
			}
			else if(status == 1){//��ϸ����
				if(x>250 && x<265 && y>130 && y<145){//�ӱ���
					if(gameView.hero.getArmyWithMe()>=500){
						gameView.hero.setArmyWithMe(gameView.hero.getArmyWithMe()-500);
						this.selectCityDrawable.setArmy(this.selectCityDrawable.getArmy()+500);
					}
					else {
						this.selectCityDrawable.setArmy(this.selectCityDrawable.getArmy()+gameView.hero.getArmyWithMe());
						gameView.hero.setArmyWithMe(0);
					}
					initItems2();
				}
				else if(x>270 && x<285 && y>130 && y<145){//������
					if(this.selectCityDrawable.getArmy()>=500){
						gameView.hero.setArmyWithMe(gameView.hero.getArmyWithMe()+500);
						this.selectCityDrawable.setArmy(this.selectCityDrawable.getArmy()-500);
					}
					else {
						gameView.hero.setArmyWithMe(gameView.hero.getArmyWithMe()+this.selectCityDrawable.getArmy());
						this.selectCityDrawable.setArmy(0);
					}
					initItems2();
				}
				else if(x>250 && x<265 && y>160 && y<175){//������
					if(gameView.hero.getFood()>=500){
						this.selectCityDrawable.setFood(this.selectCityDrawable.getFood()+500);
						gameView.hero.setFood(gameView.hero.getFood()-500);
						
					}
					else{
						this.selectCityDrawable.setFood(this.selectCityDrawable.getFood()+gameView.hero.getFood());
						gameView.hero.setFood(0);
					}
					initItems2();
				}
				else if(x>270 && x<285 && y>160 && y<175){//������
					if(this.selectCityDrawable.getFood()>=500){
						gameView.hero.setFood(gameView.hero.getFood()+500);
						this.selectCityDrawable.setFood(this.selectCityDrawable.getFood()-500);
					}
					else {
						gameView.hero.setFood(gameView.hero.getFood()+this.selectCityDrawable.getFood());
						this.selectCityDrawable.setFood(0);
					}
					initItems2();
				}	
				else if(x>250 && x<265 && y>280 && y<295){//�Ӽ���
					if(gameView.hero.getWarTower()>0){
						this.selectCityDrawable.setWarTower(this.selectCityDrawable.getWarTower()+1);
						gameView.hero.setWarTower(gameView.hero.getWarTower()-1);
					}
					initItems2();
				}
				else if(x>270 && x<285 && y>280 && y<295){//������
					if(this.selectCityDrawable.getWarTower()>0){
						gameView.hero.setWarTower(gameView.hero.getWarTower()+1);
						this.selectCityDrawable.setWarTower(this.selectCityDrawable.getWarTower()-1);
					}
					initItems2();
				}
				else if(x>250 && x<265 && y>310 && y<325){//��ս��
					if(gameView.hero.getWarTank()>0){
						this.selectCityDrawable.setWarTank(this.selectCityDrawable.getWarTank()+1);
						gameView.hero.setWarTank(gameView.hero.getWarTank()-1);
					}
					initItems2();
				}
				else if(x>270 && x<285 && y>310 && y<325){//��ս��
					if(this.selectCityDrawable.getWarTank()>0){
						gameView.hero.setWarTank(gameView.hero.getWarTank()+1);
						this.selectCityDrawable.setWarTank(this.selectCityDrawable.getWarTank()-1);
					}
					initItems2();
				}				
			}
		}
		return true;
	}
	
	public void initItems2(){
		selectCityDrawable = cityList.get((selectI+currentI));//�õ�ѡ�еĳǳ�
		items2 = new String[][]{
			{"���ƣ�", selectCityDrawable.getCityName(),"",""},
			{"�ȼ���", selectCityDrawable.getLevel()+"","",""},
			{"������", selectCityDrawable.getArmy()+"","ʣ�ࣺ",gameView.hero.getArmyWithMe()+""},
			{"���ݣ�", selectCityDrawable.getFood()+"", "ʣ�ࣺ",gameView.hero.getFood()+""},
			{"������", GameFormula.getCityDefence(selectCityDrawable)+"","",""},
			{"������", GameFormula.getCityAttack(selectCityDrawable)+"","",""},
			{"����", selectCityDrawable.getCitizen()+"","","","",""},
			{"���⣺", selectCityDrawable.getWarTower()+"","ʣ�ࣺ",gameView.hero.warTower+""},
			{"ս����", selectCityDrawable.getWarTank()+"","ʣ�ࣺ",gameView.hero.warTank+""	}
		};
	}
}
