package com.google.control;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.control.utils.MyConstant;
import com.google.control.utils.Utils;

public class ServerConfigActivity extends Activity {
	private static final String TAG = "ServerConfigActivity";
	private EditText et_server_ip;
	private EditText et_server_port;
	private String strIP;
	private String strPort;
	private String strName="Admin";
	private SharedPreferences sp;
	/**
	 * 之前的服务器IP地址
	 */
	private String beforeServerIP;
	/**
	 * 之前的服务器端口
	 */
	private int beforeServerPort;
	/**
	 * 之前的名字
	 */
	private String beforeName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server_config);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		et_server_ip = (EditText) findViewById(R.id.et_server_ip);
		et_server_port = (EditText) findViewById(R.id.et_server_port);
		// 回显保存的信息
		echoSaveData();
	}

	/**
	 * 回显保存的信息
	 */
	private void echoSaveData() {
		beforeServerIP = sp.getString("serverIP", "192.168.1.101");
		beforeName = sp.getString("name", "Sunny");
		beforeServerPort = sp.getInt("serverPort", 8906);
		et_server_ip.setText(beforeServerIP);
		et_server_port.setText(beforeServerPort+"");
	}

	/**
	 * 完成配置
	 * 
	 * @param view
	 */
	public void doneConfig(View view) {
		boolean isDataOk = checkData();
		if (isDataOk) {
			saveData();
			if (checkDataChange()) {
				Log.v(TAG, "数据更改了！");
				reConnServer();
			}
			gotoHome();
		}
	}

	/**
	 * 判断数据是否发生改变
	 * @return 如果改变返回true，否则返回false
	 */
	private boolean checkDataChange() {
		Log.v(TAG, "判断数据是否发生改变");
		if (strIP.equals(beforeServerIP) && strPort.equals(String.valueOf(beforeServerPort)) && strName.equals(beforeName)) {
			return false;
		}
		return true;
	}

	/**
	 * 跳转到主页
	 */
	private void gotoHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 重新连接服务器
	 */
	private void reConnServer() {
		Intent intent=new Intent();
		intent.setAction(MyConstant.ANYCHAT_SERVICE_RECEIVE_ACTION);
		intent.putExtra("command", MyConstant.RECONN_SERVER);
		sendBroadcast(intent);
	}

	/**
	 * 保存数据
	 */
	private void saveData() {
		Editor editor = sp.edit();
		editor.putString("serverIP", strIP);
		editor.putInt("serverPort", Integer.parseInt(strPort));
		editor.putString("name", strName);
		editor.commit();
	}

	/**
	 * 检查输入的数据是否合法
	 * 
	 * @return 如果合法返回true，否则返回false
	 */
	private boolean checkData() {
		strIP = et_server_ip.getText().toString();
		strPort = et_server_port.getText().toString();
		// 判断IP地址是否合法
		if (!Utils.isIPAddress(strIP)) {
			Toast.makeText(getApplicationContext(), "你输入的IP地址不正确，请重新输入！", 1)
					.show();
			return false;
		}
		// 判断端口是否合法
		if (strPort.length() < 1 || strPort.length() > 5) {
			Toast.makeText(getApplicationContext(), "你输入的端口不正确，请重新输入！", 1)
					.show();
			return false;
		}
		return true;
	}
}
