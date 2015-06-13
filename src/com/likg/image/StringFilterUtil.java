package com.likg.image;

import java.util.Iterator;
import java.util.Set;

public class StringFilterUtil {

	/**
	 * 必须以指定的字符串为开头，否则删除该元素
	 * @param strSet 需要过滤的字符串集合
	 * @param start 开始字符串
	 */
	public static void startsWithFilter(Set<String> strSet, String start) {
		for(Iterator<String> iter=strSet.iterator(); iter.hasNext(); ){
			String attr = iter.next();
			if(!attr.startsWith(start)){
				iter.remove();
			}
		}
	}

}
