package com.zjk.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
\
 * ���ų�����
 */
public final class Menus  implements BaseColumns{
		// ��Ȩ����
	    public static final String AUTHORITY = "com.amaker.provider.MENUS";
        // ����Uri
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/menu1");
        // Ĭ��������
        public static final String DEFAULT_SORT_ORDER = "typeId DESC";// ����������
        // ���ֶγ���
        public static final String TYPE_ID = "typeId";			//����
        public static final String NAME= "name";				// ����
        public static final String PRICE= "price";				// �۸�
        public static final String PIC= "pic";					// ͼƬ
        public static final String REMARK= "remark";			// ��ע
}
