package wyf.wpf;//声明包语句
import android.app.Activity;//引入相关类
import android.content.SharedPreferences;	//引入相关类
import android.os.Bundle;					//引入相关类
import android.widget.EditText;				//引入相关类
//继承自Activity的子类
public class Sample_4_5 extends Activity {
	EditText etPre;			//EditText对象的引用
	SharedPreferences sp;	//SharedPreference对象的引用
	public final String EDIT_TEXT_KEY = "EDIT_TEXT";	//Preferences文件中的键
    @Override
    public void onCreate(Bundle savedInstanceState) {	//重写onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        etPre = (EditText)findViewById(R.id.et);	//获得屏幕中EditText对象引用
        sp = getPreferences(MODE_PRIVATE);		//获得SharedPreferences对象
        String result = sp.getString(EDIT_TEXT_KEY, null);
        if(result != null){		//如果获取的值不为空
        	etPre.setText(result);	//EditText对象显示的内容设置为读取的数据
        }
    }
	@Override
	protected void onDestroy() {	//重写onDestroy方法
		SharedPreferences.Editor editor = sp.edit();	//获得SharedPreferences的Editor对象
		editor.putString(EDIT_TEXT_KEY, String.valueOf(etPre.getText()));	//修改数据
		editor.commit();			//必须调用该方法以提交修改
		super.onDestroy();
	}
}