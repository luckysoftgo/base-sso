package com.application.base.core.util;

import java.util.*;

/**
 * @Author: 孤狼
 * @desc: 字符串工具
 */
public abstract class StringUtils {
	
	/**
	 * 判断是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj==null){
			return true;
		}
		String str=Objects.toString(obj,"");
		if (str.length()==0){
			return true;
		}
		return false;
	}
	
	/**
	 *
	 * @param str
	 * @return
	 */
	public static boolean hasLength(String str) {
		return (str != null && !str.isEmpty());
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static boolean hasText(String str) {
		return (hasLength(str) && containsText(str));
	}

	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param str
	 * @param delimiters
	 * @param trimTokens
	 * @param ignoreEmptyTokens
	 * @return
	 */
	public static String[] tokenizeToStringArray(
			String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}

		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	/**
	 *
	 * @param collection
	 * @return
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}

}
