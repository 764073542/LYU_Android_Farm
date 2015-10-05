package com.farm.service;

/****************************************
 * ֻ�з��Ͷ˵�IP��ַ�ͽ��ܵ������ݰ��ķ��͵�ַ��ͬ�������������ݰ��Ų��ᱻ����
 ***************************************/
/**
 * Linux����ƽ̨�ϵ����ݷ���Ӧ�÷����߳����棬Ҫÿ��һ��ʱ�����ⷢ��һ������
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.farm.data.Constant;
import com.farm.data.DataQueue;
import com.farm.data.UdpDataProvider;

import android.util.Log;

public class NetSendService implements Runnable {
	private Thread netSendThread = null;
	private DatagramSocket udpSendSocket;// ��������Socket
	private DatagramPacket sendPacket = null;
	private String netSendIP = UdpDataProvider.PROXY_IP;
	private int netSendPort = UdpDataProvider.SEND_PORT;
	private byte[] sBuffer = null;
	private DataQueue dataQueue = DataQueue.getInstance();

	public NetSendService(DatagramSocket netUdpSocket) {
		udpSendSocket = netUdpSocket;
	}

	/**
	 * ��һЩ���ݽ��г�ʼ�� ��ʱ���ᱻ���ã���Ϊ��������ʱ������Setting�����޸�IP�˿ں���θ��������⡣
	 * 
	 * @throws SocketException
	 */
	public void init() throws SocketException {

		if (!(netSendIP.equals(UdpDataProvider.PROXY_IP))
				|| (netSendPort != UdpDataProvider.SEND_PORT)) {
			netSendIP = UdpDataProvider.PROXY_IP;
			netSendPort = UdpDataProvider.SEND_PORT;
		}
	}

	/**
	 * ������ͨ�ı���ʽ������
	 * 
	 * @param SData
	 */
	public void SendData(String SData) {
		try {
			init();
			sBuffer = SData.getBytes(Constant.ENDODED);

			sendPacket = new DatagramPacket(sBuffer, sBuffer.length,
					InetAddress.getByName(netSendIP), netSendPort);

			udpSendSocket.send(sendPacket);
			Log.d("Farm", "�ɹ���������Ϊ��" + SData);
			sendPacket = null;
			sBuffer = null;
		} catch (IOException ie) {
			udpSendSocket.close();
			udpSendSocket = null;
			sendPacket = null;
			UdpDataProvider.NET_STATE = false;
			Log.d("Farm", "���ݷ����쳣");
		} catch (Exception e) {
			UdpDataProvider.NET_STATE = false;
			Log.e("Farm", "���ݷ��ʹ��ڴ���");
		}
	}

	/**
	 * �߳�run()����
	 */
	@Override
	public void run() {

		while (Thread.currentThread() == netSendThread) {
			try {
				if (dataQueue.getLength() > 0) {

					SendData(dataQueue.getValue());
					dataQueue.deleteElement();
				}
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �Զ������������
	 */
	public void startSend() {
		if (udpSendSocket == null)
			try {
				udpSendSocket = new DatagramSocket();
			} catch (SocketException e) {
				UdpDataProvider.NET_STATE = false;
				Log.e("Farm", "DatagramSocket����ʧ��");
			}
		if (netSendThread == null) {
			netSendThread = new Thread(this);
			netSendThread.start();
		}
	}

	/**
	 * �Զ������ֹͣ����
	 */
	public void stopService() {
		UdpDataProvider.NET_STATE = false;
		if (netSendThread != null) {
			netSendThread.stop();
			netSendThread = null;
		}
	}

}
