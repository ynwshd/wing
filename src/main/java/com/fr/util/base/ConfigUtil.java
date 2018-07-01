package com.fr.util.base;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author admin
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 获得adminSessionInfo名字
	 * 
	 * @return
	 */
	public static final String getAdminSessionInfoName() {
		return bundle.getString("adminSessionInfoName");
	}

	/**
	 * 获得图片上传路径
	 * 
	 * @return
	 */
	public static final String getUploadPath() {
		return bundle.getString("uploadPath");
	}

	/**
	 * 获得图片上传真实路径
	 * 
	 * @return
	 */
	public static final String getUploadRealPath() {
		return bundle.getString("uploadRealPath");
	}

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}

	/**
	 * 获取工资平台照片文件展示路径
	 * 
	 * @return
	 */
	public static final String getImgPath() {
		return bundle.getString("wagePhotoWebPath");
	}

}
