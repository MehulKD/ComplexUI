package com.example.adapter;

import java.util.ArrayList;

import com.example.newgoldinglauncher.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ListAdapter extends BaseAdapter{
	private ArrayList<Integer> imgList = new ArrayList<Integer>();
	private Context context;
	
	public ListAdapter(Context context,ArrayList<Integer>imgList){
		this.imgList = imgList;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = new ViewHolder();
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.imageview, null);
			viewHolder.img = (ImageView)convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		}else {
			convertView.getTag();
		}
		viewHolder.img.setImageResource(imgList.get(position));
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView img;
	}

}
