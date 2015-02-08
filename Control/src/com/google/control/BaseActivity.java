package com.google.control;

import com.google.control.utils.MyConstant;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
public abstract class BaseActivity extends Activity {
	private BroadcastReceiver receiver;

	@Override
	protected void onStart() {
		super.onStart();
		
		IntentFilter filter=new IntentFilter();
		filter.addAction(MyConstant.ANYCHAT_ACTIVITY_RECEIVE_ACTION);
		receiver=new MyReceiver();
		registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(receiver);
	}
	
	
	private class MyReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			String type = intent.getStringExtra("type");
			if (MyConstant.SELF_MSG.equals(type)) {
//				接受自身发的通知消息
				String message = intent.getStringExtra("message");
				receiveSelf(message);
			}else if(MyConstant.TEXT_MSG.equals(type)){
//				接受文本信息
				int dwUserid = intent.getIntExtra("dwUserid", Integer.MAX_VALUE);
				String message = intent.getStringExtra("message");
				receiveText(dwUserid,message);
			}
		}

		

	}
	

	/**
	 * 接受文本信息
	 * @param fromUserId 发送者ID
	 * @param message 信息内容
	 */
	public abstract void receiveText(int dwUserid,String message);
	/**
	 * 接受自身发的通知消息
	 * @param message
	 */
	public abstract void receiveSelf(String message);
	
}
