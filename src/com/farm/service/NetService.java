package com.farm.service;

import java.net.DatagramSocket;
import java.net.SocketException;

import com.farm.data.UdpDataProvider;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NetService extends Service {
	private ReceiveService netReceiveService;
	private NetSendService netSendService;
	private DatagramSocket netUdpSocket = null;// ��������Socket

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void init() {
		/* ��ʼ���������շ����̶߳��󣬲������߳� */
		/**
		 * �����������ݷ��ͽ����߳�
		 */
		try {
			netUdpSocket = new DatagramSocket(UdpDataProvider.SEND_PORT);
		} catch (SocketException e) {
			Log.e("Farm", "DatagramSocket����ʧ��");
		}
		netSendService = new NetSendService(netUdpSocket);
		netReceiveService = new ReceiveService(netUdpSocket);
		netReceiveService.startReceive();
		netSendService.startSend();
	}

	@Override
	public void onCreate() {
		Log.d("Farm", "�����Ѿ�������");
		super.onCreate();
		init();// ��ʼ��
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.d("Farm", "NetService�����Ѿ�������");
		super.onStart(intent, startId);
	}

	public void onDestroy() {
		super.onDestroy();
		netSendService.stopService();
		netReceiveService.stopService();
		Log.d("Farm", "�����Ѿ�������");
		System.exit(0);
	};
}