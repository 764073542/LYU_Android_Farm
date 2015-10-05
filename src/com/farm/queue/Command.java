package com.farm.queue;

/**
 * 静态命令常量 命令格式：
 * 
 */
public class Command {
	/**
	 * 窗户控制
	 */
	public final static String OPEN_WINDOWS = "WO";
	public final static String CLOSE_WINDOWS = "WC";
	/**
	 * 浇水控制
	 */
	public final static String START_WATERING = "TO";
	public final static String STOP_WATERING = "TC";
	/**
	 * 光照控制
	 */
	public final static String OPEN_LIGHT = "LO";
	public final static String CLOSE_LIGHT = "LC";
	/**
	 * 风扇控制
	 */
	public final static String OPEN_FAN = "FO";
	public final static String CLOSE_FAN = "FC";
	/**
	 * 加热器控制
	 */
	public final static String OPEN_HEAT  = "HO";
	public final static String CLOSE_HEAT = "HC";
	public static final String ERROR = "ER";
}
