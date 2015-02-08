package com.google.control.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.control.HomeActivity;

import android.content.Context;
import android.content.Intent;
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
	
	/**
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public static String formatDate(long date) {
		SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
		Date date2=new Date(date);
		Date now=new Date();
		if (date2.getDay()==now.getDay()) {
			return "今天 "+dateFormat.format(date2);
		}else if (date2.getDay()== (now.getDay()-1)) {
			return "昨天 "+dateFormat.format(date2);
		}else if (date2.getDay()== (now.getDay()-2)) {
			return "前天 "+dateFormat.format(date2);
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date));
	}
	
	
}
