package com.example.been;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

public class InBeen extends BmobObject implements Serializable{
	private String title;
	private String option_one;
	private String option_two;
	private String option_three;
	private String explain;
	private List<InBeen> beens;

	public List<InBeen> getBeens() {
		return beens;
	}

	public void setBeens(List<InBeen> beens) {
		this.beens = beens;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOption_one() {
		return option_one;
	}

	public void setOption_one(String option_one) {
		this.option_one = option_one;
	}

	public String getOption_two() {
		return option_two;
	}

	public void setOption_two(String option_two) {
		this.option_two = option_two;
	}

	public String getOption_three() {
		return option_three;
	}

	public void setOption_three(String option_three) {
		this.option_three = option_three;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}
