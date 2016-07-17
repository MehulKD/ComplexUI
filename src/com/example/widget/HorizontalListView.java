package com.example.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Scroller;

public class HorizontalListView extends ViewGroup{
	private Scroller scroller;
	private VelocityTracker vTracker;
	private int LastX = 0;
	private int LastY = 0;
	private int LastXIntercept = 0;
	private int LastYIntercept = 0;
	private int ChildIndex;
	private int ChildWidth;
	private int ChildSize;
	
	public HorizontalListView(Context context){
		super(context);
		init();
	}
	
	public HorizontalListView(Context context,AttributeSet attrs){
		super(context, attrs);
		init();
	}
	
	public HorizontalListView(Context context,AttributeSet atts,int defStyle){
		super(context, atts, defStyle);
		init();
	}

	public void init(){
		if(scroller == null){
			scroller = new Scroller(getContext());
			vTracker = VelocityTracker.obtain();
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event){
		boolean intercept = false;
		int x = (int) event.getX();
		int y = (int) event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			intercept = false;
			if(!scroller.isFinished()){
				scroller.abortAnimation();
				intercept = true;
			}
			break;
			
		case MotionEvent.ACTION_MOVE:
			int deltaX = x - LastXIntercept;
			int deltaY = y - LastXIntercept;
			if(Math.abs(deltaX) > Math.abs(deltaY)){
				intercept = true;
			}else {
				intercept = false;
			}

		case MotionEvent.ACTION_UP:
			intercept = false;
			break;
		default:
			break;
		}
		LastX = x;
		LastY = y;
		LastXIntercept = x;
		LastYIntercept = y;
		
		return intercept;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		vTracker.addMovement(event);
		int x = (int)event.getX();
		int y = (int)event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(!scroller.isFinished()){
				scroller.abortAnimation();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			int scrollerX = getScrollX();
			vTracker.computeCurrentVelocity(1000);
			float xVelocity = vTracker.getXVelocity();
			if(Math.abs(xVelocity) >= 50){
				ChildIndex = xVelocity > 0 ? ChildIndex + 1 : ChildIndex - 1;
			}else {
				ChildIndex = (scrollerX + ChildWidth / 2) / ChildWidth;
			}
			ChildIndex = Math.max(0, Math.min(ChildIndex, ChildSize - 1));
			

		default:
			break;
		}
	}
}
