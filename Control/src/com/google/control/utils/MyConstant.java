package com.google.control.utils;

import com.google.control.domain.User;

/**
 * 常量池
 */
public class MyConstant {
	/**
	 * anychatService接受信息的Action
	 */
	public static final String ANYCHAT_SERVICE_RECEIVE_ACTION="com.google.control.anychatservice.receiver";
	/**
	 * Activity接受信息的Action
	 */
	public static final String ANYCHAT_ACTIVITY_RECEIVE_ACTION="com.google.control.activity.receiver";
	/**
	 * 连接服务器命令
	 */
	public static final String CONN_SERVER="connServer";
	/**
	 *重新连接服务器命令
	 */
	public static final String RECONN_SERVER="reConnServer";
	/**
	 * 用户在线列表
	 */
	public static final String USER_ONLINE_LIST = "user_online_list";
	/**
	 * 在线列表发生改变了
	 */
	public static final String USER_ONLINE_CHANGE = "user_online_change";
	/**
	 * 发送信息
	 */
	public static final String SEND = "send";
	/**
	 * 文本信息
	 */
	public static final String TEXT_MSG = "text_msg";
	/**
	 * 自身的信息
	 */
	public static final String SELF_MSG = "self_msg";
	/**
	 * 百度地图的Key
	 */
	public static final String BD_MAP_KEY = "bO8B5EPgghNSzq3N7PYFGHZP";
	
	/**
	 * 当前用户
	 */
	public static User currentUser;
}
