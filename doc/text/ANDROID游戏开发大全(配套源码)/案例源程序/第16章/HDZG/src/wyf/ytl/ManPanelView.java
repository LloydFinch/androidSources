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
	
	int yeSpan = 11;//每页显示的个数
	int yeSpan2 = 10;//每页显示的个数
	
	int currentI = 0;//当前绘制的屏幕最上边的元素下标
	int selectI = 0;
	
	int startY = 120;
	int startX = 25;
	
	int status = 0;//显示的标记位，0-属性列表  1-技能列表
	
	String[][] items1;//需要列出的属性 
	String[][] items2;//需要列出的技能 
	
	static Bitmap threeBitmap;//右上角的三个按钮
	static Bitmap panel_back;//背景图
	static Bitmap selectBackground;//选中后的背景
	static Bitmap buttonBackGround;//按钮背景
	static Bitmap upBitmap;//向上的小箭头
	static Bitmap downBitmap;//向下的小箭头
	static Bitmap menuTitle;//表格的标题背景
	static Bitmap logo;
	
	Paint paint;
	public ManPanelView(GameView gameView) {
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
		menu_item = null;//释放掉大图
		panel_back = BitmapFactory.decodeResource(r, R.drawable.panel_back);
		logo = BitmapFactory.decodeResource(r, R.drawable.logo);
		selectBackground = BitmapFactory.decodeResource(r, R.drawable.select_back);
		menuTitle = BitmapFactory.decodeResource(r, R.drawable.menu_title);//标题背景
	}
	
	public void initData(){//初始化数据
		//初始化人物属性
		this.items1 = new String[][]
		{
			{"名称:", gameView.hero.getName()},
			{"等级:", gameView.hero.getLevel()+" 级"},
			{"官衔:", gameView.hero.getTitle()},
			{"将军:", gameView.hero.getGeneralNumber()+" 个"},
			{"城池:", gameView.hero.getCityList().size()+" 个"},
			{"黄金:", gameView.hero.getTotalMoney()+" 金"},
			{"粮草:", gameView.hero.getTotalFood()+" 石"},
			{"兵力:", gameView.hero.getTotalArmy()+""},
			{"总人口:", gameView.hero.getTotalCitizen()+" 人"},
			{"投石车:", gameView.hero.getTotalWarTank()+" 个"},
			{"箭垛:", gameView.hero.getTotalWarTower()+" 个"},
		};
		
		//初始化技能
		HashMap<Integer,Skill> heroSkill = gameView.hero.getHeroSkill();//得到英雄的所有技能
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
	
	public void onDraw(Canvas canvas){//绘制的方法 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//绘制logo
		paint.setTextSize(23);//设置文字大小
		canvas.drawText("个人属性", 50, 40, paint);
		paint.setTextSize(18);//设置文字大小
		
		canvas.drawBitmap(buttonBackGround, 15, 57, paint);
		canvas.drawBitmap(buttonBackGround, 85, 57, paint);
		canvas.drawText("属性", 27, 78, paint);
		canvas.drawText("技能", 97, 78, paint);
		
		if(status == 0){//画属性时
			int tempCurrentI = currentI;
			if(items1.length < yeSpan){//当一个屏幕可以显示全时
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
			if(tempCurrentI != 0){//绘制小的向上箭头
				canvas.drawBitmap(upBitmap, 150, 85, paint);
			}
			if(items1.length>yeSpan && (tempCurrentI+yeSpan) < items1.length){//绘制小的向下箭头
				canvas.drawBitmap(downBitmap, 150, 455, paint);
			}
		}
		else if(status == 1){//绘制技能
			canvas.drawBitmap(menuTitle, 10, 100, paint);
			canvas.drawText("技能名", 15, 120, paint);
			canvas.drawText("等级", 125, 120, paint);
			canvas.drawText("消耗", 240, 120, paint);
			
			int tempCurrentI = currentI;
			if(items2.length < yeSpan2){//当一个屏幕可以显示全时
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
			if(tempCurrentI != 0){//绘制小的向上箭头
				canvas.drawBitmap(upBitmap, 150, 85, paint);
			}
			if(items2.length>yeSpan2 && (tempCurrentI+yeSpan2) < items2.length){//绘制小的向下箭头
				canvas.drawBitmap(downBitmap, 150, 455, paint);
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//屏幕被按下
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(y>15 && y<45){
				if(x>212 && x<242){//点击了向下翻页按钮
					if(status == 0){//人物属性时 
						selectI++;
						if(items1.length < yeSpan){//当一个屏幕可以全部显示时，即不需要滚屏 
							if(selectI > items1.length-1){ 
								selectI = items1.length-1;
							}
						}
						else {//当一屏显示不全，需要滚屏时
							if(selectI > yeSpan-1){ 
								selectI = yeSpan-1;
								currentI++;
								if((currentI+yeSpan) > items1.length){
									currentI--;
								}
							}						
						}						
					}
					else if(status == 1){//技能显示时
						selectI++;
						if(items2.length < yeSpan2){//当一个屏幕可以全部显示时，即不需要滚屏 
							if(selectI > items2.length-1){ 
								selectI = items2.length-1;
							}
						}
						else {//当一屏显示不全，需要滚屏时
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
				else if(x>243 && x<273){//点击了向上翻页按钮
					selectI--;
					if(selectI < 0){
						selectI = 0;
						currentI--;
						if(currentI < 0){
							currentI = 0;
						}
					}					
				}
				else if(x>274 && x<304){//点击了关闭按钮
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					gameView.setStatus(0);
				}
			}
			else if(y>57 && y<57+buttonBackGround.getHeight() && x>15 && x<15 + buttonBackGround.getWidth()){//点击了属性按钮
				this.status = 0;
				this.selectI = 0;
				this.currentI = 0;
			}
			else if(y>57 && y<57+buttonBackGround.getHeight() && x>85 && x<85 + buttonBackGround.getWidth()){//点击了技能按钮
				this.status = 1;
				this.selectI = 0;
				this.currentI = 0;
			}
		}
		return true;
	}
}
