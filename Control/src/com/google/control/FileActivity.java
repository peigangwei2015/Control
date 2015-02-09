package com.google.control;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.google.control.domain.FileInfo;
import com.google.control.domain.MsgType;
import com.google.control.utils.JsonUtils;
import com.google.control.utils.MsgUtils;
import com.google.control.utils.MyConstant;
import com.google.control.utils.Utils;

public class FileActivity extends BaseActivity implements OnItemClickListener, OnItemLongClickListener {
	private LinearLayout ll_load;
	private ListView lv_file;
	private List<FileInfo> fileList;
	private String[] iconNames = { "3gp", "7z", "accdb", "ace", "ai", "amr",
			"asx", "avi", "bat", "bmp", "chm", "css", "dat", "dll", "doc",
			"docx", "eps", "exe", "fla", "gif", "html", "ind", "ini", "jpg",
			"log", "mov", "mp3", "mp4", "mpeg", "pdf", "png", "ppt", "pptx",
			"proj", "psd", "pst", "pub", "rar", "swf", "txt", "tif", "url",
			"wav", "wma", "wmv", "xlsx", "xtm", "zip" };
	private int[] iconIds = { R.drawable._3gp, R.drawable._7z,
			R.drawable.accdb, R.drawable.ace, R.drawable.ai, R.drawable.amr,
			R.drawable.asx, R.drawable.avi, R.drawable.bat, R.drawable.bmp,
			R.drawable.chm, R.drawable.css, R.drawable.dat, R.drawable.dll,
			R.drawable.doc, R.drawable.docx, R.drawable.eps, R.drawable.exe,
			R.drawable.fla, R.drawable.gif, R.drawable.html, R.drawable.ind,
			R.drawable.ini, R.drawable.jpg, R.drawable.log, R.drawable.mov,
			R.drawable.mp3, R.drawable.mp4, R.drawable.mpeg, R.drawable.pdf,
			R.drawable.png, R.drawable.ppt, R.drawable.pptx, R.drawable.proj,
			R.drawable.psd, R.drawable.pst, R.drawable.pub, R.drawable.rar,
			R.drawable.swf, R.drawable.text, R.drawable.tif, R.drawable.url,
			R.drawable.wav, R.drawable.wma, R.drawable.wmv,
			R.drawable.xlsx_mac, R.drawable.xtm, R.drawable.zip };
	private FileAdapter adapter;
	private FileInfo fileInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file);
		init();
		setup();
		MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(),
				MsgType.READ_ROM_FILE_LIST);
		ll_load.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置事件处理
	 */
	private void setup() {
		// TODO Auto-generated method stub
		lv_file.setOnItemClickListener(this);
		lv_file.setOnItemLongClickListener(this);
	}

	/**
	 * 初始化组件
	 */
	private void init() {
		ll_load = (LinearLayout) findViewById(R.id.ll_load);
		lv_file = (ListView) findViewById(R.id.lv_file);
	}

	@Override
	public void receiveText(int fromUserId, String message) {
		if (!TextUtils.isEmpty(message)) {
			JSONObject jObj = JSONObject.parseObject(message);
			String type = jObj.getString(MsgType.TYPE);
			ll_load.setVisibility(View.INVISIBLE);
			if (MsgType.FILE_LIST.equals(type)) {
//				获取文件列表
				fileList = JsonUtils.json2list(jObj.getString(MsgType.DATA),
						FileInfo.class);
				adapter = new FileAdapter();
				lv_file.setAdapter(adapter);
			}else if(MsgType.DELETE_FILE_SUCCESS.equals(type)){
//				删除文件成功
				fileList.remove(fileInfo);
				adapter.notifyDataSetChanged();
			}else if(MsgType.DELETE_FILE_FAIL.equals(type)){
//				删除文件删除文件失败
				Toast.makeText(getApplicationContext(), "不好意思，删除文件失败喽！请重试", 1).show();
			}
		}
	}

	@Override
	public void receiveSelf(String message) {

	}

	/**
	 * @author Administrator 文件列表数据适配器
	 */
	private class FileAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return fileList.size();
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			ViewHolder holder;
			if (view == null) {
				holder = new ViewHolder();
				view = View.inflate(getApplicationContext(),
						R.layout.list_item_file, null);
				holder.icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.date = (TextView) view.findViewById(R.id.tv_date);
				holder.fileName = (TextView) view
						.findViewById(R.id.tv_file_name);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			FileInfo fileInfo = fileList.get(position);
			if (holder != null) {
				if (fileInfo.getParent() != null) {
					holder.date.setText(fileInfo.getParent());
				} else {
					holder.date.setText(Utils.formatDate(fileInfo
							.getCreateDate()));
				}
				holder.fileName.setText(fileInfo.getName());
				if (!fileInfo.isDir()) {
					setIcon(holder.icon, fileInfo);
				}
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
		public TextView fileName;
		public TextView date;
		public ImageView icon;
	}

	/**
	 * 设置图标
	 * 
	 * @param icon
	 * @param fileInfo
	 */
	public void setIcon(ImageView icon, FileInfo fileInfo) {
		String hz = fileInfo.getName().substring(
				fileInfo.getName().lastIndexOf(".") + 1);
		for (int i = 0; i < iconNames.length; i++) {
			if (iconNames[i].equalsIgnoreCase(hz)) {
				icon.setImageResource(iconIds[i]);
				return;
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> apater, View parent, int position,
			long arg3) {
		fileInfo = fileList.get(position);
		if (fileInfo != null && fileInfo.isDir()) {
			ll_load.setVisibility(View.VISIBLE);
			MsgUtils.send(getApplicationContext(),
					MyConstant.currentUser.getId(), MsgType.READ_FILE_LIST,
					fileInfo.getPath());
		} else {
			showOptMenu();
		}
	}

	/**
	 * 显示操作菜单
	 */
	private void showOptMenu() {
		AlertDialog.Builder builder = new Builder(this);
		String[] items = { "下载", "删除" };
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0: // 处理下载
					downloadFile();
					break;
				case 1: // 处理删除
					delFile();
					break;
				}
			}

		});
		builder.create().show();
	}

	/**
	 * 下载文件
	 */
	public void downloadFile() {
		confimDialog("警告", "你确认要下载此文件吗？",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 下载文件
						Toast.makeText(getApplicationContext(), "正在下载，请稍后...", 1)
								.show();
						MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(), MsgType.DOWNLOAD_FILE, fileInfo.getPath());
						dialog.dismiss();
					}
				});
	}

	/**
	 * 删除文件
	 */
	public void delFile() {
		confimDialog("警告", "你确认要删除此文件或文件夹吗？删除后不可以恢复呦！",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 删除文件
						ll_load.setVisibility(View.VISIBLE);
						MsgUtils.send(getApplicationContext(), MyConstant.currentUser.getId(), MsgType.DELETE_FILE, fileInfo.getPath());
						dialog.dismiss();
					}
				});
	}

	/**
	 * 确认对话框
	 * 
	 * @param title
	 * @param message
	 * @param listener
	 */
	private void confimDialog(String title, String message,
			DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("确认", listener);
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> apater, View parent, int position,
			long arg3) {
		
		fileInfo = fileList.get(position);
		if (fileInfo != null && fileInfo.isDir()) {
			showOptMenu();
		} 
		return true;
	}
}
