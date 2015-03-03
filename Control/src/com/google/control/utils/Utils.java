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
		return new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date(date));
	}
	/**
	 * 格式化时间
	 * @param date
	 * @return
	 */
	public static String formatDate(String foramt,long date) {
		return new SimpleDateFormat(foramt).format(new Date(date));
	}

	/**
	 * 格式化时长
	 * @param durtion
	 * @return
	 */
	public static CharSequence formatDuration(int durtion) {
		int min = durtion/60;
		int hour=0;
		if (min>60) {
			 hour = min/60;
			min=min%60;
		}
		int s=durtion%60;
		return hour+"时 "+min+"分 "+s+"秒";
		
	}
	/**
	 * 格式化时长
	 * 
	 * @param recordTime
	 * @return
	 */
	public static String formatTime(int time) {
		if (time < 0) {
			return "00:00:00";
		}
		// 秒
		int s = time % 60;
		String ss = s < 10 ? "0" + s : s + "";
		// 分钟
		int m = (time / 60) % 60;
		String sm = m < 10 ? "0" + m : m + "";
		// 小时
		int h = time / (60 * 60);
		String sh = h < 10 ? "0" + h : h + "";
		return sh + ":" + sm + ":" + ss;
	};

	
	
}
