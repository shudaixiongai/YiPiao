package com.example.yipiao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.piao.fragment.AboutActivity;
import com.example.piao.fragment.HomeFragment;
import com.example.piao.fragment.HotFragment;
import com.example.piao.fragment.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.Bmob;

public class MainActivity extends FragmentActivity {

	private int currentItem;

	Button bt_in, bt_out;
	public static final String APP_ID = "98b20fe235f525721eb0f46a14c64f03";
	private DrawerLayout drawerLayout;
	private ListView listView;
	private Bitmap cursor;
	private ImageView iv_srcol;
	private Animation animation;
	private List<Fragment> fragments;
	private int offSet;
	private Context context;
	private Matrix matrix = new Matrix();
	private int bmWidth;
	private FragmentManager manager;
	private ViewPager viewPager;
	private TextView tv_host, tv_hot, tv_my;
	private String[] strings = new String[] { "首页", "登陆", "ע注册", "发起投票", "设置", "关于软件", "退出" };
	private HomeFragment homeFragment;
	private HotFragment hotFragment;
	private MyFragment myFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.main);
		Bmob.initialize(this, APP_ID);
		init();
	}

	private void init() {
		drawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout2);
		drawerLayout.setDrawerListener(new DrawerListener() {
			@Override
			public void onDrawerStateChanged(int arg0) {

			}

			@Override
			public void onDrawerSlide(View arg0, float arg1) {

			}

			@Override
			public void onDrawerOpened(View arg0) {
				Log.i("drawer", "开启侧滑");
			}

			@Override
			public void onDrawerClosed(View arg0) {
				Log.i("drawer", "关闭侧滑");
			}
		});
		drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		});
		listView = (ListView) findViewById(R.id.id_lv);
		listView.setAdapter(new adapter(MainActivity.this));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == 4) {
					Intent intent = new Intent(MainActivity.this, SettingActivity.class);
					startActivity(intent);
				}
				if (arg2 == 5) {
					Intent intent = new Intent(MainActivity.this, AboutActivity.class);
					startActivity(intent);

				}

			}
		});
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		iv_srcol = (ImageView) findViewById(R.id.iv_sorl);
		tv_host = (TextView) findViewById(R.id.tv_home);
		tv_hot = (TextView) findViewById(R.id.tv_hot);
		tv_my = (TextView) findViewById(R.id.tv_my);
		homeFragment = new HomeFragment();
		hotFragment = new HotFragment();
		myFragment = new MyFragment();
		fragments = new ArrayList<Fragment>();
		fragments.add(homeFragment);
		fragments.add(hotFragment);
		fragments.add(myFragment);
		initeCursor();
		tv_host.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(0);
			}
		});
		tv_hot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(1);
			}
		});
		tv_my.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(2);
			}
		});
		viewPager.setAdapter(new fragmentadapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					if (currentItem == 1) {
						animation = new TranslateAnimation(offSet * 2 + bmWidth, 0, 0, 0);
					} else if (currentItem == 2) {
						animation = new TranslateAnimation(offSet * 4 + 2 * bmWidth, 0, 0, 0);
					}
					break;

				case 1:
					if (currentItem == 0) {
						animation = new TranslateAnimation(0, offSet * 2 + bmWidth, 0, 0);
					} else if (currentItem == 2) {
						animation = new TranslateAnimation(4 * offSet + 2 * bmWidth, offSet * 2 + bmWidth, 0, 0);
					}
					break;
				case 2:
					if (currentItem == 0) {
						animation = new TranslateAnimation(0, 4 * offSet + 2 * bmWidth, 0, 0);
					} else if (currentItem == 1) {
						animation = new TranslateAnimation(offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth, 0, 0);
					}
				}
				currentItem = arg0;
				animation.setDuration(150);
				animation.setFillAfter(true);
				iv_srcol.startAnimation(animation);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	class fragmentadapter extends FragmentPagerAdapter {

		public fragmentadapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

	class adapter extends BaseAdapter {
		private LayoutInflater inflater;

		public adapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return getdate().size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolper viewHolper;
			if (arg1 == null) {
				arg1 = inflater.inflate(R.layout.lv_item, null);
				viewHolper = new ViewHolper();
				viewHolper.tv_title = (TextView) arg1.findViewById(R.id.lv_title);
				viewHolper.iv_title = (ImageView) arg1.findViewById(R.id.iv_title);
				arg1.setTag(viewHolper);
			} else {
				viewHolper = (ViewHolper) arg1.getTag();
			}
			viewHolper.tv_title.setText(getdate().get(arg0).get("tv_item").toString());
			viewHolper.iv_title.setImageResource((Integer) getdate().get(arg0).get("iv_item"));
			return arg1;
		}

		class ViewHolper {
			private TextView tv_title;
			private ImageView iv_title;
		}

	}

	public List<Map<String, Object>> getdate() {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> maps;
		maps = new HashMap<String, Object>();
		maps.put("tv_item", "首页");
		maps.put("iv_item", R.drawable.weather);
		lists.add(maps);

		maps = new HashMap<String, Object>();
		maps.put("tv_item", "登陆");
		maps.put("iv_item", R.drawable.updater);
		lists.add(maps);

		maps = new HashMap<String, Object>();
		maps.put("tv_item", "注册");
		maps.put("iv_item", R.drawable.thememanager);
		lists.add(maps);

		maps = new HashMap<String, Object>();
		maps.put("tv_item", "发起投票");
		maps.put("iv_item", R.drawable.updater);
		lists.add(maps);

		maps = new HashMap<String, Object>();
		maps.put("tv_item", "设置");
		maps.put("iv_item", R.drawable.settings);
		lists.add(maps);
		maps = new HashMap<String, Object>();
		maps.put("tv_item", "关于软件");
		maps.put("iv_item", R.drawable.settings);
		lists.add(maps);
		maps = new HashMap<String, Object>();
		maps.put("tv_item", "退出");
		maps.put("iv_item", R.drawable.weather);
		lists.add(maps);
		return lists;
	}

	private void initeCursor() {
		cursor = BitmapFactory.decodeResource(getResources(), R.drawable.next);
		bmWidth = cursor.getWidth();
		DisplayMetrics dm = getResources().getDisplayMetrics();
		offSet = (dm.widthPixels - 3 * bmWidth) / 6;
		Log.i("widthPixels", String.valueOf(dm.widthPixels));
		matrix.setTranslate(offSet, 0);
		Log.i("offSet", String.valueOf(offSet));

		iv_srcol.setImageMatrix(matrix);
		currentItem = 0;
	}
}
