package com.example.yipiao;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.click_item);
		setview();
	}

	private void setview() {

	}

}
