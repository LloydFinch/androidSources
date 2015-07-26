package wyf.ytl;

import static wyf.ytl.ConstantUtil.GENERAL_TITLE;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import static wyf.ytl.ConstantUtil.*;

/**
 * 
 * �佫�鱨 ����
 *
 */

public class WuJiangView{
	GameView gameView;
	
	int yeSpan1 = 9;//��ͨ�佫������ʾ�ĸ���
	int yeSpan2 = 9;//�߼��佫����
	int yeSpan3 = 12;//��ϸ��Ϣ����
	int yeSpan4 = 7;//�������
	int yeSpan5 = 7;//�������
	int currentI = 0;//��ǰ���Ƶ���Ļ���ϱߵ�Ԫ���±�
	int selectI = 0;
	
	int startY = 120;
	int startX = 15;
	
	int status = 0;//��ʾ�ı��λ��0-��ͨ�佫�б�  1-�߼��佫�б� 2-�佫��ϸ��Ϣ 3-���� 4-���� 5-ָ��
	General selectGeneral;//��ǰѡ�е��佫
	ArrayList<General> totalGeneral;
	String[][] items1;//��Ҫ�г����佫
	String[][] items2;//��Ҫ�г��������佫 
	String[][] items3;//ѡ���佫����ϸ��Ϣ
	String[] items4 = GENERAL_TITLE;//�佫�����������ְλ
	String[][] items5;//�����е���Ŀ,���ӣ�ī�ӵ�
	String[] items6;//ָ�ɽ���
	
	static Bitmap menuTitle;//���ı��ⱳ��
	static Bitmap threeBitmap;//���Ͻǵ�������ť
	static Bitmap panel_back;//����ͼ
	static Bitmap selectBackground;//ѡ�к�ı���
	static Bitmap buttonBackGround;//��ť����
	static Bitmap upBitmap;//���ϵ�С��ͷ
	static Bitmap downBitmap;//���µ�С��ͷ
	static Bitmap logo;
	
	Paint paint;
	
	public WuJiangView(GameView gameView) {//������
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
		totalGeneral = gameView.hero.getTotalGeneral();//�õ�Ӣ����ӵ�е����н���
		
		items1 = new String[totalGeneral.size()][4];
		for(int i=0; i<totalGeneral.size(); i++){
			items1[i][0] = totalGeneral.get(i).getName();//����
			items1[i][1] = totalGeneral.get(i).getRank();//ְλ
			items1[i][2] = (totalGeneral.get(i).getCityDrawable()==null)?"����":totalGeneral.get(i).getCityDrawable().getCityName();//״̬
			items1[i][3] = totalGeneral.get(i).getStrength()+"";//����
		}
	}
	
	public void initZhiPai(){//��ʼ��ָ�ɽ���
		 ArrayList<CityDrawable> cityList = gameView.hero.getCityList();
		 items6 = new String[cityList.size()];
		 for(int i=0; i< cityList.size(); i++){
			 items6[i] = cityList.get(i).getCityName();
		 }
	}
	
	public void onDraw(Canvas canvas){//���Ƶķ��� 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//����logo
		paint.setTextSize(23);//�������ִ�С
		canvas.drawText("�佫�鱨", 50, 40, paint);
		paint.setTextSize(18);//�������ִ�С
		
		if(status == 0 || status == 1){//��View�е������� 
			canvas.drawBitmap(buttonBackGround, 15, 57, paint);
			canvas.drawBitmap(buttonBackGround, 85, 57, paint);
			canvas.drawText("��ͨ", 27, 78, paint);
			canvas.drawText("�߼�", 97, 78, paint);
			
			paint.setTextSize(18);//�������ִ�С
			if(status == 0){//��ͨ�佫����
				canvas.drawBitmap(menuTitle, 10, 100, paint);
				canvas.drawText("����", 15, 120, paint);
				canvas.drawText("ְλ", 90, 120, paint);
				canvas.drawText("״̬", 170, 120, paint);
				canvas.drawText("����", 240, 120, paint);
				
				int tempCurrentI = currentI;
				if(items1.length < yeSpan1){//һ����Ļ������ʾ��
					for(int i=0; i<items1.length; i++){
						canvas.drawText(items1[i][0], 15, startY + 35 + 30*i, paint);
						canvas.drawText(items1[i][1], 90, startY + 35 + 30*i, paint);
						canvas.drawText(items1[i][2], 170, startY + 35 + 30*i, paint);
						canvas.drawText(items1[i][3], 240, startY + 35 + 30*i, paint);
					}
				}
				else{//һ����Ļ��ʾ����ʱ
					for(int i=tempCurrentI; i<tempCurrentI+yeSpan1; i++){
						canvas.drawText(items1[i][0], 15, startY + 35 + 30*(i-tempCurrentI), paint);
						canvas.drawText(items1[i][1], 90, startY + 35 + 30*(i-tempCurrentI), paint);
						canvas.drawText(items1[i][2], 170, startY + 35 + 30*(i-tempCurrentI), paint);
						canvas.drawText(items1[i][3], 140, startY + 35 + 30*(i-tempCurrentI), paint);
					}
				}
				canvas.drawBitmap(selectBackground, 10, startY +35 + 30*selectI - 22, paint);//����ѡ��Ч��
				
				for(int i=0; i<4; i++){//���������ĸ���ť����
					canvas.drawBitmap(buttonBackGround, 25+70*i, 430, paint);
				}
				canvas.drawText("��ϸ", 35, 452, paint);//���������ĸ���ť������
				canvas.drawText("����", 105, 452, paint);
				canvas.drawText("����", 175, 452, paint);
				canvas.drawText("ָ��", 245, 452, paint);	
				
				if(currentI != 0){//����С�����ϼ�ͷ
					canvas.drawBitmap(upBitmap, 150, 80, paint);
				}
				if(items1.length>yeSpan1 && (currentI+yeSpan1) < items1.length){//����С�����¼�ͷ
					canvas.drawBitmap(downBitmap, 150, 414, paint);
				}
			}
			else if(status == 1){//�߼�����
				canvas.drawBitmap(menuTitle, 10, 100, paint);
				canvas.drawText("����", 15, 120, paint);
				canvas.drawText("������Ŀ", 65, 120, paint);
				canvas.drawText("�������", 150, 120, paint);
				canvas.drawText("�ܹ�����", 235, 120, paint);
				
				int tempCurrentI = currentI;
				if(items5.length < yeSpan2){//��һ����Ļ������ʾȫʱ
					for(int i=0; i<items5.length; i++){
						canvas.drawText(items5[i][0], 15, startY + 35 + 30*i, paint);
						canvas.drawText(items5[i][1], 65, startY + 35 + 30*i, paint);
						canvas.drawText(items5[i][2], 150, startY + 35 + 30*i, paint);
						canvas.drawText(items5[i][3], 235, startY + 35 + 30*i, paint);
					}
				} 
				else{
					for(int i=tempCurrentI; i<tempCurrentI+yeSpan2; i++){
						canvas.drawText(items5[i][0], 15, startY + 35 + 30*(i-tempCurrentI), paint);
						canvas.drawText(items5[i][1], 65, startY + 35 + 30*(i-tempCurrentI), paint);
						canvas.drawText(items5[i][2], 150, startY + 35 + 30*(i-tempCurrentI), paint);
						canvas.drawText(items5[i][3], 235, startY + 35 + 30*(i-tempCurrentI), paint);
					}			
				}
				canvas.drawBitmap(selectBackground, 10, startY + 35 + 10*selectI - 22, paint);
				
				if(currentI != 0){//����С�����ϼ�ͷ
					canvas.drawBitmap(upBitmap, 150, 80, paint);
				}
				if(items5.length>yeSpan2 && (currentI+yeSpan2) < items5.length){//����С�����¼�ͷ
					canvas.drawBitmap(downBitmap, 150, 414, paint);
				}
			}
		}
		else if(status == 2){//�佫��ϸ��Ϣ
			int tempCurrentI = currentI;
			if(items3.length < yeSpan3){//��һ����Ļ������ʾȫʱ
				for(int i=0; i<items3.length; i++){
					canvas.drawText(items3[i][0], 15, startY - 25 + 30*i, paint);
					canvas.drawText(items3[i][1], 110, startY - 25 + 30*i, paint);	
				}
			} 
			else{
				for(int i=tempCurrentI; i<tempCurrentI+yeSpan3; i++){
					canvas.drawText(items3[i][0], 15, startY - 25 + 30*(i-tempCurrentI), paint);
					canvas.drawText(items3[i][1], 110, startY - 25 + 30*(i-tempCurrentI), paint);
				}			
			}
			canvas.drawBitmap(selectBackground, 10, startY - 25 + 30*selectI - 22, paint);
			
			if(currentI != 0){//����С�����ϼ�ͷ
				canvas.drawBitmap(upBitmap, 150, 80, paint);
			}
			if(items3.length>yeSpan3 && (currentI+yeSpan3) < items3.length){//����С�����¼�ͷ
				canvas.drawBitmap(downBitmap, 150, 414, paint);
			}
		}
		else if(status == 3){//����ʱ
			canvas.drawText("����" + this.selectGeneral.getName()+"���н�̸��" + "�����ܸ�", 45, 130, paint);
			canvas.drawText("�����ҳ϶Ȼָ���100��", 25, 160, paint);
			this.selectGeneral.setLoyalty(100);//�����ҳ϶�
			canvas.drawBitmap(buttonBackGround, 235, 185, paint);//����ȷ����ť����
			canvas.drawText("ȷ��", 247, 207, paint);
		}
		else if(status == 4){//����
			canvas.drawText(this.selectGeneral.getName()+"�ĵ�ǰְλΪ"+this.selectGeneral.getRank()+"������", 35, 85, paint);
			canvas.drawText("Ҫ�佫ְλ�ı�Ϊ��", 15, 115, paint);
			
			int tempCurrentI = currentI;
			if(items4.length < yeSpan1){//һ����Ļ������ʾ��
				for(int i=0; i<items4.length; i++){
					canvas.drawText(items4[i], 120, startY + 35 + 30*i, paint);
				}
			}
			else{//һ����Ļ��ʾ����ʱ
				for(int i=tempCurrentI; i<tempCurrentI+yeSpan1; i++){
						canvas.drawText(items4[i], 120, startY + 35 + 30*(i-tempCurrentI), paint);
				}
			}
			
			canvas.drawBitmap(buttonBackGround, 128, 353, paint);//����ȷ����ť����
			canvas.drawText("ȷ��", 140, 375, paint);
			
			canvas.drawBitmap(selectBackground, 10, startY + 35 + 30*selectI - 22, paint);//����ѡ��Ч��
			if(currentI != 0){//����С�����ϼ�ͷ
				canvas.drawBitmap(upBitmap, 150, 80, paint);
			}
			if(items4.length>yeSpan4 && (currentI+yeSpan4) < items4.length){//����С�����¼�ͷ
				canvas.drawBitmap(downBitmap, 150, 414, paint);
			}
		}
		else if(status == 5){//����ָ�ɽ���
			if(this.selectGeneral.cityDrawable == null){
				canvas.drawText(this.selectGeneral.getName()+"�ĵ�ǰ�ڸ�����������", 35, 85, paint);
				canvas.drawText("Ҫ��ָ�ɵ���", 15, 115, paint);
			}
			else {
				canvas.drawText(this.selectGeneral.getName()+"�ĵ�ǰ��"+this.selectGeneral.cityDrawable.getCityName()+"������", 35, 85, paint);
				canvas.drawText("Ҫ��ָ�ɵ���", 15, 115, paint);				
			}

			int tempCurrentI = currentI;
			if(items6.length < yeSpan5){//һ����Ļ������ʾ��
				for(int i=0; i<items6.length; i++){
					canvas.drawText(items6[i], 120, startY + 35 + 30*i, paint);
				}
			}
			else{//һ����Ļ��ʾ����ʱ
				for(int i=tempCurrentI; i<tempCurrentI+yeSpan1; i++){
					canvas.drawText(items6[i], 120, startY + 35 + 30*(i-tempCurrentI), paint);
				}
			}
			
			canvas.drawBitmap(buttonBackGround, 128, 403, paint);//����ȷ����ť����
			canvas.drawText("ȷ��", 140, 425, paint);
			
			canvas.drawBitmap(selectBackground, 10, startY + 35 + 30*selectI - 22, paint);//����ѡ��Ч��
			if(currentI != 0){//����С�����ϼ�ͷ
				canvas.drawBitmap(upBitmap, 150, 80, paint);
			}
			if(items6.length>yeSpan5 && (currentI+yeSpan5) < items6.length){//����С�����¼�ͷ
				canvas.drawBitmap(downBitmap, 150, 414, paint);
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//��Ļ������
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(status == 0){//��ͨ����
				if(x>35 && x<95 && y>430 && y<460){//��ϸ
					if(totalGeneral.size() == 0){
						return true;
					}
					selectGeneral=  totalGeneral.get((currentI+selectI));//�õ�ѡ�е��佫
					this.items3 = new String[][]
					     {
							{"���ƣ�", selectGeneral.getName()},
							{"ְλ��", selectGeneral.getRank()},
							{"�ȼ���", selectGeneral.getLevel()+""},
							{"�ҳ϶ȣ�",selectGeneral.getLoyalty()+""},
							{"ͳ������",selectGeneral.getDefend()+""},
							{"������", selectGeneral.getPower()+""},
							{"������", selectGeneral.getIntelligence()+""},
							{"���ݣ�", selectGeneral.getAgility()+""},
							{"������", selectGeneral.getStrength()+""},
							{"�������ޣ�",selectGeneral.getMaxStrength()+""}
					     };
					
					this.status = 2;
					this.selectI = 0;
					this.currentI = 0;
				}
				else if(x>105 && x<165 && y>430 && y<460){//����
					if(totalGeneral.size() == 0){
						return true;
					}
					selectGeneral = totalGeneral.get((currentI+selectI));//�õ�ѡ�е��佫
					this.status = 4;
					this.selectI = 0;
					this.currentI = 0;
				}
				else if(x>175 && x<235 && y>430 && y<460){//����
					if(totalGeneral.size() == 0){
						return true;
					}
					selectGeneral = totalGeneral.get((currentI+selectI));//�õ�ѡ�е��佫
					this.status = 3;
					this.selectI = 0;
					this.currentI = 0;
				}
				else if(x>245 && x<305 && y>430 && y<460){//ָ��
					if(totalGeneral.size() == 0){
						return true;
					}
					selectGeneral = totalGeneral.get((currentI+selectI));//�õ�ѡ�е��佫
					initZhiPai();
					this.status = 5;
					this.selectI = 0;
					this.currentI = 0;
				}
			}//end��ͨ����
			else if(status == 1){//�߼�����

			}//end�߼�����
			else if(status == 5){//ָ�ɽ���
				if(x>128 && x<188 && y>403 && y<433){//ָ�ɽ�����ȷ����ť
					CityDrawable cd = gameView.hero.cityList.get((currentI+selectI));
					if(selectGeneral.cityDrawable != null){
						selectGeneral.cityDrawable.getGuardGeneral().remove(selectGeneral);
					}
					selectGeneral.cityDrawable = cd;
					initData();
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
				}
			}
			else if(status == 3){//�������
				if(x>235 && x<295 && y>185 && y<215){//ȷ����ť
					this.status = 0;
					initData();
				}
			}
			else if(status == 4){//�������
				if(x>128 && x<188 && y>353 && y<383){//ȷ����ť
					this.selectGeneral.setRank((selectI+currentI));
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					initData();
				}
			}
			
			if(x>20 && x<70 && y>60 && y<80){//�����ͨ��ť
				if(status == 1){//�ڸ߼�������ͨ��ť�ſ���
				 	this.status = 0;
				 	this.selectI = 0;
					this.currentI = 0;					
				}
			}
			else if(x>85 && x<140 && y>60 && y<80){//����߼���ť
				if(status == 0){//����ͨ����
					this.status = 1;
					this.selectI = 0;
					this.currentI = 0;	
					
					items5 = new String [gameView.hero.getResearchList().size()][4];
					for(int i=0; i<gameView.hero.getResearchList().size(); i++){
						items5[i][0] = gameView.hero.getResearchList().get(i).getName();
						items5[i][1] = RESEARCH_PROJECT[gameView.hero.getResearchList().get(i).getResearchProject()];
						items5[i][2] = gameView.hero.getResearchList().get(i).getProgress()+"";
						items5[i][3] = gameView.hero.getResearchList().get(i).getResearchNumber()+"";
					}
				}
			}
			
			if(x>212 && x<242 && y>15 && y<45){//��������·�ҳ��ť
				if(status == 0){//��ͨ�佫ʱ 
					if(items1 == null || items1.length == 0){
						return true;
					}
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
				else if(status == 1){//�߼���ʾʱ
					if(items2 == null || items2.length == 0){
						return true;
					}
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
				else if(status == 2){//��ϸ��Ϣ
					selectI++;
					if(items3.length < yeSpan3){//��һ����Ļ����ȫ����ʾʱ��������Ҫ���� 
						if(selectI > items3.length-1){ 
							selectI = items3.length-1;
						}
					}
					else {//��һ����ʾ��ȫ����Ҫ����ʱ
						if(selectI > yeSpan3-1){ 
							selectI = yeSpan3-1;
							currentI++;
							if((currentI+yeSpan3) > items3.length){
								currentI--;
							}
						}						
					}
				}
				else if(status == 4){//����ʱ
					selectI++;
					if(items4.length < yeSpan4){//��һ����Ļ����ȫ����ʾʱ��������Ҫ���� 
						if(selectI > items4.length-1){ 
							selectI = items4.length-1;
						}
					}
					else {//��һ����ʾ��ȫ����Ҫ����ʱ
						if(selectI > yeSpan4-1){ 
							selectI = yeSpan4-1;
							currentI++;
							if((currentI+yeSpan4) > items4.length){
								currentI--;
							}
						}						
					}
				}
			}
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
			else if(x>274 && x<304 && y>15 && y<45){//����˹رհ�ť
				if(this.status == 2){//����ϸ����ʱӦ�û��佫�б�
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					return true;
				}
				else if(status == 3){//���ڻ���ʱҲ���ص��佫�б�
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					return true;
				}
				else if(status == 4){//��������ҳ��ʱ
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					return true;
				}
				else if(status == 1){//�ڸ߼�����
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					return true;
				}
				else if(status == 5){//��ָ�ɽ���
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					return true;
				}
				gameView.setStatus(0);
			}
		}
		return true;
	}
}