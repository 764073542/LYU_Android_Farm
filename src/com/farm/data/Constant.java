package com.farm.data;

/**
 * 该类中的数据大部分用于SharedPreference中保存数据的名称
 * @author 傲娇小笼包
 *
 */
public class Constant {
	/**
	 * IP地址指定名称
	 */
	public final static String PROXY_IP = "PROXY_IP";
	/**
	 * 发送端口号指定名称
	 */
	public final static String SEND_PROXY_PORT = "SEND_PROXY_PORT";
	/**
	 * 接收端口号指定名称
	 */
	public final static String RECV_PROXY_PORT = "RECV_PROXY_PORT";
	/**
	 * 默认IP地址
	 */
	public final static String DEFAULT_IP = "192.168.1.100";
	/**
	 * 默认发送方的（远程）端口号
	 */
	public final static int DEFAULT_SEND_PORT = 60000;
	/**
	 * 默认接收方（本机）端口号
	 */
	public final static int DEFAULT_RECV_PORT = 60000;
	/**
	 * 唯一的字符串标志着，软件的名称，此命名用于保存用户配置文件的文件名
	 */
	public final static String SOFTWARE_NAME = "Intelligent_agriculture";
	/**
	 * 定义错误数据，或异常等发送指定命令
	 */
	public final static String SEND_ERROR = "ERROR";
	/**
	 * 定义数据接收发送默认的编码方式
	 */
	public final static String ENDODED = "UTF-8";
	/**
	 * 数据库名称
	 */
	public static final String DATABASE_NAME = "LYU_FARM";
	/**
	 * 数据库版本
	 */
	public static final int DATABASE_VERSION = 1;
	/**
	 * 连接确认数据
	 */
	public final static String CONFIRMATION = "HELLO";
}
