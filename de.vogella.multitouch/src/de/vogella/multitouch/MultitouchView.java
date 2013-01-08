package de.vogella.multitouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

public class MultitouchView extends View {

	private static final int SIZE = 60;
	
	private SparseArray<PointF> mActivePointers;
	private Paint mPaint;
	
	public MultitouchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		mActivePointers = new SparseArray<PointF>();
		mPaint = new Paint();
		mPaint.setColor(Color.parseColor("#ffbd21"));
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		int pointerId = event.getPointerId(pointerIndex);
		int maskedAction = event.getAction() & MotionEvent.ACTION_MASK;
		
		switch (maskedAction) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN: { // we have a new pointer
				PointF point = new PointF();
				point.x = event.getX(pointerIndex);
				point.y = event.getY(pointerIndex);
				mActivePointers.put(pointerId, point);
				break;
			}
			case MotionEvent.ACTION_MOVE: { // a pointer was moved
				for(int size = event.getPointerCount(), i=0; i<size; i++) {
					PointF point = mActivePointers.get(event.getPointerId(i));
					if (point != null) {
						point.x = event.getX(i);
						point.y = event.getY(i);
					}
				}
				break;
			}
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL: { // a pointer was removed
				mActivePointers.remove(pointerId);
				break;
			}
		}
		
		invalidate(); // force onDraw to be called
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int size = mActivePointers.size(), i=0; i<size; i++) { // draw all pointers
			PointF point = mActivePointers.valueAt(i);
			if (point != null) canvas.drawCircle(point.x, point.y, SIZE, mPaint);
		}
	}
	
}
