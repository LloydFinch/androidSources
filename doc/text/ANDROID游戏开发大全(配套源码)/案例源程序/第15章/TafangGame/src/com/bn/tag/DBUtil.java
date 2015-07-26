package com.bn.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

public class DBUtil
{
	static SQLiteDatabase sld;
	static EditText et;
	public static void createOrOpenDatabase() throws Exception
    {
    	sld=SQLiteDatabase.openDatabase
    	(
    			"/data/data/com.bn.tag/tagtdb", //数据库所在路径
    			null, 								//CursorFactory
    			SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY //读写、若不存在则创建
    	);	    	
    	String sql1="create table if not exists save(savename varchar2(20),savetime varchar2(20))";
    	sld.execSQL(sql1); 
    	String sql2="create table if not exists nochange(savename varchar2(20),money Integer(4),blood Integer(4),shdi Integer(4),shuijing Integer(4))";
    	sld.execSQL(sql2); 
    	String sql3="create table if not exists guaiw(savename varchar2(20),gw_x INTEGER(4),gw_y INTEGER(4),gw_state INTEGER(2),direction INTEGER(3),ii INTEGER(2),currentblood INTEGER(3),sumblood INTEGER(3))";
    	sld.execSQL(sql3); 
    	String sql4="create table if not exists jiant(savename varchar2(20),jt_x INTEGER(4),jt_y INTEGER(4),gt_state INTEGER(2))";
    	sld.execSQL(sql4); 
    		 
    }
    
    public static void closeDatabase() throws Exception
    {
	   try
	   {
		   sld.close(); 
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
    	
    }
   
    /*=====================================begin==========================================================*/
    //获得当前存档的金钱等
    public static int[] searchnochange(String savename)
    {
    	int[] result=new int[4];
    	try{
    		createOrOpenDatabase();
    		String sql="select nochange.money,nochange.blood,nochange.shdi,nochange.shuijing from nochange where nochange.savename='"+savename+"'";
    		Cursor cur=sld.rawQuery(sql, new String[]{});
	    	if(cur.moveToNext())
	    	{
	    		result[0]=cur.getInt(0);
	    		result[1]=cur.getInt(1);
	    		result[2]=cur.getInt(2);
	    		result[3]=cur.getInt(3);
	    		
	    	}
	    	cur.close();
	    	closeDatabase();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return result;
    	
    }
    //获得当前存档的箭塔位置
    public static Vector<Integer> searjianta(String savename)
    {
    	Vector<Integer> result=new Vector<Integer>();
    	try{
    		createOrOpenDatabase();
    		String sql="select jiant.jt_x,jiant.jt_y,jiant.gt_state from jiant,save where save.savename=jiant.savename and save.savename='"+savename+"'";
    		Cursor cur=sld.rawQuery(sql, new String[]{});
	    	while(cur.moveToNext())
	    	{
	    		result.add(cur.getInt(0));
	    		result.add(cur.getInt(1));
	    		result.add(cur.getInt(2));
	    		//result.add(cur.getString(2));
	    	}
	    	cur.close();
	    	closeDatabase();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return result;
    	
    }
    //获得当前存档的怪物信息
    public static List<Integer> searchguaiwu(String savename)
    {
    	List<Integer> list=new ArrayList<Integer>();
    	try{
    		createOrOpenDatabase();
    		String sql="select guaiw.gw_x,guaiw.gw_y,guaiw.gw_state,guaiw.direction,guaiw.ii,guaiw.currentblood,guaiw.sumblood from guaiw,save where save.savename=guaiw.savename and save.savename='"+savename+"'";
    		Cursor cur=sld.rawQuery(sql, new String[]{});
	    	while(cur.moveToNext())
	    	{
	    		list.add(cur.getInt(0));
	    		list.add(cur.getInt(1));
	    		list.add(cur.getInt(2));
	    		list.add(cur.getInt(3));
	    		list.add(cur.getInt(4));
	    		list.add(cur.getInt(5));
	    		list.add(cur.getInt(6));
	    		//list.add(cur.getInt(2));
	    	}
	    	cur.close();
	    	closeDatabase();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return list;
    }
    //获得所有游戏存档
    public static List<String> getcundang()
    {
    	List<String> list=new ArrayList<String>();
    	try
    	{
    		createOrOpenDatabase();
	    	String sql="select savename from save";
	    	Cursor cur=sld.rawQuery(sql, new String[]{});
	    	while(cur.moveToNext())
	    	{
	    		list.add(cur.getString(0));  //经度
	    		//list.add(cur.getString(1));  //纬度	    		
	    	}
	    	cur.close();
	    	closeDatabase();
    	}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		return list;    	
    	
    }
    public static void updatetable(String sql)
    {
    	try
    	{
    		createOrOpenDatabase();	    	
    		sld.execSQL(sql);	    	
	    	closeDatabase();
    	}
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
    }
   
    /*=====================================end==========================================================*/
}
