package com.example.widget;

import java.util.HashMap;
import java.util.LinkedList;

import com.example.newgoldinglauncher.R;
import com.example.widget.MyHorizontalScroller.OnItemCilckListener;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnTouchListener;;

public class MyImageView extends ImageView implements OnTouchListener{
	private MyHorizontalScroller horizontalScroller;
	private OnImageClickListener iListener;
	private HashMap<View, Integer> selector = new HashMap<View, Integer>();
	private LinkedList<Integer> blueList = new LinkedList<Integer>();
	private LinkedList<Integer> whiteList = new LinkedList<Integer>();
	private int change;
	private View selectView;//选中的view
	private static int k;
	private int fixpos = 0;
	private int startpos;
	
	public interface OnImageClickListener  {
		public void click(View v);
	}

	
	public MyImageView(Context context){
		super(context);
		horizontalScroller = new MyHorizontalScroller(context);
		this.setOnTouchListener(this);
		init();
	}
	
	public MyImageView(Context context,AttributeSet attrs){
		super(context, attrs);
		horizontalScroller = new MyHorizontalScroller(context, attrs);
		this.setOnTouchListener(this);
		init();
	}
	
	public MyImageView(Context context,AttributeSet attrs,int defStyle){
		super(context, attrs, defStyle);
		horizontalScroller = new MyHorizontalScroller(context, attrs, defStyle);
		this.setOnTouchListener(this);
		init();
	}
	
	public void setOnImageClickListener(OnImageClickListener iListener){
		this.iListener = iListener;
	}
	
	public void init(){
		horizontalScroller.setOnClickItemListener(new OnItemCilckListener() {
			
			@Override
			public void onClick(View v, int pos) {
				// TODO Auto-generated method stub
				v.setBackgroundColor(Color.parseColor("#AA024DA4"));
				horizontalScroller.setfirstposition(v);
			}
		});
	}
	
	
	
	public void NextImage(View v){//按按钮后滚轴向后移动
		int distance = horizontalScroller.getChildWidth();
		horizontalScroller.scrollBy(distance, 0);
		horizontalScroller.loadNextImg();
		k = horizontalScroller.getfirstposition();
		Log.e("k", ""+k);
		View view = horizontalScroller.getChildView(k);
		for(int i = 0;i < horizontalScroller.getCount();i++){
			horizontalScroller.getChildView(i).setBackgroundColor(Color.WHITE);
		}
		view.setBackgroundColor(Color.parseColor("#AA024DA4"));
		//selectView = view;

	}
	
	public void PreImage(View v){//按按钮滚轴向前移动
		int distance = horizontalScroller.getChildWidth();
		horizontalScroller.scrollBy(-distance, 0);
		int scrollx = getScrollX();
		View view;
		if(scrollx == 0){
			horizontalScroller.loadPreImg();
			view = horizontalScroller.getChildView(0);
			view.setBackgroundColor(Color.parseColor("#AA024DA4"));
		}else {
			int i = horizontalScroller.getNextNumber(v) - 1;
			if(i < 0){
				i = horizontalScroller.getCount() - 1;
			}
			view = horizontalScroller.getChildView(i);
			view.setBackgroundColor(Color.parseColor("#AA024DA4"));
		}
		
	}
	
	
	public void ClickImage(View v){
		
		if(this.getId() == R.id.previous){
			PreImage(v);
		}else if(this.getId() == R.id.next){
			NextImage(v);
		}
	}
	
	public void getImgId(View v){
		this.selectView = v;
	}
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x1 = (int)event.getX();
			y1 = (int)event.getY();
			if(iListener != null){
				iListener.click(v);
			}
			this.setBackgroundColor(Color.parseColor("#AA024DA4"));
			for(int i = 0;i < horizontalScroller.getCount();i++){
				horizontalScroller.getChildView(i).setBackgroundColor(Color.WHITE);
			}
			selectView.setBackgroundColor(Color.parseColor("#AA024DA4"));
			break;
		case MotionEvent.ACTION_UP:
			x2 = (int)event.getX();
			y2 = (int)event.getY();
			this.setBackgroundColor(Color.parseColor("#FFFFFF"));
			ClickImage(selectView);
			break;
		default:
			break;
		}
		return true;

		
	}


	

	
}
