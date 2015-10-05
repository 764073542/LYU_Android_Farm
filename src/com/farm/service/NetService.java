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
	private DatagramSocket netUdpSocket = null;// 接收数据Socket

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void init() {
		/* 初始化创建接收发送线程对象，并启动线程 */
		/**
		 * 创建网络数据发送接收线程
		 */
		try {
			netUdpSocket = new DatagramSocket(UdpDataProvider.SEND_PORT);
		} catch (SocketException e) {
			Log.e("Farm", "DatagramSocket创建失败");
		}
		netSendService = new NetSendService(netUdpSocket);
		netReceiveService = new ReceiveService(netUdpSocket);
		netReceiveService.startReceive();
		netSendService.startSend();
	}

	@Override
	public void onCreate() {
		Log.d("Farm", "服务已经被创建");
		super.onCreate();
		init();// 初始化
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.d("Farm", "NetService服务已经被启动");
		super.onStart(intent, startId);
	}

	public void onDestroy() {
		super.onDestroy();
		netSendService.stopService();
		netReceiveService.stopService();
		Log.d("Farm", "服务已经被销毁");
		System.exit(0);
	};
}