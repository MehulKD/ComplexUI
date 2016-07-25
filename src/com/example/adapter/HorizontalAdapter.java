package com.example.adapter;

import java.util.HashMap;
import java.util.List;

import com.example.newgoldinglauncher.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HorizontalAdapter {
	private Context context;
	private List<Integer> list;
	private LayoutInflater inflater;
	
	public HorizontalAdapter(Context context,List<Integer> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	public int getCount(){
		return list.size();
	}
	
	public Object getItem(int position){
		return list.get(position);
	}
	
	public long getItemId(int position){
		return position;
	}
	
	public View getView(int position,View convertView,ViewGroup parent){
		ViewHolder viewHolder = new ViewHolder();
		if(convertView == null){
			convertView = inflater.inflate(R.layout.imageview, parent,false);
			viewHolder.img = (ImageView)convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
		}else {
			convertView.getTag();
		}
		viewHolder.img.setImageResource(list.get(position));
		return convertView;
	}
	
	class ViewHolder{
		ImageView img;
	}
	
}
