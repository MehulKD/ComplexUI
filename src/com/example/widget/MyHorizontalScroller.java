package com.example.widget;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class MyHorizontalScroller extends HorizontalScrollView implements OnTouchListener,setChildViewListener{
	private static LinearLayout container;
	private static Map<View, Integer> map = new HashMap<View, Integer>();//每个view的编号
	private static Map<View, Integer> containermap = new HashMap<View, Integer>();//每个view在父容器的编号
	private int ScreenWidth;
	private int currentIndex;
	private int FirstIndex = 0;
	private static HorizontalAdapter adapter;
	private OnItemCilckListener mListener;
	private CurrentImageChangeListener cListener;
	private static LinkedList<Integer> showList = new LinkedList<Integer>();//子view显示列表
	private static LinkedList<Integer> waitlist = new LinkedList<Integer>();//子view待显示列表
	private int LastInterceptX;
	private int LastInterceptY;
	private int ChildWidth;
	private int ChildHeight;
	private static int childCountonScreen;//当前屏幕子view个数
	private LinearLayout parentLayout;
	private VelocityTracker vTracker;
	private static int firstposition;
	
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
	
	protected void loadNextImg(){//手动滑动，向后滑动
		//Log.e("movenext", "here");
		if(adapter.getCount() == 0){
			return;
		}
		int waittoadd = waitlist.removeFirst();
		int deleteshow = showList.removeFirst();
		showList.addLast(waittoadd);
		waitlist.addLast(deleteshow);
		int index = showList.get(childCountonScreen - 1);
		map.remove(container.getChildAt(0));
		containermap.remove(container.getChildAt(0));
		container.removeViewAt(0);
		View view = adapter.getView(index, null, container);
		container.addView(view);
		map.put(view, index);
		Log.e("index", ""+index);
//		for(Integer value : map.values()){
//			Log.e("mapvalue", ""+value);
//		}
		for(int k = 0;k < childCountonScreen;k++){
			containermap.put(container.getChildAt(k), k);
		}
		String clazz = this.getClass().getName();
		//Log.e("nextthis", ""+clazz);
		view.setOnTouchListener(this);
		//setchildviewListener();
		if(cListener != null){
			notifyCurrentImgChange();
		}
	}
	
	protected void loadPreImg(){//手动滑动，向前滑动
		if(adapter.getCount() == 0){
			return;
		}
		int last = container.getChildCount() - 1;
		map.remove(container.getChildAt(last));
		containermap.remove(container.getChildAt(last));
		container.removeViewAt(last);
		int waittoadd = waitlist.removeLast();
		int deleteshow = showList.removeLast();
		showList.addFirst(waittoadd);
		waitlist.addFirst(deleteshow);
		int index = showList.get(0);
		View view = adapter.getView(index, null, container);
		container.addView(view,0);
		map.put(view, index);
		for(int k = 0;k < childCountonScreen;k++){
			containermap.put(container.getChildAt(k), k);
		}
		String clazz = this.getClass().getName();
		Log.e("prethis", ""+clazz+last);
		view.setOnTouchListener(this);
		for(Integer value : map.values()){
			Log.e("mapvalue",""+value);
		}
		//setchildviewListener();
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
//			int x1 = (int)event.getX();
//			int y1 = (int)event.getY();
//			int deltaX = x - x1;
//			int deltaY = y - y1;
//			if(Math.abs(deltaX) < 10 && Math.abs(deltaY) < 10){
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
//		return Intercept;
//	}
	
	
	
	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		// TODO Auto-generated method stub
		int scrollX = getScrollX();
		vTracker.computeCurrentVelocity(1000);
		float xVelocity =  vTracker.getXVelocity();
		//Log.e("xv", ""+xVelocity);
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.e("down", "here");
			int id = 0;
			if(mListener != null){
				Log.e("mListener", "notnull"+container.getChildCount());
				for(int i = 0;i < container.getChildCount();i++){
					container.getChildAt(i).setBackgroundColor(Color.WHITE);//将所有子view背景涂白
				}
				
				mListener.onClick(v,containermap.get(v));
				Log.e("hclick",""+id);
			}
			Log.e("mListener", "null");
			break;
			
		case MotionEvent.ACTION_MOVE:
			Log.e("scrollx", ""+scrollX);
			if(scrollX >= ChildWidth){
				Log.e("go", "h");
				loadNextImg();
			}else if(scrollX == 0){
				Log.e("back", "h");
				loadPreImg();
			}
			break;
			
		case MotionEvent.ACTION_UP:
			vTracker.clear();
			break;
		default:
			break;
		}
		return true;
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
			view.setOnTouchListener(this);
			container.addView(view);
			map.put(view, i);
			containermap.put(view, i);
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
	
	public void PressToScroll(){
		scrollBy(ChildWidth, 0);
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
		
		return container.getChildAt(i);
	}
	
	public int getAllCount(){
		return adapter.getCount();
	}
	
	public int getCount(){
		return container.getChildCount();
	}
	
	public int getNextNumber(View v){
		Log.e("containermap",""+containermap.get(v));
		return containermap.get(v);
	}
	
	public View getInitView(){
		return container.getChildAt(0);
	}
	
	public void setfirstposition(View v){
		int position = containermap.get(v);
		
		this.firstposition = position;
	}
	
	public int getfirstposition(){
		Log.e("fposition", ""+firstposition);
		return firstposition;
	}

	@Override
	public void setchildviewListener() {
		// TODO Auto-generated method stub
		for(int i = 0;i < childCountonScreen;i++){
			container.getChildAt(i).setOnTouchListener(this);
		}
	}
	
}
