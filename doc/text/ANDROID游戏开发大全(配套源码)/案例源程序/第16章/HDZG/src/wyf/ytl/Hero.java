package wyf.ytl;		//声明包语句

import static wyf.ytl.ConstantUtil.*;		//引入相关类
import java.io.Externalizable;		//引入相关类
import java.io.IOException;			//引入相关类
import java.io.ObjectInput;			//引入相关类
import java.io.ObjectOutput;		//引入相关类
import java.util.ArrayList;			//引入相关类
import java.util.HashMap;			//引入相关类
import java.util.Random;			//引入相关类
import android.graphics.Bitmap;		//引入相关类
import android.graphics.Canvas;		//引入相关类
		
/*
 * 此类为游戏中一个重要的类，封装了玩家的所有游戏信息，以及一些游戏中需要用到的方法
 * 在该类中既包含英雄的个人属性，也包含其手下的将领列表，拥有的城池列表等
 */
public class Hero implements Externalizable{

	public Hero(){
	}
	
	//我方可能得到的将领
	public static ArrayList<General> MY_GENERAL = new ArrayList<General>();	
	static{
		MY_GENERAL.add(new General("萧何", 0, 100, 30, 34, 62, 38, 100, 1));
		MY_GENERAL.add(new General("张良", 0, 100, 20, 54, 42, 58, 100, 1));
		MY_GENERAL.add(new General("韩信", 0, 70, 55, 83, 32, 38, 100, 1));
		MY_GENERAL.add(new General("曹参", 0, 96, 30, 44, 43, 65, 100, 1));
		MY_GENERAL.add(new General("周勃", 0, 65, 50, 34, 32, 61, 100, 1));
		MY_GENERAL.add(new General("陈平", 0, 100, 40, 54, 52, 38, 100, 1));
		MY_GENERAL.add(new General("王陵", 0, 100, 60, 34, 42, 48, 100, 1));
		MY_GENERAL.add(new General("灌婴", 0, 40, 40, 64, 22, 48, 100, 1));
		MY_GENERAL.add(new General("龙骨", 0, 100, 50, 44, 52, 38, 100, 1));
		MY_GENERAL.add(new General("夏侯婴", 0, 100, 30, 63, 32, 48, 100, 1));
		MY_GENERAL.add(new General("刘敬", 0, 88, 43, 44, 42, 78, 100, 1));	
		MY_GENERAL.add(new General("叔孙通", 0, 90, 46, 44, 22, 63, 100, 1));	
		MY_GENERAL.add(new General("周亚夫", 0, 100, 40, 44, 12, 78, 100, 1));	
		MY_GENERAL.add(new General("李牧", 0, 60, 40, 44, 42, 48, 100, 1));	
		MY_GENERAL.add(new General("李广", 0, 60, 40, 43, 45, 50, 100, 1));	
	}
	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(name);
		out.writeInt(strength);
		out.writeInt(maxStrength);
		out.writeInt(level);
		out.writeInt(direction);
		out.writeInt(currentFrame);
		out.writeInt(food);
		out.writeInt(totalMoney);
		out.writeInt(armyWithMe);
		out.writeInt(totalArmy);
		out.writeInt(col);
		out.writeInt(row);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(width);
		out.writeInt(height);
		out.writeInt(title);
		out.writeInt(warTank);
		out.writeInt(warTower);
		out.writeInt(strengthGrowSpan);
		out.writeInt(basicAttack);
		out.writeInt(basicDefend);
		out.writeObject(cityList);
		out.writeObject(generalList);
		out.writeObject(researchList);
		out.writeObject(heroSkill);
		out.writeObject(MY_GENERAL);
	}
	
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		name = (String) in.readObject();
		this.strength = in.readInt();
		this.maxStrength = in.readInt();
		this.level = in.readInt();
		direction = in.readInt();
		currentFrame = in.readInt();
		food = in.readInt();
		totalMoney = in.readInt();
		armyWithMe = in.readInt();
		totalArmy = in.readInt();
		col = in.readInt();
		row = in.readInt();
		x = in.readInt();
		y = in.readInt();
		width = in.readInt();
		height = in.readInt();
		title = in.readInt();
		warTank = in.readInt();
		warTower = in.readInt();
		strengthGrowSpan = in.readInt();
		basicAttack = in.readInt();
		basicDefend = in.readInt();
		cityList = (ArrayList<CityDrawable>) in.readObject();
		generalList = (ArrayList<General>) in.readObject();		
		researchList = (ArrayList<Research>) in.readObject();
		heroSkill = (HashMap<Integer, Skill>) in.readObject();
		MY_GENERAL=(ArrayList<General>)in.readObject();
		
		//恢复相关信息和数据 
		ht = new HeroThread();
		
		initHeroSkills();	
	}
	
	private static final long serialVersionUID = -5433306195939766058L;
	GameView father;//Activity引用
	private String name = "段世雄";
	int strength = 100;//英雄体力，
	int maxStrength = 100;//体力上限，会随着等级增加
	int level = 1;//英雄等级
	int direction = -1;//英雄的移动方向，4：下，5：左，6:右，7：上。同时也代表当前英雄的动画段号，从零开始
	int currentFrame = 0;//当前英雄的动画段的当前动画帧，从零开始
	int food = 10000;//随身携带的粮草，不包括每个城池中的粮食
	int totalMoney=6000;//总金钱

	int armyWithMe = 6000;//跟随自己的军队
	int totalArmy;//总军队，包括跟随自己的和守城在家的

	int col;//英雄的定位点在大地图中的列，定位点为下面的格子的中心
	int row;//英雄的定位点在大地图中的行，定位点为下面的格子的中心
	int x;//英雄『中心点』的x坐标，用于绘制 
	int y;//英雄『中心点』的y坐标，用于绘制
	int width;//英雄的宽度=======在initAnimationSegment方法中初始化
	int height;//英雄的高度=======在initAnimationSegment方法中初始化
	int title = 0;//官衔 
	int warTank = 0;//随身携带的战车
	int warTower = 0;//随身携带的箭垛
	int strengthGrowSpan = 1;
	int basicAttack = 18;//基础攻击力
	int basicDefend = 18;//基础防御力


	ArrayList<Bitmap []> animationSegment = new ArrayList<Bitmap []>();//存放英雄所有的动画段，每个动画段为一个一维数组
	ArrayList<CityDrawable> cityList = new ArrayList<CityDrawable>();//存放英雄拥有的城池
	ArrayList<General> generalList = new ArrayList<General>();//存放【跟随】英雄的将领
	ArrayList<Research> researchList = new ArrayList<Research>();//存放现有的科研项目
	HashMap<Integer,Skill> heroSkill = new HashMap<Integer,Skill>();//存放英雄的所有技能
	
	HeroThread ht;//负责英雄动画换帧的线程
	HeroGoThread hgt;//负责英雄走路的线程
	HeroBackDataThread hbdt;//负责后台数据更改的线程，如粮草减少，科研进度/敌人来袭等
	//构造器：
	public Hero(GameView father,int col,int row){
		this.col = col;
		this.row = row;
		this.x = col*TILE_SIZE+TILE_SIZE/2+1;
		this.y = row*TILE_SIZE+TILE_SIZE/2+1;
		this.father = father;
		ht = new HeroThread();
		hgt = new HeroGoThread(father,this);
		hbdt = new HeroBackDataThread(this);
		hbdt.start();
		initHeroSkills();	
	}
	//方法：初始化动画段列表
	public void initAnimationSegment(Bitmap [][] segments){
		for(Bitmap [] segment:segments){
			addAnimationSegment(segment);
		}
		this.height = segments[0][0].getHeight();//初始化英雄图片高度
		this.width = segments[0][0].getWidth();//初始化英雄图片宽度
	}
	//方法：向动画段列表中添加动画段,该方法会在初始化动画段列表中被调用
	public void addAnimationSegment(Bitmap [] segment){
		this.animationSegment.add(segment);
	}
	//方法：设置方向，同时也是动画段索引
	public void setAnimationDirection(int direction){
		this.direction = direction;
	}
	//方法：开始换帧动画
	public void startAnimation(){
		ht.isGameOn = true;//设置换帧标志位为真
		if(!ht.isAlive()){//如果换帧线程没有启动则启动它
			ht.start();
		}
	}
	//方法：暂停动画
	public void pauseAnimation(){
		if(ht != null){//如果换帧线程存在，就设置换帧标志位为假
			ht.isGameOn = false;
		}
	}
	//方法：销毁动画及其换帧线程
	public void destroyAnimation(){
		ht.isGameOn = false;//停止换帧
		ht.flag = false;//停止线程的run方法
		ht = null;//释放对象
	}
	//方法：在屏幕上绘制自己,根据传入的屏幕定位row和col计算出相对坐标画出
	public void drawSelf(Canvas canvas,int startRow,int startCol,int offsetX,int offsetY){
		if(direction>animationSegment.size()){
			return;
		}
		Bitmap bmp = animationSegment.get(direction)[currentFrame];
		int topLeftCornerX = x-TILE_SIZE/2-1 - startCol*TILE_SIZE;//先计算出左上角坐标，再转换成屏幕的相对坐标
		int topLeftCornerY = y+TILE_SIZE/2-height - startRow*TILE_SIZE;//先计算出左上角坐标，再转换成屏幕的相对坐标
		canvas.drawBitmap(bmp, topLeftCornerX-offsetX, topLeftCornerY-offsetY, null);
	}
	//方法：掷骰子方法,接收骰子的个数，返回一个数组表示每个骰子上的点数
	public int [] throwDice(int diceNumber){
		int [] result = new int[3];
		Random random = new Random();
		for(int i=0;i<diceNumber;i++){//根据骰子个数来随机产生骰子
			result[i] = random.nextInt(32)%6;//注意：产生的是0-5的数，不是1-6
		}
		return result;
	}
	//方法：换帧
	public void nextFrame(){
		int frameLength = this.animationSegment.get(direction).length;
		this.currentFrame = (this.currentFrame+1)%frameLength;
	}
	//方法：激活英雄的走路线程，传入格子数使其开动
	public void startToGo(int steps){
		hgt.setMoving(true);
		hgt.setSteps(steps);	
		if(!hgt.isAlive()){//没启动就启动
			hgt.start();
		}			
		father.setStatus(1);//为1表示状态为英雄正在走
	}
	
	//方法：根据英雄移动的增加英雄的体力
	public void growStrength(int steps){
		int increment = steps*strengthGrowSpan;
		if(strength+increment < maxStrength){//加不满
			strength+=increment;
		}
		else{
			strength = maxStrength;
		}
	}
	
	//内部线程类：负责定时更改英雄的动画帧，但是不负责改变动画段
	class HeroThread extends Thread{
		
		boolean flag;//线程的run方法是否执行的标志位
		boolean isGameOn;//是否进行换帧的标志位
		public HeroThread(){
			super.setName("==Hero.HeroThread");
			flag = true;
		}
		public void run(){
			while(flag){
				while(isGameOn){
					try{
						nextFrame();//进行换帧操作
					}
					catch(Exception e){
					}
					try{
						Thread.sleep(HERO_ANIMATION_SLEEP_SPAN);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				//空转的时候也要睡眠
				try{
					Thread.sleep(HERO_NO_ANIMATION_SLEEP_SPAN);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	public int getTotalFood(){
		int result = 0;
		for(CityDrawable cd : cityList){
			result = result+cd.getFood();//加上每个城池的
		}
		result += this.food;//加上随身携带的
		return result;
	}
	
	public int getGeneralNumber(){//得到英雄所拥有的所有将军
		int result = 0;
		for(CityDrawable cd : cityList){
			result = result+cd.guardGeneral.size();
		}
		result += generalList.size();
		return result;
	}
	
	//方法：初始化英雄的技能
	public void initHeroSkills(){
	//技能类型，包括0:生活技能、1:战斗技能、2:普通技能
		LumberSkill ls = new LumberSkill(LUMBER, "伐木", 500, 0, this);
		this.heroSkill.put(ls.id, ls); 
		FishingSkill fishingSkill = new FishingSkill(FISHING,"捕鱼",500, 0, this);
		this.heroSkill.put(fishingSkill.id, fishingSkill);
		MiningSkill miningSkill = new MiningSkill(MINING, "采矿", 500, 0, this);
		this.heroSkill.put(miningSkill.id, miningSkill);
		FarmingSkill farmSkill = new FarmingSkill(FARMING, "农耕", 500, 0, this);
		this.heroSkill.put(farmSkill.id, farmSkill);	
		SuiXinBuSkill s = new SuiXinBuSkill(SUI_XIN_BU, "随心步", -1, 1, this);
		this.heroSkill.put(s.id, s);
	}
	
	public void setHeroGeneral(General general){
		this.generalList.add(general);
	}
	
	public String getName(){
		return name;
	}
	
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getMaxStrength() {
		return maxStrength;
	}
	public void setMaxStrength(int maxStrength) {
		this.maxStrength = maxStrength;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getCurrentFrame() {
		return currentFrame;
	}
	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	public int getFood() {
		return food;
	}
	public void setFood(int food) {
		this.food = food;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getTitle() {
		return HERO_TITLE[title];
	}
	public void setTitle(int title) {
		this.title = title;
	}
	public HashMap<Integer,Skill> getHeroSkill() {
		return heroSkill;
	}
	public void setHeroSkill(HashMap<Integer,Skill> heroSkill) {
		this.heroSkill = heroSkill;
	}
	public ArrayList<General> getGeneralList() {
		return generalList;
	}
	public void setGeneralList(ArrayList<General> generalList) {
		this.generalList = generalList;
	}
	public ArrayList<CityDrawable> getCityList() {
		return cityList;
	}
	public ArrayList<Research> getResearchList() {
		return researchList;
	}
	public int getArmyWithMe() {
		return armyWithMe;
	}
	public void setArmyWithMe(int armyWithMe) {
		this.armyWithMe = armyWithMe;
	}
	public int getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}
	public int getTotalArmy() {
		int result = this.getArmyWithMe();
		for(CityDrawable cd : cityList){
			result = result+cd.getArmy();//加上每个城池的
		}
		return result;		
	}
	public void setTotalArmy(int totalArmy) {
		this.totalArmy = totalArmy;
	}
	
	public int getTotalCitizen(){//得到总居民人口
		int result = 0;
		for(CityDrawable cd : cityList){
			result = result+cd.getCitizen();//加上每个城池的
		}
		return result;
	}
	
	public int getTotalWarTank(){//得到战车数量
		int result = 0;
		for(CityDrawable cd : cityList){
			result = result+cd.getWarTank();//加上每个城池的
		}
		result += this.warTank;//加上自身的
		return result;
	}
	
	public int getTotalWarTower(){//得到战车数量
		int result = 0;
		for(CityDrawable cd : cityList){
			result = result+cd.getWarTower();//加上每个城池的
		}
		result += this.warTower;//加上自身的
		return result;
	}
	
	public ArrayList<General> getTotalGeneral(){//得到所有的将领，包括自己城池中的
		ArrayList<General> result = new ArrayList<General>();
		result.addAll(generalList);//将跟随的将领添加到列表
		for(CityDrawable cb : cityList){//添加各个城池中的将领
			result.addAll(cb.getGuardGeneral());
		}
		return result;
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