package com.farm.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;

import com.farm.data.DataQueue;
import com.farm.queue.Command;
import com.farm.queue.UIDataProvide;

public class HeaterControl extends Activity {
	private Button heaterControler;
	private Switch switch_heater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controler_heater);
		init();

	}

	private void init() {
		heaterControler = (Button) findViewById(R.id.btn_heatercontroler);
		switch_heater = (Switch) findViewById(R.id.switch_heater);
		setOnClickListener();
		if (UIDataProvide.ISHEAT == true) {
			heaterControler.setBackgroundResource(R.drawable.btn_heater_open);
			switch_heater.setChecked(true);
		} else {
			heaterControler.setBackgroundResource(R.drawable.btn_heater_close);
			switch_heater.setChecked(false);
		}
	}

	/**
	 * 加热器按钮点击事件
	 * 
	 */
	public void HeaterControler(View view) {
		if (UIDataProvide.ISHEAT == true) { /* 如果为开灯状态，发送关灯命令 */
			heaterControler.setBackgroundResource(R.drawable.btn_heater_close);
			switch_heater.setChecked(false);
			UIDataProvide.ISHEAT = false;
			DataQueue.getInstance().addElement(Command.CLOSE_HEAT);
		} else { /* 否则为关灯状态，发送开灯命令 */
			heaterControler.setBackgroundResource(R.drawable.btn_heater_open);
			switch_heater.setChecked(true);
			UIDataProvide.ISHEAT = true;
			DataQueue.getInstance().addElement(Command.OPEN_HEAT);
		}
	}

	private void setOnClickListener() {
		switch_heater.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (UIDataProvide.ISWATERING == true) {
					heaterControler
							.setBackgroundResource(R.drawable.btn_heater_close);
					switch_heater.setChecked(false);
					UIDataProvide.ISHEAT = false;
					DataQueue.getInstance().addElement(Command.CLOSE_HEAT);
				} else {
					heaterControler
							.setBackgroundResource(R.drawable.btn_heater_open);
					switch_heater.setChecked(true);
					UIDataProvide.ISHEAT = true;
					DataQueue.getInstance().addElement(Command.OPEN_HEAT);
				}
			}
		});
	}
}
