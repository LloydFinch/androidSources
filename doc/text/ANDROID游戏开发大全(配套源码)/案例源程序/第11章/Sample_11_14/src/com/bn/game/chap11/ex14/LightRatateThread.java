package com.bn.game.chap11.ex14;
public class LightRatateThread extends Thread {
	private int timeSpan=100;
	MyGLView myGLView;
	private boolean flag=true;
	public LightRatateThread(MyGLView myGLView){
		this.myGLView=myGLView;
	}
	@Override
	public void run(){
		while(flag){
			myGLView.angdegLight+=3;
			
			try {
				Thread.sleep(timeSpan);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
