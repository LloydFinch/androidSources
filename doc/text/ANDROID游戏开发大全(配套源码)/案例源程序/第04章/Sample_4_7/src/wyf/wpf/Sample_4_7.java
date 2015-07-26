package wyf.wpf;			//声明包语句
import android.app.Activity;		//引入相关类
import android.hardware.Sensor;		//引入相关类
import android.hardware.SensorEvent;	//引入相关类
import android.hardware.SensorEventListener;	//引入相关类
import android.hardware.SensorManager;	//引入相关类
import android.os.Bundle;	//引入相关类
import android.widget.TextView;	//引入相关类
//继承自Activity的子类
public class Sample_4_7 extends Activity {
	SensorManager mySensorManager;		//SensorManager对象引用
	TextView tvX;	//TextView对象引用	
	TextView tvY;	//TextView对象引用	
	TextView tvZ;	//TextView对象引用	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);		//获得屏幕上TextView控件的引用
        tvY = (TextView)findViewById(R.id.tvY);		//获得屏幕上TextView控件的引用
        tvZ = (TextView)findViewById(R.id.tvZ);		//获得屏幕上TextView控件的引用
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	//获得SensorManager对象
    }
	@Override
	protected void onResume() {						//重写onResume方法
		mySensorManager.registerListener(//调用方法为SensorManager注册监听器
				mySensorEventListener,			//实现了SensorEventListener接口的监听器对象
				mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),	//Sensor对象
				SensorManager.SENSOR_DELAY_UI		//系统传递SensorEvent事件的频度
				);
		super.onResume();
	}	
	@Override
	protected void onPause() {						//重写onPause方法
		mySensorManager.unregisterListener(mySensorEventListener);	//取消注册监听器
		super.onPause();
	}
	//开发实现了SensorEventListener接口的传感器监听器
	private SensorEventListener mySensorEventListener = new SensorEventListener(){
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {//重写onAccuracyChanged方法，在此为空实现
		}
		@Override
		public void onSensorChanged(SensorEvent event) {
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){//判断是否为加速度传感器变化产生的数据
				float [] values = event.values;		//获得捕获的数据
				tvX.setText("x轴方向上的加速度为："+values[0]);		//将提取的x方向上加速度显示到TextView
				tvY.setText("y轴方向上的加速度为："+values[1]);		//将提取的y方向上加速度显示到TextView
				tvZ.setText("z轴方向上的加速度为："+values[2]);		//将提取的z方向上加速度显示到TextView
			}			
		}		
	};
}