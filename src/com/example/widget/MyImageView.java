package com.example.widget;

import java.util.HashMap;
import java.util.LinkedList;

import com.example.newgoldinglauncher.R;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnClickListener;

public class MyImageView extends ImageView {
	private MyHorizontalScroller horizontalScroller;
	private OnImageClickListener iListener;
	private HashMap<View, Integer> selector = new HashMap<View, Integer>();
	private LinkedList<Integer> blueList = new LinkedList<Integer>();
	private LinkedList<Integer> whiteList = new LinkedList<Integer>();
	private int change;
	private int id;
	public interface OnImageClickListener  {
		public void onClick(View v);
	}

	
	public MyImageView(Context context){
		super(context);
		horizontalScroller = new MyHorizontalScroller(context);	
	}
	
	public MyImageView(Context context,AttributeSet attrs){
		super(context, attrs);
		horizontalScroller = new MyHorizontalScroller(context, attrs);	
	}
	
	public MyImageView(Context context,AttributeSet attrs,int defStyle){
		super(context, attrs, defStyle);
		horizontalScroller = new MyHorizontalScroller(context, attrs, defStyle);
	}
	
	public void setOnImageClickListener(OnImageClickListener iListener){
		this.iListener = iListener;
	}
	
	public void NextImage(int i){
		View view = horizontalScroller.getChildView(i + 1);
		for(int k = 0;k < horizontalScroller.getCount();k++){
			horizontalScroller.getChildView(i).setBackgroundColor(Color.WHITE);
		}
		view.setBackgroundColor(Color.parseColor("#AA024DA4"));
		int distance = horizontalScroller.getChildWidth();
		scrollBy(distance, 0);
		horizontalScroller.loadNextImg();
	}
	
	public void PreImage(int i){
		int distance = horizontalScroller.getChildWidth();
		scrollBy(-distance, 0);
		int scrollx = getScrollX();
		View view;
		for(int k = 0;k < horizontalScroller.getCount();k++){
			horizontalScroller.getChildView(i).setBackgroundColor(Color.WHITE);
		}
		if(scrollx == 0){
			horizontalScroller.loadPreImg();
			view = horizontalScroller.getChildView(0);
			view.setBackgroundColor(Color.parseColor("#AA024DA4"));
		}else {
			view = horizontalScroller.getChildView(i - 1);
			view.setBackgroundColor(Color.parseColor("#AA024DA4"));
		}
		
	}
	
	public void getSelector(HashMap<View, Integer> selector){
		this.selector = selector;
	}
	
	public void ClickImage(int id){
		if(this.getId() == R.id.previous){
			PreImage(id);
		}else if(this.getId() == R.id.next){
			NextImage(id);
		}
	}
	
	public void getImgId(int id){
		this.id = id;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.setBackgroundColor(Color.parseColor("#AA024DA4"));
			break;
		case MotionEvent.ACTION_UP:
			this.setBackgroundColor(Color.parseColor("#FFFFFF"));
			ClickImage(id);
			break;
		default:
			break;
		}
		return true;
	}


	
}
