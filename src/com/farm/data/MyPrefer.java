package com.farm.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*
 * ͨ���������洢�ͷ����û�����������ļ��������ڳ���������ʱ���Ҫ���������
 */
public class MyPrefer {
	private Context context;

	public MyPrefer(Context context) {
		this.context = context;
	}

	/**
	 * ��ȡ���͵�IP��ַ
	 * 
	 * @return
	 */
	public String getIp() {
		SharedPreferences sPref = context.getSharedPreferences(
				Constant.SOFTWARE_NAME, Context.MODE_PRIVATE);// MODE_PRIVATE��ʾ�洢��sharedpreference�ļ�ֻ�ܱ���Ӧ�ó������
		return sPref.getString(Constant.PROXY_IP, Constant.DEFAULT_IP);
	}

	/**
	 * ���÷��͵�IP��ַ
	 * 
	 * @param Proxy_IP
	 */
	public void setIP(String Proxy_IP) {

		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				Constant.SOFTWARE_NAME, context.MODE_PRIVATE);

		Editor ed = mySharedPreferences.edit();
		ed.putString(Constant.PROXY_IP, Proxy_IP);
		ed.commit();
		// MyDataInfo.Proxy_IP = Proxy_IP.trim();
	}

	/**
	 * ��ȡ���Ͷ˿ں�
	 * 
	 * @return
	 */

	public String getSendPort() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Constant.SOFTWARE_NAME, context.MODE_PRIVATE);
		String Port = sharedPreferences.getString(Constant.SEND_PROXY_PORT,
				Constant.DEFAULT_SEND_PORT + "");
		return Port;

	}

	/**
	 * ���÷��Ͷ˿ں�
	 * 
	 * @param Proxy_Port
	 */
	public void setSendPort(String Proxy_Port) {

		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				Constant.SOFTWARE_NAME, context.MODE_PRIVATE);

		Editor ed = mySharedPreferences.edit();
		ed.putString(Constant.SEND_PROXY_PORT, Proxy_Port);
		ed.commit();
		// MyDataInfo.Port = Integer.parseInt(Proxy_Port);
	}

	/**
	 * ��ȡ�������ڽ��յĶ˿ں�
	 * 
	 * @return
	 */
	public String getRecvPort() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				Constant.SOFTWARE_NAME, context.MODE_PRIVATE);
		String Port = sharedPreferences.getString(Constant.RECV_PROXY_PORT,
				Constant.DEFAULT_RECV_PORT + "");
		return Port;

	}

	/**
	 * ���ñ������ڽ��յĶ˿ں�
	 * 
	 * @param Proxy_Port
	 */
	public void setRecvPort(String Proxy_Port) {

		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				Constant.SOFTWARE_NAME, context.MODE_PRIVATE);

		Editor ed = mySharedPreferences.edit();
		ed.putString(Constant.RECV_PROXY_PORT, Proxy_Port);
		ed.commit();
		// MyDataInfo.Port = Integer.parseInt(Proxy_Port);
	}
}
