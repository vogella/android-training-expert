package com.vogella.android.imagegrid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.LruCache;
import android.widget.ImageView;

import com.vogella.android.imagegrid.AsyncTaskImageLoader.AsyncDrawable;

/**
 * Web image loader with LruCache.
 * @author sergej
 */
public class ImageLoader {

	private static final String TAG = "headless";
	private static final int MEM_CACHE_SIZE = 4 * 1024 * 1024; // 4MB
	
	/** Retained headless fragment holding the instance of ImageLoader */
	public static class RetainedHeadlessFragment extends Fragment {
		private ImageLoader mImageLoader = new ImageLoader();

		@Override public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}
		
		public ImageLoader getImageLoader() {
			return mImageLoader;
		}
	}
	
	/** Static methods for accessing current ImageLoader (single per activity) */
	public static ImageLoader getInstance(Activity activity) {
		FragmentManager fm = activity.getFragmentManager();
		RetainedHeadlessFragment fragment = (RetainedHeadlessFragment) 
				fm.findFragmentByTag(TAG);
		if (fragment == null) {
			fragment = new RetainedHeadlessFragment();
			fm.beginTransaction().add(fragment, TAG).commit();
		}
		return fragment.getImageLoader();
	}

	private final LruCache<String, Bitmap> mMemoryCache;
	
	/* No public constructor. Use getInstance() instead. */
	private ImageLoader() {
		mMemoryCache = new LruCache<String, Bitmap>(MEM_CACHE_SIZE) {
            @Override protected int sizeOf(String key, Bitmap value) {
            	return value.getByteCount();
            }
        };
	}
	
	/** load image from cache or from the Web */
	public void loadImage(String url, ImageView imageView, Bitmap placeholderBitmap) {
		Bitmap bitmap = mMemoryCache.get(url);
		
		if (bitmap != null) { // get from cache
			//imageView.setImageBitmap(bitmap);
			AnimationRunner.runAnimationAndSetBitmap(imageView, bitmap);
			
		} else { // load from Web
			if (cancelPotentialWork(url, imageView)) {
				AsyncTaskImageLoader task = new AsyncTaskImageLoader(imageView);
				Resources resources = imageView.getContext().getResources();
				final AsyncDrawable asyncDrawable = new AsyncDrawable(resources, placeholderBitmap, task);
				imageView.setImageDrawable(asyncDrawable);
				task.execute(url, mMemoryCache);
			}
		}
	}
	
	private static boolean cancelPotentialWork(String url, ImageView imageView) {
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
		// No task associated with the ImageView, or an existing task was cancelled
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
