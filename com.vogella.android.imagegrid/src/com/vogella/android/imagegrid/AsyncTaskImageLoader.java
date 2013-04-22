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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncTaskImageLoader extends AsyncTask<String, Void, Bitmap> {
	private final WeakReference<ImageView> ref;
	public String url = null;
	public int data = 0;

	public AsyncTaskImageLoader(ImageView view) {
		ref = new WeakReference<ImageView>(view);
	}

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
		return null;

	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		final Bitmap b = bitmap;
		float dest = 0;
		if (isCancelled()) {
			bitmap = null;
		}
		if (ref != null && bitmap != null) {
			final ImageView imageView = ref.get();
			if (imageView != null) {
				dest = 360;
				if (imageView.getRotation() == 360) {
					dest = 0;
				}		
				final float rotationX= imageView.getRotationX();
				final float rotationY= imageView.getRotationY();
				float random1 = (float) Math.random()*3;
				imageView.animate().rotation(dest).setDuration(1500).scaleX(random1)
				.scaleY(random1).withEndAction(new Runnable() {

					@Override
					public void run() {
						imageView.setImageBitmap(b);
						imageView.animate()
								.rotationXBy(100)
								.rotation(
										Math.abs(360 - imageView
												.getRotation()))
								.scaleX(1F).scaleY(1F)
								.setDuration(1500).withEndAction(new Runnable() {
									
									@Override
									public void run() {
										imageView.animate().rotationX(rotationX).rotationY(rotationY).setDuration(1000);
									}
								});
					}
				});
			}
		}
	}

	static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<AsyncTaskImageLoader> bitmapWorkerTaskReference;

		public AsyncDrawable(Resources res, Bitmap bitmap,
				AsyncTaskImageLoader bitmapWorkerTask) {
			super(res, bitmap);
			bitmapWorkerTaskReference = new WeakReference<AsyncTaskImageLoader>(
					bitmapWorkerTask);
		}

		public AsyncTaskImageLoader getBitmapWorkerTask() {
			return bitmapWorkerTaskReference.get();
		}
	}

	


}
