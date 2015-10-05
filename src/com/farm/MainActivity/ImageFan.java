package com.farm.MainActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImageFan extends Activity {
	private ProgressBar progress;
	private ImageView image_fan_close;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageview);
		progress = (ProgressBar) findViewById(R.id.Progress);
		image_fan_close = (ImageView) findViewById(R.id.image_fan_close);
	}
	public void Start(View view){
		image_fan_close.setVisibility(ImageView.GONE);
		progress.setVisibility(ProgressBar.VISIBLE);
	}
	public void Stop(View view){
		progress.setVisibility(ProgressBar.GONE); //不可见且不占用原来的布局控空间
		image_fan_close.setVisibility(ImageView.VISIBLE);
	}
}
