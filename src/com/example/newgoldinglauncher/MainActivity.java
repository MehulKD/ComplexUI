package com.example.newgoldinglauncher;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.example.adapter.ListAdapter;
import com.example.widget.HorizontalListView;
import com.example.widget.LedView;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private LedView ledview;
	private TextView cityname;
	private TextView tempView;
	private ImageView previous;
	private ImageView next;
	private HorizontalListView fountionList;
	private int[] imgId = new int[]{R.drawable.fountion1,R.drawable.fountion2,R.drawable.fountion3,R.drawable.fountion4,
			R.drawable.fountion5,R.drawable.fountion6,R.drawable.fountion7,R.drawable.fountion8,R.drawable.fountion9,
			R.drawable.fountion10,R.drawable.fountion11};
	
	private ArrayList<Integer> imgList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ledview = (LedView) findViewById(R.id.led_view);
        cityname = (TextView)findViewById(R.id.cityname);
        tempView = (TextView)findViewById(R.id.temp);
        fountionList = (HorizontalListView)findViewById(R.id.fountion_list);
        previous = (ImageView)findViewById(R.id.previous);
        next = (ImageView)findViewById(R.id.next);
        init(this);
        
    }
    
    private void init(Context context){
    	LayoutInflater inflater = getLayoutInflater();
    	for(int i = 0;i < imgId.length;i++){
    		imgList.add(imgId[i]);
    		Log.e("imgId",String.valueOf(imgId[i]));
    	}
    	for(int i = 0;i < imgList.size();i++){
    		ViewGroup layout = (ViewGroup)inflater.inflate(R.layout.imageview, fountionList,false);
    		ImageView img = (ImageView)layout.findViewById(R.id.image);
    		img.setImageResource(imgList.get(i));
    		Log.e("layout",String.valueOf(i));
    		//layout.getLayoutParams().width = 500;
    		fountionList.addView(layout);
    	}
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
