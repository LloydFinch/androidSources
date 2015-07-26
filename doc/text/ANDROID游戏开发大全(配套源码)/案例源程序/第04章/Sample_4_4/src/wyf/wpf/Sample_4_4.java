package wyf.wpf;							//���������
import android.app.Activity;				//���������
import android.content.ContentResolver;		//���������
import android.database.Cursor;				//���������
import android.net.Uri;						//���������
import android.os.Bundle;					//���������
import android.provider.Contacts.People;	//���������
import android.widget.TextView;				//���������
//�̳���Activity������
public class Sample_4_4 extends Activity {
	String [] columns = {		//��ѯContent Providerʱϣ�����ص���
		People._ID,		
		People.NAME,
	};
	Uri contactUri = People.CONTENT_URI;				//����Content Provider��Ҫ��Uri
	TextView tv;		//TextView��������
    @Override
    public void onCreate(Bundle savedInstanceState) {	//��дonCreate����
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView)findViewById(R.id.tv);        	//���TextView��������
        String result = getQueryData();					//���÷�������Content Provider
        tv.setText("ID\t����\n"+result);					//����ѯ������Ϣ��ʾ��TextView��
    }
    //��������ȡ��ϵ���б���Ϣ������String����
    public String getQueryData(){
    	String result = "";
    	ContentResolver resolver = getContentResolver();		//��ȡContentResolver����
    	Cursor cursor = resolver.query(contactUri, columns, null, null, null);	//���÷�����ѯContent Provider
        int idIndex = cursor.getColumnIndex(People._ID);		//���_ID�ֶε�������
        int nameIndex = cursor.getColumnIndex(People.NAME);		//���NAME�ֶε�������
        for(cursor.moveToFirst();(!cursor.isAfterLast());cursor.moveToNext()){	//����Cursor����ȡ����
        	result = result + cursor.getString(idIndex)+ "\t";
        	result = result + cursor.getString(nameIndex)+ "\t\n";
        }
        cursor.close();															//�ر�Cursor����
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
	TextView tv;		//TextView��������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView)findViewById(R.id.tv);        
        tv.setText(getQueryData());
    }
    //��������ȡ��ϵ���б���Ϣ������String����
    public String getQueryData(){
    	String result = "";
    	Cursor cursor = this.managedQuery(contactUri, columns, null, null, null);
        System.out.println("=========  the size of cursor is:"+cursor.getCount());
        int idIndex = cursor.getColumnIndex(People._ID);
        int nameIndex = cursor.getColumnIndex(People.NAME);
        int numberIndex = cursor.getColumnIndex(People.NUMBER);
        for(cursor.moveToFirst();(!cursor.isAfterLast());cursor.moveToNext()){	//����Cursor����ȡ����
        	result = result + cursor.getString(idIndex)+ "\t";
        	result = result + cursor.getString(nameIndex)+ "\t";
        	result = result + cursor.getString(numberIndex)+ "\t\n";        	
        }
        cursor.close();
    	return result;
    }    
}


 */
