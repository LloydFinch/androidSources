package wyf.ytl;
import android.os.Bundle;//������Bundle��
import android.view.View;//������View��
import android.widget.Button;//������Button��
import android.widget.EditText;//������EditText��
import com.google.android.maps.GeoPoint;//������GeoPoint��
import com.google.android.maps.MapActivity;//������MapActivity��
import com.google.android.maps.MapController;//������MapController��
import com.google.android.maps.MapView;//������MapView��
public class Sample_5_3 extends MapActivity {
	MapView myMapView = null;//����MapView������
	MapController myMapController = null;//����myMapController������
	Button button1 = null;//����Button������
	EditText editView1 = null;//����EditText������
	EditText editView2 = null;//����EditText������
	double jingDu = 116.46;
	double weidu = 39.92;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myMapView = (MapView) this.findViewById(R.id.myMapView);//�õ�myMapView������
        button1 = (Button) this.findViewById(R.id.button1);//�õ�button1������
        editView1 = (EditText) this.findViewById(R.id.editView1);//�õ�editView1������
        editView2 = (EditText) this.findViewById(R.id.editView2);//�õ�editView2������
        myMapController = myMapView.getController();//���MapController
        setGeoPoint();//���õ�ͼ�ľ�γ��
        button1.setOnClickListener(new Button.OnClickListener() {//��Ӽ���
			public void onClick(View v) {
				jingDu= Double.parseDouble(editView1.getText().toString());//ȡ��editView1�е�����
				weidu = Double.parseDouble(editView2.getText().toString());
				setGeoPoint();//���õ�ͼ�ľ�γ��
			}
		});
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