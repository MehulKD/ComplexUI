package com.example.widget;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
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
			int deltaX = x - LastX;
            int deltaY = y - LastY;
            smoothScrollBy(-deltaX, 0);
            break;
			
		case MotionEvent.ACTION_UP:
			int scrollerX = getScrollX();
			vTracker.computeCurrentVelocity(1000);
			float xVelocity = vTracker.getXVelocity();
			if(Math.abs(xVelocity) >= 50){
				ChildIndex = xVelocity > 0 ? ChildIndex - 1 : ChildIndex + 1;
			}else {
				ChildIndex = (scrollerX + ChildWidth / 2) / ChildWidth;
			}
			ChildIndex = Math.max(0, Math.min(ChildIndex, ChildSize - 1));
			int dx = ChildIndex * ChildWidth - scrollerX;
			smoothScrollBy(dx,0);
			vTracker.clear();

		default:
			break;
		}
		
		LastX = x;
		LastY = y;
		return true;
	}
	

	
	@Override
	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int measureWidth = 0;
		int measureHeight = 0;
		final int ChildCount = getChildCount();
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		Log.e("measure","here");
		int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthSpaceMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpaceMode = MeasureSpec.getMode(heightMeasureSpec);
		if(ChildCount == 0){
			setMeasuredDimension(0, 0);
			Log.e("count = 0","here");
		}else if(widthSpaceMode == MeasureSpec.AT_MOST && heightSpaceMode == MeasureSpec.AT_MOST){
			final View childView = getChildAt(0);
			measureWidth = childView.getMeasuredWidth() * ChildCount;
			measureHeight = childView.getMeasuredHeight();
			setMeasuredDimension(measureWidth, measureHeight);
			Log.e("All Most", "here");
		}else if(heightSpaceMode == MeasureSpec.AT_MOST){
			final View childView = getChildAt(0);
			measureHeight = childView.getMeasuredHeight();
			setMeasuredDimension(widthSpaceSize, childView.getMeasuredHeight());
			Log.e("h Most", "here");
		}else if(widthSpaceMode == MeasureSpec.AT_MOST){
			final View childView = getChildAt(0);
			measureWidth = childView.getMeasuredWidth() * ChildCount;
			setMeasuredDimension(measureWidth, heightSpaceSize);
			Log.e("w Most", "here");
		}
	}
	
	@Override
	public void onLayout(boolean changed,int l,int t,int r,int b){
		int childLeft = 0;
		final int childCount = getChildCount();
		ChildSize = childCount;
		
		for(int i = 0;i < childCount;i++){
			final View childView = getChildAt(i);
			if(childView.getVisibility() != View.GONE){
				Log.e("setlayout", String.valueOf(i));
				final int childWidth = childView.getMeasuredWidth();
				ChildWidth = childWidth;
				childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
				childLeft += childWidth + 10;
			}
		}
	}
	
	private void smoothScrollBy(int dx,int dy){
		scroller.startScroll(getScrollX(), 0, dx, 0,500);
		invalidate();
	}
	
	@Override
	public void computeScroll(){
		if(scroller.computeScrollOffset()){
			scrollTo(scroller.getCurrX(),scroller.getCurrY());
			postInvalidate();
		}
	}
	
	@Override
	protected void onDetachedFromWindow(){
		vTracker.recycle();
		super.onDetachedFromWindow();
	}
}
