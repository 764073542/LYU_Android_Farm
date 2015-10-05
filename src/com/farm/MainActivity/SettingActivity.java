package com.farm.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.farm.data.UdpDataProvider;
import com.farm.data.MyPrefer;

public class SettingActivity extends Activity {
	private EditText Proxy_IP, SendPort, RecvPort, Native_IP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		Proxy_IP = (EditText) findViewById(R.id.edt_ip);
		SendPort = (EditText) findViewById(R.id.edt_sendport);
		RecvPort = (EditText) findViewById(R.id.edt_recvport);
		Native_IP = (EditText) findViewById(R.id.edt_native_ip);
		/*
		 * 读取本地文件中IP，端口的数据并显示到文本框中
		 */
		Proxy_IP.setText(UdpDataProvider.PROXY_IP);
		SendPort.setText(UdpDataProvider.SEND_PORT + "");
		RecvPort.setText(UdpDataProvider.RECV_PORT + "");
		Native_IP.setText(UdpDataProvider.NATIVE_IP + "");
	}

	/**
	 * 保存按钮点击事件，将数据保存到本地文件中去 问题： 1.将数据仅仅保存到文件还不够，必须更新到当前程序调用的地方，不然其他都是调用修改之前的数据
	 * 
	 * @param view
	 */
	public void Save(View view) {
		MyPrefer prefer = new MyPrefer(this);
		String IP = (Proxy_IP.getText() + "").trim();
		String Sendport = (SendPort.getText() + "").trim();
		String Recvport = (RecvPort.getText() + "").trim();
		/*
		 * 判断IP地址是否为空，是否为空格，port是否为>65535
		 */
		if (IP != null && Sendport != null
				&& Integer.parseInt(Sendport) < 65535 && Recvport != null
				&& Integer.parseInt(Recvport) < 65535) {
			/* 将数据更新到SharedPreferences中 */
			prefer.setIP(IP);
			prefer.setSendPort(Sendport);
			prefer.setRecvPort(Recvport);
			Toast.makeText(this, "保存成功", 0).show();
			/* 保存之后跳回主界面 .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) */
			startActivity((new Intent()).setClass(this, MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			/* 将数据更新到DataProvide中 */
			UdpDataProvider.PROXY_IP = IP;
			UdpDataProvider.SEND_PORT = Integer.parseInt(Sendport);
			UdpDataProvider.RECV_PORT = Integer.parseInt(Recvport);
		} else {
			Toast.makeText(this, "数据不正确，请修正后保存", 0).show();
		}
	}

	/**
	 * 点击取消按钮将跳转到主界面
	 * 
	 * @param view
	 */
	public void Cancle(View view) {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	}

}
