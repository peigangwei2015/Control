package com.google.control.domain;

public interface MsgType {
	/**
	 * 读取短信会话列表
	 */
	String READ_CONVERS_LIST = "read_convers_list";
	/**
	 * 短信会话列表
	 */
	String CONVERS_LIST = "convers_list";
	/**
	 * 读取短信列表
	 */
	String READ_SMS_LIST = "read_sms_list";
	/**
	 * 短信列表
	 */
	String SMS_LIST = "sms_list";
	
	/**
	 * 信息类型
	 */
	String TYPE = "type";
	/**
	 * 信息数据
	 */
	String DATA = "data";
	/**
	 * 读取手机内存文件列表
	 */
	String READ_ROM_FILE_LIST = "read_rom_file_list";
	/**
	 * 读取手机SD卡文件列表
	 */
	String READ_SDCARD_FILE_LIST = "read_sdcard_file_list";
	/**
	 * 文件列表
	 */
	String FILE_LIST="file_list";
	/**
	 * 读取文件列表
	 */
	String READ_FILE_LIST = "read_file_list";
}
