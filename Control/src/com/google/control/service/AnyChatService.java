package com.google.control.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.bairuitech.anychat.AnyChatTextMsgEvent;
import com.bairuitech.anychat.AnyChatTransDataEvent;
import com.google.control.domain.MsgType;
import com.google.control.domain.User;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;

/**
 * 用户传送信息的服务
 */
public class AnyChatService extends Service implements AnyChatBaseEvent,
		AnyChatTransDataEvent, AnyChatTextMsgEvent {
	private static final String TAG = "AnyChatService";
	private static final String MY_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Controller/Download";
	/**
	 * AnyChat通讯对象
	 */
	private AnyChatCoreSDK anyChatSDK;
	/**
	 * 本地视频自动旋转控制
	 */
	private final int LOCALVIDEOAUTOROTATION = 1;
	/**
	 * 服务器端端口
	 */
	private int mServerPort = 8906;
	/**
	 * 进入房间ID
	 */
	private int mSRoomID = 1;
	/**
	 * 服务器端IP
	 */
	private String mServerIP = "192.168.1.101";
	/**
	 * 用户名
	 */
	private String mName = "Admin";
	/**
	 * 用户ID
	 */
	private int mUserId;
	/**
	 * 用户在线列表
	 */
	public static List<User> onlineList = null;

	private SharedPreferences sp;
	private BroadcastReceiver receiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		onlineList = new ArrayList<User>();
		// 连接服务器
		String serverIP = sp.getString("serverIP", null);
		if (!TextUtils.isEmpty(serverIP))
			connServer();
		// 注册接受信息广播接受者
		regRec();
	}

	/**
	 * 连接服务器
	 */
	public void connServer() {
		// 初始化SDK
		initSDK();
		// 初始化数据
		initData();
		Log.v(TAG, "连接服务器  ip:" + mServerIP + "   port:" + mServerPort);
		// 连接服务器
		anyChatSDK.Connect(mServerIP, mServerPort);
		// 登陆服务器
		anyChatSDK.Login(mName, mName);
		// 注册接受信息事件
		anyChatSDK.SetTransDataEvent(this);
		anyChatSDK.SetTextMessageEvent(this);
	}

	/**
	 * 注册接受信息广播接受者
	 */
	private void regRec() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(MyConstant.ANYCHAT_SERVICE_RECEIVE_ACTION);
		receiver = new MyReceiver();
		registerReceiver(receiver, filter);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		mServerIP = sp.getString("serverIP", mServerIP);
		mServerPort = sp.getInt("serverPort", mServerPort);
	}

	/**
	 * 初始化SDK
	 */
	private void initSDK() {
		if (anyChatSDK == null) {
			anyChatSDK = AnyChatCoreSDK.getInstance(this);
			anyChatSDK.SetBaseEvent(this);
			anyChatSDK.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION,
					LOCALVIDEOAUTOROTATION);
		}
	}

	/**
	 * 发送信息
	 * 
	 * @param id
	 *            接受者ID
	 * @param msg
	 *            信息内容
	 */
	public void send(int id, String msg) {
		if (anyChatSDK != null) {
			anyChatSDK.SendTextMessage(id, 0, msg);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 取消接受信息的广播接受者
		unregisterReceiver(receiver);
		// 退出并销毁
		anyChatSDK.Logout();
		anyChatSDK.Release();

	}

	/**
	 * 连接服务器消息, bSuccess表示是否连接成功
	 */
	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		if (bSuccess) {
			Log.v(TAG, mName + "连接服务器成功");
			Toast.makeText(getApplicationContext(), "连接服务器成功", 1).show();
			test();
		} else {
			Log.v(TAG, mName + "连接服务器失败");
			Toast.makeText(getApplicationContext(), "连接服务器失败", 1).show();
		}

	}

	/**
	 * 用户登录消息，dwUserId表示自己的用户ID号，dwErrorCode表示登录结果：0 成功，否则为出错代码
	 */
	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		Log.v(TAG, dwUserId + "登陆成功");
		// 登陆成功进入房间
		if (dwErrorCode == 0) {
			mUserId = dwUserId;
			anyChatSDK.EnterRoom(mSRoomID, "");
		}
	}

	/**
	 * 用户进入房间消息，dwRoomId表示所进入房间的ID号，dwErrorCode表示是否进入房间：0成功进入，否则为出错代码
	 */
	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		if (dwErrorCode == 0) {
			Log.v(TAG, mName + "进入" + dwRoomId + "号房间成功");
			int[] users = anyChatSDK.GetOnlineUser();

			for (int i = 0; i < users.length; i++) {
				int userId = users[i];
				String userName = anyChatSDK.GetUserName(userId);
				User user = new User();
				user.setId(userId);
				user.setName(userName);
				user.setOnline(true);
				onlineList.add(user);
				Log.v(TAG, userName + "在线");

			}
			MsgUtils.sendMsg(getApplicationContext(),
					MyConstant.USER_ONLINE_LIST);
		} else {
			Log.e(TAG, "进入房间出错，错误码：" + dwErrorCode);
		}
	}
	
	
	/**
	 * 测试数据
	 */
	public void test(){
		User user = new User();
		user.setId(1);
		user.setName("张三");
		user.setOnline(true);
		onlineList.add(user);
		MsgUtils.sendMsg(getApplicationContext(),
				MyConstant.USER_ONLINE_LIST);
	}

	/**
	 * 网络断开消息，该消息只有在客户端连接服务器成功之后，网络异常中断之时触发，dwErrorCode表示连接断开的原因
	 */
	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {

	}

	/**
	 * 房间在线用户消息，进入房间后触发一次，dwUserNum表示在线用户数（包含自己），dwRoomId表示房间ID
	 */
	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {

	}

	/**
	 * 用户进入/退出房间消息，dwUserId表示用户ID号，bEnter表示该用户是进入（TRUE）或离开（FALSE）房间
	 */
	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		Log.v(TAG, "OnAnyChatUserAtRoomMessage");
		if (dwUserId != mUserId) {
			if (bEnter) {
				// 用户上线
				String userName = anyChatSDK.GetUserName(dwUserId);
				User user = new User();
				user.setId(dwUserId);
				user.setName(userName);
				user.setOnline(true);
				onlineList.add(user);
			} else {
				// 用户离线
				onlineList.remove(findUser(dwUserId));
			}
			MsgUtils.sendMsg(getApplicationContext(),
					MyConstant.USER_ONLINE_CHANGE);
		}
	}

	/**
	 * 查找用户
	 * @param dwUserId
	 * @return
	 */
	private User findUser(int dwUserId) {
		for (User user : onlineList) {
			if (user.getId() == dwUserId) 
			{
				return user;
			}
		}
		return null;
	}

	/**
	 * 接受信息的广播接受者
	 */
	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String command = intent.getStringExtra("command");
			// 连接服务器
			if ("connServer".equals(command)) {
				connServer();
			} else if (MyConstant.RECONN_SERVER.equals(command)) {
				// 重新连接服务器
				reConnServer();
			} else if (MyConstant.SEND.equals(command)) {
				// 发送消息
				int id = intent.getIntExtra("id", -1);
				String msg = intent.getStringExtra("msg");
				send(id, msg);
			}
		}

	}

	/**
	 * 重新连接服务器
	 */
	public void reConnServer() {
		Log.v(TAG, "重新连接服务器");
		if (anyChatSDK != null) {
			anyChatSDK.Logout();
			anyChatSDK.Release();
			anyChatSDK = null;
		}
		connServer();
	}

	// @Override
	// /*
	// * 文字消息通知,dwFromUserid表示消息发送者的用户ID号，dwToUserid表示目标用户ID号，可能为-1，表示对大家说，
	// * bSecret表示是否为悄悄话
	// */
	// public void OnAnyChatTextMessage(int dwFromUserid, int dwToUserid,
	// boolean bSecret, String message) {
	// MsgUtils.sendTextToActivity(getApplicationContext(),dwFromUserid,
	// dwToUserid, bSecret, message);
	// }

	@Override
	public void OnAnyChatTransFile(int dwUserid, String FileName,
			String TempFilePath, int dwFileLength, int wParam, int lParam,
			int dwTaskId) {
		moveFile(TempFilePath,FileName,dwUserid);
	}

	/**
	 * 移动文件
	 * @param tempFilePath
	 * @param fileName 
	 * @param dwUserid 
	 */
	private void moveFile(String tempFilePath, String fileName, int dwUserid) {
		if (checkDir(MY_DIR)) {
			FileInputStream fis=null;
			FileOutputStream fos=null;
			try {
				File temp=new File(tempFilePath);
				 fis=new FileInputStream(temp);
				 fos=new FileOutputStream(MY_DIR+"/"+fileName);
				byte[] buffer=new byte[1024];
				int len=0;
				while((len=fis.read(buffer))!=-1){
					fos.write(buffer, 0, len);
				}
				fos.flush();
				temp.delete();
				Toast.makeText(getApplicationContext(), fileName+"下载成功", 1).show();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), fileName+"下载失败", 1).show();
				e.printStackTrace();
			}finally{
				try {
					if (fis!=null) {
						fis.close();
						fis=null;
					}
					if (fos!=null) {
						fos.close();
						fos=null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	/**
	 * 检查文件夹是否创建
	 * @param myDir
	 * @return
	 */
	private boolean checkDir(String myDir) {
		File file=new File(myDir);
		if (file.exists()) {
			return true;
		}
		return file.mkdirs();
	}

	@Override
	public void OnAnyChatTransBuffer(int dwUserid, byte[] lpBuf, int dwLen) {
	}

	@Override
	public void OnAnyChatTransBufferEx(int dwUserid, byte[] lpBuf, int dwLen,
			int wparam, int lparam, int taskid) {
		try {
			MsgUtils.sendTextToActivity(getApplicationContext(), dwUserid,
					new String(lpBuf, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatSDKFilterData(byte[] lpBuf, int dwLen) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatTextMessage(int dwFromUserid, int dwToUserid,
			boolean bSecret, String message) {
		MsgUtils.sendTextToActivity(getApplicationContext(), dwFromUserid,
				message);
	}

}
