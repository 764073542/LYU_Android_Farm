package com.farm.MainActivity;

/*************************************************************
 * ������ӣ�
 * 1.���WIFI״̬��⣬�Լ���Ӧ�Ŀ���WiFi�ķ���
 * Ч����ӣ�
 * 1.������󻬶�����ת��Debug������л�Ч��
 * 2.������һ�����ת�����ý�����л�Ч��
 * 3.����ҳ����Ҫ����һ��ģ�飺
 * 	A.
 * 	B.
 * �������⣺
 * 1.����Intent��ת��ʱ�������ܶ���棬Ҫ����ת��ʱ��Դҳ��ص�
 * 2.�����߳��ڳ�������ʱû�����ȱ�������ֻ�з��ͼ�������ʱ���Ż�������
 * ������debug������תʱ����ر��̡߳�
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
	 * ����UI�����߳�
	 */
	Runnable runnable = new Runnable() {
		public void run() {
			Temperature.setText(UIDataProvide.TEMPERATURE);
			Shidu.setText(UIDataProvide.SHIDU);
			Light.setText(UIDataProvide.LIGHT);
			CO2.setText(UIDataProvide.CO2);
			if (UIDataProvide.WEATHER) {
				Weather.setText("����");
			} else {
				Weather.setText("����");
			}

			handler.postDelayed(runnable, 1000);

		}
	};

	/* ���������ݿ� */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * ��ʼ��udp������Ҫ��IP��ַ���˿ں� ������̨�������
		 */
		initView();
		initParameters();
		CheckWiFiStat();
		mainActivity = this;
		handler.postDelayed(runnable, 1000);

	}

	/**
	 * ��ʼ��UI��Դ
	 */
	private void initView() {
		Temperature = (EditText) findViewById(R.id.edt_temperature);
		Shidu = (EditText) findViewById(R.id.edt_shidu);
		Light = (EditText) findViewById(R.id.edt_light);
		CO2 = (EditText) findViewById(R.id.edt_co2);
		Weather = (EditText) findViewById(R.id.edt_weather);

	}

	/**
	 * ���WiFi�Ƿ�����
	 */
	private void CheckWiFiStat() {
		wifiManager = (WifiManager) this.getSystemService(this.WIFI_SERVICE);
		int stat = WifiManager.WIFI_STATE_DISABLED; // ����ʼֵ����ΪWiFi�ر�״̬��
		String Native_ip = "";
		if (wifiManager != null) {
			stat = wifiManager.getWifiState();
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();
			Native_ip = intToIp(ipAddress);
			Log.i("Farm", "ip��ַΪ��" + Native_ip);
			Log.d("Farm", "WIFI״̬��" + wifiManager.getWifiState());
		}
		if (stat == WifiManager.WIFI_STATE_DISABLED) {
			new AlertDialog.Builder(this)

			.setTitle("��ʾ")

			.setMessage("WiFiû�п���")

			.setPositiveButton("ȷ��", null)

			.show();
		} else if (Native_ip.startsWith("192") || Native_ip.startsWith("10")
				|| Native_ip.startsWith("172")) {
			Intent intent = new Intent(this, NetService.class);
			startService(intent);
		} else {
			new AlertDialog.Builder(this)

			.setTitle("��ʾ")

			.setMessage("���ӵ����粻�ټ�ͥ������")

			.setPositiveButton("ȷ��", null)

			.show();
		}
		UdpDataProvider.NATIVE_IP = Native_ip;
	}

	/**
	 * �������Ƶ�IP��ַת��Ϊ10���Ƶ�IP��ַ�������ص�һ�顣
	 * 
	 * @param ipAddress
	 * @return
	 */
	private String intToIp(int ipAddress) {

		return (ipAddress & 0xFF) + "." + (0xFF & ipAddress >> 8) + "."
				+ (0xFF & ipAddress >> 16) + "." + (0xFF & ipAddress >> 24);
	}

	/**
	 * �����ݽ��г�ʼ��
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
	 * ��ť����¼�
	 ******************************************************/
	/**
	 * ������ð�ť��ת��Setting����
	 * 
	 * @param view
	 */
	public void Setting(View view) {
		startActivity(new Intent(this, SettingActivity.class));
	}

	/**
	 * ���յƿ��ư�ť
	 */
	public void lightControl(View view) {
		startActivity(new Intent(this, LightActivity.class));
	}

	/**
	 * ���ȿ���
	 */
	public void fanControl(View view) {
		startActivity(new Intent(this, FanActivity.class));
	}

	/**
	 * �������ؿ���
	 */
	public void windowControl(View view) {
		startActivity(new Intent(this, WindowControl.class));
	}

	/**
	 * ��ˮ��ʼֹͣ����
	 */
	public void waterControl(View view) {
		startActivity(new Intent(this, WaterControl.class));
	}

	/**
	 * ����������
	 */
	public void heaterControl(View view) {
		startActivity(new Intent(this, HeaterControl.class));
	}

	/**
	 * ����Ч������
	 */
	public void ImageFan(View view) {
		startActivity(new Intent(this, ImageFan.class));
	}

	/**
	 * ����������η����˳�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
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
	 * ����������������б�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("Farm", "onCreateOptionsMenu����һ��");
		// startActivity(new Intent(this, MenuActivity.class));
		menu.add(Menu.NONE, Menu.FIRST + 1, 5, "����").setIcon(
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
	 * �˳���������
	 */
	public static void exit() {
		mainActivity.finish();
		mainActivity.stopService(new Intent(mainActivity, NetService.class));
		System.exit(0);
	}
}
