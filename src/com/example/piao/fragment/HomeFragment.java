package com.example.piao.fragment;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.bmob.btp.e.a.in;
import com.example.been.InBeen;
import com.example.yipiao.R;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.viewpager_home, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
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
				viewholder.username = (TextView) arg1
						.findViewById(R.id.tv_vote_title);
				viewholder.password = (TextView) arg1
						.findViewById(R.id.tv_vote_title2);
				viewholder.content = (TextView) arg1
						.findViewById(R.id.tv_vote_content);
				arg1.setTag(viewholder);
			} else {
				viewholder = (Viewholder) arg1.getTag();
			}
			// for (int i = 0; i < lists.size(); i++) {
			// String title = lists.get(i).getTitle();
			// Log.i("lists.gettitle", title);
			// }
			String title = listsBeens.get(arg0).getTitle();
			viewholder.content.setText(title);
			// viewholder.iv_title.setBackgroundDrawable(drawable.ic_launcher);
			return arg1;
		}

	}

	private class Viewholder {
		private TextView username;
		private TextView password;
		private TextView content;
		private ImageView iv_title;

	}

	private void Queryout() {
		BmobQuery<InBeen> query = new BmobQuery<InBeen>();
		query.order("-createdAt");
		query.findObjects(getActivity(),
				new FindListener<InBeen>() {
					@Override
					public void onSuccess(List<InBeen> arg0) {
						final List<InBeen> lists = arg0;
						for (int i = 0; i < arg0.size(); i++) {
							Log.i("arg0", "log" + arg0.get(i).getTitle());
						}
						listView.setAdapter(new adapter(getActivity(), lists));
						listView.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// Register register = new Register();
								// // if
								// //
								// (lists.get(arg2).equals(register.getRegister_name()))
								// // {
								// Intent intent = new Intent(getActivity(),
								// DetailsActivity.class);
								// Bundle bundle = new Bundle();
								// bundle.putInt("postion", arg2);
								// intent.putExtra("list", (Serializable)
								// lists);
								// intent.putExtra("intent", int1);
								// intent.putExtra("intent2", int2);
								// intent.putExtra("intent3", int3);
								// intent.putExtra("sum", sum);
								// Log.v("绗竴涓猘ctivity鐨刬nt鍊�",
								// String.valueOf(int1));
								// intent.putExtras(bundle);
								// startActivityForResult(intent, REQUESTCODE);
								// }
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
