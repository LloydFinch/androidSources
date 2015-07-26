package wyf.wpf;		//声明包语句
import android.app.Activity;	//引入相关类
import android.content.ContentValues;	//引入相关类
import android.database.Cursor;	//引入相关类
import android.database.sqlite.SQLiteDatabase;	//引入相关类
import android.os.Bundle;	//引入相关类	
import android.widget.TextView;	//引入相关类
//继承自Activity的子类
public class Sample_4_3 extends Activity {
	MySQLiteHelper myHelper;	//数据库辅助类对象的引用
	TextView tv;				//TextView对象的引用
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//设置显示的屏幕
        tv = (TextView)findViewById(R.id.tv);		//获得TextView对象的引用
        myHelper = new MySQLiteHelper(this, "my.db", null, 1);	//创建MySQLiteOpenHelper辅助类对象
        insertAndUpdateData(myHelper);	//向数据库中插入和更新数据
        String result = queryData(myHelper);	//向数据库中查询数据
        tv.setText("名字\t等级\n"+result);		//将查询到的数据显示到屏幕上
    }
	//方法：向数据库中的表中插入和更新数据
	public void insertAndUpdateData(MySQLiteHelper myHelper){
		SQLiteDatabase db = myHelper.getWritableDatabase();	//获取数据库对象
		//使用execSQL方法向表中插入数据
		db.execSQL("insert into hero_info(name,level) values('Hero1',1)");
		//使用insert方法向表中插入数据
		ContentValues values = new ContentValues();	//创建ContentValues对象存储“列名-列值”映射
		values.put("name", "hero2");		
		values.put("level", 2);
		db.insert("hero_info", "id", values);		//调用方法插入数据
		//使用update方法更新表中的数据
		values.clear();	//清空ContentValues对象
		values.put("name", "hero2");
		values.put("level", 3);
		db.update("hero_info", values, "level = 2", null);	//更新表中level为2的那行数据		
		db.close();			//关闭SQLiteDatabase对象
	}
	//方法：从数据库中查询数据
	public String queryData(MySQLiteHelper myHelper){
		String result="";
		SQLiteDatabase db = myHelper.getReadableDatabase();		//获得数据库对象
		Cursor cursor = db.query("hero_info", null, null, null, null, null, "id asc");	//查询表中数据
		int nameIndex = cursor.getColumnIndex("name");	//获取name列的索引
		int levelIndex = cursor.getColumnIndex("level");	//获取level列的索引
		for(cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()){	//遍历查询结果集，将数据提取出来
			result = result + cursor.getString(nameIndex)+"    ";
			result = result + cursor.getInt(levelIndex)+"     \n";
		}
		cursor.close();		//关闭结果集
		db.close();			//关闭数据库对象
		return result;
	}
	@Override
	protected void onDestroy() {
		SQLiteDatabase db = myHelper.getWritableDatabase();	//获取数据库对象
		db.delete("hero_info", "1", null);		//删除hero_info表中的所有数据
		super.onDestroy();
	}
}