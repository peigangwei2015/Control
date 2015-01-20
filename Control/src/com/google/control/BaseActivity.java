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
	/**
	 * 发送信息
	 * @param command 信息类型
	 * @param bundle 参数
	 */
	protected void send(String command,Bundle bundle) {
		Intent intent=new Intent();
		intent.setAction(MyConstant.ANYCHAT_SERVICE_RECEIVE_ACTION);
		intent.putExtra("command", MyConstant.SEND);
		intent.putExtras(bundle);
		sendBroadcast(intent);
	}
	
	private class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			receiver(context,intent);
			
		}
	}


	public abstract void receiver(Context context, Intent intent) ;
}
