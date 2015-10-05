package com.farm.queue;

/**
 * ��̬����� �����ʽ��
 * 
 */
public class Command {
	/**
	 * ��������
	 */
	public final static String OPEN_WINDOWS = "WO";
	public final static String CLOSE_WINDOWS = "WC";
	/**
	 * ��ˮ����
	 */
	public final static String START_WATERING = "TO";
	public final static String STOP_WATERING = "TC";
	/**
	 * ���տ���
	 */
	public final static String OPEN_LIGHT = "LO";
	public final static String CLOSE_LIGHT = "LC";
	/**
	 * ���ȿ���
	 */
	public final static String OPEN_FAN = "FO";
	public final static String CLOSE_FAN = "FC";
	/**
	 * ����������
	 */
	public final static String OPEN_HEAT  = "HO";
	public final static String CLOSE_HEAT = "HC";
	public static final String ERROR = "ER";
}
