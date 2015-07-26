package wyf.ytl;
import java.io.IOException;//引入相关类
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
public class Sample_2_12 extends Activity implements SurfaceHolder.Callback{
	Camera myCamera;//Camera的引用
	SurfaceView mySurfaceView;//SurfaceView的引用
	SurfaceHolder mySurfaceHolder;//SurfaceHolder的引用
	Button button1;//按钮的引用
	Button button2;
	boolean isPreview = false;//是否在浏览中
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mySurfaceView = (SurfaceView) findViewById(R.id.surfaceView);//得到SurfaceView的引用
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);//得到两个按钮的应用
        mySurfaceHolder = mySurfaceView.getHolder();//获得SurfaceHolder
        mySurfaceHolder.addCallback(this);
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        button1.setOnClickListener(new Button.OnClickListener(){//打开的按钮监听
			public void onClick(View arg0) {
				initCamera();
			}
        });
        button2.setOnClickListener(new Button.OnClickListener(){//关闭的按钮监听
			public void onClick(View arg0) {
				if(myCamera != null && isPreview){//正在显示时
					myCamera.stopPreview();
					myCamera.release();
					myCamera = null;
					isPreview = false;
				}
			}
        });        
    }
    public void initCamera(){//初始化相机资源
    	if(!isPreview){
    		myCamera = Camera.open();
    	}
    	if(myCamera != null && !isPreview){
            try {
    			myCamera.setPreviewDisplay(mySurfaceHolder);
    			myCamera.startPreview();//立即运行Preview
    		} catch (IOException e) {
    			e.printStackTrace();//打印错误信息
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