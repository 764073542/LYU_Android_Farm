package com.farm.data;

import java.util.ArrayList;
import java.util.List;

/**
 * ���õ������ģʽ
 * 
 * @author ����С����
 * 
 */
public class DataQueue {
	List<String> dataList = new ArrayList<String>();
	private final static DataQueue dataQueue = new DataQueue();

	/**
	 * ˽�л����캯��
	 */
	private DataQueue() {

	}

	/**
	 * �����ṩ�����ȡ����
	 * 
	 * @return
	 */
	public static DataQueue getInstance() {
		return dataQueue;
	}

	/**
	 * ���Ԫ�ط���
	 * 
	 * @param str
	 *            boolean add(E e)��ָ����Ԫ����ӵ����б��β����
	 */
	public void addElement(String str) {
		// Log.i("Farm", "���г���Ϊ��" + dataList.size());
		/* �жϵ�ǰ������� */
		// ���Խ���Ҫ���͵�������ӵ����ף����ȷ��ͣ��ڶ��й�������������
		// l>5,���
		if (dataList.size() > 10) {
			clearQueue();
		}
		/**
		 * רҵһ��ʹ���һ���࣬�����ַ���ֵ�������ݰ�����������ʱ��
		 */

		dataList.add(0,str);
	}

	/**
	 * ɾ�����һ��Ԫ��
	 */
	private void deleteLastElement() {
		dataList.remove(dataList.size() - 1);
	}

	/**
	 * ɾ�����е�һ��Ԫ�ط��� E remove(int index) �Ƴ����б���ָ��λ���ϵ�Ԫ�ء�
	 */
	public void deleteElement() {
		if (dataList.size() > 0) {
			dataList.remove(0);
		}

	}

	/**
	 * ��ն��з��� void clear() �Ƴ����б��е�����Ԫ�ء�
	 */
	public void clearQueue() {
		dataList.clear();
	}

	/**
	 * ���ط������ݺ���������0��Ԫ�ص��ַ�ֵ
	 */
	public String getValue() {
		try {
			return dataList.get(0);
		} catch (Exception e) {
			return "null";
		}
	}

	/**
	 * ���ص�ǰ���еĳ���
	 * 
	 * @return
	 */
	public int getLength() {
		return dataList.size();
	}
}
