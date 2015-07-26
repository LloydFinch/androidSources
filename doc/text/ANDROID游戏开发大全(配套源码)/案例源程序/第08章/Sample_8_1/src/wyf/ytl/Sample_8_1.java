package wyf.ytl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
public class Sample_8_1 extends Activity {
	private static final String[] mySpinner_str = {//���������б������
		"�������","�������","�������A*","Dijkstra","Dijkstra A*"
	}; 
	Spinner mySpinner;//���������б��
	Spinner targetSpinner;//Ŀ�������б��
	Button goButton;//��ʼ��ť
	GameView gameView;//�Լ�ʵ�ֵĵ�ͼView
	TextView BSTextView;//ʹ�ò������ı�
	TextView CDTextView;//·�����ȵ��ı�
	Game game;
	private ArrayAdapter<String> adapter;//���������б��ģ��
	private ArrayAdapter<String> adapter2;//Ŀ�������б��ģ��
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//���õ�ǰ��ʾ���û�����
        mySpinner = (Spinner)findViewById(R.id.mySpinner);//�õ����������б������
        targetSpinner = (Spinner)findViewById(R.id.target);//�õ�Ŀ�������б������
        gameView = (GameView) findViewById(R.id.gameView);//�õ�GameView������
        BSTextView = (TextView)findViewById(R.id.bushu);//�õ�ʹ�ò������ı��ؼ�������
        CDTextView = (TextView)findViewById(R.id.changdu);//�õ�·�����ȵ��ı��ؼ�������
        goButton = (Button) findViewById(R.id.go);//�õ���ʼ��ť������
        game = new Game();//��ʼ���㷨��
        //�������������б��ģ��
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mySpinner_str);
        String[] target_str = new String[MapList.target.length];//����Ŀ���ĸ�������һ������
        for(int i=0; i<MapList.target.length; i++){
        	target_str[i] = "Ŀ��"+i;
        }
        //����Ŀ�������б��ģ��
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, target_str);
        mySpinner.setAdapter(adapter);//����ģ��
        targetSpinner.setAdapter(adapter2);//����ģ��
        goButton.setOnClickListener(//��ť������
        	new Button.OnClickListener(){
				public void onClick(View v) {
					game.runAlgorithm();//�������㷽��
					goButton.setEnabled(false);
				}
	        }
        );
        targetSpinner.setOnItemSelectedListener(//Ŀ��ѡ��������б����
        	new Spinner.OnItemSelectedListener(){
				public void onItemSelected(AdapterView<?> a, View v,int arg2, long arg3){
					game.target = MapList.target[arg2];
					game.clearState();//��game��״̬���
					gameView.postInvalidate();//��д����gameView
				}
				public void onNothingSelected(AdapterView<?> arg0){
				}
        	}
        );
        mySpinner.setOnItemSelectedListener(//�㷨ѡ��������б����
            	new Spinner.OnItemSelectedListener(){
    				public void onItemSelected(AdapterView<?> ada, View v,int arg2, long arg3){
    					game.clearState();//��game��״̬���
    					game.algorithmId =  (int) ada.getSelectedItemId();//�õ�ѡ����㷨ID
    					gameView.postInvalidate();//��д����gameView
    				}
    				public void onNothingSelected(AdapterView<?> arg0) {
    				}
            	}
         );
        this.initIoc();//��������ע�뷽��
    }
    public void initIoc()
    {//����ע��
    	gameView.game = this.game;
    	gameView.mySpinner = this.mySpinner;
    	gameView.CDTextView = this.CDTextView;
    	game.gameView = this.gameView;
    	game.goButton = this.goButton;
    	game.BSTextView = this.BSTextView;
    }
}