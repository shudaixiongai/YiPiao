package com.example.yipiao;

import com.example.yipiao.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMusic;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.bidaround.youtui_template.YouTuiViewType;
import cn.bidaround.youtui_template.YtTemplate;
import cn.bidaround.ytcore.YtShareListener;
import cn.bidaround.ytcore.data.ShareData;
import cn.bidaround.ytcore.data.YtPlatform;
import cn.bidaround.ytcore.util.HttpUtils;
import cn.bidaround.ytcore.util.YtToast;

public class AboutActivity extends Activity implements OnClickListener {
	private RelativeLayout rl_about_share;
	private YtTemplate template;
	private boolean usePointSys = true;
	/** 分享的数据对象 */
	private boolean isShowSharePop = true;

	private ShareData shareData;
	private static final String IMAGEURL = "http://cdnup.b0.upaiyun.com/media/image/default.png";

	/** 分享的界面平台 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		YtTemplate.init(this);

		// 开启集成检测机制，请在检测完成后关闭，默认不检测
		// YtTemplate.checkConfig(true);

		initShareData();
		initTemplate();
		setview();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_about_share:
			showTemplate(YouTuiViewType.WHITE_GRID_CENTER);
			break;

		}
	}

	/**
	 * 显示分享界面
	 */
	private void showTemplate(int type) {
		template.setTemplateType(type);
		template.setHasAct(usePointSys);
		template.show();
	}

	private void setview() {
		rl_about_share = (RelativeLayout) findViewById(R.id.rl_about_share);
		rl_about_share.setOnClickListener(this);

	}

	@SuppressLint("SimpleDateFormat")
	private void initShareData() {
		shareData = new ShareData();
		shareData.setAppShare(false); // 是否为应用分享，如果为true，分享的数据需在友推后台设置
		shareData.setDescription("友推积分组件");// 待分享内容的描述
		shareData.setTitle("一票分享"); // 待分享的标题
		shareData.setText(
				"一票是一款专业的活动类投票程序，支持免费发布投票，欢迎大家来使用");// 待分享的文字
		shareData.setImage(ShareData.IMAGETYPE_INTERNET, IMAGEURL);// 设置网络分享地址
		shareData.setPublishTime("2013-02-05");
		shareData.setTargetId(String.valueOf(100));
		// shareData.setTargetUrl("https://www.baidu.com/");// 待分享内容的跳转链接
		shareData.setTargetUrl("http://youtui.mobi/");// 待分享内容的跳转链接
	}

	private void initTemplate() {
		template = new YtTemplate(this, YouTuiViewType.WHITE_GRID, usePointSys);
		template.setShareData(shareData);
		template.addListeners(listener);
	}

	YtShareListener listener = new YtShareListener() {

		/** 分享成功后回调方法 */
		@Override
		public void onSuccess(YtPlatform platform, String result) {
			YtToast.showS(AboutActivity.this, "onSuccess");

			Log.w("YouTui", platform.getName());

			/** 清理缓存 */
			HttpUtils.deleteImage(shareData.getImagePath());
		}

		/** 分享之前调用方法 */
		@Override
		public void onPreShare(YtPlatform platform) {
			if (!isShowSharePop)
				YtTemplate.dismiss();

			YtToast.showS(AboutActivity.this, "onPreShare");

			Log.w("YouTui", platform.getName());
		}

		/** 分享失败回调方法 */
		@Override
		public void onError(YtPlatform platform, String error) {
			YtToast.showS(AboutActivity.this, "onError");

			Log.w("YouTui", platform.getName());
			Log.w("YouTui", error);
		}

		/** 分享取消回调方法 */
		@Override
		public void onCancel(YtPlatform platform) {
			YtToast.showS(AboutActivity.this, "onCancel");

			Log.w("YouTui", platform.getName());

			/** 清理缓存 */
			HttpUtils.deleteImage(shareData.getImagePath());
		}
	};

}
