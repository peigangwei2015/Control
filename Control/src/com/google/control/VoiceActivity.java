package com.google.control;

import java.util.Timer;
import java.util.TimerTask;

import me.dawson.kisstools.utils.JSONUtil;
import me.dawson.kisstools.utils.TimeUtil;

import com.google.control.domain.MsgType;
import com.google.control.utils.DialogUtils;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;
import com.google.control.utils.Utils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 语音界面
 * 
 * @author Administrator
 * 
 */
public class VoiceActivity extends BaseActivity implements OnClickListener {
	/**
	 * 本地录音按钮
	 */
	private Button bt_loc_record;
	/**
	 * 显示录音的时间
	 */
	private TextView tv_record_time;

	/**
	 * 是否开始录音
	 */
	private boolean isRecord = false;

	/**
	 * 录音的时间
	 */
	private int recordTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice);
		init();
		setup();

	}

	/**
	 * 绑定事件
	 */
	private void setup() {
		bt_loc_record.setOnClickListener(this);
	}

	/**
	 * 初始化组件
	 */
	private void init() {
		// TODO Auto-generated method stub
		bt_loc_record = (Button) findViewById(R.id.bt_loc_record);
		tv_record_time = (TextView) findViewById(R.id.tv_record_time);

	}

	@Override
	public void receiveText(int dwUserid, String message) {
		if (JSONUtil.isJSON(message)) {
			String type = JSONUtil.getString(JSONUtil.parseObject(message), "type");
			pd.dismiss();
			if (MsgType.START_VOICE_RECORD_SUCCESS.equals(type)) {
				mHandler.sendMessageDelayed(mHandler.obtainMessage(), 1000);
			}
		}

	}

	@Override
	public void receiveSelf(String message) {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 本地录音
		case R.id.bt_loc_record:
			locRecordProcess();
			break;

		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (isRecord) {
				recordTime++;
				mHandler.sendMessageDelayed(mHandler.obtainMessage(), 1000);
				String sTime = Utils.formatTime(recordTime);
				tv_record_time.setText("录音时长： "+sTime);
			}

		}

	};
	private ProgressDialog pd;

	
	/**
	 * 处理本地录音
	 */
	private void locRecordProcess() {
		isRecord = !isRecord;
		if (isRecord) {
			// 处理开始录音
			bt_loc_record.setText("停止本地录音");
			MsgUtils.send(getApplicationContext(),
					MyConstant.currentUser.getId(), MsgType.START_VOICE_RECORD);
		} else {
			// 处理停止录音
			bt_loc_record.setText("开始本地录音");
			MsgUtils.send(getApplicationContext(),
					MyConstant.currentUser.getId(), MsgType.STOP_VOICE_RECORD);
		}
		pd=DialogUtils.showProgressDialog(this, "提示", "正在处理中，请稍后...");

	}

}
