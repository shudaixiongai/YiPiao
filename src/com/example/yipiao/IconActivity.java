package com.example.yipiao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import com.parcool.mycroppicandupload.utils.AppConstant;
import com.parcool.mycroppicandupload.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IconActivity extends Activity implements OnClickListener {
	private RelativeLayout rl_setting_icon;
	private ImageView iv_people_icon;
	private EditText et_people_name, et_people_qianmi;
	private LinearLayout ll_people_data;
	private TextView tv_people_qianmin, tv_people_name, tv_people_data;
	DatePicker datapicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.peoplesetting);
		setview();
	}

	private void setview() {
		ll_people_data = (LinearLayout) findViewById(R.id.ll_people_data);
		tv_people_name = (TextView) findViewById(R.id.tv_people_name);
		et_people_name = (EditText) findViewById(R.id.et_people_name);
		tv_people_data = (TextView) findViewById(R.id.tv_people_data);
		et_people_qianmi = (EditText) findViewById(R.id.et_people_qianming);
		iv_people_icon = (ImageView) findViewById(R.id.iv_people_icon);
		rl_setting_icon = (RelativeLayout) findViewById(R.id.rl_setting_people_icon);
		rl_setting_icon.setOnClickListener((OnClickListener) this);
		SharedPreferences nicheng = this.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor edit = nicheng.edit();
		String get_name = nicheng.getString("name", "");
		String get_qianmin = nicheng.getString("qianmin", "");
		Log.i("name", get_qianmin);
		et_people_name.setText(get_name);
		et_people_qianmi.setText(get_qianmin);
		ll_people_data.setOnClickListener(this);
	}

	CharSequence getSavedText() {
		return ((TextView) findViewById(R.id.et_people_name)).getText();
	}

	void setSavedText(CharSequence text) {
		((TextView) findViewById(R.id.et_people_name)).setText(text);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences nicheng = this.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = nicheng.edit();
		editor.putString("name", et_people_name.getText().toString());
		editor.putString("qianmin", et_people_qianmi.getText().toString());
		editor.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_setting_people_icon:
			Utils.getInstance().selectPicture(this);
			break;
		case R.id.ll_people_data:
			initdata();
			break;
		}

	}

	// 日期选择器
	private void initdata() {
		Calendar calendar = Calendar.getInstance();
		AlertDialog.Builder builder = new AlertDialog.Builder(IconActivity.this);
		View dataFview = getLayoutInflater().inflate(R.layout.datapicker, null);
		datapicker = (DatePicker) dataFview.findViewById(R.id.datapicker);
		datapicker.setCalendarViewShown(false);
		// 设置时间详细
		calendar.setTimeInMillis(System.currentTimeMillis());
		datapicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				null);
		builder.setView(dataFview);
		builder.setTitle("设置日期信息");
		builder.setPositiveButton("确定", new listener());
		builder.setNegativeButton("取消", new Nlistener());
		builder.create().show();
	}

	class listener implements android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			tv_people_data
					.setText(datapicker.getYear() + "-" + datapicker.getMonth() + "-" + datapicker.getDayOfMonth());
			dialog.cancel();
		}

	}

	class Nlistener implements android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (null == data) {
			return;
		}
		Uri uri = null;
		if (requestCode == AppConstant.KITKAT_LESS) {
			uri = data.getData();
			Log.d("tag", "uri=" + uri);
			// 调用裁剪方法
			Utils.getInstance().cropPicture(this, uri);
		} else if (requestCode == AppConstant.KITKAT_ABOVE) {
			uri = data.getData();
			Log.d("tag", "uri=" + uri);
			// 先将这个uri转换为path，然后再转换为uri
			String thePath = Utils.getInstance().getPath(this, uri);
			Utils.getInstance().cropPicture(this, Uri.fromFile(new File(thePath)));
		} else if (requestCode == AppConstant.INTENT_CROP) {
			Bitmap bitmap = data.getParcelableExtra("data");
			iv_people_icon.setImageBitmap(bitmap);
			File temp = new File(Environment.getExternalStorageDirectory().getPath() + "/yourAppCacheFolder/");// 自已缓存文件夹
			if (!temp.exists()) {
				temp.mkdir();
			}
			File tempFile = new File(temp.getAbsolutePath() + "/" + Calendar.getInstance().getTimeInMillis() + ".jpg"); // 以时间秒为文件名
			// 图像保存到文件中
			FileOutputStream foutput = null;
			try {
				foutput = new FileOutputStream(tempFile);
				if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, foutput)) {
					Toast.makeText(IconActivity.this, "已生成缓存文件，等待上传！文件位置：" + tempFile.getAbsolutePath(),
							Toast.LENGTH_LONG).show();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
