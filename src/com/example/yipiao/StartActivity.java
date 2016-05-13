package com.example.yipiao;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.a.a.be;
import com.example.been.InBeen;
import com.example.piao.fragment.HomeFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class StartActivity extends Activity {
	private EditText et_start_content, et_start_title, et_start_option_one, et_start_option_two, et_start_option_three;
	private TextView tv_start_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.start_mian);
		setview();
	}

	private void setview() {
		et_start_title = (EditText) findViewById(R.id.et_start_title);
		et_start_option_one = (EditText) findViewById(R.id.et_start_option_one);
		et_start_option_three = (EditText) findViewById(R.id.et_start_option_three);
		et_start_option_two = (EditText) findViewById(R.id.et_start_option_two);
		et_start_content = (EditText) findViewById(R.id.et_start_content);
		tv_start_ok = (TextView) findViewById(R.id.tv_start_ok);
		tv_start_ok.setOnClickListener(new oklistener());

	}

	class oklistener implements OnClickListener {

		@Override
		public void onClick(View v) {
			saveinbeen();
		}
	}

	private void saveinbeen() {
		InBeen been = new InBeen();
		String title = et_start_title.getText().toString();
		String option_one = et_start_option_one.getText().toString();
		String option_two = et_start_option_two.getText().toString();
		String option_three = et_start_option_three.getText().toString();
		String content = et_start_content.getText().toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = dateFormat.format(curDate);
		been.setTitle(title);
		been.setOption_one(option_one);
		been.setOption_three(option_three);
		been.setOption_two(option_two);
		been.setExplain(content);
		been.setTime(str);
		been.save(StartActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, MainActivity.class);
				startActivity(intent);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(getApplicationContext(), "投票失败", Toast.LENGTH_SHORT).show();
			}
		});

	}
}
