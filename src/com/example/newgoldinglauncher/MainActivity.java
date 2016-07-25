package com.example.newgoldinglauncher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import com.example.adapter.HorizontalAdapter;
import com.example.adapter.ListAdapter;
import com.example.widget.HorizontalListView;
import com.example.widget.LedView;
import com.example.widget.MyHorizontalScroller;
import com.example.widget.MyHorizontalScroller.CurrentImageChangeListener;
import com.example.widget.MyHorizontalScroller.OnItemCilckListener;
import com.example.widget.MyImageView;
import com.example.widget.MyImageView.OnImageClickListener;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private LedView ledview;
	private TextView cityname;
	private TextView tempView;
	private MyImageView previous;
	private MyImageView next;
	private HorizontalListView fountionList;
	private MyHorizontalScroller horizontalScroller;
	private HorizontalAdapter adapter;
	private int[] imgId = new int[]{R.drawable.fountion1,R.drawable.fountion2,R.drawable.fountion3,R.drawable.fountion4,
			R.drawable.fountion5,R.drawable.fountion6,R.drawable.fountion7,R.drawable.fountion8,R.drawable.fountion9,
			R.drawable.fountion10,R.drawable.fountion11};
	private HashMap<Integer, View>selector = new HashMap<Integer, View>();
	private int positiion = 0;
	private ArrayList<Integer> imgList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ledview = (LedView) findViewById(R.id.led_view);
        cityname = (TextView)findViewById(R.id.cityname);
        tempView = (TextView)findViewById(R.id.temp);
        horizontalScroller = (MyHorizontalScroller)findViewById(R.id.fountion_list);
        previous = (MyImageView)findViewById(R.id.previous);
        next = (MyImageView)findViewById(R.id.next);
        init(this);
        
    }
    
    private void init(Context context){
    	LayoutInflater inflater = getLayoutInflater();
    	for(int i = 0;i < imgId.length;i++){
    		imgList.add(imgId[i]);
    		Log.e("imgId",String.valueOf(imgId[i]));
    	}
    	adapter = new HorizontalAdapter(context, imgList);
    	horizontalScroller.setOnClickItemListener(new OnItemCilckListener() {
			@Override
			public void onClick(View v, int pos) {
				// TODO Auto-generated method stub
				Log.e("item", "here");
				v.setBackgroundColor(Color.parseColor("#AA024DA4"));
				positiion = pos;
				Log.e("vpos", ""+pos);
			}
		});
    	horizontalScroller.initData(adapter);
    	horizontalScroller.setOnCurrentImageChangeListener(new CurrentImageChangeListener() {
			
			@Override
			public void OnCurrentChange(int position, View v) {
				// TODO Auto-generated method stub
				v.setBackgroundColor(Color.parseColor("#AA024DA4"));
			}
		});
    	
    	previous.setOnImageClickListener(new OnImageClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				previous.getImgId(positiion);
			}
		});
    	
    	next.setOnImageClickListener(new OnImageClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				previous.getImgId(positiion);
			}
		});
    	
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
