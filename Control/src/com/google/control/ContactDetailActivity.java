package com.google.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactDetailActivity extends BaseActivity {
	private TextView tv_title,tv_name,tv_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_detail);
		init();
	}

	private void init() {
		String name=getIntent().getStringExtra("name");
		String number=getIntent().getStringExtra("number");
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_number=(TextView) findViewById(R.id.tv_number);
		tv_title.setText(name);
		tv_name.setText(name);
		tv_number.setText(number);
		
		
	}
	
	/**
	 * 返回
	 * 
	 * @param v
	 */
	public void goBack(View v) {
		Intent intent = new Intent(this, ContactActivity.class);
		startActivity(intent);
	}

	@Override
	public void receiveText(int dwUserid, String message) {

	}

	@Override
	public void receiveSelf(String message) {
		// TODO Auto-generated method stub

	}

}
