package com.example.widget;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.security.auth.PrivateCredentialPermission;

import com.example.adapter.HorizontalAdapter;
import com.example.newgoldinglauncher.R;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class MyHorizontalScroller extends HorizontalScrollView implements OnClickListener{
	private static LinearLayout container;
	private Map<View, Integer> map = new HashMap<View, Integer>();
	private Map<Integer, View> changemap = new HashMap<Integer, View>();
	private int ScreenWidth;
	private int currentIndex;
	private int FirstIndex = 0;
	private static HorizontalAdapter adapter;
	private OnItemCilckListener mListener;
	private CurrentImageChangeListener cListener;
	private static LinkedList<Integer> showList = new LinkedList<Integer>();
	private static LinkedList<Integer> waitlist = new LinkedList<Integer>();
	private int LastX;
	private int LastY;
	private int ChildWidth;
	private int ChildHeight;
	private static int childCountonScreen;
	private LinearLayout parentLayout;
	private VelocityTracker vTracker;
	
	@Override
	public void onClick(View v){
		Log.e("hclick","here");
		if(mListener != null){
			for(int i = 0;i < container.getChildCount();i++){
				container.getChildAt(i).setBackgroundColor(Color.WHITE);
			}
			mListener.onClick(v,map.get(v));
		}
	}
	
	public interface OnItemCilckListener{
		public void onClick(View v,int pos);
	}
	
	public interface CurrentImageChangeListener{
		public void OnCurrentChange(int position,View v);
	}
	
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
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		ScreenWidth = outMetrics.widthPixels;
		vTracker = VelocityTracker.obtain();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		container = (LinearLayout)getChildAt(0);
	}
	
	protected void loadNextImg(){
		if(adapter.getCount() == 0){
			return;
		}
		int waittoadd = waitlist.remove();
		int deleteshow = showList.remove();
		showList.addLast(waittoadd);
		waitlist.addLast(deleteshow);
		int index = showList.get(childCountonScreen - 1);
		map.remove(container.getChildAt(0));
		changemap.remove(0);
		container.removeViewAt(0);
		View view = adapter.getView(index, null, container);
		container.addView(view);
		map.put(view, index);
		changemap.put(index, view);
		view.setOnClickListener(this);
		if(cListener != null){
			notifyCurrentImgChange();
		}
	}
	
	protected void loadPreImg(){
		if(adapter.getCount() == 0){
			return;
		}
		int last = container.getChildCount() - 1;
		map.remove(container.getChildAt(last));
		changemap.remove(last);
		container.removeViewAt(last);
		int waittoadd = waitlist.removeLast();
		int deleteshow = showList.removeLast();
		showList.addFirst(waittoadd);
		waitlist.addFirst(deleteshow);
		int index = showList.get(0);
		View view = adapter.getView(index, null, container);
		container.addView(view,0);
		map.put(view, index);
		changemap.put(index, view);
		view.setOnClickListener(this);
		if(cListener != null){
			notifyCurrentImgChange();
		}
	}
	
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent event){
//		boolean Intercept = false;
//		int x = (int)event.getX();
//		int y = (int)event.getY();
//		
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			Intercept = false;
//			break;
//			
//		case MotionEvent.ACTION_MOVE:
//			int deltaX = x - LastInterceptX;
//			int deltaY = y - LastInterceptY;
//			if(Math.abs(deltaX) > Math.abs(deltaY)){
//				Intercept = false;
//			}else {
//				Intercept = true;
//			}
//			break;
//		case MotionEvent.ACTION_UP:
//			Intercept = true;
//		default:
//			break;
//		}
//		
//		return Intercept;
//	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int x = (int)event.getX();
		int y = (int)event.getY();

		int scrollX = getScrollX();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if(scrollX >= ChildWidth){
				loadNextImg();
			}
			if(scrollX == 0){
				loadPreImg();
			}
			break;
		case MotionEvent.ACTION_UP:
			vTracker.clear();
			break;
			
		}
		LastX = x;
		LastY = y;
		return super.onTouchEvent(event);
		
	}
	
	
	
	public void initData(HorizontalAdapter adapter){
		this.adapter = adapter;
		container = (LinearLayout) getChildAt(0);
		final View view = adapter.getView(0, null, container);
		container.addView(view);
		if(ChildWidth == 0 && ChildHeight == 0){
			int w =View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			view.measure(w, h);
			ChildWidth = view.getMeasuredWidth();
			ChildHeight = view.getMeasuredHeight();
			Log.e("childwidth",""+ChildWidth);
			//childCountonScreen = (ScreenWidth/ ChildWidth == 0)?(ScreenWidth / ChildWidth) + 1:(ScreenWidth / ChildWidth) + 1;
			childCountonScreen = 9;
		}
		initScreen(childCountonScreen);
	}
	
	public void initScreen(int childCountonScreen){
		container = (LinearLayout) getChildAt(0);
		container.removeAllViews();
		map.clear();
		Log.e("countonScreen","" + childCountonScreen);
		for(int i = 0;i < childCountonScreen;i++){
			View view = adapter.getView(i, null, container);
			view.setOnClickListener(this);
			container.addView(view);
			map.put(view, i);
			changemap.put(i, view);
			currentIndex = i;
			showList.add(i);
		}
		Log.e("count", ""+(adapter.getCount()-childCountonScreen));
		for(int i = childCountonScreen;i < adapter.getCount();i++){
			waitlist.add(i);
		}
	}
	
	public void notifyCurrentImgChange(){
		int first = showList.peek();
		for (int i = 0; i < container.getChildCount(); i++)
		{
			container.getChildAt(i).setBackgroundColor(Color.WHITE);
		}
		
		cListener.OnCurrentChange(first,container.getChildAt(0));
	}
	
	public void setOnClickItemListener(OnItemCilckListener mListener){
		this.mListener = mListener;
	}
	
	public void setOnCurrentImageChangeListener(CurrentImageChangeListener cListener){
		this.cListener = cListener;
	}
	
	
	@Override
	protected void onDetachedFromWindow(){
		vTracker.recycle();
		super.onDetachedFromWindow();
	}
	
	public int getChildId(int i){
		return map.get(container.getChildAt(i));
	}
	
	public int getChildWidth(){
		return ChildWidth;
	}
	
	public View getChildView(int i){
		Log.e("id", "i");
		if(container instanceof LinearLayout){
			Log.e("container", "true");
		}else {
			Log.e("container", "false");
		}
		return container.getChildAt(i);
	}
	
	public int getAllCount(){
		return adapter.getCount();
	}
	
	public int getCount(){
		return container.getChildCount();
	}
}
