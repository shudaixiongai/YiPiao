package com.example.yipiao;

import com.example.been.FeedBack;

import DataClearManager.DataClearManager;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class FeedBackActivity extends Activity {
	private Button btn_setting_ok;
	DataClearManager clearManager;
	private EditText et_phone, et_contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.feedback);
		setview();
	}

	private void setview() {
		et_contact = (EditText) findViewById(R.id.et_feedback_content);
		et_phone = (EditText) findViewById(R.id.et_feedback_phone);
		btn_setting_ok = (Button) findViewById(R.id.btn_feedback_ok);
		btn_setting_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				save();
			}
		});
	}

	public void save() {
		FeedBack back = new FeedBack();
		String get_phone = et_phone.getText().toString();
		String get_contact = et_contact.getText().toString();

		if (TextUtils.isEmpty(get_contact) && TextUtils.isEmpty(get_phone)) {
			Toast.makeText(getApplicationContext(), "内容不能为空，请输入内容", 1).show();
		} else {
			back.setPhone(get_phone);
			back.setContact(get_contact);
			back.save(this, new SaveListener() {

				@Override
				public void onSuccess() {
					Toast.makeText(FeedBackActivity.this, "反馈成功，信息已经上传到服务器，非常感谢你提出的反馈信息 ！", 1).show();
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					Toast.makeText(getApplicationContext(), "反馈失败，网络没连接？", 1).show();

				}
			});
		}

	}
}
