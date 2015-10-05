package com.farm.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MenuActivity extends Activity {

	ListView time_listView;
	SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		time_listView = (ListView) findViewById(R.id.menu_list);

		adapter = new SimpleAdapter(this, getData(), R.layout.vlist,
				new String[] { "title", "info", "img" }, new int[] {
						R.id.title, R.id.info, R.id.img });
		time_listView.setAdapter(adapter);
		time_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {

				switch (position) {
				case 0:// 关于我们
					startActivity(new Intent(MenuActivity.this, AboutUS.class));
					break;
				case 1: //调试界面
					startActivity(new Intent(MenuActivity.this,
							DebugActivity.class));
					break;
				case 2: //定时问题
					startActivity(new Intent(MenuActivity.this,
							ClockActivity.class));
					break;
				case 3: //退出程序
					exit();				
				default:
					break;
				}
			}

		});
	}

	private void exit() {
		Log.d("Farm", "程序将完全退出");
		Message msg = new Message();
		msg.what = 0;
		MainActivity.handler.sendMessage(msg);
		this.finish();
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "调试信息");
		map.put("info", "开发人员选项，用于远程调试");
		map.put("img", R.drawable.ico_debug);

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("title", "关于我们");
		map1.put("info", "掌上花卉生态系统最新动态");
		map1.put("img", R.drawable.ico_logo);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("title", "定时任务");
		map2.put("info", "通过定时器来定时操作");
		map2.put("img", R.drawable.ico_clock);

		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("title", "完全退出");
		map3.put("info", "退出之后就不能接收到任何信息了");
		map3.put("img", R.drawable.ico_exit);
		list.add(map1);
		list.add(map);
		list.add(map2);
		list.add(map3);

		return list;

	}
}