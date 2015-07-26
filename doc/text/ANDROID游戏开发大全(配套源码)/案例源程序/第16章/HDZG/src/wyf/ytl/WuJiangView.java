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
 * 武将情报 界面
 *
 */

public class WuJiangView{
	GameView gameView;
	
	int yeSpan1 = 9;//普通武将界面显示的个数
	int yeSpan2 = 9;//高级武将界面
	int yeSpan3 = 12;//详细信息界面
	int yeSpan4 = 7;//任免界面
	int yeSpan5 = 7;//任免界面
	int currentI = 0;//当前绘制的屏幕最上边的元素下标
	int selectI = 0;
	
	int startY = 120;
	int startX = 15;
	
	int status = 0;//显示的标记位，0-普通武将列表  1-高级武将列表 2-武将详细信息 3-会面 4-任免 5-指派
	General selectGeneral;//当前选中的武将
	ArrayList<General> totalGeneral;
	String[][] items1;//需要列出的武将
	String[][] items2;//需要列出的特殊武将 
	String[][] items3;//选中武将的详细信息
	String[] items4 = GENERAL_TITLE;//武将能任免的所有职位
	String[][] items5;//科研中的项目,孙子，墨子等
	String[] items6;//指派界面
	
	static Bitmap menuTitle;//表格的标题背景
	static Bitmap threeBitmap;//右上角的三个按钮
	static Bitmap panel_back;//背景图
	static Bitmap selectBackground;//选中后的背景
	static Bitmap buttonBackGround;//按钮背景
	static Bitmap upBitmap;//向上的小箭头
	static Bitmap downBitmap;//向下的小箭头
	static Bitmap logo;
	
	Paint paint;
	
	public WuJiangView(GameView gameView) {//构造器
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
		totalGeneral = gameView.hero.getTotalGeneral();//得到英雄所拥有的所有将领
		
		items1 = new String[totalGeneral.size()][4];
		for(int i=0; i<totalGeneral.size(); i++){
			items1[i][0] = totalGeneral.get(i).getName();//名称
			items1[i][1] = totalGeneral.get(i).getRank();//职位
			items1[i][2] = (totalGeneral.get(i).getCityDrawable()==null)?"跟随":totalGeneral.get(i).getCityDrawable().getCityName();//状态
			items1[i][3] = totalGeneral.get(i).getStrength()+"";//体力
		}
	}
	
	public void initZhiPai(){//初始化指派界面
		 ArrayList<CityDrawable> cityList = gameView.hero.getCityList();
		 items6 = new String[cityList.size()];
		 for(int i=0; i< cityList.size(); i++){
			 items6[i] = cityList.get(i).getCityName();
		 }
	}
	
	public void onDraw(Canvas canvas){//绘制的方法 
		canvas.drawBitmap(panel_back, 0, 0, paint);  
		canvas.drawBitmap(threeBitmap, 212, 15, paint);
		canvas.drawBitmap(logo, 15, 16, paint);//绘制logo
		paint.setTextSize(23);//设置文字大小
		canvas.drawText("武将情报", 50, 40, paint);
		paint.setTextSize(18);//设置文字大小
		
		if(status == 0 || status == 1){//此View中的主界面 
			canvas.drawBitmap(buttonBackGround, 15, 57, paint);
			canvas.drawBitmap(buttonBackGround, 85, 57, paint);
			canvas.drawText("普通", 27, 78, paint);
			canvas.drawText("高级", 97, 78, paint);
			
			paint.setTextSize(18);//设置文字大小
			if(status == 0){//普通武将界面
				canvas.drawBitmap(menuTitle, 10, 100, paint);
				canvas.drawText("名称", 15, 120, paint);
				canvas.drawText("职位", 90, 120, paint);
				canvas.drawText("状态", 170, 120, paint);
				canvas.drawText("体力", 240, 120, paint);
				
				int tempCurrentI = currentI;
				if(items1.length < yeSpan1){//一个屏幕可以显示下
					for(int i=0; i<items1.length; i++){
						canvas.drawText(items1[i][0], 15, startY + 35 + 30*i, paint);
						canvas.drawText(items1[i][1], 90, startY + 35 + 30*i, paint);
						canvas.drawText(items1[i][2], 170, startY + 35 + 30*i, paint);
						canvas.drawText(items1[i][3], 240, startY + 35 + 30*i, paint);
					}
				}
				else{//一个屏幕显示不下时
					for(int i=tempCurrentI; i<tempCurrentI+yeSpan1; i++){
						canvas.drawText(items1[i][0], 15, startY + 35 + 30*(i-tempCurrentI), paint);
						canvas.drawText(items1[i][1], 90, startY + 35 + 30*(i-tempCurrentI), paint);
						canvas.drawText(items1[i][2], 170, startY + 35 + 30*(i-tempCurrentI), paint);
						canvas.drawText(items1[i][3], 140, startY + 35 + 30*(i-tempCurrentI), paint);
					}
				}
				canvas.drawBitmap(selectBackground, 10, startY +35 + 30*selectI - 22, paint);//绘制选择效果
				
				for(int i=0; i<4; i++){//绘制下面四个按钮背景
					canvas.drawBitmap(buttonBackGround, 25+70*i, 430, paint);
				}
				canvas.drawText("详细", 35, 452, paint);//绘制下面四个按钮的文字
				canvas.drawText("任免", 105, 452, paint);
				canvas.drawText("会面", 175, 452, paint);
				canvas.drawText("指派", 245, 452, paint);	
				
				if(currentI != 0){//绘制小的向上箭头
					canvas.drawBitmap(upBitmap, 150, 80, paint);
				}
				if(items1.length>yeSpan1 && (currentI+yeSpan1) < items1.length){//绘制小的向下箭头
					canvas.drawBitmap(downBitmap, 150, 414, paint);
				}
			}
			else if(status == 1){//高级界面
				canvas.drawBitmap(menuTitle, 10, 100, paint);
				canvas.drawText("人物", 15, 120, paint);
				canvas.drawText("科研项目", 65, 120, paint);
				canvas.drawText("完成数量", 150, 120, paint);
				canvas.drawText("总共任务", 235, 120, paint);
				
				int tempCurrentI = currentI;
				if(items5.length < yeSpan2){//当一个屏幕可以显示全时
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
				
				if(currentI != 0){//绘制小的向上箭头
					canvas.drawBitmap(upBitmap, 150, 80, paint);
				}
				if(items5.length>yeSpan2 && (currentI+yeSpan2) < items5.length){//绘制小的向下箭头
					canvas.drawBitmap(downBitmap, 150, 414, paint);
				}
			}
		}
		else if(status == 2){//武将详细信息
			int tempCurrentI = currentI;
			if(items3.length < yeSpan3){//当一个屏幕可以显示全时
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
			
			if(currentI != 0){//绘制小的向上箭头
				canvas.drawBitmap(upBitmap, 150, 80, paint);
			}
			if(items3.length>yeSpan3 && (currentI+yeSpan3) < items3.length){//绘制小的向下箭头
				canvas.drawBitmap(downBitmap, 150, 414, paint);
			}
		}
		else if(status == 3){//会面时
			canvas.drawText("您与" + this.selectGeneral.getName()+"亲切交谈，" + "其深受感", 45, 130, paint);
			canvas.drawText("动，忠诚度恢复到100。", 25, 160, paint);
			this.selectGeneral.setLoyalty(100);//设置忠诚度
			canvas.drawBitmap(buttonBackGround, 235, 185, paint);//绘制确定按钮背景
			canvas.drawText("确定", 247, 207, paint);
		}
		else if(status == 4){//任免
			canvas.drawText(this.selectGeneral.getName()+"的当前职位为"+this.selectGeneral.getRank()+"，您需", 35, 85, paint);
			canvas.drawText("要其将职位改变为？", 15, 115, paint);
			
			int tempCurrentI = currentI;
			if(items4.length < yeSpan1){//一个屏幕可以显示下
				for(int i=0; i<items4.length; i++){
					canvas.drawText(items4[i], 120, startY + 35 + 30*i, paint);
				}
			}
			else{//一个屏幕显示不下时
				for(int i=tempCurrentI; i<tempCurrentI+yeSpan1; i++){
						canvas.drawText(items4[i], 120, startY + 35 + 30*(i-tempCurrentI), paint);
				}
			}
			
			canvas.drawBitmap(buttonBackGround, 128, 353, paint);//绘制确定按钮背景
			canvas.drawText("确定", 140, 375, paint);
			
			canvas.drawBitmap(selectBackground, 10, startY + 35 + 30*selectI - 22, paint);//绘制选择效果
			if(currentI != 0){//绘制小的向上箭头
				canvas.drawBitmap(upBitmap, 150, 80, paint);
			}
			if(items4.length>yeSpan4 && (currentI+yeSpan4) < items4.length){//绘制小的向下箭头
				canvas.drawBitmap(downBitmap, 150, 414, paint);
			}
		}
		else if(status == 5){//绘制指派界面
			if(this.selectGeneral.cityDrawable == null){
				canvas.drawText(this.selectGeneral.getName()+"的当前在跟随您，您需", 35, 85, paint);
				canvas.drawText("要其指派到？", 15, 115, paint);
			}
			else {
				canvas.drawText(this.selectGeneral.getName()+"的当前在"+this.selectGeneral.cityDrawable.getCityName()+"，您需", 35, 85, paint);
				canvas.drawText("要其指派到？", 15, 115, paint);				
			}

			int tempCurrentI = currentI;
			if(items6.length < yeSpan5){//一个屏幕可以显示下
				for(int i=0; i<items6.length; i++){
					canvas.drawText(items6[i], 120, startY + 35 + 30*i, paint);
				}
			}
			else{//一个屏幕显示不下时
				for(int i=tempCurrentI; i<tempCurrentI+yeSpan1; i++){
					canvas.drawText(items6[i], 120, startY + 35 + 30*(i-tempCurrentI), paint);
				}
			}
			
			canvas.drawBitmap(buttonBackGround, 128, 403, paint);//绘制确定按钮背景
			canvas.drawText("确定", 140, 425, paint);
			
			canvas.drawBitmap(selectBackground, 10, startY + 35 + 30*selectI - 22, paint);//绘制选择效果
			if(currentI != 0){//绘制小的向上箭头
				canvas.drawBitmap(upBitmap, 150, 80, paint);
			}
			if(items6.length>yeSpan5 && (currentI+yeSpan5) < items6.length){//绘制小的向下箭头
				canvas.drawBitmap(downBitmap, 150, 414, paint);
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){//屏幕被按下
			int x = (int) event.getX();
			int y = (int) event.getY();
			if(status == 0){//普通界面
				if(x>35 && x<95 && y>430 && y<460){//详细
					if(totalGeneral.size() == 0){
						return true;
					}
					selectGeneral=  totalGeneral.get((currentI+selectI));//得到选中的武将
					this.items3 = new String[][]
					     {
							{"名称：", selectGeneral.getName()},
							{"职位：", selectGeneral.getRank()},
							{"等级：", selectGeneral.getLevel()+""},
							{"忠诚度：",selectGeneral.getLoyalty()+""},
							{"统御力：",selectGeneral.getDefend()+""},
							{"武力：", selectGeneral.getPower()+""},
							{"智力：", selectGeneral.getIntelligence()+""},
							{"敏捷：", selectGeneral.getAgility()+""},
							{"体力：", selectGeneral.getStrength()+""},
							{"体力上限：",selectGeneral.getMaxStrength()+""}
					     };
					
					this.status = 2;
					this.selectI = 0;
					this.currentI = 0;
				}
				else if(x>105 && x<165 && y>430 && y<460){//任免
					if(totalGeneral.size() == 0){
						return true;
					}
					selectGeneral = totalGeneral.get((currentI+selectI));//得到选中的武将
					this.status = 4;
					this.selectI = 0;
					this.currentI = 0;
				}
				else if(x>175 && x<235 && y>430 && y<460){//会面
					if(totalGeneral.size() == 0){
						return true;
					}
					selectGeneral = totalGeneral.get((currentI+selectI));//得到选中的武将
					this.status = 3;
					this.selectI = 0;
					this.currentI = 0;
				}
				else if(x>245 && x<305 && y>430 && y<460){//指派
					if(totalGeneral.size() == 0){
						return true;
					}
					selectGeneral = totalGeneral.get((currentI+selectI));//得到选中的武将
					initZhiPai();
					this.status = 5;
					this.selectI = 0;
					this.currentI = 0;
				}
			}//end普通界面
			else if(status == 1){//高级界面

			}//end高级界面
			else if(status == 5){//指派界面
				if(x>128 && x<188 && y>403 && y<433){//指派界面中确定按钮
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
			else if(status == 3){//会面界面
				if(x>235 && x<295 && y>185 && y<215){//确定按钮
					this.status = 0;
					initData();
				}
			}
			else if(status == 4){//任免界面
				if(x>128 && x<188 && y>353 && y<383){//确定按钮
					this.selectGeneral.setRank((selectI+currentI));
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					initData();
				}
			}
			
			if(x>20 && x<70 && y>60 && y<80){//点击普通按钮
				if(status == 1){//在高级界面普通按钮才可用
				 	this.status = 0;
				 	this.selectI = 0;
					this.currentI = 0;					
				}
			}
			else if(x>85 && x<140 && y>60 && y<80){//点击高级按钮
				if(status == 0){//在普通界面
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
			
			if(x>212 && x<242 && y>15 && y<45){//点击了向下翻页按钮
				if(status == 0){//普通武将时 
					if(items1 == null || items1.length == 0){
						return true;
					}
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
				else if(status == 1){//高级显示时
					if(items2 == null || items2.length == 0){
						return true;
					}
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
				else if(status == 2){//详细信息
					selectI++;
					if(items3.length < yeSpan3){//当一个屏幕可以全部显示时，即不需要滚屏 
						if(selectI > items3.length-1){ 
							selectI = items3.length-1;
						}
					}
					else {//当一屏显示不全，需要滚屏时
						if(selectI > yeSpan3-1){ 
							selectI = yeSpan3-1;
							currentI++;
							if((currentI+yeSpan3) > items3.length){
								currentI--;
							}
						}						
					}
				}
				else if(status == 4){//任免时
					selectI++;
					if(items4.length < yeSpan4){//当一个屏幕可以全部显示时，即不需要滚屏 
						if(selectI > items4.length-1){ 
							selectI = items4.length-1;
						}
					}
					else {//当一屏显示不全，需要滚屏时
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
			else if(x>274 && x<304 && y>15 && y<45){//点击了关闭按钮
				if(this.status == 2){//在详细界面时应该回武将列表
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					return true;
				}
				else if(status == 3){//当在会面时也返回到武将列表
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					return true;
				}
				else if(status == 4){//当在任免页面时
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					return true;
				}
				else if(status == 1){//在高级界面
					this.status = 0;
					this.selectI = 0;
					this.currentI = 0;
					return true;
				}
				else if(status == 5){//在指派界面
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