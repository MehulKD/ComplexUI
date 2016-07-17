package com.example.newgoldinglauncher;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private LedView ledview;
	private TextView cityname;
	private TextView tempView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ledview = (LedView) findViewById(R.id.led_view);
        cityname = (TextView)findViewById(R.id.cityname);
        tempView = (TextView)findViewById(R.id.temp);
    }

    @Override
    protected void onResume(){
    	super.onResume();
    	ledview.start();
    }
    
    @Override
    protected void onStop(){
    	super.onStop();
    	ledview.stop();
    }
    
}
