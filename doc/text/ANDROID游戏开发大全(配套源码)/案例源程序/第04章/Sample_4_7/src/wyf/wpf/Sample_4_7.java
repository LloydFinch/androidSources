package wyf.wpf;			//���������
import android.app.Activity;		//���������
import android.hardware.Sensor;		//���������
import android.hardware.SensorEvent;	//���������
import android.hardware.SensorEventListener;	//���������
import android.hardware.SensorManager;	//���������
import android.os.Bundle;	//���������
import android.widget.TextView;	//���������
//�̳���Activity������
public class Sample_4_7 extends Activity {
	SensorManager mySensorManager;		//SensorManager��������
	TextView tvX;	//TextView��������	
	TextView tvY;	//TextView��������	
	TextView tvZ;	//TextView��������	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvX = (TextView)findViewById(R.id.tvX);		//�����Ļ��TextView�ؼ�������
        tvY = (TextView)findViewById(R.id.tvY);		//�����Ļ��TextView�ؼ�������
        tvZ = (TextView)findViewById(R.id.tvZ);		//�����Ļ��TextView�ؼ�������
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	//���SensorManager����
    }
	@Override
	protected void onResume() {						//��дonResume����
		mySensorManager.registerListener(//���÷���ΪSensorManagerע�������
				mySensorEventListener,			//ʵ����SensorEventListener�ӿڵļ���������
				mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),	//Sensor����
				SensorManager.SENSOR_DELAY_UI		//ϵͳ����SensorEvent�¼���Ƶ��
				);
		super.onResume();
	}	
	@Override
	protected void onPause() {						//��дonPause����
		mySensorManager.unregisterListener(mySensorEventListener);	//ȡ��ע�������
		super.onPause();
	}
	//����ʵ����SensorEventListener�ӿڵĴ�����������
	private SensorEventListener mySensorEventListener = new SensorEventListener(){
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {//��дonAccuracyChanged�������ڴ�Ϊ��ʵ��
		}
		@Override
		public void onSensorChanged(SensorEvent event) {
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){//�ж��Ƿ�Ϊ���ٶȴ������仯����������
				float [] values = event.values;		//��ò��������
				tvX.setText("x�᷽���ϵļ��ٶ�Ϊ��"+values[0]);		//����ȡ��x�����ϼ��ٶ���ʾ��TextView
				tvY.setText("y�᷽���ϵļ��ٶ�Ϊ��"+values[1]);		//����ȡ��y�����ϼ��ٶ���ʾ��TextView
				tvZ.setText("z�᷽���ϵļ��ٶ�Ϊ��"+values[2]);		//����ȡ��z�����ϼ��ٶ���ʾ��TextView
			}			
		}		
	};
}