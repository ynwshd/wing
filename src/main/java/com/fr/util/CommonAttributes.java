package com.fr.util;

public final class CommonAttributes {

	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd",
			"yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** wszp.xml文件路径 */
	public static final String SHOP_XML_PATH = "/wszp.xml";

	/** properties文件路径 */
	public static final String SHOP_PROPERTIES_PATH = "/config.properties";

	/**
	 * 不可实例化
	 */
	private CommonAttributes() {
	}

}
