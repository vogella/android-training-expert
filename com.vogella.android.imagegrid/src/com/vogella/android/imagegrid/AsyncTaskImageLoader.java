package com.vogella.android.imagegrid;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AsyncTaskImageLoader extends AsyncTask<String, Void, Bitmap> {
	
	public AsyncTaskImageLoader(ImageView view){
		ref = new WeakReference<ImageView>(view);
	}
	
	private final WeakReference<ImageView> ref;
	private String url = null;

	@Override
	protected Bitmap doInBackground(String... params) {
		url = params[0];
		URLConnection conn;
		try {
			conn = new URL(url).openConnection();
			conn.connect();
			return BitmapFactory.decodeStream(conn.getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;

	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (ref != null && bitmap != null) {
			final ImageView imageView = ref.get();
			if (imageView != null) {
				imageView.setImageBitmap(bitmap);
			}
		}
	}
}
