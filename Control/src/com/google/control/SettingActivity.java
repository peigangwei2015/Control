package com.google.control;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.google.control.domain.MsgType;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;

/**
 * 设置中心 界面
 * 
 * @author Administrator
 * 
 */
public class SettingActivity extends BaseActivity {

	private EditText et_password;
	private EditText et_confirm_password;
	private Button bt_ok;
	private Button bt_clear;
	private Button bt_cancle;
	private AlertDialog dialog;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
	}

	@Override
	public void receiveText(int dwUserid, String message) {
		if (!TextUtils.isEmpty(message)) {
			JSONObject jObj = JSONObject.parseObject(message);
			String type = jObj.getString(MsgType.TYPE);
			if (MsgType.SET_LOCK_SCREEN_PWD_SUCCESS.equals(type)) {
				pDialog.dismiss();
				Toast.makeText(getApplicationContext(), "设置锁屏密码成功！", 1).show();
			}else if (MsgType.LOCK_SCREEN_SUCCESS.equals(type)) {
				pDialog.dismiss();
				Toast.makeText(getApplicationContext(), "锁屏成功！", 1).show();
			}
		}
	}

	@Override
	public void receiveSelf(String message) {

	}

	/**
	 * 设置锁屏密码
	 */
	public void setLockScreenPwd(View view) {
		showInputPwdDialog();
		// MsgUtils.send(getApplicationContext(),
		// MyConstant.currentUser.getId(), MsgType.SET_LOCK_SCREEN_PWD);
	}

	/**
	 * 显示输入密码对话框
	 */
	private void showInputPwdDialog() {
		AlertDialog.Builder builder = new Builder(this);
		dialog = builder.create();
		View view = View.inflate(getApplicationContext(),
				R.layout.setup_password_dialog, null);
		et_password = (EditText) view.findViewById(R.id.et_password);
		et_confirm_password = (EditText) view
				.findViewById(R.id.et_confirm_password);
		bt_ok = (Button) view.findViewById(R.id.bt_ok);
		bt_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String pwd = et_password.getText().toString();
				String cpwd = et_confirm_password.getText().toString();
				if (TextUtils.isEmpty(pwd)) {
					Toast.makeText(getApplicationContext(), "密码不能为空！请重新输入", 1)
							.show();
					return;
				} else if (TextUtils.isEmpty(cpwd)) {
					Toast.makeText(getApplicationContext(), "确认密码不能为空！请重新输入", 1)
							.show();
					return;
				} else if (!pwd.equals(cpwd)) {
					Toast.makeText(getApplicationContext(), "密码和确认密码不一致！请重新输入",
							1).show();
					return;
				}
				MsgUtils.send(getApplicationContext(),
						MyConstant.currentUser.getId(),
						MsgType.SET_LOCK_SCREEN_PWD, pwd);
				dialog.dismiss();
				showProcessDialog();
			}

		});
		bt_cancle = (Button) view.findViewById(R.id.bt_cancle);
		bt_cancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		bt_clear = (Button) view.findViewById(R.id.bt_clear);
		bt_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MsgUtils.send(getApplicationContext(),
						MyConstant.currentUser.getId(),
						MsgType.SET_LOCK_SCREEN_PWD, "");
				dialog.dismiss();
				showProcessDialog();
			}
		});
		dialog.setView(view);
		dialog.show();
	}

	/**
	 * 显示处理界面
	 */
	protected void showProcessDialog() {
		 pDialog=new ProgressDialog(this);
		pDialog.setTitle("正在处理中...");
		pDialog.show();
	}

}
