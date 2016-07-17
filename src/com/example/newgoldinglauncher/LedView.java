package com.example.newgoldinglauncher;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LedView extends LinearLayout{
	private TextView timetext;
	private TextView daytext;
	private static final String DATE_FORMAT = "%02d:%02d:%02d";
	private static final String DAY_FORMAT = "ÐÇÆÚ%s%sÔÂ%sÈÕ";
	private static final int REFRESH_DELAY=500;
	
	private final Handler handler = new Handler();
	private final Runnable timerefresh = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
			final Date date = new Date();
			calendar.setTime(date);
			timetext.setText(String.format(DATE_FORMAT, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
					calendar.get(Calendar.SECOND)));
			String week = String.valueOf(calendar.get(Calendar.WEDNESDAY));
			String month = String.valueOf(calendar.get(Calendar.MONTH));
			String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			Log.e("week", week);
			Log.e("month", month);
			Log.e("day",day);
			daytext.setText(String.format(DAY_FORMAT, week,month,day));
			handler.postDelayed(this, REFRESH_DELAY);
			
		}
	};
	
	public LedView(Context context){
		super(context);
		init(context);
	}
	
	public LedView(Context context,AttributeSet attrs){
		super(context,attrs);
		init(context);
	}
	
	@SuppressLint("NewApi") 
	public LedView(Context context,AttributeSet attrs,int defStyle){
		super(context, attrs, defStyle);
		init(context);
		
	}
	
	public void init(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate( R.layout.ledview, this);
		timetext = (TextView)view.findViewById(R.id.led_time);
		daytext = (TextView)view.findViewById(R.id.led_day);
	}
	
	public void start(){
		handler.post(timerefresh);
	}
	
	public void stop(){
		handler.removeCallbacks(timerefresh);
	}
	

}
