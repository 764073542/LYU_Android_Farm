package com.farm.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*
 * 通过此类来存储和访问用户保存的配置文件，所以在程序启动的时候就要加载这个类
 */
public class MyPrefer {
	private Context context;

	public MyPrefer(Context context) {
		this.context = context;
	}

	/**
	 * 获取发送的IP地址
	 * 
	 * @return
	 */
	public String getIp() {
		SharedPreferences sPref = context.getSharedPreferences(
				Constant.SOFTWARE_NAME, Context.MODE_PRIVATE);// MODE_PRIVATE表示存储的sharedpreference文件只能被本应用程序访问
		return sPref.getString(Constant.PROXY_IP, Constant.DEFAULT_IP);
	}

	/**
	 * 设置发送的IP地址
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
	 * 获取发送端口号
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
	 * 设置发送端口号
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
	 * 获取本机用于接收的端口号
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
	 * 设置本机用于接收的端口号
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
