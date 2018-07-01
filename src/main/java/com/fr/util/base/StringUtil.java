package com.fr.util.base;

/**
 * String工具类
 * 
 * @author 孙宇
 * 
 */
public class StringUtil {

	/**
	 * 格式化字符串
	 * 
	 * 例：formateString("xxx{0}bbb",1) = xxx1bbb
	 * 
	 * @param str
	 * @param params
	 * @return
	 */
	public static String formateString(String str, String... params) {
		for (int i = 0; i < params.length; i++) {
			str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
		}
		return str;
	}

	/**
	 * 实现oracle lpad函数
	 * 
	 * @param str
	 * @param num
	 * @param pad
	 * @return
	 */
	public static String lpad(String str, int num, String pad) {
		String n_str = str;
		if (str == null)
			n_str = " ";
		for (int i = str.length(); i < num; i++) {
			n_str = pad + n_str;
		}
		return n_str;
	}

	/**
	 * 在特定字符前前导数据
	 * 
	 * @param str
	 * @param num
	 * @param pad
	 * @return
	 */
	public static String prefix(String str, int num, String pad) {
		String n_str = str;
		if (str == null)
			n_str = " ";
		for (int i = 0; i < num; i++) {
			n_str = pad + n_str;
		}
		return n_str;
	}

}
