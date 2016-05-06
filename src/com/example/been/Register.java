package com.example.been;

import cn.bmob.v3.BmobObject;
import android.widget.TextView;

public class Register extends BmobObject {
	private String register_name;
	private String register_psd;
	private String register_psd2;
	public String getRegister_name() {
		return register_name;
	}
	public void setRegister_name(String register_name) {
		this.register_name = register_name;
	}
	public String getRegister_psd() {
		return register_psd;
	}
	public void setRegister_psd(String register_psd) {
		this.register_psd = register_psd;
	}
	public String getRegister_psd2() {
		return register_psd2;
	}
	public void setRegister_psd2(String register_psd2) {
		this.register_psd2 = register_psd2;
	}


}
