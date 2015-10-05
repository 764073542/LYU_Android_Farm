package com.farm.database;

import com.farm.data.Constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * ��Ҫ���ڴ������ݿ� LYU_FARM ��������һ�ű� user
 * ���ڴ洢ʱ�Ӷ�ʱ������ز��������濪ʼ����ʱ�䣬��ȡ��ʱ���ȡ�
 * @author ����С����
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	String DATABASE_CREATE = "CREATE TABLE USER"
			+ "(_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "START_TIME VARCHAR(200)," + "START_DO VARCHAR(100),"
			+ "STOP_TIME VARCHAR(200)," + "STOP_DO VARCHAR(100),"
			+ "TIME_LENGTH LONG)"+ "FLAG INT)";

	// String DATABASE_CREATE_TEMP =
	// "create table user_done(_id INTEGER PRIMARY KEY AUTOINCREMENT,mtext varchar(8000),mtime varchar(200),isalarm long)";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, Constant.DATABASE_NAME, factory,
				Constant.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("Farm", "�������ݱ�USER");
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("Farm","�������ݿ�汾");
	}

}
