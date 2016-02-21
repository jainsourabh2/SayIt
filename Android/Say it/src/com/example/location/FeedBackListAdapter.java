package com.example.location;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.sayit.common.model.Feedback;

public class FeedBackListAdapter extends ArrayAdapter<Feedback> {

	private List<Feedback> feedbackList;
	private Context context;
	private SeekbarListner sbLitener;

	private class SeekbarListner implements OnSeekBarChangeListener {

		private Drawable getThumb(int progress) {
			int id = R.drawable.r1;

			if (progress <= 10 && progress >= 0)
				id = R.drawable.r1;
			else if (progress > 10 && progress <= 25)
				id = R.drawable.r2;
			else if (progress > 25 && progress <= 50)
				id = R.drawable.r3;
			else if (progress > 50 && progress <= 85)
				id = R.drawable.r4;
			else if (progress > 85 && progress <= 100)
				id = R.drawable.r5;

			Bitmap bm = BitmapFactory
					.decodeResource(context.getResources(), id).copy(
							Bitmap.Config.ARGB_8888, true);

			return new BitmapDrawable(context.getResources(),bm);
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

			seekBar.setProgress(progress);
			seekBar.setThumb(getThumb(progress));
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

	}

	public FeedBackListAdapter(Context context, int textViewResourceId,
			List<Feedback> feedback) {
		super(context, textViewResourceId, feedback);
		this.feedbackList = feedback;
		this.context = context;
		this.sbLitener = new SeekbarListner();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Feedback feedBack = feedbackList.get(position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.feedback_list_row, parent,
				false);
		SeekBar mySeekbar = (SeekBar) rowView.findViewById(R.id.seekBar1);
		mySeekbar.setProgress(50);
		mySeekbar.setThumb(sbLitener.getThumb(50));
		mySeekbar.setOnSeekBarChangeListener(sbLitener);
		TextView feedbackTitle = (TextView) rowView
				.findViewById(R.id.feedbackTitle);

		feedbackTitle.setText(feedBack.getAttribute());
		return rowView;
	}
}
