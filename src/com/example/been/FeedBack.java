package com.example.been;

import cn.bmob.v3.BmobObject;

public class FeedBack extends BmobObject {
	private String phone;
	private String contact;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
