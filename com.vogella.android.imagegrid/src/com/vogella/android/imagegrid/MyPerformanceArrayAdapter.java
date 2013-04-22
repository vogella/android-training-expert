package com.vogella.android.imagegrid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MyPerformanceArrayAdapter extends ArrayAdapter {
	private final Activity context;
	private final String[] names;

	static class ViewHolder {
		public ImageView image;
	}

	public MyPerformanceArrayAdapter(Activity context, String[] names) {
		super(context, R.layout.rowlayout, names);
		this.context = context;
		this.names = names;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.rowlayout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) rowView
					.findViewById(R.id.imageView1);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		AsyncTaskImageLoader task = new AsyncTaskImageLoader(holder.image);
		task.execute(names[position]);
		return rowView;
	}
}