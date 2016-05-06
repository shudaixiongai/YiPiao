package com.example.been;

import cn.bmob.v3.BmobObject;
import android.widget.TextView;

public class Login extends BmobObject {
	private String login_name;
	private String login_psd;

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getLogin_psd() {
		return login_psd;
	}

	public void setLogin_psd(String login_psd) {
		this.login_psd = login_psd;
	}

}
