package com.farm.MainActivity;

/*************************************************************
 * 功能添加：
 * 1.添加WIFI状态监测，以及相应的开启WiFi的方案
 * 效果添加：
 * 1.添加向左滑动，跳转到Debug界面的切换效果
 * 2.添加向右滑动跳转到设置界面的切换效果
 * 3.设置页面需要新增一下模块：
 * 	A.
 * 	B.
 * 存在问题：
 * 1.当用Intent跳转的时候会产生很多界面，要在跳转到时候将源页面关掉
 * 2.接收线程在程序运行时没有率先被启动，只有发送几次数据时，才会启动，
 * 而且由debug界面跳转时，会关闭线程。
 * 
 **************************************************************/
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.farm.data.MyPrefer;
import com.farm.data.UdpDataProvider;
import com.farm.queue.UIDataProvide;
import com.farm.service.NetService;

public class MainActivity extends Activity {
	private static MainActivity mainActivity;
	private WifiManager wifiManager = null;
	private long exitTime = 0;
	private EditText Temperature, Shidu, Light, CO2, Weather;
	public static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.d("Farm", "Hanlder" + msg.what);
			switch (msg.what) {
			case 0:
				exit();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};
	/**
	 * 更新UI的子线程
	 */
	Runnable runnable = new Runnable() {
		public void run() {
			Temperature.setText(UIDataProvide.TEMPERATURE);
			Shidu.setText(UIDataProvide.SHIDU);
			Light.setText(UIDataProvide.LIGHT);
			CO2.setText(UIDataProvide.CO2);
			if (UIDataProvide.WEATHER) {
				Weather.setText("有雨");
			} else {
				Weather.setText("晴朗");
			}

			handler.postDelayed(runnable, 1000);

		}
	};

	/* 主界面数据框 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * 初始化udp连接需要的IP地址，端口号 开启后台服务服务
		 */
		initView();
		initParameters();
		CheckWiFiStat();
		mainActivity = this;
		handler.postDelayed(runnable, 1000);

	}

	/**
	 * 初始化UI资源
	 */
	private void initView() {
		Temperature = (EditText) findViewById(R.id.edt_temperature);
		Shidu = (EditText) findViewById(R.id.edt_shidu);
		Light = (EditText) findViewById(R.id.edt_light);
		CO2 = (EditText) findViewById(R.id.edt_co2);
		Weather = (EditText) findViewById(R.id.edt_weather);

	}

	/**
	 * 检查WiFi是否开启了
	 */
	private void CheckWiFiStat() {
		wifiManager = (WifiManager) this.getSystemService(this.WIFI_SERVICE);
		int stat = WifiManager.WIFI_STATE_DISABLED; // 将初始值设置为WiFi关闭状态码
		String Native_ip = "";
		if (wifiManager != null) {
			stat = wifiManager.getWifiState();
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();
			Native_ip = intToIp(ipAddress);
			Log.i("Farm", "ip地址为：" + Native_ip);
			Log.d("Farm", "WIFI状态：" + wifiManager.getWifiState());
		}
		if (stat == WifiManager.WIFI_STATE_DISABLED) {
			new AlertDialog.Builder(this)

			.setTitle("提示")

			.setMessage("WiFi没有开启")

			.setPositiveButton("确定", null)

			.show();
		} else if (Native_ip.startsWith("192") || Native_ip.startsWith("10")
				|| Native_ip.startsWith("172")) {
			Intent intent = new Intent(this, NetService.class);
			startService(intent);
		} else {
			new AlertDialog.Builder(this)

			.setTitle("提示")

			.setMessage("连接的网络不再家庭网络中")

			.setPositiveButton("确定", null)

			.show();
		}
		UdpDataProvider.NATIVE_IP = Native_ip;
	}

	/**
	 * 将二进制的IP地址转化为10进制的IP地址，并返回第一组。
	 * 
	 * @param ipAddress
	 * @return
	 */
	private String intToIp(int ipAddress) {

		return (ipAddress & 0xFF) + "." + (0xFF & ipAddress >> 8) + "."
				+ (0xFF & ipAddress >> 16) + "." + (0xFF & ipAddress >> 24);
	}

	/**
	 * 将数据进行初始化
	 */
	private void initParameters() {
		MyPrefer prefer = new MyPrefer(this);
		UdpDataProvider.PROXY_IP = prefer.getIp();
		UdpDataProvider.SEND_PORT = Integer.parseInt(prefer.getSendPort()
				.trim());
		UdpDataProvider.RECV_PORT = Integer.parseInt(prefer.getRecvPort()
				.trim());

	}

	/******************************************************
	 * 按钮点击事件
	 ******************************************************/
	/**
	 * 点击设置按钮跳转到Setting界面
	 * 
	 * @param view
	 */
	public void Setting(View view) {
		startActivity(new Intent(this, SettingActivity.class));
	}

	/**
	 * 光照灯控制按钮
	 */
	public void lightControl(View view) {
		startActivity(new Intent(this, LightActivity.class));
	}

	/**
	 * 风扇控制
	 */
	public void fanControl(View view) {
		startActivity(new Intent(this, FanActivity.class));
	}

	/**
	 * 窗户开关控制
	 */
	public void windowControl(View view) {
		startActivity(new Intent(this, WindowControl.class));
	}

	/**
	 * 浇水开始停止控制
	 */
	public void waterControl(View view) {
		startActivity(new Intent(this, WaterControl.class));
	}

	/**
	 * 加热器控制
	 */
	public void heaterControl(View view) {
		startActivity(new Intent(this, HeaterControl.class));
	}

	/**
	 * 动画效果测试
	 */
	public void ImageFan(View view) {
		startActivity(new Intent(this, ImageFan.class));
	}

	/**
	 * 连续点击两次返回退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 关于主界面的设置列表
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("Farm", "onCreateOptionsMenu运行一次");
		// startActivity(new Intent(this, MenuActivity.class));
		menu.add(Menu.NONE, Menu.FIRST + 1, 5, "关于").setIcon(
				android.R.drawable.ic_menu_delete);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case Menu.FIRST + 1:

			startActivity(new Intent(this, MenuActivity.class));

			break;

		}

		return false;
	}

	/**
	 * 退出结束服务
	 */
	public static void exit() {
		mainActivity.finish();
		mainActivity.stopService(new Intent(mainActivity, NetService.class));
		System.exit(0);
	}
}
