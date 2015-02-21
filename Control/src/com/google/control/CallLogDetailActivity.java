package com.google.control;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.google.control.domain.MsgType;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;
import com.google.control.utils.Utils;

/**
 * 呼叫记录详情 界面
 * @author Administrator
 *
 */
public class CallLogDetailActivity extends BaseActivity {
	private TextView tv_name,tv_number,tv_date,tv_duration;
	private ImageView iv_type;
	private String name;
	private String number;
	private int duration;
	private long date;
	private int type;
	private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calllog_detail);
		Intent intent=getIntent();
		id=intent.getIntExtra("id", Integer.MAX_VALUE);
		 name=intent.getStringExtra("name");
		 number=intent.getStringExtra("number");
		 duration=intent.getIntExtra("duration", 0);
		 date=intent.getLongExtra("date", 0);	
		 type=intent.getIntExtra("type",0);
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_number=(TextView) findViewById(R.id.tv_number);
		tv_duration=(TextView) findViewById(R.id.tv_duration);
		tv_date=(TextView) findViewById(R.id.tv_date);
		iv_type=(ImageView) findViewById(R.id.iv_type);
		
		
		tv_name.setText(TextUtils.isEmpty(name)?"回拨":"呼叫 "+name);
		tv_number.setText("手机  "+number);
		tv_duration.setText(Utils.formatDuration(duration));
		tv_date.setText(Utils.formatDate("yyyy年MM月dd日 HH:mm:ss",date));
		
		// 设置类型
		if (type == 1) {
			iv_type.setImageResource(R.drawable.ic_incoming_call);
		} else if (type == 2) {
			iv_type.setImageResource(R.drawable.ic_out_call);
		} else if (type == 3) {
			iv_type.setImageResource(R.drawable.ic_missed_call);
		}
	}
	/**
	 * 删除通话记录
	 * @param v
	 */
	public void deleteCallLog(View v){
		if(id!=Integer.MAX_VALUE){
			MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(), MsgType.DEL_CALL_LOG, id);
		}
	}


	@Override
	public void receiveText(int dwUserid, String message) {
		if (!TextUtils.isEmpty(message)) {
			JSONObject jObj = JSONObject.parseObject(message);
			String type = jObj.getString(MsgType.TYPE);
			if (MsgType.DEL_CALL_LOG_SUCCESS.equals(type)) {
				Toast.makeText(getApplicationContext(), "删除成功。", 1).show();
			}else if(MsgType.DEL_CALL_LOG_FAIL.equals(type)){
				Toast.makeText(getApplicationContext(), "删除失败。", 1).show();
			}
		}
	}

	@Override
	public void receiveSelf(String message) {
		
	}
}
