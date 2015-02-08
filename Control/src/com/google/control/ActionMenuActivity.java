package com.google.control;

import com.google.control.domain.User;
import com.google.control.utils.MyConstant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 功能菜单
 */
public class ActionMenuActivity extends BaseActivity implements
		OnItemClickListener {
	private GridView gv_action_menu;
	private MenuAdapter adapter;
	private int[] ids = { R.drawable.ic_sms, R.drawable.ic_insert_sms,
			R.drawable.ic_contact, R.drawable.ic_call, R.drawable.ic_location,
			R.drawable.ic_folder, R.drawable.ic_voice, R.drawable.ic_video,
			R.drawable.ic_setting };
	private String[] names = { "短信", "虚拟短信", "通讯录", "通话记录 ", "定位", "文件", "语音",
			"视频", "设置中心" };
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_menu);
		if (MyConstant.currentUser == null) {
			goBack(null);
			return;
		}
		gv_action_menu = (GridView) findViewById(R.id.gv_action_menu);
		adapter = new MenuAdapter();
		gv_action_menu.setAdapter(adapter);
		gv_action_menu.setOnItemClickListener(this);
	}

	/**
	 * 返回
	 * 
	 * @param v
	 */
	public void goBack(View v) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	private class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return ids.length;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			view = View.inflate(getApplicationContext(),
					R.layout.list_item_action_menu, null);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			iv_icon.setImageResource(ids[position]);
			tv_name.setText(names[position]);
			return view;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long arg3) {
		switch (position) {
		case 0: // 进入短信会话页面
			intent = new Intent(ActionMenuActivity.this,
					ConversActivity.class);
			startActivity(intent);
			break;
			
		case 5: // 进入文件浏览
			 intent = new Intent(ActionMenuActivity.this,
					FileActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void receiveText(int fromUserId, String message) {

	}

	@Override
	public void receiveSelf(String message) {

	}
}
