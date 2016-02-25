package com.zjk.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**

 * ���ų�����
 */
public final class Tables  implements BaseColumns{
		// ��Ȩ����
	    public static final String AUTHORITY = "com.amaker.provider.TABLES";
        // ����Uri
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/table");
        // Ĭ��������
        public static final String DEFAULT_SORT_ORDER = "num DESC";// ����������
        // ���ֶγ���
        public static final String NUM = "num";					//����
        public static final String DESCRIPTION= "description";	// ����
}
