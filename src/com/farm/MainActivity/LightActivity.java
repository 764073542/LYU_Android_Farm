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
	 * ��ʼ���ؼ�������ȡ��ǰ״̬
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
	 * �ƹ���ư�ť�¼�
	 * 
	 * @param view
	 */
	public void LightControler(View view) {
		if (UIDataProvide.ISLIGHT == true) { /* ���Ϊ����״̬�����͹ص����� */
			lightControler.setBackgroundResource(R.drawable.btn_light_close);
			UIDataProvide.ISLIGHT = false;
			DataQueue.getInstance().addElement(Command.CLOSE_LIGHT);
		} else { /* ����Ϊ�ص�״̬�����Ϳ������� */
			lightControler.setBackgroundResource(R.drawable.btn_light_open);
			UIDataProvide.ISLIGHT = true;
			DataQueue.getInstance().addElement(Command.OPEN_LIGHT);
		}
	}
}
