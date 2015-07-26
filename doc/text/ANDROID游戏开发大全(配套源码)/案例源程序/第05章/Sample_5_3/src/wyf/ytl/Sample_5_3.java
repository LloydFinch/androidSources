package wyf.ytl;
import android.os.Bundle;//引入相Bundle类
import android.view.View;//引入相View类
import android.widget.Button;//引入相Button类
import android.widget.EditText;//引入相EditText类
import com.google.android.maps.GeoPoint;//引入相GeoPoint类
import com.google.android.maps.MapActivity;//引入相MapActivity类
import com.google.android.maps.MapController;//引入相MapController类
import com.google.android.maps.MapView;//引入相MapView类
public class Sample_5_3 extends MapActivity {
	MapView myMapView = null;//声明MapView的引用
	MapController myMapController = null;//声明myMapController的引用
	Button button1 = null;//声明Button的引用
	EditText editView1 = null;//声明EditText的引用
	EditText editView2 = null;//声明EditText的引用
	double jingDu = 116.46;
	double weidu = 39.92;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myMapView = (MapView) this.findViewById(R.id.myMapView);//得到myMapView的引用
        button1 = (Button) this.findViewById(R.id.button1);//得到button1的引用
        editView1 = (EditText) this.findViewById(R.id.editView1);//得到editView1的引用
        editView2 = (EditText) this.findViewById(R.id.editView2);//得到editView2的引用
        myMapController = myMapView.getController();//获得MapController
        setGeoPoint();//设置地图的经纬度
        button1.setOnClickListener(new Button.OnClickListener() {//添加监听
			public void onClick(View v) {
				jingDu= Double.parseDouble(editView1.getText().toString());//取得editView1中的数据
				weidu = Double.parseDouble(editView2.getText().toString());
				setGeoPoint();//设置地图的经纬度
			}
		});
    }
    public void setGeoPoint(){//设置地图的经纬度的方法
		GeoPoint gp = new GeoPoint((int)(weidu*1E6), (int)(jingDu*1E6));
		myMapController.animateTo(gp);//设置经纬度
		myMapController.setZoom(18);//设置放大等级
    }
	protected boolean isRouteDisplayed() {
		return false;
	}
}