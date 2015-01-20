package com.google.control;

import com.google.control.domain.User;
import com.google.control.utils.MyConstant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ConversActivity extends BaseActivity {
	/**
	 * 当前用户
	 */
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_convers);
		user = (User) getIntent().getExtras().getSerializable("user");
	}

	@Override
	public void receiver(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
	}
}
