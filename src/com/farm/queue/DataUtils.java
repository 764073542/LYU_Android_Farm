package com.farm.queue;

import com.farm.data.Constant;
import com.farm.data.DataQueue;
import com.farm.data.UdpDataProvider;

import android.util.Log;

/**
 * @���յ������ݲ����н��� ���ݽ��ն���
 * @1.������������ �� �¶�(W)��ʪ��(S)������(G)��CO2Ũ��(C), ����(Y)[0:���� 1:����]����
 * @2.���Կ��ƵĽڵ��״̬ �� ��ˮ(T)������(W)�����յ�(L),����(F),������(H) ����
 * @���ݸ�ʽ�� ÿ�����ݼ�ǰ׺D��״̬�ڵ���ǰ׺S��ÿ�������÷��ţ�&���ֿ�
 * @���� �� DW11.2 & DY0 & DS20.5 & DG16.8 & DC17.8 & STO & SWC & SLO &��SFO & SHC
 * 
 *     ÿ�����ݲ���Ҫ�̶����򣬿ո������������ԣ���Сд������
 */
public class DataUtils {

	/**
	 * ���ݷ������߼�
	 * 
	 * @param data
	 */
	public void AnalyzeData(String data) {
		/* ���ַ���ת��Ϊ��д�ڽ��зָ� */
		if(data == null || data.equals("")){
			cleanAllData();
		}
		String[] temp = data.toUpperCase().split("/");
		/* ȥ��û����������λ�Ŀո񣬷�ֹ�ж��ǲ�ͨ�� */
		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp[i].trim();
		}
		if (temp.length <= 0) {
			return;
		}
		if (temp[0] == Constant.CONFIRMATION) {
			if (UdpDataProvider.NET_STATE == false) {
				DataQueue.getInstance().addElement(Constant.CONFIRMATION);
			}
			UdpDataProvider.NET_STATE = true;
			return;
		}
		if (temp.length >= 3) { // ������ʵ��Ӧ��д��̫�������������޸�
			UdpDataProvider.NET_STATE = true;
			for (int i = 0; i < temp.length; i++) {
				/* ��ֵ���ݵĻ�ȡ��ʽ */
				if (temp[i].startsWith("D")) {
					String numData = temp[i].substring(1, temp[i].length());
					checkNumData(numData);
				}
				/* ״̬���ݵĻ�ȡ��ʽ */
				else if (temp[i].startsWith("S")) {
					String statData = temp[i].substring(1, temp[i].length());
					checkStatData(statData);
				}

			}
		}	
	}
	/**
	 * ��ʼ������
	 */
	private void cleanAllData() {
		UIDataProvide.CO2 = "0";
		UIDataProvide.LIGHT = "0";
		UIDataProvide.TEMPERATURE = "0";
		UIDataProvide.SHIDU = "0.0";
		UIDataProvide.WEATHER = false;
		UIDataProvide.ISLIGHT = false;
		UIDataProvide.ISWATERING = false;
		UIDataProvide.ISWINDOWS = false;
		UIDataProvide.ISFAN = false;
		UIDataProvide.ISHEAT = false;
	}

	/**
	 * �жϿɿ��ƽڵ�ĵ�ǰ״̬
	 * 
	 * @param statData
	 */
	private void checkStatData(String statData) {
		if (statData.startsWith("T")) { // �ж������ڽ�ˮ����ֹͣ��ˮ
			if (statData.equals(Command.START_WATERING)) {
				UIDataProvide.ISWATERING = true;
			} else if (statData.equals(Command.STOP_WATERING)) {
				UIDataProvide.ISWATERING = false;
			} else {
				UIDataProvide.ISWATERING = false;
			}
		} else if (statData.startsWith("W")) { // �жϴ����ǿ������ǹر�
			if (statData.equals(Command.OPEN_WINDOWS)) {
				UIDataProvide.ISWINDOWS = true;
			} else if (statData.equals(Command.CLOSE_WINDOWS)) {
				UIDataProvide.ISWINDOWS = false;
			} else {
				UIDataProvide.ISWINDOWS = false;
			}
		} else if (statData.startsWith("L")) { // �жϹ��յ��ǿ������ǹر�
			if (statData.equals(Command.OPEN_LIGHT)) {
				UIDataProvide.ISLIGHT = true;
			} else if (statData.equals(Command.CLOSE_LIGHT)) {
				UIDataProvide.ISLIGHT = false;
			} else {
				UIDataProvide.ISLIGHT = false;
			}
		} else if (statData.startsWith("F")) { // �жϷ����ǿ������ǹر�
			if (statData.equals(Command.OPEN_FAN)) {
				UIDataProvide.ISFAN = true;
			} else if (statData.equals(Command.CLOSE_FAN)) {
				UIDataProvide.ISFAN = false;
			} else {
				UIDataProvide.ISFAN = false;
			}
		} else if (statData.startsWith("H")) { // �жϼ������ǿ������ǹر�
			if (statData.equals(Command.OPEN_HEAT)) {
				UIDataProvide.ISHEAT = true;
			} else if (statData.equals(Command.CLOSE_HEAT)) {
				UIDataProvide.ISHEAT = false;
			} else {
				UIDataProvide.ISHEAT = false;
			}
		} else {
			return;
		}
	}

	/**
	 * �ж���ֵ���ݷֱ����ĸ�������������
	 * 
	 * @param numData
	 */
	private void checkNumData(String numData) {
		if (numData.startsWith("W")) { // ��ȡ�¶�����
			UIDataProvide.TEMPERATURE = (numData.substring(1, numData.length()))
					.trim();
		} else if (numData.startsWith("S")) { // ��ȡʪ������
			UIDataProvide.SHIDU = (numData.substring(1, numData.length()))
					.trim();
		} else if (numData.startsWith("G")) { // ��ȡ����ǿ������
			UIDataProvide.LIGHT = (numData.substring(1, numData.length()))
					.trim();
		} else if (numData.startsWith("C")) { // ��ȡCO2Ũ������
			UIDataProvide.CO2 = (numData.substring(1, numData.length())).trim();
		} else if (numData.startsWith("Y")) { // ��ȡ��ѩ����������
			if ("1".equals((numData.substring(1, numData.length())))) {
				UIDataProvide.WEATHER = true;
			} else {
				UIDataProvide.WEATHER = false;
			}
		} else {
			return;
		}
	}
}
