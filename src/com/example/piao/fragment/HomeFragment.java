package com.example.piao.fragment;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.btp.e.a.in;
import com.example.been.InBeen;
import com.example.yipiao.ItemActivity;
import com.example.yipiao.R;

import android.R.drawable;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	private ListView listView;
	View view;
	int int1, int2, int3;
	int sum, sum2, sum3;
	private static final int REQUESTCODE = 1;
	String vote_name;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.viewpager_home, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		vote_name = bundle.getString("get_name");
		initView();
	}

	private void initView() {
		listView = (ListView) getActivity().findViewById(R.id.viewpager_home);
		Queryout();
	}

	class adapter extends BaseAdapter {

		LayoutInflater inflater;
		private List<InBeen> listsBeens;

		public adapter(Context context, List<InBeen> beens) {
			this.inflater = inflater.from(context);
			this.listsBeens = beens;
		}

		@Override
		public int getCount() {
			return listsBeens.size();
		}

		@Override
		public Object getItem(int arg0) {
			return listsBeens.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			Viewholder viewholder;
			if (arg1 == null) {
				viewholder = new Viewholder();
				arg1 = inflater.inflate(R.layout.out_item, null);
				// viewholder.iv_title = (ImageView) arg1
				// .findViewById(R.id.iv_head);
				viewholder.iv_title = (ImageView) arg1.findViewById(R.id.iv_head);
				viewholder.name = (TextView) arg1.findViewById(R.id.tv_vote_username);
				viewholder.time = (TextView) arg1.findViewById(R.id.tv_vote_time);
				viewholder.content = (TextView) arg1.findViewById(R.id.tv_vote_content);
				viewholder.title = (TextView) arg1.findViewById(R.id.tv_vote_option1);
				arg1.setTag(viewholder);
			} else {
				viewholder = (Viewholder) arg1.getTag();
			}

			String time = listsBeens.get(arg0).getTime();
			String title = listsBeens.get(arg0).getTitle();
			String explain = listsBeens.get(arg0).getExplain();
			viewholder.content.setText(explain);
			viewholder.time.setText(time);
			viewholder.title.setText(title);
			viewholder.iv_title.setBackgroundResource(R.drawable.user_default_icon);
			viewholder.name.setText(vote_name);
			return arg1;
		}

	}

	private class Viewholder {
		private TextView title;
		private TextView password;
		private TextView content;
		private TextView name;
		private ImageView iv_title;
		private TextView time;

	}

	private void Queryout() {
		BmobQuery<InBeen> query = new BmobQuery<InBeen>();
		query.order("-createdAt");
		query.findObjects(getActivity(), new FindListener<InBeen>() {
			@Override
			public void onSuccess(List<InBeen> arg0) {
				final List<InBeen> lists = arg0;
				for (int i = 0; i < arg0.size(); i++) {
					Log.i("arg0", "log" + arg0.get(i).getTitle());
				}
				listView.setAdapter(new adapter(getActivity(), lists));
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						Intent intent = new Intent();
						intent.putExtra("postion", arg2);
						intent.setClass(getActivity(), ItemActivity.class);
						startActivity(intent);
					}
				});
			}

			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(getActivity(), "网络错误", 1).show();
			}
		});

	}
}
