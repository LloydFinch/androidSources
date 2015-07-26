package wyf.ytl;
import android.content.Context;//引入相关类
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
public class Sample_5_4 extends MapActivity {
	MapView myMapView = null;//声明MapView的引用
	MapController myMapController = null;//声明myMapController的引用
	LocationManager mLocationManager = null;
	double jingDu = 116.46; 
	double weidu = 39.92;
    public final LocationListener mLocationListener = new LocationListener(){
		public void onLocationChanged(Location location){
	    	weidu = location.getLatitude();
	    	jingDu = location.getLongitude();
	    	setGeoPoint();//设置地图的经纬度
		}
		public void onProviderDisabled(String provider){//空实现
		}
		public void onProviderEnabled(String provider){//空实现
		}
		public void onStatusChanged(String provider, int status, Bundle extras){//空实现
		}
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myMapView = (MapView) this.findViewById(R.id.myMapView);//得到myMapView的引用
        myMapController = myMapView.getController();//获得MapController
        setGeoPoint();//设置地图的经纬度
        //取得系统LOCATION服务
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //监听Location更改时的事件,更新MapView
        mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 2000, 10, mLocationListener);
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