package wyf.ytl;
import android.content.Context;//���������
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
public class Sample_5_4 extends MapActivity {
	MapView myMapView = null;//����MapView������
	MapController myMapController = null;//����myMapController������
	LocationManager mLocationManager = null;
	double jingDu = 116.46; 
	double weidu = 39.92;
    public final LocationListener mLocationListener = new LocationListener(){
		public void onLocationChanged(Location location){
	    	weidu = location.getLatitude();
	    	jingDu = location.getLongitude();
	    	setGeoPoint();//���õ�ͼ�ľ�γ��
		}
		public void onProviderDisabled(String provider){//��ʵ��
		}
		public void onProviderEnabled(String provider){//��ʵ��
		}
		public void onStatusChanged(String provider, int status, Bundle extras){//��ʵ��
		}
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myMapView = (MapView) this.findViewById(R.id.myMapView);//�õ�myMapView������
        myMapController = myMapView.getController();//���MapController
        setGeoPoint();//���õ�ͼ�ľ�γ��
        //ȡ��ϵͳLOCATION����
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //����Location����ʱ���¼�,����MapView
        mLocationManager.requestLocationUpdates(mLocationManager.GPS_PROVIDER, 2000, 10, mLocationListener);
    }
    public void setGeoPoint(){//���õ�ͼ�ľ�γ�ȵķ���
		GeoPoint gp = new GeoPoint((int)(weidu*1E6), (int)(jingDu*1E6));
		myMapController.animateTo(gp);//���þ�γ��
		myMapController.setZoom(18);//���÷Ŵ�ȼ�
    }
	protected boolean isRouteDisplayed() {
		return false;
	}
}