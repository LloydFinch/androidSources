package wyf.wpf;		//声明包语句
import java.util.ArrayList;	//引入相关类
//继承自Thread的类，主要功能是添加粒子并修改粒子的位置
public class ParticleThread extends Thread{
	boolean flag;		//线程执行标志位
	ParticleView father;		//ParticleView对象引用
	int sleepSpan = 80;		//线程休眠时间
	double time = 0;		//物理引擎的时间轴
	double span = 0.15;		//每次计算粒子的位移时采用的时间间隔
	//ParticleThread类的构造器
	public ParticleThread(ParticleView father){
		this.father = father;
		this.flag = true;		//设置线程执行标志位为true
	}
	//线程的执行方法
	public void run(){
		while(flag){
			father.ps.add(5, time);	//每次添加10个粒子
			ArrayList<Particle> tempSet = father.ps.particleSet;//获取粒子集合
			int count = tempSet.size();		//记录粒子集合的大小
			
			for(int i=0; i<count; i++){		//遍历粒子集合，修改其轨迹
				Particle particle = tempSet.get(i);
				double timeSpan = time - particle.startTime;	//计算从程序开始到现在经过的时间间隔
				int tempx = (int)(particle.startX+particle.horizontal_v*timeSpan);	//计算出粒子的X坐标
				int tempy = (int)(particle.startY + 4.9*timeSpan*timeSpan + particle.vertical_v*timeSpan);
				if(tempy>ParticleView.DIE_OUT_LINE){						//如果超过屏幕下边沿
					tempSet.remove(particle);		//从粒子集合冲移除该Particle对象
					count = tempSet.size();			//重新设置粒子个数
				}
				particle.x = tempx;					//修改粒子的X坐标
				particle.y = tempy;					//修改粒子的Y坐标
			}
			time += span;								//将时间延长
			try{
				Thread.sleep(sleepSpan);					//休眠一段时间
			}
			catch(Exception e){
				e.printStackTrace();				//捕获并打印异常
			}
		}
	}
}