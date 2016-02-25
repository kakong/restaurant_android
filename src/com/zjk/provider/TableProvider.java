package com.zjk.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class TableProvider extends ContentProvider{
	// ���ݿ������
	private DBHelper dbHelper;
    // Uri������
    private static final UriMatcher sUriMatcher;
    // ��ѯ����������
    private static final int TABLES = 1;
    private static final int TABLES_ID = 2;
    // ��ѯ�м���
    private static HashMap<String, String> tblProjectionMap;
    static {
    	// Uriƥ�乤����
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Tables.AUTHORITY, "table", TABLES);
        sUriMatcher.addURI(Tables.AUTHORITY, "table/#", TABLES_ID);
        // ʵ������ѯ�м���
        tblProjectionMap = new HashMap<String, String>();
        // ��Ӳ�ѯ��
        tblProjectionMap.put(Tables._ID, Tables._ID);
        tblProjectionMap.put(Tables.NUM, Tables.NUM);
        tblProjectionMap.put(Tables.DESCRIPTION,Tables.DESCRIPTION);
    }

	// �����ǵ���
	public boolean onCreate() {
		// ʵ�������ݿ������
		dbHelper = new DBHelper(getContext());
		return true;
	}
	// ��ӷ���
	public Uri insert(Uri uri, ContentValues values) {
		// ������ݿ�ʵ��
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// �������ݣ�������ID
		long rowId = db.insert(DBHelper.TABLES_TABLE_NAME,Tables.NUM, values);
		// �������ɹ�����uri
        if (rowId > 0) {
            Uri empUri = ContentUris.withAppendedId(Tables.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(empUri, null);
            return empUri;
        }
		return null;
	}
	

	// �������
	public String getType(Uri uri) {
		return null;
	}

	// ��ѯ����
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	        switch (sUriMatcher.match(uri)) {
	        // ��ѯ����
	        case TABLES:
	            qb.setTables(DBHelper.TABLES_TABLE_NAME);
	            qb.setProjectionMap(tblProjectionMap);
	            break;
	        // ����ID��ѯ
	        case TABLES_ID:
	            qb.setTables(DBHelper.TABLES_TABLE_NAME);
	            qb.setProjectionMap(tblProjectionMap);
	            qb.appendWhere(Tables._ID + "=" + uri.getPathSegments().get(1));
	            break;
	        default:
	            throw new IllegalArgumentException("Uri���� " + uri);
	        }

	        // ʹ��Ĭ������
	        String orderBy;
	        if (TextUtils.isEmpty(sortOrder)) {
	            orderBy = Tables.DEFAULT_SORT_ORDER;
	        } else {
	            orderBy = sortOrder;
	        }

	        // ������ݿ�ʵ��
	        SQLiteDatabase db = dbHelper.getReadableDatabase();
	        // �����α꼯��
	        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
	        c.setNotificationUri(getContext().getContentResolver(), uri);
	        return c;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		return 0;
	}
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}
}
