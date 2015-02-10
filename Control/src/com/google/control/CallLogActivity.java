package com.google.control;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.google.control.domain.CallLogInfo;
import com.google.control.domain.MsgType;
import com.google.control.utils.JsonUtils;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;
import com.google.control.utils.Utils;

public class CallLogActivity extends BaseActivity implements
		OnItemClickListener, OnScrollListener {
	private LinearLayout ll_load;
	private ListView lv_calllog;
	private List<CallLogInfo> calllogList;
	/**
	 * 偏移量
	 */
	private int offset = 0;
	private CallLogAdapter callLogAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calllog);
		init();
		setup();
		MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(),
				MsgType.READ_CALL_LOG_LIST, offset);
		ll_load.setVisibility(View.VISIBLE);
	}

	private void setup() {
		lv_calllog.setOnItemClickListener(this);
		lv_calllog.setOnScrollListener(this);
	}

	/**
	 * 初始化组件
	 */
	private void init() {
		ll_load = (LinearLayout) findViewById(R.id.ll_load);
		lv_calllog = (ListView) findViewById(R.id.lv_calllog);
	}

	@Override
	public void receiveText(int dwUserid, String message) {
		if (!TextUtils.isEmpty(message)) {
			ll_load.setVisibility(View.INVISIBLE);
			JSONObject jObj = JSONObject.parseObject(message);
			String type = jObj.getString(MsgType.TYPE);
			if (MsgType.CALL_LOG_LIST.equals(type)) {
				ll_load.setVisibility(View.INVISIBLE);
				if (calllogList!=null && callLogAdapter!=null) {
					List<CallLogInfo> temp = JsonUtils.json2list(jObj.getString(MsgType.DATA),
							CallLogInfo.class);
					calllogList.addAll(temp);
					callLogAdapter.notifyDataSetChanged();
				}else{
					calllogList = JsonUtils.json2list(jObj.getString(MsgType.DATA),
							CallLogInfo.class);
					callLogAdapter = new CallLogAdapter();
					lv_calllog.setAdapter(callLogAdapter);
				}
			}
		}
	}

	@Override
	public void receiveSelf(String message) {

	}

	/**
	 * 返回
	 * 
	 * @param v
	 */
	public void goBack(View v) {
		Intent intent = new Intent(this, ActionMenuActivity.class);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View parent, int position, long arg3) {
		CallLogInfo cInfo=calllogList.get(position);
		Intent intent = new Intent(this, CallLogDetailActivity.class);
		intent.putExtra("name", cInfo.getName());
		intent.putExtra("id", cInfo.getId());
		intent.putExtra("number", cInfo.getNumber());
		intent.putExtra("duration", cInfo.getDuration());
		intent.putExtra("date", cInfo.getDate());
		intent.putExtra("type", cInfo.getType());
		startActivity(intent);
	}

	/**
	 * @author Administrator 通话记录列表数据适配器
	 */
	private class CallLogAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return calllogList.size();
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ViewHolder holder;
			CallLogInfo callLogInfo = calllogList.get(position);
			if (view == null) {
				holder = new ViewHolder();
				view = View.inflate(getApplicationContext(),
						R.layout.list_item_calllog, null);
				holder.name = (TextView) view.findViewById(R.id.tv_name);
				holder.number = (TextView) view.findViewById(R.id.tv_number);
				holder.type = (ImageView) view.findViewById(R.id.iv_type);
				holder.date = (TextView) view.findViewById(R.id.tv_call_date);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			if (holder != null && callLogInfo != null) {
				// 设置名字和电话号码
				boolean isName = callLogInfo.getName() != null;
				if (isName) {
					holder.name.setText(callLogInfo.getName());
					holder.number.setText(callLogInfo.getNumber());
				} else {
					holder.name.setText(callLogInfo.getNumber());
					holder.number.setText("");

				}
				// 设置类型
				if (callLogInfo.getType() == 1) {
					holder.type.setImageResource(R.drawable.ic_incoming_call);
				} else if (callLogInfo.getType() == 2) {
					holder.type.setImageResource(R.drawable.ic_out_call);
				} else if (callLogInfo.getType() == 3) {
					holder.type.setImageResource(R.drawable.ic_missed_call);
				}
				// 设置时间
				holder.date.setText(Utils.formatDate(callLogInfo.getDate()));

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
		public TextView name;
		public ImageView type;
		public TextView number;
		public TextView date;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE:
			int lastPos = lv_calllog.getLastVisiblePosition();
			System.out.println(calllogList.size());
			if (lastPos == calllogList.size()-1) {
				Toast.makeText(getApplicationContext(), "加载数据..", 0).show();
				offset+=20;
				MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(),
						MsgType.READ_CALL_LOG_LIST, offset);
			}
			break;
		case OnScrollListener.SCROLL_STATE_FLING:
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			break;

		}
	}

}
