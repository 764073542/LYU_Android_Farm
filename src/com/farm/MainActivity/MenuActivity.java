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
				case 0:// ��������
					startActivity(new Intent(MenuActivity.this, AboutUS.class));
					break;
				case 1: //���Խ���
					startActivity(new Intent(MenuActivity.this,
							DebugActivity.class));
					break;
				case 2: //��ʱ����
					startActivity(new Intent(MenuActivity.this,
							ClockActivity.class));
					break;
				case 3: //�˳�����
					exit();				
				default:
					break;
				}
			}

		});
	}

	private void exit() {
		Log.d("Farm", "������ȫ�˳�");
		Message msg = new Message();
		msg.what = 0;
		MainActivity.handler.sendMessage(msg);
		this.finish();
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "������Ϣ");
		map.put("info", "������Աѡ�����Զ�̵���");
		map.put("img", R.drawable.ico_debug);

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("title", "��������");
		map1.put("info", "���ϻ�����̬ϵͳ���¶�̬");
		map1.put("img", R.drawable.ico_logo);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("title", "��ʱ����");
		map2.put("info", "ͨ����ʱ������ʱ����");
		map2.put("img", R.drawable.ico_clock);

		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("title", "��ȫ�˳�");
		map3.put("info", "�˳�֮��Ͳ��ܽ��յ��κ���Ϣ��");
		map3.put("img", R.drawable.ico_exit);
		list.add(map1);
		list.add(map);
		list.add(map2);
		list.add(map3);

		return list;

	}
}