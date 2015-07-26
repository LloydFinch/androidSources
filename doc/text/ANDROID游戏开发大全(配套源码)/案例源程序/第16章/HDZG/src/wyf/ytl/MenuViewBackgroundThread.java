package wyf.ytl;

public class MenuViewBackgroundThread extends Thread{
	private int sleepSpan = 100;//睡眠的毫秒数
	private boolean flag = true;//循环标志位
	private int span = 1;//图片移动的步长
	MenuView menuView;
	
	public MenuViewBackgroundThread(MenuView menuView){//构造器
		super.setName("==MenuView.MenuViewBackgroundThread");
		this.menuView = menuView;
	}
	public void setFlag(boolean flag){//设置标记位
		this.flag = flag;
	}
	public void run(){ 
		while(flag){
			menuView.backGroundIX -= span;
			if(menuView.backGroundIX <-ConstantUtil.PICTUREWIDTH){
				menuView.i = (menuView.i+1)%ConstantUtil.PICTURECOUNT;
				menuView.backGroundIX+=ConstantUtil.PICTUREWIDTH;
			}
			try{
				Thread.sleep(sleepSpan);//睡眠
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
