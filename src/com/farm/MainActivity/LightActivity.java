package com.farm.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.farm.data.DataQueue;
import com.farm.queue.Command;
import com.farm.queue.UIDataProvide;

public class LightActivity extends Activity {
	private Button lightControler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controler_light);

		init();
	}

	/**
	 * 初始化控件，并获取当前状态
	 */
	private void init() {
		lightControler = (Button) findViewById(R.id.btn_lightcontroler);
		if (UIDataProvide.ISLIGHT == true) {
			lightControler.setBackgroundResource(R.drawable.btn_light_open);
		} else {
			lightControler.setBackgroundResource(R.drawable.btn_light_close);
		}
	}

	/**
	 * 灯光控制按钮事件
	 * 
	 * @param view
	 */
	public void LightControler(View view) {
		if (UIDataProvide.ISLIGHT == true) { /* 如果为开灯状态，发送关灯命令 */
			lightControler.setBackgroundResource(R.drawable.btn_light_close);
			UIDataProvide.ISLIGHT = false;
			DataQueue.getInstance().addElement(Command.CLOSE_LIGHT);
		} else { /* 否则为关灯状态，发送开灯命令 */
			lightControler.setBackgroundResource(R.drawable.btn_light_open);
			UIDataProvide.ISLIGHT = true;
			DataQueue.getInstance().addElement(Command.OPEN_LIGHT);
		}
	}
}
