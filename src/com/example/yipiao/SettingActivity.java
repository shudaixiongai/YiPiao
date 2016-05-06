package com.example.yipiao;

import java.sql.Savepoint;

import com.example.been.FeedBack;

import DataClearManager.DataClearManager;
import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class SettingActivity extends Activity {
	private RelativeLayout rl_setting_icon, rl_setting_back;
	private LinearLayout ll_settting_people, ll_setting_clear;
	private TextView tv_clear;
	String totalCacheSize = null;
	private Button btn_setting_ok;
	DataClearManager clearManager;
	private EditText et_phone, et_contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.other);
		setview();
	}

	private void setview() {
		ll_settting_people = (LinearLayout) findViewById(R.id.ll_setting_people_icon);
		ll_settting_people.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this, IconActivity.class);
				startActivity(intent);

			}
		});
		tv_clear = (TextView) findViewById(R.id.tv_clear);
		clearManager = new DataClearManager();
		try {
			totalCacheSize = DataClearManager.getTotalCacheSize(SettingActivity.this);
			tv_clear.setText(totalCacheSize);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ll_setting_clear = (LinearLayout) findViewById(R.id.ll_setting_clean);
		ll_setting_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DataClearManager.cleanSharedPreference(SettingActivity.this);
				Toast.makeText(getApplicationContext(), "清除成功", 1).show();
			}
		});
		rl_setting_back = (RelativeLayout) findViewById(R.id.rl_setting_backSF);
		rl_setting_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this, FeedBackActivity.class);
				startActivity(intent);

			}
		});
	}
}
