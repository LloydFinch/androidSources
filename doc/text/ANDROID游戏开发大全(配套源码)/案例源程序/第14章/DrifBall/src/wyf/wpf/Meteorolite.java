package wyf.wpf;			//声明包语句
/*
 * 该类代表陨石对象，将陨石的坐标，当前帧号封装起来
 * 有一个成员方法负责初始化陨石的信息
 */
public class Meteorolite{
	int x;				//陨石x坐标
	int y;				//陨石y坐标
	boolean up;			//为true则在地图图层上，为false则在地图图层下
	int index;			//旋转角度
	public Meteorolite(){	//构造器，调用init方法进行初始化
		init();
	}		
	public void init(){//方法：初始化成员变量
		x = (int)(Math.random()*290);		 	//随机产生X坐标	
		y = -(int)(Math.random()*250);			//随机产生Y坐标
		up = (Math.random()>0.5?true:false);	//随机设置是否在上层的标志位
		index = (int)(Math.random()*8);			//随机产生陨石动画的帧索引	
	}
}