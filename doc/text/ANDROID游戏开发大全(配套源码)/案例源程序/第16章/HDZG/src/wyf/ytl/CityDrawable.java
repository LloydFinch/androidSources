package wyf.ytl;

import static wyf.ytl.ConstantUtil.CITY_INFO;
import static wyf.ytl.ConstantUtil.COUNTRY_NAME;
import static wyf.ytl.ConstantUtil.DIALOG_BTN_HEIGHT;
import static wyf.ytl.ConstantUtil.DIALOG_BTN_SPAN;
import static wyf.ytl.ConstantUtil.DIALOG_BTN_START_X;
import static wyf.ytl.ConstantUtil.DIALOG_BTN_START_Y;
import static wyf.ytl.ConstantUtil.DIALOG_BTN_WIDTH;
import static wyf.ytl.ConstantUtil.DIALOG_BTN_WORD_LEFT;
import static wyf.ytl.ConstantUtil.DIALOG_BTN_WORD_UP;
import static wyf.ytl.ConstantUtil.DIALOG_START_Y;
import static wyf.ytl.ConstantUtil.DIALOG_WORD_SIZE;
import static wyf.ytl.GameFormula.getCityAttack;
import static wyf.ytl.GameFormula.getCityDefence;
import static wyf.ytl.GameFormula.getHeroAttack;
import static wyf.ytl.GameFormula.getHeroDefence;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
/*
 * �������ǳ��࣬��װ���йسǳص���Ϣ����ǳ����ơ��������ҡ����������ݡ��سǽ���
 * �����������ͻ������������ȼ��������˿ڵȵȣ���������ս���ĸ����ͼ��⣬�Լ����ڶ�
 */
public class CityDrawable extends MyMeetableDrawable implements Externalizable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5592838008100279432L;
	String cityName;//�ǳ�����
	int country;//��������
	int army;//����
	int food;//����
	int level;//�ȼ�
	ArrayList<General> guardGeneral = new ArrayList<General>();//�佫�б�
	int baseAttack = 20;//ÿ��ʿ���Ļ���������
	int baseDefend = 15;//ÿ��ʿ���Ļ���������
	int citizen;//����
	int warTank = 0;//ս���ĸ������������ӹ�����
	int warTower = 0;//����ĸ����������ӷ�����
	
	int tax = 5;//����Ҫ����˰
	
	int info;
	
	String []dialogMessage = {
			"����aa�ĳ���bb������cc������dd���ؽ�ee��Ԥ��ͨ��˰Ϊff����ѡ��",
			"ս��xx������ս���м������yy�ˣ���ʧ��ʿzz�ˡ�",
			"��ָ������������������һ�ٹ����˶Է��ĳǳأ������������������ˣ��ؽ�ҲͶ���ˣ��������㾡�ң������ػ������ǡ�",
			"�����������̵�ʿ����û�ˣ���������ʵʵ��˰���ǰɡ�",
			"���������Լ��ĳǳأ���ӭ���ģ�"
	};
	int status= 0;//Ϊ0����ѯ�ʣ�Ϊ1��ʾս�����,2��ʾ���˳ǳأ�3��ʾû���ɴ����ˣ�4��ʾ�����Լ��ĳǳأ�5��ʾonTouch������Ϊ����ѡ����
	private int heroLost;//Ӣ����ս���е���ʧ
	private int cityLost;//�ǳ���ս���е���ʧ
	int result = -1;//ս�������0��ʾ�䣬1��ʾӮ,
	

	public CityDrawable(){}
	
	public CityDrawable
			(
				Bitmap bmpSelf,Bitmap bmpDialogBack,Bitmap bmpDialogButton,boolean meetable,
				int width,int height,int col,int row,
				int refCol,int refRow,int [][] noThrough,
				int [][] meetableMatrix
			){
		super(bmpSelf, col, row, width, height, refCol, refRow, noThrough, meetable,
				meetableMatrix, bmpDialogBack, bmpDialogButton);
	}
	public void addCityInfo(CityInfo cityInfo){//���ó��е���Ϣ
		this.cityName = cityInfo.cityName;
		this.country = cityInfo.country;
		this.army = cityInfo.army;
		this.food = cityInfo.food;
		this.level = cityInfo.level;
		this.baseAttack = cityInfo.baseAttack;
		this.baseDefend = cityInfo.baseDefend;
		this.citizen = cityInfo.citizen;
		this.warTank = cityInfo.warTank;
		this.warTower = cityInfo.warTower;
		this.guardGeneral = cityInfo.guardGeneral;
		this.info = cityInfo.info;
	}
	
	public void setBackToInit(){//�ָ���Ĭ��		
		for(int i=0; i<CITY_INFO.size(); i++){
			CityInfo ci = CITY_INFO.get(i);
			if(ci.info == this.info){
				this.cityName = ci.cityName;
				this.country = ci.country;
				this.army = ci.army;
				this.food = ci.food;
				this.level = ci.level;
				this.baseAttack = ci.baseAttack;
				this.baseDefend = ci.baseDefend;
				this.citizen = ci.citizen;
				this.warTank = ci.warTank;
				this.warTower = ci.warTower;
				this.guardGeneral = ci.guardGeneral;
				break;
			}
		}
	}
	
	//���ƶԻ��򷽷�
	public void drawDialog(Canvas canvas, Hero hero) {
		String showString = null;//��Ҫ��ʾ���Ի����е��ַ���
		tempHero = hero;
		//�Ȼ�����
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		if(tempHero.cityList.contains(this) && status!=2){//��������Լ��ĳǳ�
			status = 4;
		}
		if(status == 0){//ѯ��ʱ��˰����ս��
			showString = dialogMessage[status];
			showString = showString.replaceAll("aa", COUNTRY_NAME[country]);//�滻�����ַ���
			showString = showString.replaceFirst("bb", cityName);//�滻�����������ַ���
			showString = showString.replaceFirst("cc", army+"");//�滻�����������ַ���
			showString = showString.replaceFirst("dd", food+"");//�滻�����������ַ���
			showString = showString.replaceFirst("ee", getGeneralsName());//�滻�����������ַ���
			showString = showString.replaceFirst("ff", calculateTax()+"");//�滻����˰��Ŀ�ַ���	
			
			drawString(canvas, showString);
			//����ťȷ����ť
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
			Paint paint = new Paint();
			paint.setARGB(255, 42, 48, 103);
			paint.setAntiAlias(true);
			paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
			paint.setTextSize(18);
			canvas.drawText("��˰",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);
			//��ȡ����ť
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
			canvas.drawText("����", 
					DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WORD_LEFT, 
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);				
		} 
		else if(status == 1){//��ʾս�����
			showString = dialogMessage[status];
			showString = showString.replaceFirst("xx", result==0?"ʧ��":"ʤ��");//�滻��ս�����
			showString = showString.replaceFirst("yy", cityLost+"");//�滻���ߵ�����
			showString = showString.replaceFirst("zz", heroLost+"");//�滻����ʧ����
			drawString(canvas, showString);//����ս�����
			//����ťȷ����ť
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
			Paint paint = new Paint();
			paint.setARGB(255, 42, 48, 103);
			paint.setAntiAlias(true);
			paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
			paint.setTextSize(18);
			canvas.drawText("ȷ��",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);			
		}
		else if(status == 2 || status == 3 || status ==4){//����һ����,����û���ɴ���,�����������Լ��ĳǳ�
			showString = dialogMessage[status];
			drawString(canvas, showString);//����ս�����
			//����ťȷ����ť
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
			Paint paint = new Paint();
			paint.setARGB(255, 42, 48, 103);
			paint.setAntiAlias(true);
			paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
			paint.setTextSize(18);
			canvas.drawText("ȷ��",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);			
		}	
	}
	//��ý��������
	private String getGeneralsName() {
		String s = "";
		for(General g:guardGeneral){
			s+=g.getName()+"��";
		}
		return s.substring(0, s.length()-1);
	}
	//�����������˰
	public int calculateTax(){
		int defence = GameFormula.getCityDefence(this);
		return defence/200;
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(status == 0){//�����ѯ��״̬ʱ
				if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
						&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ������
					tempHero.setTotalMoney(tempHero.getTotalMoney()-calculateTax());
					if(tempHero.getTotalMoney() <= 0){//�Ʋ���
						tempHero.setTotalMoney(0);
						
						GameView gv = tempHero.father;
						GameOverAlert goa = new GameOverAlert(gv,0,GameView.dialogBack,GameView.dialogButton);
						recoverGame();
						gv.currentGameAlert = goa;
					}
					else{
						recoverGame();
					}
				}
				else if(x>DIALOG_BTN_START_X+DIALOG_BTN_SPAN && x<DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WIDTH
						&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ��ǹ��Ǽ�
					if(tempHero.armyWithMe <=100){//Ӣ����ͷ�ϵı�̫��
						status = 3;
					}
					else{
						tempHero.father.selectGeneral.initData();
						tempHero.father.setStatus(3);//����״̬Ϊѡ�񽫾���ս
						status = 5;//״̬Ϊ5��ʾ������Ϊ����ѡ����		
					}					
				}							
			}
			else if(status ==1 || status == 2 || status == 4){//���������ʾս�������״̬���ޱ��ɴ�\�����Լ���״̬���°���ȷ��
				if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
						&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//���µ���ȷ����
					recoverGame();//�ָ���Ϸ״̬
				}
			}		
			else if(status == 3){//������ޱ��ɴ��״̬���е�״̬0
				status = 0;
			}
			else if(status == 5){//����ǽ���ѡ�����
				tempHero.father.selectGeneral.onTouchEvent(event);
			}
		}
		return true;
	}
	//�������������������ظ���Ϸ״̬
	public void recoverGame(){
		tempHero.father.setOnTouchListener(tempHero.father);//����������
		tempHero.father.setCurrentDrawable(null);//�ÿռ�¼���õı���
		tempHero.father.setStatus(0);//��������GameViewΪ����״̬
		tempHero.father.gvt.setChanging(true);//����ת����
		status = 0;//Ϊ�´���������״̬
	}
	//����������ս������Ӯ
	public void calculateWinOrLose(){
		//����Ӣ�۵���ʧ
		int tempHeroLost = getCityAttack(this) - getHeroDefence(tempHero, tempHero.father.fightingGeneral);
		if(tempHeroLost<getCityAttack(this)*0.01f){//���Ӣ����ʧС�ڹ���������С�˺�ֵ
			heroLost = (int)(getCityAttack(this)*0.01f);//Ӣ����ʧΪ����������С�˺�ֵ
		}
		else if(tempHeroLost > tempHero.armyWithMe){//���Ӣ�۵���ʧ�������б���
			heroLost = tempHero.armyWithMe;//Ӣ����ʧ�ı���Ϊ���б���
		}
		else{//�����������
			heroLost = tempHeroLost;
		}
		//����ǳص���ʧ
		int tempCityLost = getHeroAttack(tempHero, tempHero.father.fightingGeneral) - getCityDefence(this);
		if(tempCityLost < getHeroAttack(tempHero, tempHero.father.fightingGeneral)*0.01f){//����ǳ���ʧС��Ӣ�۵���С����ֵ
			cityLost = (int)(getHeroAttack(tempHero, tempHero.father.fightingGeneral)*0.01f);//�ǳ���ʧΪ��Сֵ
		}
		else if(tempCityLost > this.army){//����ǳ���ʧ���ڳǳصı���
			cityLost = this.army;
		}
		else{//�����������
			cityLost = tempCityLost;
		}
		//������Ӯ
		if(cityLost == this.army){//����Է�ȫ����û
			status = 2;
			//�������ǻ���Ӣ��
			tempHero.getCityList().add(this);//�ѳǳػ����Լ�
			tempHero.father.allCityDrawable.remove(this);//�ط��ǳ��б���ɾȥ
			this.setCountry(8);
//			//���ԡ���������������������
//			tempHero.father.allCityDrawable = new ArrayList<CityDrawable>();
//			//��������������
			//����Ƿ�ͳһȫ��
			if(tempHero.father.allCityDrawable.size() == 0){//�з�û�ǳ���
				GameOverAlert goa = new GameOverAlert(tempHero.father, 1, GameView.dialogBack, GameView.dialogButton);
				tempHero.father.setCurrentGameAlert(goa);//����Ϊ��ǰ��GameAlert
			}
		}
		else if(heroLost == tempHero.armyWithMe){//���Ӣ��ȫ����û
			result = 0;
			status = 1;
		}
		
		else if(heroLost < cityLost){//��ͨ������
			result = 0;
			status = 1;
		}
		else if(heroLost > cityLost){//��ͨ��Ӯ��
			result = 1;
			status = 1;
		}
		else {
		}

		this.army -= cityLost;
		tempHero.setArmyWithMe(tempHero.getArmyWithMe() - heroLost);
		General g = tempHero.father.fightingGeneral;
		g.setStrength(g.getStrength()-20);//���̵���λ������������
		General g2 = this.guardGeneral.get(0);
		g2.setStrength(g2.getStrength()-20);//�Է��Ľ���Ҳ��������
		
	}
	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(cityName);
		out.writeInt(country);
		out.writeInt(army);
		out.writeInt(food);
		out.writeInt(level);
		out.writeObject(guardGeneral);
		out.writeInt(baseAttack);
		out.writeInt(baseDefend);
		out.writeInt(citizen);
		out.writeInt(warTank);
		out.writeInt(warTower);
		out.writeInt(tax);
		out.writeObject(dialogMessage);
		out.writeInt(info);
		out.writeInt(col);
		out.writeInt(row);
	}
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		cityName = in.readUTF();
		country = in.readInt();
		army = in.readInt();
		food = in.readInt();
		level = in.readInt();
		guardGeneral = (ArrayList<General>) in.readObject();
		baseAttack = in.readInt();
		baseDefend = in.readInt();
		citizen = in.readInt();
		warTank = in.readInt();
		warTower = in.readInt();
		tax = in.readInt();
		dialogMessage = (String[]) in.readObject();
		info = in.readInt();
		col=in.readInt();
		row=in.readInt();
	}

	public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


	public int getCountry() {
		return country;
	}


	public void setCountry(int country) {
		this.country = country;
	}


	public int getArmy() {
		return army;
	}


	public void setArmy(int army) {
		this.army = army;
	}


	public int getFood() {
		return food;
	}


	public void setFood(int food) {
		this.food = food;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public ArrayList<General> getGuardGeneral() {
		return guardGeneral;
	}


	public void setGuardGeneral(ArrayList<General> guardGeneral) {
		this.guardGeneral = guardGeneral;
	}


	public int getBaseAttack() {
		return baseAttack;
	}


	public void setBaseAttack(int baseAttack) {
		this.baseAttack = baseAttack;
	}


	public int getBaseDefend() {
		return baseDefend;
	}


	public void setBaseDefend(int baseDefend) {
		this.baseDefend = baseDefend;
	}


	public int getCitizen() {
		return citizen;
	}


	public void setCitizen(int citizen) {
		this.citizen = citizen;
	}


	public int getWarTank() {
		return warTank;
	}


	public void setWarTank(int warTank) {
		this.warTank = warTank;
	}


	public int getWarTower() {
		return warTower;
	}


	public void setWarTower(int warTower) {
		this.warTower = warTower;
	}
}
