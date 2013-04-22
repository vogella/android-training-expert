package com.vogella.android.imagegrid;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class FancyAnimationRunner {

	public static void runAnimation(final ImageView imageView, final Bitmap bitmap) {
		float dest = 0;
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
				imageView.setImageBitmap(bitmap);
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
