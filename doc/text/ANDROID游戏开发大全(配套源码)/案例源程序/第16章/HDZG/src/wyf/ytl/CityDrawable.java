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
 * 该类代表城池类，封装了有关城池的信息，如城池名称、所属国家、兵力、粮草、守城将领
 * 基础防御力和基础攻击力，等级、居民人口等等，还可以有战车的个数和箭垛，以及民众度
 */
public class CityDrawable extends MyMeetableDrawable implements Externalizable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5592838008100279432L;
	String cityName;//城池名称
	int country;//所属国家
	int army;//兵力
	int food;//粮草
	int level;//等级
	ArrayList<General> guardGeneral = new ArrayList<General>();//武将列表
	int baseAttack = 20;//每个士兵的基础攻击力
	int baseDefend = 15;//每个士兵的基础防御力
	int citizen;//居民
	int warTank = 0;//战车的个数，用于增加攻城力
	int warTower = 0;//箭垛的个数用于增加防御力
	
	int tax = 5;//过城要交的税
	
	int info;
	
	String []dialogMessage = {
			"来到aa的城市bb，兵力cc，粮草dd，守将ee，预计通关税为ff金。请选择：",
			"战斗xx！你在战斗中歼灭敌人yy人，损失将士zz人。",
			"你指挥若定，犹如神助，一举攻破了对方的城池，现在这座城属于你了，守将也投降了，他将向你尽忠，继续守护这座城。",
			"你连用来打仗的士兵都没了，还是老老实实交税过城吧。",
			"你来到了自己的城池，欢迎检阅！"
	};
	int status= 0;//为0绘制询问，为1显示战斗结果,2表示攻克城池，3表示没兵可打仗了，4表示来到自己的城池，5表示onTouch监听器为将领选择器
	private int heroLost;//英雄在战斗中的损失
	private int cityLost;//城池在战斗中的损失
	int result = -1;//战斗结果，0表示输，1表示赢,
	

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
	public void addCityInfo(CityInfo cityInfo){//设置城市的信息
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
	
	public void setBackToInit(){//恢复到默认		
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
	
	//绘制对话框方法
	public void drawDialog(Canvas canvas, Hero hero) {
		String showString = null;//需要显示到对话框中的字符串
		tempHero = hero;
		//先画背景
		canvas.drawBitmap(bmpDialogBack, 0, DIALOG_START_Y, null);
		if(tempHero.cityList.contains(this) && status!=2){//如果来到自己的城池
			status = 4;
		}
		if(status == 0){//询问时交税还是战斗
			showString = dialogMessage[status];
			showString = showString.replaceAll("aa", COUNTRY_NAME[country]);//替换国家字符串
			showString = showString.replaceFirst("bb", cityName);//替换掉城市名称字符串
			showString = showString.replaceFirst("cc", army+"");//替换掉城市名称字符串
			showString = showString.replaceFirst("dd", food+"");//替换掉城市名称字符串
			showString = showString.replaceFirst("ee", getGeneralsName());//替换掉城市名称字符串
			showString = showString.replaceFirst("ff", calculateTax()+"");//替换掉缴税数目字符串	
			
			drawString(canvas, showString);
			//画按钮确定按钮
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
			Paint paint = new Paint();
			paint.setARGB(255, 42, 48, 103);
			paint.setAntiAlias(true);
			paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
			paint.setTextSize(18);
			canvas.drawText("缴税",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);
			//画取消按钮
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X+DIALOG_BTN_SPAN, DIALOG_BTN_START_Y, null);
			canvas.drawText("攻城", 
					DIALOG_BTN_START_X+DIALOG_BTN_SPAN+DIALOG_BTN_WORD_LEFT, 
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);				
		} 
		else if(status == 1){//显示战斗结果
			showString = dialogMessage[status];
			showString = showString.replaceFirst("xx", result==0?"失败":"胜利");//替换掉战斗结果
			showString = showString.replaceFirst("yy", cityLost+"");//替换掉歼敌人数
			showString = showString.replaceFirst("zz", heroLost+"");//替换掉损失人数
			drawString(canvas, showString);//画出战斗结果
			//画按钮确定按钮
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
			Paint paint = new Paint();
			paint.setARGB(255, 42, 48, 103);
			paint.setAntiAlias(true);
			paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
			paint.setTextSize(18);
			canvas.drawText("确定",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);			
		}
		else if(status == 2 || status == 3 || status ==4){//攻克一座城,或是没兵可打了,或是遇到了自己的城池
			showString = dialogMessage[status];
			drawString(canvas, showString);//画出战斗结果
			//画按钮确定按钮
			canvas.drawBitmap(bmpDialogButton, DIALOG_BTN_START_X, DIALOG_BTN_START_Y, null);		
			Paint paint = new Paint();
			paint.setARGB(255, 42, 48, 103);
			paint.setAntiAlias(true);
			paint.setTypeface(Typeface.create((Typeface)null,Typeface.ITALIC));
			paint.setTextSize(18);
			canvas.drawText("确定",
					DIALOG_BTN_START_X+DIALOG_BTN_WORD_LEFT,
					DIALOG_BTN_START_Y+DIALOG_WORD_SIZE+DIALOG_BTN_WORD_UP,
					paint
					);			
		}	
	}
	//获得将领的名称
	private String getGeneralsName() {
		String s = "";
		for(General g:guardGeneral){
			s+=g.getName()+"、";
		}
		return s.substring(0, s.length()-1);
	}
	//方法：计算关税
	public int calculateTax(){
		int defence = GameFormula.getCityDefence(this);
		return defence/200;
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(status == 0){//如果是询问状态时
				if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
						&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是左键
					tempHero.setTotalMoney(tempHero.getTotalMoney()-calculateTax());
					if(tempHero.getTotalMoney() <= 0){//破产了
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
						&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是攻城键
					if(tempHero.armyWithMe <=100){//英雄手头上的兵太少
						status = 3;
					}
					else{
						tempHero.father.selectGeneral.initData();
						tempHero.father.setStatus(3);//设置状态为选择将军出战
						status = 5;//状态为5表示监听器为将领选择器		
					}					
				}							
			}
			else if(status ==1 || status == 2 || status == 4){//如果是在显示战斗结果的状态或无兵可打\遇到自己的状态下下按下确定
				if(x>DIALOG_BTN_START_X && x<DIALOG_BTN_START_X+DIALOG_BTN_WIDTH
						&& y>DIALOG_BTN_START_Y && y<DIALOG_BTN_START_Y+DIALOG_BTN_HEIGHT){//点下的是确定键
					recoverGame();//恢复游戏状态
				}
			}		
			else if(status == 3){//如果是无兵可打的状态，切到状态0
				status = 0;
			}
			else if(status == 5){//如果是将领选择界面
				tempHero.father.selectGeneral.onTouchEvent(event);
			}
		}
		return true;
	}
	//方法：返还监听器，回复游戏状态
	public void recoverGame(){
		tempHero.father.setOnTouchListener(tempHero.father);//返还监听器
		tempHero.father.setCurrentDrawable(null);//置空记录引用的变量
		tempHero.father.setStatus(0);//重新设置GameView为待命状态
		tempHero.father.gvt.setChanging(true);//骰子转起来
		status = 0;//为下次遇到重置状态
	}
	//方法：计算战斗的输赢
	public void calculateWinOrLose(){
		//计算英雄的损失
		int tempHeroLost = getCityAttack(this) - getHeroDefence(tempHero, tempHero.father.fightingGeneral);
		if(tempHeroLost<getCityAttack(this)*0.01f){//如果英雄损失小于攻击方的最小伤害值
			heroLost = (int)(getCityAttack(this)*0.01f);//英雄损失为攻击方的最小伤害值
		}
		else if(tempHeroLost > tempHero.armyWithMe){//如果英雄的损失超过现有兵力
			heroLost = tempHero.armyWithMe;//英雄损失的兵力为现有兵力
		}
		else{//其他正常情况
			heroLost = tempHeroLost;
		}
		//计算城池的损失
		int tempCityLost = getHeroAttack(tempHero, tempHero.father.fightingGeneral) - getCityDefence(this);
		if(tempCityLost < getHeroAttack(tempHero, tempHero.father.fightingGeneral)*0.01f){//如果城池损失小于英雄的最小公机值
			cityLost = (int)(getHeroAttack(tempHero, tempHero.father.fightingGeneral)*0.01f);//城池损失为最小值
		}
		else if(tempCityLost > this.army){//如果城池损失大于城池的兵力
			cityLost = this.army;
		}
		else{//其他正常情况
			cityLost = tempCityLost;
		}
		//计算输赢
		if(cityLost == this.army){//如果对方全军覆没
			status = 2;
			//把这座城划给英雄
			tempHero.getCityList().add(this);//把城池划给自己
			tempHero.father.allCityDrawable.remove(this);//地方城池列表中删去
			this.setCountry(8);
//			//测试。。。。。。。。。。。
//			tempHero.father.allCityDrawable = new ArrayList<CityDrawable>();
//			//。。。。。。。
			//检测是否统一全国
			if(tempHero.father.allCityDrawable.size() == 0){//敌方没城池了
				GameOverAlert goa = new GameOverAlert(tempHero.father, 1, GameView.dialogBack, GameView.dialogButton);
				tempHero.father.setCurrentGameAlert(goa);//设置为当前的GameAlert
			}
		}
		else if(heroLost == tempHero.armyWithMe){//如果英雄全军覆没
			result = 0;
			status = 1;
		}
		
		else if(heroLost < cityLost){//普通的输了
			result = 0;
			status = 1;
		}
		else if(heroLost > cityLost){//普通的赢了
			result = 1;
			status = 1;
		}
		else {
		}

		this.army -= cityLost;
		tempHero.setArmyWithMe(tempHero.getArmyWithMe() - heroLost);
		General g = tempHero.father.fightingGeneral;
		g.setStrength(g.getStrength()-20);//打仗的那位将军消耗体力
		General g2 = this.guardGeneral.get(0);
		g2.setStrength(g2.getStrength()-20);//对方的将军也消耗体力
		
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
