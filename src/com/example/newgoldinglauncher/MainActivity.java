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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
	private int[] imgId = new int[]{R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,
			R.drawable.h,R.drawable.i,R.drawable.j,R.drawable.k};
	private HashMap<Integer, View>selector = new HashMap<Integer, View>();
	private static int position;
	private ArrayList<Integer> imgList = new ArrayList<Integer>();
	private View selectView;

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
    
    private void init(Context context){//初始化界面
    	LayoutInflater inflater = getLayoutInflater();
    	for(int i = 0;i < imgId.length;i++){
    		imgList.add(imgId[i]);
    		Log.e("imgId",String.valueOf(imgId[i]));
    	}
    	adapter = new HorizontalAdapter(context, imgList);
    	horizontalScroller.setOnClickItemListener(new OnItemCilckListener() {//自己编写的接口，为了将选中的view的背景变蓝
			@Override
			public void onClick(View v, int pos) {
				// TODO Auto-generated method stub
				Log.e("item", "pos"+pos);
				v.setBackgroundColor(Color.parseColor("#AA024DA4"));
				horizontalScroller.setfirstposition(v);
				selectView = v;
				position = pos;
				Log.e("position", ""+position);
			}
		});
    	horizontalScroller.initData(adapter);
//    	horizontalScroller.setOnCurrentImageChangeListener(new CurrentImageChangeListener() {
//			
//			@Override
//			public void OnCurrentChange(int position, View v) {
//				// TODO Auto-generated method stub
//				v.setBackgroundColor(Color.parseColor("#AA024DA4"));
//			}
//		});
    	
    	previous.setOnImageClickListener(new OnImageClickListener() {//向前滑动控件的接口

			@Override
			public void click(View v) {
				// TODO Auto-generated method stub
				Log.e("previous", "here");
				if(selectView == null){
					selectView = horizontalScroller.getInitView();
				}else {
					previous.getImgId(selectView);
				}
				
			}
		});
    	
    	next.setOnImageClickListener(new OnImageClickListener() {//向后滑动控件
			
			@Override
			public void click(View v) {
				// TODO Auto-generated method stub
				Log.e("next", "here");
				if(selectView == null){
					selectView = horizontalScroller.getInitView();
				}else {
					next.getImgId(selectView);
				}
				
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
