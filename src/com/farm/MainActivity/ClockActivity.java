package com.farm.MainActivity;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.farm.queue.Command;

public class ClockActivity extends Activity {
	private String cmd;// 保存
	private TimePicker mTimePicker;
	private Spinner selector;
	private String[] items = { "开灯", "关灯", "开风扇", "关风扇", "开窗", "关窗", "开始浇水",
			"停止浇水", "开始加热", "停止加热" };
	private int mHour = -1;
	private int mMinute = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_clock);
		// 初始化资源
		init();
		// spinner 的设置与初始化
		ListSelect();
		// 闹钟的设置
		SetClock();
	}

	/**
	 * 初始化时钟，设置首次显示当前时间
	 */
	private void SetClock() {
		mTimePicker
				.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

					@Override
					public void onTimeChanged(TimePicker view, int hourOfDay,
							int minute) {

						mHour = hourOfDay;
						mMinute = minute;
					}
				});
	}

	/**
	 * 初始化资源
	 */
	private void init() {
		/**
		 * Spinner 初始化
		 */
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, items);
		selector = (Spinner) findViewById(R.id.spinner_selector);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selector.setAdapter(adapter);
		/**
		 * 闹钟初始化
		 */
		mTimePicker = (TimePicker) findViewById(R.id.timePicker);
		
		//是否使用24小时制  
		mTimePicker.setIs24HourView(true); 
        
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if (mHour == -1 && mMinute == -1) {
			mHour = calendar.get(Calendar.HOUR_OF_DAY);
			mMinute = calendar.get(Calendar.MINUTE);
		}
		mTimePicker.setCurrentHour(mHour);
		mTimePicker.setCurrentMinute(mMinute);

	}

	/**
	 * 关于Spinner各个选项被选中是的操作-发送相应数据
	 */
	private void ListSelect() {

		selector.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(position);
				switch (position) {

				case 0:
					cmd = Command.OPEN_LIGHT;
					break;
				case 1:
					cmd = Command.CLOSE_LIGHT;
					break;
				case 2:
					cmd = Command.OPEN_FAN;
					break;
				case 3:
					cmd = Command.CLOSE_FAN;
					break;
				case 4:
					cmd = Command.OPEN_WINDOWS;
					break;
				case 5:
					cmd = Command.CLOSE_WINDOWS;
					break;
				case 6:
					cmd = Command.START_WATERING;
					break;
				case 7:
					cmd = Command.STOP_WATERING;
					break;
				case 8:
					cmd = Command.OPEN_HEAT;
					break;
				case 9:
					cmd = Command.CLOSE_HEAT;
					break;

				default:
					cmd = Command.ERROR;
					break;
				}
				Log.d("Farm", "选择了该项："+position +"--"+ cmd);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				cmd = Command.ERROR;
				;

			}
		});
	}

	/**
	 * 设定闹钟点击事件
	 * 
	 * @param view
	 */
	public void onSubmit(View view) {
		Intent intent = new Intent(this, AlarmReceiver.class);
		intent.putExtra("data", cmd);
		intent.setAction("Demo01"); //设置时钟标记
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		calendar.add(Calendar.SECOND, 10); //定时时长来响应闹钟

		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

		Toast.makeText(this, "定时器已设定!", Toast.LENGTH_LONG).show();
		this.finish();
	}

	public void onCancel(View view) {
		// 取消事件
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);

		// 取消闹钟
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.cancel(sender);
		this.finish();
	}
}
