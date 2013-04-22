package com.vogella.android.imagegrid;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.vogella.android.imagegrid.AsyncTaskImageLoader.AsyncDrawable;

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
		if (cancelPotentialWork(names[position], holder.image)) {
			AsyncTaskImageLoader task = new AsyncTaskImageLoader(holder.image);
			final AsyncDrawable asyncDrawable = new AsyncDrawable(
					context.getResources(), null, task);
			holder.image.setImageDrawable(asyncDrawable);
			task.execute(names[position]);
		}
		return rowView;
	}

	public static boolean cancelPotentialWork(String url, ImageView imageView) {
		final AsyncTaskImageLoader bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			final String bitmapData = bitmapWorkerTask.url;
			if (bitmapData != url) {
				// Cancel previous task
				bitmapWorkerTask.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}
		// No task associated with the ImageView, or an existing task was
		// cancelled
		return true;
	}

	private static AsyncTaskImageLoader getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}
}