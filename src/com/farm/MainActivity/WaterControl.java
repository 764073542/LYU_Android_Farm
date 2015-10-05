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

public class WaterControl extends Activity {
	private Button waterControler;
	private Switch waterSwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controler_water);
		init();
	}

	private void init() {
		waterControler = (Button) findViewById(R.id.btn_watercontroler);
		waterSwitch = (Switch) findViewById(R.id.switch_water);
		setOnClickListener();
		if (UIDataProvide.ISWATERING == true) {
			waterControler.setBackgroundResource(R.drawable.btn_water_open);
			waterSwitch.setChecked(true);
		} else {
			waterControler.setBackgroundResource(R.drawable.btn_water_close);
			waterSwitch.setChecked(false);
		}
	}

	/**
	 * ��ť����¼�
	 */
	public void WaterControler(View view) {
		if (UIDataProvide.ISWATERING == true) { /* ���Ϊ��ˮ״̬�����͹ص����� */
			waterControler.setBackgroundResource(R.drawable.btn_water_close);
			UIDataProvide.ISWATERING = false;
			waterSwitch.setChecked(false);
			DataQueue.getInstance().addElement(Command.STOP_WATERING);
		} else { /* ����Ϊ�ص�״̬�����Ϳ������� */
			waterControler.setBackgroundResource(R.drawable.btn_water_open);
			UIDataProvide.ISWATERING = true;
			waterSwitch.setChecked(true);
			DataQueue.getInstance().addElement(Command.START_WATERING);
		}
	}

	private void setOnClickListener() {
		waterSwitch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (UIDataProvide.ISWATERING == true) {
					waterControler
							.setBackgroundResource(R.drawable.btn_water_close);
					UIDataProvide.ISWATERING = false;
					waterSwitch.setChecked(false);
					DataQueue.getInstance().addElement(Command.STOP_WATERING);
				} else {
					waterControler
							.setBackgroundResource(R.drawable.btn_water_open);
					UIDataProvide.ISWATERING = true;
					waterSwitch.setChecked(true);
					DataQueue.getInstance().addElement(Command.START_WATERING);
				}
			}
		});
	}
}
