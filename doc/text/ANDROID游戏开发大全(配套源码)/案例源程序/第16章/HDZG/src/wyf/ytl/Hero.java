package wyf.ytl;		//���������

import static wyf.ytl.ConstantUtil.*;		//���������
import java.io.Externalizable;		//���������
import java.io.IOException;			//���������
import java.io.ObjectInput;			//���������
import java.io.ObjectOutput;		//���������
import java.util.ArrayList;			//���������
import java.util.HashMap;			//���������
import java.util.Random;			//���������
import android.graphics.Bitmap;		//���������
import android.graphics.Canvas;		//���������
		
/*
 * ����Ϊ��Ϸ��һ����Ҫ���࣬��װ����ҵ�������Ϸ��Ϣ���Լ�һЩ��Ϸ����Ҫ�õ��ķ���
 * �ڸ����мȰ���Ӣ�۵ĸ������ԣ�Ҳ���������µĽ����б�ӵ�еĳǳ��б��
 */
public class Hero implements Externalizable{

	public Hero(){
	}
	
	//�ҷ����ܵõ��Ľ���
	public static ArrayList<General> MY_GENERAL = new ArrayList<General>();	
	static{
		MY_GENERAL.add(new General("����", 0, 100, 30, 34, 62, 38, 100, 1));
		MY_GENERAL.add(new General("����", 0, 100, 20, 54, 42, 58, 100, 1));
		MY_GENERAL.add(new General("����", 0, 70, 55, 83, 32, 38, 100, 1));
		MY_GENERAL.add(new General("�ܲ�", 0, 96, 30, 44, 43, 65, 100, 1));
		MY_GENERAL.add(new General("�ܲ�", 0, 65, 50, 34, 32, 61, 100, 1));
		MY_GENERAL.add(new General("��ƽ", 0, 100, 40, 54, 52, 38, 100, 1));
		MY_GENERAL.add(new General("����", 0, 100, 60, 34, 42, 48, 100, 1));
		MY_GENERAL.add(new General("��Ӥ", 0, 40, 40, 64, 22, 48, 100, 1));
		MY_GENERAL.add(new General("����", 0, 100, 50, 44, 52, 38, 100, 1));
		MY_GENERAL.add(new General("�ĺ�Ӥ", 0, 100, 30, 63, 32, 48, 100, 1));
		MY_GENERAL.add(new General("����", 0, 88, 43, 44, 42, 78, 100, 1));	
		MY_GENERAL.add(new General("����ͨ", 0, 90, 46, 44, 22, 63, 100, 1));	
		MY_GENERAL.add(new General("���Ƿ�", 0, 100, 40, 44, 12, 78, 100, 1));	
		MY_GENERAL.add(new General("����", 0, 60, 40, 44, 42, 48, 100, 1));	
		MY_GENERAL.add(new General("���", 0, 60, 40, 43, 45, 50, 100, 1));	
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
		
		//�ָ������Ϣ������ 
		ht = new HeroThread();
		
		initHeroSkills();	
	}
	
	private static final long serialVersionUID = -5433306195939766058L;
	GameView father;//Activity����
	private String name = "������";
	int strength = 100;//Ӣ��������
	int maxStrength = 100;//�������ޣ������ŵȼ�����
	int level = 1;//Ӣ�۵ȼ�
	int direction = -1;//Ӣ�۵��ƶ�����4���£�5����6:�ң�7���ϡ�ͬʱҲ����ǰӢ�۵Ķ����κţ����㿪ʼ
	int currentFrame = 0;//��ǰӢ�۵Ķ����εĵ�ǰ����֡�����㿪ʼ
	int food = 10000;//����Я�������ݣ�������ÿ���ǳ��е���ʳ
	int totalMoney=6000;//�ܽ�Ǯ

	int armyWithMe = 6000;//�����Լ��ľ���
	int totalArmy;//�ܾ��ӣ����������Լ��ĺ��س��ڼҵ�

	int col;//Ӣ�۵Ķ�λ���ڴ��ͼ�е��У���λ��Ϊ����ĸ��ӵ�����
	int row;//Ӣ�۵Ķ�λ���ڴ��ͼ�е��У���λ��Ϊ����ĸ��ӵ�����
	int x;//Ӣ�ۡ����ĵ㡻��x���꣬���ڻ��� 
	int y;//Ӣ�ۡ����ĵ㡻��y���꣬���ڻ���
	int width;//Ӣ�۵Ŀ��=======��initAnimationSegment�����г�ʼ��
	int height;//Ӣ�۵ĸ߶�=======��initAnimationSegment�����г�ʼ��
	int title = 0;//���� 
	int warTank = 0;//����Я����ս��
	int warTower = 0;//����Я���ļ���
	int strengthGrowSpan = 1;
	int basicAttack = 18;//����������
	int basicDefend = 18;//����������


	ArrayList<Bitmap []> animationSegment = new ArrayList<Bitmap []>();//���Ӣ�����еĶ����Σ�ÿ��������Ϊһ��һά����
	ArrayList<CityDrawable> cityList = new ArrayList<CityDrawable>();//���Ӣ��ӵ�еĳǳ�
	ArrayList<General> generalList = new ArrayList<General>();//��š����桿Ӣ�۵Ľ���
	ArrayList<Research> researchList = new ArrayList<Research>();//������еĿ�����Ŀ
	HashMap<Integer,Skill> heroSkill = new HashMap<Integer,Skill>();//���Ӣ�۵����м���
	
	HeroThread ht;//����Ӣ�۶�����֡���߳�
	HeroGoThread hgt;//����Ӣ����·���߳�
	HeroBackDataThread hbdt;//�����̨���ݸ��ĵ��̣߳������ݼ��٣����н���/������Ϯ��
	//��������
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
	//��������ʼ���������б�
	public void initAnimationSegment(Bitmap [][] segments){
		for(Bitmap [] segment:segments){
			addAnimationSegment(segment);
		}
		this.height = segments[0][0].getHeight();//��ʼ��Ӣ��ͼƬ�߶�
		this.width = segments[0][0].getWidth();//��ʼ��Ӣ��ͼƬ���
	}
	//�������򶯻����б�����Ӷ�����,�÷������ڳ�ʼ���������б��б�����
	public void addAnimationSegment(Bitmap [] segment){
		this.animationSegment.add(segment);
	}
	//���������÷���ͬʱҲ�Ƕ���������
	public void setAnimationDirection(int direction){
		this.direction = direction;
	}
	//��������ʼ��֡����
	public void startAnimation(){
		ht.isGameOn = true;//���û�֡��־λΪ��
		if(!ht.isAlive()){//�����֡�߳�û��������������
			ht.start();
		}
	}
	//��������ͣ����
	public void pauseAnimation(){
		if(ht != null){//�����֡�̴߳��ڣ������û�֡��־λΪ��
			ht.isGameOn = false;
		}
	}
	//���������ٶ������任֡�߳�
	public void destroyAnimation(){
		ht.isGameOn = false;//ֹͣ��֡
		ht.flag = false;//ֹͣ�̵߳�run����
		ht = null;//�ͷŶ���
	}
	//����������Ļ�ϻ����Լ�,���ݴ������Ļ��λrow��col�����������껭��
	public void drawSelf(Canvas canvas,int startRow,int startCol,int offsetX,int offsetY){
		if(direction>animationSegment.size()){
			return;
		}
		Bitmap bmp = animationSegment.get(direction)[currentFrame];
		int topLeftCornerX = x-TILE_SIZE/2-1 - startCol*TILE_SIZE;//�ȼ�������Ͻ����꣬��ת������Ļ���������
		int topLeftCornerY = y+TILE_SIZE/2-height - startRow*TILE_SIZE;//�ȼ�������Ͻ����꣬��ת������Ļ���������
		canvas.drawBitmap(bmp, topLeftCornerX-offsetX, topLeftCornerY-offsetY, null);
	}
	//�����������ӷ���,�������ӵĸ���������һ�������ʾÿ�������ϵĵ���
	public int [] throwDice(int diceNumber){
		int [] result = new int[3];
		Random random = new Random();
		for(int i=0;i<diceNumber;i++){//�������Ӹ����������������
			result[i] = random.nextInt(32)%6;//ע�⣺��������0-5����������1-6
		}
		return result;
	}
	//��������֡
	public void nextFrame(){
		int frameLength = this.animationSegment.get(direction).length;
		this.currentFrame = (this.currentFrame+1)%frameLength;
	}
	//����������Ӣ�۵���·�̣߳����������ʹ�俪��
	public void startToGo(int steps){
		hgt.setMoving(true);
		hgt.setSteps(steps);	
		if(!hgt.isAlive()){//û����������
			hgt.start();
		}			
		father.setStatus(1);//Ϊ1��ʾ״̬ΪӢ��������
	}
	
	//����������Ӣ���ƶ�������Ӣ�۵�����
	public void growStrength(int steps){
		int increment = steps*strengthGrowSpan;
		if(strength+increment < maxStrength){//�Ӳ���
			strength+=increment;
		}
		else{
			strength = maxStrength;
		}
	}
	
	//�ڲ��߳��ࣺ����ʱ����Ӣ�۵Ķ���֡�����ǲ�����ı䶯����
	class HeroThread extends Thread{
		
		boolean flag;//�̵߳�run�����Ƿ�ִ�еı�־λ
		boolean isGameOn;//�Ƿ���л�֡�ı�־λ
		public HeroThread(){
			super.setName("==Hero.HeroThread");
			flag = true;
		}
		public void run(){
			while(flag){
				while(isGameOn){
					try{
						nextFrame();//���л�֡����
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
				//��ת��ʱ��ҲҪ˯��
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
			result = result+cd.getFood();//����ÿ���ǳص�
		}
		result += this.food;//��������Я����
		return result;
	}
	
	public int getGeneralNumber(){//�õ�Ӣ����ӵ�е����н���
		int result = 0;
		for(CityDrawable cd : cityList){
			result = result+cd.guardGeneral.size();
		}
		result += generalList.size();
		return result;
	}
	
	//��������ʼ��Ӣ�۵ļ���
	public void initHeroSkills(){
	//�������ͣ�����0:����ܡ�1:ս�����ܡ�2:��ͨ����
		LumberSkill ls = new LumberSkill(LUMBER, "��ľ", 500, 0, this);
		this.heroSkill.put(ls.id, ls); 
		FishingSkill fishingSkill = new FishingSkill(FISHING,"����",500, 0, this);
		this.heroSkill.put(fishingSkill.id, fishingSkill);
		MiningSkill miningSkill = new MiningSkill(MINING, "�ɿ�", 500, 0, this);
		this.heroSkill.put(miningSkill.id, miningSkill);
		FarmingSkill farmSkill = new FarmingSkill(FARMING, "ũ��", 500, 0, this);
		this.heroSkill.put(farmSkill.id, farmSkill);	
		SuiXinBuSkill s = new SuiXinBuSkill(SUI_XIN_BU, "���Ĳ�", -1, 1, this);
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
			result = result+cd.getArmy();//����ÿ���ǳص�
		}
		return result;		
	}
	public void setTotalArmy(int totalArmy) {
		this.totalArmy = totalArmy;
	}
	
	public int getTotalCitizen(){//�õ��ܾ����˿�
		int result = 0;
		for(CityDrawable cd : cityList){
			result = result+cd.getCitizen();//����ÿ���ǳص�
		}
		return result;
	}
	
	public int getTotalWarTank(){//�õ�ս������
		int result = 0;
		for(CityDrawable cd : cityList){
			result = result+cd.getWarTank();//����ÿ���ǳص�
		}
		result += this.warTank;//���������
		return result;
	}
	
	public int getTotalWarTower(){//�õ�ս������
		int result = 0;
		for(CityDrawable cd : cityList){
			result = result+cd.getWarTower();//����ÿ���ǳص�
		}
		result += this.warTower;//���������
		return result;
	}
	
	public ArrayList<General> getTotalGeneral(){//�õ����еĽ��죬�����Լ��ǳ��е�
		ArrayList<General> result = new ArrayList<General>();
		result.addAll(generalList);//������Ľ�����ӵ��б�
		for(CityDrawable cb : cityList){//��Ӹ����ǳ��еĽ���
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