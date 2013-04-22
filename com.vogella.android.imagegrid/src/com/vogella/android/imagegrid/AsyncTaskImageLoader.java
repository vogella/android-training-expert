package com.vogella.android.imagegrid;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

public class AsyncTaskImageLoader extends AsyncTask<Object, Void, Bitmap> {
	private final WeakReference<ImageView> ref;
	public String url = null;
	public int data = 0;

	public AsyncTaskImageLoader(ImageView view) {
		ref = new WeakReference<ImageView>(view);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Bitmap doInBackground(Object... params) {
		url = (String) params[0];
		LruCache<String, Bitmap> memoryCache = (LruCache<String, Bitmap>) params[1];
		
		URLConnection conn;
		try {
			conn = new URL(url).openConnection();
			conn.connect();
			// load form the Web
			Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
			// add to cache
			memoryCache.put(url, bitmap);
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (isCancelled()) {
			bitmap = null;
		}
		if (bitmap != null) {
			final ImageView imageView = ref.get();
			if (imageView != null) {
				FancyAnimationRunner.runAnimation(imageView, bitmap);
			}
		}
	}

	static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<AsyncTaskImageLoader> bitmapWorkerTaskReference;

		public AsyncDrawable(Resources res, Bitmap bitmap, AsyncTaskImageLoader bitmapWorkerTask) {
			super(res, bitmap);
			bitmapWorkerTaskReference = new WeakReference<AsyncTaskImageLoader>(
					bitmapWorkerTask);
		}

		public AsyncTaskImageLoader getBitmapWorkerTask() {
			return bitmapWorkerTaskReference.get();
		}
	}

}
