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
	int yeSpan1 = 9;//主页显示城池的个数 
	int yeSpan2 = 9;//管理页显示城池的个数 
	int currentI = 0;//当前绘制的屏幕最上边的元素下标
	int selectI = 0;
	
	int startY = 120;
	int startX = 17;
	int status = 0;//0-主界面  1-详细信息

	String[][] items1;//城池列表 
	String[][] items2;//城池管理界面列表
	
	ArrayList<CityDrawable> cityList;//存放英雄拥有的城池
	CityDrawable selectCityDrawable;//当前选中的城市

	static Bitmap menuTitle;//表格的标题背景
	static Bitmap threeBitmap;//右上角的三个按钮
	static Bitmap panel_back;//背景图
	static Bitmap selectBackground;//选中后的背景
	static Bitmap buttonBackGround;//按钮背景
	static Bitmap upBitmap;//向上的小箭头
	static Bitmap downBitmap;//向下的小箭头
	static Bitmap addBitmap;//小加号
	static Bitmap cutBitmap;//小减号
	static Bitmap logo;
	
	Paint paint;
	
	public CityManageView(){}
	
	public CityManageView(GameView gameView) {//构造器
		this.gameView = gameView;	
		paint = new Paint();
		paint.setARGB(255, 42, 48, 103);//设置字体颜色
		paint.setAntiAlias(true);//抗锯齿
		initData();//初始化数据
	}
	public static void initBitmap(Resources r){//初始化图片资源   
		Bitmap menu_item = BitmapFactory.decodeResource(r, R.drawable.buttons); 
		threeBitmap = Bitmap.createBitmap(menu_item, 60, 0, 90, 30);
		buttonBackGround = Bitmap.createBitmap(menu_item, 0, 0, 60, 30);
		upBitmap = Bitmap.createBitmap(menu_item, 210, 15, 15, 15);
		downBitmap = Bitmap.createBitmap(menu_item, 210, 0, 15, 15);
		addBitmap = Bitmap.createBitmap(menu_item, 225, 15, 15, 15);
		cutBitmap = Bitmap.createBitmap(menu_item, 225, 0, 15, 15);
		menu_item = null;//释放掉大图
		panel_back = BitmapFactory.decodeResource(r, R.drawable.panel_back);
		logo = BitmapFactory.decodeResource(r, R.drawable.logo);
		selectBackground = BitmapFactory.decodeResource(r, R.drawable.select_back);
		menuTitle = BitmapFactory.decodeResource(r, R.drawable.menu_title);
	}
	
	public void initData(){//初始化数据
		cityList = gameView.hero.getCityList();//得到英雄所拥有的所有城池
		items1 = new String[cityList.size()][4];
		//初始化需要显示的城池信息
		for(int i=0; i<cityList.size(); i++){
			items1[i][0] = cityList.get(i).getCityName();//城池名称
			items1[i][1] = cityList.get(i).getGuardGeneral().size()+"人";//武将的个数
			items1[i][2] = cityList.get(i).getArmy()+"人";//兵力
			items1[i][3] = cityList.get(i).getFood()+"石";//粮草
		}
	}

	public void onDraw(Canvas canvas){//绘制的方法 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//绘制logo
		paint.setTextSize(18);//设置文字大小

		if(status == 0){//主界面时
			paint.setTextSize(23);//设置文字大小
			canvas.drawText("城池管理", 50, 40, paint);
			paint.setTextSize(18);//设置文字大小
			canvas.drawBitmap(menuTitle, 10, 70, paint);
			canvas.drawText("名称", 15, 90, paint);
			canvas.drawText("将领个数", 75, 90, paint);
			canvas.drawText("兵力", 160, 90, paint);
			canvas.drawText("粮草", 230, 90, paint);	
			
			int tempCurrentI = currentI;
			if(items1.length < yeSpan1){//一个屏幕可以显示下
				for(int i=0; i<items1.length; i++){
					canvas.drawText(items1[i][0], 15, startY + 5 + 30*i, paint);
					canvas.drawText(items1[i][1], 75, startY + 5 + 30*i, paint);
					canvas.drawText(items1[i][2], 160, startY + 5 + 30*i, paint);
					canvas.drawText(items1[i][3], 230, startY + 5 + 30*i, paint);
				}
			}
			else{//一个屏幕显示不下时
				for(int i=tempCurrentI; i<tempCurrentI+yeSpan1; i++){
					canvas.drawText(items1[i][0], 15, startY + 5+ 30*(i-tempCurrentI), paint);
					canvas.drawText(items1[i][1], 75, startY + 5+ 30*(i-tempCurrentI), paint);
					canvas.drawText(items1[i][2], 160, startY + 5+ 30*(i-tempCurrentI), paint);
					canvas.drawText(items1[i][3], 230, startY + 5+ 30*(i-tempCurrentI), paint);
				}
			}
			canvas.drawBitmap(selectBackground, 10, startY + 5 + 30*selectI - 22, paint);//绘制选择效果
			if(currentI != 0){//绘制小的向上箭头
				canvas.drawBitmap(upBitmap, 150, 80, paint);
			}
			if(items1.length>yeSpan1 && (currentI+yeSpan1) < items1.length){//绘制小的向下箭头
				canvas.drawBitmap(downBitmap, 150, 414, paint);
			}
			
			canvas.drawBitmap(buttonBackGround, 130, 430, paint);//绘制详细按钮背景
			canvas.drawText("详细", 142, 452, paint);
		}
		else if(status == 1){//详细信息界面 
			paint.setTextSize(23);//设置文字大小
			canvas.drawText("城池详细信息", 50, 40, paint);
			paint.setTextSize(18);//设置文字大小
			
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
			canvas.drawBitmap(selectBackground, 10, startY - 35 + 30*selectI - 22, paint);//绘制选择效果
		}
	}

	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){//屏幕被按下
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(x>274 && x<304 && y>15 && y<45){//点击了关闭按钮
				if(status == 1){
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					this.initData();
					return true;
				}
				gameView.setStatus(0);
			}
			if(x>212 && x<242 && y>15 && y<45){//点击了向下翻页按钮
				if(status == 0){//主界面
					selectI++;
					if(items1.length < yeSpan1){//当一个屏幕可以全部显示时，即不需要滚屏 
						if(selectI > items1.length-1){ 
							selectI = items1.length-1;
						}
					}
					else {//当一屏显示不全，需要滚屏时
						if(selectI > yeSpan1-1){ 
							selectI = yeSpan1-1;
							currentI++;
							if((currentI+yeSpan1) > items1.length){
								currentI--;
							}
						}						
					}						
				}
				else if(status == 1){//详细信息
					selectI++;
					if(selectI > items2.length-1){ 
						selectI = items2.length-1;
					}
				}
			}//end点击了向下翻页按钮
			else if(x>243 && x<273 && y>15 && y<45){//点击了向上翻页按钮
				selectI--;
				if(selectI < 0){
					selectI = 0;
					currentI--;
					if(currentI < 0){
						currentI = 0;
					}
				}	
			}
			
			if(status == 0){//主界面
				if(x>130 && x<190 && y>430 && y<460){//详细按钮
					if(cityList.size() == 0){
						return true;
					}
					selectCityDrawable = cityList.get((selectI+currentI));//得到选中的城池
					items2 = new String[][]{
						{"名称：", selectCityDrawable.getCityName(),"",""},
						{"等级：", selectCityDrawable.getLevel()+"","",""},
						{"兵力：", selectCityDrawable.getArmy()+"","剩余：",gameView.hero.getArmyWithMe()+""},
						{"粮草：", selectCityDrawable.getFood()+"", "剩余：",gameView.hero.getFood()+""},
						{"防御：", GameFormula.getCityDefence(selectCityDrawable)+"","",""},
						{"攻击：", GameFormula.getCityAttack(selectCityDrawable)+"","",""},
						{"居民：", selectCityDrawable.getCitizen()+"","","","",""},
						{"箭垛：", selectCityDrawable.getWarTower()+"","剩余：",gameView.hero.warTower+""},
						{"战车：", selectCityDrawable.getWarTank()+"","剩余：",gameView.hero.warTank+""	}
					};
//					selectI = 0;
//					currentI = 0;
					status = 1;
				}
			}
			else if(status == 1){//详细界面
				if(x>250 && x<265 && y>130 && y<145){//加兵力
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
				else if(x>270 && x<285 && y>130 && y<145){//减兵力
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
				else if(x>250 && x<265 && y>160 && y<175){//加粮草
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
				else if(x>270 && x<285 && y>160 && y<175){//减粮草
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
				else if(x>250 && x<265 && y>280 && y<295){//加箭垛
					if(gameView.hero.getWarTower()>0){
						this.selectCityDrawable.setWarTower(this.selectCityDrawable.getWarTower()+1);
						gameView.hero.setWarTower(gameView.hero.getWarTower()-1);
					}
					initItems2();
				}
				else if(x>270 && x<285 && y>280 && y<295){//减箭垛
					if(this.selectCityDrawable.getWarTower()>0){
						gameView.hero.setWarTower(gameView.hero.getWarTower()+1);
						this.selectCityDrawable.setWarTower(this.selectCityDrawable.getWarTower()-1);
					}
					initItems2();
				}
				else if(x>250 && x<265 && y>310 && y<325){//加战车
					if(gameView.hero.getWarTank()>0){
						this.selectCityDrawable.setWarTank(this.selectCityDrawable.getWarTank()+1);
						gameView.hero.setWarTank(gameView.hero.getWarTank()-1);
					}
					initItems2();
				}
				else if(x>270 && x<285 && y>310 && y<325){//减战车
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
		selectCityDrawable = cityList.get((selectI+currentI));//得到选中的城池
		items2 = new String[][]{
			{"名称：", selectCityDrawable.getCityName(),"",""},
			{"等级：", selectCityDrawable.getLevel()+"","",""},
			{"兵力：", selectCityDrawable.getArmy()+"","剩余：",gameView.hero.getArmyWithMe()+""},
			{"粮草：", selectCityDrawable.getFood()+"", "剩余：",gameView.hero.getFood()+""},
			{"防御：", GameFormula.getCityDefence(selectCityDrawable)+"","",""},
			{"攻击：", GameFormula.getCityAttack(selectCityDrawable)+"","",""},
			{"居民：", selectCityDrawable.getCitizen()+"","","","",""},
			{"箭垛：", selectCityDrawable.getWarTower()+"","剩余：",gameView.hero.warTower+""},
			{"战车：", selectCityDrawable.getWarTank()+"","剩余：",gameView.hero.warTank+""	}
		};
	}
}
