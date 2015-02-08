package com.google.control;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.control.domain.MsgType;
import com.google.control.domain.SmsInfo;
import com.google.control.utils.JsonUtils;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;
import com.google.control.utils.Utils;

public class SmsListActivity extends BaseActivity {
	private TextView tv_title;
	private LinearLayout ll_load;
	private ListView lv_sms;
	private List<SmsInfo> smsList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (MyConstant.currentUser==null) {
			Intent intent=new Intent(this, HomeActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_list);
		init();
		setUp();
		int id=getIntent().getIntExtra("id", Integer.MAX_VALUE);
		if (id!= Integer.MAX_VALUE) {
			ll_load.setVisibility(View.VISIBLE);
			MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(), MsgType.READ_SMS_LIST,id);
		}
		
		String name=getIntent().getStringExtra("name");
		tv_title.setText(name);
	}

	/**
	 * 设置事件监听
	 */
	private void setUp() {
	}

	/**
	 * 初始化组件
	 */
	private void init() {
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText(MyConstant.currentUser.getName());
		ll_load=(LinearLayout) findViewById(R.id.ll_load);
		lv_sms=(ListView) findViewById(R.id.lv_sms);
	}

	@Override
	public void receiveText(int fromUserId, String message) {
		if (!TextUtils.isEmpty(message)) {
			JSONObject jObj=JSONObject.parseObject(message);
			String type=jObj.getString(MsgType.TYPE);
			if (MsgType.SMS_LIST.equals(type)) {
				ll_load.setVisibility(View.INVISIBLE);
				smsList=JsonUtils.json2list(jObj.getString(MsgType.DATA), SmsInfo.class);
				lv_sms.setAdapter(new SmsAdapter());
			}
		}
		
	}

	@Override
	public void receiveSelf(String message) {
		
	}
	
	/**
	 * @author Administrator 短信列表数据适配器
	 */
	private class SmsAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return smsList.size();
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ViewHolder holder;
			if (view == null) {
				holder = new ViewHolder();
				view =  View.inflate(getApplicationContext(),
						R.layout.list_item_sms, null);
				holder.icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.date = (TextView) view.findViewById(R.id.tv_date);
				holder.body = (TextView) view.findViewById(R.id.tv_body);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			SmsInfo smsInfo = smsList.get(position);
			if (holder!=null) {
				holder.date.setText(Utils.formatDate(smsInfo.getDate()));
				holder.body.setText(smsInfo.getBody());
			}
			return view;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}
	}

	class ViewHolder {
		public TextView body;
		public TextView date;
		public ImageView icon;
	}

}
