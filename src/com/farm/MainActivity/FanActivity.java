package com.farm.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.farm.data.DataQueue;
import com.farm.queue.Command;
import com.farm.queue.UIDataProvide;

public class FanActivity extends Activity {
	private Button fanControler;
	private ProgressBar Pro_fan_open;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controler_fan);
		init();
		setOnclickListener();
	}

	private void setOnclickListener() {
		Pro_fan_open.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (UIDataProvide.ISFAN == true) {
					Pro_fan_open.setVisibility(ProgressBar.GONE);
					fanControler.setVisibility(Button.VISIBLE);
					UIDataProvide.ISFAN = false;
					DataQueue.getInstance().addElement(Command.CLOSE_FAN);
				} else {
					Pro_fan_open.setVisibility(ProgressBar.VISIBLE);
					fanControler.setVisibility(Button.GONE);
					UIDataProvide.ISFAN = true;
					DataQueue.getInstance().addElement(Command.OPEN_FAN);
				}
			}
		});
	}

	private void init() {
		fanControler = (Button) findViewById(R.id.btn_fancontroler);
		Pro_fan_open = (ProgressBar) findViewById(R.id.Progress_fan_open);
		if (UIDataProvide.ISFAN == true) {
			Pro_fan_open.setVisibility(ProgressBar.VISIBLE);
			fanControler.setVisibility(Button.GONE);
		} else {
			Pro_fan_open.setVisibility(ProgressBar.GONE);
			fanControler.setVisibility(Button.VISIBLE);
		}
	}

	/**
	 * 按钮点击事件
	 */
	public void FanControler(View view) {
		if (UIDataProvide.ISFAN == true) {
			Pro_fan_open.setVisibility(ProgressBar.GONE);
			fanControler.setVisibility(Button.VISIBLE);
			UIDataProvide.ISFAN = false;
			DataQueue.getInstance().addElement(Command.CLOSE_FAN);
		} else {
			Pro_fan_open.setVisibility(ProgressBar.VISIBLE);
			fanControler.setVisibility(Button.GONE);
			UIDataProvide.ISFAN = true;
			DataQueue.getInstance().addElement(Command.OPEN_FAN);
		}
	}

	/*
	 * 
	 * private void setOnClickListener() { fanSwitch.setOnClickListener(new
	 * OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { if (UIDataProvide.ISWATERING ==
	 * true) { Pro_fan_open.setVisibility(ProgressBar.GONE);
	 * fanControler.setVisibility(Button.VISIBLE); fanSwitch.setChecked(false);
	 * UIDataProvide.ISFAN = false;
	 * SendDataService.getInstance().SendData(Command.CLOSE_FAN); } else {
	 * Pro_fan_open.setVisibility(ProgressBar.VISIBLE);
	 * fanControler.setVisibility(Button.GONE); fanSwitch.setChecked(true);
	 * UIDataProvide.ISFAN = true;
	 * SendDataService.getInstance().SendData(Command.OPEN_FAN); } } }); }
	 */
}
