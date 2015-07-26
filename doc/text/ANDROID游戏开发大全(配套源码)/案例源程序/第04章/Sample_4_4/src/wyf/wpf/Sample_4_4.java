package wyf.wpf;							//声明包语句
import android.app.Activity;				//引入相关类
import android.content.ContentResolver;		//引入相关类
import android.database.Cursor;				//引入相关类
import android.net.Uri;						//引入相关类
import android.os.Bundle;					//引入相关类
import android.provider.Contacts.People;	//引入相关类
import android.widget.TextView;				//引入相关类
//继承自Activity的子类
public class Sample_4_4 extends Activity {
	String [] columns = {		//查询Content Provider时希望返回的列
		People._ID,		
		People.NAME,
	};
	Uri contactUri = People.CONTENT_URI;				//访问Content Provider需要的Uri
	TextView tv;		//TextView对象引用
    @Override
    public void onCreate(Bundle savedInstanceState) {	//重写onCreate方法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView)findViewById(R.id.tv);        	//获得TextView对象引用
        String result = getQueryData();					//调用方法访问Content Provider
        tv.setText("ID\t名字\n"+result);					//将查询到的信息显示到TextView中
    }
    //方法：获取联系人列表信息，返回String对象
    public String getQueryData(){
    	String result = "";
    	ContentResolver resolver = getContentResolver();		//获取ContentResolver对象
    	Cursor cursor = resolver.query(contactUri, columns, null, null, null);	//调用方法查询Content Provider
        int idIndex = cursor.getColumnIndex(People._ID);		//获得_ID字段的列索引
        int nameIndex = cursor.getColumnIndex(People.NAME);		//获得NAME字段的列索引
        for(cursor.moveToFirst();(!cursor.isAfterLast());cursor.moveToNext()){	//遍历Cursor，提取数据
        	result = result + cursor.getString(idIndex)+ "\t";
        	result = result + cursor.getString(nameIndex)+ "\t\n";
        }
        cursor.close();															//关闭Cursor对象
        return result;
    }    
}






/*

package wyf.wpf;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.widget.TextView;

public class Sample_4_4 extends Activity {
	String [] columns = {
		People._ID,
		People.NAME,
		People.NUMBER
	};
	Uri contactUri = People.CONTENT_URI;
	TextView tv;		//TextView对象引用
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView)findViewById(R.id.tv);        
        tv.setText(getQueryData());
    }
    //方法：获取联系人列表信息，返回String对象
    public String getQueryData(){
    	String result = "";
    	Cursor cursor = this.managedQuery(contactUri, columns, null, null, null);
        System.out.println("=========  the size of cursor is:"+cursor.getCount());
        int idIndex = cursor.getColumnIndex(People._ID);
        int nameIndex = cursor.getColumnIndex(People.NAME);
        int numberIndex = cursor.getColumnIndex(People.NUMBER);
        for(cursor.moveToFirst();(!cursor.isAfterLast());cursor.moveToNext()){	//遍历Cursor，提取数据
        	result = result + cursor.getString(idIndex)+ "\t";
        	result = result + cursor.getString(nameIndex)+ "\t";
        	result = result + cursor.getString(numberIndex)+ "\t\n";        	
        }
        cursor.close();
    	return result;
    }    
}


 */
