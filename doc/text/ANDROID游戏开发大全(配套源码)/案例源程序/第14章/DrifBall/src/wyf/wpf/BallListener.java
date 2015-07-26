package wyf.wpf;				//声明包语句 
import android.hardware.SensorListener;//引入相关类
import android.hardware.SensorManager;//引入相关类
/*
 * 该类继承自SensorListener，主要功能是感应传感器的姿态数据
 * 通过onSensorChanged方法读取数据,并调用RoatatUtil中的静态
 * 方法来确定小球应该移动的方向。
 */
public class BallListener implements SensorListener{
	DriftBall father;		//DriftBall引用
	int timeSpan = 500;		//500MS检查一次
	long startTime;			//记录开始的时间
	
	public BallListener(DriftBall father){		//构造器，初始化成员变量
		this.father = father;
		startTime = System.currentTimeMillis();	//记录系统时间作为当前时间
	}
	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {//重写onAccuracyChanged方法		
	}
	@Override
	public void onSensorChanged(int sensor, float[] values) {//重写onSensorChanged方法 
		System.out.println("===========================");
		
		long now = System.currentTimeMillis();		//获取系统当前时间
		if(now - startTime >= timeSpan){			//判断是否走过指定的时间间隔
			if(sensor == SensorManager.SENSOR_ORIENTATION){
				analysisData(values);		//调用analysisData方法对数据进行分析
			}	
			startTime = now;				//重新计时
		}
	}	
	public void analysisData(float[] values) {		//对读取的姿态数据进行分析，调用RoateUtil的静态方法解析出方向
		double[] valuesTemp=new double[]{values[0],-values[1],values[2]};	//对y轴进行修正
		father.gv.direction = RotateUtil.getDirectionCase(valuesTemp);	//调用RotateUtil的静态方法计算出小球方向
	}	
}