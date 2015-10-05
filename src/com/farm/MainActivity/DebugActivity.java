package com.farm.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.farm.data.Constant;
import com.farm.data.DataQueue;
import com.farm.data.UdpDataProvider;
import com.farm.queue.UIDataProvide;

public class DebugActivity extends Activity {
	private EditText Receive_Msg, Send_Msg;
	private TextView tv_Net_State;
	public static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.d("Farm", "Hanlder" + msg.what);
			switch (msg.what) {
			case 0:
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}

	};
	/**
	 * 更新UI的子线程
	 */
	Runnable runnable = new Runnable() {
		public void run() {
			Receive_Msg.setText(UIDataProvide.RECV_MSG);
			if (UdpDataProvider.NET_STATE == true) {
				tv_Net_State.setText("已连接");
				tv_Net_State.setTextColor(Color.rgb(0, 255, 0));
			} else {

				tv_Net_State.setText("未连接");
				tv_Net_State.setTextColor(Color.rgb(255, 0, 0));
			}
			handler.postDelayed(runnable, 1000);

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);
		Receive_Msg = (EditText) findViewById(R.id.edt_receivedata);
		Send_Msg = (EditText) findViewById(R.id.edt_senddata);
		tv_Net_State = (TextView) findViewById(R.id.tv_net_state);
		handler.postDelayed(runnable, 1000);		
	}

	/**
	 * 返回按钮点击事件
	 * 
	 * @param view
	 */
	public void Reback(View view) {
		startActivity(new Intent(this, MainActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	}

	/**
	 * 数据发送点击事件 应该调用服务中的方法
	 * 
	 * @param view
	 */
	public void SendUdpData(View view) {
		String data = Send_Msg.getText() + "";
		Log.d("Farm", "需要发送的数据为：" + data);
		if (!(data.trim().equals("")))
			DataQueue.getInstance().addElement(data);
	}
	/**
	 * 网络测试按钮点击事件
	 */
	public void NetStateTest(View view){
		DataQueue.getInstance().addElement(Constant.CONFIRMATION);
	}

}
