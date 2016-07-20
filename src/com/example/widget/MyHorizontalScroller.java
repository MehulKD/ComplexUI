package com.example.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class MyHorizontalScroller extends HorizontalScrollView implements OnClickListener{
	
	public MyHorizontalScroller(Context context){
		super(context);
		init(context);
	}
	
	public MyHorizontalScroller(Context context,AttributeSet attrs){
		super(context, attrs);
		init(context);
	}
	
	public MyHorizontalScroller(Context context,AttributeSet attrs,int defStyle){
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context){
		
	}
	
	
	
}
