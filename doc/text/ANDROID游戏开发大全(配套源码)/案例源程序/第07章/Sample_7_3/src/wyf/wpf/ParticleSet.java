package wyf.wpf;		//声明包语句
import java.util.ArrayList;		//引入相关类
import android.graphics.Color;	//引入相关类

public class ParticleSet{
	ArrayList<Particle> particleSet;		//用于存放Particle对象的集合
	//构造器，初始化粒子集合
	public ParticleSet(){
		particleSet = new ArrayList<Particle>();
	}
	//方法：向粒子集合中添加指定个数的粒子对象
	public void add(int count, double startTime){
		for(int i=0; i<count; i++){		//创建count个数的Particle对象
			int tempColor = this.getColor(i);	//获得粒子颜色
			int tempR = 1;		//粒子半径
			double tempv_v = 0;	//粒子竖直方向上速度设置为零
			double tempv_h = 20 + 10*(Math.random());	//随机产生粒子水平方向上速度
			int tempX = 50;	//粒子的X坐标是固定的
			int tempY = (int)(50 - 10*(Math.random()));	//随机产生粒子Y坐标
			//创建Particle对象
			Particle particle = new Particle(tempColor, tempR, tempv_v, tempv_h, tempX, tempY, startTime);
			particleSet.add(particle);//将创建好的Particle对象添加到列表中
		}
	}
	//方法：获取指定索引的颜色
	public int getColor(int i){
		int color = Color.RED;
		switch(i%4){	//对i进行分支判断
		case 0:
			color = Color.RED;	//将颜色设为红色
			break;
		case 1:
			color = Color.GREEN;//将颜色设为绿色
			break;
		case 2:
			color = Color.YELLOW;//将颜色设为黄色
			break;
		case 3:
			color = Color.GRAY;//将颜色设为灰色
			break;
		}
		return color;		//返回得到的颜色
	}
}