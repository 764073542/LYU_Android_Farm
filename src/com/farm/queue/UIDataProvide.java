package com.farm.queue;

/**
 * 接收来的数据经过DataUtils进行解析之后，会将数据存储在该类中， 该类负责向外提供数据
 * 
 * @author 傲娇小笼包
 * 
 */
public class UIDataProvide {
	public static String RECV_MSG = null;
	/* false：表示晴朗，true：表示有雨 */
	public static boolean WEATHER = false;
	public static String TEMPERATURE = "0.0";
	public static String SHIDU = "0.0";
	public static String LIGHT = "0.0";
	public static String CO2 = "0.0";
	public static boolean ISLIGHT = false;
	public static boolean ISWATERING = false;
	public static boolean ISWINDOWS = false;
	public static boolean ISFAN = false;
	public static boolean ISHEAT = false;
}
