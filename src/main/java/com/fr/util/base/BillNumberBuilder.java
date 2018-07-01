package com.fr.util.base;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 编号生成规则yyMMddHHmmssSSS
 * 
 * @author admin
 *
 */
public class BillNumberBuilder extends Thread {

	private static long orderNum = 0l;
	private static String date;

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 300; i++) {
			// System.out.println(BillNumberBuilder.getOrderNo());
			// Thread.sleep(1000);
			System.out.println(BillNumberBuilder.createNum(""));
		}
	}

	/**
	 * 生成订单编号
	 * 
	 * @return
	 */
	public static synchronized String getOrderNo() {
		String str = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		// System.out.println(str);
		if (date == null || !date.equals(str)) {
			date = str;
			orderNum = 0l;
		}
		orderNum++;
		long orderNo = Long.parseLong((date)) * 10000;
		orderNo += orderNum;
		;
		return orderNo + "";
	}

	/**
	 * 系统编号生成规则
	 * 
	 * @param ywtype
	 *            业务类别
	 * @return
	 */
	public static synchronized String createNum(String ywtype) {
		String str = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		String finalStr = ywtype + str;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return finalStr;
	}

	/**
	 * 图片名称生成规则
	 * 
	 * @param name
	 *            图片名称
	 * @return
	 */
	public static synchronized String createImgNum(String name) {
		String str = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
		String finalStr = name + "_" + str;
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return finalStr;
	}

}