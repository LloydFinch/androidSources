package wyf.wpf;	//���������
import android.content.Context;	//���������
import android.database.sqlite.SQLiteDatabase;	//���������
import android.database.sqlite.SQLiteOpenHelper;	//���������
import android.database.sqlite.SQLiteDatabase.CursorFactory;	//���������
//�̳���SQLiteOpenHelper������
public class MySQLiteHelper extends SQLiteOpenHelper{	
	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {			
		super(context, name, factory, version);		//���ø���Ĺ�����
	}
	@Override
	public void onCreate(SQLiteDatabase db) {		//��дonCreate����
		db.execSQL("create table if not exists hero_info("	//����execSQL����������
				 + "id integer primary key,"
				 + "name varchar,"
				 + "level integer)");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}	
}