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
	private String cmd;// ����
	private TimePicker mTimePicker;
	private Spinner selector;
	private String[] items = { "����", "�ص�", "������", "�ط���", "����", "�ش�", "��ʼ��ˮ",
			"ֹͣ��ˮ", "��ʼ����", "ֹͣ����" };
	private int mHour = -1;
	private int mMinute = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_clock);
		// ��ʼ����Դ
		init();
		// spinner ���������ʼ��
		ListSelect();
		// ���ӵ�����
		SetClock();
	}

	/**
	 * ��ʼ��ʱ�ӣ������״���ʾ��ǰʱ��
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
	 * ��ʼ����Դ
	 */
	private void init() {
		/**
		 * Spinner ��ʼ��
		 */
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, items);
		selector = (Spinner) findViewById(R.id.spinner_selector);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selector.setAdapter(adapter);
		/**
		 * ���ӳ�ʼ��
		 */
		mTimePicker = (TimePicker) findViewById(R.id.timePicker);
		
		//�Ƿ�ʹ��24Сʱ��  
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
	 * ����Spinner����ѡ�ѡ���ǵĲ���-������Ӧ����
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
				Log.d("Farm", "ѡ���˸��"+position +"--"+ cmd);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				cmd = Command.ERROR;
				;

			}
		});
	}

	/**
	 * �趨���ӵ���¼�
	 * 
	 * @param view
	 */
	public void onSubmit(View view) {
		Intent intent = new Intent(this, AlarmReceiver.class);
		intent.putExtra("data", cmd);
		intent.setAction("Demo01"); //����ʱ�ӱ��
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		calendar.add(Calendar.SECOND, 10); //��ʱʱ������Ӧ����

		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

		Toast.makeText(this, "��ʱ�����趨!", Toast.LENGTH_LONG).show();
		this.finish();
	}

	public void onCancel(View view) {
		// ȡ���¼�
		Intent intent = new Intent(this, AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);

		// ȡ������
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.cancel(sender);
		this.finish();
	}
}
