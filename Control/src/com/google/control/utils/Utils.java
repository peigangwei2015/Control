package com.google.control.utils;

import android.text.TextUtils;
public class Utils {
	/**
	 * 判断是否是一个合法的IP地址
	 * 
	 * @param strIP
	 *            要检测的IP
	 * @return 如果是IP地址则返回true，否则返回false
	 */
	public static boolean isIPAddress(String strIP) {
		String regIP = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
		if (!TextUtils.isEmpty(strIP) && strIP.matches(regIP)) {
			return true;
		}
		return false;
	}
}
