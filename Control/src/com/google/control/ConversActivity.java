package com.google.control;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.control.domain.ConversInfo;
import com.google.control.domain.MsgType;
import com.google.control.domain.User;
import com.google.control.utils.JsonUtils;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.Utils;

public class ConversActivity extends BaseActivity {
	private static final String TAG = "ConversActivity";
	/**
	 * 当前用户
	 */
	private User user;
	/**
	 * 加载中画面
	 */
	private LinearLayout ll_load;
	/**
	 * 会话列表
	 */
	private ListView lv_convers;
	private List<ConversInfo> conversList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_convers);
		user = (User) getIntent().getExtras().getSerializable("user");
		MsgUtils.send(getApplicationContext(),user.getId(), MsgType.READ_CONVERS_LIST);
		lv_convers=(ListView) findViewById(R.id.lv_convers);
		ll_load=(LinearLayout) findViewById(R.id.ll_load);
		ll_load.setVisibility(View.VISIBLE);
	}


	@Override
	public void receiveText(int fromUserId, int toUserId, String message) {
		try {
			JSONObject jsonObject=new JSONObject(message);
			String type=jsonObject.getString("type");
			if (type.equals(MsgType.CONVERS_LIST)) {
				ll_load.setVisibility(View.INVISIBLE);
				Log.v(TAG, "获取到会话列表");
				conversList=JsonUtils.json2list(jsonObject.getString("data"), ConversInfo.class);
				lv_convers.setAdapter(new ConversAdapter());
				
			}
		} catch (JSONException e) {
			Log.e(TAG,"Json格式不正确");
			e.printStackTrace();
		}	
	}


	@Override
	public void receiveSelf(String message) {
			
	}
	
	private class ConversAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return conversList.size();
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ViewHolder holder;
			if (view == null) {
				holder=new ViewHolder();
				view=View.inflate(getApplicationContext(), R.layout.list_item_convers, null);
				holder.icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.name = (TextView) view.findViewById(R.id.tv_name);
				holder.date = (TextView) view.findViewById(R.id.tv_date);
				holder.number = (TextView) view.findViewById(R.id.tv_number);
				holder.body = (TextView) view.findViewById(R.id.tv_body);
				view.setTag(holder);
			}else {
				holder=(ViewHolder) view.getTag();
			}
			ConversInfo conversInfo=conversList.get(position);
			holder.name.setText(conversInfo.getName()!=null ?conversInfo.getName():conversInfo.getAddress());
			holder.number.setText(conversInfo.getCount()+"");
			holder.date.setText(Utils.formatDate("MM-dd HH:mm:ss",conversInfo.getDate()));
			holder.body.setText(conversInfo.getBody());
			
			return view;
		}

	

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	class ViewHolder{
		public TextView body;
		public TextView number;
		public TextView date;
		public TextView name;
		public ImageView icon;
	}
}
