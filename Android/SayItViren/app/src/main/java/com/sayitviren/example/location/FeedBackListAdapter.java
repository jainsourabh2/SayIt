package com.sayitviren.example.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sayitviren.CustomSeekBar;
import com.sayitviren.ProgressItem;
import com.sayitviren.R;
import com.sayitviren.sayit.common.model.Feedback;

public class FeedBackListAdapter extends ArrayAdapter<Feedback> {

	private List<Feedback> feedbackList;
	private Context context;
	private SeekbarListner sbLitener;
	private CustomSeekBar seekbar;
	private EditText txtSeekProgress;
	private ToggleButton btnToogleSeek;

	private float totalSpan = 50;
	private float redSpan = 5;
	private float blueSpan = 5;
	private float greenSpan = 5;
	private float yellowSpan = 5;
	private float darkGreySpan;
	private HashMap<Integer,Integer> progressValue;
	private ArrayList<ProgressItem> progressItemList;
	private ProgressItem mProgressItem;

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
			progressValue.put((Integer)seekBar.getTag(),progress);
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
		progressValue = new HashMap<Integer,Integer>();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Feedback feedBack = feedbackList.get(position);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.feedback_list_row, parent,
				false);

		seekbar = (CustomSeekBar) rowView.findViewById(R.id.seekBar1);
		seekbar.setTag(position);
		if(progressValue.get(position)== null) {
			seekbar.setProgress(50);
			seekbar.setThumb(sbLitener.getThumb(50));
		}else{
			seekbar.setProgress(progressValue.get(position));
			seekbar.setThumb(sbLitener.getThumb(progressValue.get(position)));
		}
		seekbar.setOnSeekBarChangeListener(sbLitener);
		TextView feedbackTitle = (TextView) rowView
				.findViewById(R.id.feedbackTitle);

		feedbackTitle.setText(feedBack.getAttribute());
		return rowView;
	}
	private void initDataToSeekbar() {
		progressItemList = new ArrayList<ProgressItem>();
		// red span
		mProgressItem = new ProgressItem();
		mProgressItem.progressItemPercentage = (10);
		Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
		mProgressItem.color = R.color.red;
		progressItemList.add(mProgressItem);
		// blue span
		mProgressItem = new ProgressItem();
		mProgressItem.progressItemPercentage = (20);
		mProgressItem.color = R.color.blue;
		progressItemList.add(mProgressItem);
		// green span
		mProgressItem = new ProgressItem();
		mProgressItem.progressItemPercentage = 30;
		mProgressItem.color = R.color.green;
		progressItemList.add(mProgressItem);
		// yellow span
		mProgressItem = new ProgressItem();
		mProgressItem.progressItemPercentage = 40;
		mProgressItem.color = R.color.yellow;
		progressItemList.add(mProgressItem);
		// greyspan
		mProgressItem = new ProgressItem();
		mProgressItem.progressItemPercentage = 50;
		mProgressItem.color = R.color.grey;
		progressItemList.add(mProgressItem);


	}
}
