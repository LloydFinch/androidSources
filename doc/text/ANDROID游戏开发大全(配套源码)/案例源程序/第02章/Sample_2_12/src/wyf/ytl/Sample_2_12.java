package wyf.ytl;
import java.io.IOException;//���������
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
public class Sample_2_12 extends Activity implements SurfaceHolder.Callback{
	Camera myCamera;//Camera������
	SurfaceView mySurfaceView;//SurfaceView������
	SurfaceHolder mySurfaceHolder;//SurfaceHolder������
	Button button1;//��ť������
	Button button2;
	boolean isPreview = false;//�Ƿ��������
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mySurfaceView = (SurfaceView) findViewById(R.id.surfaceView);//�õ�SurfaceView������
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);//�õ�������ť��Ӧ��
        mySurfaceHolder = mySurfaceView.getHolder();//���SurfaceHolder
        mySurfaceHolder.addCallback(this);
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        button1.setOnClickListener(new Button.OnClickListener(){//�򿪵İ�ť����
			public void onClick(View arg0) {
				initCamera();
			}
        });
        button2.setOnClickListener(new Button.OnClickListener(){//�رյİ�ť����
			public void onClick(View arg0) {
				if(myCamera != null && isPreview){//������ʾʱ
					myCamera.stopPreview();
					myCamera.release();
					myCamera = null;
					isPreview = false;
				}
			}
        });        
    }
    public void initCamera(){//��ʼ�������Դ
    	if(!isPreview){
    		myCamera = Camera.open();
    	}
    	if(myCamera != null && !isPreview){
            try {
    			myCamera.setPreviewDisplay(mySurfaceHolder);
    			myCamera.startPreview();//��������Preview
    		} catch (IOException e) {
    			e.printStackTrace();//��ӡ������Ϣ
    		}  	
    		isPreview = true;
    	}
    }
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	public void surfaceCreated(SurfaceHolder holder) {
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}