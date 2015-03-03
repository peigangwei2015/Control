package com.google.control;

import me.dawson.kisstools.utils.JSONUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapView;
import com.google.control.domain.LocationInfo;
import com.google.control.domain.MsgType;
import com.google.control.utils.JsonUtils;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;

public class LocationActivity extends BaseActivity {
	/**
	 * 百度地图的管理器
	 */
	private BMapManager manager;
	private MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initBMap();
		 setContentView(R.layout.activity_location);
		MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(),
				MsgType.START_LOCAATION);

	}

	/**
	 * 初始化百度地图
	 */
	private void initBMap() {
		manager=new BMapManager(getApplicationContext());
		manager.init(MyConstant.BD_MAP_KEY, new MKGeneralListener() {
			
			@Override
			public void onGetPermissionState(int iError) {
				Toast.makeText(getApplicationContext(), "Key验证失败，请检查Key是否正确！", 1).show();
			}
			
			@Override
			public void onGetNetworkState(int iError) {
				if (iError== MKEvent.ERROR_NETWORK_CONNECT) {
					Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", 1)
							.show();
				}
				
			}
		});
	}

	@Override
	public void receiveText(int dwUserid, String message) {
		if (JSONUtil.isJSON(message)) {
			JSONObject jObj = JSONObject.parseObject(message);
			String type = jObj.getString(MsgType.TYPE);
			if (MsgType.LOCAATION.equals(type)) {
				LocationInfo location = JsonUtils.json2obj(
						jObj.getString(MsgType.DATA), LocationInfo.class);
				
				
			}
		}
	}

	@Override
	public void receiveSelf(String message) {

	}

	@Override
	protected void onStop() {
		MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(),
				MsgType.STOP_LOCAATION);
		super.onStop();
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
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
