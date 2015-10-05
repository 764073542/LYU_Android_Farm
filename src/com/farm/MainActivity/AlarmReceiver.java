package com.farm.MainActivity;

import com.farm.data.DataQueue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @ClassName: AlarmReceiver
 * @Description: 闹铃时间到了会进入这个广播，这个时候可以做一些该做的业务。
 * @author HuHood
 * @date 2013-11-25 下午4:44:30
 * 
 */
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String data = "ER";
		if (intent.getAction().equals("Demo01")) {

			data = intent.getStringExtra("data");
			DataQueue.getInstance().addElement(data);
			Toast.makeText(context, "命令已发送" + data, 0).show();
			Log.d("Farm", "广播接收到数据：" + data);
		}
	}

}