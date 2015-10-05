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
		 * ��ȡ�����ļ���IP���˿ڵ����ݲ���ʾ���ı�����
		 */
		Proxy_IP.setText(UdpDataProvider.PROXY_IP);
		SendPort.setText(UdpDataProvider.SEND_PORT + "");
		RecvPort.setText(UdpDataProvider.RECV_PORT + "");
		Native_IP.setText(UdpDataProvider.NATIVE_IP + "");
	}

	/**
	 * ���水ť����¼��������ݱ��浽�����ļ���ȥ ���⣺ 1.�����ݽ������浽�ļ���������������µ���ǰ������õĵط�����Ȼ�������ǵ����޸�֮ǰ������
	 * 
	 * @param view
	 */
	public void Save(View view) {
		MyPrefer prefer = new MyPrefer(this);
		String IP = (Proxy_IP.getText() + "").trim();
		String Sendport = (SendPort.getText() + "").trim();
		String Recvport = (RecvPort.getText() + "").trim();
		/*
		 * �ж�IP��ַ�Ƿ�Ϊ�գ��Ƿ�Ϊ�ո�port�Ƿ�Ϊ>65535
		 */
		if (IP != null && Sendport != null
				&& Integer.parseInt(Sendport) < 65535 && Recvport != null
				&& Integer.parseInt(Recvport) < 65535) {
			/* �����ݸ��µ�SharedPreferences�� */
			prefer.setIP(IP);
			prefer.setSendPort(Sendport);
			prefer.setRecvPort(Recvport);
			Toast.makeText(this, "����ɹ�", 0).show();
			/* ����֮������������ .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) */
			startActivity((new Intent()).setClass(this, MainActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			/* �����ݸ��µ�DataProvide�� */
			UdpDataProvider.PROXY_IP = IP;
			UdpDataProvider.SEND_PORT = Integer.parseInt(Sendport);
			UdpDataProvider.RECV_PORT = Integer.parseInt(Recvport);
		} else {
			Toast.makeText(this, "���ݲ���ȷ���������󱣴�", 0).show();
		}
	}

	/**
	 * ���ȡ����ť����ת��������
	 * 
	 * @param view
	 */
	public void Cancle(View view) {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	}

}
