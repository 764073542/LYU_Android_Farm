package com.farm.queue;

import com.farm.data.Constant;
import com.farm.data.DataQueue;
import com.farm.data.UdpDataProvider;

import android.util.Log;

/**
 * @接收到的数据并进行解析 数据接收对象：
 * @1.传感器的数据 ： 温度(W)，湿度(S)，光照(G)，CO2浓度(C), 天气(Y)[0:无雨 1:有雨]……
 * @2.可以控制的节点的状态 ： 浇水(T)，窗户(W)，光照灯(L),风扇(F),加热器(H) ……
 * @数据格式： 每组数据加前缀D，状态节点用前缀S，每个数据用符号（&）分开
 * @例如 ： DW11.2 & DY0 & DS20.5 & DG16.8 & DC17.8 & STO & SWC & SLO &　SFO & SHC
 * 
 *     每个数据不需要固定次序，空格留不留都可以，大小写都可以
 */
public class DataUtils {

	/**
	 * 数据分析主逻辑
	 * 
	 * @param data
	 */
	public void AnalyzeData(String data) {
		/* 将字符串转换为大写在进行分割 */
		if(data == null || data.equals("")){
			cleanAllData();
		}
		String[] temp = data.toUpperCase().split("/");
		/* 去除没组数据中首位的空格，防止判断是不通过 */
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
		if (temp.length >= 3) { // 这里其实不应该写的太死，后期在做修改
			UdpDataProvider.NET_STATE = true;
			for (int i = 0; i < temp.length; i++) {
				/* 数值数据的获取方式 */
				if (temp[i].startsWith("D")) {
					String numData = temp[i].substring(1, temp[i].length());
					checkNumData(numData);
				}
				/* 状态数据的获取方式 */
				else if (temp[i].startsWith("S")) {
					String statData = temp[i].substring(1, temp[i].length());
					checkStatData(statData);
				}

			}
		}	
	}
	/**
	 * 初始化数据
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
	 * 判断可控制节点的当前状态
	 * 
	 * @param statData
	 */
	private void checkStatData(String statData) {
		if (statData.startsWith("T")) { // 判断是正在浇水还是停止浇水
			if (statData.equals(Command.START_WATERING)) {
				UIDataProvide.ISWATERING = true;
			} else if (statData.equals(Command.STOP_WATERING)) {
				UIDataProvide.ISWATERING = false;
			} else {
				UIDataProvide.ISWATERING = false;
			}
		} else if (statData.startsWith("W")) { // 判断窗户是开启还是关闭
			if (statData.equals(Command.OPEN_WINDOWS)) {
				UIDataProvide.ISWINDOWS = true;
			} else if (statData.equals(Command.CLOSE_WINDOWS)) {
				UIDataProvide.ISWINDOWS = false;
			} else {
				UIDataProvide.ISWINDOWS = false;
			}
		} else if (statData.startsWith("L")) { // 判断光照灯是开启还是关闭
			if (statData.equals(Command.OPEN_LIGHT)) {
				UIDataProvide.ISLIGHT = true;
			} else if (statData.equals(Command.CLOSE_LIGHT)) {
				UIDataProvide.ISLIGHT = false;
			} else {
				UIDataProvide.ISLIGHT = false;
			}
		} else if (statData.startsWith("F")) { // 判断风扇是开启还是关闭
			if (statData.equals(Command.OPEN_FAN)) {
				UIDataProvide.ISFAN = true;
			} else if (statData.equals(Command.CLOSE_FAN)) {
				UIDataProvide.ISFAN = false;
			} else {
				UIDataProvide.ISFAN = false;
			}
		} else if (statData.startsWith("H")) { // 判断加热器是开启还是关闭
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
	 * 判断数值数据分别是哪个传感器的数据
	 * 
	 * @param numData
	 */
	private void checkNumData(String numData) {
		if (numData.startsWith("W")) { // 获取温度数据
			UIDataProvide.TEMPERATURE = (numData.substring(1, numData.length()))
					.trim();
		} else if (numData.startsWith("S")) { // 获取湿度数据
			UIDataProvide.SHIDU = (numData.substring(1, numData.length()))
					.trim();
		} else if (numData.startsWith("G")) { // 获取光照强度数据
			UIDataProvide.LIGHT = (numData.substring(1, numData.length()))
					.trim();
		} else if (numData.startsWith("C")) { // 获取CO2浓度数据
			UIDataProvide.CO2 = (numData.substring(1, numData.length())).trim();
		} else if (numData.startsWith("Y")) { // 获取雨雪传感器数据
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
