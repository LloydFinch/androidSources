package wyf.wpf;	//声明包语句
import android.content.Context;	//引入相关类
import android.database.sqlite.SQLiteDatabase;	//引入相关类
import android.database.sqlite.SQLiteOpenHelper;	//引入相关类
import android.database.sqlite.SQLiteDatabase.CursorFactory;	//引入相关类
//继承自SQLiteOpenHelper的子类
public class MySQLiteHelper extends SQLiteOpenHelper{	
	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {			
		super(context, name, factory, version);		//调用父类的构造器
	}
	@Override
	public void onCreate(SQLiteDatabase db) {		//重写onCreate方法
		db.execSQL("create table if not exists hero_info("	//调用execSQL方法创建表
				 + "id integer primary key,"
				 + "name varchar,"
				 + "level integer)");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}	
}