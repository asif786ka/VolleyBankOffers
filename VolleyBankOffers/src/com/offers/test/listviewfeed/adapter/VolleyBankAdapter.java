package com.offers.test.listviewfeed.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.offers.test.listviewfeed.VolleyBankImageView;
import com.offers.test.listviewfeed.app.VolleyBankController;
import com.offers.test.listviewfeed.data.VolleyBankFeedItem;
import com.volley.bankoffers.R;

public class VolleyBankAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<VolleyBankFeedItem> feedItems;
	ImageLoader imageLoader = VolleyBankController.getInstance().getImageLoader();

	public VolleyBankAdapter(Activity activity, List<VolleyBankFeedItem> feedItems) {
		this.activity = activity;
		this.feedItems = feedItems;
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.feed_item_new, null);

		if (imageLoader == null)
			imageLoader = VolleyBankController.getInstance().getImageLoader();

		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView validDate = (TextView) convertView
				.findViewById(R.id.validdate);
		TextView availaibleCount = (TextView) convertView
				.findViewById(R.id.count);
		TextView description = (TextView) convertView.findViewById(R.id.description);

		VolleyBankImageView feedImageView = (VolleyBankImageView) convertView
				.findViewById(R.id.feedImage);

		VolleyBankFeedItem item = feedItems.get(position);

		title.setText(item.getTitle());

		validDate.setText(item.getValiddate());
		
		availaibleCount.setText(item.getCount());
		//availaibleCount.setTextColor(Color.RED);
		
		description.setText(item.getDecsription());

		// Feed image
		if (item.getImage() != null) {
			feedImageView.setImageUrl(item.getImage(), imageLoader);
			feedImageView.setVisibility(View.VISIBLE);
			feedImageView
					.setResponseObserver(new VolleyBankImageView.ResponseObserver() {
						@Override
						public void onError() {
						}

						@Override
						public void onSuccess() {
						}
					});
		} else {
			feedImageView.setVisibility(View.GONE);
		}

		return convertView;
	}

}
