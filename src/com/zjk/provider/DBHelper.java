package com.zjk.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 
\
 * ���ݿ⹤����
 */
public class DBHelper extends SQLiteOpenHelper{
    // ���ݿ����Ƴ���
    private static final String DATABASE_NAME = "Wireless.db";
    // ���ݿ�汾����
    private static final int DATABASE_VERSION = 2;
    // �����Ƴ���
    public static final String TABLES_TABLE_NAME = "TableTbl";
    public static final String TABLES_TABLE_NAME2 = "MenuTbl";
	// ���췽��
	public DBHelper(Context context) {
		// �������ݿ�
		super(context, DATABASE_NAME,null, DATABASE_VERSION);
	}

	// ����ʱ����
	public void onCreate(SQLiteDatabase db) {
		//��ע��ȥ��---Bylee
        db.execSQL("CREATE TABLE " + TABLES_TABLE_NAME + " ("
                + Tables._ID + " INTEGER PRIMARY KEY,"
                + Tables.NUM + " TEXT,"
                + Tables.DESCRIPTION + " TEXT"
                + ");");
        
        db.execSQL("CREATE TABLE " + TABLES_TABLE_NAME2 + " ("
                + Menus._ID + " INTEGER PRIMARY KEY,"
                + Menus.TYPE_ID + " INTEGER,"
                + Menus.NAME + " TEXT,"
                + Menus.PRICE + " INTEGER,"
                + Menus.PIC + " TEXT,"
                + Menus.REMARK + " TEXT"
                + ");");
	}

	// �汾����ʱ����
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// ɾ����
		db.execSQL("DROP TABLE IF EXISTS TableTbl");//��ע��ȥ��---Bylee
		db.execSQL("DROP TABLE IF EXISTS MenuTbl");
        onCreate(db);
	}

}
