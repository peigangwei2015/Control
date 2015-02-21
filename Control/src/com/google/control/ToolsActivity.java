package com.google.control;

import com.google.control.domain.MsgType;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;

import android.os.Bundle;
import android.view.View;

/**
 *小工具 界面
 */
public class ToolsActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);
		
	}

	@Override
	public void receiveText(int dwUserid, String message) {
		
	}

	@Override
	public void receiveSelf(String message) {
		
	}
	
	/**
	 * 一键锁屏
	 * @param view
	 */
	public void lockScreen(View view){
		MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(), MsgType.LOCK_SCREEN);
	}
	
}
