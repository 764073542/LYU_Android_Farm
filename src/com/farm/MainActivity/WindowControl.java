package com.farm.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.farm.data.DataQueue;
import com.farm.queue.Command;
import com.farm.queue.UIDataProvide;

public class WindowControl extends Activity {
	private Button windowControler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.controler_window);
		init();
	}

	/**
	 * 初始化控件，并获取当前状态
	 */
	private void init() {
		windowControler = (Button) findViewById(R.id.btn_windowcontroler);
		if (UIDataProvide.ISWINDOWS == true) {
			windowControler.setBackgroundResource(R.drawable.btn_window_open);
		} else {
			windowControler.setBackgroundResource(R.drawable.btn_window_close);
		}
	}

	/**
	 * 按钮点击事件
	 */
	public void WindowControler(View view) {
		if (UIDataProvide.ISWINDOWS == true) { /* 如果为开灯状态，发送关灯命令 */
			windowControler.setBackgroundResource(R.drawable.btn_window_close);
			UIDataProvide.ISWINDOWS = false;
			DataQueue.getInstance().addElement(Command.CLOSE_WINDOWS);
		} else { /* 否则为关灯状态，发送开灯命令 */
			windowControler.setBackgroundResource(R.drawable.btn_window_open);
			UIDataProvide.ISWINDOWS = true;
			DataQueue.getInstance().addElement(Command.OPEN_WINDOWS);
		}
	}
}
