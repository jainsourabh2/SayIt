package com.sayitviren;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class CustomSeekBar extends SeekBar {

	private ArrayList<ProgressItem> mProgressItemsList;

	public CustomSeekBar(Context context) {
		super(context);
	}

	public CustomSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void initData(ArrayList<ProgressItem> progressItemsList) {
		this.mProgressItemsList = progressItemsList;
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	protected void onDraw(Canvas canvas) {
			int progressBarWidth = getWidth();
			int progressBarHeight = getHeight();

		Paint progressPaint = new Paint();
		progressPaint.setColor(Color.WHITE);


		int left = 21;
		int leftMargin = left +30;
		int top = progressBarHeight/2;
		int right = progressBarWidth;
		int bottom = progressBarHeight/2+10;
		Rect progressRectBack = new Rect();
		progressRectBack.set(leftMargin, top, right-leftMargin, bottom);

		//Line
		canvas.drawRect(progressRectBack, progressPaint);

		top=top+5;
		//Circle
		canvas.drawCircle(leftMargin, top, left, progressPaint);
		canvas.drawCircle(((progressBarWidth)/4)+left, top, left, progressPaint);
		canvas.drawCircle((float)((progressBarWidth)/1.3)-left, top, left, progressPaint);
		canvas.drawCircle(((progressBarWidth)/2), top, left, progressPaint);
		canvas.drawCircle(progressBarWidth-leftMargin, top, left, progressPaint);


		super.onDraw(canvas);

	}

}