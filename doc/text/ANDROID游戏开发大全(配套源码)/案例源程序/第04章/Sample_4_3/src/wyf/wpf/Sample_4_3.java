package wyf.wpf;		//���������
import android.app.Activity;	//���������
import android.content.ContentValues;	//���������
import android.database.Cursor;	//���������
import android.database.sqlite.SQLiteDatabase;	//���������
import android.os.Bundle;	//���������	
import android.widget.TextView;	//���������
//�̳���Activity������
public class Sample_4_3 extends Activity {
	MySQLiteHelper myHelper;	//���ݿ⸨������������
	TextView tv;				//TextView���������
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//������ʾ����Ļ
        tv = (TextView)findViewById(R.id.tv);		//���TextView���������
        myHelper = new MySQLiteHelper(this, "my.db", null, 1);	//����MySQLiteOpenHelper���������
        insertAndUpdateData(myHelper);	//�����ݿ��в���͸�������
        String result = queryData(myHelper);	//�����ݿ��в�ѯ����
        tv.setText("����\t�ȼ�\n"+result);		//����ѯ����������ʾ����Ļ��
    }
	//�����������ݿ��еı��в���͸�������
	public void insertAndUpdateData(MySQLiteHelper myHelper){
		SQLiteDatabase db = myHelper.getWritableDatabase();	//��ȡ���ݿ����
		//ʹ��execSQL��������в�������
		db.execSQL("insert into hero_info(name,level) values('Hero1',1)");
		//ʹ��insert��������в�������
		ContentValues values = new ContentValues();	//����ContentValues����洢������-��ֵ��ӳ��
		values.put("name", "hero2");		
		values.put("level", 2);
		db.insert("hero_info", "id", values);		//���÷�����������
		//ʹ��update�������±��е�����
		values.clear();	//���ContentValues����
		values.put("name", "hero2");
		values.put("level", 3);
		db.update("hero_info", values, "level = 2", null);	//���±���levelΪ2����������		
		db.close();			//�ر�SQLiteDatabase����
	}
	//�����������ݿ��в�ѯ����
	public String queryData(MySQLiteHelper myHelper){
		String result="";
		SQLiteDatabase db = myHelper.getReadableDatabase();		//������ݿ����
		Cursor cursor = db.query("hero_info", null, null, null, null, null, "id asc");	//��ѯ��������
		int nameIndex = cursor.getColumnIndex("name");	//��ȡname�е�����
		int levelIndex = cursor.getColumnIndex("level");	//��ȡlevel�е�����
		for(cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()){	//������ѯ���������������ȡ����
			result = result + cursor.getString(nameIndex)+"    ";
			result = result + cursor.getInt(levelIndex)+"     \n";
		}
		cursor.close();		//�رս����
		db.close();			//�ر����ݿ����
		return result;
	}
	@Override
	protected void onDestroy() {
		SQLiteDatabase db = myHelper.getWritableDatabase();	//��ȡ���ݿ����
		db.delete("hero_info", "1", null);		//ɾ��hero_info���е���������
		super.onDestroy();
	}
}