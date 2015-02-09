package com.google.control.utils;

import android.content.Context;
import android.content.Intent;

public class MsgUtils {
	/**
	 * 发送文本信息给Activity
	 * 
	 * @param dwFromUserid
	 *            发送者ID
	 * @param message
	 *            信息内容
	 */
	public static void sendTextToActivity(Context context, int dwUserid,
			String message) {
		Intent intent = new Intent();
		intent.setAction(MyConstant.ANYCHAT_ACTIVITY_RECEIVE_ACTION);
		intent.putExtra("dwUserid", dwUserid);
		intent.putExtra("type", MyConstant.TEXT_MSG);
		intent.putExtra("message", message);
		context.sendBroadcast(intent);
	}

	/**
	 * 发送通知信息
	 */
	public static void sendMsg(Context context, String message) {
		Intent intent = new Intent();
		intent.setAction(MyConstant.ANYCHAT_ACTIVITY_RECEIVE_ACTION);
		intent.putExtra("message", message);
		intent.putExtra("type", MyConstant.SELF_MSG);
		context.sendBroadcast(intent);
	}

	/**
	 * 发送信息
	 * 
	 * @param id
	 *            接受者ID
	 * @param msg
	 *            信息内容
	 * @return 
	 */
	public static String send(Context context, int id, String type, Object data) {
		String json=formart(type, data);
		Intent intent = new Intent();
		intent.setAction(MyConstant.ANYCHAT_SERVICE_RECEIVE_ACTION);
		intent.putExtra("command", MyConstant.SEND);
		intent.putExtra("id", id);
		intent.putExtra("msg", json);
		context.sendBroadcast(intent);
		return json;
	}
	/**
	 * 发送信息
	 * 
	 * @param id
	 *            接受者ID
	 * @param msg
	 *            信息内容
	 */
	public static void send(Context context, int id, String type) {
		send(context, id, type, null);
	}

	/**
	 * 格式化Json
	 * @param string
	 * @return
	 */
	public static String formart(String type,Object data) {
		StringBuilder sb = new StringBuilder();
		sb.append("{type:\"" + type + "\"");
		if (data != null) {
			sb.append(",data:");
			sb.append(JsonUtils.obj2json(data));
		}
		sb.append("}");
		return sb.toString();
	}
	/**
	 * 格式化Json
	 * @param string
	 * @return
	 */
	public static String formart(String type) {
		return formart(type, null);
	}
}
