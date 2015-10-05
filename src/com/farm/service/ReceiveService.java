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
	private Thread RecvThread = null;// ���������߳�
	private DatagramSocket rUdp = null;// ��������Socket
	private DatagramPacket rPacket = null;
	private int RecvPort = UdpDataProvider.RECV_PORT;
	private byte[] rBuffer = new byte[1024];// �������ݻ���1024�ֽ�
	private DataUtils utils = new DataUtils();

	public ReceiveService(DatagramSocket netUdpSocket) {
		rUdp = netUdpSocket;
	}

	/**
	 * �߳�run���� ���巽���ο� com.farm.net.UdpThread
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
	 * ���յ��������ı���ʽ����ʾ
	 */
	private void recvData() {
		try {
			rUdp.receive(rPacket);
			RecvData = new String(rPacket.getData(), Constant.ENDODED).trim();
			/**
			 * �����յ������ݽ��н�����
			 */			
			utils.AnalyzeData(RecvData);
			UIDataProvide.RECV_MSG = RecvData;
			Log.d("Farm", "���յ����ݣ�" + RecvData);
			/**
			 * ����Ŀ����Ϊ�����rBuffer��������ݣ���Ȼ���յ��������ݻ���֮ǰ���ݵ�һ���� ���磺 ��һ�η��� 1234567 �ڶ��η���
			 * 33 ����ڶ����յ� 3334567
			 */
			rBuffer = null;
			rBuffer = new byte[1024];
			rPacket = new DatagramPacket(rBuffer, rBuffer.length); // �˴���ջ������ķ�ʽ��������

		} catch (IOException ie) {
			Log.d("Farm", "���ݴ���");
		}
	}

	/**
	 * ��������
	 */
	public void startReceive() {
		if (ConnectSocket()) {
			System.out.println("�����߳��Ѿ�����");

		} else {
			System.out.println("�����߳��Ѿ������в�������");
		}
	}

	/**
	 * �رշ���
	 */
	public void closeReceive() {
		stopThread();
	}

	/**
	 * �����߳�
	 */
	private void startThread() {
		if (RecvThread == null) {
			RecvThread = new Thread(this);
			RecvThread.start();
		}
	}

	/**
	 * �ر��߳�
	 */
	private void stopThread() {
		if (RecvThread != null) {
			RecvThread.stop();
			RecvThread = null;
		}
	}

	/**
	 * udp�����¼��������������ݽ����߳�
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
			Log.d("Farm", "�߳�������");
		} catch (SocketException se) {
			DisConnectSocket();
			System.out.println("open udp port error:" + se.getMessage());
		}
		return result;
	}

	/**
	 * �Ͽ�����
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
	 * �Զ���ֹͣ����������
	 */
	public void stopService() {
		UdpDataProvider.NET_STATE = false;
		if (RecvThread != null) {
			RecvThread.stop();
			RecvThread = null;
		}
	}

}
