package com.google.control;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.control.domain.User;
import com.google.control.service.AnyChatService;
import com.google.control.utils.MyConstant;

public class HomeActivity extends BaseActivity implements OnItemClickListener {
	private static final String TAG = "MainActivity";
	private SharedPreferences sp;
	private Intent intent;
	private ListView lv_user_online;
	private UserOnlineAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		String serverIP = sp.getString("serverIP", null);

		intent = new Intent(this, AnyChatService.class);
		startService(intent);

		if (TextUtils.isEmpty(serverIP)) {
			intent = new Intent(this, ServerConfigActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		lv_user_online = (ListView) findViewById(R.id.lv_user_online);
		lv_user_online.setOnItemClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (AnyChatService.onlineList != null) {
			// 在线列表
			adapter = new UserOnlineAdapter();
			lv_user_online.setAdapter(adapter);
		}
	}

	/**
	 * 设置服务器参数
	 * 
	 * @param v
	 */
	public void setServerConfig(View v) {
		Intent intent = new Intent(this, ServerConfigActivity.class);
		startActivity(intent);
	}


	private class UserOnlineAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return AnyChatService.onlineList.size();
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ViewHolder holder = null;
			if (view == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.list_item_user_online, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
				holder.tv_status = (TextView) view.findViewById(R.id.tv_status);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			User user = AnyChatService.onlineList.get(position);

			holder.tv_name.setText(user.getName());

			return view;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}
	}

	class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_status;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long arg3) {
		MyConstant.currentUser = AnyChatService.onlineList.get(position);
		Intent intent = new Intent(this, ActionMenuActivity.class);
		startActivity(intent);
	}

	@Override
	public void receiveText(int fromUserId, String message) {
		
	}

	@Override
	public void receiveSelf(String message) {
		// TODO Auto-generated method stub
		if (message.equals(MyConstant.USER_ONLINE_CHANGE)) {
			if (adapter != null)
				adapter.notifyDataSetChanged();
			Log.v(TAG, "在线列表发生改变");
		} else if (message.equals(MyConstant.USER_ONLINE_LIST)) {
			// 在线列表
			adapter = new UserOnlineAdapter();
			lv_user_online.setAdapter(adapter);
		}
	}

}
