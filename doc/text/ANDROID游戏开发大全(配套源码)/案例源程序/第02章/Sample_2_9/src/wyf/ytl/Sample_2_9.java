package wyf.ytl;
import android.app.Activity;	//����Activity��
import android.os.Bundle;//����Bundle��
import android.view.animation.Animation;//����Animation��
import android.view.animation.AnimationUtils;//����AnimationUtils��
import android.widget.ImageView;//����ImageView��
public class Sample_2_9 extends Activity {
	Animation myAnimation;//����������
	ImageView myImageView;//ImageView������
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {//��д��onCreate�ص�����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ��ʾ��View
        myAnimation= AnimationUtils.loadAnimation(this,R.anim.myanim);//���ض���
        myImageView = (ImageView) this.findViewById(R.id.myImageView);//�õ�ImageView������
        myImageView.startAnimation(myAnimation);//��������
    }    
}