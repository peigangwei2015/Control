package com.google.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatus.Builder;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.google.control.domain.LocationInfo;
import com.google.control.domain.MsgType;
import com.google.control.utils.JsonUtils;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;

public class LocationActivity extends BaseActivity {
	private SDKReceiver mReceiver;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private BaiduMapOptions baiduMapOptions;
	private MapStatus mMapStatus;

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class SDKReceiver extends BroadcastReceiver {
		private static final String LTAG = "SDKReceiver";

		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			Log.d(LTAG, "action: " + s);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Toast.makeText(getApplicationContext(), "KAY认证出错！", 1).show();
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Toast.makeText(getApplicationContext(), "网络错误！", 1).show();

			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_location);
		MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(),
				MsgType.START_LOCAATION);

		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);

		baiduMapOptions = new BaiduMapOptions();
		mMapView = new MapView(this, baiduMapOptions);
		setContentView(mMapView);
		mBaiduMap = mMapView.getMap();

	}

	@Override
	public void receiveText(int dwUserid, String message) {
		if (!TextUtils.isEmpty(message)) {
			JSONObject jObj = JSONObject.parseObject(message);
			String type = jObj.getString(MsgType.TYPE);
			if (MsgType.LOCAATION.equals(type)) {
				LocationInfo location = JsonUtils.json2obj(
						jObj.getString(MsgType.DATA), LocationInfo.class);
				MapStatus.Builder builder = new Builder();
				LatLng mLatlng = new LatLng(location.getLatitude(),
						location.getLongitude());
				mMapStatus = builder.target(mLatlng).build();
				baiduMapOptions.mapStatus(mMapStatus);
				mMapView.refreshDrawableState();
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
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		super.onDestroy();
		// 取消监听 SDK 广播
		unregisterReceiver(mReceiver);
	}

}
