package com.zjk.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
\vhttps://github.com/kakong/restaurant_android.git
 * 桌号常量类
 * https://github.com/kakong/restaurant_android.git
 *
 */
public final class Menus  implements BaseColumns{
		// 授权常量
	    public static final String AUTHORITY = "com.zjkAndroid app for recording calls to .m4a using AAC Audio Codec.provider.MENUS";
        // 访问uri
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/menu1");
        // 默认排序
        public static final String DEFAULT_SORT_ORDER = "typeId DESC";
        // 表字段
        public static final String TYPE_ID = "typeId";			//类型
        public static final String NAME= "name";				// 名称
        public static final String PRICE= "price";				// 价格
        public static final String PIC= "pic";					// 图片
        public static final String REMARK= "remark";			// 备注
}
