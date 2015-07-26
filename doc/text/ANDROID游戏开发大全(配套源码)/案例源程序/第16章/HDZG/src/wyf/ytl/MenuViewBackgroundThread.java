package wyf.ytl;

public class MenuViewBackgroundThread extends Thread{
	private int sleepSpan = 100;//˯�ߵĺ�����
	private boolean flag = true;//ѭ����־λ
	private int span = 1;//ͼƬ�ƶ��Ĳ���
	MenuView menuView;
	
	public MenuViewBackgroundThread(MenuView menuView){//������
		super.setName("==MenuView.MenuViewBackgroundThread");
		this.menuView = menuView;
	}
	public void setFlag(boolean flag){//���ñ��λ
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
				Thread.sleep(sleepSpan);//˯��
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
