package com.zjk.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**

 * 桌号常量类
 */
public final class Tables  implements BaseColumns{
		// 授权常量
	    public static final String AUTHORITY = "com.zjk.provider.TABLES";
        // 访问Uri
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/table");
        // 默认排序常量
        public static final String DEFAULT_SORT_ORDER = "num DESC";
        // 表字段常量
        public static final String NUM = "num";
        public static final String DESCRIPTION= "description";
}
