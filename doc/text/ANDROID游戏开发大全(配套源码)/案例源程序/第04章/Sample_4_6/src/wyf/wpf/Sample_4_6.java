package wyf.wpf;			//���������
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
import android.app.Activity;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
//�̳���Activity������
public class Sample_4_6 extends Activity {
//	SensorManager mySensorManager;		//SensorManager��������
	SensorManagerSimulator mySensorManager;		//ʹ��SensorSimulatorģ��ʱ����SensorSensorManager�������õķ���
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
//        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);	//���SensorManager����
      //ʹ��SensorSimulatorģ��ʱ����SensorSensorManager�������õķ���
        mySensorManager = SensorManagerSimulator.getSystemService(this, SENSOR_SERVICE);
        mySensorManager.connectSimulator();
    }
	@Override
	protected void onResume() {						//��дonResume����
		mySensorManager.registerListener(			//ע�������
				mySensorListener, 					//����������
				SensorManager.SENSOR_ACCELEROMETER,	//����������
				SensorManager.SENSOR_DELAY_UI		//�������¼����ݵ�Ƶ��
				);
		super.onResume();
	}	
	@Override
	protected void onPause() {									//��дonPause����
		mySensorManager.unregisterListener(mySensorListener);	//ȡ��ע�������
		super.onPause();
	}
	//����ʵ����SensorEventListener�ӿڵĴ�����������
	private SensorListener mySensorListener = new SensorListener(){
		@Override
		public void onAccuracyChanged(int sensor, int accuracy) {	//��дonAccuracyChanged����
		}
		@Override
		public void onSensorChanged(int sensor, float[] values) {	//��дonSensorChanged����
			if(sensor == SensorManager.SENSOR_ACCELEROMETER){		//�ж��Ƿ�Ϊ���ٶȴ������仯����������
				tvX.setText("x�᷽���ϵļ��ٶ�Ϊ��"+values[0]);		//����ȡ��������ʾ��TextView
				tvY.setText("y�᷽���ϵļ��ٶ�Ϊ��"+values[1]);		//����ȡ��������ʾ��TextView
				tvZ.setText("z�᷽���ϵļ��ٶ�Ϊ��"+values[2]);		//����ȡ��������ʾ��TextView
			}	
		}		
	};
}