package com.google.control;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.control.domain.ContactInfo;
import com.google.control.domain.MsgType;
import com.google.control.utils.JsonUtils;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;

public class ContactActivity extends BaseActivity implements OnItemClickListener {
	private LinearLayout ll_load;
	private ListView lv_contact;
	private List<ContactInfo> contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		init();
		setup();
		MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(),
				MsgType.READ_CONTACT_LIST);
		ll_load.setVisibility(View.VISIBLE);
	}

	private void setup() {
		lv_contact.setOnItemClickListener(this);
	}

	/**
	 * 初始化组件
	 */
	private void init() {
		ll_load = (LinearLayout) findViewById(R.id.ll_load);
		lv_contact = (ListView) findViewById(R.id.lv_contact);
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
	public void receiveText(int dwUserid, String message) {
		if (!TextUtils.isEmpty(message)) {
			JSONObject jObj = JSONObject.parseObject(message);
			String type = jObj.getString(MsgType.TYPE);
			if (MsgType.CONTACT_LIST.equals(type)) {
				ll_load.setVisibility(View.INVISIBLE);
				contactList = JsonUtils.json2list(jObj.getString(MsgType.DATA),
						ContactInfo.class);
				lv_contact.setAdapter(new ContactAdapter());
			}
		}
	}

	@Override
	public void receiveSelf(String message) {

	}

	/**
	 * @author Administrator 短信列表数据适配器
	 */
	private class ContactAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return contactList.size();
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ViewHolder holder;
			ContactInfo contactInfo = contactList.get(position);
			if (view == null) {
				holder = new ViewHolder();
				view = View.inflate(getApplicationContext(),
						R.layout.list_item_contact, null);
				holder.icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.name = (TextView) view.findViewById(R.id.tv_name);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			if (holder != null && contactInfo != null) {
				holder.name.setText(contactInfo.getName());
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
		public ImageView icon;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View praent, int position, long arg3) {
		ContactInfo contactInfo=contactList.get(position);
		if (contactInfo!=null) {
			Intent intent=new Intent(ContactActivity.this, ContactDetailActivity.class);
			intent.putExtra("name", contactInfo.getName());
			intent.putExtra("number", contactInfo.getNumber());
			startActivity(intent);
			
		}
		
	}

}
