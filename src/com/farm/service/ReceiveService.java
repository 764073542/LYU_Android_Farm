package com.farm.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import android.util.Log;

import com.farm.data.Constant;
import com.farm.data.UdpDataProvider;
import com.farm.queue.DataUtils;
import com.farm.queue.UIDataProvide;

public class ReceiveService implements Runnable {
	private String RecvData;
	private Thread RecvThread = null;// 接收数据线程
	private DatagramSocket rUdp = null;// 接收数据Socket
	private DatagramPacket rPacket = null;
	private int RecvPort = UdpDataProvider.RECV_PORT;
	private byte[] rBuffer = new byte[1024];// 接收数据缓存1024字节
	private DataUtils utils = new DataUtils();

	public ReceiveService(DatagramSocket netUdpSocket) {
		rUdp = netUdpSocket;
	}

	/**
	 * 线程run方法 具体方法参考 com.farm.net.UdpThread
	 */
	@Override
	public void run() {
		while (Thread.currentThread() == RecvThread) {
			try {
				recvData();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 接收的数据以文本格式来显示
	 */
	private void recvData() {
		try {
			rUdp.receive(rPacket);
			RecvData = new String(rPacket.getData(), Constant.ENDODED).trim();
			/**
			 * 将接收到的数据进行解析。
			 */			
			utils.AnalyzeData(RecvData);
			UIDataProvide.RECV_MSG = RecvData;
			Log.d("Farm", "接收到数据：" + RecvData);
			/**
			 * 以下目的是为了清空rBuffer里面的数据，不然接收到到的数据会有之前数据的一部分 例如： 第一次发送 1234567 第二次发送
			 * 33 结果第二次收到 3334567
			 */
			rBuffer = null;
			rBuffer = new byte[1024];
			rPacket = new DatagramPacket(rBuffer, rBuffer.length); // 此处清空缓冲区的方式存在问题

		} catch (IOException ie) {
			Log.d("Farm", "数据错误");
		}
	}

	/**
	 * 开启服务
	 */
	public void startReceive() {
		if (ConnectSocket()) {
			System.out.println("接收线程已经启动");

		} else {
			System.out.println("接收线程已经在运行不能启动");
		}
	}

	/**
	 * 关闭服务
	 */
	public void closeReceive() {
		stopThread();
	}

	/**
	 * 开启线程
	 */
	private void startThread() {
		if (RecvThread == null) {
			RecvThread = new Thread(this);
			RecvThread.start();
		}
	}

	/**
	 * 关闭线程
	 */
	private void stopThread() {
		if (RecvThread != null) {
			RecvThread.stop();
			RecvThread = null;
		}
	}

	/**
	 * udp连接事件，用于启动数据接收线程
	 * 
	 * @return
	 */
	public boolean ConnectSocket() {
		boolean result = false;
		try {
			if (rUdp == null)
				rUdp = new DatagramSocket(RecvPort);
			if (rPacket == null)
				rPacket = new DatagramPacket(rBuffer, rBuffer.length);
			startThread();
			result = true;
			Log.d("Farm", "线程已启动");
		} catch (SocketException se) {
			DisConnectSocket();
			System.out.println("open udp port error:" + se.getMessage());
		}
		return result;
	}

	/**
	 * 断开连接
	 */
	public void DisConnectSocket() {
		if (rUdp != null) {
			rUdp.close();
			rUdp = null;
		}
		if (rPacket != null)
			rPacket = null;
		stopThread();
	}

	/**
	 * 自定义停止服务函数调用
	 */
	public void stopService() {
		UdpDataProvider.NET_STATE = false;
		if (RecvThread != null) {
			RecvThread.stop();
			RecvThread = null;
		}
	}

}
