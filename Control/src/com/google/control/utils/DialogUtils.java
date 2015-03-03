package com.google.control.utils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * 提示框帮助类
 * @author Administrator
 *
 */
public class DialogUtils {
	/**
	 * 显示土司
	 * @param context上下文
	 * @param msg 要显示的信息
	 */
	public static void showToast(Context context,String msg){
		Toast.makeText(context, msg, 0).show();
	}
	
	/**
	 * 显示处理中弹窗
	 * @param activity 当前Activity
	 * @param title 标题
	 * @param msg 信息内容
	 * @return ProgressDialog对象
	 */
	public static ProgressDialog showProgressDialog(Activity activity,String title,String msg){
		ProgressDialog pd=new ProgressDialog(activity);
		pd.setTitle(title);
		pd.setMessage(msg);
		pd.show();
		return pd;
	}
	
	
}
