package wyf.wpf;		//���������
import android.app.Activity;		//���������
import android.os.Bundle;			//���������
import android.view.Window;			//���������
import android.view.WindowManager;	//���������
//�̳���Activity������
public class Sample_7_3 extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {		//��дonCreate����
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);		//���ò���ʾ����
        getWindow().setFlags(									//����Ϊȫ��ģʽ
        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ParticleView lz = new ParticleView(this);		//����һ��LiZiView����
        setContentView(lz);								//����Ļ����ΪParticleView����
    }
}